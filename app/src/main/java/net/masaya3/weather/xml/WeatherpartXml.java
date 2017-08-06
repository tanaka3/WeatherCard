package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class WeatherpartXml {
    public HashMap<Integer, WeatherXml> weatherXmlHashMap = new HashMap<Integer, WeatherXml>();

    public int refid;
    public String weather = "";
    /**
     * コンストラクタ
     */
    public WeatherpartXml(){
    }


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static WeatherpartXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        WeatherpartXml me = new WeatherpartXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("weather")){
                            WeatherXml child = WeatherXml.parse(xml);
                            if(child != null){
                                me.weatherXmlHashMap.put(child.refid, child);
                            }
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
