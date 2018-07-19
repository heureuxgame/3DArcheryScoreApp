package com.yaleiden.archeryscore;

import java.util.Date;

/**
 * Created by Yale on 3/8/2016.
 */
public class Round {

    String Name;
    String BowType;
    String Division;
    int Total;
    String ShootName;
    Date Date;
    String SortDate;
    String CourseName;
    int Targets;
    String Scoring;
    int[] Scores;
    double Average;
    double Percentage;
    int Possible;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getBowType() {
        return BowType;
    }

    public void setBowType(String bowType) {
        this.BowType = bowType;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        this.Division = division;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        this.Total = total;
    }

    public String getShootName() {
        return ShootName;
    }

    public void setShootName(String shootName) {
        this.ShootName = shootName;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        this.Date = date;
    }

    public String getSortDate() {
        return SortDate;
    }

    public void setSortDate(String sortDate) {
        this.SortDate = sortDate;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }

    public int getTargets() {
        return Targets;
    }

    public void setTargets(int targets) {
        this.Targets = targets;
    }

    public String getScoring() {
        return Scoring;
    }

    public void setScoring(String scoring) {
        this.Scoring = scoring;
    }

    public int[] getScores() {
        return Scores;
    }

    public void setScores(int[] scores) {
        this.Scores = scores;
    }

    public double getAverage() {
        return Average;
    }

    public void setAverage(double average) {
        this.Average = average;
    }

    public double getPercentage() {
        return Percentage;
    }

    public void setPercentage(double percentage) {
        this.Percentage = percentage;
    }

    public int getPossible() {
        return Possible;
    }

    public void setPossible(int possible) {
        this.Possible = possible;
    }


}
