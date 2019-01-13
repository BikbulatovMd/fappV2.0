package test.fragment.me.fragmenttest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.List;

import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.fragments.InfoFrag;
import test.fragment.me.fragmenttest.fragments.ContactsFrag;
import test.fragment.me.fragmenttest.fragments.MapsFrag;
import test.fragment.me.fragmenttest.fragments.PhotosFrag;
import test.fragment.me.fragmenttest.fragments.RepositoryFrag;
import test.fragment.me.fragmenttest.utils.App;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private SectionsAdapter mySectionsAdapter;
//    private ViewPager myViewPager;

    private TextView txtLogin;

    FragNavController fragNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorGrey), 0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragNavController = new FragNavController(getSupportFragmentManager(), R.id.drawer_container);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RepositoryFrag()); //TAB1
        fragmentList.add(new ContactsFrag()); //TAB2
        fragmentList.add(new InfoFrag()); //TAB3
        fragmentList.add(new PhotosFrag()); //TAB4
        fragmentList.add(new MapsFrag()); //TAB5
        fragNavController.setRootFragments(fragmentList);
        fragNavController.setFragmentHideStrategy(FragNavController.HIDE);
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState);


//        mySectionsAdapter = new SectionsAdapter(getSupportFragmentManager());
//        myViewPager = findViewById(R.id.container);
//
//        setupViewPager(myViewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_repositories) {
            fragNavController.switchTab(FragNavController.TAB1);
//            myViewPager.setCurrentItem(0);
        } else if (id == R.id.nav_contacts) {
            fragNavController.switchTab(FragNavController.TAB2);
//            myViewPager.setCurrentItem(1);
        } else if (id == R.id.nav_info) {
            fragNavController.switchTab(FragNavController.TAB3);
//            myViewPager.setCurrentItem(2);
        } else if (id == R.id.nav_photos) {
            fragNavController.switchTab(FragNavController.TAB4);
        } else if (id == R.id.nav_maps){
            fragNavController.switchTab(FragNavController.TAB5);
        } else if (id == R.id.nav_logout){
            Intent intent = new Intent(this, ActivityVhod.class);
            App.setAccessToken(null);
            App.setAuthNetClient();
            startActivity(intent);
            finishAffinity();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void setupViewPager(ViewPager viewPager){
//        SectionsAdapter adapter = new SectionsAdapter(getSupportFragmentManager());
//        adapter.addFragment(new ContactsFrag(), "Fragment 2");
//        adapter.addFragment(new RepositoryFrag(), "Fragment 3");
//        adapter.addFragment(new InfoFrag(), "Fragment 1");
//        viewPager.setAdapter(adapter);
//    }

}
