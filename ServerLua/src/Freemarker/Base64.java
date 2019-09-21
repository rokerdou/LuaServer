package Freemarker;

import java.io.ByteArrayInputStream;
import java.util.List;

import freemarker.core.SimpleCharStream;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import tool.Utils;

public class Base64 implements TemplateMethodModel {
private static Base64 obj=new Base64();
	@Override
	public Object exec(List arg0) throws TemplateModelException {
		// TODO Auto-generated method stub
        if (arg0.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
       }
        byte[] text=Utils.decode((String) arg0.get(0));
       return  new String(text);
	}
	public static Base64 getObj()
	{
		return obj;
	}

}
