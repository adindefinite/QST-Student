package student.most;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import student.bean.UserScore;
import student.util.JDBCUtil;

public class MostDao {
	// 课程分数最低五名
	public ArrayList<UserScore> getLowFive(String un) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<UserScore> userscoreList = new ArrayList<UserScore>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "select s.Course,u.username,s.Score,u.grade from `user` u INNER JOIN score s where u.username=s.`Name` and s.Course=? and s.Score in(select n.Score from (select * from score where Course=? GROUP BY Score order by Score asc limit 5)as n) order by s.Score desc";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数值
			psmt.setString(1, un);
			psmt.setString(2, un);
			// 执行sql
			rs = psmt.executeQuery();// executeUpdate()
			// 获取结果集
			while (rs.next()) {
				UserScore sc = new UserScore();
				sc.setCourse(rs.getString("Course"));// 课程名称
				sc.setUsername(rs.getString("username"));// 学生姓名
				sc.setScore(rs.getInt("Score"));// 课程分数
				sc.setGrade(rs.getString("grade"));// 班级
				userscoreList.add(sc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 最后要关闭连接
			JDBCUtil.close(conn, psmt, null);
		}
		return userscoreList;
	}

	// 课程分数最高五名
	public ArrayList<UserScore> getTopFive(String un) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<UserScore> userscoreList = new ArrayList<UserScore>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "select s.Course,u.username,s.Score,u.grade from `user` u INNER JOIN score s where u.username=s.`Name` and s.Course=? and s.Score in(select n.Score from (select * from score where Course=? GROUP BY Score order by Score desc limit 5)as n) order by s.Score desc";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数值
			psmt.setString(1, un);
			psmt.setString(2, un);
			// 执行sql
			rs = psmt.executeQuery();// executeUpdate()
			// 获取结果集
			while (rs.next()) {
				UserScore sc = new UserScore();
				sc.setCourse(rs.getString("Course"));// 课程名称
				sc.setUsername(rs.getString("username"));// 学生姓名
				sc.setScore(rs.getInt("Score"));// 课程分数
				sc.setGrade(rs.getString("grade"));// 班级
				userscoreList.add(sc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 最后要关闭连接
			JDBCUtil.close(conn, psmt, null);
		}
		return userscoreList;
	}
}
