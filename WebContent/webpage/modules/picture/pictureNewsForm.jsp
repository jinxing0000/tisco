<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>图片新闻管理</title>
	<meta name="decorator" content="default"/>
	<style>
	  .uploadifyQueueItem {
		background-color: #F5F5F5;
		border: 1px solid #E5E5E5;
		margin: 5px 0 5px 0;
		padding: 3px;
		width: 250px; 
		}
	</style>
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
		<form:form id="inputForm" modelAttribute="pictureNews" action="${ctx}/news/picture/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="form-control "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">来源：</label>
			<div class="controls">
				<form:input path="origin" htmlEscape="false" maxlength="64" class="form-control "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">发布人：</label>
			<div class="controls">
				<sys:treeselect id="issuer" name="issuer.id" value="${pictureNews.issuer.id}" labelName="issuer.name" labelValue="${pictureNews.issuer.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control " allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">摘要：</label>
			<div class="controls">
				<form:textarea path="summary" rows="4" htmlEscape="false" class="form-control"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:hidden id="news_content" path="content"/>
				<div id="rich_content">
                           

                </div>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
 				<div id="fileQueue0">
					<c:forEach var="file" items="${files}">
								  <c:if test="${file.tag eq '0'}">
									<div id="attachments${file.id}" class="uploadifyQueueItem completed">
										<span class="fileName">
										<%-- <a href="${ctx}/policyInfo/download.do?id=${file.id}">${file.name}</a> --%>
										<a href="${ctx}/view?id=${file.id}" target="_blank">${file.name}</a>
										</span>
									</div>
									</c:if>
								</c:forEach>
								
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>