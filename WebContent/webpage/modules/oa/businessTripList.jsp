<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>出差申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			laydate({
	            elem: '#startDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
			laydate({
	            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>出差申请列表 </h5>
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
	<form:form id="searchForm" modelAttribute="businessTrip" action="${ctx}/businesstrip/businessTrip/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>所在部门：</span>
				<sys:treeselect id="department" name="department" value="${businessTrip.department}" labelName="" labelValue="${businessTrip.}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>出差人：</span>
				<sys:treeselect id="applicant" name="applicant" value="${businessTrip.applicant}" labelName="" labelValue="${businessTrip.}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>目的地：</span>
				<sys:treeselect id="destAddr" name="destAddr" value="${businessTrip.destAddr}" labelName="" labelValue="${businessTrip.}"
					title="区域" url="/sys/area/treeData" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
			<span>出差开始日期：</span>
				<input id="startDate" name="startDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${businessTrip.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<span>出差结束日期：</span>
				<input id="endDate" name="endDate" type="text" maxlength="20" class="laydate-icon form-control layer-date input-sm"
					value="<fmt:formatDate value="${businessTrip.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="businesstrip:businessTrip:add">
				<table:addRow url="${ctx}/businesstrip/businessTrip/form" title="出差申请"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="businesstrip:businessTrip:edit">
			    <table:editRow url="${ctx}/businesstrip/businessTrip/form" title="出差申请" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="businesstrip:businessTrip:del">
				<table:delRow url="${ctx}/businesstrip/businessTrip/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="businesstrip:businessTrip:import">
				<table:importExcel url="${ctx}/businesstrip/businessTrip/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="businesstrip:businessTrip:export">
	       		<table:exportExcel url="${ctx}/businesstrip/businessTrip/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column ">所在部门</th>
				<th  class="sort-column ">出差人</th>
				<th  class="sort-column ">目的地</th>
				<th  class="sort-column startDate">出差开始日期</th>
				<th  class="sort-column endDate">出差结束日期</th>
				<th  class="sort-column reason">出差事由</th>
				<th  class="sort-column transport">出行交通工具</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="businessTrip">
			<tr>
				<td> <input type="checkbox" id="${businessTrip.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看出差申请', '${ctx}/businesstrip/businessTrip/form?id=${businessTrip.id}','800px', '500px')">
					${businessTrip.}
				</a></td>
				<td>
					${businessTrip.}
				</td>
				<td>
					${businessTrip.}
				</td>
				<td>
					<fmt:formatDate value="${businessTrip.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${businessTrip.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${businessTrip.reason}
				</td>
				<td>
					${fns:getDictLabel(businessTrip.transport, '', '')}
				</td>
				<td>
					<shiro:hasPermission name="businesstrip:businessTrip:view">
						<a href="#" onclick="openDialogView('查看出差申请', '${ctx}/businesstrip/businessTrip/form?id=${businessTrip.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="businesstrip:businessTrip:edit">
    					<a href="#" onclick="openDialog('修改出差申请', '${ctx}/businesstrip/businessTrip/form?id=${businessTrip.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="businesstrip:businessTrip:del">
						<a href="${ctx}/businesstrip/businessTrip/delete?id=${businessTrip.id}" onclick="return confirmx('确认要删除该出差申请吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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