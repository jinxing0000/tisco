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
		
		function init() {
			var taskDefKey = "${taskDefKey}";
			
			$('.dept-leader').show();
			if (taskDefKey !== null && taskDefKey !== "") {
				if (taskDefKey == "deptLeaderAudit") {
					$('.charge-leader').show();
				}
				
				if (taskDefKey == "agentAudit" || taskDefKey == "bossAudit") {
					$('.charge-leader, .boss-leader').show();
				}
			}
		}
		
		$(document).ready(function() {
			$('.dept-leader, .charge-leader, .boss-leader').hide();
			init();
			
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
						<form:input path="fileName" htmlEscape="false" maxlength="100" class="form-control input-sm required"/>
					</td>
					
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>文件数量：</label></td>
					<td class="width-35">
						<form:input path="count" htmlEscape="false" class="form-control input-sm number required"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>申请人：</label></td>
					<td class="width-35">
						<sys:treeselect id="applicant" name="applicant.id" value="${officialSeal.applicant.id}" labelName="applicant.name" labelValue="${officialSeal.applicant.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>申请日期：</label></td>
					<td class="width-35">
						<input id="applyDate" name="applyDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${officialSeal.applyDate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>主要内容：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="mainContent" htmlEscape="false" rows="4" maxlength="255" class="form-control required" style="width:600px;" />
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="control-group dept-leader">
			<label class="control-label"><font color="red">*</font>部门负责人：</label>
			<div class="controls">
				<sys:treeselect id="deptLeader" name="deptLeader.id" value="${officialSeal.deptLeader.id}" 
					labelName="deptLeader.name" labelValue="${officialSeal.deptLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group charge-leader">
			<label class="control-label"><font color="red">*</font>经办人：</label>
			<div class="controls">
				<sys:treeselect id="agentLeader" name="agentLeader.id" value="${officialSeal.agentLeader.id}" 
					labelName="agentLeader.name" labelValue="${officialSeal.agentLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group boss-leader">
			<label class="control-label"><font color="red">*</font>中心领导：</label>
			<div class="controls">
				<sys:treeselect id="bossLeader" name="bossLeader.id" value="${officialSeal.bossLeader.id}" 
					labelName="bossLeader.name" labelValue="${officialSeal.bossLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="form-control required" rows="5" maxlength="20"/>
			</div>
		</div>
	
		<div class="form-actions">
				<c:if test="${officialSeal.act.taskDefKey ne 'endEvent'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
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

