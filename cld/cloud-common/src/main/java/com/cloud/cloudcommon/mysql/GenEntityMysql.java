package com.cloud.cloudcommon.mysql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Aurher: QiaoHang
 * @Description:
 * @Data: 2019/9/20 8:51
 * @Modified By:
 */
public class GenEntityMysql {
    private String projectName="cloud-common";
    private String packageOutPath = "com.cloud.cloudcommon.pojo";//指定实体生成所在包的路径com.cloud.cloudcommons.pojo
    //C:\Users\xltc-1\Desktop\cld\cloud-commons\src\main\java\com\cloud\cloudcommons\pojo
    private String authorName = "lry";//作者名字
    private String tablename = "";//表名
    private String[] colnames; // 列名数组
    private String[] colTypes; //列名类型数组
    private int[] colSizes; //列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
    private boolean f_jpa = true; // 是否需要生成基于注解的JPA实体对象

    //数据库连接
    private static final String URL ="jdbc:mysql://192.168.111.129:3306/test";
    private static final String NAME = "root";
    private static final String PASS = "root";
    private static final String DRIVER ="com.mysql.jdbc.Driver";

    /*
     * 构造函数
     */
    public GenEntityMysql(){
        List<String> list=getTableName();
        for(int p=0;p<list.size();p++){
            tablename=list.get(p);
            //创建连接
            Connection con;
            //查要生成实体类的表
            String sql = "select * from " + tablename;
            PreparedStatement pStemt = null;
            try {
                try {
                    Class.forName(DRIVER);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                con = DriverManager.getConnection(URL,NAME,PASS);
                pStemt = con.prepareStatement(sql);
                ResultSetMetaData rsmd = pStemt.getMetaData();
                int size = rsmd.getColumnCount();   //统计列
                colnames = new String[size];
                colTypes = new String[size];
                colSizes = new int[size];
                for (int i = 0; i < size; i++) {
                    colnames[i] = rsmd.getColumnName(i + 1);
                    colTypes[i] = rsmd.getColumnTypeName(i + 1);

                    if(colTypes[i].equalsIgnoreCase("datetime")){
                        f_util = true;
                    }
                    if(colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")){
                        f_sql = true;
                    }
                    colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
                }

                File directorys = new File("");// 参数为空
                String courseFile = directorys.getCanonicalPath();
                String content = parse(colnames,colTypes,colSizes);
                try {
                    File directory = new File("");
//                    String outputPath = "C:\\Users\\xltc-1\\Desktop\\cld\\cloud-commons\\src\\main\\java\\com\\cloud\\cloudcommons\\pojo".replace(".", "\\")+"\\"+ initcap(tablename) + ".java";
                    String outputPath = (courseFile+"\\"+projectName+"\\src\\main\\java\\")+packageOutPath.replace(".", "\\")+"\\"+ initcap(tablename) + ".java";
                    FileWriter fw = new FileWriter(outputPath);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(content);
                    pw.flush();
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } finally{
                //          try {
                //              con.close();
                //          } catch (SQLException e) {
                //              // TODO Auto-generated catch block
                //              e.printStackTrace();
                //          }
            }
        }
        System.out.println("生成完毕！");
    }


    /**
     * Java方法 得到当前数据库下所有的表名
     * @param con
     */
    private List<String> getTableName() {
        List<String> list=new ArrayList<String>();
        try {
            DatabaseMetaData meta = DriverManager.getConnection(URL,NAME,PASS).getMetaData();
            ResultSet rs = meta.getTables(null, null, null,new String[]{"TABLE"});
            while (rs.next()) {
                list.add(rs.getString(3));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 功能：生成实体类主体代码
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n");
        sb.append("\r\n");

        //判断是否导入工具包
        if(f_util){
            sb.append("import java.util.Date;\r\n");
        }
        if(f_sql){
            sb.append("import java.sql.*;\r\n");
        }

        //jpa
        if(f_jpa){
            sb.append("import javax.persistence.Entity;\r\n");
            sb.append("import javax.persistence.GeneratedValue;\r\n");
            sb.append("import javax.persistence.GenerationType;\r\n");
            sb.append("import javax.persistence.Id;\r\n\r\n");
        }

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * "+tablename+" 实体类\r\n");
        sb.append(" * "+new Date()+"\r\n");
        sb.append(" * @"+this.authorName+"\r\n");
        sb.append(" */ \r\n");

        if(f_jpa){
            sb.append("@Entity\r\n");
        }
        //实体部分
        sb.append("public class " + initcap(tablename) + "{\r\n\r\n");
        processAllAttrs(sb);//属性
        processAllMethod(sb);//get set方法
        sb.append("}\r\n");

        //System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 功能：生成所有属性
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n");
        }
        sb.append("\r\n");
    }

    /**
     * 功能：生成所有方法
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {

        for (int i = 0; i < colnames.length; i++) {

            if(f_jpa){
                if(i==0){
                    sb.append("\t@Id\r\n");
                    sb.append("\t@GeneratedValue(strategy = GenerationType.AUTO)\r\n");
                    sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
                }else{
                    sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
                }
            }else{
                sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
            }
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");
            sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + "){\r\n");
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");
        }

    }

    /**
     * 功能：将输入字符串的首字母改成大写
     * @param str
     * @return
     */
    private String initcap(String str) {

        char[] ch = str.toCharArray();
        if(ch[0] >= 'a' && ch[0] <= 'z'){
            ch[0] = (char)(ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 功能：获得列的数据类型
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {

        if(sqlType.equalsIgnoreCase("bit")){
            return "boolean";
        }else if(sqlType.equalsIgnoreCase("tinyint")){
            return "byte";
        }else if(sqlType.equalsIgnoreCase("smallint")){
            return "short";
        }else if(sqlType.equalsIgnoreCase("int")||sqlType.equalsIgnoreCase("INT UNSIGNED")){
            //INT UNSIGNED无符号整形
            return "int";
        }else if(sqlType.equalsIgnoreCase("bigint")){
            return "long";
        }else if(sqlType.equalsIgnoreCase("float")){
            return "float";
        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")){
            return "double";
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")){
            return "String";
        }else if(sqlType.equalsIgnoreCase("datetime")){
            return "Date";
        }else if(sqlType.equalsIgnoreCase("image")){
            return "Blod";
        }
        return null;
    }

    /**
     * 出口
     * TODO
     * @param args
     */
    public static void main(String[] args) {
        new GenEntityMysql();
    }
}
