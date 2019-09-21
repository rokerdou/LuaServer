package lua;






import java.io.IOException;

import cn.iolove.luajava.LuaException;
import cn.iolove.luajava.LuaObject;
import cn.iolove.luajava.LuaState;
import debug.Log;
import tool.Utils;



public class LuaHelper {
	private static LuaState l=LuaService.getLuaState();

	public  synchronized static LuaObject[] excuteLuaFunction(String mehodName,Object[] args,int returnnumber) throws LuaException
	{
		LuaState ls = l;

		
		int top = ls.getTop();
	try {
			
			ls.getField(LuaState.LUA_GLOBALSINDEX, mehodName);
			int i=0;
			for(i=0;i<args.length;i++)
			{
	
				ls.pushObjectValue(args[i]);
			}
			int status = ls.pcall(args.length, returnnumber, 0);
			if(status!=0)
			{
				Log.e(ls.dumpStack()+ls.toString(-1));
				Log.e(" 执行LUA函数失败");
				//RuntimeContext.showLuaError();
				
			}
			LuaObject[] returns = new  LuaObject[returnnumber];
			Log.d(ls.dumpStack());
			for(i=-1;i>=(0-returnnumber);i--)
			{
				
				returns[0-i-1]=ls.getLuaObject(i);
			}
			
			ls.setTop(top);
			ls.checkStack(20);
			
			return returns; 
			
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(ls.dumpStack()+ls.toString(-1));
			throw new LuaException(e);
			
		}finally
		{
			ls.setTop(top);
			
		}
		
	}
	public static void setGlobalObject(String name,Object obj)
	
	{
		//mLuaState.pushObjectValue(obj);
		
		l.pushJavaObject(obj);
		

		l.setGlobal(name);
	}
	public static void removeGlobalObject(String name)
	{
		l.pushNil();
		//mLuaState.pushJavaObject(null);
		

		l.setGlobal(name);
		Thread th=Thread.currentThread();		
		
	}
	

	public static LuaState loadScript(String pagename) throws LuaException
	{
		
		int error;
		try {
			Log.d(Utils.getDir()+"lua/"+pagename);
			error = l.LdoString(Utils.readFile(Utils.getDir()+"lua/"+pagename));		
			if(error!=0)
			{
				Log.e(l.toString(-1));

			}
			else{
				Log.d( "成功载入LUA脚本");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Log.e(e.getMessage());
		}

		

		
		return l;
		
	}

}
