package button.prg.com.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragNav extends Fragment {
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private AdapterNav adapterNav;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_frag_nav, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.redraw);
        AdapterNav adapter = new AdapterNav(getActivity(), postdata());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void install(DrawerLayout draw, final Toolbar toolbar) {
        DrawerLayout mDrawerLayout = draw;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), draw, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        //ایجاد ایکوندر تولبار
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                //ترسیم آیکون با این دستور
                mActionBarDrawerToggle.syncState();


            }
        });

    }

    //تعداد لیست ها و آیکون هارو مشخص میکنیم
    private List<InfoNav> postdata() {
        List<InfoNav> data = new ArrayList<>();
        String title[] = {"جستجو", "علاقه مندی ها", "تنظیمات", "منابع", "نظر به برنامه", "خروج"};
        int iconid[] = {R.drawable.ic_magnify_black_48dp, R.drawable.ic_favorite, R.drawable.ic_settings_black_48dp, R.drawable.ic_web_black_48dp, R.drawable.ic_comment_processing_grey600_48dp, R.drawable.ic_exit_to_app_black_48dp};
        for (int i = 0; i < title.length && i < iconid.length; i++) {
            InfoNav infoNav = new InfoNav();
            infoNav.title = title[i];
            infoNav.iconId = iconid[i];
            data.add(infoNav);
        }


        return data;
    }
}
