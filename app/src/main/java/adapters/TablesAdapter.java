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
 * Created by E5430 on 11/20/2017.
 */

public class TablesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Table> tablesList;

    // 1
    public TablesAdapter(Context context, ArrayList<Table> tablesList) {
        this.mContext = context;
        this.tablesList = tablesList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 2
    @Override
    public int getCount() {
        return tablesList.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return tablesList.get(position);
    }

    // 5
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
