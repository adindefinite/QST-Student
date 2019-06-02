package student.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import student.bean.UserScore;
import student.util.JDBCUtil;

/**
 * 
 * FileName: FailDao.java
 * @Description: 课程不及格的同学
 * @author 25478
 *
 * @data: 2019年5月29日 下午2:51:24
 */
public class FailDao {
	//课程不及格的学生
		public ArrayList<UserScore> getFailUser(String un) {
			PreparedStatement psmt = null;
			ResultSet rs = null;
			ArrayList<UserScore> userscoreList = new ArrayList<UserScore>();
			// 获取数据库连接
			Connection conn = JDBCUtil.getConnection();
			String sql = "select s.Course,u.username,s.Score,u.grade from score s,`user` u where s.Score<60 and s.Course=? and s.`Name`=u.username order by Score desc";
			try {
				// 编译sql
				psmt = conn.prepareStatement(sql);
				// 设置参数值
				psmt.setString(1, un);
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
