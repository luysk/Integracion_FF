package ucr.tools;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class VisitaAdapter extends BaseAdapter {
	
	@SuppressWarnings("unused")
	private Context context;
	private ArrayAdapter<String> matrix;
	
	public VisitaAdapter(Context context) {
		this.context = context;
		matrix = new ArrayAdapter<String>(context, 0);	
	}
	
	public VisitaAdapter(Context context, ArrayList<String> values) {
		this.context = context;
		matrix = new ArrayAdapter<String>(context, 0);
		for (int i = 0; i < values.size(); ++i) {
			matrix.add(values.get(i));
		}
	}
	
	public VisitaAdapter(Context context2, int simpleListItem1, ArrayList<String> temp) {
		// TODO Auto-generated constructor stub
	}

	public void add(String values) {
		matrix.add(values);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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
		return null;
	}

}
