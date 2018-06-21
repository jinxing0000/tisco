package com.tisco.app.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

public class FileUploadUtil {
	/**
	 * 获取MP3的时长
	 * @param filePath
	 * @return
	 */
	public static long getMp3WhenLong(String filePath){
		long whenLong=0;
		File file = new File(filePath);
		try {
			MP3File f = (MP3File)AudioFileIO.read(file);
			MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
			whenLong=audioHeader.getTrackLength();
//			System.out.println(audioHeader.getTrackLength());	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return whenLong;
	}
	
	/**
	 * 获取MP4的时长
	 * @param filePath
	 * @return
	 */
	public static long getMp4WhenLong(String filePath){
		long whenLong=0;
		File source = new File(filePath);
        Encoder encoder = new Encoder();
        try {
             MultimediaInfo m = encoder.getInfo(source);
             long ls = m.getDuration();
             whenLong=(ls)/1000;
//             System.out.println("此视频时长为:"+ls/60000+"分"+(ls)/1000+"秒！");
        } catch (Exception e) {
            e.printStackTrace();
        }
		return whenLong;
	}
	/**
	 * 网络获取mp3长度
	 * @param filePath
	 * @return
	 */
	public static long getHttpMp3WhenLong(String filePath){
		long whenLong=0;
		try {
			URL urlfile = new URL(filePath);
	        URLConnection con = null;
	        try {
	            con = urlfile.openConnection();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        int b = con.getContentLength();//
	        BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
	        Bitstream bt = new Bitstream(bis);
	        Header h = bt.readFrame();
	        int time = (int) h.total_ms(b);
	        System.out.println(time / 1000);
	        whenLong=time / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return whenLong;
	}
	
	public static void main(String[] args) {
		FileUploadUtil.getHttpMp3WhenLong("http://127.0.0.1:8088/file/tisco/file-201704/26/ae6646fa-9f6e-4224-bc70-b7939ca70fc3.mp3");
	}
	
}
