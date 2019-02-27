package org.duohuo.paper.model.result;

import java.io.Serializable;
import java.util.Objects;

public class PaperResult implements Serializable {

    private static final long serialVersionUID = -4910753801427139422L;

    private Long paperId;

    private String accessionNumber;

    private String doi;

    private String pmid;

    private String articleName;

    private String authors;

    private String source;

    private String researchField;

    private Integer timesCited;

    private String countries;

    private String addresses;

    private String institutions;

    private Integer publicationDate;

    private String categoryName;

    private Integer elemNum;

    private Integer year;

    private Integer month;

    public PaperResult() {
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResearchField() {
        return researchField;
    }

    public void setResearchField(String researchField) {
        this.researchField = researchField;
    }

    public Integer getTimesCited() {
        return timesCited;
    }

    public void setTimesCited(Integer timesCited) {
        this.timesCited = timesCited;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getInstitutions() {
        return institutions;
    }

    public void setInstitutions(String institutions) {
        this.institutions = institutions;
    }

    public Integer getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Integer publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getElemNum() {
        return elemNum;
    }

    public void setElemNum(Integer elemNum) {
        this.elemNum = elemNum;
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
        PaperResult that = (PaperResult) o;
        return Objects.equals(paperId, that.paperId) &&
                Objects.equals(accessionNumber, that.accessionNumber) &&
                Objects.equals(doi, that.doi) &&
                Objects.equals(pmid, that.pmid) &&
                Objects.equals(articleName, that.articleName) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(source, that.source) &&
                Objects.equals(researchField, that.researchField) &&
                Objects.equals(timesCited, that.timesCited) &&
                Objects.equals(countries, that.countries) &&
                Objects.equals(addresses, that.addresses) &&
                Objects.equals(institutions, that.institutions) &&
                Objects.equals(publicationDate, that.publicationDate) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(elemNum, that.elemNum) &&
                Objects.equals(year, that.year) &&
                Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, accessionNumber, doi, pmid, articleName, authors, source, researchField, timesCited, countries, addresses, institutions, publicationDate, categoryName, elemNum, year, month);
    }

    @Override
    public String toString() {
        return "PaperResult{" +
                "paperId=" + paperId +
                ", accessionNumber='" + accessionNumber + '\'' +
                ", doi='" + doi + '\'' +
                ", pmid='" + pmid + '\'' +
                ", articleName='" + articleName + '\'' +
                ", authors='" + authors + '\'' +
                ", source='" + source + '\'' +
                ", researchField='" + researchField + '\'' +
                ", timesCited=" + timesCited +
                ", countries='" + countries + '\'' +
                ", addresses='" + addresses + '\'' +
                ", institutions='" + institutions + '\'' +
                ", publicationDate=" + publicationDate +
                ", categoryName='" + categoryName + '\'' +
                ", elemNum=" + elemNum +
                ", year=" + year +
                ", month=" + month +
                '}';
    }
}
