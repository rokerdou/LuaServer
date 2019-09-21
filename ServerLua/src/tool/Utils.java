package tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.core.util.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.iolove.luajava.LuaException;
import cn.iolove.luajava.LuaObject;
import debug.Log;
import lua.LuaHelper;
import service.HttpLuaContext;
import service.Registry;


public class Utils {
	public static Gson   gson = new Gson();
	public static String toJson(Object obj) {
		

		return gson.toJson(obj);
	}
	public static String doInLua(HttpServletRequest request,HttpServletResponse response) throws LuaException
	{

		if(Log.getLevel().contains("DEBUG"))
		{
			try {
				LuaHelper.loadScript("init.lua");
			} catch (LuaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(e.getMessage());
			}

		}
		long startTime = System.currentTimeMillis(); 
		

		
	    String msg=new String();

	    //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
	    String LuaMethod=Utils.getLuaMethod(request);
	    HttpLuaContext hlc=new HttpLuaContext(request,response);

	    if(!Registry.getInstance().isNull(LuaMethod))
	    {
	    	Log.d("准备执行Lua函数");


	    	LuaObject[] obj = LuaHelper.excuteLuaFunction(LuaMethod,new Object[]{Utils.getRequestParam(request),hlc},2);
	    	msg=obj[1].getString();
	    	
	    	Log.d("LuaObject[]长度"+obj.length+" "+obj[0].toString()+" "+obj[1].toString());
	    	
	    	response.setStatus(Integer.parseInt(obj[0].getString()));
	    	
	    }
	    else
	    {

	    	LuaObject[] obj = (LuaHelper.excuteLuaFunction("InvalidRequest",new Object[]{Utils.getRequestParam(request),hlc},2));
	    	msg=obj[1].getString();
	    	response.setStatus(Integer.parseInt(obj[0].getString()));

	    }
	    
		long endTime = System.currentTimeMillis(); 
		Log.i("LuaFuction exec Time LuaMethod "+LuaMethod+" "+(endTime-startTime));
		Log.d("lua return String : " +msg);
		return msg;
	}
	public static String getRequestParam(HttpServletRequest request)
	{
		if(request.getMethod().contains("GET"))
		{
		    String queryString = request.getQueryString();//得到请求的URL地址中附带的参数

			return Utils.getJsonStrByQueryUrl(queryString);
		}
		else
		{
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			try {
				br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
				String line = null;
				
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				br=null;
			}
			return sb.toString();
		}
		
	}
    public static byte[] decode(String data) {  
    	return Base64.decodeBase64(data.getBytes());
     
    }  
  
    /** 
     * 二进制数据编码为BASE64字符串 
     * 
     * @param bytes 
     * @return 
     * @throws Exception 
     */  
    public static String encode(String data) {  
        return new String(Base64.encodeBase64(data.getBytes()));  
    }  
	public static void printRequest(HttpServletRequest request,HttpServletResponse response,String msg) 
	{

	    String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
	    String requestUri = request.getRequestURI();//得到请求的资源
	    String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
	    String remoteAddr = request.getRemoteAddr();//得到来访者的IP地址
	    String remoteHost = request.getRemoteHost();
	    int remotePort = request.getRemotePort();
	    String remoteUser = request.getRemoteUser();
	    String method = request.getMethod();//得到请求URL地址时使用的方法
	    String pathInfo = request.getPathInfo();
	    String localAddr = request.getLocalAddr();//获取WEB服务器的IP地址
	    String localName = request.getLocalName();//获取WEB服务器的主机名
	    response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
		
	    response.setHeader("content-type", "text/html;charset=UTF-8");
	    PrintWriter out=null;
		try {
			out = response.getWriter();
			StringBuilder str= new StringBuilder();
				
			str.append("\n获取到的客户机信息如下：");
			str.append("\n<hr/>");
			str.append("\n请求的URL地址："+requestUrl);

			str.append("\n请求的资源："+requestUri);

			str.append("\n请求的URL地址中附带的参数："+queryString);

			str.append("\n来访者的IP地址："+remoteAddr);

			str.append("\n来访者的主机名："+remoteHost);

			str.append("\n使用的端口号："+remotePort);

			str.append("\nremoteUser："+remoteUser);

			str.append("\n请求使用的方法："+method);

			str.append("\npathInfo："+pathInfo);

			str.append("\nlocalAddr："+localAddr);

			str.append("\nlocalName："+localName);
			Log.d(str.toString());
			Log.d("\nResponse: "+msg);

			out.println(msg);
		    
		    out.flush();
		    
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			out.close();
			
		}

	}
	public static String getLuaMethod(HttpServletRequest request)
	{
		String method=request.getMethod()+request.getRequestURI().replaceAll("/", "_");
		Log.d("Lua函数名"+method+" ");
	
		if(method.charAt(method.length()-1)=='_')
		{
			method=method.substring(0, method.length()-1);
		}
		Log.d("Lua函数名"+method.replace('.', '_')+" ");
		
		return method.replace('.', '_');
	}
    public static String getJsonStrByQueryUrl(String paramStr){
    	if(!(paramStr==null))
    	{
	        //String paramStr = "a=a1&b=b1&c=c1";
	        String[] params = paramStr.split("&");
	        JsonObject obj = new JsonObject();
	        for (int i = 0; i < params.length; i++) {
	            String[] param = params[i].split("=");
	            if (param.length >= 2) {
	                String key = param[0];
	                String value = param[1];
	                for (int j = 2; j < param.length; j++) {
	                    value += "=" + param[j];
	                }
	                obj.addProperty(key, value);
	         
	            }
	        }
	        return obj.toString();
    	}
    	return "{}";
    }
    public static void jsonTree(JsonElement e)
    {
    
        if (e.isJsonNull())
        {
            System.out.println(e.toString());
            return;
        }
 
        if (e.isJsonPrimitive())
        {
        	
            System.out.println(e.toString());
            
            return;
        }
 
        if (e.isJsonArray())
        {
            JsonArray ja = e.getAsJsonArray();
            if (null != ja)
            {
                for (JsonElement ae : ja)
                {
                    jsonTree(ae);
                }
            }
            return;
        }
 
        if (e.isJsonObject())
        {
            Set<Entry<String, JsonElement>> es = e.getAsJsonObject().entrySet();
            for (Entry<String, JsonElement> en : es)
            {
            	
            	System.out.println(en.getKey());
            	
                jsonTree(en.getValue());
               
               
            }
        }
    }


	/**
	 * @param args
	 */
	public static String getDir()
	{
		return System.getProperty("user.dir")+"/";
	}
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return sb.toString();
    }
}
