package com.dream.qst0521;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.qst0521.http.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * 
 * FileName: LoginActivity.java
 * @Description: TODO
 * @author 25478
 *
 * @data: 2019年5月27日 下午6:27:33
 */

public class LoginActivity extends Activity {

	private EditText studentNo;// 账号
	private EditText passWord;// 密码
	private Button button1;// 登录
	private Button button2;// 取消
	private Button forget;//忘记密码
	private Button register;//注册
	private CheckBox checkBox;//复选框
	private Switch aSwitch;//记住密码
	
	SharedPreferences sharedPreferences;//文件存储类型
    SharedPreferences.Editor editor;//编辑

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		studentNo = (EditText) findViewById(R.id.studentNo_edit);
		passWord = (EditText) findViewById(R.id.password_edit);
		checkBox=(CheckBox)findViewById(R.id.save_pw);//记住密码
		
		sharedPreferences=getSharedPreferences("rememberpwd", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();//编辑
        
      //保存账号密码
        boolean f = sharedPreferences.getBoolean("remember_password", false);
        if(f){
            String uname=sharedPreferences.getString("un",null);
            String pword=sharedPreferences.getString("pw",null);
            studentNo.setText(uname);
            passWord.setText(pword);//将文件中的账户密码读取显示
            Log.d("记住的密码为：",pword);
            checkBox.setChecked(true);
        }
        
      //是否显示密码
        aSwitch=(Switch)findViewById(R.id.switch_pw);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    if (passWord.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "请输入密码！",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                    	passWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        //把光标设置在文字结尾
                    	passWord.setSelection(passWord.getText().length());
                        Toast.makeText(LoginActivity.this,"显示密码",Toast.LENGTH_SHORT).show();//显示密码
                    }
                }else {
                    if (passWord.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "请输入密码！",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                    	passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码
                        //把光标设置在文字结尾
                    	passWord.setSelection(passWord.getText().length());
                        Toast.makeText(LoginActivity.this, "隐藏密码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //输入密码的时候显示隐藏密码按钮处于隐藏状态
        passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){//获得焦点
                    aSwitch.setChecked(false);
                }
            }
        });

		//登录按钮
		button1 = (Button) findViewById(R.id.login);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String sn = studentNo.getText().toString().trim();
				String pwd = passWord.getText().toString().trim();
				Log.d("Login",sn+","+pwd);
				if (sn.equals("")) {//无学号
					if(pwd.equals("")) {//无密码
						Toast.makeText(getApplicationContext(), "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
						return;
					}else {//无学号有密码
						Toast.makeText(getApplicationContext(), "请输入用户名！", Toast.LENGTH_SHORT).show();
						return;
					}
				} else if(pwd.equals("")) {//有学号无密码
					Toast.makeText(getApplicationContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
					return;
				}else {
					//connectionURL(sn,pwd);
					//连接后台
					String url = "http://10.0.2.2:8080/QST0521Servlet/login";
					RequestParams params = new RequestParams(); // 绑定参数
					params.put("studentNo", sn);
					params.put("password", pwd);
					Log.d("22333433311111123",sn);
					HttpUtil.get(url, params, new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
							Log.d("22333433323",statusCode+"");
							if (statusCode == 200) {
								try {
									Log.d("2323",response.getBoolean("msg")+"");
									if (response.getBoolean("msg") == true) {
										//判断记住密码的复选框是否勾选如果勾选就在SharedPreferences中存入账号片密码，状态值
				                        if(checkBox.isChecked()){
				                            editor.putBoolean("remember_password", true);//如果选中了为true
				                            editor.putString("un",studentNo.getText().toString());
				                            editor.putString("pw",passWord.getText().toString());
				                        }else{
//				                            editor.putBoolean("remember_password", false);
				                            editor.clear();//将临时存储的文件清空
				                        }
				                        editor.commit();//提交
										
										Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
										Intent intent = new Intent(LoginActivity.this, MainActivity.class);
										Bundle bundle=new Bundle();//打包组件实例化
						                bundle.putString("No", sn);//获取当前用户登录的学号
						                intent.putExtras(bundle);//打包传值
										startActivity(intent);
										LoginActivity.this.finish();
									} else {
										Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
										studentNo.setText("");//清空
										passWord.setText("");
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
							Log.d("Login---servlet", "---->>onFailure" + throwable.toString());
							Toast.makeText(LoginActivity.this, "请检查服务端口！", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});

		//取消按钮
		button2 = (Button) findViewById(R.id.login_cancel);
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "退出程序！", Toast.LENGTH_SHORT).show();
				 cancel();
			}
		});
		
		//忘记密码按钮
		forget= (Button) findViewById(R.id.forget_pw);
		forget.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "忘记密码", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(LoginActivity.this,ForgetPwdActivity.class);
                startActivity(intent);
			}
		});
		
		//注册按钮
		register= (Button) findViewById(R.id.resgiter);
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "注册账号", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
			}
		});
	}
	
	//回调方法
//	@Override
//    public void onResume() {
//        super.onResume();  // 首先调用父类的方法
//        studentNo.setText("");//清空
//		passWord.setText("");
//    }

	public void connectionURL(final String id, String pw) {
		/*
		 * 这里192.168.38.45==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * 171023_printting==》你在eclipse创建的项目名称
		 * zhu/LoginServlet==》表示在eclipse的web.xml配置servlet的地址
		 */
		

	}

	// 取消弹出对话框
	public void cancel() {
		// 创建退出对话框
		AlertDialog isExit = new AlertDialog.Builder(this).create();
		// 设置对话框标题
		isExit.setTitle("来自小肥猪们的提示");
		// 设置对话框消息
		isExit.setMessage("确定要退出吗");
		// 添加选择按钮并注册监听
		isExit.setButton("确定", listener);
		isExit.setButton2("取消", listener);
		// 显示对话框
		isExit.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
// 设置对话框标题
			isExit.setTitle("来自小肥猪们的提示");
// 设置对话框消息
			isExit.setMessage("确定要退出吗");
// 添加选择按钮并注册监听
			isExit.setButton("确定", listener);
			isExit.setButton2("取消", listener);
// 显示对话框
			isExit.show();
		}
		return false;
	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				System.exit(0);;
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};
}
