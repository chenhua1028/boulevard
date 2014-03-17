package com.webservice.bean;

public class UploadResp
{

	private String msg;

	private int retCode;

	public UploadResp(int retCode, String msg)
	{
		setMsg(msg);
		setRetCode(retCode);
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
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

}
