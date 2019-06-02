package student.init;

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

@WebServlet("/init")
public class InitServlet extends HttpServlet {

	InitDao ldao = new InitDao();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断界面发出的是哪个请求信息
		String method = request.getParameter("method_i") == null ? "" : request.getParameter("method_i");
		System.out.println("init=" + method);
		switch (method) {
		case "student":
			initstudent(request, response);
			return;
		case "score":
			initScore(request, response);
			return;
		}
	}

	// 学生管理界面初始化
	public void initstudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<User> u = ldao.getUserList();
			/* 将list集合装换成json对象 */
			JSONArray datas = JSONArray.fromObject(u);
			// 接下来发送数据
			/* 设置编码，防止出现乱码问题 */
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			System.out.println("initStudent");
			/* 得到输出流 */
			PrintWriter respWritters = response.getWriter();
			/* 将JSON格式的对象toString()后发送 */
			JSONObject joReturn = new JSONObject();
			joReturn.put("total_is", datas.size());
			joReturn.put("rows_is", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_is", false);
			} else {
				joReturn.put("msg_is", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 成绩管理界面初始化
	public void initScore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<Score> u = ldao.getScoreList();
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
			joReturn.put("total_ic", datas.size());
			joReturn.put("rows_ic", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_ic", false);
			} else {
				joReturn.put("msg_ic", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
