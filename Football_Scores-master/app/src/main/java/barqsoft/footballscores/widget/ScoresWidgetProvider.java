package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;
import barqsoft.footballscores.activity.MainActivity;
import barqsoft.footballscores.service.WidgetService;


public class ScoresWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_OPEN_DETAILS = "barqsoft.footballscores.widget.ACTION_OPEN_DETAILS";
    private static final String ACTION_REFRESH = "barqsoft.footballscores.widget.ACTION_REFRESH";
    public static final String EXTRA_MATCH_ID = "barqsoft.footballscores.widget.EXTRA_MATCH_ID";
    private final int PAGE_TODAY = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_OPEN_DETAILS)) {
            int selectedMatchId = intent.getExtras().getInt(EXTRA_MATCH_ID);
            Intent intentMain = new Intent(context, MainActivity.class);
            intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intentMain.putExtra(MainActivity.ARG_CURRENT_PAGE, PAGE_TODAY);
            intentMain.putExtra(MainActivity.ARG_SELECTED_MATCH, selectedMatchId);

            context.startActivity(intentMain);
        } else if (intent.getAction().equals(ACTION_REFRESH)) {
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, ScoresWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.scores_list);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, widgetId);

            final Intent onItemClick = new Intent(context, ScoresWidgetProvider.class);
            onItemClick.setAction(ACTION_OPEN_DETAILS);
            onItemClick.setData(Uri.parse(onItemClick.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0,
                    onItemClick, PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.scores_list, onClickPendingIntent);

            appWidgetManager.updateAppWidget(widgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews initViews(Context context, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.scores_list, intent);

        final Intent refreshIntent = new Intent(context, ScoresWidgetProvider.class);
        refreshIntent.setAction(ScoresWidgetProvider.ACTION_REFRESH);
        final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mView.setOnClickPendingIntent(R.id.btn_refresh, refreshPendingIntent);

        return mView;
    }

}