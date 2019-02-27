package org.duohuo.paper.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author lwolvej
 */
public class SearchDto2 implements Serializable {

    private static final long serialVersionUID = 1295507355294454346L;
    private Integer pageNum;

    private Boolean ifDesc;

    private String keyWord;

    private List<Integer> subject;

    public SearchDto2() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Boolean getIfDesc() {
        return ifDesc;
    }

    public void setIfDesc(Boolean ifDesc) {
        this.ifDesc = ifDesc;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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
        SearchDto2 that = (SearchDto2) o;
        return Objects.equals(pageNum, that.pageNum) &&
                Objects.equals(ifDesc, that.ifDesc) &&
                Objects.equals(keyWord, that.keyWord) &&
                Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNum, ifDesc, keyWord, subject);
    }

    @Override
    public String toString() {
        return "SearchDto2{" +
                "pageNum=" + pageNum +
                ", ifDesc=" + ifDesc +
                ", keyWord='" + keyWord + '\'' +
                ", subject=" + subject +
                '}';
    }
}
