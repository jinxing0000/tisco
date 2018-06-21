<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%><%--
<html>
<head>
	<title>菜单导航</title>
	<meta name="decorator" content="blank"/>--%>
	<script type="text/javascript">
	
		/*$(document).ready(function() {
			$(".accordion-heading a").click(function(){
				$('.accordion-toggle i').removeClass('icon-chevron-down');
				$('.accordion-toggle i').addClass('icon-chevron-right');
				if(!$($(this).attr('href')).hasClass('in')){
					$(this).children('i').removeClass('icon-chevron-right');
					$(this).children('i').addClass('icon-chevron-down');
				}
			});
			$(" .menu-style").click(function(){
				$(".menu-style").removeClass('active');
				$(this).addClass('active');
			});
			
			*/
/* 			$(".accordion-body a").click(function(){
				$("#menu-${param.parentId} li").removeClass("active");
				$("#menu-${param.parentId} li i").removeClass("icon-white");
				$(this).parent().addClass("active");
				$(this).children("i").addClass("icon-white");
				//loading('正在执行，请稍等...');
			}); */
			//$(".accordion-body a:first i").click();
			//$(".accordion-body li:first li:first a:first i").click();
		// });
	</script>
<style>
.menu-style {
	height:45px;
	line-height:29px;
	cursor:pointer;
	position: relative;
	padding: 8px 10px 8px 0; 
	border-bottom: 1px solid #d8d5d5; 
	font-size: 14px;
	font-family: Microsoft YaHei;
	color: #666;
}
.menu-style .fa{
	line-height:29px;
}
.menu-style.active{
	background:#fff;
	border-bottom:0;
	border-right:2px solid #25adeb;
}
a.dropdown-toggle:hover{
	text-decoration:none;
}
.titleActive .topTitle{
	color: #00A0EA;
}

</style>
<script>
//通过遍历给菜单项加上data-index属性
$(".wuzi-menuItem").each(function (index) {
    if (!$(this).attr('data-index')) {
        $(this).attr('data-index', $(this).attr("data-flag") + index);
    }
});


	// 左侧菜单修复最后代码
	$(".accordion-body").hide();  //  页面打开时左边的子菜单隐藏
	$(".accordion-group").eq(0).find(".accordion-heading").addClass("titleActive"); // 第一个添加激活样式
	$(".accordion-group").eq(0).find(".accordion-body").show(); //第一个子菜单显示


	//右箭头修整样式
	for (var i = 0; i < $(".accordion-heading").length; i++) {
		if($(".accordion-heading").eq(i).hasClass("titleActive")){
			$(".accordion-heading").eq(i).find(".arrow").removeClass("fa-angle-right");
			$(".accordion-heading").eq(i).find(".arrow").addClass("fa-angle-down");
		}
	}

	// 点击切换子菜单效果
	$(".accordion-heading").click(function(){
		if($(this).hasClass("titleActive")){  // 隐藏
			$(".accordion-heading").removeClass("titleActive");
			$(".accordion-body").hide(); 
			// 所有的向下箭头去掉，添加向右箭头 【全关闭】
			$(".accordion-heading").find(".arrow").removeClass("fa-angle-down");
			$(".accordion-heading").find(".arrow").addClass("fa-angle-right");
			$(this).find(".arrow").removeClass("fa-angle-down");
			$(this).find(".arrow").addClass("fa-angle-right");
			// $(this).removeClass("titleActive");
			// $(this).parent().next().hide();
		}else{   // 展示
			$(".accordion-heading").removeClass("titleActive");
			$(".accordion-body").hide(); 
			$(this).addClass("titleActive");
			$(this).parent().next().show();
			// 所有的向下箭头去掉，添加向右箭头 【全关闭】
			$(".accordion-heading").find(".arrow").removeClass("fa-angle-down");
			$(".accordion-heading").find(".arrow").addClass("fa-angle-right");
			// 当前向右箭头去掉，添加向下箭头【打开】
			$(this).find(".arrow").removeClass("fa-angle-right");
			$(this).find(".arrow").addClass("fa-angle-down");
		}
		
	})

	// 收缩样式
	if($("#sidebar").hasClass("menu-min")){
	    $(".accordion-heading").removeClass("titleActive");
		$(".accordion-heading").find(".topTitle").hide();
		$(".accordion-heading").find("b").hide();
		$(".accordion-body").hide(); 
		$("#sidebar-collapse i").removeClass("fa-angle-double-left");
		$("#sidebar-collapse i").addClass("fa-angle-double-right");
		$(".accordion-heading").find(".arrow").removeClass("fa-angle-down");
		$(".accordion-heading").find(".arrow").addClass("fa-angle-right");
	}
	$("#sidebar-collapse").click(function(){
		if($(this).find("i").hasClass("fa-angle-double-left")){ // 隐藏操作
			$(".accordion-heading").removeClass("titleActive");
			$(".accordion-heading").find(".topTitle").hide();
			$(".accordion-heading").find("b").hide();
			$(".accordion-body").hide(); 
		}else{  // 展开操作
			$(".accordion-heading").find(".arrow").removeClass("fa-angle-down");
			$(".accordion-heading").find(".arrow").addClass("fa-angle-right");
			$(".accordion-heading").find(".topTitle").show();
			$(".accordion-heading").find("b").show();
		}
		
	})
	

