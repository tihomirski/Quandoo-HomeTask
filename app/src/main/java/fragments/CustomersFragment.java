package fragments;

/**
 * Created by tivachkov on 11/19/2017.
 */


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tivachkov.reservations.reservations.ActivitySelectTable;
import com.tivachkov.reservations.reservations.Customer;
import com.tivachkov.reservations.reservations.R;

import java.util.ArrayList;

import adapters.CustomersAdapter;
import helpers.DatabaseHandler;

public class CustomersFragment extends Fragment {

    private ListView mListView;
    private ArrayList<Customer> mCustomersList;
    private Context mContext;
    private DatabaseHandler dbHandler;

    public static CustomersFragment newInstance() {
        return new CustomersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout fragment = (ConstraintLayout) inflater.inflate(R.layout.fragment_customers, container, false);
        mContext = getActivity();

        mListView = (ListView) fragment.findViewById(R.id.customers_list_view);

        dbHandler = new DatabaseHandler(mContext);
        mCustomersList = dbHandler.getAllReservations();

        CustomersAdapter adapter = new CustomersAdapter(mContext, mCustomersList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer selectedCustomer = mCustomersList.get(position);
                Intent detailIntent = new Intent(mContext, ActivitySelectTable.class);
                detailIntent.putExtra("customerId", selectedCustomer.getId());
                startActivity(detailIntent);
            }

        });

        return fragment;
    }
}

