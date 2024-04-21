package edu.cse470.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ContactSettingsActivity extends AppCompatActivity {

    private static final String TAG = "ContactSettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_settings);
        Log.d(TAG, "About to start settings activity");
        initListButton();
        initMapButton();
        initSettingsButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
    }

    private void initListButton() {
        ImageButton ib = (ImageButton) findViewById(R.id.listButton);
        ib.setOnClickListener(v -> {
            Intent intent = new Intent(ContactSettingsActivity.this, ContactListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initMapButton() {
        ImageButton ib = (ImageButton) findViewById(R.id.mapButton);
        ib.setOnClickListener(v -> {
            Intent intent = new Intent(ContactSettingsActivity.this, ContactMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initSettingsButton() {
        ImageButton ib = (ImageButton) findViewById(R.id.settingsButton);
        ib.setEnabled(false);
        Log.d(TAG, "The Setting Button is disabled");
    }

    private void initSettings() {
        String sortBy = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortfield", "name");
        String sortOrder = getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        getSharedPreferences("MyTestPreferences", Context.MODE_PRIVATE).edit() .putString("Greeting", "Hello").commit();
        RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
        RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
        RadioButton rbBirthDay = (RadioButton) findViewById(R.id.radioBirtday);
        if (sortBy.equalsIgnoreCase("name")) {
            rbName.setChecked(true);
        } else if (sortBy.equalsIgnoreCase("city")) {
            rbCity.setChecked(true);
        } else {
            rbBirthDay.setChecked(true);
        }

        RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
        RadioButton rbDescending = (RadioButton) findViewById(R.id.radioDescending);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        } else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = (RadioGroup) findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener((arg0, arg1) -> {
            RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
            RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
            if (rbName.isChecked()) {
                getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit() .putString("sortfield", "name").commit();
            }
            else if (rbCity.isChecked()) {
                getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "city").commit();
            }
            else {
                getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "birthday").commit();
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = (RadioGroup) findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener((arg0, arg1) -> {
            RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
            if (rbAscending.isChecked()) {
                getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").commit();
            }
            else {
                getSharedPreferences("MyContactListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").commit();
            }
        });
    }
}