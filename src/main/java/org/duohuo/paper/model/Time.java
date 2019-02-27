package org.duohuo.paper.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
@Entity
@Table(name = "time_period")
public class Time implements Serializable {

    private static final long serialVersionUID = -6421172898434772890L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_id")
    private Integer timeId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    public Time() {
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
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
        Time time = (Time) o;
        return Objects.equals(timeId, time.timeId) &&
                Objects.equals(year, time.year) &&
                Objects.equals(month, time.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeId, year, month);
    }

    @Override
    public String toString() {
        return "Time{" +
                "timeId=" + timeId +
                ", year=" + year +
                ", month=" + month +
                '}';
    }
}
