package org.duohuo.paper.model.result;

import org.duohuo.paper.model.BaseLine;

import java.io.Serializable;
import java.util.Objects;

public class BaseLineResult implements Serializable {

    private static final long serialVersionUID = -7414250354512194113L;

    private String subject;

    private String percent;

    private String year;

    private Integer value;

    public BaseLineResult() {
    }

    public BaseLineResult(BaseLine baseLine) {
        this.setPercent(baseLine.getPercent());
        this.setValue(baseLine.getValue());
        this.setYear(baseLine.getYear());
        this.setSubject(baseLine.getCategory().getCategoryName());
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
        BaseLineResult that = (BaseLineResult) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(percent, that.percent) &&
                Objects.equals(year, that.year) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, percent, year, value);
    }

    @Override
    public String toString() {
        return "BaseLineResult{" +
                "subject='" + subject + '\'' +
                ", percent='" + percent + '\'' +
                ", year='" + year + '\'' +
                ", value=" + value +
                '}';
    }
}
