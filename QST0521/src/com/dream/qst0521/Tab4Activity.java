package com.dream.qst0521;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.qst0521.adapter.ScoreAdapter;
import com.dream.qst0521.bean.Score;
import com.dream.qst0521.bean.Student;
import com.dream.qst0521.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab4Activity extends Activity {
	private ListView lsv;
	private List<Score> ls = new ArrayList<Score>();
	private List<Score> ls2 = new ArrayList<Score>();
	private ScoreAdapter scAdapter;

	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private Button exit;
	private Button change;
	private Button change_pwd;
	private Button add_info;
	private String no = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab4);

		Intent intent = getIntent();
		final Bundle bundle = intent.getExtras();// 获取打包好的数据
		final String studentNo = bundle.getString("Personal_No");// 获取登录账号的学号
		no = studentNo;
		textview1 = (TextView) findViewById(R.id.personal_name);
		textview2 = (TextView) findViewById(R.id.personal_grade);
		textview3 = (TextView) findViewById(R.id.personal_telephone);
		lsv = (ListView) findViewById(R.id.listView3);

		add_info = (Button) findViewById(R.id.add_info);

		// 个人中心信息初始化
//		String url = "http://10.0.2.2:8080/QST0521Servlet/personal";
//		RequestParams params = new RequestParams(); // 绑定参数
//		params.put("method_p", "p");
//		params.put("stuNo", studentNo);
//		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//				if (statusCode == 200) {
//					try {
//						// Toast.makeText(Tab4Activity.this,"春天在哪里呀"+response.getBoolean("msg_t"),
//						// Toast.LENGTH_SHORT).show();
//						if (response.getBoolean("msg_p") == true) {
//							String array = response.getString("rows_p");
//							List<Student> list = new ArrayList<Student>();
//							// 将JSON的String 转成一个JsonArray对象
//							JsonParser parser = new JsonParser();
//							JsonArray jsonArray = parser.parse(array).getAsJsonArray();
//							Gson gson = new Gson();
//							for (JsonElement jsonarray : jsonArray) {
//								// 使用GSON，直接转成Student对象
//								Student score = gson.fromJson(jsonarray, Student.class);
//								list.add(score);
//							}
//
//							String username = list.get(0).getUsername();
//							String grade = list.get(0).getGrade();
//							String telephone = list.get(0).getTelephone();
//
//							textview1.setText(username);
//							textview2.setText(grade);
//							textview3.setText(telephone);
//							add_info.setVisibility(View.INVISIBLE);
//
//							if (username.equals("") || username.equals(null)) {
//								textview1.setText("未填写学生姓名");
//								add_info.setVisibility(View.VISIBLE);
//							}
//							if (grade.equals("") || grade.equals(null)) {
//								textview2.setText("未填写学生班级");
//								add_info.setVisibility(View.VISIBLE);
//							}
//							if (telephone.equals("") || telephone.equals(null)) {
//								textview3.setText("未填写联系方式");
//								add_info.setVisibility(View.VISIBLE);
//							}
//
//						} else {
//							Toast.makeText(Tab4Activity.this, "失败！", Toast.LENGTH_SHORT).show();
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//
//		// 个人中心学生成绩初始化
//		// listview初始化
//		//String url2 = "http://10.0.2.2:8080/QST0521Servlet/personal";
//		
//		RequestParams params2 = new RequestParams(); // 绑定参数
//		params2.put("method_p", "personal_score");
//		params2.put("stuNo", studentNo);
//		HttpUtil.get(url, params2, new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode2, Header[] headers2, JSONObject response2) {
//				if (statusCode2 == 200) {
//					try {
//						if (response2.getBoolean("msg") == true) {
//							JSONArray array2 = response2.getJSONArray("rows");
//							// Toast.makeText(Tab2Activity.this, array2.toString(),
//							// Toast.LENGTH_SHORT).show();
//							for (int j = 0; j < array2.length(); j++) {
//								JSONObject jsonObject2 = (JSONObject) array2.get(j);
//								Score uu = new Score();
//								uu.setName(jsonObject2.getString("name"));
//								uu.setCourse(jsonObject2.getString("course"));
//								uu.setScore(jsonObject2.getInt("score"));
//								uu.setRank(jsonObject2.getInt("rank"));
//								ls.add(uu);
//							}
//							scAdapter = new ScoreAdapter(getApplicationContext(),ls);
//							lsv.setAdapter(scAdapter);// 通过setAdapter（）方法将Listview与自定义Adapter绑定
//							scAdapter.notifyDataSetChanged();// 更新数据序列中的数据
//							Toast.makeText(Tab4Activity.this, "显示成功", Toast.LENGTH_SHORT).show();
//						} else {
//							Toast.makeText(Tab4Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});

		// 完善个人信息
		add_info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(Tab4Activity.this, "完善个人信息", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), AddInfoActivity.class);
				Bundle bundle = new Bundle();// 打包组件实例化
				bundle.putString("stuNo", studentNo);// 获取当前用户登录的学号
				intent.putExtras(bundle);// 打包传值
				startActivity(intent);
				// 当前activity不结束
			}
		});

		// 修改学生个人信息
		change = (Button) findViewById(R.id.change_info);
		change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(Tab4Activity.this, "修改个人信息", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), AddInfoActivity.class);
				Bundle bundle = new Bundle();// 打包组件实例化
				bundle.putString("stuNo", studentNo);// 获取当前用户登录的学号
				intent.putExtras(bundle);// 打包传值
				startActivity(intent);
			}
		});

		// 修改密码
		change_pwd = (Button) findViewById(R.id.change_pwd);
		change_pwd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(Tab4Activity.this, "修改密码", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), ChangePwdActivity.class);
				Bundle bundle = new Bundle();// 打包组件实例化
				bundle.putString("stuNo", studentNo);// 获取当前用户登录的学号
				intent.putExtras(bundle);// 打包传值
				startActivity(intent);
			}
		});

		// 退出登录
		exit = (Button) findViewById(R.id.exit);
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Tab4Activity.this, LoginActivity.class);
				startActivity(intent);
				Tab4Activity.this.finish();
			}
		});
	}

	/**
	 * 回调方法，完善个人信息后返回tab4界面时刷新个人信息和成绩信息
	 * Tab4Activity生命周期--》onCreate()--》onResume()
	 */
	@Override
	public void onResume() {
		super.onResume(); // 首先调用父类的方法
		Log.d("回调方法",no);
		//ls2.removeAll(null);
		ls2.clear();
		String url = "http://10.0.2.2:8080/QST0521Servlet/personal";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_p", "p");
		params.put("stuNo", no);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_p") == true) {
							String array = response.getString("rows_p");
							List<Student> list = new ArrayList<Student>();
							// 将JSON的String 转成一个JsonArray对象
							JsonParser parser = new JsonParser();
							JsonArray jsonArray = parser.parse(array).getAsJsonArray();
							Gson gson = new Gson();
							for (JsonElement jsonarray : jsonArray) {
								// 使用GSON，直接转成Student对象
								Student score = gson.fromJson(jsonarray, Student.class);
								list.add(score);
							}

							String username = list.get(0).getUsername();
							String grade = list.get(0).getGrade();
							String telephone = list.get(0).getTelephone();

							textview1.setText(username);
							textview2.setText(grade);
							textview3.setText(telephone);
							add_info.setVisibility(View.INVISIBLE);

							if (username.equals("") || username.equals(null)) {
								textview1.setText("未填写学生姓名");
								add_info.setVisibility(View.VISIBLE);
							}
							if (grade.equals("") || grade.equals(null)) {
								textview2.setText("未填写学生班级");
								add_info.setVisibility(View.VISIBLE);
							}
							if (telephone.equals("") || telephone.equals(null)) {
								textview3.setText("未填写联系方式");
								add_info.setVisibility(View.VISIBLE);
							}

						} else {
							Toast.makeText(Tab4Activity.this, "失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		RequestParams params2 = new RequestParams(); // 绑定参数
		params2.put("method_p", "personal_score");
		params2.put("stuNo", no);
		HttpUtil.get(url, params2, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode2, Header[] headers2, JSONObject response2) {
				if (statusCode2 == 200) {
					try {
						if (response2.getBoolean("msg") == true) {
							JSONArray array2 = response2.getJSONArray("rows");
							for (int j = 0; j < array2.length(); j++) {
								JSONObject jsonObject2 = (JSONObject) array2.get(j);
								Score uu = new Score();
								uu.setName(jsonObject2.getString("name"));
								uu.setCourse(jsonObject2.getString("course"));
								uu.setScore(jsonObject2.getInt("score"));
								uu.setRank(jsonObject2.getInt("rank"));
								ls2.add(uu);
							}
							scAdapter = new ScoreAdapter(getApplicationContext(),ls2);
							lsv.setAdapter(scAdapter);// 通过setAdapter（）方法将Listview与自定义Adapter绑定
							scAdapter.notifyDataSetChanged();// 更新数据序列中的数据
							Toast.makeText(Tab4Activity.this, "成绩信息显示成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Tab4Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 显示个人信息（已弃用）
	 * 
	 * @param no
	 * @return
	 */
	public List<Student> connectionURL(String no) {
		final List<Student> list = new ArrayList<Student>();
		/*
		 * 这里192.168.137.1==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * QST0521Servlet==》你在eclipse创建的项目名称 init==》表示在eclipse的web.xml配置servlet的地址
		 */
		String url = "http://10.0.2.2:8080/QST0521Servlet/personal";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_p", "p");
		params.put("stuNo", no);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_p") == true) {
							String array = response.getString("rows_p");
							// 将JSON的String 转成一个JsonArray对象
							JsonParser parser = new JsonParser();
							JsonArray jsonArray = parser.parse(array).getAsJsonArray();
							Gson gson = new Gson();
							for (JsonElement jsonarray : jsonArray) {
								// 使用GSON，直接转成Student对象
								Student score = gson.fromJson(jsonarray, Student.class);
								list.add(score);
							}
							Toast.makeText(Tab4Activity.this, "显示成功" + list.get(0).getTelephone(), Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(Tab4Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
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
	 * 退出程序
	 */
	public void exit() {
		// 创建退出对话框
		AlertDialog isExit = new AlertDialog.Builder(this).create();
		// 设置对话框标题
		isExit.setTitle("提示");
		// 设置对话框消息
		isExit.setMessage("确定要退出吗");
		// 添加选择按钮并注册监听
		isExit.setButton("确定", listener);
		isExit.setButton2("取消", listener);
		// 显示对话框
		isExit.show();
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};
}
