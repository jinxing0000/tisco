package com.ckfinder.connector.handlers.command;

import com.ckfinder.connector.configuration.IConfiguration;
import com.ckfinder.connector.data.ResourceType;
import com.ckfinder.connector.errors.ConnectorException;
import com.ckfinder.connector.utils.AccessControlUtil;
import com.ckfinder.connector.utils.FileUtils;
import com.ckfinder.connector.utils.ImageUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThumbnailCommand extends Command {
	private String fileName;
	private File thumbFile;
	private String ifNoneMatch;
	private long ifModifiedSince;
	private HttpServletResponse response;
	private String fullCurrentPath;

	public void setResponseHeader(HttpServletResponse response,
			ServletContext sc) {
		response.setHeader("Cache-Control", "public");

		String mimetype = getMimeTypeOfImage(sc, response);

//		if (mimetype != null) {
//			response.setContentType(mimetype.concat("; name:\"")
//					+ this.fileName + "\"");
//		} else {
//			response.setContentType("name:\"" + this.fileName + "\"");
//		}
		
		// Set content type  
	    if (mimetype != null) {  
	        response.setContentType(mimetype + "; name='"  
	                + this.fileName + "'");//这里修改  
	    } else {  
	        response.setContentType("name='" + this.fileName + "'");//这里修改  
	    }  


		this.response = response;
	}

	private String getMimeTypeOfImage(ServletContext sc,
			HttpServletResponse response) {
		if ((this.fileName == null) || (this.fileName.equals(""))) {
			response.setStatus(500);
			return null;
		}
		String tempFileName = this.fileName.substring(0,
				this.fileName.lastIndexOf('.') + 1).concat(
				FileUtils.getFileExtension(this.fileName).toLowerCase());

		String mimeType = sc.getMimeType(tempFileName);
		if (mimeType == null) {
			response.setStatus(500);
			return null;
		}
		return mimeType;
	}

	public void execute(OutputStream out) throws ConnectorException {
		validate();
		createThumb();
		if (setResponseHeadersAfterCreatingFile())
			try {
				FileUtils.printFileContentToResponse(this.thumbFile, out);
			} catch (IOException e) {
				if (this.configuration.isDebugMode())
					throw new ConnectorException(e);
				try {
					this.response.sendError(403);
				} catch (IOException e1) {
					throw new ConnectorException(e1);
				}
			}
		else
			try {
				this.response.reset();
				this.response.sendError(304);
			} catch (IOException e1) {
				throw new ConnectorException(e1);
			}
	}

	public void initParams(HttpServletRequest request,
			IConfiguration configuration, Object[] params)
			throws ConnectorException {
		super.initParams(request, configuration, params);
		this.fileName = getParameter(request, "FileName");
		try {
			this.ifModifiedSince = Long.valueOf(
					request.getDateHeader("If-Modified-Since")).longValue();
		} catch (IllegalArgumentException e) {
			this.ifModifiedSince = 0L;
		}
		this.ifNoneMatch = request.getHeader("If-None-Match");
	}

	private void validate() throws ConnectorException {
		if (!this.configuration.getThumbsEnabled()) {
			throw new ConnectorException(501);
		}

		if (!AccessControlUtil.getInstance(this.configuration).checkFolderACL(
				this.type, this.currentFolder, this.userRole, 16)) {
			throw new ConnectorException(103);
		}

		if (!FileUtils.checkFileName(this.fileName)) {
			throw new ConnectorException(109);
		}

		if (FileUtils.checkIfFileIsHidden(this.fileName, this.configuration)) {
			throw new ConnectorException(117);
		}

		File typeThumbDir = new File(this.configuration.getThumbsPath()
				+ File.separator + this.type);
		try {
			this.fullCurrentPath = (typeThumbDir.getAbsolutePath() + this.currentFolder);

			if (!typeThumbDir.exists())
				FileUtils.mkdir(typeThumbDir, this.configuration);
		} catch (SecurityException e) {
			throw new ConnectorException(104, e);
		}
	}

	private void createThumb() throws ConnectorException {
		this.thumbFile = new File(this.fullCurrentPath, this.fileName);
		try {
			if (!this.thumbFile.exists()) {
				File orginFile = new File(((ResourceType) this.configuration
						.getTypes().get(this.type)).getPath()
						+ this.currentFolder, this.fileName);

				if (!orginFile.exists()) {
					throw new ConnectorException(117);
				}
				try {
					ImageUtils.createThumb(orginFile, this.thumbFile,
							this.configuration);
				} catch (Exception e) {
					this.thumbFile.delete();
					throw new ConnectorException(104, e);
				}
			}
		} catch (SecurityException e) {
			throw new ConnectorException(104, e);
		}
	}

	private boolean setResponseHeadersAfterCreatingFile()
			throws ConnectorException {
		File file = new File(this.fullCurrentPath, this.fileName);
		try {
			String etag = Long.toHexString(file.lastModified()).concat("-")
					.concat(Long.toHexString(file.length()));

			if (etag.equals(this.ifNoneMatch)) {
				return false;
			}
			this.response.setHeader("Etag", etag);

			if (file.lastModified() <= this.ifModifiedSince) {
				return false;
			}
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat df = new SimpleDateFormat(
					"EEE, dd MMMM yyyy HH:mm:ss z");

			this.response.setHeader("Last-Modified", df.format(date));

			this.response.setContentLength((int) file.length());
		} catch (SecurityException e) {
			throw new ConnectorException(104, e);
		}

		return true;
	}
}