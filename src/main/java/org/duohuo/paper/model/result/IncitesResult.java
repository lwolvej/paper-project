package org.duohuo.paper.model.result;

import java.io.Serializable;
import java.util.Objects;

public class IncitesResult implements Serializable {

    private static final long serialVersionUID = 8766107982760015969L;

    private Integer incitesId;

    private String accessionNumber;

    private String doi;

    private String pmid;

    private String articleName;

    private String link;

    private String authors;

    private String sources;

    private String volume;

    private String period;

    private String page;

    private Integer publicationDate;

    private Integer citedTimes;

    private String journalExpectCitedTimes;

    private String subjectExpectCitedTimes;

    private String journalInfluence;

    private String subjectInfluence;

    private String subjectAreaPercentile;

    private String journalImpactFactor;

    private String categoryName;

    private Integer valueType;

    private Double value;

    private Integer elemNum;

    public IncitesResult() {
    }

    public Integer getIncitesId() {
        return incitesId;
    }

    public void setIncitesId(Integer incitesId) {
        this.incitesId = incitesId;
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

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getCitedTimes() {
        return citedTimes;
    }

    public void setCitedTimes(Integer citedTimes) {
        this.citedTimes = citedTimes;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getElemNum() {
        return elemNum;
    }

    public void setElemNum(Integer elemNum) {
        this.elemNum = elemNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncitesResult result = (IncitesResult) o;
        return Objects.equals(incitesId, result.incitesId) &&
                Objects.equals(accessionNumber, result.accessionNumber) &&
                Objects.equals(doi, result.doi) &&
                Objects.equals(pmid, result.pmid) &&
                Objects.equals(articleName, result.articleName) &&
                Objects.equals(link, result.link) &&
                Objects.equals(authors, result.authors) &&
                Objects.equals(sources, result.sources) &&
                Objects.equals(volume, result.volume) &&
                Objects.equals(period, result.period) &&
                Objects.equals(page, result.page) &&
                Objects.equals(publicationDate, result.publicationDate) &&
                Objects.equals(citedTimes, result.citedTimes) &&
                Objects.equals(journalExpectCitedTimes, result.journalExpectCitedTimes) &&
                Objects.equals(subjectExpectCitedTimes, result.subjectExpectCitedTimes) &&
                Objects.equals(journalInfluence, result.journalInfluence) &&
                Objects.equals(subjectInfluence, result.subjectInfluence) &&
                Objects.equals(subjectAreaPercentile, result.subjectAreaPercentile) &&
                Objects.equals(journalImpactFactor, result.journalImpactFactor) &&
                Objects.equals(categoryName, result.categoryName) &&
                Objects.equals(valueType, result.valueType) &&
                Objects.equals(value, result.value) &&
                Objects.equals(elemNum, result.elemNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incitesId, accessionNumber, doi, pmid, articleName, link, authors, sources, volume, period, page, publicationDate, citedTimes, journalExpectCitedTimes, subjectExpectCitedTimes, journalInfluence, subjectInfluence, subjectAreaPercentile, journalImpactFactor, categoryName, valueType, value, elemNum);
    }

    @Override
    public String toString() {
        return "IncitesResult{" +
                "incitesId=" + incitesId +
                ", accessionNumber='" + accessionNumber + '\'' +
                ", doi='" + doi + '\'' +
                ", pmid='" + pmid + '\'' +
                ", articleName='" + articleName + '\'' +
                ", link='" + link + '\'' +
                ", authors='" + authors + '\'' +
                ", sources='" + sources + '\'' +
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
                ", categoryName='" + categoryName + '\'' +
                ", valueType=" + valueType +
                ", value=" + value +
                ", elemNum=" + elemNum +
                '}';
    }
}
