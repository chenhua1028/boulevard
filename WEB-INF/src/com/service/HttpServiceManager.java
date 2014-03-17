package com.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.util.ClassUtil;
import com.jsits.commons.util.ServiceAssert;
import com.service.exporter.WebExporter;
import com.webservice.WebServices;

public class HttpServiceManager
{
	private static Logger logger = Logger.getLogger(HttpServiceManager.class);

	private List<String> packageList = new ArrayList<String>(38);

	private Map<String, HttpService> serviceMap = new ConcurrentHashMap<String, HttpService>(100);

	private HttpServiceManager()
	{
	}

	private List<Class<?>> getServiceClassList()
	{
		List<Class<?>> classList = new ArrayList<Class<?>>();

		for (String packageName : packageList)
		{
			classList.addAll(ClassUtil.getClasses(packageName));
		}

		classList.add(WebServices.class);

		return classList;
	}

	@SuppressWarnings("unused")
	private void init() throws InstantiationException, IllegalAccessException
	{
		List<Class<?>> classList = getServiceClassList();

		// List<Object> exporterList = new ArrayList<Object>(classList.size());
		// for (Class<?> c : classList)
		// {
		// Object obj = c.newInstance();
		// exporterList.add(obj);
		// }

		// logger.info("Exported " + exporterList.size() + " classes... ");

		for (Class<?> c : classList)
		{
			// logger.info("CLASS   : " + c.getClass().getName());

			Object obj = c.newInstance();

			for (Method m : c.getMethods())
			{
				WebExporter anno = m.getAnnotation(WebExporter.class);
				if (anno != null)
				{
					logger.info("  METHOD: " + m);

					HttpService service = new HttpService();
					service.setObj(obj);
					service.setMethod(m);

					registService(anno.value(), service);
				}
			}
		}

		print();
	}

	private void registService(String url, HttpService service)
	{
		ServiceAssert.isNull(serviceMap.get(url), 444, "\nURL:\"", url, "\" more than 1 config, please change other service named \"", url, "\" to other diffrent names!");

		serviceMap.put(url, service);

	}

	public Map<String, HttpService> getHttpServices()
	{
		return serviceMap;
	}

	public HttpService getHttpService(String url)
	{
		/*
		 * /boulevard/json/test /json/test
		 */
		String[] us = StringUtils.split(url, "/");

		String uri = null;

		if (us.length == 2)
		{
			uri = "/" + us[0] + "/" + us[1];
		} else
		{
			uri = "/" + us[1] + "/" + us[2];
		}

		HttpService service = serviceMap.get(uri);

		ServiceAssert.isNotNull(service, 404, "Can not find this service:", url);

		return service;
	}

	private void print()
	{
		// System.out.println("Exported " + serviceMap.size() + " services... ");
	}

	public void setPackageList(List<String> packageList)
	{
		this.packageList = packageList;
	}

}
