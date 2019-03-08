package org.duohuo.paper.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "incites_info")
public class Incites implements Serializable {

    private static final long serialVersionUID = 2798495665037402878L;

    @Id
    @Column(name = "incites_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incitesId;

    @Column(name = "accession_number", columnDefinition = "CHAR(<21)")
    private String accessionNumber;

    @Column(name = "doi")
    private String doi;

    @Column(name = "pmid")
    private String pmid;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "article_name", columnDefinition = "TEXT")
    private String articleName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "authors", columnDefinition = "TEXT")
    private String authors;

    @Column(name = "sources")
    private String sources;

    @Column(name = "volume")
    private String volume;

    @Column(name = "period")
    private String period;

    @Column(name = "page")
    private String page;

    @Column(name = "publication_date")
    private Integer publicationDate;

    @Column(name = "cited_times")
    private Integer citedTimes;

    @Column(name = "journal_expect_cited_times")
    private String journalExpectCitedTimes;

    @Column(name = "subject_expect_cited_times")
    private String subjectExpectCitedTimes;

    @Column(name = "journal_influence")
    private String journalInfluence;

    @Column(name = "subjectInfluence")
    private String subjectInfluence;

    @Column(name = "subject_area_percentile")
    private String subjectAreaPercentile;

    @Column(name = "journal_impact_factor")
    private String journalImpactFactor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Incites() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incites incites = (Incites) o;
        return Objects.equals(incitesId, incites.incitesId) &&
                Objects.equals(accessionNumber, incites.accessionNumber) &&
                Objects.equals(doi, incites.doi) &&
                Objects.equals(pmid, incites.pmid) &&
                Objects.equals(articleName, incites.articleName) &&
                Objects.equals(link, incites.link) &&
                Objects.equals(authors, incites.authors) &&
                Objects.equals(sources, incites.sources) &&
                Objects.equals(volume, incites.volume) &&
                Objects.equals(period, incites.period) &&
                Objects.equals(page, incites.page) &&
                Objects.equals(publicationDate, incites.publicationDate) &&
                Objects.equals(citedTimes, incites.citedTimes) &&
                Objects.equals(journalExpectCitedTimes, incites.journalExpectCitedTimes) &&
                Objects.equals(subjectExpectCitedTimes, incites.subjectExpectCitedTimes) &&
                Objects.equals(journalInfluence, incites.journalInfluence) &&
                Objects.equals(subjectInfluence, incites.subjectInfluence) &&
                Objects.equals(subjectAreaPercentile, incites.subjectAreaPercentile) &&
                Objects.equals(journalImpactFactor, incites.journalImpactFactor) &&
                Objects.equals(category, incites.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incitesId, accessionNumber, doi, pmid, articleName, link, authors, sources, volume, period, page, publicationDate, citedTimes, journalExpectCitedTimes, subjectExpectCitedTimes, journalInfluence, subjectInfluence, subjectAreaPercentile, journalImpactFactor, category);
    }

    @Override
    public String toString() {
        return "Incites{" +
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
                ", journalExpectCitedTimes=" + journalExpectCitedTimes +
                ", subjectExpectCitedTimes=" + subjectExpectCitedTimes +
                ", journalInfluence=" + journalInfluence +
                ", subjectInfluence=" + subjectInfluence +
                ", subjectAreaPercentile=" + subjectAreaPercentile +
                ", journalImpactFactor='" + journalImpactFactor + '\'' +
                ", category=" + category +
                '}';
    }
}
