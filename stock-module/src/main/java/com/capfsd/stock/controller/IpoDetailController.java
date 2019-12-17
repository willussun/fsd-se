package com.capfsd.stock.controller;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.IpoDetailDto;
import com.capfsd.stock.service.IIpoDetailService;
import com.capfsd.stock.util.ResponseResult;
import com.capfsd.stock.vo.IpoDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/ipodetails")
public class IpoDetailController {

    @Autowired
    private IIpoDetailService ipoDetailServiceService;

    @GetMapping
    public ResponseResult<PageDto<IpoDetailDto>> getAll (
            @RequestParam(value = "q", required = false, defaultValue = "") String keyword,
            @PageableDefault(value = 1000, sort = {"updatedTime"}) Pageable pageable) {
        {
            PageDto<IpoDetailDto> ipoDetailPage = ipoDetailServiceService.findAll(keyword, pageable);
            return ResponseResult.success("get resources successfully", ipoDetailPage, null);
        }
    }

    @PostMapping
    public ResponseResult create(@Valid @RequestBody IpoDetailVo ipoDetailVo) throws ParseException {
        ipoDetailServiceService.addIpoDetail(ipoDetailVo);
        return ResponseResult.success("create IPO detail successfully", null, null);
    }

    @GetMapping(value = "/{id}")
    public ResponseResult<Object> getById(@PathVariable(value = "id") Long id) {

        Optional<IpoDetailDto> optional = ipoDetailServiceService.findIpoDetailById(id);
        if (optional.isPresent()) {
            return ResponseResult.success("get IPO detail successfully", optional.get(), null);
        } else {
            return ResponseResult.fail("failed to get IPO detail " + id, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult deleteById(@PathVariable(value = "id") Long id)  {

        ipoDetailServiceService.deleteIpoDetailById(id);

        return ResponseResult.success("delete IPO " + id + " successfully", null, null);
    }

    @PostMapping(value="/update")
        public ResponseResult<IpoDetailDto> updateById(
            @RequestBody IpoDetailVo ipoDetailVo) throws ParseException {
        Long id = ipoDetailVo.getId();
        IpoDetailDto resourceDto = ipoDetailServiceService.updateIpoDetail(id, ipoDetailVo);
        return ResponseResult.success("update IPO detail successfully", resourceDto, null);
    }

    @GetMapping(value = "/se")
        public ResponseResult getByStockExchangeCode (
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = false) String stockExchangeCode)
    {
        List<IpoDetailDto> ipoDetailDtoList = ipoDetailServiceService.findByStockExchangeCode(stockExchangeCode);
        return ResponseResult.success("get companies successfully", ipoDetailDtoList, null);
    }

}
