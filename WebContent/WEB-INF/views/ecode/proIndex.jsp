<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
						<a href="#">基本信息</a>
					</li>
				</ul>
			</div>
			
			<div class="sortable row-fluid">
				<a data-rel="tooltip" title="今日 新增   ${todayAdd} 新成员" class="well span3 top-block" href="#">
					<span class="icon32 icon-red icon-user"></span>
					<div>后台商户</div>
					<div>${totalUser}</div>
					<span class="notification">${todayAdd}</span>
				</a>

			 	<a data-rel="tooltip" title="" class="well span3 top-block" href="#" id="money_title">
					<span class="icon32 icon-color icon-cart"></span>
					<div>消费总额</div>
					<div id="money_totle">0</div>
					<span class="notification yellow" id="money_span">0</span>
				</a>
				
				<a data-rel="tooltip" title="" class="well span3 top-block" href="#" id="message_title">
					<span class="icon32 icon-color icon-inbox"></span>
					<div>盒子总数</div>
					<div id="message_div">0</div>
					<span class="notification red" id= "message_span">0</span>
				</a>
			</div>
			
			
 			<div class="well">
				 <h4>登录用户：${curUser.name}</h4>
				 <h4>简称：${curUser.contcat}</h4>
				 <h4>登地址：${curUser.address}</h4>
				 <h4>手机号码：${curUser.mobile}</h4>
				 <h4>上次登录ip：${curUser.loginIp}</h4>
				 <h4>上次登录时间：${curUser.lastLoginTime}</h4>
			</div>
	</div>
<script type="text/javascript">
  $(function(){
	  totalMoney();
	  total_mk();
  });

function totalMoney(){
	  $.ajax({  
		    type : "POST",  
		    url : "${ctx}/getMoneyNum.mn",
		    dataType: "json",  
		    success : function(data) {
		    	$("#money_title").attr("title","今日新增消费"+data[1]);
		    	$("#money_totle").html(data[0]);
		    	$("#money_span").html(data[1]);
		    	 $('[rel="tooltip"],[data-rel="tooltip"]').tooltip({"placement":"bottom",delay: { show: 400, hide: 200 }}); 
		    },  
		    error :function(){  
		        alert("请稍后再试！");  
		    }  
		}); 
  }
  
  function total_mk(){
	  $.ajax({  
		    type : "POST",  
		    url : "${cxy}/pro/total_mk.mn",
		    dataType: "json",  
		    data:{localId:'${user.localid}'},
		    success : function(data) {
		    	 $("#message_title").attr("title",data[0]+"个支付盒子");
		         $("#message_div").html(data[0]);
		         $("#message_span").html(data[1]);
		    },  
		    error :function(){  
		        alert("请稍后再试！");  
		    }  
		}); 
  }
</script>

</body>
</html>