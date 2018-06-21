<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>评论管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(state){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			$('#commentStatus').val(state);
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
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
			
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
	</div>
	<form:form id="inputForm" modelAttribute="auditComment" action="${ctx}/cms/audit/comment/save" method="post" class="form-horizontal">
		<form:hidden path="cmsId" value="${newsComment.id}" />
		<form:hidden path="comments" value="${newsComment.comment}" />
		<form:hidden id="commentStatus" path="result" />
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				<form:textarea path="comments" rows="4" htmlEscape="false" class="form-control"/>
			</div>
		</div>
<!-- 		<div class="form-actions">
			<input id="issueSubmit" class="btn btn-success" type="button" value="通 过" onclick="doSubmit('1')"/>&nbsp;
			<input id="draftSubmit" class="btn btn-primary" type="submit" value="驳 回" onclick="doSubmit('2')"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> -->
	</form:form>
	</div>
	</div>
</body>
</html>