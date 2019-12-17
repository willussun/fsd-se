package com.capfsd.stock.dto;

import java.util.ArrayList;
import java.util.List;

public class StockExchangeDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockExchangeCode() {
        return stockExchangeCode;
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

    private String stockExchangeCode;
    private String remarks;
    private String contactAddress;

    public String getStockExchangeName() {
        return stockExchangeName;
    }

    public void setStockExchangeName(String stockExchangeName) {
        this.stockExchangeName = stockExchangeName;
    }

    private String stockExchangeName;

    public String getBrief() {
        return brief;
    }

    @Override
    public String toString() {
        return "StockExchangeDto{" +
                "id=" + id +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", remarks='" + remarks + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", stockExchangeName='" + stockExchangeName + '\'' +
                ", brief='" + brief + '\'' +
                '}';
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    private String brief;

    public List<IpoDetailDto> getIpoDetailDtoList() {
        return ipoDetailDtoList;
    }

    public void setIpoDetailDtoList(List<IpoDetailDto> ipoDetailDtoList) {
        this.ipoDetailDtoList = ipoDetailDtoList;
    }

    private List<IpoDetailDto> ipoDetailDtoList = new ArrayList<IpoDetailDto>();

}
