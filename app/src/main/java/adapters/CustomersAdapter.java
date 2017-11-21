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
 * Created by E5430 on 11/20/2017.
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

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_customer, parent, false);

        ImageView thumb = rowView.findViewById(R.id.customer_thumb);
        TextView tvName = rowView.findViewById(R.id.cutomers_item_name_holder);
        TextView tvID = rowView.findViewById(R.id.customers_item_id_holder);


        // 1
        //Recipe recipe = (Recipe) getItem(position);
        Customer customer = (Customer) getItem(position);

// 2

        tvName.setText(customer.getName() + " " + customer.getSurname());
        tvID.setText(String.valueOf(customer.getId()));

// 3
        //Picasso.with(mContext).load(recipe.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        thumb.setImageResource(R.drawable.user_thumb_80px);
        //thumb.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.user_thumb_80px));

        return rowView;
    }
}
