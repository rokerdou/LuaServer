package database;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import debug.Log;

import tool.Utils;

public class SessionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    private SessionFactory(){

    }

   synchronized public static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory==null){
            String resources=Utils.getDir()+"config//dbconfig.xml";
            Log.d("DBCONFIG "+resources );
            InputStream inputStream=null;
            try {
                inputStream= new FileInputStream(resources);

            }catch (Exception e){
                e.printStackTrace();
                Log.e(e.getMessage());
            }
           
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);

        }
        return sqlSessionFactory;

    }
   
}