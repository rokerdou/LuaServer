package service;

import java.io.IOException;
import java.util.HashMap;

import debug.Log;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateNotFoundException;

public class TemplateContainer {
	private static HashMap hm=new HashMap();
	private Configuration cfg;
	public TemplateContainer(Object obj) {
		// TODO Auto-generated constructor stub
	    cfg = new Configuration(Configuration.VERSION_2_3_25);
        cfg.setServletContextForTemplateLoading(obj, "/WEB-INF");
        cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
        cfg.setDefaultEncoding("UTF-8");
	}
	public void putTemplate(String key) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException
	{
		if(hm.containsKey(key))
		{
			return;
		}else
		{
			Log.d("存入模板缓存"+key);
			hm.put(key, cfg.getTemplate(key));
			return;
		}
	}
	public Object getTemplate(String key) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException
	{
		



				return cfg.getTemplate(key);

		}

		
		


}
