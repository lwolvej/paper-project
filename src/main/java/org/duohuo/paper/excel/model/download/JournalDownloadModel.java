package org.duohuo.paper.excel.model.download;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.model.Journal;
import org.duohuo.paper.model.Time;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
public class JournalDownloadModel extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = -2742153494781461596L;

    @ExcelProperty(index = 0, value = "Full title")
    private String fullTitle;

    @ExcelProperty(index = 1, value = "Title29")
    private String title29;

    @ExcelProperty(index = 2, value = "Title20")
    private String title20;

    @ExcelProperty(index = 3, value = "ISSN")
    private String issn;

    @ExcelProperty(index = 4, value = "EISSN")
    private String eissn;

    @ExcelProperty(index = 5, value = "Category name")
    private String categoryName;

    @ExcelProperty(index = 6, value = "Time")
    private String time;

    public JournalDownloadModel() {
    }

    public JournalDownloadModel(Journal esiInfo) {
        setFullTitle(esiInfo.getFullTitle());
        setTitle29(esiInfo.getTitle29());
        setTitle20(esiInfo.getTitle20());
        setIssn(esiInfo.getIssn());
        setEissn(esiInfo.getEissn());
        setCategoryName(esiInfo.getCategory().getCategoryName());
        Time timePeriod = esiInfo.getTime();
        setTime(timePeriod.getYear() + "/" + timePeriod.getMonth());
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalDownloadModel that = (JournalDownloadModel) o;
        return Objects.equals(fullTitle, that.fullTitle) &&
                Objects.equals(title29, that.title29) &&
                Objects.equals(title20, that.title20) &&
                Objects.equals(issn, that.issn) &&
                Objects.equals(eissn, that.eissn) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullTitle, title29, title20, issn, eissn, categoryName, time);
    }

    @Override
    public String toString() {
        return "JournalDownloadModel{" +
                "fullTitle='" + fullTitle + '\'' +
                ", title29='" + title29 + '\'' +
                ", title20='" + title20 + '\'' +
                ", issn='" + issn + '\'' +
                ", eissn='" + eissn + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
