<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>发文管理</title>
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
			alert(taskDefKey);
			if (taskDefKey !== null && taskDefKey !== "") {
				if (taskDefKey == "bossAudit") {
					$('.boss-audit').show();
				} else {
					$('.boss-audit').hide();
				}
			}
		}
		
		$(document).ready(function() {
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
			            elem: '#closureDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body >
	<div class="wrapper wrapper-content">
	<div class="ibox">
		<div class="ibox-title">
			<h5>当前步骤--[${dispatchFile.act.taskName}] </h5>
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
	
		<form:form id="inputForm" modelAttribute="dispatchFile" action="${ctx}/oa/dispatchFile/saveAudit" method="post" class="form-horizontal">
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
					<td class="width-15 active"><label class="pull-right">主办单位：</label></td>
					<td class="width-35">
						<form:input path="hostUnit" value="${dispatchFile.hostUnit}" htmlEscape="false" maxlength="64" class="form-control " />
					</td>
					<td class="width-15 active"><label class="pull-right">拟稿人：</label></td>
					<td class="width-35">
					<sys:treeselect id="draftUint" name="draftUint.id" value="${dispatchFile.draftUint.id}" labelName="draftUint.name" labelValue="${dispatchFile.draftUint.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">核稿人：</label></td>
					<td class="width-35">
					<sys:treeselect id="reviewer" name="reviewer.id" value="${dispatchFile.reviewer.id}" labelName="reviewer.name" labelValue="${dispatchFile.reviewer.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">会签人员：</label></td>
					<td colspan="3">
						<sys:treeselect id="recipients" name="docRecipientIds" value="${dispatchFile.docRecipientIds}" 
							labelName="docRecipientNames" labelValue="${dispatchFile.docRecipientNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-select required" 
							checked="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">签发人：</label></td>
					<td class="width-35 qian-fa-user" colspan="3">
						<sys:treeselect id="issuer" name="issuer.id" value="${dispatchFile.issuer.id}" labelName="issuer.name" labelValue="${dispatchFile.issuer.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr class="boss-audit">
					<td class="width-15 active" ><label class="pull-right">文件接收人：</label></td>
					<td class="width-35">
						<sys:treeselect id="recipitentsId" name="fileRecipientIds" value="${dispatchFile.fileRecipientIds}" labelName="fileRecipientNames" labelValue="${dispatchFile.fileRecipientNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " checked="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">事由：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="reason" htmlEscape="false" rows="5" maxlength="500" class="form-control " style="width:600px;"/>
					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">主送单位：</label></td>
					<td class="width-35">
						<form:input path="senderUnit" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">抄送单位：</label></td>
					<td class="width-35">
						<form:input path="ccUnit" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">秘密等级：</label></td>
					<td class="width-35">
						<form:select path="secretLevel" class="form-control ">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('act_secret_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">紧急程度：</label></td>
					<td class="width-35">
						<form:select path="urgenceLevel" class="form-control ">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('act_urgence_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">发文字号：</label></td>
					<td class="width-35">
						<form:input path="issuedNum" htmlEscape="false" maxlength="64" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">封发日期：</label></td>
					<td class="width-35">
						<input id="closureDate" name="closureDate" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${dispatchFile.closureDate}" pattern="yyyy-MM-dd"/>"/>
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
		
		<act:flowChart procInsId="${dispatchFile.act.procInsId}"/>
		<act:histoicFlow procInsId="${dispatchFile.act.procInsId}"/>
	</form:form>
	</div>
	</div>
</body>
</html>