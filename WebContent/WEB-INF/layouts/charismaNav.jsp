<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				
				<!-- theme selector starts -->
				<div class="btn-group pull-right theme-container" >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-tint"></i><span class="hidden-phone"> Change Theme / Skin</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" id="themes">
						<li><a data-value="classic" href="#"><i class="icon-blank"></i> Classic</a></li>
						<li><a data-value="cerulean" href="#"><i class="icon-blank"></i> Cerulean</a></li>
						<li><a data-value="cyborg" href="#"><i class="icon-blank"></i> Cyborg</a></li>
						<li><a data-value="redy" href="#"><i class="icon-blank"></i> Redy</a></li>
						<li><a data-value="journal" href="#"><i class="icon-blank"></i> Journal</a></li>
						<li><a data-value="simplex" href="#"><i class="icon-blank"></i> Simplex</a></li>
						<li><a data-value="slate" href="#"><i class="icon-blank"></i> Slate</a></li>
						<li><a data-value="spacelab" href="#"><i class="icon-blank"></i> Spacelab</a></li>
						<li><a data-value="united" href="#"><i class="icon-blank"></i> United</a></li>
					</ul>
				</div>
				<!-- theme selector ends -->
				
				<!-- user dropdown starts -->
				<div class="btn-group pull-right" >
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone">  <shiro:principal property="name"/></span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<!-- <li><a href="#">Profile</a></li> -->
						
						<li><a href="${ctx}/logout.mn">退出</a></li>
						<li class="divider"></li>
					</ul>
				</div>
				<!-- user dropdown ends -->
				
				<div class="top-nav nav-collapse" style="overflow: visible;z-index:999">
					  <ul id="nav-menu" class="main-menu-span" style="margin: 0 0 10px 0;" >
								<li class="nav-menu-li"><a href="${ctx}/homePage.mn" class="nav-menu-a nav-menu-select" id="menu-0" menu="#menu-0" submenu="#sm-0">首页</a></li>
								<li class="nav-menu-li nav-menu-sep"></li>
								<li class="nav-menu-li"><a href="#" class="nav-menu-a" id="menu-100" menu="#menu-100" submenu="#sm-100">项目</a></li>
								<li class="nav-menu-li nav-menu-sep"></li>
								<shiro:hasPermission name="user:manage">
								<li class="nav-menu-li"><a href="#" class="nav-menu-a" id="menu-200" menu="#menu-200" submenu="#sm-200">用户</a></li>
								</shiro:hasPermission>
								<li class="nav-menu-li nav-menu-sep"></li>
								<!-- <li class="nav-menu-li"><a href="#" class="nav-menu-a" id="menu-300" menu="#menu-300" submenu="#sm-300">统计</a></li>
								<li class="nav-menu-li nav-menu-sep"></li> -->
					   </ul>	
   
						  <ul style="display: none;top: 100px;left: 80px; " id="sm-100" class="sub-menu ">
							<li class="sub-menu-top"><div class="sub-menu-top-nested">&nbsp;</div></li>
							      <shiro:hasPermission name="pro:ecode:index">
										<li class="sub-menu-li"><a href="${ctx}/pro/goProIndex.mn"><div onclick="" class="sub-menu-div" menu="">智能洗衣机</div></a></li>			
								  </shiro:hasPermission>
								   <shiro:hasPermission name="pro:ele:index">
									    <li class="sub-menu-li"><div onclick="" class="sub-menu-div" menu="">充电桩</div></li>		
								   </shiro:hasPermission>	
							<li class="sub-menu-bottom"><div class="sub-menu-bottom-nested">&nbsp;</div></li>	   
						</ul>
						
						 <ul style="display: none;top: 100px;left: 160px;z-index:999" id="sm-200" class="sub-menu">
							<li class="sub-menu-top"><div class="sub-menu-top-nested">&nbsp;</div></li>	
									<li class="sub-menu-li"><a href="${ctx}/user/list.mn"><div onclick="" class="sub-menu-div" menu="">用户管理</div></a></li>			
									<li class="sub-menu-li"><a href="${ctx}/role/list.mn"><div onclick="" class="sub-menu-div" menu="">角色管理</div></a></li>			
							<li class="sub-menu-bottom"><div class="sub-menu-bottom-nested">&nbsp;</div></li>
						</ul>
	
				</div><!--/.nav-collapse -->
			</div>
		</div>
	</div>
	
	
		
