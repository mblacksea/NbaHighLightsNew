package com.oxygenmobile.nbahighlights.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.oxygenmobile.nbahighlights.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0");
        String url = "https://play.google.com/store/apps/developer?id=Oxygen+Mobile";
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Element websiteElement = new Element();
        websiteElement.setTitle("Click");
        websiteElement.setIconDrawable(R.drawable.about_icon_link);
        websiteElement.setIconTint(R.color.about_item_icon_color);
        websiteElement.setValue(url);

        Uri uri = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

        websiteElement.setIntent(browserIntent);




        View aboutPage = new AboutPage(this)
                .setDescription("With this application, you will be able to watch the current NBA match highlights & top plays. In addition, " +
                        "the application will be developed step by step.")
                .isRTL(false)
                .setImage(R.drawable.rsz_web_hi_res_512)
                .addItem(versionElement)
                .addGroup("Other Applications")
                .addItem(websiteElement)
                .create();

        setContentView(aboutPage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
