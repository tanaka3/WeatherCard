package net.masaya3.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import net.masaya3.weather.fragment.CardSelectFragmentDialog;
import net.masaya3.weather.model.CardInfo;
import net.masaya3.weather.model.RealmManager;
import net.masaya3.weather.util.CommonUtil;
import net.masaya3.weather.view.CardAdapter;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView cardView;
    private RealmManager realmManager;
    private CardAdapter adapter;
    private TextView cardCount;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    changeActivity(new Intent(MainActivity.this, GetActivity.class));
                    return true;
                case R.id.navigation_notifications:
                    changeActivity(new Intent(MainActivity.this, TradeActivity.class));
                    return true;
            }
            return false;
        }

        private void changeActivity(Intent intent){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = (GridView)this.findViewById(R.id.cardGrid);

        realmManager = new RealmManager();
        //realmManager.insertCard(new WeatherInfo());

        //List<CardInfo> cards = realmManager.findCard();
        //adapter = new CardAdapter(getApplicationContext(), cards);
        //cardView.setAdapter(adapter);
        cardCount = (TextView) this.findViewById(R.id.card_count);

        setCardList();
        cardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CardInfo info = adapter.getItem(i);

                CardSelectFragmentDialog dialog = CardSelectFragmentDialog.newInstance(info.getCardId());
                dialog.setOnCardEventListener(new CardSelectFragmentDialog.OnCardEventListener() {
                    @Override
                    public void onPrint(long id) {

                        realmManager.printCard(id);

                        if (PrintHelper.systemSupportsPrint()) {

                            File path = new File(getFilesDir(), getString(R.string.image_path));
                            File file = new File(path, String.format(getString(R.string.card_file), id));

                            PrintHelper printHelper = new PrintHelper(MainActivity.this);
                            printHelper.setColorMode(PrintHelper.COLOR_MODE_COLOR);
                            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                            printHelper.printBitmap(file.getAbsolutePath(), CommonUtil.loadImage(file));

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "この端末では印刷をサポートしていません",
                                    Toast.LENGTH_SHORT).show();
                        }

                        setCardList();
                    }

                    @Override
                    public void onTrade(long id) {
                        realmManager.tradeCard(id);
                        setCardList();

                    }

                    @Override
                    public void onDelete(long id) {
                        realmManager.deleteCard(id);
                        setCardList();

                    }
                });
                dialog.show(getFragmentManager(), "");

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setCardList(){
        List<CardInfo> cards = realmManager.findCard();

        if(adapter != null) {
            adapter.clear();
        }
        adapter = new CardAdapter(getApplicationContext(), cards);
        cardView.setAdapter(adapter);
        cardView.deferNotifyDataSetChanged();

        cardCount.setText(String.format(getString(R.string.card_num), cards.size(), getResources().getInteger(R.integer.card_max_num)));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
