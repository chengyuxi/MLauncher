package com.yuxi.mlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MLauncherActivity extends Activity {
    private GridView appList;
    private List<ResolveInfo> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlauncher);
        initView();
    }

    public void loadApps(){
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        new ImageView(this);

        apps = getPackageManager().queryIntentActivities(intent,0);
        appList.setAdapter(new AppAdapter());
    }

    private void initView() {
        appList = (GridView) findViewById(R.id.app_list);
        loadApps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mlauncher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AppAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int position) {
            return apps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.layout_app_item,null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.app_icon);
            TextView name = (TextView) convertView.findViewById(R.id.app_name);
            ResolveInfo info = apps.get(position);
            icon.setBackground(info.activityInfo.loadIcon(getPackageManager()));
            name.setText(info.activityInfo.loadLabel(getPackageManager()));
            return convertView;
        }

    }
}
