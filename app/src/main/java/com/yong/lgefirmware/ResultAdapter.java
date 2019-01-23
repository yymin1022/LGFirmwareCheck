package com.yong.lgefirmware;

import android.widget.*;
import android.view.*;
import java.util.*;

import androidx.recyclerview.widget.RecyclerView;

public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView tvDevice;
		TextView tvOperator;
		TextView tvChipset;
		TextView tvUpdate;

        ResultViewHolder(View view){
            super(view);
            tvDevice = view.findViewById(R.id.recyclerDevice);
            tvOperator = view.findViewById(R.id.recyclerOperator);
			tvChipset = view.findViewById(R.id.recyclerChipset);
            tvUpdate = view.findViewById(R.id.recyclerUpdate);
        }
    }

    private ArrayList<ResultRecycler> resultRecycler;
    ResultAdapter(ArrayList<ResultRecycler> resultRecycler){
        this.resultRecycler = resultRecycler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new ResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultViewHolder resultViewHolder = (ResultViewHolder) holder;
        resultViewHolder.tvDevice.setText(resultRecycler.get(position).device);
		resultViewHolder.tvOperator.setText(resultRecycler.get(position).operator);
		resultViewHolder.tvChipset.setText(resultRecycler.get(position).chipset);
		resultViewHolder.tvUpdate.setText(resultRecycler.get(position).update);
    }

    @Override
    public int getItemCount() {
        return resultRecycler.size();
    }
}
