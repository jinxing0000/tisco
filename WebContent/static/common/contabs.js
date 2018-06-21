
$(function () {
	
/*	$('.J_menuItem').on('click', menuItem);*/
	
	$('.wuzi-menuItem').on('click', menuItem);
	
	$('.J_menuTabs').on('click', '.J_menuTab i', closeTab);
	
	$('.J_tabCloseOther').on('click', closeOtherTabs);
	
	$('.J_tabShowActive').on('click', showActiveTab);
	
	$('.J_menuTabs').on('click', '.J_menuTab', activeTab);

	$('.J_menuTabs').on('dblclick', '.J_menuTab', refreshTab);

	// 左移按扭
	$('.J_tabLeft').on('click', scrollTabLeft);

	// 右移按扭
	$('.J_tabRight').on('click', scrollTabRight);
	
	// 关闭全部
	$('.J_tabCloseAll').on('click', function () {
	    $('.page-tabs-content').children("[data-id]").not(":first").each(function () {
	        $('.mainFrame[data-id="' + $(this).data('id') + '"]').remove();
	        $(this).remove();
	    });
	    $('.page-tabs-content').children("[data-id]:first").each(function () {
	        $('.mainFrame[data-id="' + $(this).data('id') + '"]').show();
	        $(this).addClass("active");
	    });
	    $('.page-tabs-content').css("margin-left", "0");
	});
	// 绑定菜单单击事件
	$("#menu a.menu").click(function(){
		$('#left').empty();
		// 一级菜单焦点
	//	$("#menu li.menu").removeClass("active");
	//	$(this).parent().addClass("active");
		// 左侧区域隐藏
		/* if ($(this).attr("target") == "mainFrame"){
			$("#left,#openClose").hide();
			wSizeWidth();
			// <c:if test="${tabmode eq '1'}"> 隐藏页签
			$(".jericho_tab").hide();
			$("#mainFrame").show();//</c:if>
			return true;
		} */
		// 左侧区域显示
		/* $("#left,#openClose").show();
		if(!$("#openClose").hasClass("close")){
			$("#openClose").click();
		} */
		// 显示二级菜单
		var menuId = "#menu-" + $(this).attr("data-id");
		if ($(menuId).length > 0){
			$("#left .accordion").hide();
			$(menuId).show();
			// 初始化点击第一个二级菜单
			if (!$(menuId + " .accordion-body:first").hasClass('in')){
				$(menuId + " .accordion-heading:first a").click();
			}
			if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")){
				$(menuId + " .accordion-body a:first i").click();
			}
			// 初始化点击第一个三级菜单
			$(menuId + " .accordion-body li:first li:first a:first i").click();
		}else{
			// 获取二级菜单数据
			$.get($(this).attr("data-href"), function(data){
				if (data.indexOf("id=\"loginForm\"") != -1){
					alert('未登录或登录超时。请重新登录，谢谢！');
					top.location = "";
					return false;
				}
				$("#left .accordion").hide();
				$("#left").append(data);
				$('.wuzi-menuItem').on('click', menuItem);
				// 链接去掉虚框
				$(menuId + " a").bind("focus",function() {
					if(this.blur) {this.blur()};
				});
				// 二级标题
				$(menuId + " .accordion-heading a").click(function(){
					$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
					if(!$($(this).attr('data-href')).hasClass('in')){
						$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
					}
				});
				// 二级内容
				$(menuId + " .accordion-body a").click(function(){
					$(menuId + " li").removeClass("active");
					$(menuId + " li i").removeClass("icon-white");
					$(this).parent().addClass("active");
					$(this).children("i").addClass("icon-white");
				});
				// 展现三级
				$(menuId + " .accordion-inner a").click(function(){
					var href = $(this).attr("data-href");
					if($(href).length > 0){
						$(href).toggle().parent().toggle();
						return false;
					}
					// <c:if test="${tabmode eq '1'}"> 打开显示页签
					return addTab($(this)); // </c:if>
				});
				// 默认选中第一个菜单
				$(menuId + " .accordion-body a:first i").click();
				$(menuId + " .accordion-body li:first li:first a:first i").click();
			});
		}
		// 大小宽度调整
		//wSizeWidth();
		return false;
	});
	
});



//计算元素集合的总宽度
function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function () {
        width += $(this).outerWidth(true);
    });
    return width;
}
//滚动到指定选项卡
function scrollToTab(element) {
    var marginLeftVal = calSumWidth($(element).prevAll()), marginRightVal = calSumWidth($(element).nextAll());
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}
//查看左侧隐藏的选项卡
function scrollTabLeft() {
    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").width() < visibleWidth) {
        return false;
    } else {
        var tabElement = $(".J_menuTab:first");
        var offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        offsetVal = 0;
        if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).prev();
            }
            scrollVal = calSumWidth($(tabElement).prevAll());
        }
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}
//查看右侧隐藏的选项卡
function scrollTabRight() {
    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").width() < visibleWidth) {
        return false;
    } else {
        var tabElement = $(".J_menuTab:first");
        var offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        scrollVal = calSumWidth($(tabElement).prevAll());
        if (scrollVal > 0) {
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        }
    }
}



