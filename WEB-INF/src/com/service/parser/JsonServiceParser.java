package com.service.parser;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import com.jsits.commons.util.JsonUtil;
import com.jsits.commons.util.ObjUtil;
import com.jsits.commons.util.ServiceException;

public class JsonServiceParser extends ServiceParser
{
	public String parseFromObj(Object obj)
	{
		return JsonUtil.obj2Json(obj);
	}

	public Object parseToObj(HttpServletRequest request, Class<?> reqKlass)
	{
		try
		{
			setCharset(request);

			Object req = reqKlass.newInstance();

			Field[] fields = reqKlass.getDeclaredFields();
			for (Field field : fields)
			{
				String paramName = field.getName();
				String paramValue = request.getParameter(paramName);

				ObjUtil.setParamValue(req, field, paramValue);
			}

			return req;

		} catch (Exception e)
		{
			throw new ServiceException(800802, "Convertor request Str to object  failed!\n", "\n CLASS:", reqKlass.getCanonicalName(), " \n ", e.toString());
		}
	}

}
