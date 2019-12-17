package com.capfsd.stock.dto;

public class SectorDto {
    private Long id;

    @Override
    public String toString() {
        return "SectorDto{" +
                "id=" + id +
                ", sectorName='" + sectorName + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", sectorDesc='" + sectorDesc + '\'' +
                '}';
    }

    private String sectorName;

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getSectorDesc() {
        return sectorDesc;
    }

    public void setSectorDesc(String sectorDesc) {
        this.sectorDesc = sectorDesc;
    }

    private String sectorCode;
    private String sectorDesc;
}
