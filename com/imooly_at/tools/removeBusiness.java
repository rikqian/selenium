package com.imooly_at.tools;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianqiang on 14-12-24.
 * Remove all related business info from the MongoDB and Oracle by a certain business id.
 */
public class removeBusiness {


    public static void removeOBI(String bid) {
        List<String> list = new ArrayList<String>();
        db db = new db();
        String[] ber = null;

        int i = 0;
        ResultSet rs = db.executeQuery("select id from mb_businesser where businessid = '" + bid + "'");


        //删除商家信息
        String binfo = "delete from mb_business where id = '" + bid + "'";
        list.add(binfo);
        //删除商家角色关系
        String brole = "delete from mb_business_role_relation where businessid = '" + bid + "'";
        list.add(brole);
        //删除商家上下级关系
        String brel = "delete from mb_business_relation where businessid = '" + bid + "'";
        list.add(brel);

        //删除商家支付关系
        String bpay = "delete from mb_pay_assign where id = '" + bid + "'";
        list.add(bpay);


        //删除商家账号角色关系
        String baccountrole = null;
        try {
            while (rs.next()) {
                baccountrole = "delete from mb_businesser_role_relation where businesserid = '" + rs.getString("id") + "'";
                list.add(baccountrole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //删除商家账号

        String baccount = "delete from mb_businesser where businessid = '" + bid + "'";
        list.add(baccount);
        //删除商家联系人
        String bcontract = "delete from mb_business_contract where businessid = '" + bid + "'";
        list.add(bcontract);
        //删除商家钱包
        String bwallet = "delete from mb_business_wallet where businessid = '" + bid + "'";
        list.add(bwallet);

        //删除商家分类
        String bclassify = "delete from mb_business_classify_relation where businessid = '" + bid + "'";
        list.add(bclassify);
        String buclassify = "delete from mb_business_uclassify_relation where businessid = '" + bid + "'";
        list.add(buclassify);

        //删除商家邮费规则
        String bdiscount = "delete from mb_business_discount where businessid = '" + bid + "'";
        //list.add(bdiscount);
        //删除商家银行卡
        String bcard = "delete from mb_business_card where businessid = '" + bid + "'";
        //list.add(bcard);


        //删除商家订单
        //删除商家商品



        /*Debug INFO*/

        /*移除oracle DB中所有该商家相关信息*/
        for (int n = 0; n < list.size(); n++) {
            try {
                int r = db.executeUpdate(list.get(n));
                System.out.print("Result for '" + list.get(n) + "': ");
                if (r == 0) {
                    System.out.println("NO record is REMOVED.");
                } else {
                    System.out.println(r + " records is REMOVED.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        db.close();

    }


    public static void removeMBI(String bid) {
        DBCollection dc = null;
        try {
            dc = mongodb.getdb("business");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        WriteResult rs = dc.remove(new BasicDBObject("businessid", bid));
        System.out.println("Remove records NO.: " + rs.getN());
        System.out.println("Remove records INFO: " + rs.toString());
        //关闭Mongo连接
        dc.getDB().getMongo().close();

    }


    public static void main(String[] args) {
        String bid = "0EBA85C3-3EA0-78D0-E805-946B8545FD58";
        removeOBI(bid);
        removeMBI(bid);
    }
}
