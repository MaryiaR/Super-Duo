package barqsoft.footballscores.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import barqsoft.footballscores.R;
import barqsoft.footballscores.fragment.MainPagerFragment;

public class MainActivity extends AppCompatActivity {
    public static final String ARG_CURRENT_PAGE = "current_page";
    public static final String ARG_SELECTED_MATCH = "selected_match";
    public static final String ARG_PAGER_FRAGMENT = "pager_fragment";

    private int selectedMatchId;
    private int currentPagerItem = 2;
    private MainPagerFragment pagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent.hasExtra(ARG_CURRENT_PAGE) && intent.hasExtra(ARG_SELECTED_MATCH)) {
            currentPagerItem = intent.getIntExtra(ARG_CURRENT_PAGE, 0);
            selectedMatchId = intent.getIntExtra(ARG_SELECTED_MATCH, 0);
        }
        if (savedInstanceState == null) {
            pagerFragment = new MainPagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, pagerFragment)
                    .commit();
        } else {
            currentPagerItem = savedInstanceState.getInt(ARG_CURRENT_PAGE);
            selectedMatchId = savedInstanceState.getInt(ARG_SELECTED_MATCH);
            pagerFragment = (MainPagerFragment) getSupportFragmentManager().getFragment(savedInstanceState, ARG_PAGER_FRAGMENT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getCurrentPagerItem() {
        return currentPagerItem;
    }

    public int getSelectedMatchId() {
        return selectedMatchId;
    }

    public void setSelectedMatchId(int selectedMatchId) {
        this.selectedMatchId = selectedMatchId;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_CURRENT_PAGE, pagerFragment.getCurrentItem());
        outState.putInt(ARG_SELECTED_MATCH, selectedMatchId);
        getSupportFragmentManager().putFragment(outState, ARG_PAGER_FRAGMENT, pagerFragment);
        super.onSaveInstanceState(outState);
    }

}
