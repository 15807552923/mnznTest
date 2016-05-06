<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%@ include file="./left.jsp"%>

		<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="${ctx}/">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">用户列表</a>
					</li>
		  	</ul>
	       </div>
		
		
			<div class="row-fluid ">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>用户</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					
					
					<p class="row-fluid">
					   <div>
					     <%--  <a href="${ctx}/user/add.mn" style="color: #fff;"> <button class="btn btn-small btn-success" style="float: left; margin-left: 20px;">增加+</button></a> --%>
					   </div>
					
						<div class="span8">
							  <div class="input-append">
							    <span class="" for="name">用户姓名：</span>
								<input id="name" name="name" value="" type="text">
								<button class="btn" type="button" onclick="search();">搜索</button>
							  </div>
						</div>
			  	  </p>
			  	  
					
					<div class="box-content">
						<table class="table table-striped table-bordered " id="billStatList">
						  <thead>
							  <tr>
							      <th>序号</th>
								  <th>姓名</th>
								  <th>描述</th>
								  <th>地址</th>
								  <th>电话</th>
								  <th>类型</th>
								  <th>注册日期</th>
							      <th>更新时间</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody > 
						  </tbody>
					  </table> 
					  
					 <p><button class="btn btn-large btn-inverse" style="width: 100%;height: 30px;" id="more_data">加 载 更 多</button></p>  
					</div>
				</div><!--/span-->
			
			</div><!--/row-->
				
	    </div>
	    
	    <input type="hidden" value="0" id="page"/>
	    <input type="hidden" value="${m}" id = "m"/>
 
   <script type="text/javascript">
   
      $(function(){
    	 tableSize(".box-content");
    	//  $(".box-content tbody").html("<tr><td cospan='9' style='text-aglin:center;'><img src='${ctx}/static/charisma/img/ajax-loaders/ajax-loader-7.gif' title='img/ajax-loaders/ajax-loader-7.gif'></td></tr>");
    	$("#page").val(0); 
    	a_findUserList();
        $("#more_data").click(function(){
    		  var domPage = $("#page");
    		  var page = parseInt(domPage.val())+1;
    		  domPage.val(page);
    		  a_findUserList();
    	  });
    	  
      })
   
         function search(){
    	  $("#page").val(0);
    	  $("#more_data").html("加载更多数据");
    	  $(".box-content tbody").html("");
    	  $(".box-content tbody tr:first").html("<td colspan=\"9\" style=\"text-align: center;\"><img src=\"${ctx}/static/charisma/img/ajax-loaders/ajax-loader-7.gif\" title=\"img/ajax-loaders/ajax-loader-7.gif\"></td>");
    	  a_findUserList();
    	  $(".box-content tbody tr:first").remove();
    	  
      }
      
      
      function a_findUserList(){
    	  var page = $("#page").val();
    	  var name = $("#name").val();
    	  $.ajax( {  
    		    type : "POST",  
    		    url : "${ctx}/user/a_f_userList.mn",
    		    data : {page:page,name:name}, 
    		    dataType: "json",  
    		    success : function(data) {
    		    	if(data.length > 0){
    		    	var html="";
    		    	data.forEach(function(e){
    		    		html +="<tr>";
				        html +="<td class='center'>"+e.localid+"</td>";
				        html += "<td>"+e.name+"</td>";
					 	html += "<td class='center'>"+e.contcat+"</td>";
					 	html += "<td class='center'>"+e.address+"</td>";
						html += "<td class='center'>"+e.mobile +"</td>";
						
						if(e.usertype == '0'){
							html += "<td class='center'>管理员</td>";
						}else if(e.usertype=='1'){
							html += "<td class='center'>经销商</td>";
						}else if(e.usertype == '2'){
							html += "<td class='center'>运营商</td>";
						}else if(e.usertype == '3'){
							html += "<td class='center'>子运营商</td>";
						}else if(e.usertype == '4'){
							html += "<td class='center'>财务</td>";
						}else if(e.usertype == '5'){
							html += "<td class='center'>客服</td>";
						}else{
							html += "<td class='center'>未知</td>";
						}
					
						
						html += "<td class='center'>";
						if(e.billDate!=""){html += new Date(e.inserttime).format("yyyy-MM-dd")}
						html +="</td>";
						html += "<td class='center'>";
						if(e.billDate!=""){html += new Date(e.updatetime).format("yyyy-MM-dd")}
						html +="</td>";
						html += "<td class='center'>"+"<a class='btn btn-success' href='${cxy}/user/edit.mn?localId="+e.localid+"'><i class='icon-zoom-in icon-white'></i>编辑</a>"+"</td>";
						html += "</tr>";
    		    	})
    		     		 $(".box-content tbody").append(html);
    		    	
    		    	}else{
    		    		 $("#more_data").html("没有更多数据")
    		    		
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