package zaba.constituent;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ericz on 5/31/2017.
 */

public class CongressCustomList extends ArrayAdapter<String> {

    private ArrayList<Congressman> members;
    private Activity context;
    public CongressCustomList(Context context, ArrayList<Congressman> members)
    {


        super(context, R.layout.congressman_list_single);
        this.context = (Activity) getContext();
        this.members = members;
    }

    @Override
    public int getCount()
    {

        return 4;
    }

    @NonNull
    public View getView(int position, View rowView, ViewGroup parent)
    {

        LayoutInflater inflater = context.getLayoutInflater();

        ViewHolder holder;

        if (rowView ==null)
        {
            holder = new ViewHolder();
            rowView = inflater.inflate(R.layout.congressman_list_single, null, true);

            holder.nameText = (TextView) rowView.findViewById(R.id.congressmanName);
            holder.partyText = (TextView) rowView.findViewById(R.id.congressmanParty);
            holder.stateText = (TextView) rowView.findViewById(R.id.congressmanState);
            holder.profileImage = (ImageView) rowView.findViewById(R.id.congressmanImage);

            rowView.setTag(holder);

        }

        else {
            holder = (ViewHolder) rowView.getTag();

        }

        try {
            holder.nameText.setText(members.get(position).getName());

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return rowView;
    }

    static class ViewHolder
    {
        TextView nameText;
        TextView partyText;
        TextView stateText;
        ImageView profileImage;
    }



}
