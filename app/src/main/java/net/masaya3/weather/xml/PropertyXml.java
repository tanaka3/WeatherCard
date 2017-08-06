package net.masaya3.weather.xml;

import net.masaya3.weather.model.WeatherInfo;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class PropertyXml {

    public WeatherpartXml weatherpartXml;
    public TemperaturepartXml temperaturepartXml;
    public ProbabilityofprecipitationpartXml probabilityofprecipitationpartXml;

    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static PropertyXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        PropertyXml me = new PropertyXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("weatherpart")){
                            me.weatherpartXml = WeatherpartXml.parse(xml);
                        }

                        if(xml.getName().equalsIgnoreCase("probabilityofprecipitationpart")){
                            if(xml.getName().equalsIgnoreCase("probabilityofprecipitationpart")){
                                me.probabilityofprecipitationpartXml = ProbabilityofprecipitationpartXml.parse(xml);
                            }

                        }


                        if(xml.getName().equalsIgnoreCase("temperaturepart")){
                            if(xml.getName().equalsIgnoreCase("temperaturepart")){
                                me.temperaturepartXml = TemperaturepartXml.parse(xml);
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
