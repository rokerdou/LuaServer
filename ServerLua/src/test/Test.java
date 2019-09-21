package test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import com.google.gson.JsonParser;

import cn.iolove.luajava.LuaException;
import cn.iolove.luajava.LuaObject;
import cn.iolove.luajava.LuaState;
import cn.iolove.luajava.LuaStateFactory;
import database.ArticleService;
import debug.Log;
import lua.LuaHelper;
import lua.LuaService;
import service.BootService;
import thread.Method;
import thread.ThreadFactory;
import tool.Utils;

public class Test {

	public static void main(String[] args) throws LuaException {
		JsonParser p = new JsonParser();
	
		BootService boot =new BootService();
		boot.init();
		System.out.print(ArticleService.getInstance().getArticles(1, 5));
		System.out.print(ArticleService.getInstance().getArticles(5, 5));

		//LuaHelper.loadScript( "init.lua");

	//	LuaObject objs = (LuaHelper.excuteLuaFunction("boot",new Object[]{},1))[0];
		//

	}

}
