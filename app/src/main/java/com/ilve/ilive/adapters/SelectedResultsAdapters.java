package com.ilve.ilive.adapters;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilve.ilive.R;
import com.ilve.ilive.dataModel.SearchResults;

import java.util.ArrayList;

/**
 * Created by ashrafiqubal on 17/02/18.
 */

public class SelectedResultsAdapters  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SearchResults> selectedResults = new ArrayList<>();

    public void setSelectedResults(ArrayList<SearchResults> selectedResults) {
        this.selectedResults = selectedResults;
    }

    public SelectedResultsAdapters(AppCompatActivity activity) {

        this.activity = activity;
    }

    AppCompatActivity activity;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType ==0 ){
            return new ViewHolderHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_selected_header, parent, false));
        }else {
            return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_selected_results, parent, false));
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolderItem){
            ((ViewHolderItem) holder).item_name.setText(selectedResults.get(position-1).getTestName());
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(selectedResults != null){
            if(selectedResults.size() > 0)
            return selectedResults.size() + 1;
            else
                return 0;
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

    private class ViewHolderHeader extends RecyclerView.ViewHolder {
        View view;
        TextView item_name;

        ViewHolderHeader(View view) {
            super(view);
            this.view = view;
            item_name = view.findViewById(R.id.item_name);
        }
    }
}
