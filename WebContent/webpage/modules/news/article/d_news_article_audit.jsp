<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function doSubmit(state) {
			$('#submit_result, #article_status').val(state);
			
			$('#auditForm').submit();
			
			return true;
		}
		$(document).ready(function() {
			 $(".container img").addClass("img-responsive center-block");
		});
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
		
<form:form id="auditForm" modelAttribute="article" action="${ctx}/cms/audit/save" method="post" class="form-horizontal">
	<form:hidden path="id" value="${article.id}"/>
	<form:hidden path="type.id" value="${article.type.id}" />
	<form:hidden path="title" value="${article.title }" />
	<form:hidden path="type.name" value="${article.type.name}" />
	<form:hidden path="isPushMsg" value="${article.isPushMsg}" />
	<form:hidden path="auditComments.cmsId" value="${article.id}" />
	<form:hidden id="article_status" path="status"/>
	<form:hidden id="submit_result" path="auditComments.result" />
	
	<div class="container" >
		<br>
	    <h2 style="text-align:center;font-weight:900;font-family : 宋体;font-size: 24px;line-height: 25px;color: black;line-height: 125%">${article.title}</h2>
	    <div class="" style="color:#888888;text-align: center;font-size: 15px;"><fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;<span style="margin-left: 30px;">${article.origin}</span><span style="margin-left: 40px;">浏览量：${readingAmount}</span></div>
	    <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3);margin-top: 15px;" width="100%" color=#DDDDDD SIZE=3>
	    <div class="info-content">
	        
	    	${fns:unescapeHtml(article.content)}
	    </div>
	    <!-- 
	    <div style="font-size:14px; color:#888888;">
	    	<div style="float:left;height: 34px;line-height: 34px;padding: 5px;">阅读量：${readingAmount}</div>
	    	<div style="float:right;height: 34px;line-height: 34px;padding: 5px;">责任编辑：${article.issuer.name}</div>
	    </div>
	     -->
	</div>
	
	<br>
	<div class="control-group">
	</div>
	<c:if test="${article.status eq '1'}">
		<div class="control-group">
			<label class="control-label">审核意见：</label>
			<div class="controls">
				<form:textarea path="auditComments.comments" rows="4" htmlEscape="false" class="form-control"/>
			</div>
		</div>
<!-- 		<div class="form-actions">
			<input id="issueSubmit" class="btn btn-success" type="button" value="发 布" onclick="doSubmit('3')"/>&nbsp;
			<input id="draftSubmit" class="btn btn-primary" type="submit" value="驳 回" onclick="doSubmit('2')"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> -->
	</c:if>
</form:form>
</body>
</html>