package student.avg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import student.bean.ScoreAvg;
import student.bean.UserScore;
import student.util.JDBCUtil;

public class AvgDao {
	// 查询每门课程平均分,最高分，最低分
	public ArrayList<ScoreAvg> getScoreAVG(String un) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<ScoreAvg> scoreList = new ArrayList<ScoreAvg>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "select Course,AVG(Score),Max(Score),Min(score) from score where Course=? GROUP BY Course;";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数值
			psmt.setString(1, un);
			// 执行sql
			rs = psmt.executeQuery();// executeUpdate()
			// 获取结果集
			while (rs.next()) {
				ScoreAvg sc = new ScoreAvg();
				sc.setCourse(rs.getString("Course"));// 课程名称
				sc.setAvgScore(rs.getInt("AVG(Score)"));// 平均分
				sc.setMaxScore(rs.getInt("Max(Score)"));// 最高分
				sc.setMinScore(rs.getInt("Min(Score)"));// 最低分
				scoreList.add(sc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 最后要关闭连接
			JDBCUtil.close(conn, psmt, null);
		}
		return scoreList;
	}

	
}
