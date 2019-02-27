package org.duohuo.paper.utils;

import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.duohuo.paper.excel.model.download.JournalDownloadModel;

import java.io.File;


public final class ExcelUtil {

    public static String getJarPath() {
        String path = ExcelUtil.class.getProtectionDomain()
                .getCodeSource().getLocation()
                .getPath();
        return path.substring(path.lastIndexOf(System.getProperty("path.separator")) + 1,
                path.lastIndexOf(File.separator) + 1);
    }

    public static Sheet createJournalSheet() {
        Sheet sheet = new Sheet(1, 7, JournalDownloadModel.class);
        sheet.setSheetName("sheet1");
        sheet.setTableStyle(createJournalTableStyle());
        sheet.setAutoWidth(true);
        return sheet;
    }

    private static TableStyle createJournalTableStyle() {
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
