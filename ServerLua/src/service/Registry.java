package service;

import java.util.HashMap;
import java.util.Map;

import database.ArticleService;
import debug.Log;
import lua.LuaHelper;
import lua.LuaService;

public class Registry {

private static Registry obj = new Registry();
private Registry(){}
public static Registry getInstance(){return obj;}
private static  Map ServiceMapping = new HashMap();
public RegistryAdapter a=null;
public  interface  RegistryAdapter
{
	public void addRegister(Registry r);
}
public void  bindAdapter(RegistryAdapter adp)
{
	a=adp;
}

public  void registerInlua(String qName)
{
	Object obj= ServiceMapping.get(qName);
	LuaHelper.setGlobalObject( qName, obj);
}
public  Object register(String qName)
{
	Object obj= ServiceMapping.get(qName);
	return obj;

}
public  void register(String qName,Object obj)
{
	ServiceMapping.put(qName, obj);
}

public boolean isNull(String qname)
{
	return !ServiceMapping.containsKey(qname);
}
public  void pushServiceInLua()
{
	register("Registry",getInstance());
	registerInlua("Registry");
	register("Log",Log.getInstance());
	registerInlua("Log");
	register("ArticleService",ArticleService.getInstance());
	registerInlua("ArticleService");
	if(a!=null)
	a.addRegister(obj);
}


}
