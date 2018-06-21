<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
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
			$("#oldPassword").focus();
			
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
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
		<h5>修改密码 </h5>
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
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modify/passwd"  method="post" class="form-horizontal form-group">
		<form:hidden path="id" value="${user.id}" />
		<form:hidden path="password" value="${user.password}" />
		
		<div class="control-group">
			<label class="col-sm-3 control-label"><font color="red">*</font>旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3"  class="form-control  max-width-250 required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="col-sm-3 control-label"><font color="red">*</font>新密码:</label>
			<div class="controls">
				<form:password id="newPassword" path="newPassword" value="" maxlength="50" class="form-control  max-width-250 required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" class="form-control  max-width-250 required" equalTo="#newPassword"></input>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="issueSubmit" class="btn btn-success" type="button" value="修 改" onclick="doSubmit()"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	</div>
	</div>
	</div>
</body>
</html>