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
			  $('.article-status').val(state);
			  //var sHTML = $('.summernote').code(); 
			  var sHTML = editor.getContent();
			  			  
			  var picId=$("#picId").val();
			  if(picId==''){
				  alert('请至少上传一张图片');
				  return false;
			  }
			  var type=$("#type").val();
			  if(type=='heard'){
				  var audioId=$("#audioId").val();
				  if(audioId==''){
					  alert('请上传音频文件');
					  return false;
				  }
			  }else if(type=='video'){
				  var videoId=$("#videoId").val();
				  if(videoId==''){
					  alert('请上传视频文件');
					  return false;
				  }
			  }
			  $(".news-content").val(sHTML);//取富文本的值
			  $("#inputForm").submit();
			  return true;
		}
		
		function setStatus(status) {
			$('.article-status').val(status);
		}
		
		function disabledBtns() {
			$('#issueSubmit, #draftSubmit').attr("disabled", "disabled");
		}
		
		function enabledBtns() {
			$('#issueSubmit, #draftSubmit').removeAttr("disabled");
		}
		
		/*
			初始化新闻摘要
		*/
		function initNewsSummary() {
			var summary = $('#news_summary').val();
			
			if (summary === "") {
				$('#news_summary').val("讲述太钢故事 传播太钢声音");
			}
		}
		
		/*
			页面初始化
		*/
		function init() {
			disabledBtns();
			
			initNewsSummary();
			
			laydate({
	            elem: '#issuedDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		}
		
		$(document).ready(function() {
			// 页面初始化工作
			init();
			
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
             */

		});
		var picSort=1;
		var fileSort=1;
		var videoSort=1;
		var imgPath='${imgPath}';
		function uploadPic(){
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
			   		var json = eval('(' + data.substring(data.indexOf("["),data.indexOf("]")+1)+ ')');
			   		if(json[0]=='success'){
			   			var htl="<div id='"+json[1].id+"_picId'><img src='"+imgPath+json[1].path+"' width='200px' height='150px' style='margin-bottom: 5px;margin-left: 5px;'/>排序号：<input type='text' onkeyup=\"setSort(this.value,'"+json[1].id+"')\" value='"+json[1].sort+"'/><button type='button' onclick=\"delFile('"+json[1].id+"','picId')\">删除</button></div>";
			   			$("#picDiv").append(htl);
			   			$("#picId").val($("#picId").val()+json[1].id+",")
			   		}else{
			   			alert(json[1]);
			   		}
			   		
			   	 	var type = $("#type").val();
			   		if ("video" != type && "heard" != type) {
			   			enabledBtns();
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
			   		var json = eval('(' + data.substring(data.indexOf("["),data.indexOf("]")+1) + ')');
			   		if(json[0]=='success'){
			   			var htl="<li id='"+json[1].id+"_audioId'>"+json[1].name+"排序号：<input type='text' onkeyup=\"setSort(this.value,'"+json[1].id+"')\" value='"+json[1].sort+"'/><button type='button' onclick=\"delFile('"+json[1].id+"','audioId')\">删除</button></li>";
						$("#fileUl").append(htl);
						$("#audioId").val($("#audioId").val()+json[1].id+",");
						$("#filePath").val($("#filePath").val() + json[1].path + ",");
			   		}else{
			   			alert(json[1]);
			   		}
			   		
			   		enabledBtns();
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
			   		var json = eval('(' + data.substring(data.indexOf("["),data.indexOf("]")+1) + ')');
			   		if(json[0]=='success'){
			   			var htl="<li id='"+json[1].id+"_videoId'>"+json[1].name+"排序号：<input type='text' onkeyup=\"setSort(this.value,'"+json[1].id+"')\" value='"+json[1].sort+"'/><button type='button' onclick=\"delFile('"+json[1].id+"','videoId')\">删除</button></li>";
						$("#fileVoideUl").append(htl);
						$("#videoId").val($("#videoId").val()+json[1].id+",")
						$("#filePath").val($("#filePath").val() + json[1].path + ",");
			   		}else{
			   			alert(json[1]);
			   		}
			   		
			   		enabledBtns();
			   	},
			   	error : function(data, statue, e) {
			   		alert('上传失败！！');
			   	}
			});
		}
		
		function delAttachment(fileId, type) {
			if (fileId === undefined || fileId === '') {
				return;
			}
			
			var filePath = $('#filePath').val();
			var reqData = {
				"fileId": fileId,
				"filePath": filePath
			};
			
			switch(type) {
			case "vedio":
				videoSort = 1;
				break;
			case "audio":
				fileSort = 1;
				break;
			default:
				picSort = 1;
			}
			
			$.post("${ctx}/news/article/attachment/del", reqData, 
					function(data) {
				// do nothing
			});
		}
		
		function removeVedio() {
			// 附件表中删除附件信息
			delAttachment($('#videoId').val(), "vedio");
			
			// 页面上附件信息清除
			$('#videoId, #file_voide, #filePath').val("");
			$("#fileVoideUl").empty();
		}
		
		function removeAudio() {
			// 附件表中删除附件信息
			delAttachment($('#audioId').val(), "audio");
			
			// 页面上附件信息清除
			$('#audioId, #file, #filePath').val("");
			$("#fileUl").empty();
		}
		
		function switchType(){
			
		   var type = $("#type").val();
		   	if ("vedio" == type || "heard" == type) {
				disabledBtns();
		   	}
			
		   if ('enjoyThinking' == type || 'heard'==type) {// 思享
			   $("#audio").show();
			   $("#video").hide();
			   $("#level").hide();
			   $("#contentTypeDiv").hide();
			   
			   // 视频附件信息清除
			   removeVedio();
		   } else if('video'==type) { //视频
			   $("#video, #level").show();
			   $('#audio, #contentTypeDiv').hide();
			   
			   // 页面清除音频附件信息
			   removeAudio();
		   } else {
			   $("#video").hide();
			   $("#audio").hide();
			   $("#level").hide();
			   
			   if ("caption" != type) {
				   $("#contentTypeDiv").show();
			   }
			   
			   // 视频附件信息清除
			   removeVedio();
			   // 页面清除音频附件信息
			   removeAudio();
		   }
		}
		
		function delFile(fileId,divId){
			var ids=$("#"+divId).val().split(",");
			var str;
			for(var i=0;i<ids.length-1;i++){
				if(ids[i]!=fileId){
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
<body>
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>发布新闻 </h5>
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
		<form:form id="inputForm" modelAttribute="article" action="${ctx}/news/article/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="picId"/>
		<form:hidden path="audioId"/>
		<form:hidden path="videoId"/>
		<form:hidden path="recommendNews"/>
		
		<input type="hidden" id="filePath" />
		<input type="hidden" id="picFilePath" />
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
			<label class="control-label">权重（排序）：</label>
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
		    <label class="control-label">推荐焦点：</label>
			<div class="controls">
			    <form:checkbox path="toplineFocus" value="focus" />&nbsp;&nbsp;是
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

		<div class="control-group" id="contentTypeDiv" >
			<label class="control-label">本栏目焦点：</label>
			<div class="controls">
			    <form:checkbox path="contentType" value="focus" />&nbsp;&nbsp;是
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label">摘要：</label>
			<div class="controls">
				<form:textarea id="news_summary" path="summary" rows="4" maxlength="100" htmlEscape="false" 
					class="form-control" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
 				<form:hidden path="content" class="news-content" />
				<script id="ueditor" type="text/plain" style="width:100%;height:350px;"></script>
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
				   
				</ul>
			</div>
		</div>
		
		 <div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls" style="width: 1000px;">
				<input type="file" name="pic" id="pic" /><span><button onclick="uploadPic()" type="button">上传</button></span>
				<div id="picDiv">
				</div>
			</div>
		</div>
		<div class="control-group" id="audio" style="display: none;">
			<label class="control-label">音频：</label>
			<div class="controls" style="width: 1000px;">
				<input type="file" name="file" id="file" /><span><button onclick="uploadFile()" type="button">上传</button></span>
				<div >
				   <ul id='fileUl'>
				      
				   </ul>
				</div>
			</div>
		</div>
		<div class="control-group" id="video" style="display: none;">
			<label class="control-label">视频：</label>
			<div class="controls" style="width: 1000px;">
				<input type="file" name="file_voide" id="file_voide" /><span><button onclick="uploadVideoFile()" type="button">上传</button></span>
				<div >
				   <ul id="fileVoideUl">
				     
				   </ul>
				</div>
			</div>
		</div>
		<form:hidden path="status" class="article-status" />
		
		<div class="form-actions">
			<input id="issueSubmit" class="btn btn-success" type="button" value="提 交" onclick="doSubmit('1')"/>&nbsp;
			<input id="draftSubmit" class="btn btn-primary" type="button" value="保存草稿" onclick="doSubmit('0')"/>&nbsp;
		</div>
	</form:form>
	</div>
	</div>
	</div>
</body>
</html>