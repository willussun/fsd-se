package com.capfsd.stock.vo;

//import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyVo {
    @NotBlank(message = "companyName must not be blank")
    @Size(min = 3, message = "companyName must be at least 3 characters")
    private String companyName;

    private Double turnover;

    @NotBlank(message = "ceo must not be blank")
    private String ceo;

    @NotBlank(message = "boardDirector must not be blank")
    private String boardDirector;

    @NotBlank(message = "sectorCode must not be blank")
    private String sectorCode;

    private String briefWriteup;

    public String getBriefWriteup() {
        return briefWriteup;
    }

    public void setBriefWriteup(String briefWriteup) {
        this.briefWriteup = briefWriteup;
    }

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

    public List<IpoDetailVo> getIpoDetailVoList() {
        return ipoDetailVoList;
    }

    public void setIpoDetailVoList(List<IpoDetailVo> ipoDetailVoList) {
        this.ipoDetailVoList = ipoDetailVoList;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    private List<IpoDetailVo> ipoDetailVoList = new ArrayList<IpoDetailVo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CompanyVo{" +
                "companyName='" + companyName + '\'' +
                ", turnover=" + turnover +
                ", ceo='" + ceo + '\'' +
                ", boardDirector='" + boardDirector + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", briefWriteup='" + briefWriteup + '\'' +
//                ", ipoDetailVoList=" + ipoDetailVoList +
                ", id=" + id +
                ", status=" + status+
                '}';
    }

    private Integer status;
}
