<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>请假申请</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			
			$("#name").focus();
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			laydate({
	            elem: '#applyDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>当前步骤--[${officialSeal.act.taskName}] </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
	<div class="ibox-content">
	<form:form id="inputForm" modelAttribute="officialSeal" action="${ctx}/oa/officialSeal/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>

		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>需盖公章文件名称：</label></td>
					<td class="width-35">
						${officialSeal.fileName}
					</td>
					
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>文件数量：</label></td>
					<td class="width-35">
						${officialSeal.count}
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>申请人：</label></td>
					<td class="width-35">
						${officialSeal.applicant.name}
					</td>
					
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>申请日期：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${officialSeal.applyDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>主要内容：</label></td>
					<td class="width-35" colspan="3">
						${officialSeal.mainContent}
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="control-group dept-leader">
			<label class="control-label"><font color="red">*</font>部门负责人：</label>
			<div class="controls">
				${officialSeal.deptLeader.name}
			</div>
		</div>
		
		<div class="control-group charge-leader">
			<label class="control-label"><font color="red">*</font>经办人：</label>
			<div class="controls">
				${officialSeal.agentLeader.name}
			</div>
		</div>
		
		<div class="control-group boss-leader">
			<label class="control-label"><font color="red">*</font>中心领导：</label>
			<div class="controls">
				${officialSeal.bossLeader.name}
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:flowChart procInsId="${officialSeal.act.procInsId}"/>
		<act:histoicFlow procInsId="${officialSeal.act.procInsId}"/>
	</form:form>
</div>
	</div>
	</div>
</body>
</html>

