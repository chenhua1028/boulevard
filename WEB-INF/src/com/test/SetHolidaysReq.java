package com.test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SetHolidaysReq
{

	private String month;

	private int[] workTypes;

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public int[] getWorkTypes()
	{
		return workTypes;
	}

	public void setWorkTypes(int[] workTypes)
	{
		this.workTypes = workTypes;
	}

	/**
	*@description
	*@param strings
	*/
	public void setStrTypes(String[] strings)
	{
	}



}
