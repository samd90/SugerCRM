package com.androidcrm.wakensys.sugercrm.AdapterClass;

import java.util.List;

import com.androidcrm.wakensys.sugercrm.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EntriesListAdapter extends BaseAdapter {

	private Context context;
	private List<String> mtitle;

	// private int[] micon;
	private LayoutInflater inflater;

	public EntriesListAdapter(Context context, List<String> entry_names) {
		this.context = context;
		this.mtitle = entry_names;
		// this.micon = icon;

	}

	@Override
	public int getCount() {
		return mtitle.size();
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
		// ImageView icon;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.entries_list_item, parent,
				false);

		title = (TextView) itemView.findViewById(R.id.title);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/roboto_regular.ttf");
		// icon = (ImageView) itemView.findViewById(R.id.icon);

		title.setText(mtitle.get(position));
        title.setTypeface(type);
		// icon.setImageResource(micon[position]);



		return itemView;

	}

}
