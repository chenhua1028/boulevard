package com.service.servlet;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jsits.commons.util.ObjUtil;
import com.jsits.commons.util.ServiceAssert;
import com.jsits.commons.util.ServiceUtil;
import com.service.HttpService;
import com.service.HttpServiceManager;
import com.service.parser.ServiceParser;
import com.service.parser.ServiceParserManager;

public class ServiceServlet extends HttpServlet
{

	private static Logger logger = Logger.getLogger(HttpServlet.class);
	private static final long serialVersionUID = 873157461077040591L;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		checkServerConfig();

		System.out.println("Deploy WebApp:" + config.getServletContext().getContextPath() + "\n    Exported " + ServiceUtil.getService(HttpServiceManager.class).getHttpServices().size()
				+ " webservices... ");

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("Request:\n\tMethod       : " + request.getMethod() + "\n\tRequestURI   : " + request.getRequestURI() + "\n\tRequestData  : " + request.getQueryString());

		// sessionShare(request, response);

		checkRequest(request);

		HttpService service = ServiceUtil.getService(HttpServiceManager.class).getHttpService(request.getRequestURI());

		ServiceParser parser = ServiceUtil.getService(ServiceParserManager.class).getParser(request.getRequestURI());

		Object respObj = null;
		if (service.getRequstClass() == null)
		{
			if (service.getMethod().getParameterTypes() == null || service.getMethod().getParameterTypes().length == 0)
			{
				respObj = ObjUtil.invoke(service.getMethod(), service.getObj());
			} else
			{
				respObj = ObjUtil.invoke(service.getMethod(), service.getObj(), request);
			}
		} else
		{
			Object reqObj = parser.parseToObj(request, service.getRequstClass());

			respObj = ObjUtil.invoke(service.getMethod(), service.getObj(), reqObj, request);
		}

		String rspData = parser.parseFromObj(respObj);

		write2Response(response, rspData);

		logger.info("Response:\n\tResponseData : " + rspData);

		return;

	}

	/**
	 * @description
	 */
	private void checkServerConfig()
	{
		String config = System.getProperty("catalina.home") + "/conf/server.xml";
		XPathFactory factory = XPathFactory.newInstance();
		XPath path = factory.newXPath();
		try
		{
			NodeList nodes = (NodeList) path.evaluate("//Service/Connector/@useBodyEncodingForURI", new InputSource(new FileInputStream(config)), XPathConstants.NODESET);

			if (nodes.getLength() == 0 || nodes.item(0).getNodeValue() == null || Boolean.parseBoolean(nodes.item(0).getNodeValue()) == false)
			{
				System.out
						.println("\nError: **************************************************************\nbe sure the value of property \"//Service/Connector/@useBodyEncodingForURI\" is \"true\" in the file:server.xml!\n");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private static String id = null;

	/**
	 * session共享
	 * 
	 * @description
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void sessionShare(HttpServletRequest request, HttpServletResponse response)
	{
		if (id == null)
		{
			HttpSession session = request.getSession();
			id = session.getId();

			System.out.println("Created  new SessionId : " + session.getId());

		} else if (request.getSession(false) == null)
		{
			Cookie cookie = new Cookie("JSESSIONID", id);
			response.addCookie(cookie);

			System.out.println("Set Response SessionId : " + id + " ,Session == null");
		} else
		{
			System.out.println("Http Request SessionId : " + request.getSession(false).getId());
		}
	}

	private void write2Response(HttpServletResponse response, String retStr) throws IOException
	{
		ServletOutputStream out = response.getOutputStream();

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		out.write(retStr.getBytes("utf-8"));

		out.flush();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	/**
	 * uri split 长度=3 ，例如
	 * 
	 * 127.0.0.1/bridge/json/login 127.0.0.1/json/login
	 * 
	 * @param url
	 */
	private void checkRequest(HttpServletRequest request)
	{
		String[] urls = StringUtils.split(request.getRequestURI(), "/");

		ServiceAssert.isTrue(urls != null && (urls.length == 3 || urls.length == 2), 503, request.getRequestURI(), " is not a valid URL!");
	}

}
