package com.akurihine.exam.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.akurihine.exam.DealClickListener;
import com.akurihine.exam.model.Deal;
import com.akurihine.exam.view.GlossyCardView;
import com.akurihine.exam.view.SimpleCardView;
import com.akurihine.exam.view.UnderTenView;

import java.util.List;

public class DealListAdapter extends RecyclerView.Adapter {
    private final List<Deal> underTenDeals;
    private final DealClickListener dealClickListener;
    private List<Integer> types;
    private List<Deal> deals;

    public DealListAdapter(List<Deal> deals, List<Integer> types, List<Deal> underTenDeals, DealClickListener dealClickListener) {
        this.types = types;
        this.deals = deals;
        this.underTenDeals = underTenDeals;
        this.dealClickListener = dealClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 && underTenDeals != null) {
            return 4;
        }

        return types.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case 0:
            case 1:
                GlossyCardView glossyCardView = new GlossyCardView(parent.getContext());
                glossyCardView.setDealClickListener(dealClickListener);

                if (viewType == 0) {
                    glossyCardView.setStyle(GlossyCardView.CardStyle.SHORT);
                } else {
                    glossyCardView.setStyle(GlossyCardView.CardStyle.TALL);
                }

                viewHolder = new GlossDealViewHolder(glossyCardView);
                break;
            case 2:
            case 3:
                SimpleCardView simpleCardView = new SimpleCardView(parent.getContext());
                simpleCardView.setDealClickListener(dealClickListener);

                if (viewType == 2) {
                    simpleCardView.setStyle(SimpleCardView.CardStyle.DEFUALT);
                } else {
                    simpleCardView.setStyle(SimpleCardView.CardStyle.STRIPPED);
                }

                viewHolder = new SimpleDealViewHolder(simpleCardView);
                break;
            case 4:
                UnderTenView underTenView = new UnderTenView(parent.getContext());
                underTenView.setDealClickListener(dealClickListener);

                viewHolder = new UnderTenViewHolder(underTenView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case 0:
            case 1:
                GlossDealViewHolder glossDealViewHolder = (GlossDealViewHolder) viewHolder;
                glossDealViewHolder.glossyCardView.setData(deals.get(position));
                break;
            case 2:
            case 3:
                SimpleDealViewHolder simpleDealViewHolder = (SimpleDealViewHolder) viewHolder;
                simpleDealViewHolder.simpleCardView.setData(deals.get(position));
                break;
            case 4:
                UnderTenViewHolder underTenViewHolder = (UnderTenViewHolder) viewHolder;
                underTenViewHolder.underTenView.setData(underTenDeals);
        }
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public static class GlossDealViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public GlossyCardView glossyCardView;
        public GlossDealViewHolder(GlossyCardView v) {
            super(v);
            glossyCardView = v;
        }
    }

    public static class SimpleDealViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public SimpleCardView simpleCardView;
        public SimpleDealViewHolder(SimpleCardView v) {
            super(v);
            simpleCardView = v;
        }
    }

    public static class UnderTenViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public UnderTenView underTenView;
        public UnderTenViewHolder(UnderTenView v) {
            super(v);
            underTenView = v;
        }
    }
}