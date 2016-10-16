package org.alextan.android.exits.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.alextan.android.exits.Constants;
import org.alextan.android.exits.R;
import org.alextan.android.exits.adapter.TripAdapter;
import org.alextan.android.exits.model.directions.Step;

import java.util.List;

public class TripActivity extends AppCompatActivity {

    private RecyclerView mTripRecyclerView;
    private TripAdapter mTripAdapter;
    private List<Step> mSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        mSteps = getIntent().getExtras().getParcelableArrayList(Constants.KEY_STEPS);

        mTripRecyclerView = (RecyclerView) findViewById(R.id.act_trip_rv);
        mTripAdapter = new TripAdapter(TripActivity.this, mSteps);
        mTripRecyclerView.setHasFixedSize(true);
        mTripRecyclerView.setLayoutManager(new LinearLayoutManager(TripActivity.this));
        mTripRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTripRecyclerView.setAdapter(mTripAdapter);
    }
}
