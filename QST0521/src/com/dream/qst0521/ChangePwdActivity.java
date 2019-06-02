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

public class ChangePwdActivity extends Activity {
	
	private EditText pwd;
	private EditText pwd_re;
	private EditText code;
	private Button bu_change;
	private Button bu_change_get_code;
	private Button bu_change_cancel;
	private String studentNo="";
	int num= (int) ((Math.random() * 9 + 1) * 100000);//获取随机数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();// 获取打包好的数据
		studentNo = bundle.getString("stuNo");// 获取登录账号的学号
		
		pwd=(EditText)findViewById(R.id.change_pwd_edit);
        pwd_re=(EditText)findViewById(R.id.change_repwd_edit);
        code=(EditText)findViewById(R.id.change_Verification_Code_edit);
		
		/**
		 * 确认修改密码
		 */
		bu_change=(Button)findViewById(R.id.changepwd);//取消忘记密码
		bu_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	String p=pwd.getText().toString();
            	String pr=pwd_re.getText().toString();
            	String c=code.getText().toString();
            	String randomnum=num+"";
            	if (p.equals("")) {//无密码
					if(pr.equals("")) {//无重复密码
						Toast.makeText(getApplicationContext(), "请输入密码和确认密码！", Toast.LENGTH_SHORT).show();
						return;
					}else {//无姓名有密码
						Toast.makeText(getApplicationContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
						return;
					}
				} else if(pr.equals("")) {//有密码无重复密码
					Toast.makeText(getApplicationContext(), "请再次输入确认密码！", Toast.LENGTH_SHORT).show();
					return;
				}else {//有姓名有密码
					if(p.equals(pr)) {//判断用户输入的密码前后是否一致
						Log.d("unun",p);
                        if(c.equals(randomnum)){//判断用户获取的验证码是否输入正确,正确的话就进行注册
                        	//开始判断是否有重复的用户存在
                        	String url = "http://10.0.2.2:8080/QST0521Servlet/change";
                    		RequestParams params = new RequestParams(); // 绑定参数
                    		params.put("studentNo", studentNo);
                    		params.put("password", p);
                    		HttpUtil.get(url, params, new JsonHttpResponseHandler() {
                    			@Override
                    			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    				if (statusCode == 200) {
                    					try {
                    						if (response.getBoolean("msg") == true) {
                    							Toast.makeText(ChangePwdActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                    							finish();
                    						} else {
                    							Toast.makeText(ChangePwdActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    							pwd.setText("");
                    							pwd_re.setText("");
                    							code.setText("");
                    						}
                    					} catch (JSONException e) {
                    						e.printStackTrace();
                    					}
                    				}
                    			}
                    		});
                        }else{//如果验证码输入错误
                            if(code.equals("")){//判断是否已经获取验证码并填写
                                Toast.makeText(getApplicationContext(),"请获取验证码！",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"验证码输入错误，请重新输入！",Toast.LENGTH_SHORT).show();
                                code.getText().clear();//清空验证码
                            }
                        }
					}else {
						Toast.makeText(getApplicationContext(), "输入的密码前后不一致，请重新输入！", Toast.LENGTH_SHORT).show();
						pwd.requestFocus();//获得光标焦点
						pwd.setText("");
						pwd_re.setText("");
						code.setText("");
					}
				}
            }
		});
		
		/**
		 * 取消修改密码
		 */
		bu_change_cancel=(Button)findViewById(R.id.change_cancel);//取消忘记密码
		bu_change_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Toast.makeText(getApplicationContext(), "取消忘记密码", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
		
		/**
		 * 获取验证码
		 */
		bu_change_get_code=(Button)findViewById(R.id.change_Verification_Code_get);//获取验证码
		//获取验证码
		bu_change_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
	}
	
	/**
	 * 获取弹出框的验证码
	 */
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
}
