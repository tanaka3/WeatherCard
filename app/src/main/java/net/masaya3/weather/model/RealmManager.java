package net.masaya3.weather.model;

import android.content.Context;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class RealmManager {
    /**
     *
     */
    private Realm realm = null;

    /**
     *
     */
    public RealmManager(){
        realm = Realm.getDefaultInstance();
    }

    /**
     *
     * @param context
     */
    public static void init(Context context){

        Realm.init(context);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();         //自動マイグレーション対応

        Realm.setDefaultConfiguration(configuration);
    }

    /**
     * RealmがOpenされている状態かどうか
     * @return
     */
    public boolean isOpen(){
        return realm != null && !realm.isClosed();
    }

    /**
     * Realmの解放
     */
    public void release(){
        if(realm != null && !realm.isClosed()){
            realm.close();
            realm = null;
        }
    }

    /**
     *
     * @return
     */
    public List<CardInfo> findCard(){
        if(!isOpen()){
            return null;
        }

        RealmResults<CardInfo> result = realm.where(CardInfo.class).equalTo("type", 0).findAll();

        if(result == null){
            return  null;
        }

        return realm.copyFromRealm(result);
    }

    /**
     *
     * @return
     */
    public List<TradeInfo> findTradeCard(){
        if(!isOpen()){
            return null;
        }

        RealmResults<TradeInfo> result = realm.where(TradeInfo.class).equalTo("type", 0).findAll();

        if(result == null){
            return  null;
        }

        return realm.copyFromRealm(result);
    }

    public CardInfo insertCard(final WeatherInfo info){
        if(!isOpen()){
            return null;
        }

        realm.beginTransaction();

        Number maxId = realm.where(CardInfo.class).max("cardId");

        int id = 1;

        if(maxId != null){
            id = maxId.intValue() + 1;
        }
        CardInfo card = realm.createObject(CardInfo.class, id);
        card.setDetail(info.getWeather());
        card.setName(info.getWeather());
        card.setAddress(info.getAddress());
        card.setTemperature(info.getTemperature());
        card.setWeather(info.getWeather());
        card.setRainy(info.getRainy());
        card.setTime(new Date().getTime());

        realm.commitTransaction();

        return realm.copyFromRealm(card);
    }

    public void cancelTrade(final long tradeId){
        if(!isOpen()){
            return;
        }

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    TradeInfo result = realm.where(TradeInfo.class).equalTo("tradeId", tradeId).findFirst();
                    result.setType(-1);
                    result.setCloseDate(new Date().getTime());

                    result.getCardInfo().setType(0);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tradeCard(final long cardId){
        if(!isOpen()){
            return;
        }

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    CardInfo result = realm.where(CardInfo.class).equalTo("cardId", cardId).findFirst();
                    result.setType(1);

                    Number maxId = realm.where(TradeInfo.class).max("tradeId");

                    int id = 1;

                    if(maxId != null){
                        id = maxId.intValue() + 1;
                    }
                    TradeInfo info = realm.createObject(TradeInfo.class, id);
                    info.setOpenDate(new Date().getTime());
                    info.setCardId(result.getCardId());
                    info.setCardInfo(result);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printCard(final long cardId){
        if(!isOpen()){
            return;
        }

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    CardInfo result = realm.where(CardInfo.class).equalTo("cardId", cardId).findFirst();
                    result.setType(2);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printTradeCard(final long tradeId){
        if(!isOpen()){
            return;
        }

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    TradeInfo result = realm.where(TradeInfo.class).equalTo("tradeId", tradeId).findFirst();
                    result.setType(2);
                    result.setCloseDate(new Date().getTime());

                    result.getCardInfo().setType(2);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteCard(final long cardId){
        if(!isOpen()){
            return;
        }

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    CardInfo result = realm.where(CardInfo.class).equalTo("cardId", cardId).findFirst();
                    result.setType(-1);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void deleteTradeCard(final long tradeId){
        if(!isOpen()){
            return;
        }

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    TradeInfo result = realm.where(TradeInfo.class).equalTo("tradeId", tradeId).findFirst();
                    result.setType(-1);
                    result.setCloseDate(new Date().getTime());

                    result.getCardInfo().setType(-1);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
