package student.check;

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

/**
 * 查找学生信息、学生成绩信息servlet
 * FileName: CheckServlet.java
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月30日 上午11:38:40
 */
@WebServlet("/check")
public class CheckServlet extends HttpServlet {
	CheckDao ldao = new CheckDao();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断界面发出的是哪个请求信息
		String method = request.getParameter("method_c") == null ? "" : request.getParameter("method_c");
		System.out.println("check=" + method);
		switch (method) {
		case "student":
			checkstudent(request, response);
			return;
		case "scorename":
			checknamescore(request, response);//查找成绩信息（课程名，学生姓名）
			return;
		case "score":
			checkscore(request, response);//查找成绩信息（课程名）
			return;
		}
	}

	// 按名称模糊查找学生信息
	public void checkstudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String us = request.getParameter("username");
		System.out.println("check=" + us);
		try {
			ArrayList<User> u = ldao.getUserByUsername(us);
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
			joReturn.put("total_c", datas.size());
			joReturn.put("rows_c", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_c", false);
			} else {
				joReturn.put("msg_c", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 模糊查找学生成绩_按课程名字
	public void checkscore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cr = request.getParameter("course");
		System.out.println("check=" + cr);
		try {
			ArrayList<Score> u = ldao.getScoreByCourse(cr);
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
			joReturn.put("total_cs", datas.size());
			joReturn.put("rows_cs", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_cs", false);
			} else {
				joReturn.put("msg_cs", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 模糊查找学生成绩
	public void checknamescore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String us = request.getParameter("username");
		String cr = request.getParameter("course");
		System.out.println("check=" + us + "," + cr);
		try {
			ArrayList<Score> u = ldao.getScoreByCourseName(us, cr);
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
			joReturn.put("total_ca", datas.size());
			joReturn.put("rows_ca", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_ca", false);
			} else {
				joReturn.put("msg_ca", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
