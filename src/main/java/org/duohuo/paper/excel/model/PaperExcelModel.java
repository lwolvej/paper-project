package org.duohuo.paper.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.model.Category;
import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.PaperType;
import org.duohuo.paper.model.Time;

import java.util.Objects;

/**
 * @author lwolvej
 */
public class PaperExcelModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String accessionNumber;

    @ExcelProperty(index = 1)
    private String doi;

    @ExcelProperty(index = 2)
    private String pmid;

    @ExcelProperty(index = 3)
    private String articleName;

    @ExcelProperty(index = 4)
    private String authors;

    @ExcelProperty(index = 5)
    private String source;

    @ExcelProperty(index = 6)
    private String researchField;

    @ExcelProperty(index = 7)
    private Integer timeCited;

    @ExcelProperty(index = 8)
    private String countries;

    @ExcelProperty(index = 9)
    private String address;

    @ExcelProperty(index = 10)
    private String institutions;

    @ExcelProperty(index = 11)
    private Integer publicationDate;

    public PaperExcelModel() {
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

    public Integer getTimeCited() {
        return timeCited;
    }

    public void setTimeCited(Integer timeCited) {
        this.timeCited = timeCited;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperExcelModel that = (PaperExcelModel) o;
        return Objects.equals(accessionNumber, that.accessionNumber) &&
                Objects.equals(doi, that.doi) &&
                Objects.equals(pmid, that.pmid) &&
                Objects.equals(articleName, that.articleName) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(source, that.source) &&
                Objects.equals(researchField, that.researchField) &&
                Objects.equals(timeCited, that.timeCited) &&
                Objects.equals(countries, that.countries) &&
                Objects.equals(address, that.address) &&
                Objects.equals(institutions, that.institutions) &&
                Objects.equals(publicationDate, that.publicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessionNumber, doi, pmid, articleName, authors, source, researchField, timeCited, countries, address, institutions, publicationDate);
    }

    @Override
    public String toString() {
        return "PaperExcelModel{" +
                "accessionNumber='" + accessionNumber + '\'' +
                ", doi='" + doi + '\'' +
                ", pmid='" + pmid + '\'' +
                ", articleName='" + articleName + '\'' +
                ", authors='" + authors + '\'' +
                ", source='" + source + '\'' +
                ", researchField='" + researchField + '\'' +
                ", timeCited=" + timeCited +
                ", countries='" + countries + '\'' +
                ", address='" + address + '\'' +
                ", institutions='" + institutions + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
