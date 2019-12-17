package com.capfsd.stock.dto;
import java.sql.Date;
import java.sql.Time;

public class StockPriceRecordDto {
    private Date date;
    private Time time;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(Double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    private Double pricePerShare;

    private java.util.Date datetime;

    public java.util.Date getDatetime() {
        return datetime;
    }

    public void setDatetime(java.util.Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "StockPriceRecordDto{" +
                "date=" + date +
                ", time=" + time +
                ", pricePerShare=" + pricePerShare +
                ", datetime=" + datetime +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                '}';
    }

    public void setStockExchangeCode(String stockExchangeCode) {
        this.stockExchangeCode = stockExchangeCode;
    }

    private String stockExchangeCode;
}
