package com.dream.qst0521;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @Classname AddInfoActivity.java
 * @author 25478
 * @Data 2019年5月23日
 */
public class AddInfoActivity extends Activity {
	private TextView stuno;
	private EditText name;
	private EditText grade;
	private EditText telephone;
	private RadioGroup radioGroup;
	private RadioButton man;
	private RadioButton woman;
	private Button add;
	private Button add_cancel;
	private int sex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_info);

		Intent intent = getIntent();
		final Bundle bundle = intent.getExtras();// 获取打包好的数据
		final String studentNo = bundle.getString("stuNo");// 获取登录账号的学号

		// 控件实例化
		stuno = (TextView) findViewById(R.id.add_no_edit);
		name = (EditText) findViewById(R.id.add_name_edit);
		grade = (EditText) findViewById(R.id.add_grade_edit);
		telephone = (EditText) findViewById(R.id.add_telephone_edit);
		add = (Button) findViewById(R.id.add);
		add_cancel = (Button) findViewById(R.id.add_cancel);

		// 性别按钮实例化
		radioGroup = (RadioGroup) findViewById(R.id.radio_sex);
		man = (RadioButton) findViewById(R.id.radio_man);
		woman = (RadioButton) findViewById(R.id.radio_woman);
		
		//单选框选中效果
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(radioGroup.getCheckedRadioButtonId()){
                    case R.id.radio_man:
                    	Toast.makeText(radioGroup.getContext(),"性别为男",Toast.LENGTH_SHORT).show();
                        sex=0;
                        break;
                    case R.id.radio_woman:
                        Toast.makeText(radioGroup.getContext(),"性别为女",Toast.LENGTH_SHORT).show();
                        sex=1;
                        break;
                }
            }
        });

		// 个人中心信息初始化
		String url = "http://10.0.2.2:8080/QST0521Servlet/personal";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_p", "p");
		params.put("stuNo", studentNo);
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

							stuno.setText(list.get(0).getStudentNo());
							if(!(list.get(0).getUsername().equals("")) && !(list.get(0).getUsername().equals(null))){
								name.setText(list.get(0).getUsername());
								name.requestFocus();//获得焦点
								name.setSelection(name.getText().toString().length());//将光标移至文字末尾
							}
							if(!(list.get(0).getGrade().equals("")) && !(list.get(0).getGrade().equals(null))){
								grade.setText(list.get(0).getGrade());
							}
							if(!(list.get(0).getTelephone().equals("")) && !(list.get(0).getTelephone().equals(null))){
								telephone.setText(list.get(0).getTelephone());
							}
							String sexx=list.get(0).getGender()+"";
							if(!(sexx.equals("")) && !(sexx.equals(null))){
								if(list.get(0).getGender()==0) {
									man.setChecked(true);
								}
								if(list.get(0).getGender()==1) {
									woman.setChecked(true);
								}
							}
						} else {
							Toast.makeText(AddInfoActivity.this, "失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
//		name.requestFocus();//获得焦点
//		name.setSelection(name.getText().toString().length());//将光标移至文字末尾

		// 确认完善信息
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//获取edittext内要完善的信息
				String studentNo=stuno.getText().toString();
				String username=name.getText().toString();
				String grrade=grade.getText().toString();
				String tele=telephone.getText().toString();
				
				if(username.trim().length()==0) {
					Toast.makeText(AddInfoActivity.this, "请输入完整的用户名", Toast.LENGTH_SHORT).show();
					return;
				}
				if(grrade.trim().length()==0) {
					Toast.makeText(AddInfoActivity.this, "请输入完整的班级", Toast.LENGTH_SHORT).show();
					return;
				}
				if(tele.trim().length()==0) {
					Toast.makeText(AddInfoActivity.this, "请输入完整的联系方式", Toast.LENGTH_SHORT).show();
					return;
				}
				
				//连接后台
				String url = "http://10.0.2.2:8080/QST0521Servlet/addinfo";
				RequestParams params = new RequestParams(); // 绑定参数
				params.put("studentNo", studentNo);
				params.put("username", username);
				params.put("grade", grrade);
				params.put("telephone", tele);
				params.put("sex", sex);
				HttpUtil.get(url, params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
						if (statusCode == 200) {
							try {
								if (response.getBoolean("msg") == true) {
									Toast.makeText(AddInfoActivity.this, "操作成功!", Toast.LENGTH_SHORT).show();
									finish();//结束当前进程
								} else {
									Toast.makeText(AddInfoActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
									stuno.setText("");//清空
									name.setText("");
									grade.setText("");
									telephone.setText("");
									man.setChecked(true);//默认性别为男性
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
			});
			}
		});

		// 取消完善信息
		add_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "取消操作", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
}
