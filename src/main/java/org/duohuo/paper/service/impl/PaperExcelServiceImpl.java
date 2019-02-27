package org.duohuo.paper.service.impl;

import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.PaperExcelService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service("paperExcelService")
public class PaperExcelServiceImpl implements PaperExcelService {


    /*
    Paper插入思路：
    1. 首先获取压缩包文件，将其保存在jar包同级目录下。
    2. 解压缩，同时获取解压之后文件
    3. 如果是esi论文，过滤文件名和学科相验证，存在不符合规范文件，直接抛出异常结束。
    4. 如果是校级论文，首先分辨出图片和excel，一个excel和对应的多少张图片，过滤图片名称和excel中文件相验证，如果不存在的articleName，直接抛出异常结束。
    5. 检验excel中学科是否存在，不存在直接结束
    6. 如果是校级论文，将图片转成base64编码，存入数据库中
    7. 判断新的时间是否存在，如果不存在，直接插入时间。
    8. 如果存在，首先删除单类别下的该时间点所有数据，接着查出除此单类别之外的该时间点下所有数据，查到相同accessionNumber，存在该类别，不变，不存在该类别添加上该类别，同时都要修改除了accessionNumber之外的所有数据
    9. 插入新的数据
    10. 结束
    */
    @Override
    public JsonResult insertPaperExcel(InputStream stream, Integer year, Integer month, Integer type, String fileName) {
        return null;
    }
}
