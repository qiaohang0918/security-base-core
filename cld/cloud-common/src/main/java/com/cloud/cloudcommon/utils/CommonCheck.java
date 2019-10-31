package com.cloud.cloudcommon.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/27 14:26
 * @Modified By:
 */
public class CommonCheck {

    /**
     * strings is null
     * @param strings
     * @return
     */
    public static boolean nullStrings(String... strings){
        if(strings == null || strings.length<=0)
            return true;
        for (String string : strings) {
            if(StringUtils.isBlank(string))
                return true;
        }
        return false;
    }

    /**
     * list is null
     * @param list
     * @return
     */
    public static  boolean nullList(List list){
        if(list==null || list.size()<=0)
            return true;
        else
            return false;
    }


    /**
     * list is null
     * @param set
     * @return
     */
    public static  boolean nullList(Set set){
        if(set==null || set.size()<=0)
            return true;
        else
            return false;
    }


    /**
     * map is null
     * @param map
     * @return
     */
    public static boolean nullMap(Map<String,Object> map){
        if(map==null || map.size()<=0)
            return true;
        else
            return false;
    }

    /**
     * 检查int结构
     * @param parseWaiting
     * @return
     */
    public static boolean checkInt(String parseWaiting){
        try {
            Integer.parseInt(parseWaiting);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 检查double结构
     * @param parseWaiting
     * @return
     */
    public static boolean chechDouble(String parseWaiting){
        try {
            Double.parseDouble(parseWaiting);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
