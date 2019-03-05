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

    private Double journalExpectCitedTimes;

    private Double subjectExpectCitedTimes;

    private Double journalInfluence;

    private Double subjectInfluence;

    private Double subjectAreaPercentile;

    private String journalImpactFactor;

    private String categoryName;

    private Boolean valueExist;

    private Double value;

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

    public Double getJournalExpectCitedTimes() {
        return journalExpectCitedTimes;
    }

    public void setJournalExpectCitedTimes(Double journalExpectCitedTimes) {
        this.journalExpectCitedTimes = journalExpectCitedTimes;
    }

    public Double getSubjectExpectCitedTimes() {
        return subjectExpectCitedTimes;
    }

    public void setSubjectExpectCitedTimes(Double subjectExpectCitedTimes) {
        this.subjectExpectCitedTimes = subjectExpectCitedTimes;
    }

    public Double getJournalInfluence() {
        return journalInfluence;
    }

    public void setJournalInfluence(Double journalInfluence) {
        this.journalInfluence = journalInfluence;
    }

    public Double getSubjectAreaPercentile() {
        return subjectAreaPercentile;
    }

    public void setSubjectAreaPercentile(Double subjectAreaPercentile) {
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

    public Boolean getValueExist() {
        return valueExist;
    }

    public void setValueExist(Boolean valueExist) {
        this.valueExist = valueExist;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getSubjectInfluence() {
        return subjectInfluence;
    }

    public void setSubjectInfluence(Double subjectInfluence) {
        this.subjectInfluence = subjectInfluence;
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
                Objects.equals(journalExpectCitedTimes, result.journalExpectCitedTimes) &&
                Objects.equals(subjectExpectCitedTimes, result.subjectExpectCitedTimes) &&
                Objects.equals(journalInfluence, result.journalInfluence) &&
                Objects.equals(subjectInfluence, result.subjectInfluence) &&
                Objects.equals(subjectAreaPercentile, result.subjectAreaPercentile) &&
                Objects.equals(journalImpactFactor, result.journalImpactFactor) &&
                Objects.equals(categoryName, result.categoryName) &&
                Objects.equals(valueExist, result.valueExist) &&
                Objects.equals(value, result.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incitesId, accessionNumber, doi, pmid, articleName, link, authors, sources, volume, period, page, publicationDate, journalExpectCitedTimes, subjectExpectCitedTimes, journalInfluence, subjectInfluence, subjectAreaPercentile, journalImpactFactor, categoryName, valueExist, value);
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
                ", journalExpectCitedTimes=" + journalExpectCitedTimes +
                ", subjectExpectCitedTimes=" + subjectExpectCitedTimes +
                ", journalInfluence=" + journalInfluence +
                ", subjectInfluence=" + subjectInfluence +
                ", subjectAreaPercentile=" + subjectAreaPercentile +
                ", journalImpactFactor='" + journalImpactFactor + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", valueExist=" + valueExist +
                ", value=" + value +
                '}';
    }
}
