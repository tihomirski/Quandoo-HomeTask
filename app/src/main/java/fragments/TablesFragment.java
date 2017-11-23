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
import android.widget.GridView;

import com.tivachkov.reservations.reservations.ActivityTableStatus;
import com.tivachkov.reservations.reservations.Customer;
import com.tivachkov.reservations.reservations.R;
import com.tivachkov.reservations.reservations.Table;

import java.util.ArrayList;

import adapters.TablesAdapter;
import helpers.DatabaseHandler;

public class TablesFragment extends Fragment {

    private GridView mGridView;
    private ArrayList<Customer> mCustomersList;
    private Context mContext;
    private DatabaseHandler mDBHandler;
    private TablesAdapter mTablesAdapter;

    public static TablesFragment newInstance() {
        return new TablesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout fragment = (ConstraintLayout) inflater.inflate(R.layout.fragment_tables, container, false);
        //Context mContext = getActivity().getApplicationContext();
        mContext = getActivity();
        GridView gridView = (GridView) fragment.findViewById(R.id.grid_view_tables_fragment);

        mDBHandler = new DatabaseHandler(mContext);
        final ArrayList<Table> tablesList = mDBHandler.getTables();

        mTablesAdapter = new TablesAdapter(mContext, tablesList);
        gridView.setAdapter(mTablesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent intentTableStatus = new Intent(mContext, ActivityTableStatus.class);
                intentTableStatus.putExtra("tableNr", String.valueOf(position+1));
                startActivity(intentTableStatus);

            }
        });
        return fragment;
    }
}
