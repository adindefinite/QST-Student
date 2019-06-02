package student.addinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import student.bean.User;
import student.util.JDBCUtil;

/**
 * 完善个人信息dao
 * FileName: AddInfoDao.java
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月30日 上午11:38:22
 */
public class AddInfoDao {
	// 更新修改数据库中的数据
	public boolean updateStudent(User u) {
		PreparedStatement psmt = null;
		int i = -1;
		// 定义 ？：占位符
		// 数据库连接
		Connection conn = JDBCUtil.getConnection();
		String sql = "update user a,score b set a.username = ?,a.grade = ?,a.telephone=?,a.gender=?,b.`Name`=? where a.studentNo = ? and b.studentNo=?";
		try {
			// 编译sql
			psmt = conn.prepareStatement(sql);
			// 设置参数
			psmt.setString(1, u.getUsername());
			psmt.setString(2, u.getGrade());
			psmt.setString(3, u.getTelephone());
			psmt.setInt(4, u.getGender());
			psmt.setString(5, u.getUsername());
			psmt.setString(6, u.getStudentNo());
			psmt.setString(7, u.getStudentNo());
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
