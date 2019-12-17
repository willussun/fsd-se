package com.capfsd.stock.vo;

import java.util.Date;

public class StockPriceDetailVo {
    private String companyCode;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getStockExchangeCode() {
        return stockExchangeCode;
    }

    public void setStockExchangeCode(String stockExchangeCode) {
        this.stockExchangeCode = stockExchangeCode;
    }

    public Double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(Double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public java.sql.Time getTime() {
        return time;
    }

    public void setTime(java.sql.Time time) {
        this.time = time;
    }

    private String stockExchangeCode;

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    private Double pricePerShare;
    private String sectorCode;
    private java.sql.Date date;
    private java.sql.Time time;

    @Override
    public String toString() {
        return "StockPriceDetailVo{" +
                "companyCode='" + companyCode + '\'' +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", sectorCode='" + sectorCode + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", datetime=" + datetime +
                '}';
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    private java.util.Date datetime;
}
