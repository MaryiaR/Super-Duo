package barqsoft.footballscores.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import barqsoft.footballscores.R;

/**
 * Created by yehya khaled on 2/26/2015.
 */
public class ViewHolder {
    public TextView tvHomeName;
    public TextView tvAwayName;
    public TextView tvScore;
    public TextView tvDate;
    public ImageView ivHomeCrest;
    public ImageView ivAwayCrest;
    public double matchId;

    public ViewHolder(View view) {
        tvHomeName = (TextView) view.findViewById(R.id.home_name);
        tvAwayName = (TextView) view.findViewById(R.id.away_name);
        tvScore = (TextView) view.findViewById(R.id.score_textview);
        tvDate = (TextView) view.findViewById(R.id.data_textview);
        ivHomeCrest = (ImageView) view.findViewById(R.id.home_crest);
        ivAwayCrest = (ImageView) view.findViewById(R.id.away_crest);
    }
}
