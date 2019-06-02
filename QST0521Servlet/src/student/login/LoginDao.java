package student.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import student.bean.User;
import student.util.JDBCUtil;

public class LoginDao {
	// 登陆
		public boolean login(User user) {
			PreparedStatement psmt = null;
			ResultSet rs = null;
			boolean flag = false;
			// 定义 ？：占位符
			String sql = "select * from user where studentNo=? And password=?";
			// Log.d("UserDao","数据库查询进行登录");
			Connection conn = JDBCUtil.getConnection();
			try {
				// 编译sql
				psmt = conn.prepareStatement(sql);
				// 设置参数
				psmt.setString(1, user.getStudentNo());
				psmt.setString(2, user.getPassword());
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
}
