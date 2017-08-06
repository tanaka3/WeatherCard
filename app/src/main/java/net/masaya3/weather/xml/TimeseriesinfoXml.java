package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class TimeseriesinfoXml {

    public List<ItemXml> itemsXml = new ArrayList<ItemXml>();
    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static TimeseriesinfoXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        TimeseriesinfoXml me = new TimeseriesinfoXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("item")){
                            ItemXml itemXml = ItemXml.parse(xml);
                            if(itemXml != null){
                                me.itemsXml.add(itemXml);
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
