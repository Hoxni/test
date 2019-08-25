package com.example.test.services.utils;

import java.time.LocalDate;

public class WeekStatistics {

    private LocalDate startDate;
    private LocalDate endDate;

    private int averageSpeed;
    private int averageTime;
    private int totalDistance;

    public WeekStatistics() {
    }

    public WeekStatistics(LocalDate startDate, LocalDate endDate, int averageSpeed, int averageTime, int totalDistance) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageSpeed = averageSpeed;
        this.averageTime = averageTime;
        this.totalDistance = totalDistance;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }
}
