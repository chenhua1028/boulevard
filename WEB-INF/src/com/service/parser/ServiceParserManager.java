package com.service.parser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.util.ServiceAssert;

public class ServiceParserManager
{
	private static Logger logger = Logger.getLogger(ServiceParserManager.class);

	/**
	 * <protocal{json/xml}, HttpServiceParser>
	 */
	private Map<String, ServiceParser> parserMap = new ConcurrentHashMap<String, ServiceParser>();

	public void setParserMap(Map<String, ServiceParser> parserMap)
	{
		this.parserMap = parserMap;
	}

	public ServiceParser getParser(String uri)
	{
		/* 	request url: "/bridge/json/test" 			*/
		/* 	request url: "/json/test" 					*/
		
		/* 	request url: "/bridge/xml/test" 			*/
		/* 	request url: "/xml/test" 					*/
		
		/* 	request url: "/bridge/upload/test" 		*/
		/* 	request url: "/upload/test" 				*/
		
		String[] us = StringUtils.split(uri, "/");

		String protocal = null;
		if (us.length == 2)
		{
			protocal = us[0];
		} else
		{
			protocal = us[1];
		}

		ServiceParser parser = parserMap.get(protocal);

		ServiceAssert.isNotNull(parser, 404, "The protocal in the request (", uri, ") is ", protocal, "; we just support protocal{json}");
		return parser;
	}

	private ServiceParserManager()
	{
	}

	@SuppressWarnings("unused")
	private void init()
	{
		logger.info("Deployed " + parserMap.size() + " parsers of service: " + parserMap);
	}

	private String charset = "utf-8";

	public String getCharset()
	{
		return charset;
	}

	public void setCharset(String charset)
	{
		this.charset = charset;
	}

}
