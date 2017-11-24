package adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tivachkov.reservations.reservations.Customer;
import com.tivachkov.reservations.reservations.R;

import java.util.ArrayList;

/**
 * Created by tivachkov on 11/20/2017.
 */

public class CustomersAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Customer> mDataSource;

    public CustomersAdapter(Context context, ArrayList<Customer> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.list_item_customer, parent, false);

        ImageView thumb = rowView.findViewById(R.id.customer_thumb);
        TextView tvName = rowView.findViewById(R.id.cutomers_item_name_holder);
        TextView tvID = rowView.findViewById(R.id.customers_item_id_holder);

        Customer customer = (Customer) getItem(position);

        tvName.setText(customer.getName() + " " + customer.getSurname());
        tvID.setText(String.valueOf(customer.getId()));

        thumb.setImageResource(R.drawable.user_thumb_80px);

        return rowView;
    }
}
