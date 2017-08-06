package net.masaya3.weather.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import net.masaya3.weather.R;
import net.masaya3.weather.model.TradeInfo;
import net.masaya3.weather.util.CommonUtil;

import java.io.File;
import java.util.List;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class TradeCardAdapter extends ArrayAdapter<TradeInfo> {

    /** */
    private static class ViewHolder {
        public ImageView cardImage;
        public Bitmap bitmap;

    }

    /** */
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     * @param context
     * @param objects
     */
    public TradeCardAdapter(Context context, List<TradeInfo> objects) {
        super(context, R.layout.gridview_card, objects);

        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * 表示内容の設定
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        Resources resources = getContext().getResources();
        if (convertView == null) {

            convertView = this.mInflater.inflate(R.layout.gridview_card, null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.cardImage = (ImageView) convertView.findViewById(R.id.card_image);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        TradeInfo info = ((TradeInfo)getItem(position));

        holder.cardImage.setImageDrawable(null);
        holder.cardImage.setImageBitmap(null);

        if(holder.bitmap != null){
            holder.bitmap.recycle();
        }

        File path = new File(getContext().getFilesDir(), getContext().getString(R.string.image_path));
        File file = new File(path, String.format(getContext().getString(R.string.card_file), info.getCardId()));
        holder.bitmap=CommonUtil.loadScaleImage(file);
        holder.cardImage.setImageBitmap(holder.bitmap);

        return convertView;

    }
}
