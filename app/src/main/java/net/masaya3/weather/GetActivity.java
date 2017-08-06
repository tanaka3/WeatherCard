package net.masaya3.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import net.masaya3.weather.fragment.CardFragmentDialog;
import net.masaya3.weather.model.RealmManager;
import net.masaya3.weather.view.CardAdapter;

public class GetActivity extends AppCompatActivity {

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
                    changeActivity(new Intent(GetActivity.this, MainActivity.class));

                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    changeActivity(new Intent(GetActivity.this, TradeActivity.class));
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
        setContentView(R.layout.activity_get);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        menu.getItem(1).setChecked(true);
        menu.getItem(0).setChecked(false);

        TextView cardCount = (TextView)this.findViewById(R.id.card_count);
        cardCount.setText(String.format(getString(R.string.get_num), 3));

        this.findViewById(R.id.iineView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardFragmentDialog dialog = CardFragmentDialog.newInstance();
                dialog.show(getFragmentManager(), null);

            }
        });





    }



    @Override
    protected void onResume() {
        super.onResume();
    }


}
