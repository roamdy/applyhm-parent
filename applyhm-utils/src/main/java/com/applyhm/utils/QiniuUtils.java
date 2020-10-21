package com.applyhm.utils;

import java.util.HashMap;
import java.util.Map;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuUtils {

	private static final String  AK = "fPOlejgFg-J6ILvy7RwRImLD3iwdfpSkHgPA_BRt";
	private static final String  SK = "FThIX2D6o90RMTAHEu8wryqxULAlkfMWZtpVZBMf";

	public static final int UPLOAD_SIMPLE = 1;           //简单上传
	public static final int UPLOAD_OVER = 2;             //上传覆盖

	/**
	 * 获取七牛云简单上传凭证
	 */
	public static String getUploadToken(String bucket){
		if(bucket == null || "".equals(bucket))bucket = DomainToBucket.DOMAIN_DEFAULT;
		Auth auth = Auth.create(AK, SK);
		String uploadToken = auth.uploadToken(bucket);
		return uploadToken;
	}

	/**
	 * 获取七牛云覆盖上传凭证
	 */
	public static String getUploadToken(String bucket, String key){
		if("".equals(key)) return "";
		if(bucket == null || "".equals(bucket))bucket = DomainToBucket.DOMAIN_DEFAULT;
		Auth auth = Auth.create(AK, SK);
		String uploadToken = auth.uploadToken(bucket, key, 3600, new StringMap().put("insertOnly", 1 ));
		return uploadToken;
	}

	/**
	 * 上传文件到七牛云
	 * @param file
	 * @param fileName
	 * @param bucket
	 * @param type : 1 简单上传 2 覆盖上传
	 * @return
	 */
	public static int uploadFile(byte[] file, String fileName, String bucket, Integer type){
		UploadManager uploadManager = new UploadManager();//七牛上传管理器
		String uploadToken = null;
		if(type == QiniuUtils.UPLOAD_SIMPLE){
			uploadToken = getUploadToken(bucket);
		}else if(type == QiniuUtils.UPLOAD_OVER){
			uploadToken = getUploadToken(bucket, fileName);
		}
		Response res = null;
		int statusCode = 0 ;
		try {
			res = uploadManager.put(file, fileName,uploadToken);//fileName是key,在7牛相当于文件名
			statusCode = res.statusCode;
		} catch (QiniuException e) {
			Response r = e.response;
	        // 请求失败时打印的异常的信息
	        System.out.println(r.toString());
		}
		return statusCode;
	}

	/**
     * 上传文件到七牛云
     * @param file
     * @param fileName
     * @param bucket
     * @param type : 1 简单上传 2 覆盖上传
     * @return
     */
    public static int uploadFileLocal(String filePath, String fileName, String bucket, Integer type){
        UploadManager uploadManager = new UploadManager();//七牛上传管理器
        String uploadToken = null;
        if(type == QiniuUtils.UPLOAD_SIMPLE){
            uploadToken = getUploadToken(bucket);
        }else if(type == QiniuUtils.UPLOAD_OVER){
            uploadToken = getUploadToken(bucket, fileName);
        }
        Response res = null;
        int statusCode = 0 ;
        try {
            res = uploadManager.put(filePath, fileName,uploadToken);//fileName是key,在7牛相当于文件名
            statusCode = res.statusCode;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
        }
        return statusCode;
    }

	public static String getFileName(){
		String fileName = "";

		return fileName;
	}

	/**
	 * 角色类型
	 *
	 */
	public static class DomainToBucket {
		/**
		 * 默认资源空间
		 */
		public static String  DOMAIN_DEFAULT = "cdn";

		public static Map<String, String> DomainToBucketMap = new HashMap<String, String>();

		static {
			DomainToBucketMap.put(DOMAIN_DEFAULT, "http://www.cdn.com/");
		}
	}


}
