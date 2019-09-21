package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Freemarker.Base64;
import debug.Log;
import freemarker.template.Configuration;
import freemarker.template.Template;
import service.TemplateContainer;
import tool.Utils;

public class FreeMarkServlet extends HttpServlet {
	private TemplateContainer tc;

	@Override
		public void init() throws ServletException {
			// TODO Auto-generated method stub
			super.init();
			tc=new TemplateContainer(getServletContext());
			

	       // cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(0, 10));
		}
@Override
protected void service(HttpServletRequest arg0, HttpServletResponse response) throws ServletException, IOException {
	// 得到模板
	response.setCharacterEncoding("UTF-8");
	response.setHeader("content-type","text/html;charset=UTF-8");
	try {
	String jsondata=Utils.doInLua(arg0, response);


	 Map<String, Object> data = new HashMap<String, Object>();
     data.put("data", jsondata);
     data.put("base64", Base64.getObj());
	

		// 将模板和数据结合，并返回浏览器
		
		long startTime = System.currentTimeMillis(); 

		Template template = (Template) tc.getTemplate(arg0.getRequestURI());
		template.process(data, response.getWriter());

		long endTime = System.currentTimeMillis(); 
		Log.i("FreemakrTime "+(endTime-startTime));
		//response.getWriter().close();



	} catch (Exception e) {
		e.printStackTrace();
		PrintWriter out = response.getWriter();
		StringBuilder str= new StringBuilder();

		str.append("访问失败    "+e.getMessage());
		out.print(str);
		out.flush();
	}
	//super.service(arg0, response);
}
}
