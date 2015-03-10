package com.androidcrm.wakensys.sugercrm.AdapterClass;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidcrm.wakensys.sugercrm.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {

	private Context context;
	private List<String> mtitle;
    private List<String> mSubTitle;

	// private int[] micon;
	private LayoutInflater inflater;

	public ListAdapter(Context context, List<String> entry_names, List<String> sub_entry_names) {
		this.context = context;
		this.mtitle = entry_names;
        this.mSubTitle = sub_entry_names;
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
        TextView title_1;
		// ImageView icon;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.list_item, parent,
				false);

		title = (TextView) itemView.findViewById(R.id.title);
        title_1 = (TextView) itemView.findViewById(R.id.title1);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/roboto_regular.ttf");
		// icon = (ImageView) itemView.findViewById(R.id.icon);

		title.setText(mtitle.get(position));
        title_1.setText(mSubTitle.get(position));
        title.setTypeface(type);
        title_1.setTypeface(type);
		// icon.setImageResource(micon[position]);



		return itemView;

	}

}
