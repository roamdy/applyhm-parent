package com.applyhm.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.applyhm.bean.GsonModel;

/***
 *
 * 请求工具类; 返回json数据;
 */
public class ResponseUtil {

    private static final Logger logger = LogManager.getLogger(ResponseUtil.class.getName());

    public static final String JSON_API_INTERNET_BUSY = "{\"error_code\":200,\"data\":\"服务器忙，请稍后再试！\"}";

    /***
     * 默认使用的;
     * @param data
     * @return
     */
    public static String succ(Object data) {
        try {
            GsonModel model = new GsonModel(GsonModel.SUCCESS, data);
            String result = GSonUtil.getJson(model);
            return result;
        } catch (Exception exp) {
            exp.printStackTrace();
            Log4jUtil.exception(exp);
        }
        return JSON_API_INTERNET_BUSY;
    }

    /***
     * 含有版本信息的返回;
     * @param appVersion
     * @param data
     * @return
     */
    public static String succ(double appVersion, Object data) {
        try {
            GsonModel model = new GsonModel();
            model.setError_code(0);
            model.setData(data);
            String result = GSonUtil.getJson(appVersion, model);
            return result;
        } catch (Exception exp) {
            exp.printStackTrace();
            Log4jUtil.exception(exp);
        }
        return JSON_API_INTERNET_BUSY;
    }

    /***
     * 状态码和自定义的错误信息;
     * @param code
     * @param data
     * @return
     */
    public static String error(int error_code, String data) {
        try {
            GsonModel model = new GsonModel();
            model.setError_code(error_code);
            model.setData(data);
            String result = GSonUtil.getJson(model);
            return result;
        } catch (Exception exp) {
            exp.printStackTrace();
            Log4jUtil.exception(exp);
        }
        return JSON_API_INTERNET_BUSY;
    }
}