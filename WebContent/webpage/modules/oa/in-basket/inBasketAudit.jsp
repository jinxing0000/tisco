<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>收文管理</title>
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
			
			if (taskDefKey === undefined || taskDefKey === "") {
				$('.boss-audit').show();
			} else if (taskDefKey == "bossAudit"){
				$('.director-audit').show();
			} else if (taskDefKey == "deptAudit") {
				$('.leader-audit').show();
			} else if (taskDefKey == "officeAudit") {
				$('.office-audit').show();
			}
		}
		$(document).ready(function() {
			$(".boss-audit, .director-audit, .leader-audit, .office-audit").hide();
			
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
			            elem: '#receiptDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="ibox">
		<div class="ibox-title">
		<h5>当前步骤--[${inBasket.act.taskName}] </h5>
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
		<form:form id="inputForm" modelAttribute="inBasket" action="${ctx}/oa/inBasket/saveAudit" method="post" class="form-horizontal">
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
					<td class="width-15 active"><label class="pull-right">文件编号：</label></td>
					<td class="width-35">
						<form:input path="fileNum" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">收文日期：</label></td>
					<td class="width-35">
						<input id="receiptDate" name="receiptDate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${inBasket.receiptDate}" pattern="yyyy-MM-dd"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">发文单位：</label></td>
					<td class="width-35">
						<form:input path="dispatchFileUnit" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">文件号：</label></td>
					<td class="width-35">
						<form:input path="docNum" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">主要内容：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="content" htmlEscape="false" rows="4" maxlength="500" class="form-control "/>
					</td>
				</tr>
				<tr class="boss-audit">
					<td class="width-15 active"><label class="pull-right">主管领导：</label></td>
					<td class="width-35" colspan="3">
						<sys:treeselect id="boss" name="boss.id" value="${inBasket.boss.id}" labelName="boss.name" labelValue="${inBasket.boss.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr class="director-audit">
					<td class="width-15 active"><label class="pull-right">部门副主任：</label></td>
					<td class="width-35" colspan="3">
						<sys:treeselect id="deptDirector" name="directorAuditersIds" value="${inBasket.directorAuditersIds}" labelName="directorAuditersNames" labelValue="${inBasket.directorAuditersNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " checked="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr class="leader-audit">
					<td class="width-15 active"><label class="pull-right">科室负责人：</label></td>
					<td class="width-35" colspan="3">
						<sys:treeselect id="deptLeader" name="leaderAuditersIds" value="${inBasket.leaderAuditersIds}" labelName="leaderAuditersNames" labelValue="${inBasket.leaderAuditersNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " checked="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr class="office-audit">
					<td class="width-15 active"><label class="pull-right">文件接收人：</label></td>
					<td class="width-35" colspan="3">
						<sys:treeselect id="recipitentsId" name="fileRecipientIds" value="${inBasket.fileRecipientIds}" labelName="fileRecipientNames" labelValue="${inBasket.fileRecipientNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " checked="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">附件：</label></td>
					<td class="width-35" colspan="3">
						<form:hidden id="attachment" path="attachment" htmlEscape="false" maxlength="64" class="form-control"/>
						<sys:ckfinder input="attachment" type="files" uploadPath="/in-basket/inBasket" selectMultiple="true"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="form-control required" rows="5" maxlength="20"/>
			</div>
		</div>
		
		<div class="form-actions">
				<c:if test="${dispatchFile.act.taskDefKey ne 'end'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
		<act:flowChart procInsId="${inBasket.act.procInsId}"/>
		<act:histoicFlow procInsId="${inBasket.act.procInsId}"/>
	</form:form>
</body>
</html>