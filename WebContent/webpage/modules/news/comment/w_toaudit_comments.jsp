<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>评论管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function modifyArticle(title,url,width,height,target){
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: ['同意', '驳回', '关闭'],
		    yes: function(index, layero){
				 var body = top.layer.getChildFrame('body', index);
		         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         
		         var inputForm = body.find('#inputForm');
		         var top_iframe;
		         if(target){
		        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
		         }else{
		        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
		         }
		         
		         
		         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
		         
		        if(iframeWin.contentWindow.doSubmit('1') ){
		        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
		         }
				
			  },
		    btn2: function(index, layero) {
				 var body = top.layer.getChildFrame('body', index);
		         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         var inputForm = body.find('#inputForm');
		         
		         var top_iframe;
		         if(target){
		        	 top_iframe = target;//如果指定了iframe，则在改frame中跳转
		         }else{
		        	 top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
		         }
		         	
		         
		         inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
		         
		        if(iframeWin.contentWindow.doSubmit('2') ){
		        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
		        }
		    },
			cancel: function(index){ 
				  
		    }
		}); 	
		
	}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>待审核评论列表 </h5>
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
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="comment:tb_news_comment:add">
				<table:addRow url="${ctx}/news/comment/form" title="评论"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="comment:tb_news_comment:edit">
			    <table:editRow url="${ctx}/news/comment/form" title="评论" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="comment:tb_news_comment:del">
				<table:delRow url="${ctx}/news/comment/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="comment:tb_news_comment:import">
				<table:importExcel url="${ctx}/news/comment/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="comment:tb_news_comment:export">
	       		<table:exportExcel url="${ctx}/news/comment/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	<form:form id="searchForm" modelAttribute="newsComment" action="${ctx}/news/comment/toaudit/list" method="post" class="form-inline">
	      <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		  <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
	</form:form>
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th style="width:10px;"> <input type="checkbox" class="i-checks"></th>
				<th style="width:25%;">新闻标题</th>
				<th style="width:35%;">评论内容</th>
				<th style="width:10%;">评论人</th>
				<th style="width:8%;">是否匿名</th>
				<th style="width:8%;">审核状态</th>
				<th style="width:15%;">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="newsComment">
			<tr>
				<td> <input type="checkbox" id="${tb_news_comment.id}" class="i-checks"></td>
				<td>
					<a href="#" title="${newsComment.news.title}">
						${fns:abbr(newsComment.news.title, 40)}
					</a>
				</td>
				<td>
					<a  href="#" onclick="openDialogView('查看评论', '${ctx}/news/comment/form?id=${tb_news_comment.id}','800px', '500px')" title="${newsComment.comment}">
					${fns:abbr(newsComment.comment, 40)}
					</a>
				</td>
				<td>
					${newsComment.commentator.name}
				</td>
				<td>
					${fns:getDictLabel(newsComment.isAnon,'news_comment_isanon','无')}
				</td>
				<td>
					${fns:getDictLabel(newsComment.status,'news_comment_status','无')}
				</td>
				<td>
    					<a href="#" onclick="modifyArticle('审核评论', '${ctx}/cms/audit/comment/${newsComment.id}','800px', '350px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>审核</a>
						<a href="${ctx}/news/comment/delete?id=${newsComment.id}" onclick="return confirmx('确认要删除该评论吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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