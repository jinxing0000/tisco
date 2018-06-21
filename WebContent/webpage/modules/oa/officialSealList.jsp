<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>公章申请管理</title>
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
		<h5>公章申请列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
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
	<form:form id="searchForm" modelAttribute="officialSeal" action="${ctx}/officialseal/officialSeal/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="officialseal:officialSeal:add">
				<table:addRow url="${ctx}/officialseal/officialSeal/form" title="公章申请"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="officialseal:officialSeal:edit">
			    <table:editRow url="${ctx}/officialseal/officialSeal/form" title="公章申请" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="officialseal:officialSeal:del">
				<table:delRow url="${ctx}/officialseal/officialSeal/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="officialseal:officialSeal:import">
				<table:importExcel url="${ctx}/officialseal/officialSeal/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="officialseal:officialSeal:export">
	       		<table:exportExcel url="${ctx}/officialseal/officialSeal/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column fileName">需盖公章文件名称</th>
				<th  class="sort-column count">文件数量</th>
				<th  class="sort-column mainContent">主要内容</th>
				<th  class="sort-column ">公章使用人</th>
				<th  class="sort-column applyDate">申请日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="officialSeal">
			<tr>
				<td> <input type="checkbox" id="${officialSeal.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看公章申请', '${ctx}/officialseal/officialSeal/form?id=${officialSeal.id}','800px', '500px')">
					${officialSeal.fileName}
				</a></td>
				<td>
					${officialSeal.count}
				</td>
				<td>
					${fns:unescapeHtml(officialSeal.mainContent)}
				</td>
				<td>
					${officialSeal.}
				</td>
				<td>
					${officialSeal.applyDate}
				</td>
				<td>
					<shiro:hasPermission name="officialseal:officialSeal:view">
						<a href="#" onclick="openDialogView('查看公章申请', '${ctx}/officialseal/officialSeal/form?id=${officialSeal.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="officialseal:officialSeal:edit">
    					<a href="#" onclick="openDialog('修改公章申请', '${ctx}/officialseal/officialSeal/form?id=${officialSeal.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="officialseal:officialSeal:del">
						<a href="${ctx}/officialseal/officialSeal/delete?id=${officialSeal.id}" onclick="return confirmx('确认要删除该公章申请吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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