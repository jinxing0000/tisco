<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>车辆申请管理</title>
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
				if ((taskDefKey == "deptLeaderAudit") || (taskDefKey == "executiveAudit")) {
					$('.executive-leader').show();
				}
			}
		}
		$(document).ready(function() {
			$('.dept-leader, .executive-leader').hide();
			init();
			
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
					laydate({
			            elem: '#startDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
					laydate({
			            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>车辆使用申请 </h5>
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
		<form:form id="inputForm" modelAttribute="vehicleApply" action="${ctx}/oa/vehicleApply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>车辆使用人：</label></td>
					<td class="width-35">
						<sys:treeselect id="applicant" name="applicant.id" value="${vehicleApply.applicant.id}" 
							labelName="applicant.name" labelValue="${vehicleApply.applicant.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
							allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所在部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${vehicleApply.office.id}" 
							labelName="office.name" labelValue="${vehicleApply.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" 
							allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>申请日期：</label></td>
					<td class="width-35" colspan="3">
						<input id="applyDate" name="applyDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${vehicleApply.applyDate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>申请日期：</label></td>
					<td class="width-35" colspan="3">
						<input id="startDate" name="startDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${vehicleApply.startDate}" pattern="yyyy-MM-dd HH:mm"/>"/>
						&nbsp;--&nbsp;
						<input id="endDate" name="endDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${vehicleApply.endDate}" pattern="yyyy-MM-dd HH:mm"/>"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>用车事由：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="255" class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="control-group dept-leader">
			<label class="control-label"><font color="red">*</font>部门负责人：</label>
			<div class="controls">
				<sys:treeselect id="deptLeader" name="deptLeader.id" value="${vehicleApply.deptLeader.id}" 
					labelName="deptLeader.name" labelValue="${vehicleApply.deptLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group executive-leader">
			<label class="control-label"><font color="red">*</font>行政负责人：</label>
			<div class="controls">
				<sys:treeselect id="executiveLeader" name="executiveLeader.id" value="${vehicleApply.executiveLeader.id}" 
					labelName="executiveLeader.name" labelValue="${vehicleApply.executiveLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="申 请"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	</div>
</body>
</html>