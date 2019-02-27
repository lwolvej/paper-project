package org.duohuo.paper.model.result;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
public class JournalResult implements Serializable {

    private static final long serialVersionUID = -5005993356975177932L;

    private Long journalId;

    private Integer elemNum;

    private String fullTitle;

    private String title29;

    private String title20;

    private String issn;

    private String eissn;

    private String categoryName;

    private Integer year;

    private Integer month;

    public JournalResult() {
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Integer getElemNum() {
        return elemNum;
    }

    public void setElemNum(Integer elemNum) {
        this.elemNum = elemNum;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalResult that = (JournalResult) o;
        return Objects.equals(journalId, that.journalId) &&
                Objects.equals(elemNum, that.elemNum) &&
                Objects.equals(fullTitle, that.fullTitle) &&
                Objects.equals(title29, that.title29) &&
                Objects.equals(title20, that.title20) &&
                Objects.equals(issn, that.issn) &&
                Objects.equals(eissn, that.eissn) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(year, that.year) &&
                Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalId, elemNum, fullTitle, title29, title20, issn, eissn, categoryName, year, month);
    }

    @Override
    public String toString() {
        return "JournalResult{" +
                "journalId=" + journalId +
                ", elemNum=" + elemNum +
                ", fullTitle='" + fullTitle + '\'' +
                ", title29='" + title29 + '\'' +
                ", title20='" + title20 + '\'' +
                ", issn='" + issn + '\'' +
                ", eissn='" + eissn + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", year=" + year +
                ", month=" + month +
                '}';
    }
}
