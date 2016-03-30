package com.imooly_at.tools;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import java.sql.*;

/**
 * Created by qianqiang on 15-1-16.
 * Oracle DB connection methods, used to be revoked by other test cases.
 */

public class db {


    //dbUrl数据库连接串信息，其中“1521”为端口，“ora9”为sid
    String dbUrl = "jdbc:oracle:thin:@192.168.10.42:1521:mdtest";
    //theUser为数据库用户名
    String theUser = "mb_md_user";
    //thePw为数据库密码
    String thePw = "mb_md_user";
    //几个数据库变量
    Connection c = null;
    Statement conn;
    ResultSet rs = null;

    /*构造方法，创建初始化连接    */
    public db() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            //与url指定的数据源建立连接
            c = DriverManager.getConnection(dbUrl, theUser, thePw);
            //采用Statement进行查询
            conn = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*创建查询方法，进行查询执行
    * 返回结果集ResultSet
    *
    * */
    public ResultSet executeQuery(String sql) {
        rs = null;
        try {
            rs = conn.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String sql) throws SQLException {

        return conn.executeUpdate(sql);

    }


    /*关闭数据库连接*/
    public void close() {
        try {
            conn.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*测试常用方法调用*/
    public static void main(String[] args) {
        ResultSet newrs;
        db newjdbc = new db();
        newrs = newjdbc.executeQuery("select id from mb_business_classify t where caption = '电玩'");

        String sname = "qqstat2";
        String fname = "qqstat2";
        newrs = newjdbc.executeQuery("select * from mb_business where shortname = '" + sname + "' and name = '" + fname + "'");

        try {
            while (newrs.next()) {
                String s1 = newrs.getString("shortname");
                System.out.print(newrs.getString("name"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet obid = newjdbc.executeQuery("select id from mb_business where type = 4 and status != 3");
        String[] ob = new String[1000];
        String[] mb = new String[1000];
        int n = 0;
        try {
            while (obid.next()) {
                ob[n] = obid.getString("id");
                n++;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        DBCollection mdc = null;
        try {
            mdc = mongodb.getdb("business");
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();

        }
        BasicDBObject query = new BasicDBObject();
        query.put("type", 4);
        query.put("status", new BasicDBObject("$ne", 3));
        DBCursor dbobj = mdc.find(query, new BasicDBObject("businessid", true));
        int m = 0;

        while (dbobj.hasNext()) {
            mb[m] = dbobj.next().get("businessid").toString();
            m++;

        }
        newjdbc.close();

    }

}