

jQuery(document).ready(function($) {

	"use strict";
	var e = jQuery.cookie("JSESSIONID")
	

  	if (!e) {
     }
            event.preventDefault(); 
            var user = {
            event:"checklogin",

        
            };

            jQuery.ajax({
            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"../apis/",
            contentType: "application/json;charset=utf-8",
            //提交的数据
            data:JSON.stringify(user),
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数

            //成功返回之后调用的函数             
            success:function(data){
            }   ,
            //调用执行后调用的函数

            //调用出错执行的函数
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                 alert("校验失败错误");


  						
            }         
         });


});