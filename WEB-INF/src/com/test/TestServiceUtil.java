package com.test;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import com.jsits.commons.util.ServiceUtil;
import com.service.HttpServiceManager;
import com.service.parser.ServiceParserManager;

public class TestServiceUtil
{
	@Test
	public void getService()
	{

		try
		{
			ServiceUtil.getService(HttpServiceManager.class);

			ServiceUtil.getService(ServiceParserManager.class);

			System.out.println(ToStringBuilder.reflectionToString(StringUtils.split("/bridge/json/test.jsp", "/")));

			System.out.println(StringUtils.split("/bridge/json/test.jsp", "/")[1]);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Test
	public void ss()
	{
		String[] msgs = new String[] { "sss", "dfdd" };
		System.out.println(ArrayUtils.toString(msgs, "ww"));
	}

	public void test1(String paramName)
	{
		if (paramName.equals("password"))
		{

		}
	}

}
