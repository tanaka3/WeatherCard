package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class ItemXml {
    public List<KindXml> kindXmls = new ArrayList<KindXml>();

    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static ItemXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        ItemXml me = new ItemXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("kind")){
                            KindXml child = KindXml.parse(xml);
                            if(child != null){
                                me.kindXmls.add(child);
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
