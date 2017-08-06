package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class HeadXml {
    public TitleXml titleXml;

    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static HeadXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        HeadXml me = new HeadXml();
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("title")){
                            me.titleXml= TitleXml.parse(xml);
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
