package com.example.test.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Jogging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int distance;
    private int time;
    private LocalDateTime dateTime;

    public Jogging() {
    }

    public Jogging(int distance, int time, LocalDateTime dateTime) {
        this.distance = distance;
        this.time = time;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Jogging{" +
                "id=" + id +
                ", distance=" + distance +
                ", time=" + time +
                ", dateTime=" + dateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogging)) return false;
        Jogging jogging = (Jogging) o;
        return getDistance() == jogging.getDistance() &&
                getTime() == jogging.getTime() &&
                Objects.equals(getId(), jogging.getId()) &&
                Objects.equals(getDateTime(), jogging.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDistance(), getTime(), getDateTime());
    }
}
