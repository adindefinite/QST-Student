package com.dream.qst0521;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgetPwdActivity extends Activity {
	
	private Button bu_forget_get_code;
	private Button bu_forget_cancel;
	int num= (int) ((Math.random() * 9 + 1) * 100000);//获取随机数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);

		bu_forget_cancel=(Button)findViewById(R.id.forget_cancel);//取消忘记密码
		bu_forget_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Toast.makeText(getApplicationContext(), "取消忘记密码", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
		
		bu_forget_get_code=(Button)findViewById(R.id.forget_Verification_Code_get);//获取验证码
		//获取验证码
        bu_forget_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
	}
	
	//获取弹出框的验证码
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
