package com.test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class StrTest
{

	@Test
	public void hh()
	{
		try
		{
			System.out.println(URLEncoder.encode("你好", "UTF-8"));
			System.out.println(URLDecoder.decode("\u7535\u8B66", "UTF-8"));
			
			List<String> urlList = new ArrayList<String>();
			urlList.add("xxx");
			urlList.add("ffgj");
			
			System.out.println(ArrayUtils.toString(urlList));
			
		} catch (Exception e)
		{

		}
	}

}
