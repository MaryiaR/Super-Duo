package barqsoft.footballscores.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.widget.WidgetDataProvider;


public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        WidgetDataProvider dataProvider = new WidgetDataProvider(getApplicationContext());
        return dataProvider;
    }

}