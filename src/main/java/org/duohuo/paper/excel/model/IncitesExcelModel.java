package org.duohuo.paper.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @author lwolvej
 */
public class IncitesExcelModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String accessionNumber;

    private String doi;

    private String pubmedId;

    private String articleName;

    private String link;

    private String authors;

    private String origin;

    private String subjectArea;

    private String volume;

    private String period;

    private String page;

    private Integer publishingYear;

    private Integer citedTimes;


}
