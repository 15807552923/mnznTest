<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>木牛支付宝</title>

</head>

<body>
	<link href='${ctx}/static/ecode/mnpay.css' rel='stylesheet' />
	<div id="content" class="span10">
		<div class="withdraw-confirm-tit">
			<h3>确认提现信息</h3>
		</div>
		<form action="${ctx}/my/referMNPay.mn" method="post" onsubmit="return dosubmit()" >
			 <ul class="dashboard-list">
				<li>
					<p href="#">
						<label class="ui-label">银行卡信息：${curUser.payname }&nbsp;&nbsp;${curUser.payaccount }&nbsp;&nbsp;${curUser.servicephone }</label>
					</p>
				</li>
				<li>
					<p href="#">
						<label class="ui-label">提现金额：${money }</label>
					</p>
				</li>
				<li>
					<p href="#">
						<label class="ui-label">到账时间：24小时内</label>
					</p>
				</li>
		    </ul>
		<input name="money" value="${money}" type="hidden"/>
		<input type="hidden" name="token" value="${sessionScope.token}"/> 
		<input value="确认提现" class="btn btn-primary" type="submit" id="submit">
       </form>
	</div>
	
	<script type="text/javascript">
	function dosubmit(){
	      var btnSubmit = document.getElementById("submit");
	      //将表单提交按钮设置为不可用，这样就可以避免用户再次点击提交按钮
	      btnSubmit.disabled= "disabled";
	      //返回true让表单可以正常提交
	      return true;
	}
	</script>
</body>
</html>
