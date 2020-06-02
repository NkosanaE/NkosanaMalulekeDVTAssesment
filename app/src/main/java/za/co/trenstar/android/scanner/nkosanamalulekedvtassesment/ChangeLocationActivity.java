package za.co.trenstar.android.scanner.nkosanamalulekedvtassesment;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.database.DbManager;
import za.co.trenstar.android.scanner.nkosanamalulekedvtassesment.helper.Helper;

public class ChangeLocationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addLocationButton;
    private EditText txtLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_change_location);

        txtLocation = (EditText) findViewById(R.id.txt_city);
        addLocationButton = (Button) findViewById(R.id.btn_change_location);


        addLocationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_location:

                final String CityName = txtLocation.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("City", CityName);
                setResult(Helper.GET_LOCATION_FOR_CITY, resultIntent);
                finish();
                break;
        }
    }


}
