package com.cloud.cloudcommon.pojo;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/21 14:50
 * @Modified By:
 */
public class CommonParm<T> {

    public T data;

    public String description;

    @Override
    public String toString() {
        return "CommonParm{" +
                "data=" + data +
                ", description='" + description + '\'' +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommonParm(T data, String description) {
        this.data = data;
        this.description = description;
    }

    public CommonParm() {
    }

    public CommonParm(T data) {
        this.data = data;
    }
}
