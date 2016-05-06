<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<style type="text/css">
.clickOn {
	background-color: #7BB43D;
}
</style>
</head>
<body>
	<%@ include file="./left.jsp"%>
	<script type="text/javascript"
		src="${cxt}/static/excel/jquery.base64.js"></script>
	<script type="text/javascript" src="${cxt}/static/excel/tableExport.js"></script>

	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="#">首页</a> <span class="divider">/</span></li>
				<li><a href="#">客服列表</a></li>
			</ul>
		</div>


		<div class="row-fluid ">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					<h2>
						<i class="icon-user"></i>客服管理
					</h2>
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						<a href="#" class="btn btn-close btn-round"><i
							class="icon-remove"></i></a>
					</div>
				</div>


				<p class="row-fluid">
					
					<div class="span8">
						  <div class="input-append">
						    <span class="" for="mobile">手机号码：</span>
							<input id="mobile" name="mobile" value="" type="text">
							<span class="" for="deviceNo">支付盒子编号：</span>
							<input id="deviceNo" name="deviceNo" value="" type="text">
							
							<button class="btn btn-info" type="button" onclick="search();">搜索</button>
						  </div>
					</div>
				</p>


				<div class="box-content">
					<table class="table table-striped table-bordered bootstrap-datatable" id="customSerList">
						<thead>
							<tr>
								<th>序号</th>
								<th>经销商</th>
								<th>运营商</th>
								<th>服务电话</th>
								<th>代理编号</th>
								<th>设备编号</th>
								<th>洗衣密码</th>
								<th>状态</th>
								<th>金额</th>
								<th>洗衣类型</th>
								<th>时间</th>
								<th >地址</th>
								<th >用户手机</th>
								<th >操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<%@ include file="../common/loadMore.jsp"%>

				</div>
				
				
			</div>
			<!--/span-->

		</div>
		<!--/row-->

		

	</div>

	<input type="hidden" value="0" id="page" />
	
	<script type="text/javascript">
      $(function(){
    	  tableSize(".box-content");
    	   $("#page").val(0);
    	   $("#more_p").css("display","none");
    	   $("#more_data").click(function(){
      		  var domPage = $("#page");
      		  var page = parseInt(domPage.val())+1;
      		  domPage.val(page);
      	      a_findWashBoxList();
      	  });
    	   
    	   
    	 
    	   
      })
      
      function search(){
    	  $("#more_p").css("display","block");
    	  $("#page").val(0);
    	  $("#more_data").html("加载更多数据");
    	  $(".box-content tbody").html("");
    	  $(".box-content tbody tr:first").html("<td colspan=\"9\" style=\"text-align: center;\"><img src=\"${ctx}/static/charisma/img/ajax-loaders/ajax-loader-7.gif\" title=\"img/ajax-loaders/ajax-loader-7.gif\"></td>");
    	  a_findWashBoxList();
    	  $(".box-content tbody tr:first").remove();
    	  
      }
      
     
      
    
   
     
      function a_findWashBoxList(){
    	  var page = $("#page").val();
    	  var mobile = $("#mobile").val();
    	  var deviceNo = $("#deviceNo").val();
    	  $.ajax( {  
    		    type : "POST",  
    		    url : "${ctx}/cusSer/a_f_boxWashList.mn",
    		    data : {page:page,mobile:mobile,deviceNo:deviceNo}, 
    		    dataType: "json",  
    		    success : function(data) {
    		    	if(data.length > 0){
    		    	 	var html="";
        		    	var i = 1;
        		    	$.each(data,function(i,e){
        		    		var d=10*parseInt(page)+ i+1;
        		    		html += "<tr>"
    				        html +="<td class='center'>"+d+"</td>";
    				        html += "<td>"+e[0]+"</td>";
    					 	html += "<td class='center'>"+e[1]+"</td>";
    					 	html += "<td class='center'>"+e[2]+"</td>";
    						html += "<td class='center'>"+e[3] +"</td>";
    						html += "<td class='center'>"+e[4]+"</td>";
    						
    						html += "<td class='center'>"+e[5]+"</td>";
    						html += "<td class='center'>"+e[11]+"</td>";
    						
    						html += "<td class='center'>"+e[6]+"</td>";
    						
    						html += "<td class='center'>";
    						if(e[7] == "601"){
    							html += "单脱";
    						}else if (e[7] == "602"){
    							html += "快洗";
    						}else if (e[7] == "603"){
    							html += "标准洗";
    						}else if (e[7] == "604"){
    							html += "大物洗";
    						}
    						
    						html +="</td>";
    						html += "<td class='center'>"+e[8]+"</td>";
    						html += "<td class='center'>"+e[9]+"</td>";
    						html += "<td class='center'>"+e[10]+"</td>";
    						if(e[13] == "0"){
    							html += "<td class='center re_status_"+e[12]+"' >"+"<button class=\"btn btn-mini btn-succese\" onclick='returnMoney(this)' loal_attr='"+e[12]+"'>退款</button>"+"</td>";
    						}else if(e[13] == "604"){
    							html += "<td class='center re_status_"+e[12]+"'>"+"<button class=\"btn btn-mini btn-info\" onclick='returnMoney(this)' loal_attr='"+e[12]+"'>604</button>"+"</td>";
    						}else if(e[13] == "1"){
    							html += "<td class='center re_status_"+e[12]+"'>"+"<button class=\"btn btn-mini btn-danger\" >已退款</button>"+"</td>";
    						}else{
    							html += "<td class='center re_status_"+e[12]+"'>"+"<button class=\"btn btn-mini btn-danger\" >未知</button>"+"</td>";
    						}
    						
    						/* if(e.status == '0'){
    							html += "<td class='center' id='status_"+e.localId+"' ><span class='label label-important'>未结</span></td>";
    							
    							html += "<td class='center'  id='acc_"+e.localId+"'>"+"<a href='#' onclick='account("+e.localId+","+ e.user.usertype+")'>结账</a>"+"</td>";
    						}else if(e.status == '2' || e.status == "1"){
    							html += "<td class='center' id='status_"+e.localId+"'><span class='label label-success'>已结</span></td>";
    							html += "<td class='center'  id='acc_"+e.localId+"'>"+"<a href='#' onclick='accountBack("+e.localId+","+ e.user.usertype+")'>退款</a>"+"</td>";
    						}else{
    							html += "<td class='center' id='status_"+e.localId+"'><span class='label ' >异常</span></td>";
    						} */
    						
    						
    						html += "</tr>";
    					    i++;
        		    	})
        		      $(".box-content tbody").append(html);
    		    		
    		    		
    		    		
    		    	}else{
    		    	
    		    		 $("#more_data").html("没有更多数据");
    		    		
    		    	}
    		   
    		    },  
    		    error :function(){  
    		        alert("请稍后再试！");  
    		    }  
    		});   
      }
      
   function returnMoney(e){
	   if(confirm("确认要退款吗？")){
		   var washId = $(e).attr("loal_attr");
	    	  $.ajax( {  
	    		    type : "POST",  
	    		    url : "${ctx}/cusSer/a_reBack.mn",
	    		    data : {washId:washId}, 
	    		    dataType: "json",  
	    		    success : function(data) {
	    		    	if(data=="200"){
	    		    		$(".re_status_"+washId).html("<button class=\"btn btn-mini btn-danger\" >已退款</button>");
	    		    	}else{
	    		    		 alert("请稍后再试！");  
	    		    	}
	    		        
	    		    },  
	    		    error :function(){  
	    		        alert("请稍后再试！");  
	    		    }  
	    		});  
		   
	   }
	   
   }
   </script>

</body>
</html>