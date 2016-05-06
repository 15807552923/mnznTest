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
						<a href="#">Home</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">Tables</a>
					</li>
		  	</ul>
	       </div>
		
		
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>用户与消费统计</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
							      <th>序号</th>
								  <th>日期</th>
								  <th>用户数</th>
								  <th>充值金额</th>
								  <th>消费金额</th>
								  <th>模块数</th>
							  </tr>
						  </thead>   
						  <tbody > 
							
						  </tbody>
					  </table>            
					</div>
				</div><!--/span-->
			
			</div><!--/row-->

	
				
	    </div>
	    
	    <input type="hidden" value="0" id="page"/>
	    <input type="hidden" value="${m}" id = "m"/>
 
   <script type="text/javascript">
      $(function(){
    	  tableSize(".box-content");
    	   $(".box-content tbody").html("");
    	  a_findBoxInfo();
    	  
      })
   
      function a_findBoxInfo(){
    	  var page = $("#page").val();
    	  var m = $("#m").val();
    	  
    	  $.ajax( {  
    		    type : "POST",  
    		    url : "${ctx}/count/a_f_usList_om.mn",
    		    data : {page:page,m:m}, 
    		    dataType: "json",  
    		    success : function(data) {  
    		    	var html="";
    		    	var i = 1;
    		    	data.forEach(function(e){
    		    		html +="<tr>";
				        html +="<td class='center'>"+i+"</td>";
				        html += "<td><a href='#'>"+e[0]+"</a></td>";
					 	html += "<td class='center'>"+e[1]+"</td>";
					 	html += "<td class='center'>"+e[2]+"</td>";
						html += "<td class='center'>"+e[3] +"</td>";
						html += "<td class='center'>"+e[4]+"</td>";
						html += "</tr>";
					    i++;
    		    	})
    		    	
    		      $(".box-content tbody").html(html);
    		    },  
    		    error :function(){  
    		        alert("请稍后再试！");  
    		    }  
    		});   
      }
      
   
   </script>

</body>
</html>