package service;

import java.io.File;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.preventers.AppContextLeakPreventer;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import cn.iolove.luajava.LuaException;
import cn.iolove.luajava.LuaObject;
import debug.Log;
import lua.LuaHelper;
import lua.LuaService;

public class BootService {
	public   void init()
	{
		try {
			Registry.getInstance().pushServiceInLua();
			LuaHelper.loadScript("init.lua");
			LuaObject objs = (LuaHelper.excuteLuaFunction("boot",new Object[]{},1))[0];
			Log.i(objs.getString());
		
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			Log.e(e.getMessage());
			e.printStackTrace();
		}
		Server server=new Server(8083);
		server.setAttribute("org.eclipse.jetty.server.webapp.WebInfIncludeJarPattern", "SCAN-NO-JARS");
		QueuedThreadPool qp = new QueuedThreadPool();
		SelectChannelConnector conn = new SelectChannelConnector();
		conn.setUseDirectBuffers(false);
		server.addConnector(conn);


		qp.setMaxThreads(180);
		qp.setMinThreads(10);
		qp.setDetailedDump(false);
		server.setThreadPool(qp);
		WebAppContext webapp =new WebAppContext();

		webapp.setDefaultsDescriptor("./web/WEB-INF/web.xml");
		webapp.setResourceBase("./web/");
		webapp.setContextPath("/");
	
		Log.i(webapp.getResourceBase());
		

		

		
		server.setHandler(webapp);
		
//		
//		HandlerCollection handlers = new HandlerCollection();
//		ContextHandlerCollection contexts = new ContextHandlerCollection();
//		RequestLogHandler requestLogHandler = new RequestLogHandler();
//		handlers.setHandlers(new Handler[]{contexts,webapp,requestLogHandler});
//		server.setHandler(handlers);
//		  
//		NCSARequestLog requestLog = new NCSARequestLog("./jetty-yyyy_mm_dd.request.log");
//		requestLog.setRetainDays(90);
//		requestLog.setAppend(true);
//		requestLog.setExtended(false);
//		requestLog.setLogTimeZone("GMT");
//		requestLogHandler.setRequestLog(requestLog);

		try {
			server.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Log.e(e1.getMessage());
			e1.printStackTrace();
		}


	}

}
