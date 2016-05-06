<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1">
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
     <link href='${ctx}/static/ecode/mnpay.css' rel='stylesheet'/>
	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="#">Home</a> <span class="divider">/</span></li>
				<li><a href="#">Tables</a></li>
			</ul>
		</div>


		<div class="row-fluid ">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					<h2>
						<i class="icon-user"></i>提现管理
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
					<div class="span10">
						  <div class="input-append withDrawal_div" >
						    <div>
						    <span class="" for="searchStr">登录账号：</span>
							<input id="searchStr" name="searchStr" value="" type="text" >
							
							</div>
							 <div>
							<span class="control-label" for="dateChoose">申请时间：</span>
							<input class="input-xlarge datepicker " id="dateChoose" value="" type="text" >
							</div>
							<div>
							<span class="control-label" for="selectError3">状态:</span>
						    <select id="status" name="status" >
								<option value="1" selected="selected">处理中</option>
								<option value="2">完成</option>
								<option value="3">异常</option>
					    	</select>
					    	</div>
					    	<div>
							<button class="btn" type="button" onclick="search();">搜索</button>
							</div>
						  </div>
					</div>
				</p>

                <br style="clear: both;"/>
				<div class="box-content" >
					<table class="table table-striped table-bordered bootstrap-datatable" id="billStatList">
						<thead>
							<tr>
								<th><input type="checkbox" id="c_all">全选</th>
								<th>序号</th>
								<th>登录账号</th>
								<th>提现金额</th>
								<th>账户信息</th>
								<th>提交时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="9" style="text-align: center;"><img
									src="${ctx}/static/charisma/img/ajax-loaders/ajax-loader-7.gif"
									title="img/ajax-loaders/ajax-loader-7.gif"></td>
							</tr>
						</tbody>
					</table>
					<%@ include file="../common/loadMore.jsp"%>

				</div>
				
				<p>
		           <a class="btn btn-success" onclick="getXlsFromTbl('billStatList',null)"><span>导出EXCEL</span></a>
	            </p>
				
			</div>
			<!--/span-->

		</div>
		<!--/row-->

		

	</div>



	<input type="hidden" value="0" id="page" />
	<input type="hidden" value="${m}" id="m" />
	<button class="btn btn-primary noty" data-noty-options='' id="showMessage"><i class="icon-bell icon-white" id="" style="display: none;"></i></button>
	
	<script type="text/javascript">
      $(function(){
    	  tableSize(".box-content");
    	   $("#page").val(0);
    	   if('${status}'!=null && '${status}'!=''){
    		   $("#status").find("option[value='${status}']").attr("selected",true);
    	   }
    	   withdrawal();
    	   $(".box-content tbody tr:first").remove();
    	   $("#more_data").click(function(){
      		  var domPage = $("#page");
      		  var page = parseInt(domPage.val())+1;
      		  domPage.val(page);
      		  withdrawal();
      		 
      	  });
    	   
    	   $("#c_all").click(function(){
    		   if ("checked" == $(this).attr("checked")) {
    			   $("input[name='localIds']").each(function(){
    				   $(this).attr("checked","checked");
    				   $(this).parent().parent().attr("style", "background-color:#F8B939 ");
    			   });
 		       }else{
 		    	  $("input[name='localIds']").each(function(){
   				   $(this).attr("checked",false);
   				   $(this).parent().parent().attr("style", "background-color: #f9f9f9");
   			   });
 		       } 
    	   });
    	   
    	   $('.sum-setting').click(function(e){
    			e.preventDefault();
    			var sum=0.00;
    			 $("input[name='localIds']").each(function(){
    	    		    if ("checked" == $(this).attr("checked")) {
    	    		          sum += parseFloat($(this).attr('value'));
    	    		    } 
    	    		    
    	    	  }
    			 
    			 );	    
    			
    			$(".modal-body p").html("总金额为："+sum);
    			$('#myModal').modal('show');
    		}); 
    	   
    	 
    	   
      })
      
      function search(){
    	  $("#page").val(0);
    	  $("#more_data").html("加载更多数据");
    	  $(".box-content tbody").html("");
    	  $(".box-content tbody tr:first").html("<td colspan=\"9\" style=\"text-align: center;\"><img src=\"${ctx}/static/charisma/img/ajax-loaders/ajax-loader-7.gif\" title=\"img/ajax-loaders/ajax-loader-7.gif\"></td>");
    	  withdrawal();
    	  $(".box-content tbody tr:first").remove();
    	  
      }
      
      function changeColor(obj){
    	  var check =  $(obj).children().eq(0).children().eq(0).attr("checked");
    	  if("checked" == check){
    		  $(obj).children().eq(0).children().eq(0).attr("checked",false);
        	
        		  $(obj).attr("style", "background-color: #f9f9f9");
        	
    	  }else{
    		  $(obj).children().eq(0).children().eq(0).attr("checked","checked");
        		  $(obj).attr("style", "background-color: #F8B939");
    	  }
      }
      
      function account(id,applyId){
    	  var ctx='${ctx}';
    	  if(confirm('确定该账单已付款了吗？')){
    		  $.ajax( {  
    	  		    type : "POST",  
    	  		    url : "${ctx}/bill/a_account_withdrawal.mn",
    	  		    data : {id:id}, 
    	  		    dataType: "json",  
    	  		    success : function(data) {
    	  		    	if(data == "200"){
    	  		    		$("#acc_"+id + " a").eq(0).attr("onclick","accountBack("+id+","+applyId+")").html("退款");
    	  	  		    	$("#status_"+id).html("<span class='label label-success'>已结</span>");
    	  	  		    	$("#showMessage").attr("data-noty-options","{\"text\":\"id="+id+"结账成功\",\"layout\":\"topLeft\",\"type\":\"success\"}");
    	  		    	}else{
    	  		    		$("#showMessage").attr("data-noty-options","{\"text\":\"id="+id+"结账失败\",\"layout\":\"topLeft\",\"type\":\"success\"}");
    	  		    	}
    	  		    	
    	  		    },  
    	  		    error :function(){  
    	  		        alert("请稍后再试！");  
    	  		    }  
    	  		}); 
    		  
    	  }else{
    		  
    		  return false;
    	  }
    	  
      }
   
      function accountBack(id,applyId){
    	  var ctx='${ctx}';
    	  if(confirm('确定退款吗？')){
    		  $.ajax( {  
    	  		    type : "POST",  
    	  		    url : "${ctx}/bill/a_accountBack_withdrawal.mn",
    	  		    data : {id:id,applyId:applyId}, 
    	  		    dataType: "json",  
    	  		    success : function(data) {
    	  		    	alert(data);
    	  		    	if(data == "200"){
    	  		    		$("#acc_"+id + " a").eq(0).attr("onclick","#").html("退款");
    	  	  		    	$("#status_"+id).html("<span class='label label-error'>异常</span></td>");
    	  	  		    	$("#showMessage").attr("data-noty-options","{\"text\":\"id="+id+"退款成功\",\"layout\":\"topLeft\",\"type\":\"success\"}");
    	  		    	}else{
    	  		    		$("#showMessage").attr("data-noty-options","{\"text\":\"id="+id+"结账失败\",\"layout\":\"topLeft\",\"type\":\"success\"}");
    	  		    	}
    	  		    	
    	  		    },  
    	  		    error :function(){  
    	  		        alert("请稍后再试！");  
    	  		    }  
    	  		}); 
    		  
    	  }else{
    		  
    		  return false;
    	  }
    	  
      }
      function withdrawal(){
    	  var page = $("#page").val();
    	  var m = $("#m").val();
    	  var searchStr = $("#searchStr").val();
    	  var dateChoose = $("#dateChoose").val();
    	  var status = $("#status  option:selected").val();
    	  $.ajax( {  
    		    type : "POST",  
    		    url : "${ctx}/bill/a_withdrawal.mn",
    		    data : {page:page,searchStr:searchStr,dateChoose:dateChoose,status:status}, 
    		    dataType: "json",  
    		    success : function(data) {
    		    	if(data.length > 0){
    		    	 	var html="";
        		    	var i = 1;
        		    	$.each(data,function(i,e){
        		    		var d=10*parseInt(page)+ i+1;
        		    		html +="<tr onclick='changeColor(this);' class='tr_select'>";
        		    		html +="<td>"+d+"&nbsp;&nbsp;&nbsp;<input type='checkbox' name='localIds' value='"+e.money+"'  attr-localId='"+e.applyId+"' ></td>";
        		    		
    				        html +="<td class='center'>"+e.traceNo+"</td>";
    				        html += "<td>"+e.applyName+"</td>";
    					 	html += "<td class='center'>"+e.money+"</td>";
    					 	html += "<td class='center'>"+e.bankInfo+"</td>";
    						html += "<td class='center'>"+new Date(e.mnPayInsertTime).format("yyyy-MM-dd hh:mm:ss") +"</td>";
    						if(e.status == '1'){
    							html += "<td class='center'>等待处理</td>";
    						}else if(e.status =='2'){
    							html += "<td class='center'>完成</td>";
    						}else{
    							html += "<td class='center'>异常</td>";
    						}
    						
    						if(e.status == '1'){
    							html += "<td class='center' id='status_"+e.id+"' ><span class='label label-important'>未结</span></td>";
    							html += "<td class='center'  id='acc_"+e.id+"'>"+"<a href='#'  onclick='account("+e.id+","+e.applyId+")'>结账</a>"+"</td>";
    						}else if(e.status == '2' ){
    							html += "<td class='center' id='status_"+e.id+"'><span class='label label-success'>已结</span></td>";
    							html += "<td class='center'  id='acc_"+e.id+"'>"+"<a href='#' onclick='accountBack("+e.id+","+e.applyId+")'>退款</a>"+"</td>";
    						}else{
    							html += "<td class='center' id='status_"+e.id+"'><span class='label ' >异常</span></td>";
    						}
    						
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
      
   
   </script>

</body>
</html>