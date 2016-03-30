package com.imooly_at.tools;

import com.mongodb.*;

/**
 * Created by qianqiang on 14-10-20.
 * Offer the basic method to connect mongodb.
 */
public class mongodb {

    private static Mongo mg = null;
    private static DB db;
    private static DBCollection table;
    private static String host = "192.168.10.55:27018";

    public static Mongo mdb() throws java.net.UnknownHostException {
        mg = new Mongo(host);
        return mg;
    }

    public static DBCollection getdb(String tablename) throws java.net.UnknownHostException {

        mg = new Mongo(host);
        db = mg.getDB("md");
        db.authenticate("root", "123456".toCharArray());
        table = db.getCollection(tablename);

        return table;
    }

    public static void main(String[] args) {

        DBCollection dc = null;
        try {
            dc = getdb("goods");
        } catch (java.net.UnknownHostException e) {
            e.printStackTrace();
        }

        DBObject dbobj = dc.findOne(new BasicDBObject("name", "atgood_20141025220434"), new BasicDBObject("code", true));
        String s, s1 = null;
        s = (String) dbobj.get("code");
        s1 = (String) dbobj.get("_id");
        System.out.println(s);
        System.out.print(table.findOne(new BasicDBObject("name", "atgood_20141025220434"), new BasicDBObject("code", true)
        ));
        //s = table.findOne(new BasicDBObject("name", "atgood_20141020160938"), new BasicDBObject("code", true));


    }


}
