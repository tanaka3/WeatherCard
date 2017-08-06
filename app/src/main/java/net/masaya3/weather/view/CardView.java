package net.masaya3.weather.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.masaya3.weather.R;
import net.masaya3.weather.model.CardInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class CardView extends LinearLayout {

    private CardInfo cardInfo;

    public CardInfo getCardInfo(){
        return cardInfo;
    }

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private View layout;
    public void setCard(CardInfo cardInfo){
        layout = LayoutInflater.from(getContext()).inflate(R.layout.card_view, this);

        ImageView weatherImage = (ImageView) layout.findViewById(R.id.weather_image);
        TextView detailValue = (TextView)layout.findViewById(R.id.detail_value);
        TextView rainyValue = (TextView)layout.findViewById(R.id.rainy_value);
        TextView cardName = (TextView)layout.findViewById(R.id.card_name);
        TextView temperatureValue = (TextView)layout.findViewById(R.id.temperature_value);
        TextView dateValue = (TextView)layout.findViewById(R.id.date_value);
        TextView addressValue = (TextView)layout.findViewById(R.id.address_value);


        Random rand = new Random();
        if(cardInfo.getWeather().equals("雨")){
            weatherImage.setImageResource(R.mipmap.rain);

            String[] details = getContext().getResources().getStringArray(R.array.rain_detail);
            detailValue.setText(details[rand.nextInt(details.length)]);

        }
        else if(cardInfo.getWeather().equals("くもり")) {
            weatherImage.setImageResource(R.mipmap.cloud);

            String[] details = getContext().getResources().getStringArray(R.array.cloud_detail);
            detailValue.setText(details[rand.nextInt(details.length)]);
        }
        else{
            weatherImage.setImageResource(R.mipmap.sun);

            String[] details = getContext().getResources().getStringArray(R.array.sun_detail);
            detailValue.setText(details[rand.nextInt(details.length)]);
        }


        //detailText.setText(cardInfo.getDetail());
        cardName.setText(cardInfo.getName());
        temperatureValue.setText(String.valueOf(cardInfo.getTemperature()));
        rainyValue.setText(String.format(getContext().getString(R.string.pp_format),cardInfo.getRainy()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateValue.setText(sdf.format(new Date(cardInfo.getTime())));
        addressValue.setText(cardInfo.getAddress());
    }

    /*
    public Bitmap createBitmap() {

        Bitmap b = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        this.draw(c);
        return b;
    }
    */
    public Bitmap createBitmap() {

        // 画面内に配置してないので、measureを読んでからBitmapに書き込む
        if (this.getMeasuredHeight() <= 0) {
            this.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(this.getMeasuredWidth(), this.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            this.layout(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
            this.draw(c);
            return b;
        }

        return null;
    }
}
