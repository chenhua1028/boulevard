package com.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class ReflectTest
{
	@Test
	public void test() throws Exception
	{
		
		System.out.println(Apple.class.getCanonicalName());
		
		String s="/boulevard/json/service";
		
		String  deployPath=StringUtils.split(s,"/")[0];
		
		System.out.println(deployPath );
	}

}
