package a2dp.vol.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import a2dp.vol.A2DPApplication;
import a2dp.vol.R;

public class LocationActivity extends AppCompatActivity {

    String car_name;
    private A2DPApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        String st = getIntent().getStringExtra("filestr");
        final Uri uri = Uri.parse(st);
        car_name = getIntent().getStringExtra("name");
        this.application = (A2DPApplication) this.getApplication();

        // this was intended to create a home screen shortcut but I was not able to make it work.  For now I made the fab gone.
        // if you want to get this working you need to clear the visibility of the fab in activity_loc_viewer.xml
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
 */
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                //intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setData(uri.normalizeScheme());

                Intent shortcutIntent = new Intent();
                shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, car_name);
                shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, R.drawable.car2);
                shortcutIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(application, R.drawable.car2));
                shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
                shortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                shortcutIntent.putExtra("duplicate", false);

                sendBroadcast(shortcutIntent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myWebView.loadUrl(uri.toString());
    }

}
