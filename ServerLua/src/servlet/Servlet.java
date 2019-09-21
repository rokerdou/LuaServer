package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.iolove.luajava.LuaException;
import cn.iolove.luajava.LuaObject;
import debug.Log;
import lua.LuaHelper;
import service.Registry;
import tool.Utils;

public class Servlet extends HttpServlet  {



@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
	arg1.setCharacterEncoding("UTF-8");
	arg1.setHeader("content-type","text/html;charset=UTF-8");
	
	
    try {
		Utils.printRequest(arg0,arg1,Utils.doInLua(arg0, arg1));
	} catch (LuaException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		PrintWriter out = arg1.getWriter();
		StringBuilder str= new StringBuilder();

		str.append("访问失败    "+e.getMessage());
		out.print(str);
		out.flush();
	}
    Log.d("Servlet执行了");

	}

}
