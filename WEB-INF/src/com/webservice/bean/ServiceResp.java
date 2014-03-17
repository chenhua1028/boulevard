package com.webservice.bean;

public class ServiceResp
{
	private int retCode;

	private String msg;

	private Object result;

	public ServiceResp()
	{
	}

	public ServiceResp(String msg)
	{
		this.msg = msg;
	}

	public int getRetCode()
	{
		return retCode;
	}

	public void setRetCode(int retCode)
	{
		this.retCode = retCode;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}

}
