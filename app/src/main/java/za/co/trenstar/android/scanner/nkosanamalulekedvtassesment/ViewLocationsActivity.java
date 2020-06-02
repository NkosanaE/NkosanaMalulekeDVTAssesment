package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment;

import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.adapter.CustomCursorAdapter;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.database.DbHelper;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.database.DbManager;

public class ViewLocationsActivity extends AppCompatActivity {
    private DbManager dbManager;

    private ListView listView;

    private CustomCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locations);

        dbManager = new DbManager(this);
        dbManager.open();
        Cursor cursor = dbManager.getAllLocations();

        listView = (ListView) findViewById(R.id.list_view);

        adapter = new CustomCursorAdapter(this, cursor);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }



    }
