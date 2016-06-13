package com.giroux.kevin.kevingirouxportfolio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.giroux.kevin.kevingirouxportfolio.R;

public class ShortCutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent.ShortcutIconResource icon = Intent.ShortcutIconResource.fromContext(this,R.mipmap.launcher );
        Intent intent = new Intent();
        Intent launchIntent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getApplicationContext().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
    }
}
