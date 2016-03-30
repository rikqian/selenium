package com.imooly_at.tools;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianqiang on 14-12-19.
 * Get all the businessinfo in MongoDB and OracleDB, in order to compare the difference between the two databases.
 */
public class findBusiness {

    public static String[] mb = null;
    public static DBCollection dc = null;


    public static void main(String[] args) throws SQLException {
        try {
            dc = mongodb.getdb("business");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        DBObject ref = new BasicDBObject();
        DBObject keys = new BasicDBObject();

        //ref.put("status",0);
        //ref.put("type",4);
        keys.put("businessid", 1);
        keys.put("_id", 0);
        //Mongo数据集合
        DBCursor mdbo = dc.find(ref, keys);
        DBCursor mdbo1 = dc.find(ref, keys);

        Integer n = mdbo.size();


        db odb = new db();
        String ostatement = "select id from mb_business where type !=3";
        ResultSet odbs = odb.executeQuery(ostatement);

        List<String> olist = new ArrayList<String>();
        String otemp = null;
        while (odbs.next()) {
            otemp = odbs.getString("id");
            olist.add(otemp);
        }

        /*查找存在于oracle，不存在于Mongo的businessid*/
        String sbn = null;
        while (mdbo.hasNext()) {
            sbn = (String) mdbo.next().get("businessid");
            if (olist.contains(sbn)) {
                //System.out.println("Exist in Oracle->ID: "+sbn);
            } else {
                System.out.println("NOT exist in Oracle->ID: " + sbn);
            }
        }

        /*查找存在于oracle，不存在于Mongo的businessid*/
        String obn = null;
        List<String> mlist = new ArrayList<String>();

        while (mdbo1.hasNext()) {
            mlist.add((String) mdbo1.next().get("businessid"));
        }

        for (int i = 0; i < olist.size(); i++) {
            obn = olist.get(i);

            if (mlist.contains(obn)) {
                //System.out.println("Exist in Oracle->ID: "+obn);
            } else {
                System.out.println("NOT exist in MONGODB->ID: " + obn);
                System.out.println();
            }
        }


        System.out.println("#############END##############");

    }
}
