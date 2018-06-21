<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/fileupload/ajaxfileupload.js"></script>
 	 <script src="${ctxStatic}/ueditor/ueditor.config.js"></script>
	 <script src="${ctxStatic}/ueditor/ueditor.all.min.js"></script>
	 <script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
	 <link rel="stylesheet" href="${ctxStatic}/ueditor/themes/default/css/ueditor.min.css" />
	<script type="text/javascript">
 		window.UEDITOR_HOME_URL = "${ctxStatic}/ueditor/";
	
		var editor = new baidu.editor.ui.Editor();
		editor.render('ueditor');
		
		var validateForm;
		function doSubmit(state){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			//var sHTML = $('.summernote').code();  
			var sHTML = editor.getContent();
			
			 $('.article-status').val(state);
			 
/* 			  var picId=$("#picId").val();
			  if(picId==''){
				  alert('请至少上传一张图片');
				  return;
			  } */
			 
			  var type=$("#type").val();
			  if(type=='heard'){
				  var audioId=$("#audioId").val();
				  if(audioId==''){
					  alert('请上传音频文件');
					  return;
				  }
			  }else if(type=='video'){
				  var videoId=$("#videoId").val();
				  if(videoId==''){
					  alert('请上传视频文件');
					  return;
				  }
			  }
			  $(".news-content").val(sHTML);//取富文本的值
			  $("#inputForm").submit();
			  return true;
		}
		
		function setStatus(status) {
			$('.article-status').val(status);
		}
		
		function initContent() {
			
			editor.ready(function() {
				editor.setContent($(".news-content").val());
			});
		}
		
		$(document).ready(function() {
			
			initContent();
			
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
/*             $('.summernote').summernote({
                lang: 'zh-CN'
            });
            
            $('.summernote').summernote();
            
			$('.summernote').code($(".news-content").val()); */
		     var type='${article.type.id}';
		     if('enjoyThinking'!=type&&'video'!=type&&'heard'!=type){
				$("#contentTypeDiv").show();
		     }
		});
		var picSort=1;
		var fileSort=1;
		var videoSort=1;
		var imgPath='${imgPath}';
		function uploadPic(){
			if(picSort>4){
				alert('只能上传4张');
				return;
			}
			var urlt="loadPic?sort="+picSort;
	        $.ajaxFileUpload({
			   	url : urlt,
			   	secureuri : false,
			   	fileElementId : 'pic',
			   	dataType : 'JSON',
			   	contentType : "application/x-www-form-urlencoded; charset=utf-8",
			   	modal : true,
			   	success : function(data) {
			   		picSort++;
			   		var json = eval('(' + data.replace('<pre>','').replace('</pre>','') + ')');
			   		if(json[0]=='success'){
			   			var htl="<div><img src='"+imgPath+json[1].path+"' width='200px' height='150px' style='margin-bottom: 5px;margin-left: 5px;'/>排序号：<input type='text' onkeyup=\"setSort(this.value,'"+json[1].id+"')\" value='"+json[1].sort+"'/><button type='button' onclick=\"delFile('"+json[1].id+"','picId')\">删除</button></div>";
			   			$("#picDiv").append(htl);
			   			$("#picId").val($("#picId").val()+json[1].id+",")
			   		}else{
			   			alert(json[1]);
			   		}
			   	},
			   	error : function(data, statue, e) {
			   		alert('上传失败！！');
			   	}
			});
		}
		
		function setSort(value,id){
			if(value==''){
				alert('请输入排序号');
				return ;
			}
			var urlt="../../policyInfo/updateFileForSort?id="+id+'&sort='+value;
			$.ajax({
				type:"post",
				dataType:"json",
				async:false,
				cache: false,
				url:urlt,
				success:function(data){
					if(data[0]=='error'){
						alert(data[1]);
					}
				},
				error:function(data){
					
				}
			});
		}
		
		function uploadFile(){
			if(fileSort>1){
				alert('只能上传一个音频');
				return;
			}
			var urlt="loadFile?sort="+fileSort;
	        $.ajaxFileUpload({
			   	url : urlt,
			   	secureuri : false,
			   	fileElementId : 'file',
			   	dataType : 'JSON',
			   	contentType : "application/x-www-form-urlencoded; charset=utf-8",
			   	modal : true,
			   	success : function(data) {
			   		fileSort++;
			   		var json = eval('(' + data.replace('<pre>','').replace('</pre>','') + ')');
			   		if(json[0]=='success'){
			   			var htl="<li>"+json[1].name+"排序号：<input type='text' onkeyup=\"setSort(this.value,'"+json[1].id+"')\" value='"+json[1].sort+"'/><button type='button' onclick=\"delFile('"+json[1].id+"','audioId')\">删除</button></li>";
						$("#fileUl").append(htl);
						$("#audioId").val($("#audioId").val()+json[1].id+",")
			   		}else{
			   			alert(json[1]);
			   		}
			   	},
			   	error : function(data, statue, e) {
			   		alert('上传失败！！');
			   	}
			});
		}
		
		function uploadVideoFile(){
			if(videoSort>1){
				alert('只能上传一个视频');
				return;
			}
			var urlt="loadVideoFile?sort="+videoSort;
	        $.ajaxFileUpload({
			   	url : urlt,
			   	secureuri : false,
			   	fileElementId : 'file_voide',
			   	dataType : 'JSON',
			   	contentType : "application/x-www-form-urlencoded; charset=utf-8",
			   	modal : true,
			   	success : function(data) {
			   		videoSort++;
			   		var json = eval('(' + data.replace('<pre>','').replace('</pre>','') + ')');
			   		if(json[0]=='success'){
			   			var htl="<li>"+json[1].name+"排序号：<input type='text' onkeyup=\"setSort(this.value,'"+json[1].id+"')\" value='"+json[1].sort+"'/><button type='button' onclick=\"delFile('"+json[1].id+"','videoId')\">删除</button></li>";
						$("#fileVoideUl").append(htl);
						$("#videoId").val($("#videoId").val()+json[1].id+",")
			   		}else{
			   			alert(json[1]);
			   		}
			   	},
			   	error : function(data, statue, e) {
			   		alert('上传失败！！');
			   	}
			});
		}
		
		function switchType(){
			   var type=$("#type").val();
			   if('enjoyThinking'==type||'heard'==type){
				   $("#audio").show();
				   $("#video").hide();
				   $("#level").hide();
				   $("#contentTypeDiv").hide();
			   }else if('video'==type){
				   $("#video").show();
				   $("#audio").hide();
				   $("#level").show();
				   $("#contentTypeDiv").hide();
			   }else{
				   $("#video").hide();
				   $("#audio").hide();
				   $("#level").hide();
				   $("#contentTypeDiv").show();
			   }
			}
		
		function delFile(fileId,divId){
			var ids=$("#"+divId).val().split(",");
			var str;
			for(var i=0;i<ids.length-1;i++){
				if(ids[i]!=fileId){
					alert(ids[i]);
					str=str+ids[i]+",";
				}
			}
			$("#"+divId).val("");
			$("#"+fileId+"_"+divId).remove();
		}
		
		//打开选择新闻列表
		function newsList(title,url,width,height,target){
			top.layer.open({
			    type: 2,  
			    area: [width, height],
			    title: title,
		        maxmin: true, //开启最大化最小化按钮
			    content: url ,
			    btn: ['选定', '关闭'],
			    yes: function(index, layero){
					 var body = top.layer.getChildFrame('body', index);
			         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			         
			         var newsList=iframeWin.contentWindow.newsList;
			         var htl="";
			         var str="";
			         for(var i=0;i<newsList.length;i++){
			        	 htl+="<li><a>"+newsList[i].title+"</a></li>";
			        	 str+=newsList[i].newId+",";
			         }
			         $("#queryList").html(htl);
			         $("#recommendNews").val(str);
			         setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
					
				  },
				cancel: function(index){ 
					  
			    }
			}); 	
			
		}
	</script>
