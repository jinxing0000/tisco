<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>发文管理</title>
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
		<h5>发文列表 </h5>
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
	<form:form id="searchForm" modelAttribute="dispatchFile" action="${ctx}/oa/dispatchFile/" method="post" class="form-inline">
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
			<shiro:hasPermission name="oa:dispatchFile:add">
				<table:addRow url="${ctx}/oa/dispatchFile/form" title="发文"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:dispatchFile:edit">
			    <table:editRow url="${ctx}/oa/dispatchFile/form" title="发文" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:dispatchFile:del">
				<table:delRow url="${ctx}/oa/dispatchFile/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:dispatchFile:import">
				<table:importExcel url="${ctx}/oa/dispatchFile/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="oa:dispatchFile:export">
	       		<table:exportExcel url="${ctx}/oa/dispatchFile/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column ">主办单位</th>
				<th  class="sort-column ">拟稿人</th>
				<th  class="sort-column ">核稿人</th>
				<th  class="sort-column ">签发人</th>
				<th  class="sort-column reason">事由</th>
				<th  class="sort-column senderUnit">主送单位</th>
				<th  class="sort-column ccUnit">抄送单位</th>
				<th  class="sort-column secretLevel">秘密等级</th>
				<th  class="sort-column urgenceLevel">紧急程度</th>
				<th  class="sort-column issuedNum">发文字号</th>
				<th  class="sort-column closureDate">封发日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dispatchFile">
			<tr>
				<td> <input type="checkbox" id="${dispatchFile.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看发文', '${ctx}/oa/dispatchFile/form?id=${dispatchFile.id}','800px', '500px')">
					${dispatchFile.}
				</a></td>
				<td>
					${dispatchFile.}
				</td>
				<td>
					${dispatchFile.}
				</td>
				<td>
					${dispatchFile.}
				</td>
				<td>
					${dispatchFile.reason}
				</td>
				<td>
					${dispatchFile.senderUnit}
				</td>
				<td>
					${dispatchFile.ccUnit}
				</td>
				<td>
					${fns:getDictLabel(dispatchFile.secretLevel, '', '')}
				</td>
				<td>
					${fns:getDictLabel(dispatchFile.urgenceLevel, '', '')}
				</td>
				<td>
					${dispatchFile.issuedNum}
				</td>
				<td>
					${dispatchFile.closureDate}
				</td>
				<td>
					<shiro:hasPermission name="oa:dispatchFile:view">
						<a href="#" onclick="openDialogView('查看发文', '${ctx}/oa/dispatchFile/form?id=${dispatchFile.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="oa:dispatchFile:edit">
    					<a href="#" onclick="openDialog('修改发文', '${ctx}/oa/dispatchFile/form?id=${dispatchFile.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="oa:dispatchFile:del">
						<a href="${ctx}/oa/dispatchFile/delete?id=${dispatchFile.id}" onclick="return confirmx('确认要删除该发文吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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