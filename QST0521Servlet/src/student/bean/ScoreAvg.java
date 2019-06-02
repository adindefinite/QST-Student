package student.bean;

public class ScoreAvg {
	private String Course;
	private int AvgScore;
	private int MaxScore;
	private int MinScore;

	public String getCourse() {
		return Course;
	}

	public void setCourse(String course) {
		Course = course;
	}

	public int getAvgScore() {
		return AvgScore;
	}

	public void setAvgScore(int avgScore) {
		AvgScore = avgScore;
	}

	public int getMaxScore() {
		return MaxScore;
	}

	public void setMaxScore(int maxScore) {
		MaxScore = maxScore;
	}

	public int getMinScore() {
		return MinScore;
	}

	public void setMinScore(int minScore) {
		MinScore = minScore;
	}

}
