package com.yuxi.mlauncher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        setListener();
    }

    private void setListener() {
        appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packName = apps.get(position).activityInfo.packageName;
                PackageManager packageManager = MLauncherActivity.this.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage(packName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MLauncherActivity.this.startActivity(intent);
            }
        });
    }

    public void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        new ImageView(this);

        apps = getPackageManager().queryIntentActivities(intent, 0);
        appList.setAdapter(new AppAdapter());
    }

    private void initView() {
        appList = (GridView) findViewById(R.id.app_list);
        loadApps();
    }

    private class AppAdapter extends BaseAdapter {

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
            convertView = getLayoutInflater().inflate(R.layout.layout_app_item, null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.app_icon);
            TextView name = (TextView) convertView.findViewById(R.id.app_name);
            ResolveInfo info = apps.get(position);
            icon.setImageDrawable((info.activityInfo.loadIcon(getPackageManager())));
            name.setText(info.activityInfo.loadLabel(getPackageManager()));
            return convertView;
        }

    }
}
