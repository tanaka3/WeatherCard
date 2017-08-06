package net.masaya3.weather.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import net.masaya3.weather.R;
import net.masaya3.weather.model.CardInfo;
import net.masaya3.weather.model.RealmManager;
import net.masaya3.weather.model.WeatherInfo;
import net.masaya3.weather.util.CommonUtil;
import net.masaya3.weather.view.CardView;
import net.masaya3.weather.xml.ReportXml;
import net.masaya3.weather.xml.WeatherXmlManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class CardFragmentDialog extends DialogFragment {

    private ImageView getView;

    private MediaPlayer player = new MediaPlayer();
    /**
     *
     * @return
     */
    public static CardFragmentDialog newInstance(){
        CardFragmentDialog dialog = new CardFragmentDialog();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.card_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                getView.setVisibility(View.VISIBLE);
                getView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.get_show));
            }
        });

        return dialog;
    }

    /**
     *
     * @param i
     * @param c
     * @param b
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle b)
    {
        View content = i.inflate(R.layout.card_dialog, null);

        ImageView view = (ImageView)content.findViewById(R.id.card_image);

        getView = (ImageView)content.findViewById(R.id.get_image);

        ReportXml xml = WeatherXmlManager.load(getContext());
        Random rand = new Random();
        WeatherInfo info = xml.get(rand.nextInt(2)+1);

        RealmManager realmManager = new RealmManager();
        CardInfo card = realmManager.insertCard(info);

        CardView cardView = new CardView(getContext());
        cardView.setCard(card);

        Bitmap bitmap = cardView.createBitmap();

        File path = new File(getContext().getFilesDir(), getContext().getString(R.string.image_path));
        if(!path.exists()){
            path.mkdirs();
        }

        File file = new File(path, String.format(getContext().getString(R.string.card_file), card.getCardId()));

        CommonUtil.saveAsPngImage(file, bitmap);

        view.setImageBitmap(bitmap);

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                CardInfo card = (CardInfo)objects[0];

                Random rand = new Random();

                String url = getContext().getString(R.string.aitalk_url);
                url +="?username=" + getContext().getString(R.string.aitalk_id);
                url +="&password=" + getContext().getString(R.string.aitalk_pass);
                url +="&input_type=text";

                float speed = 1.0f;//rand.nextFloat() * 2.0f + 0.5f;
                if(speed > 2.0f){
                    speed = 2.0f;
                }
                url +="&speed=" + String.format("%.2f", + speed);

                float pitch = 1.0f;//rand.nextFloat() * 2.0f + 0.5f;
                if(pitch > 2.0f){
                    pitch = 2.0f;
                }
                url +="&pitch=" + String.format("%.2f",pitch);
                url +="&range=" + String.format("%.2f",rand.nextFloat() * 2.0f);

                url +="&speaker_name=" + (String)(rand.nextBoolean() ? "aoi" : "akane_west");

                JSONObject style = new JSONObject();


                try {
                    style.put("j", String.format("%.1f", rand.nextFloat()));
                    style.put("s", String.format("%.1f", rand.nextFloat()));
                    style.put("a", String.format("%.1f", rand.nextFloat()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    url += "&style=" + URLEncoder.encode(style.toString(), "UTF-8");
                    url += "&text=" + URLEncoder.encode(card.getName() + "のカードをゲットしたよ！", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                downloadSound(url);

                return null;
            }
            @Override
            protected void onPostExecute(Object result){
            }
        };
        task.execute(card);

        return content;
    }

    private void downloadSound(String url_str) {
        try {

            URL url = new URL(url_str);

            // HttpURLConnection インスタンス生成
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // タイムアウト設定
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(20000);

            // リクエストメソッド
            urlConnection.setRequestMethod("GET");

            // リダイレクトを自動で許可しない設定
            urlConnection.setInstanceFollowRedirects(false);

            // ヘッダーの設定(複数設定可能)
            urlConnection.setRequestProperty("Accept-Language", "jp");

            // 接続
            urlConnection.connect();

            int resp = urlConnection.getResponseCode();

            switch (resp) {

                case HttpURLConnection.HTTP_OK:

                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");

                    Random rand = new Random();

                    String sound_file = String.format("/%s_%04d.ogg", dateformat.format(now.getTime()), rand.nextInt(10000));
                    sound_file = getContext().getCacheDir() + sound_file;

                    InputStream input = urlConnection.getInputStream();
                    DataInputStream dataInput = new DataInputStream(input);

                    FileOutputStream fileOutput = new FileOutputStream(sound_file);
                    DataOutputStream dataOut = new DataOutputStream(fileOutput);
                    // 読み込みデータ単位
                    final byte[] buffer = new byte[4096];
                    // 読み込んだデータを一時的に格納しておく変数
                    int readByte = 0;

                    // ファイルを読み込む
                    while((readByte = dataInput.read(buffer)) != -1) {
                        dataOut.write(buffer, 0, readByte);
                    }

                    // 各ストリームを閉じる
                    dataInput.close();
                    fileOutput.close();
                    dataInput.close();
                    input.close();

                    player.setDataSource(sound_file);
                    player.prepare();
                    player.start();

                    break;

                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    break;

                default:
                    break;
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void saveAsPngImage(Bitmap bitmap){
        try {

            File extStrageDir =
                    Environment.getExternalStorageDirectory();
            File file = new File(
                    extStrageDir.getAbsolutePath()
                            + "/" + Environment.DIRECTORY_DCIM,
                    getFileName());
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getFileName(){
        Calendar c = Calendar.getInstance();
        String s = c.get(Calendar.YEAR)
                + "_" + (c.get(Calendar.MONTH)+1)
                + "_" + c.get(Calendar.DAY_OF_MONTH)
                + "_" + c.get(Calendar.HOUR_OF_DAY)
                + "_" + c.get(Calendar.MINUTE)
                + "_" + c.get(Calendar.SECOND)
                + "_" + c.get(Calendar.MILLISECOND)
                + ".png";
        return s;
    }
}
