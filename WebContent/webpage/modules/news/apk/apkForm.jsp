<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>新增app版本信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/fileupload/ajaxfileupload.js"></script>
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
	<script type="text/javascript">
    function upload(){
    	var urlt="uploadApk";
        $.ajaxFileUpload({
   	url : urlt,
   	secureuri : false,
   	fileElementId : 'uploadApk',
   	dataType : 'JSON',
   	contentType : "application/x-www-form-urlencoded; charset=utf-8",
   	modal : true,
   	success : function(data) {
   		var json = eval('(' + data.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre>','') + ')');
   		if(json[0]=='success'){
   			$("#apkFile").val(json[2]);
   			$("#apkPath").val(json[1]);
   		}else{
   			alert(json[1]);
   		}
   	},
   	error : function(data, statue, e) {
   		alert('上传失败！！');
   	}
   });
    }
</script>
</head>
<body>
	<div class="wrapper wrapper-content">
		<form:form id="inputForm" modelAttribute="apk" action="${ctx}/news/apk/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="apkPath" id="apkPath"/>
		<sys:message content="${message}"/>	
		
		<div class="control-group">
			<label class="control-label">版本名称：</label>
			<div class="controls">
				<form:input path="apkName" htmlEscape="false" maxlength="64" class="form-control required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">版本号：</label>
			<div class="controls">
 				<form:input path="apkVersion" htmlEscape="false"  maxlength="10" class="form-control required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本提示语：</label>
			<div class="controls">
				<form:input path="apkMessage" htmlEscape="false" maxlength="50" class="form-control "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">apk文件名：</label>
			<div class="controls">
				<input type="text" id="apkFile" class="form-control " readonly="readonly"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上传apk：</label>
			<div class="controls">
				<input type="file" id="uploadApk" name="uploadApk" readonly="readonly"/><button onclick="upload()" type="button">上传</button>
			</div>
		</div>
	</form:form>
	</div>
</body>
</html>