package student.check;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import student.bean.Score;
import student.bean.User;
import student.util.JDBCUtil;

public class CheckDao {
	// 通过username模糊查找学生表数据
	public ArrayList<User> getUserByUsername(String us) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<User> userList = new ArrayList<User>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "select * from user where username like ?";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数值
			psmt.setString(1, "%" + us + "%");
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

	// 通过课程名模糊查找数据
	public ArrayList<Score> getScoreByCourse(String us) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<Score> scoreList = new ArrayList<Score>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT *,(SELECT count(b.Score) FROM score AS b WHERE a.Score<b.Score and a.Course=b.Course)+1 AS rank FROM score AS a where a.Course like ? ORDER BY Course desc,rank ";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数值
			psmt.setString(1, "%" + us + "%");
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

	// 通过学生姓名，课程名字模糊查找学生成绩
	public ArrayList<Score> getScoreByCourseName(String s, String r) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<Score> scoreList = new ArrayList<Score>();
		// 获取数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "SELECT *,(SELECT count(b.Score) FROM score AS b WHERE a.Score<b.Score and a.Course=b.Course and a.`Name`=b.`Name`)+1 AS rank FROM score AS a where a.Course like ? and a.`Name` like ? ORDER BY Course desc,rank";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数值
			psmt.setString(1, "%" + s + "%");// 学生姓名
			psmt.setString(2, "%" + r + "%");// 课程名称
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
