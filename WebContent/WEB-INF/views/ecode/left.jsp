<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
			<div class="span2 main-menu-span" style="margin-bottom: 10px;">
				<div class="well nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li class="nav-header hidden-tablet">列表</li>
						
						<shiro:hasPermission name="pro:ecode:index">
						<li><a class="ajax-link" href="${ctx}/pro/goProIndex.mn"><i class="icon-home"></i><span class="hidden-tablet">基本信息</span></a></li>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="pro:dealer:list">
						<c:choose>
						  <c:when test="${sessionScope.curUser.usertype == '0' || sessionScope.curUser.usertype == '1'}">
						     <li><a class="ajax-link" href="${ctx}/pro/dealerMana.mn"><i class="icon-eye-open"></i><span class="hidden-tablet">经销管理</span></a></li>
						  </c:when>
						   <c:when test="${sessionScope.curUser.usertype == '2'}">
						     <li><a class="ajax-link" href="${ctx}/pro/MyDealer.mn"><i class="icon-eye-open"></i><span class="hidden-tablet">经销管理</span></a></li>
						  </c:when>
						  <c:when test="${sessionScope.curUser.usertype == '3'}">
						    <li><a class="ajax-link" href="${ctx}/box/infoList.mn?companyId=${sessionScope.user.localid}"><i class="icon-eye-open"></i><span class="hidden-tablet">经销管理</span></a></li>
						  </c:when>
						 <c:otherwise>
						 
						 </c:otherwise>
						</c:choose>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="pro:wash:consume">
						<li><a class="ajax-link" href="${ctx}/washCount/list.mn"><i class="icon-edit"></i><span class="hidden-tablet">消费统计</span></a></li>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="pro:user:list">
					    <li><a class="ajax-link" href="${ctx}/count/userStat.mn"><i class="icon-list-alt"></i><span class="hidden-tablet">用户统计</span></a></li>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="ecode:bill:account">
					    <li><a class="ajax-link" href="${ctx}/bill/list.mn"><i class="icon-font"></i><span class="hidden-tablet">账单管理</span></a></li>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="ecode:bill:transferList">
					    <li><a class="ajax-link" href="${ctx}/bill/transferList.mn"><i class="icon-align-justify"></i><span class="hidden-tablet">转账查询</span></a></li>
						</shiro:hasPermission>
						 <shiro:hasPermission name="ecode:bill:transferList">
					        <li><a class="ajax-link" href="${ctx}/bill/withdrawal.mn"><i class="icon-align-justify"></i><span class="hidden-tablet">提现处理</span></a></li>
						</shiro:hasPermission>
						
						 <shiro:hasPermission name="ecode:cusSer:index">
					    <li><a class="ajax-link" href="${ctx}/cusSer/index.mn"><i class="icon-headphones"></i><span class="hidden-tablet">客服管理</span></a></li>
						</shiro:hasPermission>
						
					</ul>
				</div><!--/.well -->
			</div><!--/span-->
			<!-- left menu ends -->

