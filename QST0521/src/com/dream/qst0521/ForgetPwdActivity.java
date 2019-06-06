package com.dream.qst0521;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.qst0521.http.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPwdActivity extends Activity {

	private EditText bu_no;
	private EditText bu_pw;
	private EditText bu_repw;
	private EditText bu_code;
	private Button bu_forget_get_code;
	private Button bu_forget_cancel;
	private Button bu_forgetpwd;
	int num = (int) ((Math.random() * 9 + 1) * 100000);// 获取随机数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);

		bu_no = (EditText) findViewById(R.id.forget_name_edit);
		bu_pw = (EditText) findViewById(R.id.forget_pwd_edit);
		bu_repw = (EditText) findViewById(R.id.forget_repwd_edit);
		bu_code = (EditText) findViewById(R.id.forget_Verification_Code_edit);

		// 忘记密码按钮
		bu_forgetpwd = (Button) findViewById(R.id.forgetpwd);
		bu_forgetpwd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String no = bu_no.getText().toString();
				final String pwd = bu_pw.getText().toString();
				String repwd = bu_repw.getText().toString();
				String code = bu_code.getText().toString();
				String randomnum = num + "";
				if (no.equals("")) {// 无姓名
					if (pwd.equals("")) {// 无密码
						Toast.makeText(getApplicationContext(), "请输入账号和密码！", Toast.LENGTH_SHORT).show();
						return;
					} else {// 无姓名有密码
						Toast.makeText(getApplicationContext(), "请输入账号！", Toast.LENGTH_SHORT).show();
						return;
					}
				} else if (pwd.equals("")) {// 有姓名无密码
					Toast.makeText(getApplicationContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
					return;
				} else {// 有姓名有密码
					if (pwd.equals(repwd)) {// 判断用户输入的密码前后是否一致
						Log.d("unun", no);
						if (code.equals(randomnum)) {// 判断用户获取的验证码是否输入正确,正确的话就进行忘记密码
							// 先判断是否有该账号可以进行忘记密码
							String url = "http://10.0.2.2:8080/QST0521Servlet/register";
							RequestParams params = new RequestParams(); // 绑定参数
							params.put("method_r", "check");
							params.put("studentNo", no);
							HttpUtil.get(url, params, new JsonHttpResponseHandler() {
								@Override
								public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
									if (statusCode == 200) {
										try {
											if (response.getBoolean("msg_r") == true) {
												// 如果查找到有这个账号，就进行重置密码，如果没有找到账号，提示用户
												forgetpwd(no, pwd);// 注册
											} else {
												Toast.makeText(ForgetPwdActivity.this, "该账号不存在！请先进行注册",
														Toast.LENGTH_SHORT).show();
												bu_no.getText().clear();// 清空
												bu_no.requestFocus();// 获得光标焦点
												bu_pw.getText().clear();// 清空
												bu_code.getText().clear();// 清空
												bu_repw.setText("");// 清空
												return;
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
								public void onFailure(int statusCode, Header[] headers, Throwable throwable,
										JSONObject errorResponse) {
									super.onFailure(statusCode, headers, throwable, errorResponse);
									Log.d("Register---servlet", "---->>onFailure" + throwable.toString());
									Toast.makeText(ForgetPwdActivity.this, "请检查服务端口！", Toast.LENGTH_SHORT).show();
								}
							});
						} else {// 如果验证码输入错误
							if (code.equals("")) {// 判断是否已经获取验证码并填写
								Toast.makeText(getApplicationContext(), "请获取验证码！", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(), "验证码输入错误，请重新输入！", Toast.LENGTH_SHORT).show();
								bu_code.getText().clear();// 清空验证码
							}
						}
					} else {
						Toast.makeText(getApplicationContext(), "输入的密码前后不一致，请重新输入！", Toast.LENGTH_SHORT).show();
						bu_pw.requestFocus();// 获得光标焦点
						bu_pw.getText().clear();// 清空
						bu_repw.setText("");// 清空
					}
				}
			}
		});

		// 取消
		bu_forget_cancel = (Button) findViewById(R.id.forget_cancel);// 取消忘记密码
		bu_forget_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "取消忘记密码", Toast.LENGTH_SHORT).show();
				finish();
			}
		});

		bu_forget_get_code = (Button) findViewById(R.id.forget_Verification_Code_get);// 获取验证码
		// 获取验证码
		bu_forget_get_code.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getCode();
			}
		});
	}

	// 注册
	public void forgetpwd(final String id, String pw) {
		/*
		 * 这里192.168.38.45==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * 171023_printting==》你在eclipse创建的项目名称
		 * zhu/LoginServlet==》表示在eclipse的web.xml配置servlet的地址
		 */
		// boolean f=false;
		String url = "http://10.0.2.2:8080/QST0521Servlet/change";
		//直接调用个人中心的修改密码
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("studentNo", id);
		params.put("password", pw);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg") == true) {
							Toast.makeText(ForgetPwdActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(ForgetPwdActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	// 获取弹出框的验证码
	public void getCode() {
		// 创建退出对话框
		AlertDialog isExit = new AlertDialog.Builder(this).create();
		// 设置对话框标题
		isExit.setTitle("验证码");
		// 设置对话框消息
		isExit.setMessage("您的验证码为：" + num);
		// 添加选择按钮并注册监听
		isExit.setButton("确定", listeners);
		// 显示对话框
		isExit.show();
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listeners = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				break;
			default:
				break;
			}
		}
	};
}
