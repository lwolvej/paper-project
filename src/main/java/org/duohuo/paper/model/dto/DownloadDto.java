package org.duohuo.paper.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author lwolvej
 */
public class DownloadDto implements Serializable {

    private static final long serialVersionUID = 2840729037068017243L;

    private List<Long> data;

    public DownloadDto() {
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadDto that = (DownloadDto) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "DownloadDto{" +
                "data=" + data +
                '}';
    }
}
