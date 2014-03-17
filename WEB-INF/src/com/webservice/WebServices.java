package com.webservice;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.util.ServiceUtil;
import com.service.HttpService;
import com.service.HttpServiceManager;
import com.service.exporter.WebExporter;
import com.webservice.bean.ServiceReq;
import com.webservice.bean.ServiceResp;

public class WebServices
{
	@WebExporter("/json/services")
	public ServiceResp services(HttpServletRequest request)
	{
		StringBuilder sb = new StringBuilder();

		Map<String, HttpService> map = ServiceUtil.getService(HttpServiceManager.class).getHttpServices();

		for (String url : map.keySet())
		{
			if (!StringUtils.contains(url, "json/service"))
			{
				sb.append("<a href='#' data-url=json/service?method=").append(url).append(">").append(url).append("</a><br>");
			}
		}

		return new ServiceResp(sb.toString());

	}

	@WebExporter("/json/service")
	public ServiceResp service(ServiceReq req, HttpServletRequest request)
	{
		String deployPath = "/" + StringUtils.split(request.getRequestURI(), "/")[0] + req.getMethod();

		StringBuilder sb = new StringBuilder();
		HttpService service = ServiceUtil.getService(HttpServiceManager.class).getHttpService(deployPath);

		if (service.getRequstClass() != null)
		{
			sb.append("<h4>Request    :</h4>").append(service.getRequstClass().getCanonicalName()).append("<br>");
		}

		sb.append("<h4>RequestURL :").append("<input type='text' id='boulevard_Req_Url' readonly='readonly' value=").append(deployPath).append("/></h4>");
		sb.append("<form>");

		if (service.getRequstClass() != null)
		{
			sb.append("<table>");

			for (Field f : service.getRequstClass().getDeclaredFields())
			{
				sb.append("<tr><td>").append(f.getName()).append("</td>");
				sb.append("<td><input type='text' id='").append(f.getName()).append("'/></td></tr>");
			}
			sb.append("</table>");
		}

		sb.append("<td><input type='submit' value='Submit' onClick='showHint();return false'/>");

		sb.append("</form>");

		sb.append("<h4>Response:</h4>").append(service.getResponseClass().getCanonicalName()).append("<br>");

		sb.append("<p><h4>ResponseData:</h4><span id='boulevard_responseData' boder=1></span></p>");

		return new ServiceResp(sb.toString());

	}

}
