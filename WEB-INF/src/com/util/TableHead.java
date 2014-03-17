package com.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TableHead
{
	private String fieldName;

	private String showName;

	private int columnIndex;

	public TableHead(String fieldName, String showName, int columnIndex)
	{
		setFieldName(fieldName);
		setShowName(showName);
		setColumnIndex(columnIndex);
	}

	public TableHead(String fieldName)
	{
		setFieldName(fieldName);
		setShowName(fieldName);
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getShowName()
	{
		return showName;
	}

	public void setShowName(String showName)
	{
		this.showName = showName;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getColumnIndex()
	{
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex)
	{
		this.columnIndex = columnIndex;
	}

}
