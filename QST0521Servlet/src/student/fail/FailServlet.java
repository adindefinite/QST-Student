package student.fail;

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

/**
 * 
 * FileName: FailServlet.java
 * @Description: 课程不及格的同学 servlet
 * @author 25478
 *
 * @data: 2019年5月29日 下午2:51:41
 */
@WebServlet("/fail")
public class FailServlet  extends HttpServlet{
	FailDao ldao=new FailDao();
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cr = request.getParameter("course");
		try {
			ArrayList<UserScore> u = ldao.getFailUser(cr);
			/* 将list集合装换成json对象 */
			JSONArray datas = JSONArray.fromObject(u);
			// 接下来发送数据
			/* 设置编码，防止出现乱码问题 */
			response.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			System.out.println("FailServlet");
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
