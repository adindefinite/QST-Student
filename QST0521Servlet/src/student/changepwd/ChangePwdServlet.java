package student.changepwd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import student.bean.User;

/**
 * 修改密码servlet
 * FileName: ChangePwdServlet.java
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月30日 下午2:55:14
 */
@WebServlet("/change")
public class ChangePwdServlet extends HttpServlet{
	ChangePwdDao ldao=new ChangePwdDao();
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stuNo = request.getParameter("studentNo");
		String pwd=request.getParameter("password");
		System.out.println("change=" + pwd);
		User uu=new User();
		uu.setStudentNo(stuNo);
		uu.setPassword(pwd);
		boolean i = ldao.updatepwd(stuNo, pwd);
		response.setContentType("text/html; charset=UTF-8");  
		PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
		json.put("msg",  i );
		if(i) {
			System.out.println("修改密码成功");
		}else {
			System.out.println("132432315676");
		}
		System.out.println(json.toString());
        response.getWriter().write(json.toString());
        out.flush();  
        out.close();  
	}
}
