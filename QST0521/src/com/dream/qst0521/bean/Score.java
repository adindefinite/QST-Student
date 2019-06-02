package com.dream.qst0521.bean;

import java.io.Serializable;

public class Score implements Serializable{
	private String Name;//学生姓名
	private String Course;//课程名称
	private int Score;//课程分数
	private int Rank;//排名
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCourse() {
		return Course;
	}
	public void setCourse(String course) {
		Course = course;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	public int getRank() {
		return Rank;
	}
	public void setRank(int rank) {
		Rank = rank;
	}
	
}
