package com.capfsd.stock.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", turnover=" + turnover +
                ", ceo='" + ceo + '\'' +
                ", boardDirector='" + boardDirector + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", briefWriteup='" + briefWriteup + '\'' +
                ", status=" + status +
//                ", ipoDetailDtoList=" + ipoDetailDtoList +
                '}';
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getBoardDirector() {
        return boardDirector;
    }

    public void setBoardDirector(String boardDirector) {
        this.boardDirector = boardDirector;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getBriefWriteup() {
        return briefWriteup;
    }

    public void setBriefWriteup(String briefWriteup) {
        this.briefWriteup = briefWriteup;
    }

    private String companyName;
    private Double turnover;
    private String ceo;
    private String boardDirector;
    private String sectorCode;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private String briefWriteup;
    private Integer status;

    public List<IpoDetailDto> getIpoDetailDtoList() {
        return ipoDetailDtoList;
    }

    public void setIpoDetailDtoList(List<IpoDetailDto> ipoDetailDtoList) {
        this.ipoDetailDtoList = ipoDetailDtoList;
    }

    private List<IpoDetailDto> ipoDetailDtoList = new ArrayList<IpoDetailDto>();
}
