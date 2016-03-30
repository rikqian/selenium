package com.imooly_at.tools;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * Created by qianqiang on 15-1-20.
 * Use a certain user id to delete all related order records in MongoDB and OracleDB.
 */
public class clearOrders {


    public static void clear(String uid) {
        /*删除Mongo中对于uid的订单数据
        * order
        * ordersettle
        * ordertrade
        * orderaddress
        * */


        db odb = new db();

        Mongo mg = null;
        String host = "192.168.10.55:27018";

        try {
            mg = new Mongo(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DB db = mg.getDB("order");
        db.authenticate("root", "123456".toCharArray());

        DBCollection dc = db.getCollection("order");
        DBCollection dc_address = db.getCollection("orderaddress");
        DBCollection dc_ordertrade = db.getCollection("ordertrade");
        DBCollection dc_ordersettle = db.getCollection("ordersettle");


        DBObject ref = new BasicDBObject();
        DBObject okeys = new BasicDBObject();
        DBObject pkeys = new BasicDBObject();
        DBObject rkeys = new BasicDBObject();

        //ref.put("status",0);
        //ref.put("type",4);
        ref.put("uid", uid);
        okeys.put("orderno", 1);
        okeys.put("_id", 0);
        pkeys.put("payno", 1);
        pkeys.put("_id", 0);
        rkeys.put("receiverid", 1);
        rkeys.put("_id", 0);


        //获取订单号
        DBCursor orders = dc.find(ref, okeys);
        DBCursor orders1 = orders.copy();
        DBCursor orders2 = orders.copy();

        int number = orders.size();
        //数组获取
        /*
        String[] ao,ap,ar;
        ao = new String[number];

        int n = 0;
        while(orders.hasNext())
        {
            ao[n] = orders.next().get("orderno").toString();
            n++;
        };
        */
        //获取支付单号
        DBCursor payno = dc.find(ref, pkeys);
        //数组获取
        /*
        ap = new String[number];
        int m = 0;
        while(payno.hasNext())
        {
            ap[m] = payno.next().get("payno").toString();
            m++;
        }*/
        //获取支付地址id
        DBCursor rid = dc.find(ref, rkeys);

        //数组获取
        /*
        ar = new String[number];
        int l = 0;

        while(rid.hasNext())
        {
            ar[l] = rid.next().get("receiverid").toString();
            l++;
        }
        List<DBObject> ArrayAddr = rid.toArray();
*/
        //删除orderaddress
        while (rid.hasNext()) {
            dc_address.remove(rid.next());

        }


        //删除ordertrade
        while (orders.hasNext()) {
            dc_ordertrade.remove(orders.next());

        }

        //删除ordersettle
        while (orders1.hasNext()) {
            dc_ordersettle.remove(orders1.next());

        }


        //删除order
        while (orders2.hasNext()) {
            dc.remove(orders2.next());

        }


        //删除Oracle中mpay_order中支付单号
        while (payno.hasNext()) {

            try {
                odb.executeUpdate("delete from mpay_order where orderno = '" + payno.next().get("payno").toString() + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }








/*        WriteResult rs = dc.remove(new BasicDBObject("businessid", uid));
        System.out.println("Remove records NO.: "+rs.getN());
        System.out.println("Remove records INFO: "+rs.toString());*/
        //关闭Mongo连接
        dc.getDB().getMongo().close();
        odb.close();


    }

    public static void main(String[] args) {

        clearOrders.clear("bdd2ce29-74f0-43ff-af17-d6b38d4954e3");

    }
}
