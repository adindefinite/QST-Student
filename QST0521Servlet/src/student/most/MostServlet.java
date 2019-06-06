package student.most;

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
import student.bean.UserScore;

@WebServlet("/most")
public class MostServlet extends HttpServlet {
	MostDao ldao = new MostDao();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 判断界面发出的是哪个请求信息
		String method = request.getParameter("method_m") == null ? "" : request.getParameter("method_m");
		System.out.println("most=" + method);
		switch (method) {
		case "top"://显示最好的五名
			Top(request, response);
			return;
		case "low":// 显示最差的五名
			Low(request, response);
			return;
		}
	}

	/**
	 * 最好的五名
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void Top(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cr = request.getParameter("course");
		try {
			ArrayList<UserScore> u = ldao.getTopFive(cr);
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
			joReturn.put("total_m", datas.size());
			joReturn.put("rows_m", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_m", false);
			} else {
				joReturn.put("msg_m", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 最差的五名
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void Low(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cr = request.getParameter("course");
		try {
			ArrayList<UserScore> u = ldao.getLowFive(cr);
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
			joReturn.put("total_m", datas.size());
			joReturn.put("rows_m", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_m", false);
			} else {
				joReturn.put("msg_m", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
