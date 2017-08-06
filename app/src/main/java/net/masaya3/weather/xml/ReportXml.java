package net.masaya3.weather.xml;

import android.text.Html;

import net.masaya3.weather.model.WeatherInfo;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by masaya3 on 2017/08/06.
 */

public class ReportXml {

    public BodyXml bodyXml;
    public HeadXml headXml;

    /**
     * コンストラクタ
     */
    public ReportXml(){
    }


    /**
     *
     * @param xml
     * @param tag
     * @return
     */
    public static ReportXml parse(XmlPullParser xml) {

        //タグ位置
        int depth = xml.getDepth();

        ReportXml report = new ReportXml();

        //内部にMenuItemが存在しているかチェックする
        try {
            for(int type  = xml.next();!(type == XmlPullParser.END_TAG  && depth == xml.getDepth()) ; type = xml.next()) {

                switch(type){
                    case XmlPullParser.START_TAG:
                        if(xml.getName().equalsIgnoreCase("body")){
                            report.bodyXml = BodyXml.parse(xml);
                        }

                        if(xml.getName().equalsIgnoreCase("head")){
                            report.headXml = HeadXml.parse(xml);
                        }
                        break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return report;
    }

    public WeatherInfo get(int refid){

        WeatherInfo info = new WeatherInfo();
        if(this.bodyXml !=null) {

            for (MeteorologicalinfosXml metero : this.bodyXml.MeteorologicalinfosXmls) {

                for (ItemXml item : metero.timeseriesinfoXml.itemsXml) {

                    for (KindXml kind : item.kindXmls) {

                        if (kind.propertyXml.probabilityofprecipitationpartXml != null) {
                            if (kind.propertyXml.probabilityofprecipitationpartXml.probabilityofprecipitationpartXmlHashMap.containsKey(refid)) {
                                info.setRainy(kind.propertyXml.probabilityofprecipitationpartXml.probabilityofprecipitationpartXmlHashMap.get(refid).probabilityofprecipitation);
                            }

                        }
                        if (kind.propertyXml.weatherpartXml != null) {
                            if (kind.propertyXml.weatherpartXml.weatherXmlHashMap.containsKey(refid)) {
                                info.setWeather(kind.propertyXml.weatherpartXml.weatherXmlHashMap.get(refid).weather);
                            }

                        }
                        if (kind.propertyXml.temperaturepartXml != null) {
                            if (kind.propertyXml.temperaturepartXml.temperatureXmlHashMap.containsKey(refid)) {
                                info.setTemperature(kind.propertyXml.temperaturepartXml.temperatureXmlHashMap.get(refid).temperature);
                            }

                        }
                    }

                }

            }
        }

        if(this.headXml != null){
            if(this.headXml.titleXml != null){
                info.setAddress(this.headXml.titleXml.title);
            }
        }

        return info;
    }

}
