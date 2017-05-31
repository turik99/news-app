package zaba.constituent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class SettingsActivity extends AppCompatActivity {

    private ListView listView;
    private String[] items = {
            "News Settings",
            "Reddit Settings",
            "Twitter Settings",
    };

    private Integer[] imageId = {
            R.drawable.newspaper,
            R.drawable.reddit,
            R.drawable.twitter
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final View rootView = getWindow().getDecorView().getRootView();
        SettingsCustomlist adapter = new
                SettingsCustomlist(this, this.items, this.imageId);
        listView = (ListView) rootView.findViewById(R.id.settingsListview1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0)
                {
                    Intent intent = new Intent(getApplicationContext(), NewsSettings.class);
                    startActivity(intent);
                }

                if (position == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), RedditSettings.class);
                    startActivity(intent);

                }
                if (position == 2)
                {
                    Intent intent = new Intent(getApplicationContext(), TwitterSettings.class);
                    startActivity(intent);

                }
                if (position == 3 )
                {
                    Intent intent = new Intent(getApplicationContext(), BiasSettings.class);
                    startActivity(intent);

                }

                

            }
        });



    }






}
