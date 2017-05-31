package zaba.constituent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ericz on 5/26/2017.
 */

public class SettingsCustomlist extends ArrayAdapter<String>
{
    private Context context;
    private String[] items;
    private Integer[] images;
    public SettingsCustomlist(Context contextArg, String[] itemsArg, Integer[] imagesArg)
    {
        super(contextArg, R.layout.settings_list_single);
        this.context =  contextArg;
        this.items = itemsArg;
        this.images = imagesArg;
    }

    public int getCount()
    {

        return items.length;
        }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.settings_list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.settingsItem);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.settingsImage);
        txtTitle.setText(items[position]);

        imageView.setImageResource(images[position]);
        return rowView;
    }




}

