<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>mnzn_add</title>
</head>
<body>
	<%@ include file="./left.jsp"%>
<link href='${ctx}/static/ztree/zTreeStyle/zTreeStyle.css' rel='stylesheet'/>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" href="${ctx}/static/ztree/demo.css" type="text/css">

	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="#">Home</a> <span class="divider">/</span></li>
				<li><a href="#">${op=='edit' ? '更新' : '新增'}</a></li>
			</ul>
		</div>



		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title="">
					<h2>
						<i class="icon-edit"></i>${op=='edit' ? '更新角色' : '新增角色'}
					</h2>
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						<a href="#" class="btn btn-close btn-round"><i
							class="icon-remove"></i></a>
					</div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" action="${ctx}/user/${op=='edit' ? 'update' : 'save'}.mn" method="post">
					    <input type="hidden" name="site" value="1" />
					    <input type="hidden" name="id" value="${user.localid}">
						<fieldset>
							<div class="control-group">
								<label class="control-label" for="focusedInput">角色</label>
									<div class="controls" id="">
									  <c:forEach var= "role" items= "${roleList}">
									  	<label class="checkbox inline">
											<div id="uniform-${role[0]}" class="checker" >
												<span  id="s_${role[0]}"  > 
													<input type= "checkbox" name= "rolesIds" value="${role[0]}">
												</span>
											</div>${role[2]}
									  	</label>
									    </c:forEach>
									</div>
							</div>
							
							
							<div class="control-group">											
								<label class="control-label" for="name">经销商名称</label>
								<div class="controls">
									<input type="text" class="input-medium" name="name" value="${user.name}" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->

							<div class="control-group">											
								<label class="control-label" for="contact">联系人</label>
								<div class="controls">
									<input type="text" class="input-medium" name="contcat" value="${user.contcat}" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							<div class="control-group">											
								<label class="control-label" for="address">地址</label>
								<div class="controls">
									<input type="text" class="input-medium" name="address" value="${user.address}" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
																											
							<div class="control-group">											
								<label class="control-label" for="mobile">手机号</label>
								<div class="controls">
									<input type="text" class="input-medium" name="mobile" value="${user.mobile }" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->

							<div class="form-actions">
								<button type="submit" class="btn btn-primary">保存</button> 
								<button type="button" onclick="javascript:history.go(-1);" class="btn">取消</button>
								<input type="hidden" name="t" value="4" />
								<input type="hidden" name="status" value="1" />
								<input type="hidden" name="localid" value="${user.localid }" />
							</div> <!-- /form-actions -->

						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<!--/row-->


	</div>


	<SCRIPT type="text/javascript">
	  Array.prototype.S=String.fromCharCode(2);
	   Array.prototype.in_array=function(e){
	       var r=new RegExp(this.S+e+this.S);
	       return (r.test(this.S+this.join(this.S)+this.S));
	   }; 
	
	
	   $(function(){
		  fun();
	   })
	
	   function fun(){
		    var roleList = eval("("+'${userRoles}'+")");
		    var ids = new Array();
		    if(roleList.length<1){
		    	return;
		    }
		    for(var i= 0 ;i<roleList.length;i++ ){
		    	ids.push(roleList[i]);
		    }
			var boxes = document.getElementsByName("rolesIds");
			for(i=0;i<boxes.length;i++){
				     var b_val = boxes[i].value;
					 var r =ids.in_array(b_val);
					 if(r){
						 boxes[i].checked = true;
						 $("#s_"+b_val+" input").click();
						 $("#s_"+b_val+" input").attr("checked","checked")
			}
		}
	   }
	</SCRIPT>

</body>
</html>