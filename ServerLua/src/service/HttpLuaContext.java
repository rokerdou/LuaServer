package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import debug.Log;

public class HttpLuaContext {
	private HttpServletRequest request;
	private HttpServletResponse response;
	public HttpLuaContext(HttpServletRequest req,HttpServletResponse re) {
		request=req;
		response=re;
		response=null;
		// TODO Auto-generated constructor stub
	}
	public String getSession(String key,String value,int interval)
	{
        HttpSession session = request.getSession();
        session.setAttribute(key,value); // 登录成功，向session存入一个登录标记
        session.setMaxInactiveInterval(interval);
        return session.getId();
	}
	
	public boolean checkSession()
	{
		HttpSession session = request.getSession(false); 
		
		if (session != null) {  
			Log.d(session.getId());
			//System.out.println(session.getId());
		   // resp.setStatus(HttpServletResponse.SC_OK);
		    return true;
		}  else
		{
			
			return false;
		}

	}
	public void dropSession()
	{
		HttpSession session = request.getSession();	
		session.invalidate();
	}

}
