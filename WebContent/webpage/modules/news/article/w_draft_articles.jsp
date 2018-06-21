<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function deleteAll(){

		// var url = $(this).attr('data-url');
		  var str="";
		  var ids="";
		  $("#contentTable tbody tr td input.i-checks:checkbox").each(function(){
		    if(true == $(this).is(':checked')){
		      str+=$(this).attr("id")+",";
		    }
		  });
		  if(str.substr(str.length-1)== ','){
		    ids = str.substr(0,str.length-1);
		  }
		  if(ids == ""){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
			top.layer.confirm('确认要彻底删除数据吗?', {icon: 3, title:'系统提示'}, function(index){
			window.location = "${ctx}/news/article/deleteAll?ids="+ ids + "&flag=draft";
		    top.layer.close(index);
		});
		 

	}
	
	function modifyArticle(title,url,width,height,target){
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: ['提交', '保存草稿', '关闭'],
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
		         
		        if(iframeWin.contentWindow.doSubmit('0') ){
		        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
		        }
		    },
			cancel: function(index){ 
				  
		    }
		}); 	
		
	}
	
		$(document).ready(function() {
		    $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		    	  $('#contentTable tbody tr td input.i-checks').iCheck('check');
		    	});

		    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		    	  $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
		    	});
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>文章列表 </h5>
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
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/news/article/draft/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>标题：</span>
				<form:input path="title" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>新闻栏目：</span>
				<form:select path="type.key" class="form-control ">
					<form:option value="" label="请选择栏目"/>
					<form:options items="${newsType}" itemLabel="name" itemValue="key" htmlEscape="false"/>
				</form:select>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="news:article:del">
				<button class="btn btn-danger btn-sm" onclick="deleteAll()" data-toggle="tooltip" data-placement="top">
				<i class="fa fa-trash-o">批量删除</i>
				</button>
			</shiro:hasPermission>
		
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
				<th style="width:10px;"> <input type="checkbox" class="i-checks"></th>
				<th style="width:40%;">标题</th>
				<th style="width:10%;">栏目</th>
				<th style="width:10%;">是否推荐</th>
				<th style="width:10%;">发布时间</th>
				<th style="width:10%;">状态</th>
				<th style="width:25%;">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td> <input type="checkbox" id="${article.id}" class="i-checks"></td>
				<td>
					<a  href="#" onclick="openDialogView('查看文章', '${ctx}/news/article/view?id=${article.id}','800px', '500px')">
						${fns:abbr(article.title, 40)}
					</a>
				</td>
				<td>
					${fns:getDictLabel(article.type.key,'news_content_type','无')}
				</td>
				<td>
					${fns:getDictLabel(article.isTopline,'news_topline','未知')}
				</td>
				<td>
					<fmt:formatDate value="${article.issuedDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(article.status,'news_issue_status','无')}
				</td>
				<td>
					<shiro:hasPermission name="news:article:view">
						<a href="#" onclick="openDialogView('查看文章', '${ctx}/news/article/view?id=${article.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="news:article:edit">
						<%-- <a class="btn btn-success btn-xs" href="${ctx}/news/article/form?id=${article.id}" target="mainFrame">
							<i class="fa fa-edit"></i> 修改
						</a> --%>
    					<a href="#" onclick="modifyArticle('修改文章', '${ctx}/news/article/form?id=${article.id}&flag=draft','960px', '550px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="news:article:del">
						<a href="${ctx}/news/article/delete?id=${article.id}&flag=draft" onclick="return confirmx('确认要删除该文章吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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