package com.applyhm.bean;

import com.google.gson.annotations.SerializedName;

/***
 *
 * 服务器端返回Json参数给客户端时进行的数据传输Json规则;
 *
 */
public class GsonModel {

    public static final int SUCCESS = 0;

    @SerializedName("error_code")
    private int error_code;

    @SerializedName("data")
    private Object data;

    public static GsonModel create(int error_code, Object data) {
        GsonModel gsonModel = new GsonModel();
        gsonModel.setError_code(error_code);
        gsonModel.setData(data);
        return gsonModel;
    }

    public GsonModel() {
        super();
    }

    public GsonModel(Object data) {
        super();
        this.data = data;
    }

    public GsonModel(int error_code, Object data) {
        super();
        this.error_code = error_code;
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + error_code;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GsonModel other = (GsonModel) obj;
        if (error_code != other.error_code)
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        return true;
    }

}