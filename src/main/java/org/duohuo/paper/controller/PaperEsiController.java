package org.duohuo.paper.controller;

import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.duohuo.paper.excel.model.download.PaperExcelDownloadModel;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.model.dto.DeleteDto;
import org.duohuo.paper.model.dto.DownloadDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.PaperSearchService;
import org.duohuo.paper.utils.DownloadUtil;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.duohuo.paper.Constants.DATE_FORMAT;


@Api(description = "论文查询接口", value = "论文查询")
@RequestMapping("/allPaper")
@RestController
public class PaperEsiController extends PaperController {

    @Resource(name = "paperHighlySearchService")
    private PaperSearchService paperHighlySearchService;

    @Resource(name = "paperHotSearchService")
    private PaperSearchService paperHotSearchService;

    @ApiOperation(value = "esi论文下载", notes = "直接下载")
    @PostMapping(value = "/download")
    @RequiresAuthentication
    public ResponseEntity<byte[]> journalDownload(@ApiParam("下载的具体条目") @RequestBody DownloadDto downloadDto) throws Exception {
        List<Long> paperIdList = downloadDto.getData();
        List<BaseRowModel> paperList = paperHighlySearchService.getPaperListById(paperIdList)
                .stream().map(PaperExcelDownloadModel::new).collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullList(paperList)) {
            throw new NotFoundException("缺少论文相关下载信息");
        }
        byte[] data = ExcelUtil.getDownByte(paperList, 1);
        String fileName = Integer.toString(("PAPER " + DATE_FORMAT.format(new Date())).hashCode());
        return DownloadUtil.getResponseEntity(fileName, data);
    }


    @ApiOperation(value = "删除esi高被引某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/highlyCited/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperHighlyCitedDelete(@RequestBody DeleteDto deleteDto) {
        if (deleteDto == null) {
            throw new NotFoundException("esi高被引删除信息不完整");
        }
        if (deleteDto.getMonth() == null || deleteDto.getYear() == null) {
            throw new NotFoundException("esi高被引删除信息不完整");
        }
        return getJsonResult(deleteDto.getYear(), deleteDto.getMonth(), paperHighlySearchService);
    }

    @ApiOperation(value = "删除esi热点某年月信息", notes = "删除该年月下的全部")
    @PostMapping(value = "/hot/delete")
    @RequiresPermissions(logical = Logical.AND, value = {"edit"})
    public JsonResult paperHotDelete(@RequestBody DeleteDto deleteDto) {
        if (deleteDto == null) {
            throw new NotFoundException("esi热点删除信息不完整");
        }
        if ((deleteDto.getMonth() == null || deleteDto.getYear() == null)) {
            throw new NotFoundException("esi热点删除信息不完整");
        }
        return getJsonResult(deleteDto.getYear(), deleteDto.getMonth(), paperHotSearchService);
    }

    @ApiOperation(value = "esi高被引", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_highly_cited_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/highlyCited")
    @RequiresAuthentication
    public JsonResult paperHighlyCitedSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        if (searchDto == null) {
            throw new NotFoundException("ESI highly cited Paper搜寻条件为空!");
        }
        return getJsonResult(searchDto, paperHighlySearchService);
    }

    @ApiOperation(value = "esi热点", notes = "搜索条件有年月和学科名称")
    @Cacheable(value = "paper_hot_search_all", keyGenerator = "redisKeyGenerator")
    @PostMapping(value = "/hotPaper")
    @RequiresAuthentication
    public JsonResult paperHotSearch(@ApiParam("查询条件") @RequestBody SearchDto searchDto) {
        if (searchDto == null) {
            throw new NotFoundException("ESI hot Paper搜寻条件为空!");
        }
        return getJsonResult(searchDto, paperHotSearchService);
    }
}
