package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class ProbabilityofprecipitationpartXml {
    public HashMap<Integer, ProbabilityofprecipitationXml> probabilityofprecipitationpartXmlHashMap = new HashMap<Integer, ProbabilityofprecipitationXml>();


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static ProbabilityofprecipitationpartXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        ProbabilityofprecipitationpartXml me = new ProbabilityofprecipitationpartXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("probabilityofprecipitation")){
                            ProbabilityofprecipitationXml child = ProbabilityofprecipitationXml.parse(xml);
                            if(child != null){
                                me.probabilityofprecipitationpartXmlHashMap.put(child.refid, child);
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
