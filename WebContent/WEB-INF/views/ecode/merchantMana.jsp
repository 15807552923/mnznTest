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
						<a href="#">子商家</a>
					</li>
		  	</ul>
	       </div>
		
		
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i>子商家列表</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div>
					</div>
					
					<div class="row-fluid show-grid">
						<div class="span12" >	
						    <a href="${ctx}/pro/merchantAdd.mn?agencyid=${agencyid}" style="color: #fff;"> <button class="btn btn-small btn-success" style="float: left; margin-left: 20px;">增加+</button></a>
						</div>
			       </div>
					
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
							      <th>序号</th>
								  <th>子商家名称</th>
								  <th>联系人</th>
								  <th>手机</th>
								  <th>地址</th>
								  <th>模块数量</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody> 
						 <c:forEach var="user" items="${userList}" varStatus="u" >
							<tr>
						        <td class="center">${u.index+1}</td>
							 	<td>${user[1]}</td>
								<td class="center">${user[4]}</td>
								<td class="center">${user[2]}</td>
								<td class="center">${user[3]}</td>
								<td class="center" id="mk_${user[0]}">0</td>
								<td class="center">
									<a class="btn btn-info btn-mini " href="${ctx}/pro/merchantEdit.mn?localId=${user[0]}">
										<i class="icon-edit icon-white"></i>  
										修改                                           
									</a>
									<a class="btn btn-success btn-mini" href="${ctx}/box/infoList.mn?companyId=${user[0]}">
										<i class="icon-inbox icon-white"></i> 
										支付模块
									</a>
								</td>
							</tr>
							<script type="text/javascript">
							  $(function(){
								  $.post("${cxy}/pro/total_mk.mn", {localId:'${user[0]}'},
										  function(data){
									          var obj = eval('(' + data + ')');
									          $("#mk_${user[0]}").html(obj[0]);
										  });
							  })
							</script>
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