package com.imooly_at.test;

import com.imooly_at.tools.db;
import com.imooly_at.tools.mongodb;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestSS {


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    static String dateStr = sdf.format(new Date());
    WebDriver driver = com.imooly_at.bms.loginBMS.driver;


    String gpname = "atGoodparam" + dateStr;

    public static void checklist() {

        List<String> list = new ArrayList<String>();
        String s1 = "china", s2 = "usa", s3 = "canada", s4 = "indian";
        String[] s = {"Japan", "Korean"};

        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("LIST ELEMENT " + i + " : " + list.get(i));
        }

    }

    public static void removeMBI(String bid) {
        DBCollection dc = null;
        try {
            dc = mongodb.getdb("business_copy");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        WriteResult rs = dc.remove(new BasicDBObject("businessid", bid));
        System.out.println("Remove records NO.: " + rs.getN());
        System.out.println("Remove records INFO: " + rs.toString());
        //关闭Mongo连接
        dc.getDB().getMongo().close();

    }


    public static void testArray() {
        String bid = "0262582B-29C7-46DB-6BDB-ACBA0E14ABA6";
        bid = "996E12A9-0FCE-91FB-7CE9-14AB6E9B1068";

        db db = new db();
        int i = 0;

        List<String> list = new ArrayList<String>();
        ResultSet rs = db.executeQuery("select id from mb_businesser where businessid = '" + bid + "'");

        String baccountrole = null;
        try {
            while (rs.next()) {
                baccountrole = "delete from mb_businesser_role_relation where businesserid = '" + rs.getString("id") + "'";
                list.add(baccountrole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void out1() {
        System.out.println(gpname);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void out2() {
        System.out.println(gpname);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void out3() {
        System.out.println(gpname);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        //removeMBI("98BF3769-0EA0-572E-241A-2B0D8142CD70");
        //testArray();
/*        TestSS t = new TestSS();
        t.out1();
        Thread.sleep(1000);
        t.out2();
        Thread.sleep(1000);
        t.out3();
        Thread.sleep(1000);*/

    }


}