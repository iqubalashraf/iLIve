package com.ilve.ilive.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilve.ilive.R;
import com.ilve.ilive.dataModel.SearchResults;
import com.ilve.ilive.interfaces.NetworkResponse;
import com.ilve.ilive.utiles.GenralUtiles;

import java.util.ArrayList;

/**
 * Created by ashrafiqubal on 17/02/18.
 */

public class SearchResultsAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SearchResults> searchResultsArrayList = new ArrayList<>();
    ArrayList<SearchResults> selectedResults = new ArrayList<>();
    NetworkResponse networkResponse;

    AppCompatActivity activity;

    public void setSearchResultsArrayList(ArrayList<SearchResults> searchResultsArrayList) {
        this.searchResultsArrayList = searchResultsArrayList;
    }

    public SearchResultsAdapters(AppCompatActivity activity, NetworkResponse networkResponse) {

        this.activity = activity;
        this.networkResponse = networkResponse;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_search_results, parent, false));

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolderItem){
            ((ViewHolderItem) holder).item_name.setText(searchResultsArrayList.get(position).getTestName());
            ((ViewHolderItem) holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedResults != null){
                        if(selectedResults.size() == 0){
                            addItemtoSelected(searchResultsArrayList.get(holder.getAdapterPosition()));
                        }else {
                            boolean needToAdd = true;
                            for (int i=0; i < selectedResults.size(); i++){
                                if(selectedResults.get(i).getTestId() == searchResultsArrayList.get(holder.getAdapterPosition()).getTestId()){
                                    needToAdd = false;
                                    GenralUtiles.showMessage("Item already exits in selected list");
                                    break;
                                }
                            }
                            if(needToAdd)
                                addItemtoSelected(searchResultsArrayList.get(holder.getAdapterPosition()));

                        }
                    }

                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(searchResultsArrayList != null){
            return searchResultsArrayList.size();
        }else {
            return 0;
        }
    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {
        View view;
        TextView item_name;

        ViewHolderItem(View view) {
            super(view);
            this.view = view;
            item_name = view.findViewById(R.id.item_name);
        }
    }
    private void addItemtoSelected(SearchResults searchResults){
        selectedResults.add(searchResults);
        if(networkResponse != null){
            networkResponse.onNewSelection(selectedResults);
        }
    }
}
