package com.kwaijian.facility.UI.BaseClass.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import didikee.com.permissionshelper.PermissionsHelper;
import didikee.com.permissionshelper.permission.DangerousPermissions;

/**
 * Created by LooooG on 2017/10/12.
 * 权限管理
 */
public class PermissionsActivity extends AppCompatActivity {

    // app所需要的全部危险权限
    static final String[] PERMISSIONS = new String[]{
            // DangerousPermissions.CALENDAR,
            // DangerousPermissions.CAMERA,
            // DangerousPermissions.CONTACTS,
//            DangerousPermissions.LOCATION,
            // DangerousPermissions.MICROPHONE,
//            DangerousPermissions.PHONE,
            DangerousPermissions.STORAGE,
            // DangerousPermissions.SENSORS,
            // DangerousPermissions.SMS
    };
    private PermissionsHelper permissionsHelper;


    /**
     * 检查权限
     */
    public void checkPermissions() {
        permissionsHelper = new PermissionsHelper(this, PERMISSIONS, true);
        if (permissionsHelper.checkAllPermissions(PERMISSIONS)) {
            permissionsHelper.onDestroy();
            // onAllNeedPermissionsGranted
            Log.d("PermissionsActivity", "Permissions onAllNeedPermissionsGranted");
        } else {
            //申请权限
            permissionsHelper.startRequestNeedPermissions();
        }
        permissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {
            @Override
            public void onAllNeedPermissionsGranted() {
                Log.d("PermissionsActivity", "Permissions onAllNeedPermissionsGranted");
            }

            @Override
            public void onPermissionsDenied() {
                Log.d("PermissionsActivity", "Permissions onPermissionsDenied");
            }

            @Override
            public void hasLockForever() {
                Log.d("PermissionsActivity", "Permissions hasLockForever");
            }

            @Override
            public void onBeforeRequestFinalPermissions(PermissionsHelper helper) {
                Log.d("PermissionsActivity", "Permissions onBeforeRequestFinalPermissions");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionsHelper.onActivityResult(requestCode, resultCode, data);
    }
}
