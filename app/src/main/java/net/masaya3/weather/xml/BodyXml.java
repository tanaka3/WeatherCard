package net.masaya3.weather.xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class BodyXml {
    public List<MeteorologicalinfosXml> MeteorologicalinfosXmls = new ArrayList<MeteorologicalinfosXml>();


    /**
     * コンストラクタ
     */
    public BodyXml(){
    }


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static BodyXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        BodyXml bodyXml = new BodyXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("meteorologicalinfos")){
                            MeteorologicalinfosXml meteorologicalinfosXml = MeteorologicalinfosXml.parse(xml);
                            if(meteorologicalinfosXml != null){
                                bodyXml.MeteorologicalinfosXmls.add(meteorologicalinfosXml);
                            }
                        }
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return bodyXml;
    }
}
