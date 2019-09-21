package model;

import java.util.Date;

public class Article {
	public long id;
	public Date createDate;
	public String name;
	public String title;
	public String url;
	public String allinfo;
	public String md;
	public String mdhtml;
	public Article(String names,String titles,String urls,java.util.Date date ) {
		name=names;
		url=urls;
		createDate=date;
		title=titles;
		
		// TODO Auto-generated constructor stub
	}
	public Article(long ids,String names,String urls,java.sql.Date date,String titles,String m,String mh) {
		id=ids;
		name=names;
		url=urls;
		createDate=date;
		title=titles;
		allinfo=ids+"@"+name.toString()+"@"+date+"@"+title+"@"+url;
		md=m;
		mdhtml=mh;
		
		// TODO Auto-generated constructor stub
	}
}
