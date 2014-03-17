package com.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jsits.commons.db.TableColumn;

public abstract class TableProcessor
{
	/**
	 * 表头，自动从recordKlass()返回的记录类读取属性
	 */
	private List<TableHead> heads;

	/**数据记录的类class
	*@description
	*@return
	*/
	public abstract Class<?> recordKlass();

	private String title;

	public List<TableHead> getHeads()
	{
		return heads;
	}

	public TableProcessor()
	{
		if (heads == null)
		{
			heads = new ArrayList<TableHead>(6);
			for (Field f : recordKlass().getDeclaredFields())
			{
				TableColumn columnName = f.getAnnotation(TableColumn.class);

				if (columnName != null)
				{
					heads.add(new TableHead(f.getName(), columnName.display(), columnName.index()));
				} else
				{
					heads.add(new TableHead(f.getName()));
				}

			}

			Comparator<TableHead> c = new Comparator<TableHead>()
			{
				public int compare(TableHead o1, TableHead o2)
				{
					return o1.getColumnIndex() - o2.getColumnIndex();
				}
			};
			Collections.sort(heads, c);
		}

	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}
