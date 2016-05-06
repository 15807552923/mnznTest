<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>mnzn<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<%@include file="./header.jsp"%>
</head>

<body>

  <%@include file="./charismaNav.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<sitemesh:body/>
	   </div>
	   <%@include file="./footer.jsp"%>
    </div>		
		
	    
	 

</body>
</html>