package student.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import student.bean.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	LoginDao ldao=new LoginDao();
	
	private static Logger logger = Logger.getLogger(LoginServlet.class);

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String studentno = request.getParameter("studentNo");
		String password = request.getParameter("password");
		User user =new User();
		user.setStudentNo(studentno);
		user.setPassword(password);
		boolean i = ldao.login(user);//用于判断账号和密码是否与数据库中查询结果一致
		// 用户名为111,密码为111
//		if (i) {// 判断重复输入密码是否正确
//				// 成功的处理逻辑
//			response.getWriter().print("msg");
//			System.out.println("成功登录");
//			
//		} else {
//			response.getWriter().print("0");
//			System.out.println("奇奇怪怪");
//		}
		response.setContentType("text/html; charset=UTF-8");  
		PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
		//boolean type=false;
		json.put("msg",  i );
		if(i) {
			logger.info("登录成功！");
			System.out.println("成功登录");
		}else {
			logger.info("登录失败！");
			System.out.println("13243");
		}
        //json.put("msg", JSONArray.fromObject(type,jsonConfig)); 
		System.out.println(json.toString());
        response.getWriter().write(json.toString());
        out.flush();  
        out.close();  
	}
}
