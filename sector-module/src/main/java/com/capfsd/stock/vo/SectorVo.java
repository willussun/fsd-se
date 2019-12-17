package com.capfsd.stock.vo;

//import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SectorVo {
    @NotBlank(message = "sectorName must not be blank")
    @Size(min = 5, message = "sectorName must be at least 5 characters")
    private String sectorName;
    private String sectorCode;
    private String sectorDesc;

    public Long getId() {
        return id;
    }

    public String getSectorName() {
        return sectorName;
    }

    @Override
    public String toString() {
        return "SectorVo{" +
                "sectorName='" + sectorName + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", sectorDesc='" + sectorDesc + '\'' +
                ", id=" + id +
                '}';
    }

    public String getSectorDesc() {
        return sectorDesc;
    }

    public void setSectorDesc(String sectorDesc) {
        this.sectorDesc = sectorDesc;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

}
