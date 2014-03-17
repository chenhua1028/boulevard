package com.webservice.bean;

import java.util.Properties;

public class UploadReq
{
	private Properties properties;

	private boolean success;
	
	public static final String URLS="URLS";
	
	public String getSaveDir()
	{
		return "doc/temp";
	}

	public Properties getProperties()
	{
		return properties;
	}

	public void setProperties(Properties properties)
	{
		this.properties = properties;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

}
