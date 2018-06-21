<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>待审核新闻列表</title>
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
			<c:forEach items="${page.list}" var="article">
				<tr>
					<td style="width:65%;">
					<a  href="#" onclick="openDialogView('查看文章', '${ctx}/news/article/view?id=${article.id}','800px', '500px')">
						<span style="font-size:12px;color:#666666;">[${article.type.name}]</span>${article.title}
					</a>
					</td>
					<td>
						${article.issuer.name}
					</td>
					<td>
						<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>