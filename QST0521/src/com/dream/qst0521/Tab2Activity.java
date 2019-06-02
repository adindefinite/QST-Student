package com.dream.qst0521;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.qst0521.adapter.ScoreAdapter;
import com.dream.qst0521.adapter.StudentAdapter;
import com.dream.qst0521.bean.Score;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * FileName: Tab2Activity.java
 * 
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月28日 下午1:07:29
 */
public class Tab2Activity extends Activity {
	private ListView listv;
	private List<Score> ls = new ArrayList<Score>();
	private ScoreAdapter scAdapter;
	private Spinner sp1;// 下拉框
	private Button checkscore;
	private Button showall;
	private EditText edittext;
	private String course;// 作为下拉框选中项存储

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab2);
		Log.d("Tab2Activity", "Tab2Activity");
		
		listv = (ListView) findViewById(R.id.listView2);
		edittext = (EditText) findViewById(R.id.c_Name);
		
		LayoutInflater.from(this).inflate(R.layout.score_item, null);

//		Score sc=new Score();
//		sc.setName("缪海");
//		sc.setCourse("C++");
//		sc.setScore(88);
//		ls.add(sc);
//		
//		Score ssc=new Score();
//		ssc.setName("缪海林");
//		ssc.setCourse("JAVA");
//		ssc.setScore(77);
//		ls.add(ssc);

		Log.d("Tab2Activity", "完成成绩管理初始化");

		
		
