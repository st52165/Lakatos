package cz.pikadorama.lakatos.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.pikadorama.lakatos.R;
import cz.pikadorama.lakatos.app.Sound;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Sound> sounds = new ArrayList<Sound>();

    private Context context;
    private Intent intent;

    public WidgetFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        if (widgetId != -1) {
            SharedPreferences preferences = context.getSharedPreferences(Widget.getKey(widgetId), Context.MODE_PRIVATE);
            String restoredText = preferences.getString(Widget.SELECTED_ITEMS_PARAM, null);

            if (restoredText != null && !restoredText.isEmpty()) {
                String[] ids = restoredText.split(",");

                for (String id : ids) {
                    sounds.add(Sound.values()[Integer.parseInt(id)]);
                }
            } else {
                sounds.addAll(Arrays.asList(Sound.values()));
            }
        } else {
            sounds.addAll(Arrays.asList(Sound.values()));
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return sounds.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.grid_view_item);
        remoteView.setTextViewText(R.id.grid_item_label, sounds.get(position).getMessage());

        // set onclick listener
        Bundle extras = new Bundle();
        extras.putInt(Widget.ACTION_ITEM_PARAM, sounds.get(position).ordinal());
        Intent listenerIntent = new Intent();
        listenerIntent.putExtras(extras);
        remoteView.setOnClickFillInIntent(R.id.grid_item_label, listenerIntent);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
