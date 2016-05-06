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
<style type="text/css">
   form .prompt{width:280px;margin-left:10px;color:#999;display:none;}
   span.error {color: red;font-style: normal;margin-left: 10px;}
</style>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
$(function() {
	 validateMethod('validForm','${ctx}');
});



</script>

<link rel="stylesheet" href="${ctx}/static/ztree/demo.css" type="text/css">

	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="#">首页</a> <span class="divider">/</span></li>
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
					<form id="validForm" class="form-horizontal" action="${ctx}/pro/${op=='edit' ? 'dealerUpdate' : 'dealerSave'}.mn" method="post">
						<fieldset>
							<%-- <div class="control-group">
								<label class="control-label" for="focusedInput">角色</label>
									<div class="controls" id="">
									  	<label class="checkbox inline">
											<div id="uniform-1" class="checker" >
												<span  id="s_1"  > 
													<input type= "checkbox" name= "rolesIds" value="7" >
												</span>
											</div>经销商
									  	</label>
									    </c:forEach>
									</div>
							</div> --%>
							
							
							<div class="control-group">											
								<label class="control-label" for="name">经销商名称<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="fn-tinput " required name="name" value="${dealer.name}" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->

							<div class="control-group">											
								<label class="control-label" for="contact">联系人<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="fn-tinput " required name="contcat" value="${dealer.contcat}" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							<div class="control-group">											
								<label class="control-label" for="address">地址<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="fn-tinput " required name="address" value="${dealer.address}" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
																											
							<div class="control-group">											
								<label class="control-label" for="mobile">手机号<em class="required">*</em></label>
								<div class="controls">
									<input type="text" id="mobile" name="mobile" class="fn-tinput" placeholder="手机号" value="${dealer.mobile}"  />
								     <span id="mobile-prompt" for="mobile" class="prompt">请填写正确的手机号码</span>	
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							
							
							
							<c:if test="${op !='edit'}">
							<div class="control-group">											
								<label class="control-label" for="password">密码<em class="required">*</em></label>
								<div class="controls">
									<input type="password" id="password" class="fn-tinput" name="password" required value="${dealer.password }" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							<div class="control-group">											
								<label class="control-label" for="password2">再一次密码<em class="required">*</em></label>
								<div class="controls">
									<input type="password" class="fn-tinput" name="confirm_password"  required value="${dealer.password }"  equalTo="#password" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
                            </c:if>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">保存</button> 
								<button type="button" onclick="javascript:history.go(-1);" class="btn">取消</button>
								<input type="hidden" name="t" value="4" />
								<input type="hidden" name="status" value="1" />
								<input type = "hidden" name="usertype" value="1"/>
								<input type="hidden" name="localid" value="${dealer.localid }" />
								
							</div> <!-- /form-actions -->

						</fieldset>
						
						
					</form>
				</div>
			</div>
		</div>
		<!--/row-->


	</div>



</body>
</html>