<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>用户注册</title>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						remote: "${ctx}/register/checkLoginName"
					}
				},
				messages: {
					loginName: {
						remote: "用户登录名已存在"
					}
				}
			});
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/my/password.mn" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>用户注册</small></legend>
			<div class="control-group">
				<label for="loginName" class="control-label">登录名:</label>
				<div class="controls">
					<label>${user.name}</label>
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">新密码:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="rawPassword" class="input-large required"/>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