<script type="text/javascript">
(function($) {
	var position = function(preferred,self,selfWidth,selfHeight,topHeight,bottomHeight,leftWidth,rightWidth,topByTop,topByBottom,leftByLeft,leftByRight) {
		var top,left;
		if (bottomHeight - selfHeight < 0 && topHeight > bottomHeight) {
			top = topByTop;
		} else if(topHeight - selfHeight < 0 && bottomHeight > topHeight) {
			top = topByBottom;
		} else if(preferred[0]!='bottom') {
			top = topByTop;
		} else {
			top = topByBottom;
		}
		if (rightWidth - selfWidth < 0 && leftWidth > rightWidth) {
			left = leftByLeft;
		} else if(leftWidth - selfWidth < 0 && rightWidth > leftWidth) {
			left = leftByRight;
		} else if(preferred[1]!='right') {
			left = leftByLeft;
		} else {
			left = leftByRight;
		}
		
		self.offset({left:left,top:top});
		if($.fn.bgiframe && !self.is("img")) {
			self.bgiframe();
		}
	};
	$.fn.positionSideOf = function(of, options) {
		var settings = {
			preferred : ['bottom','right'],
			spacing : 1
		};
		$.extend(settings, options);

		var ofOffset = of.offset();
		var selfWidth = this.outerWidth();
		var selfHeight = this.outerHeight();
		var topHeight = ofOffset.top - $(document).scrollTop() + of.outerHeight();	
		var bottomHeight = $(document).scrollTop() + $(window).height() - ofOffset.top;
		var leftWidth = ofOffset.left - $(document).scrollLeft();
		var rightWidth = $(document).scrollLeft() + $(window).width() - ofOffset.left - of.outerWidth();
		var topByTop = ofOffset.top + of.outerHeight() - selfHeight;
		var topByBottom = ofOffset.top;
		var leftByLeft = ofOffset.left - selfWidth - settings.spacing;
		var leftByRight = ofOffset.left + of.outerWidth() + settings.spacing;
		
		position(settings.preferred,this,selfWidth,selfHeight,topHeight,bottomHeight,leftWidth,rightWidth,topByTop,topByBottom,leftByLeft,leftByRight);
	};
	$.fn.positionOf = function(of, options) {
		var settings = {
			preferred : ['bottom','right'],
			spacing : 1
		};
		$.extend(settings, options);
		
		var ofOffset = of.offset();
		var selfWidth = this.outerWidth();
		var selfHeight = this.outerHeight();
		var leftWidth = ofOffset.left - $(document).scrollLeft() + of.outerWidth();
		var rightWidth = $(document).scrollLeft() + $(window).width() - ofOffset.left;
		var topHeight = ofOffset.top - $(document).scrollTop();	
		var bottomHeight = $(document).scrollTop() + $(window).height() - ofOffset.top - of.outerHeight();
		var topByTop = ofOffset.top - selfHeight - settings.spacing;
		var topByBottom = ofOffset.top + of.outerHeight() + settings.spacing;
		var leftByRight = ofOffset.left;
		var leftByLeft = ofOffset.left + of.outerWidth() - selfWidth;
		position(settings.preferred,this,selfWidth,selfHeight,topHeight,bottomHeight,leftWidth,rightWidth,topByTop,topByBottom,leftByLeft,leftByRight);
	};
})(jQuery);

$(function(){
	$("#nav-menu > li > a").each(function(){
		$(this).hover(function () {
		  $(this).addClass("nav-menu-hover");
    }, function () {
      $(this).removeClass("nav-menu-hover");
    });
	});
	
	$(".nav-menu-a,.sub-menu-div").each(function(){
		$(this).click(function() {
			$(this).blur();
			$("#nav-menu a").each(function() {
				$($(this).attr("menu")).removeClass("nav-menu-select");
			});
			$($(this).attr("menu")).addClass("nav-menu-select");
		});
	});
	
	var delayTime=[];	
	$("#nav-menu > li > a").each(function(index) {
		var a = $(this);
		var sm = $($(this).attr("submenu"));
		if(sm.length>0) {
			a.hover(function() {
				clearTimeout(delayTime[index]);
				sm.show();
				sm.positionOf(a,{spacing:1});
			},function() {
				delayTime[index] = setTimeout(function() {
					sm.hide();
				},100);
			});
			sm.hover(function() {
				clearTimeout(delayTime[index]);
			},function() {
				delayTime[index] = setTimeout(function() {
					sm.hide();
				},100);				
			});
			sm.find(".sub-menu-div").each(function() {
				$(this).hover(function() {
					$(this).addClass("sub-menu-hover");
				},function() {
					$(this).removeClass("sub-menu-hover");					
				});
			});
		}
	});

});	
	
	
</script>
  