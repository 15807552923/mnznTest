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
				<li><a href="#">${op=='edit' ? '更新运营商' : '新增运营商'}</a></li>
			</ul>
		</div>



		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title="">
					<h2>
						<i class="icon-edit"></i>${op=='edit' ? '更新运营商' : '新增运营商'}
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
					<form id="validForm"  class="form-horizontal" action="${ctx}/pro/${op=='edit' ? 'merchantUpdate' : 'merchantSave'}.mn" method="post">
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
								<label class="control-label" for="name">子运营商名称<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="validForm" name="name" value="${operator.name}"  required/>
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->

							<div class="control-group">											
								<label class="control-label" for="contact">联系人<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="validForm" name="contcat" value="${operator.contcat}" required />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							<div class="control-group">											
								<label class="control-label" for="address">地址<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="validForm" name="address" value="${operator.address}" required />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
																											
							<div class="control-group">											
								<label class="control-label" for="mobile">登录手机号<em class="required">*</em></label>
								<div class="controls">
									<input type="text" id="mobile" name="mobile" class="fn-tinput" placeholder="手机号" value="${operator.mobile}" required />
								     <span id="mobile-prompt" for="mobile" class="prompt">请填写正确的手机号码</span>	
								</div> <!-- /controls -->
							</div> <!-- /control-group -->
							
									
							<div class="control-group">
								<label class="control-label" for="paytype">支付类别<em class="required">*</em></label>
								<div class="controls">
								  <select id="paytype" name="paytype">
								    <option value="0">微信</option>
									<option value="1">支付宝</option>
									<option value="2" selected="selected">银行卡</option>
								  </select>
								</div>
							  </div>
							
							
							<div class="control-group">											
								<label class="control-label" for="payname" id="payname_l">转账姓名<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="fn-tinput" name="payname" value="${operator.payname }" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							<div class="control-group" id="bankname_d">											
								<label class="control-label" for="bankname" id="bankname_l">开户行<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="fn-tinput" name="bankname" value="${operator.bankname }" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							
							<div class="control-group">											
								<label class="control-label" for="payaccount" id="payaccount_l">账号<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="fn-tinput" name="payaccount" value="${operator.payaccount }" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							<div class="control-group">											
								<label class="control-label" for="servicephone">服务电话<em class="required">*</em></label>
								<div class="controls">
									<input type="text" class="validForm" name="servicephone" required value="${operator.servicephone }" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							
							<c:if test="${op !='edit'}">
							<div class="control-group">											
								<label class="control-label" for="password">密码<em class="required">*</em></label>
								<div class="controls">
									<input type="password" id="password" class="fn-tinput" name="password" required value="${operator.password }" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
							<div class="control-group">											
								<label class="control-label" for="password2">再一次密码<em class="required">*</em></label>
								<div class="controls">
									<input type="password" class="fn-tinput" name="confirm_password"  required value="${operator.password }"  equalTo="#password" />
								</div> <!-- /controls -->				
							</div> <!-- /control-group -->
							
                            </c:if>
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">保存</button> 
								<button type="button" onclick="javascript:history.go(-1);" class="btn">取消</button>
								<input type="hidden" name="status" value="1" />
								<input type = "hidden" name="usertype" value="3"/>
								<input type="hidden" name="localid" value="${operator.localid }" />
								<input type= "hidden" name="agencyid" value="${agencyid}">
							</div> <!-- /form-actions -->

						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<!--/row-->


	</div>
<script type="text/javascript">
$(function(){
	   if('${op}'=='edit'){
		   $("#paytype").find("option[value='${operator.paytype}']").attr("selected",true);
		   changePayStyle('${operator.paytype}');
		   validateMethodById('validForm','${ctx}','${operator.localid }');
	   }else{
		   validateMethod('validForm','${ctx}');
	   }
	  
	   $("#paytype").change(function () {  
		   var str = $("#paytype").val();
		   changePayStyle(str);
	    });  
})

function changePayStyle(str){
	if(str == '0'){
		   $("#bankname_d").css("display","none");
		   $("#payaccount_l").html("微信账号<em class='required'>*</em>")
	   }else if(str == '1'){
		   $("#bankname_d").css("display","none");
		   $("#payaccount_l").html("支付宝账号<em class='required'>*</em>")
	   }else if(str == '2'){
		   $("#bankname_d").css("display","block");
		   $("#payaccount_l").html("银行账号<em class='required'>*</em>")
	   }
}
 
</script>


</body>
</html>