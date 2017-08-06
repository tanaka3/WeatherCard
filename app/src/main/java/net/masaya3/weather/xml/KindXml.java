package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class KindXml {
    public PropertyXml propertyXml;

    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static KindXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        KindXml me = new KindXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("property")){
                            me.propertyXml= PropertyXml.parse(xml);
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
