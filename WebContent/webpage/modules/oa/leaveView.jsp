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
		<h5>请假详情</h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
	<div class="ibox-content">
	<form:form id="inputForm" modelAttribute="leave" action="${ctx}/oa/leave/saveAudit" method="post" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">请假类型：</label>
			<div class="controls">
					${fns:getDictLabel(leave.leaveType, 'oa_leave_type', '无')}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">申请人：</label>
			<div class="controls">
				${leave.applicant.name}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<fmt:formatDate value="${leave.startTime}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<fmt:formatDate value="${leave.endTime}" pattern="yyyy-MM-dd"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假原因：</label>
			<div class="controls">
				${leave.reason}
			</div>
		</div>
		
		<div class="control-group office-leader">
			<label class="control-label">科室负责人：</label>
			<div class="controls">
				${leave.officeLeader.name}
			</div>
		</div>
		
		<div class="control-group dept-leader">
			<label class="control-label">部门负责人：</label>
			<div class="controls">
				${leave.deptLeader.name}
			</div>
		</div>
	
		<div class="form-actions">
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

