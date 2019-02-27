package org.duohuo.paper.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BaseLineSearchDto implements Serializable {

    private static final long serialVersionUID = -4520483711454993578L;

    private List<Integer> subjects;

    public BaseLineSearchDto() {
    }

    public List<Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Integer> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseLineSearchDto that = (BaseLineSearchDto) o;
        return Objects.equals(subjects, that.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjects);
    }

    @Override
    public String toString() {
        return "BaseLineSearchDto{" +
                "subjects=" + subjects +
                '}';
    }
}