</head>
<body >
<div class="wrapper wrapper-content">
		<form:form id="inputForm" modelAttribute="article" action="${ctx}/news/article/save?flag=${flag}" method="post" class="form-horizontal">
		<form:hidden id="newsId" path="id"/>
		<form:hidden path="picId"/>
		<form:hidden path="audioId"/>
		<form:hidden path="videoId"/>
		<form:hidden path="issuer.id" />
		<form:hidden path="recommendNews"/>
		<sys:message content="${message}"/>	
		
		<div class="control-group">
			<label class="control-label">文章类型：</label>
			<div class="controls">
				<form:select path="type.id" class="form-control " style="width:240px;" id="type" onchange="switchType()">
					<form:options items="${newsType}" itemLabel="name" itemValue="key" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="64" class="form-control required"/>
			</div>
		</div>
		
		<div class="control-group" id="level" style="display: none;">
			<label class="control-label">二级标题：</label>
			<div class="controls">
				<form:select path="twoLevel" class="form-control " style="width:240px;">
					<form:option value="1" label="新闻"/>
					<form:option value="2" label="专题"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">来源：</label>
			<div class="controls">
				<form:input path="origin" htmlEscape="false" maxlength="64" class="form-control required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">编辑人：</label>
			<div class="controls">
				<form:input path="author" htmlEscape="false" maxlength="64" class="form-control required" />
			</div>
		</div>
				
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="64" class="form-control required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">置为推荐：</label>
			<div class="controls">
				<form:checkbox path="isTopline" value="1" disabled="disabled"/>&nbsp;&nbsp;是
			</div>
		</div>
		
				<div class="control-group">
			<label class="control-label">是否允许评论：</label>
			<div class="controls">
				<form:checkbox path="enableCommentsOrNot" value="1" />&nbsp;&nbsp;是
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">推送给客户端：</label>
			<div class="controls">
				<form:checkbox path="isPushMsg" value="1" />&nbsp;&nbsp;是
			</div>
		</div>
		
		<div class="control-group" id="contentTypeDiv" style="display: none;">
			<label class="control-label">内容类型：</label>
			<div class="controls">
				<form:select path="contentType" class="form-control " style="width:240px;">
					<form:option value="" label="请选择内容类型"/>
					<form:option value="focus" label="焦点"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">摘要：</label>
			<div class="controls">
				<form:textarea path="summary" rows="4" htmlEscape="false" class="form-control"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<%-- <form:hidden path="content" class="news-content" />
			 	<div class="summernote">
                           

                </div> --%>
				<form:hidden path="content" class="news-content" />
				<script id="ueditor" type="text/plain" style="width:100%;height:240px;"></script>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input id="issuedDate" name="issuedDate" type="text" readonly="readonly" maxlength="20" 
					class="laydate-icon form-control layer-date required"
					value="<fmt:formatDate value="${article.issuedDate}" pattern="yyyy-MM-dd"/>" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">推荐新闻列表：</label>
			<div class="controls">
			    <button type="button" onclick="newsList('选择新闻','${ctx}/news/article/queryList','1000px', '600px')">选择新闻</button>
				<ul id="queryList">
				    <c:forEach var="news" items="${recommendNews}">
				       <li><a>${news.title }</a></li>
				   </c:forEach>
				</ul>
			</div>
		</div>
		
		 <div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls" style="width: 1000px;">
				<input type="file" name="pic" id="pic" /><span><button onclick="uploadPic()" type="button">上传</button></span>
				<div id="picDiv">
				   <c:forEach var="FileUpload" items="${article.fileUploads}">
				       <c:if test="${FileUpload.type =='jpg' or FileUpload.type=='png' }">
				          <div id='${FileUpload.id}_picId'><img src='${imgPath}${FileUpload.path}' width='200px' height='150px' style='margin-bottom: 5px;margin-left: 5px;'/>排序号：<input type='text' onkeyup="setSort(this.value,'${FileUpload.id}')" value='${FileUpload.sort }'/><button type='button' onclick="delFile('${FileUpload.id}','picId')">删除</button></div>
				       </c:if>
				   </c:forEach>
				</div>
			</div>
		</div>
		<div class="control-group" id="audio"  <c:if test="${article.type.id !='heard' or article.type.id!='enjoyThinking'}">style="display: none;"</c:if>  >
			<label class="control-label">音频：</label>
			<div class="controls" style="width: 1000px;">
				<input type="file" name="file" id="file" /><span><button onclick="uploadFile()" type="button">上传</button></span>
				<div >
				   <ul id='fileUl'>
				      <c:forEach var="FileUpload" items="${article.fileUploads}">
				       <c:if test="${FileUpload.type =='mp3' }">
				         <li id='${FileUpload.id}_audioId'>${FileUpload.name }排序号：<input type='text' onkeyup="setSort(this.value,'${FileUpload.id}')" value='${FileUpload.sort }'/><button type='button' onclick="delFile('${FileUpload.id}','audioId')">删除</button></li>
				       </c:if>
				   </c:forEach>
				   </ul>
				</div>
			</div>
		</div>
		<div class="control-group" id="video"   <c:if test="${article.type.id !='video' }">style="display: none;"</c:if>  >
			<label class="control-label">视频：</label>
			<div class="controls" style="width: 1000px;">
				<input type="file" name="file_voide" id="file_voide" /><span><button onclick="uploadVideoFile()" type="button">上传</button></span>
				<div >
				   <ul id="fileVoideUl">
				     <c:forEach var="FileUpload" items="${article.fileUploads}">
				       <c:if test="${FileUpload.type =='mp4' }">
				         <li id='${FileUpload.id}_videoId'>${FileUpload.name }排序号：<input type='text' onkeyup="setSort(this.value,'${FileUpload.id}')" value='${FileUpload.sort }'/><button type='button' onclick="delFile('${FileUpload.id}','videoId')">删除</button></li>
				       </c:if>
				     </c:forEach>
				   </ul>
				</div>
			</div>
		</div>
		<form:hidden id="articleStatus" path="status" class="article-status" />
		
<!-- 		<div class="form-actions">
			<input id="issueSubmit" class="btn btn-success" type="button" value="发 布" onclick="doSubmit('1')"/>&nbsp;
			<input id="draftSubmit" class="btn btn-primary" type="submit" value="保存草稿" onclick="doSubmit('0')"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> -->
	</form:form>
	</div>
</body>
<script type="text/javascript">
function init(){
	
}
</script>
</html>