package net.masaya3.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import net.masaya3.weather.fragment.TradeCardSelectFragmentDialog;
import net.masaya3.weather.model.RealmManager;
import net.masaya3.weather.model.TradeInfo;
import net.masaya3.weather.util.CommonUtil;
import net.masaya3.weather.view.TradeCardAdapter;

import java.io.File;
import java.util.List;

public class TradeActivity extends AppCompatActivity {

    private GridView cardView;
    private RealmManager realmManager;
    private TradeCardAdapter adapter;
    private TextView cardCount;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeActivity(new Intent(TradeActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_dashboard:
                    changeActivity(new Intent(TradeActivity.this, GetActivity.class));

                    return true;
                case R.id.navigation_notifications:
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
        setContentView(R.layout.activity_trade);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        menu.getItem(2).setChecked(true);
        menu.getItem(0).setChecked(false);

        cardView = (GridView)this.findViewById(R.id.cardGrid);
        cardCount = (TextView) this.findViewById(R.id.card_count);

        cardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TradeInfo info = adapter.getItem(i);

                TradeCardSelectFragmentDialog dialog = TradeCardSelectFragmentDialog.newInstance(info.getTradeId());
                dialog.setOnCardEventListener(new TradeCardSelectFragmentDialog.OnCardEventListener() {
                    @Override
                    public void onPrint(long id) {
                        realmManager.printTradeCard(id);
                        if (PrintHelper.systemSupportsPrint()) {

                            File path = new File(getFilesDir(), getString(R.string.image_path));
                            File file = new File(path, String.format(getString(R.string.card_file), id));

                            PrintHelper printHelper = new PrintHelper(TradeActivity.this);
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
                    public void onCancel(long id) {
                        realmManager.cancelTrade(id);
                        setCardList();

                    }

                    @Override
                    public void onDelete(long id) {
                        realmManager.deleteTradeCard(id);
                        setCardList();

                    }
                });
                dialog.show(getFragmentManager(), "");

            }
        });

        realmManager = new RealmManager();

        setCardList();

    }

    private void setCardList(){
        List<TradeInfo> cards = realmManager.findTradeCard();
        if(adapter != null) {
            adapter.clear();
        }
        adapter = new TradeCardAdapter(getApplicationContext(), cards);
        cardView.setAdapter(adapter);
        cardView.deferNotifyDataSetChanged();

        cardCount.setText(String.format(getString(R.string.card_num), cards.size(), getResources().getInteger(R.integer.card_max_num)));
        cardCount.setText(String.format(getString(R.string.card_num), cards.size(), getResources().getInteger(R.integer.trade_card_max_num)));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
