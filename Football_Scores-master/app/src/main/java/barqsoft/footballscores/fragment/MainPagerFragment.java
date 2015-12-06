package barqsoft.footballscores.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.activity.MainActivity;

/**
 * Created by yehya khaled on 2/27/2015.
 */
public class MainPagerFragment extends Fragment {
    private Calendar calendar = Calendar.getInstance();
    public static final int NUM_PAGES = 5;
    public static final int HOURS_24 = 86400000;
    public ViewPager viewPager;
    private DayPagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_fragment, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);

        List<DayPageFragment> fragments = new ArrayList<>();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        for (int i = 0; i < NUM_PAGES; i++) {
            DayPageFragment fragment = new DayPageFragment();
            fragment.setFragmentDate(Utilies.sdf.format(calendar.getTime()));
            fragments.add(fragment);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        mPagerAdapter = new DayPagerAdapter(getActivity(), getChildFragmentManager(), fragments);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setCurrentItem(((MainActivity) getActivity()).getCurrentPagerItem());
        return rootView;
    }


    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    private static class DayPagerAdapter extends FragmentStatePagerAdapter {

        private Context context;
        private List<DayPageFragment> fragments = new ArrayList<>();

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public DayPagerAdapter(Context context, FragmentManager fm, List<DayPageFragment> fragments) {
            super(fm);
            this.fragments = fragments;
            this.context = context;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return getDayName(System.currentTimeMillis() + ((position - 2) * HOURS_24));
        }

        public String getDayName(long dateInMillis) {
            // If the tvDate is today, return the localized version of "Today" instead of the actual
            // day name.

            Time t = new Time();
            t.setToNow();
            int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
            int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
            if (julianDay == currentJulianDay) {
                return context.getString(R.string.today);
            } else if (julianDay == currentJulianDay + 1) {
                return context.getString(R.string.tomorrow);
            } else if (julianDay == currentJulianDay - 1) {
                return context.getString(R.string.yesterday);
            } else {
                Time time = new Time();
                time.setToNow();
                // Otherwise, the format is just the day of the week (e.g "Wednesday".

                return Utilies.DAY_OF_WEEK_FORMAT.format(dateInMillis);
            }
        }
    }
}
