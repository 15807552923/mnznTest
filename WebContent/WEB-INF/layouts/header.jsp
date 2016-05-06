<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link id="bs-css" href="${ctx}/static/charisma/css/bootstrap-cerulean.css" rel="stylesheet">
<link href="${ctx}/static/charisma/css/bootstrap-responsive.css" rel="stylesheet">
<link href="${ctx}/static/charisma/css/charisma-app.css" rel="stylesheet">
<link href="${ctx}/static/charisma/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='${ctx}/static/charisma/css/fullcalendar.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/fullcalendar.print.css' rel='stylesheet'  media='print'>
<link href='${ctx}/static/charisma/css/chosen.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/uniform.default.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/colorbox.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/jquery.cleditor.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/jquery.noty.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/noty_theme_default.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/elfinder.min.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/elfinder.theme.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/opa-icons.css' rel='stylesheet'>
<link href='${ctx}/static/charisma/css/uploadify.css' rel='stylesheet'>
<link href='${ctx}/static/ecode/mycss.css' rel='stylesheet'>
<link rel="shortcut icon" href="${ctx}/static/charisma/img/favicon.ico">	

	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery-ui-1.8.21.custom.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-transition.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-alert.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-modal.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-dropdown.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-scrollspy.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-tab.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-tooltip.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-popover.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-button.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-collapse.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-carousel.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-typeahead.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/bootstrap-tour.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.cookie.js"></script>
	<script type="text/javascript" src='${ctx}/static/charisma/js/fullcalendar.min.js'></script>
	<script type="text/javascript" src='${ctx}/static/charisma/js/jquery.dataTables.min.js'></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/excanvas.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.flot.stack.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.flot.resize.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.chosen.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.colorbox.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.cleditor.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.noty.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.elfinder.min.js"></script>
	<script  type="text/javascript" src="${ctx}/static/charisma/js/jquery.raty.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.iphone.toggle.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.autogrow-textarea.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.uploadify-3.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/jquery.history.js"></script>
	<script type="text/javascript" src="${ctx}/static/ecode/common.js"></script>
	<script type="text/javascript" src="${ctx}/static/ecode/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/ecode/jquery.validation_zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/static/charisma/js/charisma.js"></script>
