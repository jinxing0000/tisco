<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>太钢新闻发布管理系统</title>

	<%@ include file="/webpage/include/acehead.jsp"%>
	<script src="${ctxStatic}/common/inspinia-ace.js?v=3.2.0"></script>
	<script src="${ctxStatic}/common/contabs.js"></script> 
	<style>
		.navbar .nav>li {
		    float: left;
		}
		.nav-collapse{
		 width:800px;
		}
		.menu span{
			  font-family: "Microsoft YaHei";
		      color: white;
		      font-size:16px;
		      line-height:100%;
		}
		
		.menu>i {
			color: white;
			font-size:16px;
		}
	    /*表格奇偶行变色*/
	    #listForm table tbody tr:nth-child(even){
	    	background-color: #f5f5f5;
	    }
	    /*左侧顶部头部隐藏*/
	    #sidebar-shortcuts{ display: none; }
	    .menu-text{ font-size: 14px; color: #666; font-family: "Microsoft YaHei"; }
	</style>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$("#shou-ye, #main-container").hide();
		
		$('li.menu').each(function() {
			if ($(this).hasClass('active')) {
				$('#shou-ye').show();
			}
		});
		
		$('.user-info').click(function() {
			if ($('#shou-ye').css('display') == "block") {
				$('.shouye-frame').removeAttr("src").attr("src", $(this).attr("href"));
			}
		});
		
		$(".menu").click(function(){
			
			$(".menu").removeClass('active');
			$(this).addClass('active');
			
			if ($(this).attr("data-id") === "9321fb7350bb44e0a21703cea225e97b") {
				$('.shouye-frame').removeAttr("src").attr("src", "${ctx}/home");
				$('#shou-ye').show();
				$('#main-container').hide();
			} else {
				$('.user-info').addClass('wuzi-menuItem');
				$('#main-container').show();
				$('#shou-ye').hide();
			}
			
			var menuId = "#menu-" + $(this).attr("data-id");
			$(menuId + ".accordion-group:first a:first i").click();
			$(menuId + ".accordion-group:first .accordion-inner li:first a:first i").click();
		});
		
	});
	</script>
</head>

<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="#" class="navbar-brand">
						<small>
							<img src="${ctxStatic }/common/login/images/wuzi_logo.png" />
							太钢新闻客户端发布管理系统
						</small>
					</a>
				</div>
				<style>
					@media screen and (max-width: 1280px) {
						#RightAdmin{ display: none; }
					}
					@media screen and (max-width: 1150px) {
						#menu{ display: none; }
					}
				</style>
				<div class="navbar-buttons navbar-header" role="navigation" style="margin-left:0px;">
					<ul id="menu" class="nav " style="*white-space:nowrap;float:none;">
						<c:set var="firstMenu" value="true"/>
						<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
								<li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
									<c:if test="${empty menu.href}">
										<a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}">
										<i class="menu-icon fa ${not empty menu.icon ? menu.icon : 'circle-arrow-right'}"></i>&nbsp;
										<span>${menu.name}</span>
										</a>
									</c:if>
									<c:if test="${not empty menu.href}">
										<a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame">
										<span>${menu.name}</span></a>
									</c:if>
								</li>
								<c:if test="${firstMenu}">
									<c:set var="firstMenuId" value="${menu.id}"/>
								</c:if>
								<c:set var="firstMenu" value="false"/>
							</c:if>
						</c:forEach>
					</ul>
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation" style="width:170px;" id="RightAdmin">
					<ul class="nav ace-nav">

						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${fns:getUser().photo}" />
								<span>
									${fns:getUser().name}
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>
							
							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a class="user-info " href="${ctx}/sys/user/imageEdit" target="mainFrame">
										<i class="ace-icon fa fa-cog"></i>
										修改头像
									</a>
								</li>
								
								<li>
									<a href="${ctx}/sys/user/modify/show" class="user-info" target="mainFrame">
										<i class="ace-icon fa fa-wrench"></i>
										修改密码
									</a>
								</li>

								<li>
									<a class="user-info " href="${ctx }/sys/user/info" target="mainFrame">
										<i class="ace-icon fa fa-user"></i>
										个人资料
									</a>
								</li>
							</ul>
							
						</li>

						<a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i></a>

						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!-- /.sidebar-shortcuts -->

					<%-- 	 <t:aceMenu  menu="${fns:getTopMenu()}"></t:aceMenu>--%>
				<div id="content" class="row-fluid">
					<div id="left">
					</div>
					<div id="openClose" class="close">&nbsp;</div>
<!-- 					<div id="right">
						<iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
					</div> -->
				</div>
					
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
				  <div class="content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                         <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose"  data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <%-- <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a> --%>
            	</div>
					</div>

					<div class="J_mainContent"  id="content-main">
		             <iframe class="mainFrame" name="mainFrame" width="100%" height="105%"  src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
		            </div>
	            </div>
            
            
            </div>
            
            <div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<span class="bigger-120">
							Copyright &copy; 太原钢铁（集团）有限公司
						</span>

					</div>

					<!-- /section:basics/footer -->
				</div>
			</div>
			
            </div>
            
            <div id="shou-ye" class="main-container">
            	<iframe class="shouye-frame" name="shouye-frame" width="100%" height="105%"  src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            
            	            <div class="footer">
				<div class="footer-inner">
					<!-- #section:basics/footer -->
					<div class="footer-content">
						<span class="bigger-120">
							Copyright &copy; 太原钢铁（集团）有限公司
						</span>

					</div>

					<!-- /section:basics/footer -->
				</div>
			</div>
            </div>
            
            
            
				

	</body>
	<!-- 语言切换插件，为国际化功能预留插件 -->
	<script type="text/javascript">
	
	$(document).ready(function(){
	
		$("a.lang-select").click(function(){
			$(".lang-selected").find(".lang-flag").attr("src",$(this).find(".lang-flag").attr("src"));
			$(".lang-selected").find(".lang-flag").attr("alt",$(this).find(".lang-flag").attr("alt"));
			$(".lang-selected").find(".lang-id").text($(this).find(".lang-id").text());
			$(".lang-selected").find(".lang-name").text($(this).find(".lang-name").text());
	
		});
	
	});

	
	function changeStyle(){
		   $.get('${pageContext.request.contextPath}/theme/default?url='+window.top.location.href,function(result){ window.location.reload();  });
		  

		}
	</script>
	
<link href="${ctxStatic}/layer-v2.3/layim/layim.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript">
		var currentId = '${fns:getUser().loginName}';
		var currentName = '${fns:getUser().name}';
		var currentFace ='${fns:getUser().photo}';
		var url="${ctx}";
		var wsServer = 'ws://'+window.document.domain+':8668';
		
	
	</script>
     <script src="${ctxStatic}/layer-v2.3/layim/layer.min.js"></script>
   <%-- <script src="${ctxStatic}/layer-v2.3/layim/layim.js"></script>--%>

</html>