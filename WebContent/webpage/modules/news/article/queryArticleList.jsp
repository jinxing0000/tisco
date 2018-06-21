<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function modifyArticle(title,url,width,height,target){
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: ['提交', '关闭'],
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
		         
		        if(iframeWin.contentWindow.doSubmit('4') ){
		        	  setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
		         }
				
			  },
			cancel: function(index){ 
				  
		    }
		}); 	
		
	}
	
	function queryPic(title,url,width,height,target){
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: [ '关闭'],
			cancel: function(index){ 
				  
		    }
		}); 	
		
	}
	
	function queryVideo(title,url,width,height,target){
		top.layer.open({
		    type: 2,  
		    area: [width, height],
		    title: title,
	        maxmin: true, //开启最大化最小化按钮
		    content: url ,
		    btn: [ '关闭'],
			cancel: function(index){ 
				  
		    }
		}); 	
		
	}
	
		$(document).ready(function() {
			$('.do-cancel, .do-collecting').hide();
			
			$('#doCollecting').click(function() {
				var newsId = $(this).attr("news-id");
				var newsUrl = $(this).attr("news-url");
				var data = {
					newsId : newsId,
					newsUrl : newsUrl
				}
				
				$.post(
					"${ctx}/news/favorite/toCollect",
					data,
					function(result) {
						if (result == "true") {
							alert("收藏成功");
							//sortOrRefresh();
						}
					},
					"json"
				);
			});
			
			$('#doCancel').click(function() {
				var newsId = $(this).attr("news-id");
				
				var data = {newsId : newsId};
				
				$.post("${ctx}/news/favorite/cancel", data, function(result) {
					if (result == "true") {
						alert("取消收藏成功");
						//sortOrRefresh();
					}
				}, 'json');
			});
		});
		var newsObj;
		var newsList=new Array();
		
		//点击复选框
		function queryClick(title,newId){
			if(newsList.length==0){
				newsObj=new Object();
				newsObj.title=title;
				newsObj.newId=newId;
				newsList[newsList.length]=newsObj;
			}else{
				var count=0;
				for(var i=0;i<newsList.length;i++){
					if(newId==newsList[i].newId){
						count++;
					}
				}
				if(count==0){
					newsObj=new Object();
					newsObj.title=title;
					newsObj.newId=newId;
					newsList[newsList.length]=newsObj;
				}
			}
			var htl="";
			for(var i=0;i<newsList.length;i++){
				htl+="<p onclick=\"delNew('"+newsList[i].newId+"')\">"+newsList[i].title+"</p>";
			}
			$("#queryList").html(htl);
		}
		//删除选中的
		function delNew(newId){
			for(var i=0;i<newsList.length;i++){
				if(newId==newsList[i].newId){
					newsList.splice(i,1);
				}
			}
			var htl="";
			for(var i=0;i<newsList.length;i++){
				htl+="<p onclick=\"delNew('"+newsList[i].newId+"')\">"+newsList[i].title+"</p>";
			}
			$("#queryList").html(htl);
		}
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
	<div id="queryList" style="color: red;">
	    
	</div>
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/news/article/queryList" method="post" class="form-inline">
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
			<shiro:hasPermission name="news:article:edit">
			   <!-- <table:editRow url="${ctx}/news/article/form" title="文章" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="news:article:delete">
				<table:delRow url="${ctx}/news/article/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="news:article:import">
				<table:importExcel url="${ctx}/news/article/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="news:article:export">
	       		<table:exportExcel url="${ctx}/news/article/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th style="width:10px;"> <input type="checkbox" class="i-checks"></th>
				<th style="width:40%;">标题</th>
				<th style="width:20%;">栏目</th>
				<th style="width:10%;">是否推荐</th>
				<th style="width:10%;">内容类型</th>
				<th style="width:15%;">发布时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td> <input type="checkbox" id="${article.id}"  onclick="queryClick('${article.title}','${article.id}')"></td>
				<td>
					<a href="#" onclick="openDialogView('查看文章', '${ctx}/news/article/view?id=${article.id}','800px', '500px')"
						data-toggle="tooltip" data-placement="top" title="${article.title}">
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
					${fns:getDictLabel(article.contentType,'article_content_type','')}
				</td>
				<td>
					<fmt:formatDate value="${article.issuedDate}" pattern="yyyy-MM-dd"/>
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