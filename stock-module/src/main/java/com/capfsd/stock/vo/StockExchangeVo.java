package com.capfsd.stock.vo;

//import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class StockExchangeVo {
    @NotBlank(message = "stock exchange code must not be blank")
    private String stockExchangeCode;
    private String remarks;
    private String contactAddress;

    public String getStockExchangeCode() {
        return stockExchangeCode;
    }

    public String getStockExchangeName() {
        return stockExchangeName;
    }

    public void setStockExchangeName(String stockExchangeName) {
        this.stockExchangeName = stockExchangeName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setStockExchangeCode(String stockExchangeCode) {
        this.stockExchangeCode = stockExchangeCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    @NotBlank(message = "stock exchange name must not be blank")
    private String stockExchangeName;

    public List<IpoDetailVo> getIpoDetailVoList() {
        return ipoDetailVoList;
    }

    public void setIpoDetailVoList(List<IpoDetailVo> ipoDetailVoList) {
        this.ipoDetailVoList = ipoDetailVoList;
    }

    @Override
    public String toString() {
        return "StockExchangeVo{" +
                "stockExchangeCode='" + stockExchangeCode + '\'' +
                ", remarks='" + remarks + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", id=" + id +
                ", stockExchangeName='" + stockExchangeName + '\'' +
                ", brief='" + brief + '\'' +
                '}';
    }

    private String brief;
    private List<IpoDetailVo> ipoDetailVoList = new ArrayList<IpoDetailVo>();
}