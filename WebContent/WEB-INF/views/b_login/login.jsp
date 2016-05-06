<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>mnzn</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />  
<jsp:include page="${ctx}/WEB-INF/layouts/charismaHeader.jsp" />
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>木牛物联</h2><span></span>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info">
						<c:if test="${!empty shiroLoginFailure }">
						<c:choose> 
						  <c:when test="${shiroLoginFailure=='com.mnzn.ecode.security.IncorrectCaptchaException' }">   
						    <span>验证码不正确！</span> 
						  </c:when> 
						  <c:when test="${shiroLoginFailure=='org.apache.shiro.authc.IncorrectCredentialsException'}">   
						    <span>用户名或密码错误！</span> 
						  </c:when> 
						   <c:when test="${shiroLoginFailure=='com.mnzn.ecode.security.UnactivatedAccountException'}">   
						    <span>用户不存在！</span> 
						  </c:when> 
						  <c:otherwise>   
						      <span>${shiroLoginFailure}</span> 
						  </c:otherwise> 
						</c:choose> 
						</c:if>
						<c:if test="${empty shiroLoginFailure }">
						<span>请输入用户名和密码</span> 
						</c:if>
					</div>
					<form class="form-horizontal" action="${ctx}/login.mn"
						method="post" id="loginForm">
						<fieldset>
							<div class="input-prepend" title="用户名" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span><input
									autofocus class="input-large span10 required" name="username"
									id="username" type="text" value="" placeholder="用户名" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="密码" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span><input
									class="input-large span10" name="password" id="password"
									type="password" value=""  placeholder="密码"/>
							</div>
		                   <c:if test="${shiroCaptchaRequired}">
						     <%-- <p class="input_p">
								<label for="captcha" class="label">&nbsp;</label>
								<img src="${ctx}/captcha.servlet" onclick="this.src='${ctx}/captcha.servlet?d='+new Date()*1" style="cursor:pointer;border:1px solid #ccc;" title="点击重新获取验证码"/>
								<div class="clear"></div>
							</p> --%>
							<div class="line">
								<div class="input">
									<input type="text" id="captcha" name="captcha" class="text" data-rule-required="true" data-rule-remote='{"url":"${ctx}/captcha.servlet","type":"post"}' data-msg-remote="验证码错误" style="width:94px;" autocomplete="off"/>
									<img src="${ctx}/captcha.servlet" onclick="this.src='${ctx}/captcha.servlet?d='+new Date()*1;$('#captcha').focus();" style="cursor:pointer;border:1px solid #ccc;margin:0;vertical-align:top;" title="点击重新获取验证码"/><label for="captcha" generated="true" class="error"></label>
								</div>
							</div>
                          </c:if>

							<div class="clearfix"></div>

						<!-- 	<div class="input-prepend">
								<label class="remember" for="remember"><input
									type="checkbox" id="remember" name="rememberMe" />Remember me</label>
							</div> -->
							<div class="clearfix"></div>

							<p class="center span5">
								<button type="submit" class="btn btn-primary">登录</button>
							</p>
						</fieldset>
					</form>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->
	</div>
	<jsp:include page="${ctx}/WEB-INF/layouts/charismaFooter.jsp" />
</body>
</html>
