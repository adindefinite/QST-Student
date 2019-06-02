package student.personall;

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
import student.bean.Score;
import student.bean.User;

@WebServlet("/personal")
public class PersonalServlet extends HttpServlet {

	PersonalDao ldao = new PersonalDao();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断界面发出的是哪个请求信息
		String method = request.getParameter("method_p") == null ? "" : request.getParameter("method_p");
		System.out.println("personal=" + method);
		switch (method) {
		case "p":// 个人中心显示信息
			Personal_student(request, response);
			return;
		case "personal_score":// 个人中心显示信息
			Personal_score(request, response);
			return;
		}
	}

	// 个人信息显示
	public void Personal_student(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String us = request.getParameter("stuNo");
		System.out.println("personal=" + us);
		try {
			ArrayList<User> u = ldao.getListByName(us);
			/* 将list集合装换成json对象 */
			JSONArray datas = JSONArray.fromObject(u);
			// 接下来发送数据
			/* 设置编码，防止出现乱码问题 */
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			System.out.println("成功转JSON");
			/* 得到输出流 */
			PrintWriter respWritters = response.getWriter();
			/* 将JSON格式的对象toString()后发送 */
			JSONObject joReturn = new JSONObject();
			joReturn.put("total_p", datas.size());
			joReturn.put("rows_p", datas);
			if (datas.size() == 1) {
				joReturn.put("msg_p", true);
			} else {
				joReturn.put("msg_p", false);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 个人成绩信息显示
		public void Personal_score(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String us = request.getParameter("stuNo");
			System.out.println("personalscore=" + us);
			try {
				ArrayList<Score> u = ldao.getScoreListByName(us);
				/* 将list集合装换成json对象 */
				JSONArray datas = JSONArray.fromObject(u);
				// 接下来发送数据
				/* 设置编码，防止出现乱码问题 */
				response.setCharacterEncoding("utf-8");
				request.setCharacterEncoding("utf-8");
				System.out.println("成功转JSON");
				/* 得到输出流 */
				PrintWriter respWritters = response.getWriter();
				/* 将JSON格式的对象toString()后发送 */
				JSONObject joReturn = new JSONObject();
				joReturn.put("total", datas.size());
				joReturn.put("rows", datas);
				if (datas.size() == 0) {
					joReturn.put("msg", false);
				} else {
					joReturn.put("msg", true);
				}
				System.out.println(joReturn.toString());
				respWritters.append(joReturn.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
