package com.capfsd.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_sector")
public class SectorEntity extends BaseEntity{
    @Column(name = "sector_code", unique = true, nullable = false)
    private String sectorCode;
    @Column(name = "sector_name", unique = true, nullable = false)
    private String sectorName;

    @Override
    public String toString() {
        return "SectorEntity{" +
                "sectorCode='" + sectorCode + '\'' +
                ", sectorName='" + sectorName + '\'' +
                ", sectorDesc='" + sectorDesc + '\'' +
                '}';
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorDesc() {
        return sectorDesc;
    }

    public void setSectorDesc(String sectorDesc) {
        this.sectorDesc = sectorDesc;
    }

    @Column(name = "sector_desc")
    private String sectorDesc;
}