package zaba.constituent;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class NewsSettings extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private CheckBox nytBox;
    private CheckBox wapoBox;
    private CheckBox wsjBox;
    private CheckBox apBox;
    private CheckBox bloombergBox;
    private CheckBox reutersBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_settings);


        SharedPreferences sharedPreferences = getSharedPreferences("name", MODE_PRIVATE);

        editor = sharedPreferences.edit();
        nytBox = (CheckBox) findViewById(R.id.nytCheckBox);
        wapoBox = (CheckBox) findViewById(R.id.wapoCheckBox);
        wsjBox = (CheckBox) findViewById(R.id.wsjCheckBox);
        apBox = (CheckBox) findViewById(R.id.apCheckBox);
        bloombergBox = (CheckBox) findViewById(R.id.bloombergCheckBox);
        reutersBox = (CheckBox) findViewById(R.id.reutersCheckBox);

        nytBox.setChecked(sharedPreferences.getBoolean("nyt", true));
        wapoBox.setChecked(sharedPreferences.getBoolean("wapo", true));
        wsjBox.setChecked(sharedPreferences.getBoolean("wsj", true));
        apBox.setChecked(sharedPreferences.getBoolean("ap", true));
        bloombergBox.setChecked(sharedPreferences.getBoolean("bloomberg", true));
        reutersBox.setChecked(sharedPreferences.getBoolean("reuters", true));


    }

    public void nytClick(View view)
    {
        editor.putBoolean("nyt", nytBox.isChecked()).commit();

    }
    public void wapoClick(View view)
    {
       editor.putBoolean("wapo", wapoBox.isChecked()).commit();
    }
    public void wsjClick(View view)
    {
        editor.putBoolean("wsj", wsjBox.isChecked()).commit();

    }

    public void bloombergClick(View view)
    {
        editor.putBoolean("bloomberg", bloombergBox.isChecked()).commit();

    }

    public void reutersClick(View view)
    {
        editor.putBoolean("reuters", reutersBox.isChecked()).commit();

    }

    public void apClick(View view)
    {
        editor.putBoolean("ap", apBox.isChecked()).commit();


    }




}
