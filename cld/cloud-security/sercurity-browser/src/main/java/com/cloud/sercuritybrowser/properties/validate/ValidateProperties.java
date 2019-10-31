package com.cloud.sercuritybrowser.properties.validate;

import com.cloud.sercuritybrowser.properties.validate.base.ValidateBaseCodeProperties;
import com.cloud.sercuritybrowser.properties.validate.image.ValidateImageCodeProperties;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/28 16:41
 * @Modified By:
 */
public class ValidateProperties {

    //validate --> image
    private ValidateImageCodeProperties image = new ValidateImageCodeProperties();

    // calidate ---> base
    private ValidateBaseCodeProperties base =  new ValidateBaseCodeProperties();

    public ValidateBaseCodeProperties getBase() {
        return base;
    }

    public void setBase(ValidateBaseCodeProperties base) {
        this.base = base;
    }

    public ValidateImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ValidateImageCodeProperties image) {
        this.image = image;
    }
}
