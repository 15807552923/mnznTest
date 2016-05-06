<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>mnzn_add</title>
</head>
<body>
	<%@ include file="./left.jsp"%>
<link href='${ctx}/static/ztree/zTreeStyle/zTreeStyle.css' rel='stylesheet'/>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" href="${ctx}/static/ztree/demo.css" type="text/css">

	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="#">Home</a> <span class="divider">/</span></li>
				<li><a href="#">${op=='edit' ? '更新' : '新增'}</a></li>
			</ul>
		</div>



		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title="">
					<h2>
						<i class="icon-edit"></i>${op=='edit' ? '更新角色' : '新增角色'}
					</h2>
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						<a href="#" class="btn btn-close btn-round"><i
							class="icon-remove"></i></a>
					</div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" action="${ctx}/role/${op=='edit' ? 'update' : 'save'}.mn" method="post">
					    <input type="hidden" name="site" value="1" />
					    <input type="hidden" name="id" value="${role.id}">
						<fieldset>
							<div class="control-group">
								<label class="control-label" for="focusedInput">名称</label>
								<div class="controls">
									<input class="input-xlarge focused" id="name" name="name"
										value="${role.name }" type="text">
								</div>
							</div>
							
							
							<div class="control-group">
								<label class="control-label" for="focusedInput">描述</label>
								<div class="controls">
									<input class="input-xlarge focused" id="description" name="description"
										value="${role.description }" type="text">
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label" for="focusedInput">是否拥有所有权限</label>
								<div class="controls">
								    <label class="checkbox inline">
									<div id="uniform-inlineCheckbox1" class="checker"><span class=""> <input style="opacity: 0;" type="checkbox" id="allPerm" name="allPerm" value="0" default="false" onclick="checkDis(this);"/></span></div>所有权限
								  </label>
								    
								</div>
							</div>
							
						
							
							<div class="control-group">
								<label class="control-label" for="focusedInput">权限</label>
								<div class="controls">
									<!-- <input class="input-xlarge focused" id="perms" name="perms"  value="" type="text"> -->
								<div class="content_wrap">
									<div class="zTreeDemoBackground left">
										<ul class="list">
											<li class="title"> <input id="perms" name="perms" type="text" readonly value="${role.perms}" style="width:120px;" onclick="showMenu();" />
										</ul>
									</div>
								</div>

								<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
									<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
								</div>

								</div>
							</div>


							<div class="form-actions">
								<button type="submit" class="btn btn-primary">Save changes</button>
								<button type="button" class="btn" id="cancel">Cancel</button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<!--/row-->


	</div>


	<SCRIPT type="text/javascript">
	
	
	
		var setting = {
			check: {
				enable: true,
				chkboxType: {"Y" : "ps", "N" : "ps"}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onCheck: onCheck
			}
		};

		var zNodes =[
			{id:1, pId:0, name:"首页登录" ,value:"core:homePage:index"},
			{id:2, pId:0, name:"角色列表",value:"core:role:list"},
			{id:3, pId:0, name:"角色增加",value:"core:role:add"},
			{id:4, pId:0, name:"商家管理", open:true,value:""},
			{id:41, pId:4, name:"洗衣项目",value:"pro:ecode:index"},
			{id:42, pId:4, name:"经销管理",value:"pro:dealer:list"},
			 {id:421, pId:42, name:"经销商角色",value:"pro:role:dealer"},
			 {id:422, pId:42, name:"运营商角色",value:"pro:role:operator"},
			 {id:423, pId:42, name:"子运营商角色",value:"pro:role:merchant"},
			{id:44, pId:4, name:"支付模块查看",value:"pro:box:list"},
			{id:45,pId:4,name:"消费统计",value:"pro:wash:consume"},
			{id:5, pId:0, name:"财务管理",value:"ecode:bill:account"},
			{id:51, pId:5, name:"结账权限",value:"ecode:bill:account"},
			{id:52, pId:5, name:"转账权限",value:"ecode:bill:transferList"},
			{id:53, pId:5, name:"转账详细查询",value:"ecode:bill:transferDetail"},
			{id:54, pId:5, name:"提现处理",value:"ecode:bill:withdrawal"},
			{id:6, pId:0, name:"客服管理",value:"ecode:cusSer:index"},
			{id:61, pId:6, name:"客服管理",value:"ecode:cusSer:list"},
		 ];

		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].value + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#perms");
			cityObj.attr("value", v);
		}

		function showMenu() {
			var cityObj = $("#perms");
			var cityOffset = $("#perms").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "perms" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		 function filter(node) {
	            return node;
	        }
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		    var nodes = zTree.getNodesByFilter(filter);
		    var perms = $("#perms").val();
			for (var i=0, l=nodes.length; i<l; i++) {
				 var node = nodes[i];
				 if(perms.indexOf(node.value)>= 0){
					 node.checked = true; //表示显示checkbox
		             zTree.updateNode(node);
				 }
	             
			}
			var isAdmin = ${role.allPerm==true?true:false};
			
			if(isAdmin == null || isAdmin ==''){
				isAdmin = false;
			}
			if(isAdmin){
				$("#uniform-inlineCheckbox1 span").addClass("checked");
				$("#allPerm").attr("checked","checked");
				$("#allPerm").val(true);
				
			}else{
				$("#uniform-inlineCheckbox1 span").removeClass("checked");
				$("#allPerm").attr("default","false");
				$("#allPerm").attr("checked","false");
				$("#allPerm").val(false);
			}
			$('#perms').prop('disabled',isAdmin);
			
			$("#cancel").click(function(){
				history.go(-1);
			})
		});
		function checkDis(obj){
			
			$('#perms').prop('disabled',obj.checked);
			$("#allPerm").val(obj.checked);
			alert($("#allPerm").val());
		}
	</SCRIPT>

</body>
</html>