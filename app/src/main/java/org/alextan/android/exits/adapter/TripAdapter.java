package org.alextan.android.exits.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.alextan.android.exits.R;
import org.alextan.android.exits.model.directions.Step;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> mSteps;

    public TripAdapter(Context context, List<Step> steps) {
        mContext = context;
        mSteps = steps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(mSteps.get(position));
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Step mItem;
        private TextView mTvArrivalTime;
        private TextView mTvDepartureTime;
        private TextView mTvOrigin;
        private TextView mTvDestination;
        private TextView mTvLine;
        private TextView mTvDuration;
        private RelativeLayout mLayoutLine;



        public ViewHolder(View itemView) {
            super(itemView);
            mTvArrivalTime = (TextView) itemView.findViewById(R.id.station_item_tv_arrival_time);
            mTvDepartureTime = (TextView) itemView.findViewById(R.id.station_item_tv_departure_time);
            mTvOrigin = (TextView) itemView.findViewById(R.id.trip_item_tv_origin);
            mTvDestination = (TextView) itemView.findViewById(R.id.trip_item_tv_destination);
            mTvLine = (TextView) itemView.findViewById(R.id.trip_item_tv_line);
            mTvDuration = (TextView) itemView.findViewById(R.id.trip_item_tv_duration);
            mLayoutLine = (RelativeLayout) itemView.findViewById(R.id.trip_item_layout_line);
        }

        public void setItem(Step item) {
            mItem = item;

            mTvArrivalTime.setText(mItem.getArrivalTime());
            mTvDepartureTime.setText(mItem.getDepartureTime());
            mTvOrigin.setText(mItem.getDepartureStop());
            mTvDestination.setText(mItem.getArrivalStop());
            mTvLine.setText(mItem.getLine());
            mTvDuration.setText("TODO");
            mLayoutLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            mLayoutLine.setBackgroundResource(R.color.colorPrimary);
        }
    }
}