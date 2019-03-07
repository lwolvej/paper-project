package org.duohuo.paper.model;

import org.duohuo.paper.convert.PaperTypeConvert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
@Entity
@Table(name = "paper_info")
public class Paper implements Serializable {

    private static final long serialVersionUID = 3078296910754976258L;

    @Id
    @Column(name = "paperId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paperId;

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
    @Column(name = "authors", columnDefinition = "TEXT")
    private String authors;

    @Column(name = "source")
    private String source;

    @Column(name = "research_field")
    private String researchField;

    @Column(name = "times_cited")
    private Integer timesCited;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "countries", columnDefinition = "TEXT")
    private String countries;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "addresses", columnDefinition = "TEXT")
    private String addresses;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "institutions", columnDefinition = "TEXT")
    private String institutions;

    @Column(name = "publication_date")
    private Integer publicationDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    @Convert(converter = PaperTypeConvert.class)
    @Column(name = "paper_type")
    private PaperType paperType;

    public Paper() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paper paper = (Paper) o;
        return Objects.equals(paperId, paper.paperId) &&
                Objects.equals(accessionNumber, paper.accessionNumber) &&
                Objects.equals(doi, paper.doi) &&
                Objects.equals(pmid, paper.pmid) &&
                Objects.equals(articleName, paper.articleName) &&
                Objects.equals(authors, paper.authors) &&
                Objects.equals(source, paper.source) &&
                Objects.equals(researchField, paper.researchField) &&
                Objects.equals(timesCited, paper.timesCited) &&
                Objects.equals(countries, paper.countries) &&
                Objects.equals(addresses, paper.addresses) &&
                Objects.equals(institutions, paper.institutions) &&
                Objects.equals(publicationDate, paper.publicationDate) &&
                Objects.equals(category, paper.category) &&
                Objects.equals(time, paper.time) &&
                paperType == paper.paperType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, accessionNumber, doi, pmid, articleName, authors, source, researchField, timesCited, countries, addresses, institutions, publicationDate, category, time, paperType);
    }

    @Override
    public String toString() {
        return "Paper{" +
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
                ", category=" + category +
                ", time=" + time +
                ", paperType=" + paperType +
                '}';
    }
}
