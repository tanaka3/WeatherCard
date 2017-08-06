package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class TitleXml {
    public String title = "";


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static TitleXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        if(!xml.getName().equalsIgnoreCase("title")) {
            return null;
        }

        TitleXml me = new TitleXml();

        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.TEXT:
                        me.title = xml.getText().trim();
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return me;
    }
}

