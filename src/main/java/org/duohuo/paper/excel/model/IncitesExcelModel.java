package org.duohuo.paper.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
public class IncitesExcelModel extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = -5413609546076671792L;

    @ExcelProperty(index = 0)
    private String accessionNumber;

    @ExcelProperty(index = 1)
    private String doi;

    //这里修改
    @ExcelProperty(index = 2)
    private String pmid;

    @ExcelProperty(index = 3)
    private String articleName;

    @ExcelProperty(index = 4)
    private String link;

    @ExcelProperty(index = 5)
    private String authors;

    @ExcelProperty(index = 6)
    private String sources;

    //这里修改
    @ExcelProperty(index = 7)
    private String researchField;

    @ExcelProperty(index = 8)
    private String volume;

    @ExcelProperty(index = 9)
    private String period;

    @ExcelProperty(index = 10)
    private String page;

    //    这里修改
    @ExcelProperty(index = 11)
    private Integer publicationDate;

    @ExcelProperty(index = 12)
    private Integer citedTimes;

    @ExcelProperty(index = 13)
    private String journalExpectCitedTimes;

    @ExcelProperty(index = 14)
    private String subjectExpectCitedTimes;

    @ExcelProperty(index = 15)
    private String journalInfluence;

    @ExcelProperty(index = 16)
    private String subjectInfluence;

    @ExcelProperty(index = 17)
    private String subjectAreaPercentile;

    @ExcelProperty(index = 18)
    private String journalImpactFactor;

    public IncitesExcelModel() {
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

    public Integer getCitedTimes() {
        return citedTimes;
    }

    public void setCitedTimes(Integer citedTimes) {
        this.citedTimes = citedTimes;
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

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getResearchField() {
        return researchField;
    }

    public void setResearchField(String researchField) {
        this.researchField = researchField;
    }

    public Integer getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Integer publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncitesExcelModel that = (IncitesExcelModel) o;
        return Objects.equals(accessionNumber, that.accessionNumber) &&
                Objects.equals(doi, that.doi) &&
                Objects.equals(pmid, that.pmid) &&
                Objects.equals(articleName, that.articleName) &&
                Objects.equals(link, that.link) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(sources, that.sources) &&
                Objects.equals(researchField, that.researchField) &&
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
                Objects.equals(journalImpactFactor, that.journalImpactFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessionNumber, doi, pmid, articleName, link, authors, sources, researchField, volume, period, page, publicationDate, citedTimes, journalExpectCitedTimes, subjectExpectCitedTimes, journalInfluence, subjectInfluence, subjectAreaPercentile, journalImpactFactor);
    }

    @Override
    public String toString() {
        return "IncitesExcelModel{" +
                "accessionNumber='" + accessionNumber + '\'' +
                ", doi='" + doi + '\'' +
                ", pmid='" + pmid + '\'' +
                ", articleName='" + articleName + '\'' +
                ", link='" + link + '\'' +
                ", authors='" + authors + '\'' +
                ", sources='" + sources + '\'' +
                ", researchField='" + researchField + '\'' +
                ", volume='" + volume + '\'' +
                ", period='" + period + '\'' +
                ", page='" + page + '\'' +
                ", publicationDate=" + publicationDate +
                ", citedTimes=" + citedTimes +
                ", journalExpectCitedTimes=" + journalExpectCitedTimes +
                ", subjectExpectCitedTimes=" + subjectExpectCitedTimes +
                ", journalInfluence=" + journalInfluence +
                ", subjectInfluence=" + subjectInfluence +
                ", subjectAreaPercentile=" + subjectAreaPercentile +
                ", journalImpactFactor='" + journalImpactFactor + '\'' +
                '}';
    }
}
