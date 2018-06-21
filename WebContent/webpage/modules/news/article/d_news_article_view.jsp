<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<html>
<head>
	<title>太钢新闻</title>
	<meta name="decorator" content="default"/>
	 <meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	<script type="text/javascript">
		$(document).ready(function() {
			 $(".container img").addClass("img-responsive center-block");
		});
		var isPhone='${isPhone}';
		//文章详情
		function newsInfo(newsId){
			//alert(newsId);
			if('true'==isPhone){
				HostApp.toNewsDetailFragment(newsId);
			}else{
				window.open("${ctx}/app/news/article/"+newsId+"/false");
			}
		}
	</script>
	
	<style type="text/css">
		.center-ctrl{
            width: 70%;
            height: 60px;
            position: center;
            left: 50%;
            margin-left: 110px;
            line-height: 60px;
        }
        
        .footer{
            position: fixed;
            width: 100%;
            background: #4fadef;
            height: 40px;
            color: #fff;
            bottom: 0px;
            line-height: 40px;
        }
        
        .parent {
        	height:120px; width:100%; padding:13px 0 11px; border-bottom: 1px dashed #e5e6e7;
        }
        
        .img-parent-div {
        	float:left; width:60px; margin-left:15px;
        }
        
        .img-div {
        	padding:7px 0 0; width:60px; height:60px; overflow:hidden
        }
        
        .comment {
        border:0;margin:0;padding:0;text-align:left;vertical-align:baseline;width:auto;float:none;
        }
        
        .replier {
        	font-size:12px;font-color:#BFBFBF;
        }
        
        .comment-content {
        	font-size:14px;font-color:#666; height:auto;width:auto;
        }
        
        .content {
        	display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;
			-webkit-margin-start: 0px;
			-webkit-margin-end: 0px;
        }
        
        .reply {
        font-size:14px; font-color:#A3A3A3; height:12px;
        }
	</style>
</head>
<body>
		
	<div class="container" >
		<br>
	    <h2 style="font-weight:900;font-family : 宋体;font-size: 24px;line-height: 25px;color: black;line-height: 125%">${article.title}</h2>
	    <div class="" style="color:#888888;text-align: left;font-size: 12px;">
	    	<fmt:formatDate value="${article.issuedDate}" pattern="yyyy-MM-dd"/>
	    	<span style="margin-left: 20px;">${article.origin}</span>
	    	<span style="margin-left: 20px;">浏览量：${readingAmount}</span>
	    </div>
	    <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3);margin-top: 16px;" width="100%" color=#DDDDDD SIZE=3>
	    <div class="info-content">
	        
	    	${fns:unescapeHtml(article.content)}
	    </div>
	    <br/>
	    <c:if test="${fun:length(recommendNews) > 0}">
	    <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3);margin-top: 16px;" width="100%" color=#DDDDDD SIZE=3>
	    <h3 style="font-weight:900;font-family : 宋体;font-size: 20px;line-height: 25px;color: black;line-height: 125%">推荐阅读</h3>
	    <div >
	       <ul >
	          <c:forEach var="news" items="${recommendNews}" >
	             <li style="margin-bottom: 15px;list-style-type:none;margin-left: -20px;font-size: 13px;">
	                 <a onclick="newsInfo('${news.id}')" style="color:#666666; ">${news.title }</a>
	             </li>
	          </c:forEach>
	       </ul>
	    </div>
	    </c:if>
	    <c:if test="${fun:length(commentList) > 0}">
	    <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3);margin-top: 35px;" width="100%" color=#DDDDDD SIZE=3>
	    <h3 style="font-weight:900;font-family : 宋体;font-size: 20px;line-height: 25px;color: black;line-height: 125%">最近评论</h3>
	    <div >
	       <ul >
	          <c:forEach var="comment" items="${commentList }" >
	             <li style="margin-bottom: 10px;list-style-type:none;margin-left: -47px;">
	              <img alt="" src="${ctxStatic }/images/geek.png" style="float: left;margin-top: 5px;margin-left: 10px;">
	              <ul style="margin-left: 15px;">
	                 <li style="color: red;list-style-type:none;margin-bottom: 5px;">${comment.nickName }</li>
	                 <li style="list-style-type:none;margin-bottom: 10px;"><fmt:formatDate value="${comment.createDate }" type="both"/></li>
	                 <li style="list-style-type:none;margin-bottom: 10px;">${comment.comment }</li>
	              </ul>
	              <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3);margin-top: 16px;" width="100%" color=#DDDDDD SIZE=3>
	          </li>
	          </c:forEach>
	       </ul>
	    </div>
	    </c:if>
	    <!-- 
	    <div style="font-size:14px; color:#888888;">
	    	<div style="float:left;height: 34px;line-height: 34px;padding: 5px;">阅读量：${readingAmount}</div>
	    	<div style="float:right;height: 34px;line-height: 34px;padding: 5px;">责任编辑：${article.issuer.name}</div>
	    </div>
	     -->
	</div>
	
</body>
</html>