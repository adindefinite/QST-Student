package student.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import student.util.JDBCUtil;

public class JDBCUtil {
	//数据库驱动类型
		 private static final String Driver="com.mysql.jdbc.Driver";
		 //数据库地址
		 private static final String url="jdbc:mysql://localhost:3306/stu?useUnicode=true&characterEncoding=utf8";
		 //用户
		 private static final String user="root";
		 //密码
		 private static final String pwd="111111";
		 
		 //连接数据库
		 public static Connection getConnection(){
			 Connection conn=null;
			 try {
			 	//虚拟机装载类
			 	Class.forName(Driver);
			 	//连接数据库
			 	conn=DriverManager.getConnection(url, user, pwd);
			 	System.out.println("运行成功！");
			 } catch (ClassNotFoundException e) {
			 	e.printStackTrace();
			 }catch(SQLException e){
			 	e.printStackTrace();
			 }
			 return conn;
		 }
		 
		 //断开数据库
		 public static void close(Connection conn,PreparedStatement psmt,ResultSet rs){
			 try{
				 if(rs!=null){
					 rs.close();
				 }
				 if(psmt!=null){
				 	psmt.close();
				 }
				 if(conn!=null){
				 	conn.close();
				 }
				 }catch(SQLException e){
				 	e.printStackTrace();
				 }
			 }
		 
		 public static void main(String[] args){
			 JDBCUtil jdbc=new JDBCUtil();
			 Connection conn=jdbc.getConnection();
			 System.out.println("运行成功！！");
		 }


	////注册驱动
//		//创建连接
	//	
//		//关闭连接
}
