package zaba.constituent;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ericz on 5/26/2017.
 */

public class TwitterCustomList extends ArrayAdapter<String> {

    private Activity context;
    private String[] items;
    private Integer[] images;

    public TwitterCustomList(Activity contextArg, String[] itemsArg, Integer[] imagesArg) {
        super(contextArg, R.layout.twitter_list);
        this.context = contextArg;
        this.items = itemsArg;
        this.images = imagesArg;
    }

    public int getCount() {
        return this.items.length;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);


        return rowView;
    }


}
