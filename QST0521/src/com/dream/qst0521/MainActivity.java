package com.dream.qst0521;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();//获取打包好的数据
        String studentNo=bundle.getString("No");//获取登录账号的学号
		
		TabHost tbHost=(TabHost) findViewById(android.R.id.tabhost);
		tbHost.setup();
		
		LayoutInflater.from(this).inflate(R.layout.activity_tab1,
				tbHost.getTabContentView());
		LayoutInflater.from(this).inflate(R.layout.activity_tab2, 
				tbHost.getTabContentView());
		LayoutInflater.from(this).inflate(R.layout.activity_tab3, 
				tbHost.getTabContentView());
		LayoutInflater.from(this).inflate(R.layout.activity_tab4, 
				tbHost.getTabContentView());
		
		TabHost.TabSpec tab1=tbHost.newTabSpec("tab1").
				setIndicator("学生管理").
				setContent(new Intent(getApplicationContext(), Tab1Activity.class));
		TabHost.TabSpec tab2=tbHost.newTabSpec("tab2").
				setIndicator("成绩管理").
				setContent(new Intent(getApplicationContext(), Tab2Activity.class));
		TabHost.TabSpec tab3=tbHost.newTabSpec("tab3").
				setIndicator("统计查询").
				setContent(new Intent(getApplicationContext(), Tab3Activity.class));
		TabHost.TabSpec tab4=tbHost.newTabSpec("tab4").
				setIndicator("个人中心");
		Intent intent1 = new Intent(MainActivity.this, Tab4Activity.class);
		Bundle bundle1=new Bundle();//打包组件实例化
        bundle1.putString("Personal_No", studentNo);//获取当前用户登录的学号
        intent1.putExtras(bundle1);//打包传值
		tab4.setContent(intent1);
		Log.d("123","123");
		
		tbHost.addTab(tab1);
		tbHost.addTab(tab2);
		tbHost.addTab(tab3);
		tbHost.addTab(tab4);
		
		tbHost.setCurrentTab(0);
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