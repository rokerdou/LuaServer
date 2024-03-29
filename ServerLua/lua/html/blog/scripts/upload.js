      var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

    function utf16to8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }

    function utf8to16(str) {
        var out, i, len, c;
        var char2, char3;
        out = "";
        len = str.length;
        i = 0;
        while (i < len) {
            c = str.charCodeAt(i++);
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    // 0xxxxxxx
                    out += str.charAt(i - 1);
                    break;
                case 12:
                case 13:
                    // 110x xxxx 10xx xxxx
                    char2 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                case 14:
                    // 1110 xxxx 10xx xxxx 10xx xxxx
                    char2 = str.charCodeAt(i++);
                    char3 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
                    break;
            }
        }
        return out;
    }

    /*
     * Interfaces:
     * b64 = base64encode(data);
     * data = base64decode(b64);
     */

    var safe64 = function(base64) {
        base64 = base64.replace(/\+/g, "-");
        base64 = base64.replace(/\//g, "_");
        return base64;
    };
    function base64encode(str) {
        var out, i, len;
        var c1, c2, c3;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            c1 = str.charCodeAt(i++) & 0xff;
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt((c1 & 0x3) << 4);
                out += "==";
                break;
            }
            c2 = str.charCodeAt(i++);
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                out += base64EncodeChars.charAt((c2 & 0xF) << 2);
                out += "=";
                break;
            }
            c3 = str.charCodeAt(i++);
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
            out += base64EncodeChars.charAt(c3 & 0x3F);
        }
        return out;
    }

    function base64decode(str) {
        var c1, c2, c3, c4;
        var i, len, out;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            /* c1 */
            do {
                c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (i < len && c1 == -1);
            if (c1 == -1) break;
            /* c2 */
            do {
                c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (i < len && c2 == -1);
            if (c2 == -1) break;
            out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
            /* c3 */
            do {
                c3 = str.charCodeAt(i++) & 0xff;
                if (c3 == 61) return out;
                c3 = base64DecodeChars[c3];
            } while (i < len && c3 == -1);
            if (c3 == -1) break;
            out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
            /* c4 */
            do {
                c4 = str.charCodeAt(i++) & 0xff;
                if (c4 == 61) return out;
                c4 = base64DecodeChars[c4];
            } while (i < len && c4 == -1);
            if (c4 == -1) break;
            out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
        }
        return out;
    }    





   function genUpToken (accessKey, secretKey, putPolicy) {

        //SETP 2
        var put_policy = JSON.stringify(putPolicy);
        console && console.log("put_policy = ", put_policy);

        //SETP 3
        var encoded = base64encode(utf16to8(put_policy));
        console && console.log("encoded = ", encoded);

        //SETP 4
        var hash = CryptoJS.HmacSHA1(encoded, secretKey);
        var encoded_signed = hash.toString(CryptoJS.enc.Base64);
        console && console.log("encoded_signed=", encoded_signed)

        //SETP 5
        var upload_token = accessKey + ":" + safe64(encoded_signed) + ":" + encoded;
        console && console.log("upload_token=", upload_token)

        return upload_token;
    }
            function GetSK()
    {
            var user = {
                event:"GetSK",
            
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
   function uploadfile(file)
    {

        ak="d_v3Jo_rp4MxoYgXZHf-ArLAS3XQeTVZENmCsVJ_";
        sk=GetSK();
        sk=sk.substring(0,sk.length - 1);
        domain="http://static.agoboy.com";

        policy={
        scope:                "iolove:"+file.name,
        deadline:Math.round(new Date().getTime()/1000)+3600

        };
    var finishedAttr = [];
    // eslint-disable-next-line
    var compareChunks = [];
    var observable;
      var next = function(response) 
      {


        var chunks = response.chunks||[];
        var total = response.total;
        // 这里对每个chunk更新进度，并记录已经更新好的避免重复更新，同时对未开始更新的跳过
        for (var i = 0; i < chunks.length; i++) {
          if (chunks[i].percent === 0 || finishedAttr[i]){
            continue;
          }
          if (compareChunks[i].percent === chunks[i].percent){
            continue;
          }
          if (chunks[i].percent === 100){
            finishedAttr[i] = true;
          }
          $('#jincheng').css(
              "width",
              total.percent + "%"
            );
        }

       
        compareChunks = chunks;
      };
      var error=function(response) {};
 var complete=function(response) {
    alert("上传完成");

 };

         var token =  genUpToken(ak,sk,policy);
        var config = {
      useCdnDomain: false,
      disableStatisticsReport: false,
      retryCount: 6,
      region: qiniu.region.z0
    };
    var putExtra = {
      fname: "",
      params: {},
      mimeType: null
    };
    var key = file.name;

    putExtra.params["x:name"] = key.split(".")[0];
    observable = qiniu.upload(file, key, token, putExtra, config);
    var subscription = observable.subscribe(next,complete,error);// 上传开始



    }
