package zaba.constituent;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


        sharedPreferences = getSharedPreferences("My name", Context.MODE_PRIVATE);
        editor =  sharedPreferences.edit();

        nytBox = (CheckBox) findViewById(R.id.nytCheckBox);
        wapoBox = (CheckBox)findViewById(R.id.wapoCheckBox);
        wsjBox = (CheckBox) findViewById(R.id.wsjCheckBox);
        apBox = (CheckBox)findViewById(R.id.apCheckBox);
        bloombergBox = (CheckBox) findViewById(R.id.bloombergCheckBox);
        reutersBox = (CheckBox)findViewById(R.id.bloombergCheckBox);

        if (sharedPreferences.getBoolean("nyt", true))
        {
            nytBox.setChecked(true);
            editor.commit();
        }
        else {
            nytBox.setChecked(false);
            editor.commit();

        }


        if (sharedPreferences.getBoolean("wapo", true))
        {
            wapoBox.setChecked(true);
            editor.commit();

        }
        else {
            wapoBox.setChecked(false);
            editor.commit();

        }
        if(sharedPreferences.getBoolean("wsj", true)){
            wsjBox.setChecked(true);
            editor.commit();

        }
        else {
            wsjBox.setChecked(false);
            editor.commit();

        }
        if(sharedPreferences.getBoolean("bloomberg", true))
        {
            bloombergBox.setChecked(true);
            editor.commit();

        }
        else {
            bloombergBox.setChecked(false);
            editor.commit();

        }
        if(sharedPreferences.getBoolean("ap", true)){
            apBox.setChecked(true);
            editor.commit();

        }
        else {
            apBox.setChecked(false);
            editor.commit();

        }
        if(sharedPreferences.getBoolean("reuters", true))
        {
            reutersBox.setChecked(true);

        }
        else {
            reutersBox.setChecked(false);
            editor.commit();

        }

    }


    protected void nytClick(View view)
    {
        if (nytBox.isChecked())
        {
            editor.putBoolean("nyt", false);
            editor.commit();

        }
        else
        {
            editor.putBoolean("nyt", true);
            editor.commit();

        }
    }

    protected void wapoClick(View view)
    {
        if(wapoBox.isChecked())
        {
            editor.putBoolean("wapo", false);
            editor.commit();

        }
        else
        {
            editor.putBoolean("wapo", true);
            editor.commit();

        }


    }

    protected void wsjClick(View view)
    {
        if(wsjBox.isChecked())
        {
            editor.putBoolean("wsj", false);
            editor.commit();

        }
        else {
            editor.putBoolean("wsj", true);
            editor.commit();

        }
    }
    protected void apClick(View view)
    {
        if(apBox.isChecked())
        {
            editor.putBoolean("ap", false);
            editor.commit();

        }
        else {
            editor.putBoolean("ap", true);
            editor.commit();

        }
    }
    protected void bloombergClick(View view)
    {
        if(bloombergBox.isChecked())
        {
            editor.putBoolean("bloomberg", false);
            editor.commit();


        }

        else {
            editor.putBoolean("bloomberg", true);
        }
    }
    protected void reutersClick(View view)
    {
        if (reutersBox.isChecked())
        {
            editor.putBoolean("reuters", false);
        }
        else {
            editor.putBoolean("reuters", true);
        }
    }


}
