package org.duohuo.paper.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
@ApiModel("分页结果")
public class PageResult implements Serializable {

    private static final long serialVersionUID = 8269064668329241280L;

    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("总元素数量")
    private Long totalElemNums;

    @ApiModelProperty("当前页")
    private Integer nowPage;

    @ApiModelProperty("页面元素数量")
    private Integer elemNums;

    @ApiModelProperty("数据")
    private Object data;

    public PageResult() {
    }

    public PageResult(Integer totalPages, Long totalElemNums, Integer nowPage, Integer elemNums, Object data) {
        this.totalPages = totalPages;
        this.totalElemNums = totalElemNums;
        this.nowPage = nowPage;
        this.elemNums = elemNums;
        this.data = data;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getElemNums() {
        return elemNums;
    }

    public void setElemNums(Integer elemNums) {
        this.elemNums = elemNums;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotalElemNums() {
        return totalElemNums;
    }

    public void setTotalElemNums(Long totalElemNums) {
        this.totalElemNums = totalElemNums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageResult that = (PageResult) o;
        return Objects.equals(totalPages, that.totalPages) &&
                Objects.equals(totalElemNums, that.totalElemNums) &&
                Objects.equals(nowPage, that.nowPage) &&
                Objects.equals(elemNums, that.elemNums) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPages, totalElemNums, nowPage, elemNums, data);
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "totalPages=" + totalPages +
                ", totalElemNums=" + totalElemNums +
                ", nowPage=" + nowPage +
                ", elemNums=" + elemNums +
                ", data=" + data +
                '}';
    }
}
