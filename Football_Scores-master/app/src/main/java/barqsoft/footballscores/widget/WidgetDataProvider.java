package barqsoft.footballscores.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.Date;

import barqsoft.footballscores.R;
import barqsoft.footballscores.ui.ScoresAdapter;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.db.DatabaseContract;


public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Cursor mCursor;

    private Context mContext = null;

    public WidgetDataProvider(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }


    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.widget_scores_item);

        double matchId = 0;

        if (mCursor.moveToPosition(position)) {
            matchId = mCursor.getDouble(ScoresAdapter.COL_ID);
            mView.setTextViewText(R.id.home_name, mCursor.getString(ScoresAdapter.COL_HOME));
            mView.setTextColor(R.id.home_name, Color.BLACK);
            mView.setTextViewText(R.id.away_name, mCursor.getString(ScoresAdapter.COL_AWAY));
            mView.setTextColor(R.id.away_name, Color.BLACK);
            mView.setTextViewText(R.id.data_textview, mCursor.getString(ScoresAdapter.COL_MATCHTIME));
            mView.setTextColor(R.id.data_textview, Color.BLACK);
            mView.setTextViewText(R.id.score_textview, Utilies.getScores(mCursor.getInt(ScoresAdapter.COL_HOME_GOALS), mCursor.getInt(ScoresAdapter.COL_AWAY_GOALS)));
            mView.setTextColor(R.id.score_textview, Color.BLACK);
        }

        final Intent intent = new Intent();
        intent.setAction(ScoresWidgetProvider.ACTION_OPEN_DETAILS);
        final Bundle bundle = new Bundle();
        bundle.putInt(ScoresWidgetProvider.EXTRA_MATCH_ID, (int) matchId);
        intent.putExtras(bundle);
        mView.setOnClickFillInIntent(R.id.root, intent);

        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(DatabaseContract.ScoresTable.buildScoreWithDate(), null, null,
                new String[]{Utilies.sdf.format(new Date())}, null);
    }


    @Override
    public void onDestroy() {

    }

}