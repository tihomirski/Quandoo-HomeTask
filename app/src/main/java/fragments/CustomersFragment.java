package fragments;

/**
 * Created by E5430 on 11/19/2017.
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
import utils.Utils;

public class CustomersFragment extends Fragment {

    private ListView mListView;
    ArrayList<Customer> customersList;
    private Context context;
    private DatabaseHandler dbHandler;

    public static CustomersFragment newInstance() {

        return new CustomersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout fragment = (ConstraintLayout) inflater.inflate(R.layout.fragment_customers, container, false);
        //Context context = getActivity().getApplicationContext();
        context = getActivity();

        mListView = (ListView) fragment.findViewById(R.id.customers_list_view);
        // 1

        dbHandler = new DatabaseHandler(context);
        customersList = dbHandler.getAllReservations();
//        // 2
//        String[] listItems = new String[customersList.size()];
//        // 3
//        for(int i = 0; i < customersList.size(); i++){
//            Customer customer = customersList.get(i);
//            listItems[i] = customer.getFullname();
//        }
//        // 4
//        //, R.id.cutomers_item_name_holder,
//        ArrayAdapter adapter = new ArrayAdapter(fragment.getContext(), android.R.layout.simple_list_item_1, listItems);
//        mListView.setAdapter(adapter);

        CustomersAdapter adapter = new CustomersAdapter(context, customersList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer selectedCustomer = customersList.get(position);
                Intent detailIntent = new Intent(context, ActivitySelectTable.class);
                //detailIntent.putExtra("name", selectedCustomer.getName() + " " + selectedCustomer.getSurname());
                detailIntent.putExtra("customerId", selectedCustomer.getId());
                startActivity(detailIntent);
            }

        });

        return fragment;
    }
}

