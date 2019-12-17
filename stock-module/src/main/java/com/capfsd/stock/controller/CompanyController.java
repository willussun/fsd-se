package com.capfsd.stock.controller;

import com.capfsd.stock.dto.CompanyDto;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.service.ICompanyService;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.util.ResponseResult;
import com.capfsd.stock.vo.CompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @GetMapping
    public ResponseResult<PageDto<CompanyDto>> getAll (
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
            @PageableDefault(value = 1000, sort = {"updatedTime"}) Pageable pageable) {
            PageDto<CompanyDto> companyPage = companyService.findAll(keyword, pageable);
            return ResponseResult.success("get companies successfully", companyPage, null);

    }

    @GetMapping(value = "/status")
    public ResponseResult<PageDto<CompanyDto>> getAllByStatus(
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
            @RequestParam(value = "status", required = false) Integer status,
            @PageableDefault(value = 1000, sort = {"updatedTime"}) Pageable pageable) {
            PageDto<CompanyDto> companyPage = companyService.findAllByStatus(keyword, status, pageable);
            return ResponseResult.success("get companies successfully", companyPage, null);
    }

    @PostMapping()
    public ResponseResult create(@Valid @RequestBody CompanyVo companyVo)  {
        //System.out.println(companyVo);
        companyService.addCompany(companyVo);
        return ResponseResult.success("create company successfully", null, null);
    }

    @GetMapping(value = "/{id}")
    public ResponseResult<CompanyDto> findById(@PathVariable(value = "id") Long id) {
            Optional<CompanyDto> optional = companyService.findCompanyById(id);
            if (optional.isPresent()) {
                CompanyDto companyDto = optional.get();
                return ResponseResult.success("get company successfully", optional.get(), null);
            }
            return null;
        }


    @DeleteMapping(value = "/{id}")
    public ResponseResult deleteById(@PathVariable(value = "id") Long id) {
        companyService.deleteCompanyById(id);
        return ResponseResult.success("delete company successfully", null, null);
    }

    @PostMapping(value = "/{id}")
    public ResponseResult<CompanyDto> updateById(@PathVariable(value = "id") Long id,
                                              @Valid @RequestBody CompanyVo companyVo) {
       /* CompanyDto companyDto = companyService.updateCompany(id, companyVo);*/
        companyService.updateCompany(id, companyVo);
        return ResponseResult.success("update company successfully", null, null);
    }

    @PostMapping(value = "/deactivate/{id}")
    public ResponseResult<CompanyDto> DeactivateByIdWithStatus(@PathVariable(value = "id") Long id) {
            Integer result = companyService.updateCompanyByIdWithStatus(id, 0);
            return ResponseResult.success("update company successfully", null, null);
    }

    @PostMapping(value = "/activate/{id}")
    public ResponseResult<CompanyDto> activateByIdWithStatus(@PathVariable(value = "id") Long id) {
            Integer result = companyService.updateCompanyByIdWithStatus(id, 1);
            return ResponseResult.success("update company successfully", null, null);
    }

    @GetMapping(value = "/se")
    public ResponseResult getByStockExchangeCode(
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = false) String stockExchangeCode)
        {
            List<CompanyDto> companyDtoList = companyService.findByStockExchangeCode(stockExchangeCode);
            return ResponseResult.success("get companies successfully", companyDtoList, null);
    }
}