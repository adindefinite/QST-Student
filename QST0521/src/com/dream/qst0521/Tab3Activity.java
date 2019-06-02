package com.dream.qst0521;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.qst0521.adapter.StudentAdapter;
import com.dream.qst0521.bean.ScoreAvg;
import com.dream.qst0521.bean.Student;
import com.dream.qst0521.http.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * FileName: Tab3Activity.java
 * 
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月28日 下午2:14:29
 */

public class Tab3Activity extends Activity {

	private Spinner sp1;// 下拉框
	private TableLayout tLayout;
	private TableLayout tLayout2;
	private TableLayout tLayout3;
	private TableLayout tLayout4;
	private List<ScoreAvg> scolist=new ArrayList<ScoreAvg>();
	private String course="";//存储spinner选择的成绩
	private Button check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab3);

		// 下拉框
		sp1 = (Spinner) findViewById(R.id.spinner2);
		List<String> sp1List = new ArrayList<String>();
		sp1List.add("C++");
		sp1List.add("JAVA");
		sp1List.add("数据库");
		sp1List.add("Linux");
		ArrayAdapter sAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sp1List);
		sp1.setAdapter(sAdapter);

		sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String sp1SelectedString = sp1.getItemAtPosition(position).toString();
				Toast.makeText(Tab3Activity.this, "Spinner选中的是：" + sp1SelectedString, Toast.LENGTH_SHORT).show();
				 course = sp1SelectedString;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		
		tablelayout1("C++");//初始化平均分列表
		
		tablelayout2("C++");//初始化最高前五名列表
		
		tablelayout3("C++");//初始化最低五名列表
		
		tablelayout4("C++");//初始化不及格列表
		
		check=(Button)findViewById(R.id.tab3_check);
		check.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				tablelayout1(course);//初始化平均分列表
				
				tablelayout2(course);//初始化最高前五名列表
				
				tablelayout3(course);//初始化最低五名列表
				
				tablelayout4(course);//初始化不及格列表
			}
		});

		
	}
	
	/**
	 * 查找显示课程平均分
	 */
	public void tablelayout1(String cr) {
		tLayout = (TableLayout) findViewById(R.id.tableLayout1);
		View tableLayout_item1 = LayoutInflater.from(this).inflate(R.layout.tablelayout_item, null);
		tLayout.removeAllViews();

		TextView tx11 = (TextView) tableLayout_item1.findViewById(R.id.ttextView1);
		TextView tx21 = (TextView) tableLayout_item1.findViewById(R.id.ttextView2);
		TextView tx31 = (TextView) tableLayout_item1.findViewById(R.id.ttextView3);
		TextView tx41 = (TextView) tableLayout_item1.findViewById(R.id.ttextView4);
		tx11.setText("课程");
		tx21.setText("平均分");
		tx31.setText("最高分");
		tx41.setText("最低分");
		tLayout.addView(tableLayout_item1);
		
		String url = "http://10.0.2.2:8080/QST0521Servlet/scoreavg";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("course", cr);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_a") == true) {
							JSONArray array = response.getJSONArray("rows_a");
							Log.d("array.length()",array.length()+"");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject = (JSONObject) array.get(i);
								View tableLayout_item = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablelayout_item, null);

								TextView tx1 = (TextView) tableLayout_item.findViewById(R.id.ttextView1);
								TextView tx2 = (TextView) tableLayout_item.findViewById(R.id.ttextView2);
								TextView tx3 = (TextView) tableLayout_item.findViewById(R.id.ttextView3);
								TextView tx4 = (TextView) tableLayout_item.findViewById(R.id.ttextView4);
								tx1.setText(jsonObject.getString("course"));
								tx2.setText(jsonObject.getInt("avgScore")+"");
								tx3.setText(jsonObject.getInt("maxScore")+"");
								tx4.setText(jsonObject.getInt("minScore")+"");
								
								tLayout.addView(tableLayout_item);
							}
							Toast.makeText(Tab3Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab3Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 查找最好的五名学生的情况
	 */
	public void tablelayout2(String cr) {
		tLayout2 = (TableLayout) findViewById(R.id.tableLayout2);
		View tableLayout_item1 = LayoutInflater.from(this).inflate(R.layout.tablelayout_item, null);

		tLayout2.removeAllViews();
		TextView tx11 = (TextView) tableLayout_item1.findViewById(R.id.ttextView1);
		TextView tx21 = (TextView) tableLayout_item1.findViewById(R.id.ttextView2);
		TextView tx31 = (TextView) tableLayout_item1.findViewById(R.id.ttextView3);
		TextView tx41 = (TextView) tableLayout_item1.findViewById(R.id.ttextView4);
		tx11.setText("课程");
		tx21.setText("姓名");
		tx31.setText("分数");
		tx41.setText("班级");
		tLayout2.addView(tableLayout_item1);
		
		String url = "http://10.0.2.2:8080/QST0521Servlet/most";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_m", "top");
		params.put("course", cr);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_m") == true) {
							JSONArray array = response.getJSONArray("rows_m");
							Log.d("array.length()",array.length()+"");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject = (JSONObject) array.get(i);
								View tableLayout_item2 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablelayout_item, null);
								TextView tx12 = (TextView) tableLayout_item2.findViewById(R.id.ttextView1);
								TextView tx22 = (TextView) tableLayout_item2.findViewById(R.id.ttextView2);
								TextView tx32 = (TextView) tableLayout_item2.findViewById(R.id.ttextView3);
								TextView tx42 = (TextView) tableLayout_item2.findViewById(R.id.ttextView4);
								tx12.setText(jsonObject.getString("course"));
								tx22.setText(jsonObject.getString("username"));
								tx32.setText(jsonObject.getInt("score")+"");
								tx42.setText(jsonObject.getString("grade"));
								tLayout2.addView(tableLayout_item2);
							}
							Toast.makeText(Tab3Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab3Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	/**
	 * 查找显示课程最低分
	 */
	public void tablelayout3(String cr) {
		tLayout3 = (TableLayout) findViewById(R.id.tableLayout3);
		View tableLayout_item1 = LayoutInflater.from(this).inflate(R.layout.tablelayout_item, null);

		tLayout3.removeAllViews();
		TextView tx11 = (TextView) tableLayout_item1.findViewById(R.id.ttextView1);
		TextView tx21 = (TextView) tableLayout_item1.findViewById(R.id.ttextView2);
		TextView tx31 = (TextView) tableLayout_item1.findViewById(R.id.ttextView3);
		TextView tx41 = (TextView) tableLayout_item1.findViewById(R.id.ttextView4);
		tx11.setText("课程");
		tx21.setText("姓名");
		tx31.setText("分数");
		tx41.setText("班级");
		tLayout3.addView(tableLayout_item1);
		
		String url = "http://10.0.2.2:8080/QST0521Servlet/most";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_m", "low");
		params.put("course", cr);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_m") == true) {
							JSONArray array = response.getJSONArray("rows_m");
							Log.d("array.length()",array.length()+"");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject = (JSONObject) array.get(i);
								View tableLayout_item = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablelayout_item, null);

								TextView tx1 = (TextView) tableLayout_item.findViewById(R.id.ttextView1);
								TextView tx2 = (TextView) tableLayout_item.findViewById(R.id.ttextView2);
								TextView tx3 = (TextView) tableLayout_item.findViewById(R.id.ttextView3);
								TextView tx4 = (TextView) tableLayout_item.findViewById(R.id.ttextView4);
								tx1.setText(jsonObject.getString("course"));
								tx2.setText(jsonObject.getString("username"));
								tx3.setText(jsonObject.getInt("score")+"");
								tx4.setText(jsonObject.getString("grade"));
								
								tLayout3.addView(tableLayout_item);
							}
							Toast.makeText(Tab3Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab3Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 查找显示课程不及格
	 */
	public void tablelayout4(String cr) {
		tLayout4 = (TableLayout) findViewById(R.id.tableLayout4);
		View tableLayout_item1 = LayoutInflater.from(this).inflate(R.layout.tablelayout_item, null);

		tLayout4.removeAllViews();
		TextView tx11 = (TextView) tableLayout_item1.findViewById(R.id.ttextView1);
		TextView tx21 = (TextView) tableLayout_item1.findViewById(R.id.ttextView2);
		TextView tx31 = (TextView) tableLayout_item1.findViewById(R.id.ttextView3);
		TextView tx41 = (TextView) tableLayout_item1.findViewById(R.id.ttextView4);
		tx11.setText("课程");
		tx21.setText("姓名");
		tx31.setText("分数");
		tx41.setText("班级");
		tLayout4.addView(tableLayout_item1);
		
		String url = "http://10.0.2.2:8080/QST0521Servlet/fail";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("course", cr);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg") == true) {
							JSONArray array = response.getJSONArray("rows");
							Log.d("array.length()",array.length()+"");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject = (JSONObject) array.get(i);
								View tableLayout_item = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tablelayout_item, null);

								TextView tx1 = (TextView) tableLayout_item.findViewById(R.id.ttextView1);
								TextView tx2 = (TextView) tableLayout_item.findViewById(R.id.ttextView2);
								TextView tx3 = (TextView) tableLayout_item.findViewById(R.id.ttextView3);
								TextView tx4 = (TextView) tableLayout_item.findViewById(R.id.ttextView4);
								tx1.setText(jsonObject.getString("course"));
								tx2.setText(jsonObject.getString("username"));
								tx3.setText(jsonObject.getInt("score")+"");
								tx4.setText(jsonObject.getString("grade"));
								
								tLayout4.addView(tableLayout_item);
							}
							Toast.makeText(Tab3Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab3Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 查找显示课程最低分最高分平均分(已弃用)
	 * @return
	 */
	public List<ScoreAvg> avg() {
		final List<ScoreAvg> list = new ArrayList<ScoreAvg>();
		/*
		 * 这里192.168.137.1==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * QST0521Servlet==》你在eclipse创建的项目名称 init==》表示在eclipse的web.xml配置servlet的地址
		 */
		String url = "http://10.0.2.2:8080/QST0521Servlet/scoreavg";
		RequestParams params = new RequestParams(); // 绑定参数
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_a") == true) {
//							String array = response.getString("rows_is");
//							// 将JSON的String 转成一个JsonArray对象
//							JsonParser parser = new JsonParser();
//							JsonArray jsonArray = parser.parse(array).getAsJsonArray();
//							Gson gson = new Gson();
//							for (JsonElement jsonarray : jsonArray) {
//								// 使用GSON，直接转成Student对象
//								Student score = gson.fromJson(jsonarray, Student.class);
//								list.add(score);
//							}
							JSONArray array = response.getJSONArray("rows_a");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject = (JSONObject) array.get(i);
								ScoreAvg u = new ScoreAvg();
								u.setCourse(jsonObject.getString("Course"));
								u.setAvgScore(jsonObject.getInt("AVG(Score)"));
								u.setMaxScore(jsonObject.getInt("Max(Score)"));
								u.setMinScore(jsonObject.getInt("Min(Score)"));
								list.add(u);
							}
							Toast.makeText(Tab3Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab3Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return list;
	}
}
