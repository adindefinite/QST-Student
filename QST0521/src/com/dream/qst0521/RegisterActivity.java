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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText reg_no;
	private EditText reg_pw;
	private EditText reg_repw;
	private EditText reg_code;
	private Button bu_get_code;
	private Button bu_reg;
	private Button bu_reg_cancel;
	int num= (int) ((Math.random() * 9 + 1) * 100000);//获取随机数
	
	private boolean f=false;//判断是否有重复的用户
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		reg_no=(EditText)findViewById(R.id.reg_name_edit);
        reg_pw=(EditText)findViewById(R.id.reg_pwd_edit);
        reg_repw=(EditText)findViewById(R.id.reg_repwd_edit);
        reg_code=(EditText)findViewById(R.id.Verification_Code_edit);
        
        bu_reg_cancel=(Button)findViewById(R.id.register_cancel);//取消注册
        bu_reg_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Toast.makeText(getApplicationContext(), "取消注册", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
		
		bu_get_code=(Button)findViewById(R.id.Verification_Code_get);//获取验证码按钮
		bu_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
		
		//注册
		bu_reg=(Button)findViewById(R.id.register);//注册
		bu_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String studentno=reg_no.getText().toString().trim();
                final String password=reg_pw.getText().toString().trim();
                String repassword=reg_repw.getText().toString().trim();
                String getCode=reg_code.getText().toString().trim();
                String randomnum=num+"";
				Log.d("Login",studentno+","+password);
				if (studentno.equals("")) {//无姓名
					if(password.equals("")) {//无密码
						Toast.makeText(getApplicationContext(), "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
						return;
					}else {//无姓名有密码
						Toast.makeText(getApplicationContext(), "请输入用户名！", Toast.LENGTH_SHORT).show();
						return;
					}
				} else if(password.equals("")) {//有姓名无密码
					Toast.makeText(getApplicationContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
					return;
				}else {//有姓名有密码
					if(password.equals(repassword)) {//判断用户输入的密码前后是否一致
						Log.d("unun",studentno);
                        if(getCode.equals(randomnum)){//判断用户获取的验证码是否输入正确,正确的话就进行注册
                        	//f=check(studentno);
                        	//Toast.makeText(RegisterActivity.this, f+",,,", Toast.LENGTH_SHORT).show();
                        	//开始判断是否有重复的用户存在
                        	String url = "http://10.0.2.2:8080/QST0521Servlet/register";
                    		RequestParams params = new RequestParams(); // 绑定参数
                    		params.put("method_r", "check");
                    		params.put("studentNo", studentno);
                    		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
                    			@Override
                    			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    				if (statusCode == 200) {
                    					try {
                    						if (response.getBoolean("msg_r") == true) {
                                            		Toast.makeText(RegisterActivity.this, "已有该账号！", Toast.LENGTH_SHORT).show();
                                            		reg_no.getText().clear();//清空
                                            		reg_no.requestFocus();//获得光标焦点
                                                    reg_pw.getText().clear();//清空
                                                    reg_code.getText().clear();//清空
                                                    reg_repw.setText("");//清空
                                            		return;
                    						} else {
                    							register(studentno,password);//注册
                    							//Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                    						}
                    					} catch (JSONException e) {
                    						e.printStackTrace();
                    					}
                    				}
                    			}
                    		});
                    		Log.d("fffffff",f+"");
                        }else{//如果验证码输入错误
                            if(getCode.equals("")){//判断是否已经获取验证码并填写
                                Toast.makeText(getApplicationContext(),"请获取验证码！",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"验证码输入错误，请重新输入！",Toast.LENGTH_SHORT).show();
                                reg_code.getText().clear();//清空验证码
                            }
                        }
					}else {
						Toast.makeText(getApplicationContext(), "输入的密码前后不一致，请重新输入！", Toast.LENGTH_SHORT).show();
						reg_pw.requestFocus();//获得光标焦点
                        reg_pw.getText().clear();//清空
                        reg_repw.setText("");//清空
					}
				}
            }
		});
	}
	
	
	
	//注册
	public void register(final String id, String pw) {
		/*
		 * 这里192.168.38.45==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * 171023_printting==》你在eclipse创建的项目名称
		 * zhu/LoginServlet==》表示在eclipse的web.xml配置servlet的地址
		 */
		//boolean f=false;
		String url = "http://10.0.2.2:8080/QST0521Servlet/register";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_r", "register");
		params.put("studentNo", id);
		params.put("password", pw);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_r") == true) {
							Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	//获取弹出框中的验证码
	public void getCode(){
        // 创建退出对话框
        AlertDialog isExit = new AlertDialog.Builder(this).create();
        // 设置对话框标题
        isExit.setTitle("验证码");
        // 设置对话框消息
        isExit.setMessage("您的验证码为："+num);
        // 添加选择按钮并注册监听
        isExit.setButton("确定", listeners);
        // 显示对话框
        isExit.show();
    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listeners = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    break;
                default:
                    break;
            }
        }
    };
    
    /**
     * 检查是否有重复的用户名存在——已弃用
     * @param id
     * @return
     */
    public boolean check(final String id) {
		/*
		 * 这里192.168.38.45==》表示你当前使用的网络ip，还有如果是真机运行那么手机必须要链接你自个的电脑的wifi，这样才能保证在同一个网络ID地址
		 * 171023_printting==》你在eclipse创建的项目名称
		 * zhu/LoginServlet==》表示在eclipse的web.xml配置servlet的地址
		 */
		//boolean f=false;
		String url = "http://192.168.38.45:8080/QST0521Servlet/register";
		RequestParams params = new RequestParams(); // 绑定参数
		params.put("method_r", "check");
		params.put("studentNo", id);
		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				if (statusCode == 200) {
					try {
						if (response.getBoolean("msg_r") == true) {
							f=true;
						} else {
							//Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		Log.d("fffffff",f+"");
		return f;
	}
}
