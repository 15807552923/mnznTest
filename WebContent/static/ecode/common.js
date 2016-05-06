    /** 
     * 时间对象的格式化; 
     */  
    Date.prototype.format = function(format) {  
        /* 
         * eg:format="yyyy-MM-dd hh:mm:ss"; 
         */  
        var o = {  
            "M+" : this.getMonth() + 1, // month  
            "d+" : this.getDate(), // day  
            "h+" : this.getHours(), // hour  
            "m+" : this.getMinutes(), // minute  
            "s+" : this.getSeconds(), // second  
            "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
            "S" : this.getMilliseconds()  
            // millisecond  
        }  
      
        if (/(y+)/.test(format)) {  
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                            - RegExp.$1.length));  
        }  
      
        for (var k in o) {  
            if (new RegExp("(" + k + ")").test(format)) {  
                format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                                ? o[k]  
                                : ("00" + o[k]).substr(("" + o[k]).length));  
            }  
        }  
        return format;  
    }  
    
    
    function isIE() { //ie?
        if (!!window.ActiveXObject || "ActiveXObject" in window)
            return true;
        else
            return false;
    }
    
    function getXlsFromTbl(inTblId, inWindow) {
   	
        try {
            var allStr = "";
            var curStr = "";
            var fileName = getExcelFileName();
            //                var cc = document.all;
            //                var ccc = typeof (document.all)
            //                alert(cc)
            //                alert(ccc)
            if (inTblId != null && inTblId != "" && inTblId != "null") {
                var hh = isIE();
                if (hh == true) //IE浏览器
                	{
                    curStr = getTblData(inTblId, inWindow);
                    if (curStr != null) {
                        allStr += curStr;
                    }
                    else {
                        alert("你要导出的表不存在！");
                        return;
                    }
                    doFileExport(fileName, allStr);
                }else {
                    curStr = getTblData1(inTblId, inWindow);
                    if (curStr != null) {
                        allStr += curStr;
                    }
                    else {
                        alert("你要导出的表不存在！");
                        return;
                    }
                    var uri = 'data:text/xls;charset=utf-8,\ufeff' + encodeURIComponent(allStr);
                    //创建a标签模拟点击下载
                    var downloadLink = document.createElement("a");
                    downloadLink.href = uri;
                    downloadLink.download = fileName;
                    document.body.appendChild(downloadLink);
                    downloadLink.click();
                    document.body.removeChild(downloadLink);
                }
            }
        }
        catch (e) {
            alert("导出发生异常:" + e.name + "->" + e.description + "!");
        }
   	 }

    function getTblData(inTbl, inWindow) {
   	 //alert("导出。。。。。。。2");
       var rows = 0;
       var tblDocument = document;
       if (!!inWindow && inWindow != "") {

           if (!document.all(inWindow)) {
               return null;
           }
           else {
               tblDocument = eval(inWindow).document;
           }
       }

       var curTbl = tblDocument.getElementById(inTbl);
       var outStr = "";
          
       if (curTbl != null) {
          
       	for (var j = 0; j <curTbl.rows.length; j++) {
          	for (var i = 0; i < curTbl.rows[j].cells.length; i++) {
          				
                   if (i == 0 && rows > 0) {
                       outStr += "\t";//\t
                       rows -= 1;
                   }
                   var s = curTbl.rows[j+1].cells[i].innerText;
                   s = s.replace(",","");

                   outStr += s + "\t";//\t
                  
                   if (curTbl.rows[j].cells[i].colSpan > 1) {
                       for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
                           outStr += "\t";//\t
                       }
                   }
                   if (i == 0) {
                       if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
                           rows = curTbl.rows[j].cells[i].rowSpan - 1;
                       }
                   } 
               }//
          	 
               outStr += "\r\n";
           	
           }
           
       }

       else {
           outStr = null;
           alert(inTbl + "不存在 !");
       }
       return outStr;
   }


   function getTblData1(inTbl, inWindow) {
   	 //alert("导出。。。。。。。2");
       var rows = 0;
       var tblDocument = document;
       if (!!inWindow && inWindow != "") {

           if (!document.all(inWindow)) {
               return null;
           }
           else {
               tblDocument = eval(inWindow).document;
           }
       }

       var curTbl = tblDocument.getElementById(inTbl);
       var outStr = "";
          
       if (curTbl != null) {
          
       	for (var j = 0; j <curTbl.rows.length; j++) {
          		for (var i = 0; i < curTbl.rows[j].cells.length; i++) {
          		  
                   if (i == 0 && rows > 0) {
                       outStr += ",";//\t
                       rows -= 1;
                   }
                   var s = curTbl.rows[j].cells[i].innerText;
                   s = s.replace(",","");

                   outStr += s + ",";//\t
                  
                   if (curTbl.rows[j].cells[i].colSpan > 1) {
                       for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
                           outStr += ",";//\t
                       }
                   }
                   if (i == 0) {
                       if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
                           rows = curTbl.rows[j].cells[i].rowSpan - 1;
                       }
                   } 
               }//
          	 
               outStr += "\r\n";
           	
           }
           
       }

       else {
           outStr = null;
           alert(inTbl + "不存在 !");
       }
       return outStr;
   }


   function getExcelFileName() {
   	//alert("导出。。。。。。。4");
       var d = new Date();
       var curYear = d.getYear();
       var curMonth = "" + (d.getMonth() + 1);
       var curDate = "" + d.getDate();
       var curHour = "" + d.getHours();
       var curMinute = "" + d.getMinutes();
       var curSecond = "" + d.getSeconds();
       if (curMonth.length == 1) {
           curMonth = "0" + curMonth;
       }

       if (curDate.length == 1) {
           curDate = "0" + curDate;
       }

       if (curHour.length == 1) {
           curHour = "0" + curHour;
       }

       if (curMinute.length == 1) {
           curMinute = "0" + curMinute;
       }

       if (curSecond.length == 1) {
           curSecond = "0" + curSecond;
       }
       var fileName = "table" + "_" + curYear + curMonth + curDate + "_"
               + curHour + curMinute + curSecond + ".csv";
       return fileName;

   }

   function doFileExport(inName, inStr) { 
   	//alert("导出。。。。。。。5");
       var xlsWin = null;
       if (!!document.all("glbHideFrm")) {
           xlsWin = glbHideFrm;
       }
       else {
           var width = 6;
           var height = 4;
          var openPara = "left=" + (window.screen.width / 2 - width / 2)
                   + ",top=" + (window.screen.height / 2 - height / 2)
                   + ",scrollbars=no,width=" + width + ",height=" + height;
           xlsWin = window.open("", "_blank", openPara);
       }
       xlsWin.document.write(inStr);
       xlsWin.document.close();
       xlsWin.document.execCommand('Saveas', true, inName);
       xlsWin.close();

   	}
   /*fastjson format   */
   var FastJson = {
	isArray : function(a) {
		return "object" == typeof a
				&& "[object array]" == Object.prototype.toString.call(a)
						.toLowerCase()
	},
	isObject : function(a) {
		return "object" == typeof a
				&& "[object object]" == Object.prototype.toString.call(a)
						.toLowerCase()
	},
	format : function(a) {
		if (null == a)
			return null;
		"string" == typeof a && (a = eval("(" + a + ")"));
		return this._format(a, a, null, null, null)
	},
	_randomId : function() {
		return "randomId_" + parseInt(1E9 * Math.random())
	},
	_getJsonValue : function(a, c) {
		var d = this._randomId(), b;
		b = "" + ("function " + d + "(root){") + ("return root." + c + ";");
		b += "}";
		b += "";
		var e = document.createElement("script");
		e.id = d;
		e.text = b;
		document.body.appendChild(e);
		d = window[d](a);
		e.parentNode.removeChild(e);
		return d
	},
	_format : function(a, c, d, b, e) {
		d || (d = "");
		if (this.isObject(c)) {
			if (c.$ref) {
				var g = c.$ref;
				0 == g.indexOf("$.")
						&& (b[e] = this._getJsonValue(a, g.substring(2)));
				return
			}
			for ( var f in c)
				b = d, "" != b && (b += "."), g = c[f], b += f, this._format(a,
						g, b, c, f)
		} else if (this.isArray(c))
			for (f in c)
				b = d, g = c[f], b = b + "[" + f + "]", this._format(a, g, b,
						c, f);
		return a
	}
};
   
   
   function tableSize(str){
	   var _width = $(document.body).width();
	   if(_width < 800 ){
		   $(str).css("overflow","auto");
	   }
   }
   
   
   
   function validateMethodById(id,ctx,localId){
	   jQuery.validator.addMethod("mobile", function (value, element) {
		    var mobile = /^1[3|4|5|7|8]\d{9}$/;
			return this.optional(element) || (mobile.test(value));
		}, "手机格式不对");
		
		$("#"+id).validate({
			//success: function(label) {label.addClass("valid");},
		 	success: "valid",
			focusInvalid: false,
			focusCleanup: true,
			onkeyup: false, 
			errorElement:'span',
			 rules:{
				 mobile:{
					 required:true,
					 mobile:true,
					 remote:
	                 {
	                     url:ctx+"/user/check_mobile.mn",//后台处理程序
	                     type:"post",                        //数据发送方式
	                     dataType:"json", //接受数据格式 
	                     data:{mobile:function(){return $("#mobile").val();},localId:localId} 
	                     
	                 }
					 
				 }
			 },
			   messages : {
				   mobile: {
	                   required : "请输入您的手机！",
	                   mobile:"手机格式不对。",
	                   remote:"手机已注册！"
	                  
	               }
			   }
		});

		
		$("#"+id+" input").each(function() {
			$(this).focusin(function() {
				$("#"+$(this).attr("id")+"-prompt").show();
			});
			$(this).focusout(function() {
				$("#"+$(this).attr("id")+"-prompt").hide();
			});
			//$("#mobile").focus().select();
		});
		
		$.extend($.validator.messages, {
			required: '必填',
		    equalTo: "请再次输入相同的值"
		});
		
   }
   
   
   function validateMethod(id,ctx){
	   jQuery.validator.addMethod("mobile", function (value, element) {
		    var mobile = /^1[3|4|5|7|8]\d{9}$/;
			return this.optional(element) || (mobile.test(value));
		}, "手机格式不对");
		
		$("#"+id).validate({
			//success: function(label) {label.addClass("valid");},
		 	success: "valid",
			focusInvalid: false,
			focusCleanup: true,
			onkeyup: false, 
			errorElement:'span',
			 rules:{
				 mobile:{
					 required:true,
					 mobile:true,
					 remote:
	                 {
	                     url:ctx+"/user/check_mobile.mn",//后台处理程序
	                     type:"post",                        //数据发送方式
	                     dataType:"json", //接受数据格式 
	                     data:{mobile:function(){return $("#mobile").val();}} 
	                     
	                 }
					 
				 }
			 },
			   messages : {
				   mobile: {
	                   required : "请输入您的手机！",
	                   mobile:"手机格式不对。",
	                   remote:"手机已注册！"
	                  
	               }
			   }
		});
		$("#"+id+" input").each(function() {
			$(this).focusin(function() {
				$("#"+$(this).attr("id")+"-prompt").show();
			});
			$(this).focusout(function() {
				$("#"+$(this).attr("id")+"-prompt").hide();
			});
			//$("#mobile").focus().select();
		});
		
		$.extend($.validator.messages, {
			required: '必填',
		    equalTo: "请再次输入相同的值"
		});
		
   }
   
