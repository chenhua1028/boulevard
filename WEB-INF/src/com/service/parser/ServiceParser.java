package com.service.parser;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;

public abstract class ServiceParser
{

	public abstract Object parseToObj(HttpServletRequest request, Class<?> klass);

	public abstract String parseFromObj(Object obj);

	/**
	 * @description
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	protected void setCharset(HttpServletRequest request) throws UnsupportedEncodingException
	{
		String charset = getCharset();

		if (StringUtils.equalsIgnoreCase("GBK", charset))
		{
			request.setCharacterEncoding("GBK");
		} else if (StringUtils.equalsIgnoreCase("utf-8", charset))
		{
			if (isIeBrowser(request))
			{
				request.setCharacterEncoding("GBK");
			} else
			{
				request.setCharacterEncoding("UTF-8");
			}
		} else
		{
			throw new ServiceException(811811, "Only surrport charset GBK/utf-8 ,please change config in \"boulevard.spring.xml ,property:charset\" !");
		}

	}

	private boolean isIeBrowser(HttpServletRequest request)
	{
		String userAgent = request.getHeader("User-Agent");

		if (StringUtils.contains(userAgent, "IE") || StringUtils.contains(userAgent, "Trident"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * @description
	 * @return
	 */
	private String getCharset()
	{
		ServiceParserManager manager = ServiceUtil.getService(ServiceParserManager.class);

		return manager.getCharset();

	}

}
