package org.duohuo.paper.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;

import java.util.Objects;

/**
 * @author lwolvej
 */
public class JournalExcelModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String fullTitle;

    @ExcelProperty(index = 1)
    private String title29;

    @ExcelProperty(index = 2)
    private String title20;

    @ExcelProperty(index = 3)
    private String issn;

    @ExcelProperty(index = 4)
    private String eissn;

    @ExcelProperty(index = 5)
    private String categoryName;


    public JournalExcelModel() {
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getTitle29() {
        return title29;
    }

    public void setTitle29(String title29) {
        this.title29 = title29;
    }

    public String getTitle20() {
        return title20;
    }

    public void setTitle20(String title20) {
        this.title20 = title20;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getEissn() {
        return eissn;
    }

    public void setEissn(String eissn) {
        this.eissn = eissn;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalExcelModel that = (JournalExcelModel) o;
        return Objects.equals(fullTitle, that.fullTitle) &&
                Objects.equals(title29, that.title29) &&
                Objects.equals(title20, that.title20) &&
                Objects.equals(issn, that.issn) &&
                Objects.equals(eissn, that.eissn) &&
                Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullTitle, title29, title20, issn, eissn, categoryName);
    }

    @Override
    public String toString() {
        return "JournalExcelModel{" +
                "fullTitle='" + fullTitle + '\'' +
                ", title29='" + title29 + '\'' +
                ", title20='" + title20 + '\'' +
                ", issn='" + issn + '\'' +
                ", eissn='" + eissn + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    public Journal convertToJournal(Category category, Time time) {
        Journal journal = new Journal();
        journal.setFullTitle(this.fullTitle);
        journal.setCategory(category);
        journal.setTime(time);
        journal.setTitle29(this.title29);
        journal.setTitle20(this.title20);
        journal.setIssn(this.issn);
        journal.setEissn(this.eissn);
        return journal;
    }
}
