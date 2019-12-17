package com.capfsd.stock.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_stock_price_detail")
public class StockPriceDetailEntity extends BaseEntity{
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @Column(name = "stock_exchange_code", nullable = false)
    private String stockExchangeCode;

    @Column(name = "price_per_share", nullable = false)
    private Double pricePerShare;

    @Column(name = "date", nullable = false)
    private java.sql.Date date;

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
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

    @Column(name = "time", nullable = false)
    private java.sql.Time time;

    @Override
    public String toString() {
        return "StockPriceDetailEntity{" +
                "companyCode='" + companyCode + '\'' +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", date=" + date +
                ", time=" + time +
                ", sectorCode='" + sectorCode + '\'' +
                ", datetime=" + datetime +
                '}';
    }

    @Column(name = "sector_code", nullable = false)
    private String sectorCode;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Column(name = "datetime", nullable = false)
    private java.util.Date datetime;
}