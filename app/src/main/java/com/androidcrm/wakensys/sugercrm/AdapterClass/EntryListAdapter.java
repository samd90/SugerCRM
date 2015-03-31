package com.androidcrm.wakensys.sugercrm.AdapterClass;

import com.androidcrm.wakensys.sugercrm.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EntryListAdapter extends BaseAdapter {

	private Context context;
	private String[] mtitle;

	private int[] micon;
	private LayoutInflater inflater;

	public EntryListAdapter(Context context, String[] entry, int[] icon) {
		this.context = context;
		this.mtitle = entry;
		this.micon = icon;

	}

	@Override
	public int getCount() {
		return mtitle.length;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup parent) {

		TextView title;
		ImageView icon;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.entry_list_item, parent,
				false);

		title = (TextView) itemView.findViewById(R.id.title);
		icon = (ImageView) itemView.findViewById(R.id.icon);


        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/font.ttf");
        title.setTypeface(type);
		title.setText(mtitle[position]);

		icon.setImageResource(micon[position]);

		return itemView;

	}

}
