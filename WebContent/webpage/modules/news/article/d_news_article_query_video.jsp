<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>附件视频查看</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		
	<div >
		<span style="text-align:  center;margin-left: 10px;margin-top: 10px;"><label style="color: red;" ></label></span>
		<div style="padding-left: 20px;">
		   <c:forEach items="${article.fileUploads}" var="fileInfo">
		      <c:if test="${fileInfo.type eq 'mp4'}">
		          <span style="width: 300px;height: 200px;text-align: center;">
		             <video  controls autoplay  style="width=400px;height=200px;">
					   <source src="${filePath }${fileInfo.path }" type="video/mp4">
					   <object data="${filePath }${fileInfo.path }" width="300" height="200" >
					   <embed width="300" height="200" src="${filePath }${fileInfo.path }"></embed>
					   </object>
				     </video>
		          </span>
		      </c:if>
		      
		      <c:if test="${fileInfo.type eq 'mp3'}">
		          <span style="width: 100px;height: 30px;text-align: center;">
		             <video  controls autoplay  style="width=100px;height=30px;">
					   <source src="${filePath }${fileInfo.path }" type="audio/mpeg">
					   <object data="${filePath }${fileInfo.path }" width="100" height="300" >
					   <embed width="100" height="30" src="${filePath }${fileInfo.path }"></embed>
					   </object>
				     </video>
		          </span>
		      </c:if>
		   </c:forEach>
		</div>
	</div>
	
</body>
</html>