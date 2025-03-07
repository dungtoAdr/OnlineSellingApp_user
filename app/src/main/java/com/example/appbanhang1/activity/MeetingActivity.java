package com.example.appbanhang1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.appbanhang1.R;
import com.example.appbanhang1.retrofit.ApiBanHang;
import com.example.appbanhang1.retrofit.RetrofitClient;
import com.example.appbanhang1.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.VideoSDK;
import live.videosdk.rtc.android.listeners.MeetingEventListener;

public class MeetingActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiBanHang apiBanHang;
    private Meeting meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        getMeetingIdFromSever();
    }

    private void getMeetingIdFromSever() {
        compositeDisposable.add(apiBanHang.getMeeting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meetingModel -> {
                            if(meetingModel.isSuccess()){
                                String token=meetingModel.getResult().get(0).getToken();
                                String meetingId=meetingModel.getResult().get(0).getMeetingId();
                                String mode = "VIEWER";
                                boolean streamEnable = mode.equals("CONFERENCE");
                                // initialize VideoSDK
                                VideoSDK.initialize(getApplicationContext());

                                // Configuration VideoSDK with Token
                                VideoSDK.config(token);

                                // Initialize VideoSDK Meeting
                                meeting = VideoSDK.initMeeting(
                                        MeetingActivity.this, meetingId, "App ban hang",
                                        streamEnable, streamEnable, null, mode, false, null);

                                // join Meeting
                                meeting.join();

                                meeting.addEventListener(new MeetingEventListener() {
                                    @Override
                                    public void onMeetingJoined() {
                                        if (meeting != null) {
                                            if (mode.equals("VIEWER")) {
                                                getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.mainLayout, new ViewerFragment(), "viewerFragment")
                                                        .commit();
                                            }
                                        }
                                    }
                                });
                            }
                        },throwable -> {
                            Log.d("logg",throwable.getMessage());
                        }
                ));
    }
    public Meeting getMeeting() {
        return meeting;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}