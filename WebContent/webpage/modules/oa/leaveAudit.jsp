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
			if (taskDefKey !== null && taskDefKey !== "") {
				if (taskDefKey == "officeAudit") {
					$('.dept-leader').show();
				}
				
				if (taskDefKey == "deptAudit") {
					$(".office-leader, .dept-leader").show();
				}
			} else {
					$(".office-leader").show();
			}
		}
		
		$(document).ready(function() {
			$('.dept-leader').hide();
			
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
	            elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>当前步骤--[${leave.act.taskName}] </h5>
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
	<form:form id="inputForm" modelAttribute="leave" action="${ctx}/oa/leave/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">请假类型：</label>
			<div class="controls">
				<form:select path="leaveType"  cssClass="form-control input-sm" >
					<form:options items="${fns:getDictList('oa_leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">申请人：</label>
			<div class="controls">
				<sys:treeselect id="applicant" name="applicant.id" value="${leave.applicant.id}" labelName="applicant.name" 
					labelValue="${leave.applicant.name}" title="用户" url="/sys/office/treeData?type=3" 
					cssClass="form-control required" allowClear="true" notAllowSelectParent="true" disabled="disabled" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">请假时间：</label>
			<div class="controls">
				<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" 
					class="laydate-icon form-control layer-date required"/>
				&nbsp;--&nbsp;
				<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" 
					class="laydate-icon form-control layer-date required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">请假原因：</label>
			<div class="controls">
				<form:textarea path="reason" class="form-control required" rows="5" maxlength="20"/>
			</div>
		</div>
		
		<div class="control-group office-leader">
			<label class="control-label">科室负责人：</label>
			<div class="controls">
				<sys:treeselect id="officeLeader" name="officeLeader.id" value="${leave.officeLeader.id}" labelName="officeLeader.name" 
					labelValue="${leave.officeLeader.name}" title="用户" url="/sys/office/treeData?type=3" 
					cssClass="form-control required" allowClear="true" notAllowSelectParent="true" disabled="disabled" />
			</div>
		</div>
		
		<div class="control-group dept-leader">
			<label class="control-label">部门负责人：</label>
			<div class="controls">
				<sys:treeselect id="deptLeader" name="deptLeader.id" value="${leave.deptLeader.id}" labelName="deptLeader.name" 
					labelValue="${leave.deptLeader.name}" title="用户" url="/sys/office/treeData?type=3" 
					cssClass="form-control required" allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="form-control required" rows="5" maxlength="20"/>
			</div>
		</div>
	
		<div class="form-actions">
				<c:if test="${leave.act.taskDefKey ne 'apply_end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:flowChart procInsId="${leave.act.procInsId}"/>
		<act:histoicFlow procInsId="${leave.act.procInsId}"/>
	</form:form>
</div>
	</div>
	</div>
</body>
</html>

