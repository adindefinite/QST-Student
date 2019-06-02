package student.changepwd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import student.bean.User;
import student.util.JDBCUtil;

/**
 * 修改个人信息的密码
 * FileName: ChangePwdDao.java
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月30日 下午2:25:28
 */
public class ChangePwdDao {
	// 更新修改数据库中的数据
			public boolean updatepwd(String stuno,String pwd) {
				PreparedStatement psmt = null;
				int i = -1;
				// 定义 ？：占位符
				// 数据库连接
				Connection conn = JDBCUtil.getConnection();
				String sql = "update user set password=? where studentNo = ?";
				try {
					// 编译sql
					psmt = conn.prepareStatement(sql);
					// 设置参数
					psmt.setString(2, stuno);
					psmt.setString(1, pwd);
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
