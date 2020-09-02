package com.murali.school.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_EXAM")
public class Exam {
	@Id
	@GeneratedValue
	private int id;
	private String examName;
	private String examPreparedBy;
	private String examEvaluatedBy;
	private int obtainedMarks;
	private int totalMarks;
	private Date examDate;
	private int teacherId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamPreparedBy() {
		return examPreparedBy;
	}
	public void setExamPreparedBy(String examPreparedBy) {
		this.examPreparedBy = examPreparedBy;
	}
	public String getExamEvaluatedBy() {
		return examEvaluatedBy;
	}
	public void setExamEvaluatedBy(String examEvaluatedBy) {
		this.examEvaluatedBy = examEvaluatedBy;
	}
	public int getObtainedMarks() {
		return obtainedMarks;
	}
	public void setObtainedMarks(int obtainedMarks) {
		this.obtainedMarks = obtainedMarks;
	}
	public int getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
	
}
