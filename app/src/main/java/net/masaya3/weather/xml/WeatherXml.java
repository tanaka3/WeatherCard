package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class WeatherXml {
    public int refid;
    public String weather = "";


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static WeatherXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        if(!xml.getName().equalsIgnoreCase("weather")) {
            return null;
        }

        WeatherXml me = new WeatherXml();

        for( int a = 0; a < xml.getAttributeCount(); a++ ) {

            if(xml.getAttributeName(a).equalsIgnoreCase("refid")){
                me.refid =Integer.parseInt(xml.getAttributeValue(a));
            }
        }

        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.TEXT:
                        me.weather = xml.getText().trim();
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return me;
    }
}
