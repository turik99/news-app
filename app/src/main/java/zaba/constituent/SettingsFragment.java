package zaba.constituent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class SettingsFragment extends Fragment {

    private ListView listView;
    private String[] items = {
            "News",
            "Reddit",
            "Twitter",
            "Biased Sources"
    };

    private Integer[] imageId = {
            R.drawable.newspaper,
            R.drawable.reddit,
            R.drawable.twitter,
            R.drawable.bias
    };



    public SettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

         return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);



    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {


        SettingsCustomlist adapter = new
                SettingsCustomlist(this.getActivity(), this.items, this.imageId);
        listView=(ListView)view.findViewById(R.id.settingsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0)
                {
                    Intent intent = new Intent(getActivity(), NewsSettings.class);
                    startActivity(intent);
                }

                if (position == 1)
                {
                    Intent intent = new Intent(getActivity(), RedditSettings.class);
                    startActivity(intent);

                }
                if (position == 2)
                {
                    Intent intent = new Intent(getActivity(), TwitterSettings.class);
                    startActivity(intent);

                }
                if (position == 3 )
                {
                    Intent intent = new Intent(getActivity(), BiasSettings.class);
                    startActivity(intent);

                }

            }
        });


    }



}
