package com.dream.qst0521.adapter;

import java.util.List;

import com.dream.qst0521.R;
import com.dream.qst0521.bean.Score;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScoreAdapter extends BaseAdapter{
	private Context mContext;
	private List<Score> mList;

	public ScoreAdapter(Context con, List<Score> list) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.score_item, null);
		}

		TextView name_tv = (TextView) convertView.findViewById(R.id.name);
		TextView course_tv = (TextView) convertView.findViewById(R.id.course);
		TextView score_tv = (TextView) convertView.findViewById(R.id.score);
		TextView rank_tv = (TextView) convertView.findViewById(R.id.rank);

		Score s = mList.get(position);
		name_tv.setText(s.getName());
		course_tv.setText(s.getCourse());
		score_tv.setText(String.valueOf(s.getScore()));
		rank_tv.setText(String.valueOf(s.getRank()));

		return convertView;
	}
	
	//清空数据方法
	public void clear() {
		mList.clear();
	    notifyDataSetChanged();
	}
}
