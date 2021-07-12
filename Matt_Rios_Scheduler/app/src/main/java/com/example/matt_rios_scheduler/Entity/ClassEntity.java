package com.example.matt_rios_scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "class_table")
public class ClassEntity {
    @PrimaryKey
    private int classID;

    private String classTitle;
    private String startDate;
    private String endDate;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String note;
    private int termID;

    @Override
    public String toString() {
        return "ClassEntity {classID = " + classID + ", classTitle = '" + classTitle + "\'" +
                ", startDate = '" + startDate + "\', endDate = '" + endDate + "\', status = '" +
                status + "\', instructorName = '" + instructorName + "\', instructorPhone = '" +
                instructorPhone + "\', instructorEmail = " + instructorEmail + "\', note = '" +
                note + "\', termID = " + termID + "}";
    }

    public ClassEntity(int classID, String classTitle, String startDate, String endDate, String status,
                       String instructorName, String instructorPhone, String instructorEmail,
                       String note, int termID) {
        this.classID = classID;
        this.classTitle = classTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.note = note;
        this.termID = termID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
