<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>推送记录</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>推送记录列表 </h5>
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
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="apk" action="${ctx}/news/message/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="news:apk:add">
				<!--<table:addRow url="${ctx}/news/apk/form" title="apk版本"></table:addRow> 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="news:apk:del">
				<!--<table:delRow url="${ctx}/news/apk/deleteAll" id="contentTable"></table:delRow> 删除按钮 -->
			</shiro:hasPermission>
			</div>
		
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th style="width:10px;"> <input type="checkbox" class="i-checks"></th>
				<th  >新闻标题</th>
				<th  >新闻类型</th>
				<th  >推送时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="message">
			<tr>
				<td> <input type="checkbox" id="${message.id}" class="i-checks" style="width:20px;"></td>
				<td>
					${message.title}
				</td>
				<td>
					${fns:getDictLabel(message.newsType,'news_content_type','无')}
				</td>
				<td>
					<fmt:formatDate value="${message.createDate}" type="date" dateStyle="default"/>
				</td>
				<td>
    				<shiro:hasPermission name="news:apk:del">
						<!-- <a href="${ctx}/news/apk/delete?id=${apk.id}" onclick="return confirmx('确认要删除该app版本吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a> -->
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>