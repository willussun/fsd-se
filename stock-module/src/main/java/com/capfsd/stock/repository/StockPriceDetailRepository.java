package com.capfsd.stock.repository;

import com.capfsd.stock.entity.StockExchangeEntity;
import com.capfsd.stock.entity.StockPriceDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StockPriceDetailRepository extends JpaRepository<StockPriceDetailEntity, Long> {
    @Query(value = "SELECT u FROM StockPriceDetailEntity u WHERE u.companyCode = :companyCode and u.stockExchangeCode = :stockExchangeCode and u.sectorCode = :sectorCode and u.date = :date and u.time = :time")
    List<StockPriceDetailEntity> findStockPriceDetailInfo(
            @Param("companyCode") String companyCode,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("sectorCode") String sectorCode,
            @Param("date") java.sql.Date date,
            @Param("time") java.sql.Time time
    );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update StockPriceDetailEntity u set u.pricePerShare = :pricePerShare where u.companyCode = :companyCode and u.stockExchangeCode = :stockExchangeCode and u.sectorCode = :sectorCode and u.date = :date and u.time = :time")
    Integer updateStockPriceDetailEntity(
            @Param("companyCode") String companyCode,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("sectorCode") String sectorCode,
            @Param("date") java.sql.Date date,
            @Param("time") java.sql.Time time,
            @Param("pricePerShare") Double pricePerShare
    );

}