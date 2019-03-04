package org.duohuo.paper.excel.model.download;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.model.Paper;
import org.duohuo.paper.model.Time;

import java.io.Serializable;
import java.util.Objects;

public class PaperExcelDownloadModel extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = -7999422694160499321L;

    @ExcelProperty(index = 0, value = "ACCESSION NUMBER")
    private String accessionNumber;

    @ExcelProperty(index = 1, value = "DOI")
    private String doi;

    @ExcelProperty(index = 2, value = "PMID")
    private String pmid;

    @ExcelProperty(index = 3, value = "ARTICLE NAME")
    private String articleName;

    @ExcelProperty(index = 4, value = "AUTHORS")
    private String authors;

    @ExcelProperty(index = 5, value = "SOURCE")
    private String source;

    @ExcelProperty(index = 6, value = "RESEARCH FIELD")
    private String researchField;

    @ExcelProperty(index = 7, value = "TIME CITED")
    private Integer timeCited;

    @ExcelProperty(index = 8, value = "ADDRESS")
    private String address;

    @ExcelProperty(index = 9, value = "INSTITUTIONS")
    private String institutions;

    @ExcelProperty(index = 10, value = "PUBLICATION DATE")
    private Integer publicationDate;

    @ExcelProperty(index = 11, value = "TIME")
    private String time;

    public PaperExcelDownloadModel() {
    }

    public PaperExcelDownloadModel(Paper paper) {
        this.setAccessionNumber(paper.getAccessionNumber());
        this.setDoi(paper.getDoi());
        this.setPmid(paper.getPmid());
        this.setArticleName(paper.getArticleName());
        this.setSource(paper.getSource());
        this.setResearchField(paper.getResearchField());
        this.setTimeCited(paper.getTimesCited());
        this.setAddress(paper.getAddresses());
        this.setInstitutions(paper.getInstitutions());
        this.setPublicationDate(paper.getPublicationDate());
        Time time = new Time();
        this.setTime(time.getYear() + "/" + time.getMonth());
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
        PaperExcelDownloadModel that = (PaperExcelDownloadModel) o;
        return Objects.equals(accessionNumber, that.accessionNumber) &&
                Objects.equals(doi, that.doi) &&
                Objects.equals(pmid, that.pmid) &&
                Objects.equals(articleName, that.articleName) &&
                Objects.equals(authors, that.authors) &&
                Objects.equals(source, that.source) &&
                Objects.equals(researchField, that.researchField) &&
                Objects.equals(timeCited, that.timeCited) &&
                Objects.equals(address, that.address) &&
                Objects.equals(institutions, that.institutions) &&
                Objects.equals(publicationDate, that.publicationDate) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessionNumber, doi, pmid, articleName, authors, source, researchField, timeCited, address, institutions, publicationDate, time);
    }

    @Override
    public String toString() {
        return "PaperExcelDownloadModel{" +
                "accessionNumber='" + accessionNumber + '\'' +
                ", doi='" + doi + '\'' +
                ", pmid='" + pmid + '\'' +
                ", articleName='" + articleName + '\'' +
                ", authors='" + authors + '\'' +
                ", source='" + source + '\'' +
                ", researchField='" + researchField + '\'' +
                ", timeCited=" + timeCited +
                ", address='" + address + '\'' +
                ", institutions='" + institutions + '\'' +
                ", publicationDate=" + publicationDate +
                ", time='" + time + '\'' +
                '}';
    }
}
