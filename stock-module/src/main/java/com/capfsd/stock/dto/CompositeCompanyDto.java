package com.capfsd.stock.dto;

public class CompositeCompanyDto {
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

    @Override
    public String toString() {
        return "CompositeCompanyDto{" +
                "companyName='" + companyName + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                '}';
    }

    private String companyCode;
    private String stockExchangeCode;
}
