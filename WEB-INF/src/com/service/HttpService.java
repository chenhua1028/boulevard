package com.service;

import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HttpService
{
	/**
	 * 一种包含N个webservice的容器对象，其中含有某种特定的方法（此方法被注解@WebExporter）
	 */
	private Object obj;

	/**
	 * 被注解@WebExporter的方法，提供最基本的web服务
	 */
	private Method method;

	public Method getMethod()
	{
		return method;
	}

	public void setMethod(Method method)
	{
		this.method = method;
	}

	public Class<?> getRequstClass()
	{
		if (method.getParameterTypes().length > 1)
		{
			return method.getParameterTypes()[0];
		}
		return null;
	}

	public Class<?> getResponseClass()
	{
		return method.getReturnType();
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public void setObj(Object reqObj)
	{
		this.obj = reqObj;
	}

	public Object getObj()
	{
		return obj;
	}

}
