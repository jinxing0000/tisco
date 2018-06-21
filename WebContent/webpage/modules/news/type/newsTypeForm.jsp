<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>新闻类型管理</title>
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
	<div class="ibox-content">
		<form:form id="inputForm" modelAttribute="newsType" action="${ctx}/news/type/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<div class="control-group">
			<label class="control-label">类型名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="form-control "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">英文名称：</label>
			<div class="controls">
				<form:input path="key" htmlEscape="false" maxlength="64" class="form-control "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">简称：</label>
			<div class="controls">
				<form:input path="shortName" htmlEscape="false" maxlength="64" class="form-control "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">权重（排序）：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="64" class="form-control "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
			</div>
		</div>
	</form:form>
	</div>
	</div>
	</div>
</body>
</html>