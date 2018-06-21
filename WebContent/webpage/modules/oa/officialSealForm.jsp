<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>公章申请管理</title>
	<meta name="decorator" content="default"/>
	 <link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	 <link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	 <script src="${ctxStatic}/summernote/summernote.min.js"></script>
	 <script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#mainContent").val($("#rich_mainContent").next().find(".note-editable").html());//取富文本的值
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
				
				if (taskDefKey == "agentLeaderAudit") {
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
			
				//富文本初始化
			$('#rich_mainContent').summernote({
                lang: 'zh-CN'
            });

			$("#rich_mainContent").next().find(".note-editable").html(  $("#mainContent").val());

			$("#rich_mainContent").next().find(".note-editable").html(  $("#rich_mainContent").next().find(".note-editable").text());
					laydate({
			            elem: '#applyDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>公章使用申请 </h5>
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
		<form:form id="inputForm" modelAttribute="officialSeal" action="${ctx}/oa/officialSeal/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
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
				<sys:treeselect id="deptLeader" name="deptLeader.id" value="${businessTrip.deptLeader.id}" 
					labelName="deptLeader.name" labelValue="${businessTrip.deptLeader.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" 
					allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group charge-leader">
			<label class="control-label"><font color="red">*</font>经办人：</label>
			<div class="controls">
				<sys:treeselect id="agentLeader" name="agentLeader.id" value="${businessTrip.agentLeader.id}" 
					labelName="agentLeader.name" labelValue="${businessTrip.agentLeader.name}"
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
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="申 请"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	</div>
</body>
</html>