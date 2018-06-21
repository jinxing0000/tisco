/**
 * uploadify上传公用方法
 * 
 * 如何使用这个插件：页面只需引用uploader.js
 * 调用Uploader({element:'',sessionId:''})方法
 * <input type="file" id="attachments"/>
	<div id="files"></div>
	<div id="fileQueue"></div>
 */
function Uploader(config){
	this.element = config.element;
	this.sessionId = config.sessionId;
	this.tag = config.tag;
	this.uploader();
}

Uploader.prototype = {
		uploader:function(){
			var self = this;
			$(self.element).uploadify({
				'uploader': ctxStatic + '/fileupload/uploadify/uploadify.swf',
		        'script': ctx+'/policyInfo/uploadAndAdd.do;jsessionid='+self.sessionId,	//指定服务端处理类的入口
		 		'cancelImg' : ctxStatic + '/fileupload/uploadify/cancel.png', //关闭的图标小图片
		        'queueID': 'fileQueue'+self.tag,//文件队列的ID，该ID与存放文件队列的div的ID一致。给“进度条”加背景css的ID样式。文件选择后的容器ID
		        'fileDataName': 'fileInput',//和input的name属性值保持一致 设置一个名字，在服务器处理程序中根据该名字来取上传文件的数据。默认为Filedata
		        'auto': true,//是否选取文件后自动上传   
		        'multi': true,//是否支持多文件上传
		        'simUploadLimit' : 10,//每次最大上传文件数
		       	'removeCompleted' : false,
				'buttonImg':ctxStatic + '/fileupload/image/upload.png', //“附件上传”按钮是一个小图片
				'wmode': 'transparent',
			    'width': 75,
			    'height': 20,
			    'displayData': 'speed', //有speed和percentage两种，一个显示速度，一个显示完成百分比
			    //上传文件
			    'onComplete': function(event, ID, fileObj, response, data) {
			    	//fileObj：选择的文件对象，有name、size、creationDate、modificationDate、type 5个属性。
			    	//var num = $("input[name$='tag']").size();
			    	var num = $("input[name$='tag']").size();
		       		$('#files'+self.tag).append('<img src="' + "${ctx}/" + fileObj.path + '" class="img-responsive img-thumbnail" >')
		       		.append('<input type="hidden" id="'+ID+'" name="fileUploads['+num+'].id" value="'+response+'"/>')
		       		.append('<input type="hidden" id="'+ID+'" name="fileUploads['+num+'].tag" value="'+self.tag+'" num="'+ID+'"/>');
		       		//.append('<input type="hidden" name="num"/>');//主要
		         },
		         //删除文件
		       	 'onCancel': function(event,ID,fileObj,data) {
		       		 if($('#'+ID).length > 0){
		       			 $.post(ctx + '/policyInfo/deleteFile.do',
		       					 {id:$('#'+ID).val()},
		       					 function(){
		       						 $('#'+ID).remove();
		       						 $("input[num='"+ID+"']").remove();
		       						 $("div[id='"+$('#'+ID).val()+"']").remove();
		       					 });
		       		 }else{
		       			 $.post(ctx + '/policyInfo/deleteFile.do',{id:ID},function(){
		       				 $('#'+ID).remove();
		       				 $("div[id='attachments"+ID+"']").remove();
       						// $("div[id='"+$('#'+ID).val()+"']").remove();
		       			 });
		       		 }
		         }
			});
		}
};




