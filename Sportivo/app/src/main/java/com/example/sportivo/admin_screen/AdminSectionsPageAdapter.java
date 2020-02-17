package com.example.sportivo.admin_screen;

import android.content.Context;

import com.example.sportivo.R;
import com.example.sportivo.start_screen.SportsListFragment;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdminSectionsPageAdapter extends FragmentPagerAdapter {

        @StringRes
        private static final int[] TAB_TITLES = new int[]{R.string.admin_tab_text_view_reservations, R.string.admin_tab_text_add_a_reservation};
        private final Context mContext;

        public AdminSectionsPageAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position){
                case 0:
                    fragment=new AdminViewReservationsFragment();
                    break;
                case 1:
                    fragment=new SportsListFragment();
                    break;
            }
            return fragment;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }

}
