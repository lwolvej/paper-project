package org.duohuo.paper.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.duohuo.paper.excel.model.download.IncitesDownloadExcelModel;
import org.duohuo.paper.excel.model.download.JournalDownloadModel;
import org.duohuo.paper.excel.model.download.PaperExcelDownloadModel;
import org.duohuo.paper.exceptions.ExcelException;
import org.springframework.boot.system.ApplicationHome;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


public final class ExcelUtil {

    //type=1为paper，type=2为journal
    public static byte[] getDownByte(final List<BaseRowModel> data, final int type) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExcelWriter excelWriter = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        if (type == 1) {
            excelWriter.write(data, ExcelUtil.createPaperSheet());
        } else if (type == 2) {
            excelWriter.write(data, ExcelUtil.createJournalSheet());
        } else {
            excelWriter.write(data, ExcelUtil.createIncitesSheet());
        }
        excelWriter.finish();
        byte[] bytes = outputStream.toByteArray();
        try {
            outputStream.close();
            return bytes;
        } catch (IOException e) {
            throw new ExcelException(e.getMessage());
        }
    }

    public static String getJarPath() {
        String path = new ApplicationHome(ExcelUtil.class).getSource().getAbsolutePath();
        return path.substring(
                path.lastIndexOf(System.getProperty("path.separator")) + 1,
                path.lastIndexOf(File.separator) + 1
        );
    }

    private static Sheet createIncitesSheet() {
        Sheet sheet = new Sheet(1, 19, IncitesDownloadExcelModel.class);
        sheet.setSheetName("sheet1");
        sheet.setTableStyle(createTableStyle());
        sheet.setAutoWidth(true);
        return sheet;
    }

    private static Sheet createPaperSheet() {
        Sheet sheet = new Sheet(1, 11, PaperExcelDownloadModel.class);
        sheet.setSheetName("sheet1");
        sheet.setTableStyle(createTableStyle());
        sheet.setAutoWidth(true);
        return sheet;
    }

    private static Sheet createJournalSheet() {
        Sheet sheet = new Sheet(1, 7, JournalDownloadModel.class);
        sheet.setSheetName("sheet1");
        sheet.setTableStyle(createTableStyle());
        sheet.setAutoWidth(true);
        return sheet;
    }

    private static TableStyle createTableStyle() {
        TableStyle tableStyle = new TableStyle();
        Font headFont = new Font();
        headFont.setBold(false);
        headFont.setFontName("宋体(正文)");
        headFont.setFontHeightInPoints((short) 11);
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.WHITE);
        Font contentFont = new Font();
        contentFont.setBold(false);
        contentFont.setFontName("宋体(正文)");
        contentFont.setFontHeightInPoints((short) 11);
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
        return tableStyle;
    }
}
