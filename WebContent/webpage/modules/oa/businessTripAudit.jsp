<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>出差申请管理</title>
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
				
				if (taskDefKey == "chargeLeaderAudit") {
					$('.charge-leader, .boss-leader').show();
				}
				
				if (taskDefKey == "bossAudit") {
					$('.charge-leader, .boss-leader').show();
				}
			}
		}
		
		$(document).ready(function() {
			$('.dept-leader, .charge-leader, .boss-leader').hide();
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
		<h5>当前步骤--[${businessTrip.act.taskName}] </h5>
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
		<form:form id="inputForm" modelAttribute="businessTrip" action="${ctx}/oa/businessTrip/saveAudit" method="post" class="form-horizontal">
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
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>出差人：</label></td>
					<td class="width-35">
						<sys:treeselect id="applicant" name="applicant.id" value="${businessTrip.applicant.id}" labelName="applicant.name" labelValue="${businessTrip.applicant.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true" disabled="disabled"/>
					</td>
					
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所在部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="department" name="department.id" value="${businessTrip.department.id}" labelName="department.name" labelValue="${businessTrip.department.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true" disabled="disabled"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>目的地：</label></td>
					<td class="width-35">
						<sys:treeselect id="destAddr" name="destAddr.id" value="${businessTrip.destAddr.id}" labelName="destAddr.name" labelValue="${businessTrip.destAddr.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>出行工具：</label></td>
					<td class="width-35">
						<form:select path="transport" class="form-control required" style="width:200px;">
							<form:option value="" label="请选择出行工具"/>
							<form:options items="${fns:getDictList('business_trip_transport')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>出差日期：</label></td>
					<td class="width-35" colspan="3">
						<input id="startDate" name="startDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${businessTrip.startDate}" pattern="yyyy-MM-dd"/>"/>
						&nbsp;--&nbsp;
						<input id="endDate" name="endDate" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${businessTrip.endDate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>出差事由：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="255" class="form-control required" style="width:600px;" />
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="control-group dept-leader">
			<label class="control-label"><font color="red">*</font>部门负责人：</label>
			<div class="controls">
				<sys:treeselect id="deptLeader" name="deptLeader.id" value="${businessTrip.deptLeader.id}" 
					labelName="deptLeader.name" labelValue="${businessTrip.deptLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" disabled="disabled" />
			</div>
		</div>
		
		<div class="control-group charge-leader">
			<label class="control-label"><font color="red">*</font>分管领导：</label>
			<div class="controls">
				<sys:treeselect id="chargeLeader" name="chargeLeader.id" value="${businessTrip.chargeLeader.id}" 
					labelName="chargeLeader.name" labelValue="${businessTrip.chargeLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group boss-leader">
			<label class="control-label"><font color="red">*</font>中心领导：</label>
			<div class="controls">
				<sys:treeselect id="bossLeader" name="bossLeader.id" value="${businessTrip.bossLeader.id}" 
					labelName="bossLeader.name" labelValue="${businessTrip.bossLeader.name}"
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
				<c:if test="${businessTrip.act.taskDefKey ne 'end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:flowChart procInsId="${businessTrip.act.procInsId}"/>
		<act:histoicFlow procInsId="${businessTrip.act.procInsId}"/>
	</form:form>
</body>
</html>