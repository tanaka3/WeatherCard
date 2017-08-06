
package net.masaya3.weather.xml;

import android.content.Context;
import android.util.Xml;

import net.masaya3.weather.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStreamReader;

public class WeatherXmlManager {


    /**
     * コンストラクタ
     */
    public static ReportXml load(Context context){
		
		InputStreamReader isr = null;
        try {

			isr = new InputStreamReader(context.getResources().openRawResource(R.raw.data));
	        XmlPullParser xml = Xml.newPullParser();
	        xml.setInput(isr);

			ReportXml reportXml = null;
			
			for(int type = xml.getEventType(); type != XmlPullParser.END_DOCUMENT; type = xml.next()) {
	
				switch(type){
				case XmlPullParser.START_TAG:
					if(xml.getName().equalsIgnoreCase("report")){
						reportXml = ReportXml.parse(xml);
					}
					break;
				}
			}
			
			return reportXml;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			if(isr != null){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
