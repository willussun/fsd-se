package com.capfsd.stock.repository;

import com.capfsd.stock.entity.SectorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectorRepository extends JpaRepository<SectorEntity, Long> {
    List<SectorEntity> findBySectorCode(String sectorCode);

    @Query(value = "SELECT u FROM SectorEntity u WHERE UPPER(u.sectorName) like CONCAT('%',UPPER(:keyword),'%')")
    Page<SectorEntity> findAllSectorsByKeywordWithPagination(@Param("keyword") String keyword, Pageable pageable);
}
