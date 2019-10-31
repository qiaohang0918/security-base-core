package com.cloud.cloudcommon.utils;

import java.util.List;
import java.util.Set;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/21 15:16
 * @Modified By:
 */
public class CommonSqlConstruct {

    /**
     * list to sql
     * @param list
     * @return
     */
    public static String constructListToStringsOnIn(List<String> list , Set<String> set){
        StringBuffer buffer = new StringBuffer(" ");
        if(list!=null  && list.size()>0){
            for (Object o : list) {
                buffer.append("'"+o+"',");
            }
        }else if(set != null && set.size()>0){
            for (Object o : set) {
                buffer.append("'"+o+"',");
            }
        }
        String replace = buffer.replace(buffer.length() - 1, buffer.length(), "").toString();
        return  replace==null || "".equals(replace.trim()) ? null : replace;
    }

    /**
     * strings to  sql
     * @param someS
     * @param seperator
     * @return
     */
    public static String constructStringToStringsOnIn(String someS,String seperator){
        if(someS==null || "".equals(someS.trim()))
            return null;
        //默认分隔符
        String realSeperator = ",";
        if(seperator!=null && !"".equals(seperator.trim()))
            realSeperator=seperator;
        String[] split = someS.split(realSeperator);

        StringBuffer buffer = new StringBuffer(" ");
        for (String someOne : split) {
            buffer.append("'"+someOne+"',");
        }
        String replace = buffer.replace(buffer.length() - 1, buffer.length(), "").toString();
        return  replace==null || "".equals(replace.trim()) ? null : replace;
    }

}
