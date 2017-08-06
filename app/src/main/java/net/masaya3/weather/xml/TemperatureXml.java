package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class TemperatureXml {
    public int refid;
    public int temperature = 0;


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static TemperatureXml parse(XmlPullParser xml) {
        if(!xml.getName().equalsIgnoreCase("temperature")){
            return null;
        }
        //タグ位置
        int depth = xml.getDepth();

        TemperatureXml me = new TemperatureXml();
        for( int a = 0; a < xml.getAttributeCount(); a++ ) {

            if(xml.getAttributeName(a).equalsIgnoreCase("refid")){
                me.refid =Integer.parseInt(xml.getAttributeValue(a));
            }
        }

        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.TEXT:
                        me.temperature = Integer.parseInt(xml.getText().trim());
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return me;
    }
}
