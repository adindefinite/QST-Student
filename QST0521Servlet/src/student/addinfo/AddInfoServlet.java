package student.addinfo;

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

/**
 * 完善个人信息servlet
 * FileName: AddInfoServlet.java
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月30日 上午11:38:00
 */
@WebServlet("/addinfo")
public class AddInfoServlet extends HttpServlet{
	AddInfoDao ldao=new AddInfoDao();
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stuNo = request.getParameter("studentNo");
		String un=request.getParameter("username");
		String grade=request.getParameter("grade");
		String tel=request.getParameter("telephone");
		String sex=request.getParameter("sex");
		int s=Integer.parseInt(sex);
		System.out.println("check=" + un);
		User uu=new User();
		uu.setStudentNo(stuNo);
		uu.setGrade(grade);
		uu.setUsername(un);
		uu.setTelephone(tel);
		uu.setGender(s);
		boolean i = ldao.updateStudent(uu);
		response.setContentType("text/html; charset=UTF-8");  
		PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
		json.put("msg",  i );
		if(i) {
			System.out.println("操作成功");
		}else {
			System.out.println("132432315676");
		}
		System.out.println(json.toString());
        response.getWriter().write(json.toString());
        out.flush();  
        out.close();  
	}
}
