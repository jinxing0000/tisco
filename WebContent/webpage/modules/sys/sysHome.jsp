<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	
	<meta charset="UTF-8">
	<title>首页</title>

	<link type="text/css" href="${ctxStatic}/common/css/home.css" rel="stylesheet">
	<link href="${ctxStatic}/leaflet/leaflet.css" rel="stylesheet" />
	<link href="${ctxStatic}/leaflet/L.Control.MousePosition.css" rel="stylesheet" />
	<link href="${ctxStatic}/leaflet/Leaflet.label/leaflet.label.css" rel="stylesheet" />
	<!-- EasyUI css -->
	<link href="${ctxStatic}/assent/easyui/themes/gray/easyui.css" rel="stylesheet" />

	<!-- Main js -->
	<script src="${ctxStatic}/assent/js/jquery-2.1.1.min.js"></script>
	<!-- Leaflet js -->
	<script src="${ctxStatic}/leaflet/leaflet-src.js"></script>
	<script src="${ctxStatic}/leaflet/leaflet.polylineDecorator.js"></script>
	<script src="${ctxStatic}/leaflet/L.Control.MousePosition.js"></script>
	<script src="${ctxStatic}/leaflet/Leaflet.label/leaflet.label-src.js"></script>
	<!-- EasyUI js -->
	<script src="${ctxStatic}/assent/easyui/jquery.easyui.min.js"></script>
	<script src="${ctxStatic}/assent/easyui/locale/easyui-lang-zh_CN.js"></script>
	<!-- Bootstrap js -->
	<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<!-- Map js --> 
	<script src="${ctxStatic}/assent/js/map.js"></script>
	<script src="${ctxStatic}/assent/js/utility.js"></script>

	<!-- <script type="text/javascript">
		$(document).ready(function() {
		     WinMove();
		});
	</script> -->
	
</head>


<body id="body" class="gray-bg">
	<sys:message content="${message}"/>	
	<div id="section">
		<ul class="count">
				<li class="count_1">
					<div class="middle">
						<div><img src="${ctxStatic}/common/img/home/quantity.png" /></div>
						<div>
							<p class="quantity" style="color:#6f63b5">${issuedArticleNum}<span>篇</span></p>
							<p class="times">发布文章数</p>
						</div>
					</div>
				</li>
				<li class="count_2">
					<div class="middle">
						<div><img src="${ctxStatic}/common/img/home/times-1.png" /></div>
						<div>
							<p class="quantity" style="color:#6ed2b9">${inAuditingArticleNum}<span>篇</span></p>
							<p class="times">待审核文章数</p>
						</div>
					</div>
				</li>
				<li class="count_3">
					<div class="middle">
						<div><img src="${ctxStatic}/common/img/home/times-2.png" /></div>
						<div>
							<p class="quantity" style="color:#ff634c">${appUserNum}<span>位</span></p>
							<p class="times">APP用户数</p>
						</div>
					</div>
				</li>
				<li class="count_4">
					<div class="middle">
						<div><img src="${ctxStatic}/common/img/home/times-3.png" /></div>
						<div>
							<p class="quantity" style="color:#ffb844">${appUserNum}<span>位</span></p>
							<p class="times">APP新用户</p>
						</div>
					</div>
				</li>
			</ul>
	
		<div id="main_d">
				<div class="main_left_1">
					<div class="m">
						<p class="news">新闻简报</p>
						<p class="more">更多</p>
						<select class="select">
							<option>所有类型</option>
							<option>所有类型</option>
						</select>
					</div>
					<div class="test test2">
						<iframe name="mini-todo-frame" width="100%" height="100%" src="${ctx}/news/article/mini/list?status=4" frameborder="0" seamless></iframe>
					</div>
				</div>
				<div class="main_right_1">
					<div class="m">
						<p class="news">待审核文章</p>
						<p class="more">更多</p>
					</div>
					<div class="test test2">
						<div>
							<iframe name="mini-todo-frame" width="100%" height="100%" src="${ctx}/news/article/mini/list?status=1" frameborder="0" seamless></iframe>
						</div>
					</div>
				</div>
				
			</div>
			
			
		</div>

	<div class="wrapper wrapper-content">
        <div class="row">
        </div>
    </div>

		

</body>
	<script>

	</script>
</html>	