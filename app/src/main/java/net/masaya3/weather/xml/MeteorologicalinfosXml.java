package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class MeteorologicalinfosXml {

    public TimeseriesinfoXml timeseriesinfoXml;


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static MeteorologicalinfosXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        MeteorologicalinfosXml me = new MeteorologicalinfosXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("timeseriesinfo")){
                            me.timeseriesinfoXml = TimeseriesinfoXml.parse(xml);
                        }
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return me;
    }
}
