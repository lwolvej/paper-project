package org.duohuo.paper.excel.model.download;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.util.Objects;

public class IncitesDownloadExcelModel extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = -8322970178849489286L;

    @ExcelProperty(index = 0, value = "索引号")
    private String accessionNumber;

    @ExcelProperty(index = 1, value = "DOI")
    private String doi;

    @ExcelProperty(index = 2, value = "Pubmed ID")
    private String pmid;

    @ExcelProperty(index = 3, value = "论文标题")
    private String articleName;

    @ExcelProperty(index = 4, value = "链接")
    private String link;

    @ExcelProperty(index = 5, value = "作者")
    private String authors;

    @ExcelProperty(index = 6, value = "来源")
    private String sources;

    @ExcelProperty(index = 7, value = "学科领域")
    private String categoryName;

    @ExcelProperty(index = 8, value = "卷")
    private String volume;

    @ExcelProperty(index = 9, value = "期")
    private String period;

    @ExcelProperty(index = 10, value = "页")
    private String page;

    @ExcelProperty(index = 11, value = "出版年")
    private Integer publicationDate;

    @ExcelProperty(index = 12, value = "被引频次")
    private Integer citedTimes;

    @ExcelProperty(index = 13, value = "期刊预期被引频次")
    private String journalExpectCitedTimes;

    @ExcelProperty(index = 14, value = "类别预期被引频次")
    private String subjectExpectCitedTimes;

    @ExcelProperty(index = 15, value = "期刊规范化的引文影响力")
    private String journalInfluence;

    @ExcelProperty(index = 16, value = "学科规范化的引文影响力")
    private String subjectInfluence;

    @ExcelProperty(index = 17, value = "学科领域百分位")
    private String subjectAreaPercentile;

    @ExcelProperty(index = 18, value = "期刊影响因子")
    private String journalImpactFactor;

    @ExcelProperty(index = 19, value = "被引频次/基准线")
    private String value;

    public IncitesDownloadExcelModel() {
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Integer publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getJournalExpectCitedTimes() {
        return journalExpectCitedTimes;
    }

    public void setJournalExpectCitedTimes(String journalExpectCitedTimes) {
        this.journalExpectCitedTimes = journalExpectCitedTimes;
    }

    public String getSubjectExpectCitedTimes() {
        return subjectExpectCitedTimes;
    }

    public void setSubjectExpectCitedTimes(String subjectExpectCitedTimes) {
        this.subjectExpectCitedTimes = subjectExpectCitedTimes;
    }

    public String getJournalInfluence() {
        return journalInfluence;
    }

    public void setJournalInfluence(String journalInfluence) {
        this.journalInfluence = journalInfluence;
    }

    public String getSubjectInfluence() {
        return subjectInfluence;
    }

    public void setSubjectInfluence(String subjectInfluence) {
        this.subjectInfluence = subjectInfluence;
    }

    public String getSubjectAreaPercentile() {
        return subjectAreaPercentile;
    }

    public void setSubjectAreaPercentile(String subjectAreaPercentile) {
        this.subjectAreaPercentile = subjectAreaPercentile;
    }

    public String getJournalImpactFactor() {
        return journalImpactFactor;
    }

    public void setJournalImpactFactor(String journalImpactFactor) {
        this.journalImpactFactor = journalImpactFactor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCitedTimes() {
        return citedTimes;
    }

    public void setCitedTimes(Integer citedTimes) {
        this.citedTimes = citedTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncitesDownloadExcelModel that = (IncitesDownloadExcelModel) o;
        return Objects.equals(accessionNumber, that.accessionNumber) &&
                Objects.equals(doi, that.doi) &&
                Objects.equals(pmid, that.pmid) &&
                Objects.equals(articleName, that.articleName) &&
                Objects.equals(link, that.link) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(sources, that.sources) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(period, that.period) &&
                Objects.equals(page, that.page) &&
                Objects.equals(publicationDate, that.publicationDate) &&
                Objects.equals(citedTimes, that.citedTimes) &&
                Objects.equals(journalExpectCitedTimes, that.journalExpectCitedTimes) &&
                Objects.equals(subjectExpectCitedTimes, that.subjectExpectCitedTimes) &&
                Objects.equals(journalInfluence, that.journalInfluence) &&
                Objects.equals(subjectInfluence, that.subjectInfluence) &&
                Objects.equals(subjectAreaPercentile, that.subjectAreaPercentile) &&
                Objects.equals(journalImpactFactor, that.journalImpactFactor) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessionNumber, doi, pmid, articleName, link, authors, sources, categoryName, volume, period, page, publicationDate, citedTimes, journalExpectCitedTimes, subjectExpectCitedTimes, journalInfluence, subjectInfluence, subjectAreaPercentile, journalImpactFactor, value);
    }

    @Override
    public String toString() {
        return "IncitesDownloadExcelModel{" +
                "accessionNumber='" + accessionNumber + '\'' +
                ", doi='" + doi + '\'' +
                ", pmid='" + pmid + '\'' +
                ", articleName='" + articleName + '\'' +
                ", link='" + link + '\'' +
                ", authors='" + authors + '\'' +
                ", sources='" + sources + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", volume='" + volume + '\'' +
                ", period='" + period + '\'' +
                ", page='" + page + '\'' +
                ", publicationDate=" + publicationDate +
                ", citedTimes=" + citedTimes +
                ", journalExpectCitedTimes='" + journalExpectCitedTimes + '\'' +
                ", subjectExpectCitedTimes='" + subjectExpectCitedTimes + '\'' +
                ", journalInfluence='" + journalInfluence + '\'' +
                ", subjectInfluence='" + subjectInfluence + '\'' +
                ", subjectAreaPercentile='" + subjectAreaPercentile + '\'' +
                ", journalImpactFactor='" + journalImpactFactor + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
