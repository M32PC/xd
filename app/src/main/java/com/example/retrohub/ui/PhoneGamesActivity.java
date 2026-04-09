package com.example.retrohub.ui;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrohub.R;
import com.example.retrohub.model.PhoneGameItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PhoneGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_games);

        RecyclerView recyclerView = findViewById(R.id.phoneGamesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        List<PhoneGameItem> installedApps = loadInstalledApps();
        PhoneGamesAdapter adapter = new PhoneGamesAdapter(installedApps, this::launchApp);
        recyclerView.setAdapter(adapter);
    }

    private List<PhoneGameItem> loadInstalledApps() {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<PhoneGameItem> out = new ArrayList<>();
        for (ApplicationInfo app : apps) {
            if (pm.getLaunchIntentForPackage(app.packageName) == null) {
                continue;
            }
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                continue;
            }
            out.add(new PhoneGameItem(
                    pm.getApplicationLabel(app).toString(),
                    app.packageName,
                    pm.getApplicationIcon(app)
            ));
        }
        out.sort(Comparator.comparing(PhoneGameItem::getAppName, String.CASE_INSENSITIVE_ORDER));
        return out;
    }

    private void launchApp(PhoneGameItem item) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(item.getPackageName());
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
    }
}
