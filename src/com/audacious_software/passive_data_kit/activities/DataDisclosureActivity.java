package com.audacious_software.passive_data_kit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.audacious_software.passive_data_kit.activities.generators.GeneratorsAdapter;
import com.audacious_software.pdk.passivedatakit.R;

public class DataDisclosureActivity extends AppCompatActivity {
    private GeneratorsAdapter mAdapter = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_data_disclosure_pdk);
        this.getSupportActionBar().setTitle(R.string.title_data_disclosure);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FrameLayout dataView = (FrameLayout) this.findViewById(R.id.data_view);

        this.mAdapter = new GeneratorsAdapter();
        this.mAdapter.setContext(this.getApplicationContext());
        this.mAdapter.setDataView(dataView);

        RecyclerView listView = (RecyclerView) this.findViewById(R.id.list_view);

        listView.setLayoutManager(new LinearLayoutManager(this));

        listView.setAdapter(this.mAdapter);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            this.finish();
        }

        return true;
    }
}
