package student.register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import student.bean.User;
import student.util.JDBCUtil;

public class RegisterDao {
	// 注册时判断是否已存在用户
	public boolean check(User user) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		boolean flag = false;
		// 定义 ？：占位符
		String sql = "select * from user where studentNo=?";
		// Log.d("UserDao","数据库查询进行登录");
		Connection conn = JDBCUtil.getConnection();
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数
			psmt.setString(1, user.getStudentNo());
			// 执行sql
			rs = psmt.executeQuery();
			// 获取结果集
			flag = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 最后要关闭连接
			JDBCUtil.close(conn, psmt, rs);
		}
		return flag;
	}

	// 注册用户
	public boolean register(User user) {
		PreparedStatement psmt = null;
		int i = -1;
		// 定义 ？：占位符
		Connection conn = JDBCUtil.getConnection();
		String sql = "insert into user(username,password,grade,studentNo,telephone,gender) values(null,?,null,?,null,null)";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数
			psmt.setString(1, user.getPassword());
			psmt.setString(2, user.getStudentNo());
			// 执行sql
			i = psmt.executeUpdate();
			// 获取结果集
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 最后要关闭连接
			JDBCUtil.close(conn, psmt, null);
		}
		return i > 0;
	}

}
