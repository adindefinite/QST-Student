package com.dream.qst0521.adapter;

import java.util.List;

import com.dream.qst0521.R;
import com.dream.qst0521.bean.Student;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StudentAdapter extends BaseAdapter {
	private Context mContext;
	private List<Student> mList;

	public StudentAdapter(Context con, List<Student> list) {
		this.mContext = con;
		this.mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.student_item, null);
		}

		TextView name_tv = (TextView) convertView.findViewById(R.id.name_listview);
		TextView grade_tv = (TextView) convertView.findViewById(R.id.grade_listview);
		TextView studentNo_tv = (TextView) convertView.findViewById(R.id.studentNo_listview);
		TextView telephone_tv = (TextView) convertView.findViewById(R.id.telephone_listview);

		Student s = mList.get(position);
		name_tv.setText(s.getUsername());
		grade_tv.setText(s.getGrade());
		studentNo_tv.setText(s.getStudentNo());
		telephone_tv.setText(s.getTelephone());
		
		name_tv.setTextColor(Color.parseColor("#48D1CC"));
		grade_tv.setTextColor(Color.parseColor("#000000"));
		studentNo_tv.setTextColor(Color.parseColor("#000000"));
		telephone_tv.setTextColor(Color.parseColor("#000000"));
		
		if((name_tv.getText()).equals("") || (name_tv.getText()).equals(null)){
			name_tv.setText("未填写学生姓名");
			name_tv.setTextColor(Color.parseColor("#8A8A8A"));
		}
		if((grade_tv.getText()).equals("") || (grade_tv.getText()).equals(null)){
			grade_tv.setText("未填写学生班级");
			grade_tv.setTextColor(Color.parseColor("#8A8A8A"));
		}
		if((studentNo_tv.getText()).equals("") || (studentNo_tv.getText()).equals(null)){
			studentNo_tv.setText("未填写学生学号");
			studentNo_tv.setTextColor(Color.parseColor("#8A8A8A"));
		}
		if((telephone_tv.getText()).equals("") || (telephone_tv.getText()).equals(null)){
			telephone_tv.setText("未填写联系方式");
			telephone_tv.setTextColor(Color.parseColor("#8A8A8A"));
		}

		return convertView;
	}
}
