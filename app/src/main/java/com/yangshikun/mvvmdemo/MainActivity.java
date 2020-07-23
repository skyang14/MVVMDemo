package com.yangshikun.mvvmdemo;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ToastUtils;
import com.yangshikun.mvvmdemo.base.BaseActivity;
import com.yangshikun.mvvmdemo.databinding.ActivityMainBinding;
import com.yangshikun.mvvmdemo.fragment.main.MainFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel>{
    private static final String TAG = "MainActivity";
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.READ_SYNC_SETTINGS, Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
    };
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int viewModelId() {
        return BR.viewModel;
    }




    private void checkPermissions() {
        for (String permission : BASIC_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, BASIC_PERMISSIONS, 111);
                break;
            }
        }
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        checkPermissions();
       // fragment 展示位置就在fragment_container_frame_layout 这个id对应的frameLayout里
        loadRootFragment(R.id.fragment_container_frame_layout,new MainFragment());
        viewModel.getItemClickEvent().observe(this, item -> {
            dataBinding.mainTitleRecycleView.getAdapter().notifyDataSetChanged();
            ToastUtils.showShort(item.getTitle());
        });
    }
}
