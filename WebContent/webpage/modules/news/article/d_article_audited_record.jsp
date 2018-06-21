<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
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
	<div class="wrapper wrapper-content">
	<div class="ibox">		
<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead>
					<tr>
						<th>审核人</th>
						<th>审核结论</th>
						<th>审核意见</th>
						<th>审核时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="comments">
						<tr>
							<td>${comments.auditor.name}</td>
							<td>
								<c:if test="${comments.result eq '3'}">
									同意发布
								</c:if>
								<c:if test="${comments.result eq '2' }">
									驳回修改
								</c:if>
							</td>
							<td>
								${fns:abbr(comments.comments, 40)}
							</td>
							<td>
								<fmt:formatDate value="${comments.auditedTime}" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<table:page page="${page}"></table:page>
	</div>
	</div>
</body>
</html>