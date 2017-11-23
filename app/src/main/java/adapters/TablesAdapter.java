package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tivachkov.reservations.reservations.*;

import java.util.ArrayList;

/**
 * Created by tivachkov on 11/20/2017.
 */

public class TablesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Table> mTablesList;

    public TablesAdapter(Context context, ArrayList<Table> tablesList) {
        this.mContext = context;
        this.mTablesList = tablesList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mTablesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mTablesList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View cellView = mInflater.inflate(R.layout.list_item_table, parent, false);
        TextView tvTableNumber = (TextView) cellView.findViewById(R.id.table_number_holder);
        tvTableNumber.setText(String.valueOf(position + 1));

        boolean available = ((Table) getItem(position)).isAvailable();
        if (available) {
            tvTableNumber.setBackgroundColor(mContext.getResources().getColor(R.color.colorTableAvailable, null));
        } else {
            tvTableNumber.setBackgroundColor(mContext.getResources().getColor(R.color.colorTableUnavailable, null));
        }
        return cellView;
    }

}
