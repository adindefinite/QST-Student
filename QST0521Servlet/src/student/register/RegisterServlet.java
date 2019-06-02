package student.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import student.bean.User;
import student.bean.UserScore;

/**
 * 
 * FileName: RegisterServlet.java
 * 
 * @Description: 注册
 * @author 25478
 *
 * @data: 2019年5月29日 下午5:41:18
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	RegisterDao ldao = new RegisterDao();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断界面发出的是哪个请求信息
		String method = request.getParameter("method_r") == null ? "" : request.getParameter("method_r");
		System.out.println("most=" + method);
		switch (method) {
		case "check":
			check(request, response);
			return;
		case "register":
			register(request, response);
			return;
		}
	}

	/**
	 * 判断是否已有用户存在
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stuno = request.getParameter("studentNo");
		User s=new User();
		s.setStudentNo(stuno);
		boolean i=ldao.check(s);
		response.setContentType("text/html; charset=UTF-8");  
		PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
		//boolean type=false;
		json.put("msg_r",  i );
		if(i) {
			System.out.println("查找到用户");
		}else {
			System.out.println("13243");
		}
		System.out.println(json.toString());
        response.getWriter().write(json.toString());
        out.flush();  
        out.close();  
	}

	/**
	 * 注册新用户
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pwd = request.getParameter("password");
		String stuno = request.getParameter("studentNo");
		User u=new User();
		u.setPassword(pwd);
		u.setStudentNo(stuno);
		
		boolean i=ldao.register(u);
		response.setContentType("text/html; charset=UTF-8");  
		PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
		json.put("msg_r",  i );
		if(i) {
			System.out.println("成功注册");
		}else {
			System.out.println("13243");
		}
		System.out.println(json.toString());
        response.getWriter().write(json.toString());
        out.flush();  
        out.close();
	}
}
