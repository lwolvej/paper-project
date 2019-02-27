package org.duohuo.paper.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "base_line_info")
public class BaseLine implements Serializable {

    private static final long serialVersionUID = -1300479812665656277L;

    //这里为了便于更新，改为year+percent+categoryName的字符串
    @Id
    @Column(name = "base_line_id")
    private String baseLineId;

    @Column(name = "year")
    private String year;

    @Column(name = "percent")
    private String percent;

    @Column(name = "value")
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public BaseLine() {
    }

    public String getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(String baseLineId) {
        this.baseLineId = baseLineId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseLine baseLine = (BaseLine) o;
        return Objects.equals(baseLineId, baseLine.baseLineId) &&
                Objects.equals(year, baseLine.year) &&
                Objects.equals(percent, baseLine.percent) &&
                Objects.equals(category, baseLine.category) &&
                Objects.equals(value, baseLine.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseLineId, year, percent, category, value);
    }

    @Override
    public String toString() {
        return "BaseLine{" +
                "baseLineId=" + baseLineId +
                ", year='" + year + '\'' +
                ", percent='" + percent + '\'' +
                ", category=" + category +
                ", value=" + value +
                '}';
    }
}
