package org.duohuo.paper.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
@Entity
@Table(name = "journal_info")
public class Journal implements Serializable {

    private static final long serialVersionUID = -1390663160549481547L;

    @Id
    @Column(name = "journalId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalId;

    @Column(nullable = false, name = "full_title")
    private String fullTitle;

    @Column(nullable = false, name = "title_29")
    private String title29;

    @Column(nullable = false, name = "title_20")
    private String title20;

    @Column(name = "issn")
    private String issn;

    @Column(name = "eissn")
    private String eissn;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
        Journal journal = (Journal) o;
        return Objects.equals(journalId, journal.journalId) &&
                Objects.equals(fullTitle, journal.fullTitle) &&
                Objects.equals(title29, journal.title29) &&
                Objects.equals(title20, journal.title20) &&
                Objects.equals(issn, journal.issn) &&
                Objects.equals(eissn, journal.eissn) &&
                Objects.equals(time, journal.time) &&
                Objects.equals(category, journal.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalId, fullTitle, title29, title20, issn, eissn, time, category);
    }

    @Override
    public String toString() {
        return "Journal{" +
                "journalId=" + journalId +
                ", fullTitle='" + fullTitle + '\'' +
                ", title29='" + title29 + '\'' +
                ", title20='" + title20 + '\'' +
                ", issn='" + issn + '\'' +
                ", eissn='" + eissn + '\'' +
                ", time=" + time +
                ", category=" + category +
                '}';
    }
}
