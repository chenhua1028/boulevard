package com.service.parser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jsits.commons.util.JsonUtil;
import com.jsits.commons.util.ServiceAssert;
import com.jsits.commons.util.ServiceException;
import com.webservice.bean.UploadReq;

public class UploadServiceParser extends ServiceParser
{
	public String parseFromObj(Object obj)
	{
		return JsonUtil.obj2Json(obj);
	}

	public Object parseToObj(HttpServletRequest request, Class<?> reqKlass)
	{
		Properties ps = new Properties();

		List<String> urlList = new ArrayList<String>();

		UploadReq req = null;
		try
		{
			req = (UploadReq) reqKlass.newInstance();
		} catch (InstantiationException e2)
		{
			e2.printStackTrace();
		} catch (IllegalAccessException e2)
		{
			e2.printStackTrace();
		}

		String path = System.getProperty("catalina.home") + "/" + req.getSaveDir();

		try
		{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 8);// 设置8k的缓存空间
			File dir = new File(path);
			if (dir.exists())
			{
				if (!dir.isDirectory())
				{
					dir.delete();
					ServiceAssert.isTrue(dir.mkdir(), 10018, "Can not create dir :" + path + ",be sure it exists");
				}
			} else
			{
				ServiceAssert.isTrue(dir.mkdir(), 10018, "Can not create dir :" + path + ",be sure it exists");
			}
			factory.setRepository(dir);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");// 设置文件名处理中文编码

			FileItemIterator fii = upload.getItemIterator(request);// 使用遍历类
			while (fii.hasNext())
			{
				FileItemStream fis = fii.next();
				if (fis.isFormField())
				{
					InputStreamReader in = new InputStreamReader(fis.openStream(), "UTF-8");

					String fieldName = fis.getFieldName();
					String value = getStr(in);
					ps.put(fieldName, value);

				} else
				{
					String fileName = fis.getName();
					InputStream in = fis.openStream();

					saveFile(in, path + "/" + fileName);

					urlList.add(req.getSaveDir() + "/" + fileName);
				}
			}

			ps.put(UploadReq.URLS, urlList);

			req.setSuccess(true);
			req.setProperties(ps);

		} catch (FileUploadBase.InvalidContentTypeException e)
		{
			try
			{
				req.setSuccess(false);
				req.setProperties(ps);

			} catch (Exception e1)
			{
				e1.printStackTrace();
				return null;
			}

		} catch (ServiceException e)
		{
			throw e;
		} catch (Exception e)
		{
			try
			{
				req.setSuccess(false);
				req.setProperties(ps);
			} catch (Exception e1)
			{
				e1.printStackTrace();
				return null;
			}
		}

		return req;
	}

	private String getStr(InputStreamReader in) throws IOException
	{
		StringBuffer sb = new StringBuffer();

		BufferedReader br = new BufferedReader(in);
		while (true)
		{
			String temp = br.readLine();
			if (temp != null)
			{
				sb.append(temp);
			} else
			{
				break;
			}
		}

		return sb.toString();
	}

	public boolean saveFile(InputStream in, String fileTo)
	{
		try
		{
			FileOutputStream out = new FileOutputStream(fileTo);
			byte[] bt = new byte[1024];
			int count;
			while ((count = in.read(bt)) > 0)
			{
				out.write(bt, 0, count);
			}
			close(in, out);

			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	private void close(Closeable... c)
	{
		for (int i = 0; i < c.length; i++)
		{
			try
			{
				c[i].close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
