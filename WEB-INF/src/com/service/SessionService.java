package com.service;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.jsits.commons.util.ServiceUtil;

public class SessionService  implements HttpSessionListener 
{
	private static Logger logger = Logger.getLogger(SessionService.class);
	
	private ConcurrentHashMap<String, HttpSession> sessionMap = null;

	public SessionService()
	{
		sessionMap = new ConcurrentHashMap<String, HttpSession>(100);
		logger.debug("SessionService inited!");
		
		ServiceUtil.registService(SessionService.class, this);
	}

	/**
	 * @param userId
	 * @return
	 */
	public HttpSession get(String userId)
	{
		return sessionMap.get(userId);
	}

	/**
	 * ����Session����ʱ������seesion�Ѿ�������������������û���������������¼
	 * 
	 * @param userId
	 * @param session
	 * @return
	 */
	public boolean save(String userId, HttpSession session)
	{
		sessionMap.put(userId, session);
		return true;
	}

	@Override
	public void sessionCreated(HttpSessionEvent se)
	{
		HttpSession session=se.getSession();
		
		save(session.getId(),session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se)
	{
		HttpSession session=se.getSession();
		
		sessionMap.remove(session.getId());
	}

}
