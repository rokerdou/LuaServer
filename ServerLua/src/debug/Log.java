package debug;

import org.apache.log4j.*;

public class Log
{
    private static Logger logger = Logger.getLogger(Log.class);
    private static Log log=new Log();
    static{
    	PropertyConfigurator.configure(System.getProperty("user.dir")+"/log4j.properties");

    	logger = Logger.getLogger(Log.class);
    	System.out.println(System.getProperty("user.dir"));
    	System.getProperty("user.dir"); 
    	
    	
    
    }

	public static String getInfo(){
		String location="";
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace(); 
		location = "ClassName:"+stacks[3].getClassName() + " Method:" + stacks[3].getMethodName()
		+ "() File:" + stacks[3].getFileName() + "LineNum:"
		+ stacks[3].getLineNumber() + "\nmsg:";
		return location;
		
	}
	public static String getLevel()
	{
		return logger.getEffectiveLevel().toString();
	}

	public static void i(String msg)
	{

			logger.info(Log.getInfo()+" "+msg);
	
	}
	public static void d(String msg)
	{

			logger.debug(Log.getInfo()+" "+msg);		

	}
	public static void e(String msg)
	{

			logger.error(Log.getInfo()+" "+msg);		


	}
	public static  Log getInstance()
	{
		return log;
	}

}
