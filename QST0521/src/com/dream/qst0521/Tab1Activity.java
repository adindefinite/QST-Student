package com.dream.qst0521;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.qst0521.adapter.StudentAdapter;
import com.dream.qst0521.bean.Student;
import com.dream.qst0521.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab1Activity extends Activity {
	private ListView lv;
	private List<Student> lsMy = new ArrayList<Student>();
	private StudentAdapter lsvAdapter;
	private Button check;
	private Button showall;
	private EditText edittext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab1);
		Log.d("Tab1Activity", "Tab1Activity");

		lv = (ListView) findViewById(R.id.listView1);
		edittext = (EditText) findViewById(R.id.check_name);

		LayoutInflater.from(this).inflate(R.layout.student_item, null);

//		Student s=new Student();
//		s.setUsername("134");
//		lsMy.add(s);

		//lsMy = show();// 对listview初始化
		
		Log.d("Tab1Activity", "完成listview初始化");

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				//Toast.makeText(view.getContext(), lsMy.get(i).getUsername(), Toast.LENGTH_SHORT).show();
			}
		});

		check = (Button) findViewById(R.id.check);
		check.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String un = edittext.getText().toString().trim();
				if (un.equals("") || un.equals(null)) {
					Toast.makeText(view.getContext(), "请输入要查询的学生姓名", Toast.LENGTH_SHORT).show();
				} else {
					//listu = check(un);
					final List<Student> listu = new ArrayList<Student>();
					String url = "http://10.0.2.2:8080/QST0521Servlet/check";
					RequestParams params = new RequestParams(); // 绑定参数
					params.put("method_c", "student");
					params.put("username", un);
					HttpUtil.get(url, params, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							if (statusCode == 200) {
								try {
									if (response.getBoolean("msg_c") == true) {
										JSONArray array = response.getJSONArray("rows_c");
										for (int i = 0; i < array.length(); i++) {
											JSONObject jsonObject = (JSONObject) array.get(i);
											Student u = new Student();
											u.setUsername(jsonObject.getString("username"));
											u.setStudentNo(jsonObject.getString("studentNo"));
											u.setGrade(jsonObject.getString("grade"));
											u.setTelephone(jsonObject.getString("telephone"));
											listu.add(u);
										}
										lsvAdapter = new StudentAdapter(getApplicationContext(), listu);
										lv.setAdapter(lsvAdapter);// 通过setAdapter（）方法将Listview与自定义Adapter绑定
										lsvAdapter.notifyDataSetChanged();// 更新数据序列中的数据
										Toast.makeText(Tab1Activity.this, "查找成功", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(Tab1Activity.this, "查找失败！", Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					});
					
					Toast.makeText(view.getContext(), un, Toast.LENGTH_SHORT).show();
				}
			}
		});

		/**
		 * 显示全部学生信息
		 */
		showall = (Button) findViewById(R.id.show_student_all);
		showall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(Tab1Activity.this, "显示数据库中所有学生数据", Toast.LENGTH_SHORT).show();
				edittext.setText("");// 清空查询条件
				all();
			}
		});
	}
	
	/**
	 * 回调方法，当个人中心界面修改个人姓名
	 */
	@Override
	public void onResume() {
		super.onResume(); // 首先调用父类的方法
		lsMy.clear();//清空数据进行刷新
		String url = "http://10.0.2.2:8080/QST0521Servlet/init";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_i", "student");
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_is") == true) {
							JSONArray array = response.getJSONArray("rows_is");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject = (JSONObject) array.get(i);
								Student u = new Student();
								u.setUsername(jsonObject.getString("username"));
								u.setStudentNo(jsonObject.getString("studentNo"));
								u.setGrade(jsonObject.getString("grade"));
								u.setTelephone(jsonObject.getString("telephone"));
								lsMy.add(u);
								all();
							}
							Toast.makeText(Tab1Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab1Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
			/**
			 * 连接服务器失败的处理方法
			 */
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Log.d("Tab1---servlet", "---->>onFailure" + throwable.toString());
			}
		});
	}

	/**
	 * 显示所有数据
	 */
	public void all() {
		lsvAdapter = new StudentAdapter(this, lsMy);
		lv.setAdapter(lsvAdapter);// 通过setAdapter（）方法将Listview与自定义Adapter绑定
		lsvAdapter.notifyDataSetChanged();// 更新数据序列中的数据
	}

	/**
	 * 从服务器端获取数据(已弃用)
	 * 
	 * @return 获取的学生信息数据
	 */
	public List<Student> show() {
		final List<Student> list = new ArrayList<Student>();
		/*
		 * 这里192.168.137.1==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * QST0521Servlet==》你在eclipse创建的项目名称 init==》表示在eclipse的web.xml配置servlet的地址
		 */
		String url = "http://10.0.2.2:8080/QST0521Servlet/init";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_i", "student");
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_is") == true) {
//							String array = response.getString("rows_c");
//							// 将JSON的String 转成一个JsonArray对象
//							JsonParser parser = new JsonParser();
//							JsonArray jsonArray = parser.parse(array).getAsJsonArray();
//							Gson gson = new Gson();
//							for (JsonElement jsonarray : jsonArray) {
//								// 使用GSON，直接转成Student对象
//								Student score = gson.fromJson(jsonarray, Student.class);
//								list.add(score);
//							}
							JSONArray array = response.getJSONArray("rows_is");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject = (JSONObject) array.get(i);
								Student u = new Student();
								u.setUsername(jsonObject.getString("username"));
								u.setStudentNo(jsonObject.getString("studentNo"));
								u.setGrade(jsonObject.getString("grade"));
								u.setTelephone(jsonObject.getString("telephone"));
								list.add(u);
							}
							Toast.makeText(Tab1Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab1Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return list;
	}

	public List<Student> check(String un) {
		final List<Student> list = new ArrayList<Student>();
		/*
		 * 这里192.168.137.1==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * QST0521Servlet==》你在eclipse创建的项目名称 init==》表示在eclipse的web.xml配置servlet的地址
		 */
		String url = "http://10.0.2.2:8080/QST0521Servlet/check";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_c", "student");
		params.put("username", un);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_c") == true) {
//							String array = response.getString("rows_c");
//							// 将JSON的String 转成一个JsonArray对象
//							JsonParser parser = new JsonParser();
//							JsonArray jsonArray = parser.parse(array).getAsJsonArray();
//							Gson gson = new Gson();
//							for (JsonElement jsonarray : jsonArray) {
//								// 使用GSON，直接转成Student对象
//								Student score = gson.fromJson(jsonarray, Student.class);
//								list.add(score);
//							}
							JSONArray array = response.getJSONArray("rows_c");
							 for (int i = 0; i < array.length(); i++) {
							 JSONObject jsonObject = (JSONObject) array.get(i);
							 Student u = new Student();
								u.setUsername(jsonObject.getString("username"));
								u.setStudentNo(jsonObject.getString("studentNo"));
								u.setGrade(jsonObject.getString("grade"));
								u.setTelephone(jsonObject.getString("telephone"));
							 list.add(u);
							 }
							Toast.makeText(Tab1Activity.this, "查找成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab1Activity.this, "查找失败！", Toast.LENGTH_SHORT).show();
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
