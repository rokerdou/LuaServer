package model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;



public interface ArticleMapper {
    @Select("select * from article where id=#{id}")
	 Article findAccoutById(int id);
    @Select("select * from article where name=#{name}")
	 int findExistAccoutByUsername(String username);
    @Select("select * from article where name=#{name}")
    Article findAccoutByUsername(String username);
	   //查询所有数据
    @Select("select count(*) from article")
	   int findAllArticle();
    @Select("select * from article  where id <=(select id from article order by id limit #{index},1) order by id desc limit #{total}")
	   List<Article>  listAllArticle(@Param(value = "index")long index,@Param(value = "total")long total);
	   //添加User
    @Insert("insert into article(name,title,url,createtime) values(#{name},#{title},#{url},#{createDate, jdbcType=DATE})")
	int addArticle(Article u);

	   //根据id删除数据
	    @Delete("delete from article where id=#{id}")
	int deleteArticleById(int id);

	   //修改User
	    @Update("update article set password=#{password} where id=#{id}")
	   int updateArticle(Article user);
	   @Select("select * from article where id=#{id}")
	   Article getUrl(int id);
	    @Select("select id from article order by id desc limit 1")
	   int getLastArticle();
}
