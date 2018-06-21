<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	 <link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	 <link href="${ctxStatic}/common/css/rich-content.css" type="text/css" rel="stylesheet" />
	 <script src="${ctxStatic}/summernote/summernote.min.js"></script>
	 <script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
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
			//$("#name").focus();
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
	
			$("#rich_content").next().find(".note-editable").html(  $("#content").val());
	
			$("#rich_content").next().find(".note-editable").html(  $("#rich_content").next().find(".note-editable").text());
		});
	</script>
	<style type="text/css">
		.input-width {
			width: 350px;
		}
		.input-select {
			width: 120px;
		}
		.td-width {
			width: 100px;
		}
	</style>
</head>
<body>

	<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" 
		class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="td-width active">	
		         	<label class="pull-right"><font color="red">*</font>标题：</label>
		         </td>
		         <td>
		         	<form:input path="title" htmlEscape="false" maxlength="200" class="form-control input-width required" />
		         </td>
		      </tr>
		      <tr>
		      	 <td  class="td-width active">	<label class="pull-right"><font color="red">*</font>类型：</label></td>
		         <td><form:select path="type" class="form-control input-select required">
					<form:option value="" label="请选择类型"/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>
		       </tr>
		      
		       <tr>
		         <td  class="td-width active">	<label class="pull-right"><font color="red">*</font>内容：</label></td>
		         <td >
		         	<form:hidden path="content"/>
					<div id="rich_content" class="note-editor note-editable">
                           

                    </div>
		         </td>
		       </tr>
		       
		       <tr>
		           <td  class="td-width active">	<label class="pull-right">附件：</label></td>
		           <td >
			         <c:if test="${oaNotify.status ne '1'}">
						<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true"/>
					</c:if>
			         <c:if test="${oaNotify.status eq '1'}">
						<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="files" type="files" uploadPath="/oa/notify" selectMultiple="true" readonly="true" />
			         </c:if>
		           </td>
		        </tr>
		      
		      	 	<tr>
				         <td  class="td-width active">	<label class="pull-right"><font color="red">*</font>状态：</label></td>
				         <td><form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" 
				         		itemValue="value" htmlEscape="false" class="i-checks required" />
				         </td>
				     </tr>
				
				<c:if test="${oaNotify.status ne '1'}">
					<tr>
					 <td  class="td-width active">	
					 	<label class="pull-right"><font color="red">*</font>接收人：</label>
					 </td>
				     <td>
				     	<sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" 
				     		labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-select required" 
							notAllowSelectParent="true" checked="true"/>
						</td>
		      		</tr>
				</c:if>
			
					<c:if test="${oaNotify.status eq '1'}">
					  <tr>
				         <td  class="td-width active">	<label class="pull-right">接收人：</label></td>
				         <td><table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								<thead>
									<tr>
										<th>接收人</th>
										<th>接收部门</th>
										<th>阅读状态</th>
										<th>阅读时间</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
									<tr>
										<td>
											${oaNotifyRecord.user.name}
										</td>
										<td>
											${oaNotifyRecord.user.office.name}
										</td>
										<td>
											${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
										</td>
										<td>
											<fmt:formatDate value="${oaNotifyRecord.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							已查阅：${oaNotify.readNum} &nbsp; 未查阅：${oaNotify.unReadNum} &nbsp; 总共：${oaNotify.readNum + oaNotify.unReadNum}</td>
				      </tr>
				</c:if>
			</tbody>
		</table>
		     
		

	</form:form>
</body>
</html>