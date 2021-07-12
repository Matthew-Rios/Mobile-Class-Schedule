package com.example.matt_rios_scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {
    @PrimaryKey
    private int assessmentID;

    private String assessmentTitle;
    private String endDate;
    private int classID;
    private String assessmentType;

    @Override
    public String toString() {
        return "AssessmentEntity {assessmentID = " + assessmentID + ", assessmentTitle = '" +
                assessmentTitle + "\', endDate = '" + endDate + ", assessmentType = '" +
                assessmentType + "\', classID = " + classID + "}";
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public AssessmentEntity(int assessmentID, String assessmentTitle, String endDate,
                            String assessmentType, int classID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.endDate = endDate;
        this.assessmentType = assessmentType;
        this.classID = classID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }
}
