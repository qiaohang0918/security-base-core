package com.cloud.cloudcommon.utils;

import java.util.ArrayList;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/8/22 12:54
 * @Modified By:
 */
public class PattenCharsConcatUtil {

    public static void main(String[] args) {
        String andChangeBigChar = findAndChangeBigChar("aaBBcDe", "upper", "_._", "suffix");
        System.out.println(andChangeBigChar);
    }

    //转换   aaaBcc   -->    aaa_bcc  （一道面试题）
    /**
     * create by Qiaohang
     * @param str   源字符串
     * @param wantChange    想要转换的字符类型 (lower / upper )
     * @param changePatten  转换规则 ( 可以是字符串 (aaa) ，也可以是任意字符 ( " _  - * ") ，特殊字符需要转义 (  "\\."  "\\+" ) )
     * @param position  心如的规则字符出现的位置 (相对于目标字符串)   [ prefix / suffix]
     * @return  返回转化后的字符串
     * example  : findAndChangeBigChar("abQcdEfg","upper","_","suffix");   ---->     [beforeChange : abQcdEfg]----------------[afterChange : abq_cde_fg]
     * example  : findAndChangeBigChar("abQQQQcdEfg","upper","_","prefix");   ---->  [beforeChange : abQQQQcdEfg]----------------[afterChange : ab_q_q_q_qcd_efg]
     * example  : findAndChangeBigChar("abQQQQcdEfg","upper",".","prefix");   ---->  [beforeChange : abQQQQcdEfg]----------------[afterChange : ab.q.q.q.qcd.efg]
     * example  : findAndChangeBigChar("abQQQQcdEfg","upper",".","prefix");   ---->  [beforeChange : abQQQQcdEfg]----------------[afterChange : ab.q.q.q.qcd.efg]
     * example  : findAndChangeBigChar("abQQQQcdEfg","lower",".","prefix");   ---->  [beforeChange : abQQQQcdEfg]----------------[afterChange : .A.BQQQQ.C.DE.F.G]
     * example  : findAndChangeBigChar("abQQQQcdEfg","lower",">","prefix");   ---->  [beforeChange : abQQQQcdEfg]----------------[afterChange : >A>BQQQQ>C>DE>F>G]
     */
    public static String findAndChangeBigChar(String str,String wantChange,String changePatten,String position){
        // position-- 前缀/后缀 (默认前缀prefix)
        position= ( position==null || "".equals(position.trim()) )? "prefix" : position;
        //  changePatten 转换规则
        changePatten =( changePatten==null || "".equals(changePatten.trim()) )? "_" : changePatten;
        // changePatten规则长度
        int pattenLength=changePatten.length();
        // wantChange --- 想要转换的字符(lower / upper)  ,默认转换大写 upper
        wantChange = ( wantChange==null || "".equals(wantChange.trim()) )? "upper" : wantChange;

        StringBuffer buffer = new StringBuffer();
        buffer.append(str);
        char[] chars = str.toCharArray();
        ArrayList<Integer> list = new ArrayList<>();
        if(chars!=null && chars.length>0){

            for (int i = 0; i < chars.length; i++) {
                if("upper".equals(wantChange)){
                    if(chars[i]<90)
                        list.add(i);
                }else if("lower".equals(wantChange)){
                    if(chars[i]>90)
                        list.add(i);
                }else {
                    return str;
                }
            }
        }
       // System.out.println(list);
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                char baseChar=(char)(buffer.toString().charAt(list.get(i)+i*pattenLength) + ("upper".equals(wantChange) ? 32 : -32));
                buffer.replace(list.get(i)+i*pattenLength,list.get(i)+i*pattenLength+1, "prefix".equals(position) ? changePatten+baseChar : baseChar+changePatten);
            }
        }
        return "[beforeChange : "+str+"]----------------[afterChange : "+buffer.toString()+"]";
    }
}
