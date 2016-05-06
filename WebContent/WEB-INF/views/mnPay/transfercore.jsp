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
	<h2 class="title" style="margin-bottom: 10px;">提取余额到银行卡</h2>
	  <form  action="${ctx}/my/preConfirm.mn" method="POST" class="ui-form" id="withDrawal" >
	   <ul class="dashboard-list">
			<li>
				<p href="#">
					<span>余额：<span class="number">${curUser.recharge}</span>元</span>
				</p>
			</li>
			<li>
				<p href="#">
					<span class="" for="">提现到:${curUser.payname }&nbsp;&nbsp;${curUser.payaccount }&nbsp;&nbsp;${curUser.servicephone }</span>
				</p>
			</li>
			<li>
				<p href="#">
					<span class="ui-label" for="amount">提现金额: </span><input  name="money" id="money"> 元
				</p>
			</li>
			<li>
				<p href="#">
					<input smartracker="on"  id="nextBtn" value="下一步" class="btn btn-primary"  type="submit">
				</p>
			</li>
		</ul>
		<input type="hidden" name="token" value="${sessionScope.token}"/> 
		</form>
	</div>
	<script type="text/javascript">
	     $(function(){
	  	   jQuery.validator.addMethod("money", function (value, element) {
			    var money = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
				return this.optional(element) || (money.test(value));
			}, "提现金额格式不对");
	  	   
			$("#withDrawal").validate({
				//success: function(label) {label.addClass("valid");},
			 	success: "valid",
				focusInvalid: false,
				focusCleanup: true,
				onkeyup: false, 
				errorElement:'span',
				 rules:{
					 money:{
						 required:true,
						 money:true,
						 remote:
		                 {
		                     url:"${ctx}/user/check_withDrawal.mn",//后台处理程序
		                     type:"post",                        //数据发送方式
		                     dataType:"json", //接受数据格式 
		                     data:{money:function(){return $("#money").val();}} 
		                     
		                 }
					 }
				 },
				   messages : {
					   money: {
		                   required : "请输入提现金额！",
		                   money:"提现金额格式不对。",
		                   remote:"提现金额超过余额！"
		               }
				   }
			});
	     })
	
	</script>
</body>
</html>
