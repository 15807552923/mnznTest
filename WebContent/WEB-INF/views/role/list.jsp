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
						<h2><i class="icon-user"></i> 角色列表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					
					<div class="row-fluid show-grid">
						<div class="span12" >	
						    <a href="${ctx}/role/add.mn" style="color: #fff;"> <button class="btn btn-small btn-success" style="float: left; margin-left: 20px;">增加+</button></a>
						</div>
			       </div>
					
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
							      <th>ID</th>
								  <th>roleName</th>
								  <th>decription</th>
								   <th>Actions</th>
							  </tr>
						  </thead>   
						  <tbody>
						 <c:forEach var="role" items="${roleList}">
							<tr>
						        <td class="center">${role[0]}</td>
							 	<td>${role[2]}</td>
								<td class="center">${role[3]}</td>
								<td class="center">
									<a class="btn btn-success" href="#">
										<i class="icon-zoom-in icon-white"></i>  
										View                                            
									</a>
									<a class="btn btn-info" href="${ctx}/role/edit.mn?id=${role[0]}">
										<i class="icon-edit icon-white"></i>  
										Edit                                            
									</a>
									<a class="btn btn-danger" href="#">
										<i class="icon-trash icon-white"></i> 
										Delete
									</a>
								</td>
							</tr>
							
							</c:forEach>
							
						  </tbody>
					  </table>            
					</div>
				</div><!--/span-->
			
			</div><!--/row-->


   </div>
<script type="text/javascript">
  $(function(){
	  tableSize(".box-content");
  })

</script>
</body>
</html>