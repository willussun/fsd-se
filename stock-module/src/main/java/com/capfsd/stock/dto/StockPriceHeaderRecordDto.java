package com.capfsd.stock.dto;

public class StockPriceHeaderRecordDto {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private String time;

    private String datetime;


    //representing the label
    private String stockExchangeCode;

    public String getStockExchangeCode() {
        return stockExchangeCode;
    }

    public void setStockExchangeCode(String stockExchangeCode) {
        this.stockExchangeCode = stockExchangeCode;
    }

    @Override
    public String toString() {
        return "StockPriceHeaderRecordDto{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", datetime='" + datetime + '\'' +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                '}';
    }
}