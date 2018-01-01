package com.oxygenmobile.nbahighlights.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.configuration.Config;

public class YoutubeVideoDisplayActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private YouTubePlayerView youTubeView;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private String gameVideoId;
    private InterstitialAd interstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_youtube_video_display);

        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_DEVELOPER_KEY, this);
        Intent i = getIntent();
        gameVideoId = i.getStringExtra("videoID");

        Log.e("videoID", gameVideoId);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            //  adUtils = new InterstitalAdUtils(getApplicationContext());
            String REKLAM_ID = "ca-app-pub-7577190809228817/1596471172";
            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId(REKLAM_ID);

            AdRequest adRequest = new AdRequest.Builder().build();
            interstitial.loadAd(adRequest);


            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (interstitial.isLoaded()) {
                        interstitial.show();
                    }
                }
            });

            youTubePlayer.loadVideo(gameVideoId);
            youTubePlayer.setFullscreen(true);


        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
         /*   String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());*/
            //Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }


}
