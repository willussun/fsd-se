package com.capfsd.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_company")
public class CompanyEntity extends BaseEntity {
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    @Column(name = "company_name", unique = true, nullable = false)
    private String companyName;

    @Column(name = "turnover", nullable = false)
    private Double turnover;

    @Column(name = "ceo", nullable = false)
    private String ceo;

    @Column(name = "board_director", nullable = false)
    private String boardDirector;

    @Column(name = "sector_code", nullable = false)
    private String sectorCode;

//    @OneToMany(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "company_id")

    public List<IpoDetailEntity> getIpoDetailEntityList() {
        return ipoDetailEntityList;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "companyName='" + companyName + '\'' +
                ", turnover=" + turnover +
                ", ceo='" + ceo + '\'' +
                ", boardDirector='" + boardDirector + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", briefWriteup='" + briefWriteup + '\'' +
//                ", ipoDetailEntityList=" + ipoDetailEntityList +
                ", status=" + status +
                '}';
    }

    public void setIpoDetailEntityList(List<IpoDetailEntity> ipoDetailEntityList) {
        this.ipoDetailEntityList = ipoDetailEntityList;
    }

//    @OneToMany(mappedBy = "companyEntity")
    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name="company_id")
    private List<IpoDetailEntity> ipoDetailEntityList = new ArrayList<>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "status", nullable = false)
    private Integer status;

    public String getBriefWriteup() {
        return briefWriteup;
    }

    public void setBriefWriteup(String briefWriteup) {
        this.briefWriteup = briefWriteup;
    }

    @Column(name = "brief_writeup")
    private String briefWriteup;
}