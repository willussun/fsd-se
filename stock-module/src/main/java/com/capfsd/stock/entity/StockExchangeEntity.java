package com.capfsd.stock.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_stock_exchange")
public class StockExchangeEntity extends BaseEntity{
    @Column(name = "stock_exchange_code", unique = true, nullable = false)
    private String stockExchangeCode;
    @Column(name = "stock_exchange_name", unique = true, nullable = false)
    private String stockExchangeName;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "brief")
    private String brief;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getStockExchangeName() {
        return stockExchangeName;
    }

    public void setStockExchangeName(String stockExchangeName) {
        this.stockExchangeName = stockExchangeName;
    }

    @Column(name = "contact_address")
    private String contactAddress;
    
    public String getStockExchangeCode() {
        return stockExchangeCode;
    }

    public void setStockExchangeCode(String stockExchangeCode) {
        this.stockExchangeCode = stockExchangeCode;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<IpoDetailEntity> getIpoDetailEntityList() {
        return ipoDetailEntityList;
    }

    public void setIpoDetailEntityList(List<IpoDetailEntity> ipoDetailEntityList) {
        this.ipoDetailEntityList = ipoDetailEntityList;
    }

    @Override
    public String toString() {
        return "StockExchangeEntity{" +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", stockExchangeName='" + stockExchangeName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", brief='" + brief + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", ipoDetailEntityList=" + ipoDetailEntityList +
                '}';
    }

    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name="se_id")
    private List<IpoDetailEntity> ipoDetailEntityList = new ArrayList<>();
}