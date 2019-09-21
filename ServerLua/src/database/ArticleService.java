package database;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import debug.Log;
import model.Article;
import model.ArticleMapper;
import tool.Utils;






public class ArticleService {
	private static ArticleService db=new ArticleService();
	private ArticleService() {
		// TODO Auto-generated constructor stub
	}
	public static ArticleService getInstance()
	{
		return db;
	}
	public static boolean isExist(Class cls)
	{
	
     
		return false;
		
	}
	public static int getArticlesTotal()
	{
		SqlSessionFactory factory=SessionFactory.getSqlSessionFactory();
	    SqlSession sql=factory.openSession(true);
		try
		{
	    ArticleMapper mapper=sql.getMapper(ArticleMapper.class);
	    int len=mapper.findAllArticle();
	    return len;
		}catch  (Exception e)
		{
			e.printStackTrace();
		}finally{sql.close();}

		return -1;
	}
	public static String getArticles(int start,int total)
	{
		SqlSessionFactory factory=SessionFactory.getSqlSessionFactory();
	    SqlSession sql=factory.openSession(true);
		try
		{
        ArticleMapper mapper=sql.getMapper(ArticleMapper.class);
        int pages=start/total;
        int len=mapper.findAllArticle();
        int index=len-(pages)*total-1;
	       List<Article> accounts =mapper.listAllArticle(index,total);
	       String data;
	       if(accounts!=null) 
	       {
	       	data=Utils.gson.toJson(accounts);
	       	data="{"+"\"total\":"+len+",\"datas\":"+data+"}";
	       	Log.d(data);

	       	return data;
	       }
		}catch  (Exception e)
		{
			e.printStackTrace();
		}finally{sql.close();}

        return "{}";
	}
	public static boolean addItem(String name,Class cls)
	{
		SqlSessionFactory factory=SessionFactory.getSqlSessionFactory();
	    SqlSession session=factory.openSession(true);
		try
		{
//			 mapper=session.getMapper(cls);
//	        if(mapper.addArticle(new ArticleModel(username, title,url, new Date()))==1)
	        {
	        	session.commit();
	        	return true;
	        }
			
		}catch  (Exception e)
		{
			e.printStackTrace();
		}
        finally{session.close();}

        session.rollback();
        return false;
	}

}
