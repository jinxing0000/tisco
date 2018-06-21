<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	        laydate({
	            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
					
		
		
		});
		/**
		 * 签收任务
		 */
		function claim(taskId) {
			$.get('${ctx}/act/task/claim' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	top.$.jBox.tip('签收失败');
				}
		    });
		}
	</script>
</head>
<body class="gray-bg">

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<tbody>
			<c:forEach items="${list}" var="act">
				<c:set var="task" value="${act.task}" />
				<c:set var="vars" value="${act.vars}" />
				<c:set var="procDef" value="${act.procDef}" /><%--
				<c:set var="procExecUrl" value="${act.procExecUrl}" /> --%>
				<c:set var="status" value="${act.status}" />
				<tr>
					<td>
						<div class="nameAndLeave left">
							<div class="name">
								<c:if test="${empty task.assignee}">
									<a href="javascript:claim('${task.id}');">${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)} - [${act.vars.map.applyUserId}]</a>
								</c:if>
								<c:if test="${not empty task.assignee}">
									<a href="${ctx}/act/task/form?taskId=${task.id}&taskName=${fns:urlEncode(task.name)}&taskDefKey=${task.taskDefinitionKey}&procInsId=${task.processInstanceId}&procDefId=${task.processDefinitionId}&status=${status}">
										${fns:abbr(not empty vars.map.title ? vars.map.title : task.id, 60)} - [${act.vars.map.applyUserId}]
									</a>
								</c:if>
							</div>
						</div>
					</td>
					<td style="text-align:right; font-size: 12px;"><fmt:formatDate value="${task.createTime}" pattern="yyyy-MM-dd" type="both"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
</div>
</body>
</html>