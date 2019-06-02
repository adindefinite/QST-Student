package student.avg;

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
import student.bean.ScoreAvg;

@WebServlet("/scoreavg")
public class AvgServlet extends HttpServlet {
	AvgDao ldao = new AvgDao();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cr = request.getParameter("course");
		System.out.println("course="+cr);
		try {
			ArrayList<ScoreAvg> u = ldao.getScoreAVG(cr);
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
			joReturn.put("total_a", datas.size());
			joReturn.put("rows_a", datas);
			if (datas.size() == 0) {
				joReturn.put("msg_a", false);
			} else {
				joReturn.put("msg_a", true);
			}
			System.out.println(joReturn.toString());
			respWritters.append(joReturn.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
