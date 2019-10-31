package com.cloud.sercuritybrowser.properties;

import com.cloud.sercuritybrowser.properties.validate.ValidateProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 13:57
 * @Modified By:
 */
public class BrowserProperties {

    private String loginPage = "/standardLoginPage.html";

    private ReturnType returnType = ReturnType.HTML;

    private ValidateProperties validate = new ValidateProperties();


    //  remember-me default expire time
    private int tokenExpireSecend = 3600;

    public int getTokenExpireSecend() {
        return tokenExpireSecend;
    }

    public void setTokenExpireSecend(int tokenExpireSecend) {
        this.tokenExpireSecend = tokenExpireSecend;
    }

    public ValidateProperties getValidate() {
        return validate;
    }

    public void setValidate(ValidateProperties validate) {
        this.validate = validate;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

}