function menuItem() {
    // 获取标识数据
    var dataUrl = $(this).attr('href'),
        dataIndex = $(this).data('index'),
        menuName = $.trim($(this).text()),
        flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0)return false;

    // 选项卡菜单已存在
    $('.J_menuTab').each(function () {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                scrollToTab(this);
                // 显示tab对应的内容区
                $('.J_mainContent .mainFrame').each(function () {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.mainFrame').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });

    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.J_menuTab').removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="mainFrame" name="mainFrame' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.J_mainContent').find('iframe.mainFrame').hide().parents('.J_mainContent').append(str1);

        //显示loading提示
        var loading = layer.load();

        $('.J_mainContent iframe:visible').load(function () {
            //iframe加载完成后隐藏loading提示
            layer.close(loading);
        });
        // 添加选项卡
        $('.J_menuTabs .page-tabs-content').append(str);
        scrollToTab($('.J_menuTab.active'));
    }
    return false;
}




// 关闭选项卡菜单
function closeTab() {
    var closeTabId = $(this).parents('.J_menuTab').data('id');
    var currentWidth = $(this).parents('.J_menuTab').width();

    // 当前元素处于活动状态
    if ($(this).parents('.J_menuTab').hasClass('active')) {

        // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
        if ($(this).parents('.J_menuTab').next('.J_menuTab').size()) {

            var activeId = $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').data('id');
            $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').addClass('active');

            $('.J_mainContent .mainFrame').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.mainFrame').hide();
                    return false;
                }
            });

            var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
            if (marginLeftVal < 0) {
                $('.page-tabs-content').animate({
                    marginLeft: (marginLeftVal + currentWidth) + 'px'
                }, "fast");
            }

            //  移除当前选项卡
            $(this).parents('.J_menuTab').remove();

            // 移除tab对应的内容区
            $('.J_mainContent .mainFrame').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }

        // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
        if ($(this).parents('.J_menuTab').prev('.J_menuTab').size()) {
            var activeId = $(this).parents('.J_menuTab').prev('.J_menuTab:last').data('id');
            $(this).parents('.J_menuTab').prev('.J_menuTab:last').addClass('active');
            $('.J_mainContent .mainFrame').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.mainFrame').hide();
                    return false;
                }
            });

            //  移除当前选项卡
            $(this).parents('.J_menuTab').remove();

            // 移除tab对应的内容区
            $('.J_mainContent .mainFrame').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }
    }
    // 当前元素不处于活动状态
    else {
        //  移除当前选项卡
        $(this).parents('.J_menuTab').remove();

        // 移除相应tab对应的内容区
        $('.J_mainContent .mainFrame').each(function () {
            if ($(this).data('id') == closeTabId) {
                $(this).remove();
                return false;
            }
        });
        scrollToTab($('.J_menuTab.active'));
    }
    return false;
}


//关闭其他选项卡
function closeOtherTabs(){
    $('.page-tabs-content').children("[data-id]").not(":first").not(".active").each(function () {
        $('.mainFrame[data-id="' + $(this).data('id') + '"]').remove();
        $(this).remove();
    });
    $('.page-tabs-content').css("margin-left", "0");
}


//滚动到已激活的选项卡
function showActiveTab(){
    scrollToTab($('.J_menuTab.active'));
}


// 点击选项卡菜单
function activeTab() {
    if (!$(this).hasClass('active')) {
        var currentId = $(this).data('id');
        // 显示tab对应的内容区
        $('.J_mainContent .mainFrame').each(function () {
            if ($(this).data('id') == currentId) {
                $(this).show().siblings('.mainFrame').hide();
                return false;
            }
        });
        $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
        scrollToTab(this);
    }
}

//刷新iframe
function refreshTab() {
    var target = $('.mainFrame[data-id="' + $(this).data('id') + '"]');
    var url = target.attr('src');
    //显示loading提示
    var loading = layer.load();
    target.attr('src', url).load(function () {
        //关闭loading提示
        layer.close(loading);
    });
}


function getActiveTab(){
	return $(".mainFrame:visible");
}


//打开选项卡菜单
function openTab(url,title, isNew){//isNew 为true时，打开一个新的选项卡；为false时，如果选项卡不存在，打开一个新的选项卡，如果已经存在，使已经存在的选项卡变为活跃状态。
	
	 // 获取标识数据
    var dataUrl = url,
        dataIndex ,
        menuName = title,
        flag = true;
    if (dataUrl == undefined || top.$.trim(dataUrl).length == 0)return false;
//    //设置dataIndex
//    $(".J_menuItem").each(function (index) {
//        if (!$(this).attr('data-index')) {
//            $(this).attr('data-index', index);
//        }
//    });
    
    if(!isNew){
		    top.$('.J_menuTab').each(function () {
		        if (top.$(this).data('id') == dataUrl) {// 选项卡已存在，激活。
		            if (!top.$(this).hasClass('active')) {
		            	top.$(this).addClass('active').siblings('.J_menuTab').removeClass('active');
		                scrollToTab(top.$(this));
		                // 显示tab对应的内容区
		                top.$('.J_mainContent .mainFrame').each(function () {
		                    if (top.$(this).data('id') == dataUrl) {
		                    	top.$(this).show().siblings('.mainFrame').hide();
		                        return false;
		                    }
		                });
		            }
		            flag = false;
		            return false;
		        }
		    });
    }
    
    if(isNew || flag){//isNew为true，打开一个新的选项卡； flag为true，选项卡不存在，打开一个新的选项卡。
	        var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
	        top.$('.J_menuTab').removeClass('active');
	
	        // 添加选项卡对应的iframe
	        var str1 = '<iframe class="mainFrame" name="iframe' + dataIndex + '" width="100%" height="105%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
	        top.$('.J_mainContent').find('iframe.mainFrame').hide().parents('.J_mainContent').append(str1);
	
	        //显示loading提示
	        var loading = layer.load();
	
	        top.$('.J_mainContent iframe:visible').load(function () {
	            //iframe加载完成后隐藏loading提示
	            layer.close(loading);
	        });
	        // 添加选项卡
	        top.$('.J_menuTabs .page-tabs-content').append(str);
	        scrollToTab(top.$('.J_menuTab.active'));
    	
    }
    return false;
	
}


