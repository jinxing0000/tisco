<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>图片新闻管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	 <link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	 <script src="${ctxStatic}/summernote/summernote.min.js"></script>
	 <script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
	 
	<!-- uploadify上传组件 -->
	<link href="${ctxStatic}/fileupload/uploadify/uploadify.css" type="text/css" rel="stylesheet"/>
	<script src="${ctxStatic}/fileupload/uploadify/uploadify.js" type="text/javascript"></script>
	<script src="${ctxStatic}/fileupload/uploadify/swfobject.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctxStatic}/fileupload/ext/uploader.js"></script>
	
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
			  $("#news_content").val($("#rich_content").next().find(".note-editable").html());//取富文本的值
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		
		$(document).ready(function() {
			$('#btnSubmit').click(function() {
				doSubmit();
			});
			
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
			$('#rich_content').summernote({
                lang: 'zh-CN'
            });

			$("#rich_content").next().find(".note-editable").html(  $("#news_content").val());

			$("#rich_content").next().find(".note-editable").html(  $("#rich_content").next().find(".note-editable").text());
			
			//上传附件
			new Uploader({
				element:'#attachments0',
				tag:'0',
				sessionId:'<%=session.getId()%>',
				idx:'5'
			});
		});
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>图片新闻发布 </h5>
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
										<div class="cancel">
											<a href="javascript:jQuery('#attachments0').uploadifyCancel('${file.id}')">
											x
											</a>								
										</div>		
										<span class="fileName">
										<%-- <a href="${ctx}/policyInfo/download.do?id=${file.id}">${file.name}</a> --%>
										<a href="${ctx}/view?id=${file.id}" target="_blank">${file.name}</a>
										</span>
									</div>
									</c:if>
								</c:forEach>
								
								<input type="file" id="attachments0"/>
								<div id="files0"></div>			
				</div>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="发 布"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	</div>
	</div>
	</div>
</body>
</html>