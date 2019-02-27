package org.duohuo.paper.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
@ApiModel("检索信息")
public class SearchDto implements Serializable {

    private static final long serialVersionUID = 2434799022280995654L;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("关键词")
    private String keyWord;

    @ApiModelProperty("关键词类别")
    private String keyWordType;

    @ApiModelProperty("是否倒序")
    private Boolean ifDesc;

    @ApiModelProperty("检索条件")
    private ConditionDto conditionData;

    public SearchDto() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWordType() {
        return keyWordType;
    }

    public void setKeyWordType(String keyWordType) {
        this.keyWordType = keyWordType;
    }

    public Boolean getIfDesc() {
        return ifDesc;
    }

    public void setIfDesc(Boolean ifDesc) {
        this.ifDesc = ifDesc;
    }

    public ConditionDto getConditionData() {
        return conditionData;
    }

    public void setConditionData(ConditionDto conditionData) {
        this.conditionData = conditionData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchDto searchDto = (SearchDto) o;
        return Objects.equals(page, searchDto.page) &&
                Objects.equals(keyWord, searchDto.keyWord) &&
                Objects.equals(keyWordType, searchDto.keyWordType) &&
                Objects.equals(ifDesc, searchDto.ifDesc) &&
                Objects.equals(conditionData, searchDto.conditionData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, keyWord, keyWordType, ifDesc, conditionData);
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "page=" + page +
                ", keyWord='" + keyWord + '\'' +
                ", keyWordType='" + keyWordType + '\'' +
                ", ifDesc=" + ifDesc +
                ", conditionData=" + conditionData +
                '}';
    }
}
