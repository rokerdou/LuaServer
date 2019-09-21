


    function checklogin()
    {

        if (!e) {
             location.href = "../index.html"
         }
                event.preventDefault(); 

                        jQuery.ajax({
                //提交数据的类型 POST GET
                type:"GET",
                //提交的网址
                url:"/apis/login",
                contentType: "application/json;charset=utf-8",
                //提交的数据
                data:"",
                //返回数据的格式
                datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
                //在请求之前调用的函数

                //成功返回之后调用的函数             
                success:function(data){
                }   ,
                //调用执行后调用的函数

                //调用出错执行的函数
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("错误"+XMLHttpRequest)


                            location.href = "/index.html"
                }         
             });
    }
    function deletes(ids)
    {
            alert(ids);
            var user = {
                event:"DeleteArticle",
                id: ids,
            
                };

            jQuery.ajax({
            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"/apis/",
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



            }         
         });    
    }
        function deleteAccount(ids)
    {
            alert(ids);
            var user = {
                event:"DeleteUser",
                id: ids,
            
                };

            jQuery.ajax({
            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"/apis/",
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



            }         
         });    
    }
        function GetLastArticle()
    {
            var user = {
                event:"GetLastArticle",
            
                };
            var num;

            jQuery.ajax({
             async: false,

            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"/apis/",
            contentType: "application/json;charset=utf-8",
            //提交的数据
            data:JSON.stringify(user),
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数

            //成功返回之后调用的函数             
            success:function(data){
                num=data;
            }   ,
            //调用执行后调用的函数

            //调用出错执行的函数
            error: function(XMLHttpRequest, textStatus, errorThrown) {



            }         
         });    
            return num;

    }
    function reg(usernames,passwords,successcallback,errorCallback)
    {
            var user = {
            event:"addAccount",
            username: usernames,
            password: passwords,
        
            };

            jQuery.ajax({
            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"apis/login",
            contentType: "application/json;charset=utf-8",
            //提交的数据
            data:JSON.stringify(user),
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数

            //成功返回之后调用的函数             
            success:function(data){
                successcallback(data);
            }   ,
            //调用执行后调用的函数

            //调用出错执行的函数
            error: function(XMLHttpRequest, textStatus, errorThrown) {

                    errorCallback(XMLHttpRequest,textStatus,errorThrown);


            }         
         });
    }
     function addArticle(usernames,titles,urls,successcallback,errorCallback)
    {
            var user = {
            event:"addArticle",
            name: usernames,
            title: titles,
            url:urls
        
            };

            jQuery.ajax({
            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"/apis/login",
            contentType: "application/json;charset=utf-8",
            //提交的数据
            data:JSON.stringify(user),
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数

            //成功返回之后调用的函数             
            success:function(data){
                successcallback(data);
            }   ,
            //调用执行后调用的函数

            //调用出错执行的函数
            error: function(XMLHttpRequest, textStatus, errorThrown) {

                    errorCallback(XMLHttpRequest,textStatus,errorThrown);


            }         
         });
    }   
    function login(usernames,passwords,successcallback,errorCallback)
    {
            var user = {
            event:"login",
            username: usernames,
            password: passwords,
        
            };

            jQuery.ajax({
            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"/apis/login",
            contentType: "application/json;charset=utf-8",
            //提交的数据
            data:JSON.stringify(user),
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数

            //成功返回之后调用的函数             
            success:function(data){
                successcallback(data);
            }   ,
            //调用执行后调用的函数

            //调用出错执行的函数
            error: function(XMLHttpRequest, textStatus, errorThrown) {

                    errorCallback(XMLHttpRequest,textStatus,errorThrown);


            }         
         });
    }
    function listAllUser(indexs,totals,lengths,successcallback,errorCallback)
    {
            var user = {
            event:"getAllUser",
            index: indexs,
            total: totals,
            length:lengths,
        
            };

            jQuery.ajax({
            //提交数据的类型 POST GET
            type:"post",
            //提交的网址
            url:"apis/",
            contentType: "application/json;charset=utf-8",
            //提交的数据
            data:JSON.stringify(user),
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数

            //成功返回之后调用的函数             
            success:function(data){
                successcallback(data);
            }   ,
            //调用执行后调用的函数

            //调用出错执行的函数
            error: function(XMLHttpRequest, textStatus, errorThrown) {

                    errorCallback(XMLHttpRequest,textStatus,errorThrown);


            }         
         });
    }


