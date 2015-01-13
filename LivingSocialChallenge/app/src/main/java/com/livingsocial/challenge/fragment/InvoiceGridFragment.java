package com.livingsocial.challenge.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import com.livingsocial.challenge.R;
import com.livingsocial.challenge.activity.DetailActivity;
import com.livingsocial.challenge.manager.DataManager;
import com.livingsocial.challenge.model.Invoice;
import com.livingsocial.challenge.view.InvoiceGridAdapter;
import java.util.List;

/**
 * Created by Ale on 1/12/15.
 */

public class InvoiceGridFragment extends Fragment implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private GridView gridview;
    private InvoiceGridAdapter adapter;

    public InvoiceGridFragment(){
        super();
    }

    public static InvoiceGridFragment newInstance() {
        InvoiceGridFragment fragment = new InvoiceGridFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ls_fragment_invoice_grid, container, false);
        gridview = (GridView)view.findViewById(R.id.ls_gridview);
        adapter = new InvoiceGridAdapter(getActivity());
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(this);
        updateView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

    }

    public void updateView(){
        List<Invoice> list = DataManager.getInstance(getActivity()).getInvoices();
        adapter.setData(list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Invoice invoice = adapter.getItem(position);
        Intent intent = DetailActivity.prepareIntent(getActivity(),invoice);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if(s.length() > 0){
            adapter.getFilter().filter(s.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.length() == 0){
            adapter.resetFilter();
        }
        return false;
    }

    @Override
    public boolean onClose() {
        adapter.resetFilter();
        return true;
    }
}
