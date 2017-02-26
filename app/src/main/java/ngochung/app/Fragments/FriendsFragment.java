package ngochung.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ngochung.app.Adapters.ViewPagerAdapter;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by NGOCHUNG on 2/17/2017.
 */

public class FriendsFragment extends Fragment {
    Context mContext;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity();
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
       viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               adapter.mFragmentList.get(position).onResume();
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ListFriendFragment(), mContext.getResources().getString(R.string.friend_list));
        adapter.addFragment(new InvitationFragment(), mContext.getResources().getString(R.string.friend_request));
        viewPager.setAdapter(adapter);

    }
    public void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();

    }


}
