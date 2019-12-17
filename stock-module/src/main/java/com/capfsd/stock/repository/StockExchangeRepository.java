package com.capfsd.stock.repository;

import com.capfsd.stock.dto.StockExchangeDto;
import com.capfsd.stock.entity.CompanyEntity;
import com.capfsd.stock.entity.StockExchangeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockExchangeRepository extends JpaRepository<StockExchangeEntity, Long> {
    Optional<StockExchangeEntity> findByStockExchangeCode(String stockExchangeCode);

    @Query(value = "SELECT u FROM StockExchangeEntity u WHERE UPPER(u.stockExchangeCode) like CONCAT('%',UPPER(:keyword),'%')")
    Page<StockExchangeEntity> findAllStockExchangesByKeywordWithPagination(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select * from t_stock_exchange where id not in (\n" +
            "   select se_id from t_ipo_detail where company_id= :id)", nativeQuery = true)
    Page<StockExchangeEntity> getExcludeStockExchangeByCompanyId(@Param("id") Long id, Pageable pageable);
}
