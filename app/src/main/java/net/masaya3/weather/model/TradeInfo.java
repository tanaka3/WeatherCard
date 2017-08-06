package net.masaya3.weather.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class TradeInfo extends RealmObject {

    @PrimaryKey
    private long tradeId = 0;
    private long cardId = 0;
    private String userId = "";
    private long openDate = 0;
    private long closeDate = 0;
    private String tradeUserId = "";
    private long tradeDate = 0;
    private int type =0;
    private CardInfo cardInfo;

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getOpenDate() {
        return openDate;
    }

    public void setOpenDate(long openDate) {
        this.openDate = openDate;
    }

    public long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(long closeDate) {
        this.closeDate = closeDate;
    }

    public String getTradeUserId() {
        return tradeUserId;
    }

    public void setTradeUserId(String tradeUserId) {
        this.tradeUserId = tradeUserId;
    }

    public long getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(long tradeDate) {
        this.tradeDate = tradeDate;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
