<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>木牛支付宝</title>
	
</head>

<body>
    
     <link href='${ctx}/static/ecode/mnpay.css' rel='stylesheet'/>
      <div id="content" class="span10">
          <p id="result">申请已提交！</p>
          <p><a href="${ctx}/my/mnpay.mn">返回木牛宝</a></p>
      </div>
   <script type="text/javascript">
      $(function(){
    	  if('${result}'!= null&& '${result}'!=''){
    		  $('#result').html('${result}');
    	  }
      })
   
   </script>
</body>
</html>
