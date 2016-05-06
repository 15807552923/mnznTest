<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>木牛支付宝</title>
</head>

<body>
    <style type="text/css">
	   .table th, .table td {
	       text-align: center; 
	   }
	</style>
     <link href='${ctx}/static/ecode/mnpay.css' rel='stylesheet'/>
      <div id="content" class="span10">
           <div class="i-banner" >
              <div class="i-banner-content">
                 <div class="i-banner-portrait">
                     <a><img  src="${ctx}/static/images/default.png"/></a>
                 </div>
               </div>
               <div class="i-banner-main">
                     <div class="i-banner-main-hello fn-clear">
                           <p class="userName fn-left" style="float: left;">
                                                                                                                      用户名：${curUser.name }
                           </p>
                     </div>
                     <div class="i-banner-main-hello fn-clear">
                           <p class="userName fn-left">
                                                                                                                    上次登录时间：${curUser.lastLoginTime }
                           </p>
                     </div>
               </div>
           </div>
           <br style="clear: both;"/> 
           <div  style="margin-top :15px;background-color: #EEEEEE;border: 1px #ddd solid;float: left;padding: 5px;border-radius:5px;">
                  <div style="float: left;margin: 20px 5px ;width:100% "> 账户余额:</div>
                  <div style="float: left;margin-left: 5px;"><font style="font-size: 30px;font-weight: bold;">${curUser.recharge }</font><font style="margin-left: 10px;">元</font></div>    
                  <div style="float: left;margin:0px 30px 30px 30px;"><a class="btn btn-large" href = "${ctx}/my/transfercore.mn">提现</a> <a style="margin-left: 10px;" href ="#">查看</a></div>           
           </div>  
           <br style="clear: both;"/> 
           
           
           <div>
              <div class="box span12">
					<div class="box-header well">
						<h2><i class="icon-list-alt"></i> 交易记录</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					<div class="box-content">
						<ul class="nav nav-tabs" id="myTab">
							<li class="active"><a href="#near">最近交易记录</a></li>
							<li class=""><a href="#withDrawal">提现记录</a></li>
							<li class=""><a href="#reAccount">退款记录</a></li>
						</ul>
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane active" id="near" >
							   <table id="nearRecord" class="table table-bordered table-striped table-condensed">
								     <thead>
								      <th>序号</th>
								      <th>交易号</th>
								      <th>类别</th>
								      <th>创建时间</th>
								      <th>金额</th>
								      <th>状态</th>
								     </thead>
								      <tbody>
										<tr class="huanchong">
											<td colspan="9" style="text-align: center;"><img
												src="${ctx}/static/charisma/img/ajax-loaders/ajax-loader-7.gif"
												title="img/ajax-loaders/ajax-loader-7.gif"></td>
										</tr>
									</tbody>
							   </table>
							</div>
							<div class="tab-pane" id="withDrawal" >
							</div>
							<div class="tab-pane"  id="reAccount">
							</div>
						</div>
					</div>
				</div>
           
           </div>
           
           
      </div>
      
      <script type="text/javascript">
        $(function(){
        	nearRecord();
        })
        
        function nearRecord(){
       	  $.ajax( {  
       		    type : "POST",  
       		    url : "${ctx}/my/nearRecord.mn",
       		    data:{page:0},
       		    dataType: "json",  
       		    success : function(data) {
       		    	$("#nearRecord .huanchong").remove();
       		    	if(data.length > 0){
       		    		var html="";
       		    		var i = 1;
           		    	data.forEach(function(e){
           		    		html +="<tr>";
       				        html +="<td>"+i+"</td>";
       				        html += "<td><a href=''>"+e['traceNo']+"</a></td>";
       				        if(e['category'] == '1'){
       				        	html += "<td ><a href=''>提现</a></td>";
       				        }else{
       				        	html += "<td ><a href=''></a></td>";
       				        }
       				        html += "<td ><a href=''>"+new Date(e['mnPayInsertTime']).format("yyyy-MM-dd hh:mm:ss")+"</a></td>";
       					 	html += "<td >"+e['money']+"</td>";
       					 	if(e['status'] == '1'){
       					 		html += "<td >处理中</td>";
       					 	}else if (e['status'] == '2'){
       					    	html += "<td >完成</td>";
       					 	}else{
       					 		html += "<td >异常</td>";
       					 	}
       						
       						html += "</tr>";
       					    i++;
           		    	})
           		        $("#nearRecord tbody").append(html);
       		    	}else{
       		    		 $("#nearRecord tbody").html("<tr><td colspan='6'>没有更多数据</td></tr>");
       		    	}
       		    	
       		    },  
       		    error :function(){  
       		        alert("请稍后再试！");  
       		    }  
       		});   
         }
       
      </script>
   
</body>
</html>
