package com.cloud.securitycore.config;

import com.cloud.sercuritybrowser.properties.BrowserProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 14:08
 * @Modified By:
 */
@ConfigurationProperties(prefix = "qiaohang.security")
public class SecurityProperties {

    BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