</script>

	<div class="accordion" id="menu-${param.parentId}">
	<c:set var="menuList" value="${fns:getMenuList()}"/>
	<c:set var="firstMenu" value="true"/>
	<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
	<c:if test="${menu.parent.id eq (not empty param.parentId ? param.parentId:1)&&menu.isShow eq '1'}">
		<div class="accordion-group">
		<!--  data-toggle="collapse" -->
			<a class="dropdown-toggle" data-parent="#menu-${param.parentId}" data-href="#collapse-${menu.id}" 
		    		href="#collapse-${menu.id}" title="${menu.remarks}">
		    <div class="accordion-heading menu-style" >
		    	<span class="menu-text" style="font-size:14px;margin-left:18px;font-family: Microsoft YaHei;">
		    		<i class="menu-icon fa ${not empty menu.icon ? menu.icon : 'circle-arrow-right'}" style="margin-right:14px;"></i>
		    		<span class="topTitle">${menu.name}</span>
		    	</span>
		    	<b class="arrow fa fa-angle-right pull-right"></b>
		    </div>
		    </a>
		    <!-- collapse -->
		    <div id="collapse-${menu.id}" class="accordion-body  ${not empty firstMenu && firstMenu ? 'in' : ''}">
				<div class="accordion-inner">
					<ul class="nav nav-list">
					<c:forEach items="${menuList}" var="menu2">
					<c:if test="${menu2.parent.id eq menu.id&&menu2.isShow eq '1'}">
						<li>
						<a class="wuzi-menuItem" data-flag="${menu2.name}" href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}" target="${not empty menu2.target ? menu2.target : 'mainFrame'}" >
						<i class="menu-icon fa ${not empty menu2.icon ? menu2.icon : 'circle-arrow-right'}"></i>&nbsp;
							<span class="menu-text">${menu2.name}</span>
						</a>
							<ul class="nav nav-list hide submenu" style="margin:0;padding-right:0;">
								<c:forEach items="${menuList}" var="menu3">
								<c:if test="${menu3.parent.id eq menu2.id&&menu3.isShow eq '1'}">
									<li class="menu3-${menu2.id} hide">
										<a class="wuzi-menuItem" data-flag="${menu3.name}" href="${fn:indexOf(menu3.href, '://') eq -1 ? ctx : ''}${not empty menu3.href ? menu3.href : '/404'}" >
										<i class="menu-icon fa ${not empty menu3.icon ? menu3.icon : 'circle-arrow-right'}"></i>
										<span class="menu-text">${menu3.name}</span>
										
										</a>
									</li></c:if>
								</c:forEach>
							</ul>
						</li>
						<c:set var="firstMenu" value="false"/>
					</c:if>
					</c:forEach>
					</ul>
				</div>
		    </div>
		</div>
	</c:if>
	</c:forEach>
	</div>

