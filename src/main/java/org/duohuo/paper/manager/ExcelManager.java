package org.duohuo.paper.manager;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.duohuo.paper.excel.listener.BaseLineExcelListener;
import org.duohuo.paper.excel.listener.IncitesExcelListener;
import org.duohuo.paper.excel.listener.JournalExcelListener;
import org.duohuo.paper.excel.listener.PaperExcelListener;
import org.duohuo.paper.excel.model.IncitesExcelModel;
import org.duohuo.paper.excel.model.JournalExcelModel;
import org.duohuo.paper.excel.model.PaperExcelModel;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.ZipFileException;
import org.duohuo.paper.utils.ObjectUtil;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Component("excelManager")
public class ExcelManager {

    public List<String> filePathFilter(final List<String> filePath, final String typeFilePath) {
        List<String> newFilePath = filePath.stream()
//                .peek(elem -> {
//                    if (elem.contains("_MACOSX") || elem.contains(".DS_Store")) {
//                        new File(typeFilePath + elem)
//                                .deleteOnExit();
//                    }
//                })
                .filter(elem -> !elem.contains("_MACOSX")
                        && !elem.contains(".DS_Store"))
                .collect(Collectors.toList());
        if (!ObjectUtil.ifNotNullCollection(newFilePath)) {
            throw new ZipFileException("检测压缩包中可用文件为空:" + typeFilePath);
        }
        return newFilePath;
    }

    public List<JournalExcelModel> journalExcelRead(final byte[] data, final String fileName) {
        AnalysisEventListener listener;
        //创建InputStream,之后读取excel文件
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            listener = new JournalExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, null, listener);
            reader.read(new Sheet(1, 1, JournalExcelModel.class));
        } catch (Exception e) {
            throw new ExcelException("Excel文件: " + fileName + "处理时出错! " + e.getMessage());
        }
        List<JournalExcelModel> excelModels = ((JournalExcelListener) listener).getVector();
        if (!ObjectUtil.ifNotNullCollection(excelModels)) {
            throw new ExcelException("检查excel中可用数据为空,文件名称:" + fileName);
        }
        return excelModels;
    }

    public List<Object> baseLineExcelRead(final byte[] data, final String fileName) {
        AnalysisEventListener listener;
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            listener = new BaseLineExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read();
        } catch (Exception e) {
            throw new ExcelException("Excel文件: " + fileName + "处理时出错! " + e.getMessage());
        }
        List<Object> baseLineList = ((BaseLineExcelListener) listener).getVector();
        if (!ObjectUtil.ifNotNullCollection(baseLineList)) {
            throw new ExcelException("检查excel中可用数据为空,文件名称:" + fileName);
        }
        return baseLineList;
    }

    public List<IncitesExcelModel> incitesExcelRead(final byte[] data, final String fileName) {
        AnalysisEventListener listener;
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            listener = new IncitesExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read(new Sheet(1, 1, IncitesExcelModel.class));
        } catch (IOException e) {
            throw new ExcelException(e.getMessage());
        }
        List<IncitesExcelModel> incitesExcelModelList = ((IncitesExcelListener) listener).getVector();
        if (!ObjectUtil.ifNotNullCollection(incitesExcelModelList)) {
            throw new ExcelException("检查excel中可用数据为空,文件名称:" + fileName);
        }
        return incitesExcelModelList;
    }

    public List<PaperExcelModel> paperExcelRead(String filePath) {
        AnalysisEventListener listener;
        try (InputStream inputStream = new FileInputStream(filePath)) {
            listener = new PaperExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read(new Sheet(1, 6, PaperExcelModel.class));
        } catch (Exception e) {
            throw new ExcelException(e.getMessage());
        }
        List<PaperExcelModel> excelModels = ((PaperExcelListener) listener).getVector();
        if (!ObjectUtil.ifNotNullCollection(excelModels)) {
            throw new ExcelException("检查excel中可用数据为空,文件名称:" + filePath);
        }
        return excelModels;
    }
}
