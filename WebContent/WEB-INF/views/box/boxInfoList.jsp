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
    <%@ include file="../ecode/left.jsp"%>

		<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="${ctx}/">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">支付模块</a>
					</li>
		  	</ul>
	       </div>
		
		
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>支付模块列表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					
					<div class="row-fluid show-grid">
						<div class="span12" >	
						    <a href="${ctx}/box/add.mn?companyId=${companyId}" style="color: #fff;"> <button class="btn btn-small btn-success" style="float: left; margin-left: 20px;">增加+</button></a>
						</div>
			       </div>
					
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable ">
						  <thead>
							  <tr>
							      <th>序号</th>
								  <th>编号</th>
								  <th>类型</th>
								  <th>状态</th>
								  <th>地址</th>
								  <th>单脱</th>
								  <th>快洗</th>
								  <th>标准洗</th>
								  <th>大物洗</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody > 
							
						  </tbody>
					  </table>  
					  
					   <%@ include file="../common/loadMore.jsp"%>
					            
					</div>
				</div><!--/span-->
			
			</div><!--/row-->

	
				
	    </div>
	    
	    <input type="hidden" value="0" id="page"/>
	    <input type="hidden" value="${companyId}" id = "companyId"/>
 
   <script type="text/javascript">
      $(function(){
    	  $("#page").val(0); 
    	   a_findBoxInfo();
    	   $("#more_data").click(function(){
     		  var domPage = $("#page");
     		  var page = parseInt(domPage.val())+1;
     		  domPage.val(page);
     		  a_findBoxInfo();
     	  });
    	   
    	  
      })
   
      function a_findBoxInfo(){
    	  var page = $("#page").val();
    	  var companyId = $("#companyId").val();
    	  
    	  $.ajax( {  
    		    type : "POST",  
    		    url : "${ctx}/box/a_f_infoList.mn",
    		    data : {page:page,localId:companyId}, 
    		    dataType: "json",  
    		    success : function(data) {
    		    	if(data == 403){
    		    		  var html="没有权限查看相关内容";
    		    		  $(".box-content tbody").html(html);
    		    	}else{
    		    		//var data = eval("("+json+")");
        		    	
    		    		if(data.length > 0){
    		    			var i = 1;
    		    			$.each(data,function(i,e){
    		    				var html="";
    		    				var d=10*parseInt(page)+ i;
            		    		html +="<tr>";
        				        html +="<td class='center'>"+d+"</td>";
        				        html += "<td>"+e[0]+"</td>";
        					 	
        					 	if(e[6] == '0'){
        					 		html += "<td class='center'>洗衣机</td>";
        					 	}else{
        					 		html += "<td class='center'>其他</td>";
        					 	}
        					 	html += "<td class='center'>"+e[7]+"</td>";
        						html += "<td class='center'>"+e[1] +"</td>";
        						html += "<td class='center'>"+e[2] +"</td>";
        						html += "<td class='center'>"+e[3] +"</td>";
        						html += "<td class='center'>"+e[4] +"</td>";
        						html += "<td class='center'>"+e[5] +"</td>";
        						
        						html += "<td class='center'> <a class='btn btn-info btn-mini' href='${ctx}/box/edit.mn?deviceNo="+e[0]+"'> <i class='icon-zoom-in icon-white'></i>修改 </a> <a class='btn btn-danger btn-mini' href='#'> <i class='icon-edit icon-white'></i>删除";
        						html += "</a>";
        						html += "</td></tr>";
        					   
        					    i++;
            		    	})
            		    	 $(".box-content tbody").append(html);
    		    		}else{
    		    			
    		    			 $("#more_data").html("没有更多数据")
    		    			
    		    		}
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