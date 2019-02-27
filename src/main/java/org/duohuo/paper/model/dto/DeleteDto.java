package org.duohuo.paper.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@ApiModel("论文和期刊的删除")
public class DeleteDto implements Serializable {

    private static final long serialVersionUID = 3463923532189622476L;

    @ApiModelProperty("年")
    private Integer year;

    @ApiModelProperty("月")
    private Integer month;

    public DeleteDto() {
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
        DeleteDto deleteDto = (DeleteDto) o;
        return Objects.equals(year, deleteDto.year) &&
                Objects.equals(month, deleteDto.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }

    @Override
    public String toString() {
        return "DeleteDto{" +
                "year=" + year +
                ", month=" + month +
                '}';
    }
}
