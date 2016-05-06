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
		
		
			<div class="row-fluid">		
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
						<table class="table table-striped table-bordered bootstrap-datatable " id="myboxWash">
						  <thead>
							  <tr>
							      <th>序号</th>
								  <th>月份</th>
								  <th>模块数</th>
								  <th>单脱</th>
								  <th>快洗</th>
								  <th>标准洗</th>
								  <th>大物洗</th>
							      <th>金额</th>
							  </tr>
						  </thead>   
						  <tbody > 
							 <tr class="huanchong">
								<td colspan="9" style="text-align: center;"><img
									src="${ctx}/static/charisma/img/ajax-loaders/ajax-loader-7.gif"
									title="img/ajax-loaders/ajax-loader-7.gif"></td>
							</tr>
						  </tbody>
					  </table>  
					  <%@ include file="../common/loadMore.jsp"%>          
					</div>
				</div><!--/span-->
			
			</div><!--/row-->

	
				
	    </div>
	    
	    <input type="hidden" value="0" id="page"/>
	    <input type="hidden" value="${m}" id = "m"/>
 
   <script type="text/javascript">
      $(function(){
    	  tableSize("#myboxWash");
	   	   $("#page").val(0);
	       a_findBoxWash();
	   	   $("#more_data").click(function(){
	     		  var domPage = $("#page");
	     		  var page = parseInt(domPage.val())+1;
	     		  domPage.val(page);
	     		  a_findBoxWash();
	    	  
	      })
      });
      function a_findBoxWash(){
    	  var page = $("#page").val();
    	  $.ajax( {  
    		    type : "POST",  
    		    url : "${ctx}/washCount/a_f_boxWashList.mn",
    		    data : {page:page}, 
    		    dataType: "json",  
    		    success : function(data) {
    		    	if(page == 0){
    		    		$(".huanchong").remove();
    		    	}
    		    	if(data.length > 0){
    		    		var html="";
        		    	var i = 1;
        		    	data.forEach(function(e){
        		    		var d=3*parseInt(page)+ i;
        		    		html +="<tr>";
    				        html +="<td class='center'>"+d+"</td>";
    				        html += "<td><a href='${ctx}/washCount/datelist.mn?date="+e[0]+"'>"+e[0]+"</a></td>";
    					 	html += "<td class='center'>"+e[2]+"</td>";
    					 	html += "<td class='center'>"+e[3]+"</td>";
    						html += "<td class='center'>"+e[4] +"</td>";
    						html += "<td class='center'>"+e[5]+"</td>";
    						html += "<td class='center'>"+e[6]+"</td>";
    						html += "<td class='center'>"+e[1]+"</td>";
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