//		scAdapter = new ScoreAdapter(this, ls);
//		listv.setAdapter(scAdapter);// 通过setAdapter（）方法将Listview与自定义Adapter绑定
//		scAdapter.notifyDataSetChanged();// 更新数据序列中的数据

		// listview点击事件
		listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(view.getContext(), ls.get(i).getName(), Toast.LENGTH_SHORT).show();
			}
		});

		// 下拉框
		sp1 = (Spinner) findViewById(R.id.spinner1);
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
				Toast.makeText(Tab2Activity.this, "Spinner选中的是：" + sp1SelectedString, Toast.LENGTH_SHORT).show();
				course = sp1SelectedString;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		checkscore = (Button) findViewById(R.id.check_score);
		checkscore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String un = edittext.getText().toString();
				final List<Score> listu = new ArrayList<Score>();
				if (un.equals("") || un.equals(null)) {
					//按课程名称查找
					//listu = check(course);
					
					String url = "http://10.0.2.2:8080/QST0521Servlet/check";
					RequestParams params = new RequestParams(); // 绑定参数
					params.put("method_c", "score");
					params.put("course", course);
					HttpUtil.get(url, params, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							if (statusCode == 200) {
								try {
									if (response.getBoolean("msg_cs") == true) {
										JSONArray array2 = response.getJSONArray("rows_cs");
										//Toast.makeText(Tab2Activity.this, array2.toString(), Toast.LENGTH_SHORT).show();
										for (int j = 0; j < array2.length(); j++) {
											JSONObject jsonObject2 = (JSONObject) array2.get(j);
											Score uu = new Score();
											uu.setName(jsonObject2.getString("name"));
											uu.setCourse(jsonObject2.getString("course"));
											uu.setScore(jsonObject2.getInt("score"));
											uu.setRank(jsonObject2.getInt("rank"));
											listu.add(uu);
										}
										all(listu);
										Toast.makeText(Tab2Activity.this, "按课程名称:"+course+"查找成功", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(Tab2Activity.this, "查找失败！", Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					});
					Toast.makeText(view.getContext(), "按课程名称进行查找", Toast.LENGTH_SHORT).show();
				} else {
					//按课程+学生名查找
					//listu = checkall(un, course);
					String url = "http://10.0.2.2:8080/QST0521Servlet/check";
					RequestParams params = new RequestParams(); // 绑定参数
					params.put("method_c", "scorename");
					params.put("username", un);
					params.put("course", course);
					HttpUtil.get(url, params, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							if (statusCode == 200) {
								try {
									if (response.getBoolean("msg_ca") == true) {
										JSONArray array2 = response.getJSONArray("rows_ca");
										Toast.makeText(Tab2Activity.this, array2.toString(), Toast.LENGTH_SHORT).show();
										for (int j = 0; j < array2.length(); j++) {
											JSONObject jsonObject2 = (JSONObject) array2.get(j);
											Score uu = new Score();
											uu.setName(jsonObject2.getString("name"));
											uu.setCourse(jsonObject2.getString("course"));
											uu.setScore(jsonObject2.getInt("score"));
											uu.setRank(jsonObject2.getInt("rank"));
											listu.add(uu);
										}
										all(listu);
										Toast.makeText(Tab2Activity.this, "按课程名称："+course+"学生姓名："+un+"查找成功", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(Tab2Activity.this, "查找失败！", Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					});
					Toast.makeText(view.getContext(), "按课程+学生名："+un+"进行查找", Toast.LENGTH_SHORT).show();
				}
				
//				scAdapter = new ScoreAdapter(getApplicationContext(), listu);
//				listv.setAdapter(scAdapter);// 通过setAdapter（）方法将Listview与自定义Adapter绑定
//				scAdapter.notifyDataSetChanged();// 更新数据序列中的数据
			}
		});

		showall = (Button) findViewById(R.id.show_score_all);
		showall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(Tab2Activity.this, "显示数据库中所有成绩数据", Toast.LENGTH_SHORT).show();
				edittext.setText("");// 清空查询条件
				all(ls);
			}
		});
	}
	
	/**
	 * 回调方法，当个人中心界面修改个人姓名
	 */
	@Override
	public void onResume() {
		super.onResume(); // 首先调用父类的方法
		ls.clear();//清空数据进行刷新
		// listview初始化
				String url = "http://10.0.2.2:8080/QST0521Servlet/init";
				RequestParams params = new RequestParams(); // 绑定参数
				params.put("method_i", "score");
				HttpUtil.get(url, params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						if (statusCode == 200) {
							try {
								if (response.getBoolean("msg_ic") == true) {
									JSONArray array2 = response.getJSONArray("rows_ic");
									//Toast.makeText(Tab2Activity.this, array2.toString(), Toast.LENGTH_SHORT).show();
									for (int j = 0; j < array2.length(); j++) {
										JSONObject jsonObject2 = (JSONObject) array2.get(j);
										Score uu = new Score();
										uu.setName(jsonObject2.getString("name"));
										uu.setCourse(jsonObject2.getString("course"));
										uu.setScore(jsonObject2.getInt("score"));
										uu.setRank(jsonObject2.getInt("rank"));
										ls.add(uu);
									}
									all(ls);
									Toast.makeText(Tab2Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(Tab2Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}

	/**
	 * 显示所有数据
	 */
	public void all(List<Score> list) {
		scAdapter = new ScoreAdapter(this, list);
		listv.setAdapter(scAdapter);// 通过setAdapter（）方法将Listview与自定义Adapter绑定
		scAdapter.notifyDataSetChanged();// 更新数据序列中的数据
	}

	/**
	 * 初始化显示学生成绩（已弃用）
	 * @return
	 */
	public List<Score> show() {
		final List<Score> list = new ArrayList<Score>();
		/*
		 * 这里192.168.137.1==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * QST0521Servlet==》你在eclipse创建的项目名称 init==》表示在eclipse的web.xml配置servlet的地址
		 */
		String url = "http://10.0.2.2:8080/QST0521Servlet/init";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_i", "score");
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_ic") == true) {
							JSONArray array2 = response.getJSONArray("rows_ic");
							//Toast.makeText(Tab2Activity.this, array2.toString(), Toast.LENGTH_SHORT).show();
							for (int j = 0; j < array2.length(); j++) {
								JSONObject jsonObject2 = (JSONObject) array2.get(j);
								Score uu = new Score();
								uu.setName(jsonObject2.getString("name"));
								uu.setCourse(jsonObject2.getString("course"));
								uu.setScore(jsonObject2.getInt("score"));
								list.add(uu);
							}
							Toast.makeText(Tab2Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab2Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return list;
	}
	
	/**
	 * 查找学生成绩信息（课程名）（已弃用）
	 * @param cr
	 * @return
	 */
	public List<Score> check(String cr) {
		final List<Score> list = new ArrayList<Score>();
		/*
		 * 这里192.168.38.45==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * QST0521Servlet==》你在eclipse创建的项目名称 
		 * init==》表示在eclipse的web.xml配置servlet的地址
		 */
		String url = "http://10.0.2.2:8080/QST0521Servlet/check";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_c", "score");
		params.put("course", cr);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_cs") == true) {
							JSONArray array2 = response.getJSONArray("rows_cs");
							Toast.makeText(Tab2Activity.this, array2.toString(), Toast.LENGTH_SHORT).show();
							for (int j = 0; j < array2.length(); j++) {
								JSONObject jsonObject2 = (JSONObject) array2.get(j);
								Score uu = new Score();
								uu.setName(jsonObject2.getString("name"));
								uu.setCourse(jsonObject2.getString("course"));
								uu.setScore(jsonObject2.getInt("score"));
								list.add(uu);
							}
							Toast.makeText(Tab2Activity.this, "查找成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab2Activity.this, "查找失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return list;
	}

	/**
	 * 查找学生成绩(课程，名字)（已弃用）
	 * @param un
	 * @param cr
	 * @return
	 */
	public List<Score> checkall(String un, String cr) {
		final List<Score> list = new ArrayList<Score>();
		/*
		 * 这里192.168.38.45==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * QST0521Servlet==》你在eclipse创建的项目名称 
		 * init==》表示在eclipse的web.xml配置servlet的地址
		 */
		String url = "http://10.0.2.2:8080/QST0521Servlet/check";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_c", "scorename");
		params.put("username", un);
		params.put("course", cr);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_ca") == true) {
							JSONArray array2 = response.getJSONArray("rows_ca");
							Toast.makeText(Tab2Activity.this, array2.toString(), Toast.LENGTH_SHORT).show();
							for (int j = 0; j < array2.length(); j++) {
								JSONObject jsonObject2 = (JSONObject) array2.get(j);
								Score uu = new Score();
								uu.setName(jsonObject2.getString("name"));
								uu.setCourse(jsonObject2.getString("course"));
								uu.setScore(jsonObject2.getInt("score"));
								list.add(uu);
							}
							Toast.makeText(Tab2Activity.this, "查找成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab2Activity.this, "查找失败！", Toast.LENGTH_SHORT).show();
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
