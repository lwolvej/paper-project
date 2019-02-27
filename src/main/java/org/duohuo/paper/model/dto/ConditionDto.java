package org.duohuo.paper.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author lwolvej
 */
@ApiModel("检索条件")
public class ConditionDto implements Serializable {

    private static final long serialVersionUID = -4368653299936480032L;

    @ApiModelProperty("检索的月")
    private List<Integer> month;

    @ApiModelProperty("检索的年")
    private List<Integer> year;

    @ApiModelProperty("检索的学科")
    private List<Integer> subject;

    public ConditionDto() {
        super();
    }

    public List<Integer> getMonth() {
        return month;
    }

    public void setMonth(List<Integer> month) {
        this.month = month;
    }

    public List<Integer> getYear() {
        return year;
    }

    public void setYear(List<Integer> year) {
        this.year = year;
    }

    public List<Integer> getSubject() {
        return subject;
    }

    public void setSubject(List<Integer> subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionDto that = (ConditionDto) o;
        return Objects.equals(month, that.month) &&
                Objects.equals(year, that.year) &&
                Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year, subject);
    }

    @Override
    public String toString() {
        return "ConditionDto{" +
                "month=" + month +
                ", year=" + year +
                ", subject=" + subject +
                '}';
    }
}
