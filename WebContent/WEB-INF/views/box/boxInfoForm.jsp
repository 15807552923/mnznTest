<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.tuil.*"%>
<%@page import="com.mnzn.ecode.core.entity.BoxInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>mnzn_add</title>
</head>
<body>
	<%@ include file="../ecode/left.jsp"%>
<link href='${ctx}/static/ztree/zTreeStyle/zTreeStyle.css' rel='stylesheet'/>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.excheck.js"></script>
<link rel="stylesheet" href="${ctx}/static/ztree/demo.css" type="text/css">

	<div id="content" class="span10">
		<div>
			<ul class="breadcrumb">
				<li><a href="#">Home</a> <span class="divider">/</span></li>
				<li><a href="#">${op=='edit' ? '更新支付模块' : '新增支付模块'}</a></li>
			</ul>
		</div>



		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title="">
					<h2>
						<i class="icon-edit"></i>${op=='edit' ? '更新支付模块' : '新增支付模块'}
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
					<form class="form-horizontal" action="${ctx}/box/${op=='edit' ? 'update' : 'save'}.mn" method="post">
						<fieldset>
							<div class="control-group">
								<label class="control-label" for="deviceno">支付模块编号<br /></label>
								<div class="controls">
									<textarea rows="10" class="input-xxlarge" name="deviceno" >${boxInfo.deviceno}</textarea>
									<font color=red>多个模块编号之间用空格隔开</font>
								</div>
								<!-- /controls -->
							</div>
							<!-- /control-group -->

							<div class="control-group">
								<label class="control-label" for="address">地址</label>
								<div class="controls">
									<input type="text" class="input-medium" name="address" value="${boxInfo.address}" />
								</div>
								<!-- /controls -->
							</div>
							<!-- /control-group -->

							<div class="control-group">
								<label class="control-label" for="devicetype">设备类型</label>
								<div class="controls">
									<input type="radio" id="radio1" name="devicetype" value="0"
										checked="checked" onchange="dtchange()" /> 洗衣机 <input
										type="radio" name="devicetype" value="1" onchange="dtchange()" />
									时间控电器
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label" for="devicetype">优惠价格</label>
								<div class="controls">
									 <div class="oz-form-fields" >  
								        <table cellpadding="0" cellspacing="0" style="width:50%" id="optionContainer">  
								           <c:forEach var="item" items="${boxInfo.customs}" varStatus="s">
								               <tr id="option${s.index}" >
								                    <td class="oz-form-topLabel">类型:<input type="text" name="custom_key${s.index}" style="width:50px" value="${item.key }"></td>
								                    <td class="oz-property" >价格:<input type="text" name="custom_value${s.index}" style="width:50px" value="${item.value }"></td>
								                    <td><a href="#" onclick="delRow('${s.index}')">删除</a></td>
								               </tr>
								           </c:forEach>
								        </table>  
								        <div style="text-align: center;">  
								            <a href="#" onclick="addRow()">添加一行</a>  
								        </div>  
								    </div>      
								</div>
							</div>
							
							<div class="form-actions">
								<button type="submit" class="btn btn-primary">保存</button> 
								<button type="button" onclick="javascript:history.go(-1);" class="btn">取消</button>
								<input type="hidden" name="status" value="0" />
								<input type="hidden" name="companyid" value="${companyId}" />
								<input type= "hidden" name="customIds" id="customIds" value="${customIds}">
							</div> <!-- /form-actions -->

						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<!--/row-->


	</div>

<script type="text/javascript">

var rowCount= 0; 

$(function(){
	var c = '${rowCount}' ;
	if( c!= null && c.trim()!=""){
      rowCount= parseInt(c); 
	}
	$("#customIds").val('${customIds}');
	var devicetype = '${boxInfo.devicetype}'
	if(devicetype!=null){
		$("input[name='devicetype']").each(function(){
			if($(this).val()==devicetype){
				$(this).attr("checked","checked");
			}
			
		});
	}
})

//添加行  
function addRow(){  
  rowCount++;  
  var newRow='<tr id="option'+rowCount+'"><td class="oz-form-topLabel">类型:<input type="text" name="custom_key'+rowCount+'" style="width:50px"></td><td class="oz-property" >价格:<input type="text" name="custom_value'+rowCount+'" style="width:50px"></td><td><a href="#" onclick=delRow('+rowCount+')>删除</a></td></tr>';  
  $('#optionContainer').append(newRow);  
  var temp = $("#customIds").val();
  $("#customIds").val(temp+rowCount+"#");
}  

//删除行  
function delRow(rowIndex){  
  $("#option"+rowIndex).remove();
  var temp = $("#customIds").val();
  $("#customIds").val(temp.replace(rowIndex+"#",""));
} 


</script>

</body>
</html>