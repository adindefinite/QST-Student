package student.init;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import student.bean.Score;
import student.bean.User;
import student.util.JDBCUtil;

public class InitDao {
	// 获取数据库中所有学生信息
	public ArrayList<User> getUserList() {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<User> userList = new ArrayList<User>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "select username,studentNo,telephone,grade from user";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 执行sql
			rs = psmt.executeQuery();// executeUpdate()
			// 获取结果集
			while (rs.next()) {
				User user = new User();
				user.setGrade(rs.getString("grade"));// 班级
				user.setUsername(rs.getString("username"));// 姓名
				user.setStudentNo(rs.getString("studentNo"));// 学号
				user.setTelephone(rs.getString("telephone"));// 联系电话
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 最后要关闭连接
			JDBCUtil.close(conn, psmt, null);
		}
		return userList;
	}

	// 获取数据库中所有成绩信息
	public ArrayList<Score> getScoreList() {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<Score> scoreList = new ArrayList<Score>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT *,\r\n" + 
				"(SELECT count(b.Score) FROM score AS b WHERE a.Score<b.Score and a.Course=b.Course)+1 AS rank #获取排名-并列\r\n" + 
				"FROM score AS a ORDER BY Course desc,rank ; #获取学生成绩排名";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 执行sql
			rs = psmt.executeQuery();// executeUpdate()
			// 获取结果集
			while (rs.next()) {
				Score sc = new Score();
				sc.setName(rs.getString("Name"));// 学生姓名
				sc.setCourse(rs.getString("Course"));// 课程名称
				sc.setScore(rs.getInt("Score"));// 课程分数
				sc.setRank(rs.getInt("rank"));//排名
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
