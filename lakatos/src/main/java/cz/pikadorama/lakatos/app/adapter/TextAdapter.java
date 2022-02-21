package cz.pikadorama.lakatos.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cz.pikadorama.lakatos.R;

public class TextAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> messages;

    public TextAdapter(Context context, List<String> messages) {
        this.context = context;
        this.messages = messages;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        @SuppressLint({"ViewHolder", "InflateParams"}) View gridView = inflater.inflate(R.layout.grid_view_item, null);
        TextView textView = (TextView) gridView
                .findViewById(R.id.grid_item_label);
        textView.setText(messages.get(position));

        return gridView;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
