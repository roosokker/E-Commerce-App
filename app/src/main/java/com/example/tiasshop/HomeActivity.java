package com.example.tiasshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView nav;
    AppBarLayout layout;
    ActionBarDrawerToggle toggle;
    ListView catview;
    ArrayAdapter<String> catadapter;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        catview = (ListView)findViewById(R.id.cat_view);
        catadapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1);
        layout = (AppBarLayout)findViewById(R.id.app_bar);
        //drawer = (DrawerLayout)findViewById(R.id.drawer);
        //nav = (NavigationView)findViewById(R.id.drawer_nav);
        //toggle = new ActionBarDrawerToggle(this , drawer,R.string.Open , R.string.Close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();
        //nav.setNavigationItemSelectedListener(navi);
    }

    public NavigationView.OnNavigationItemSelectedListener navi = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id = menuItem.getItemId();
            if(id == R.id.menu_Home)
            {
                Intent intent = new Intent(HomeActivity.this , HomeActivity.class);
                startActivity(intent);
                return true;
            }
            else if (id == R.id.menu_Logout)
            {
                Intent intent = new Intent(HomeActivity.this , MainActivity.class);
                startActivity(intent);
                return true;
            }
            else if (id == R.id.menu_Cat)
            {
                catview.setVisibility(View.VISIBLE);
                catview.setAdapter(catadapter);
                catadapter.add("Women Clothes");
                catadapter.add("Men Clothes");
                catadapter.add("Watches");
                catadapter.add("Bags");
                catadapter.add("Shoes");
                catadapter.add("Phones");
                catadapter.add("Make-Up");
                catadapter.add("Sports");
                catadapter.add("Cameras");
                return true;
            }
            return false;
        }
    };
}
