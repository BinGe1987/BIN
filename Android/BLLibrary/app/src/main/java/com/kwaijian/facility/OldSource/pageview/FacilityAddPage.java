package com.kwaijian.facility.OldSource.pageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kwaijian.facility.DataCenter.Home.DataObj.Spare;
import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.OldSource.tools.BitmapManager;
import com.kwaijian.facility.OldSource.tools.Density;
import com.kwaijian.facility.OldSource.tools.LoadImageManager;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.tools.Utils;
import com.kwaijian.facility.OldSource.widget.AddImageDrawable;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
import com.kwaijian.facility.OldSource.widget.CustomDialog.ReturnResults;
import com.kwaijian.facility.OldSource.widget.FacilityImageView;
import com.kwaijian.facility.OldSource.widget.PickImageUploadDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

public class FacilityAddPage extends Page implements RequestCallback {
    public static final int ADD_SPAREPARTS = 10;
    public static final int UPDATE_SPAREPART = 11;
    public static final String FACILITY_DETAIL = "facilityDetail";
    private Handler mHandler;
    private String httpFlag;
    private StringBuilder imageId;
    private boolean isAddSparepart;
    private String deletedId;
    private String imageUrl;
    private String facilityId;

    private List<String> mImageList = new ArrayList<>();
    private Handler mChildThread;

    public FacilityAddPage(final Context context) {
        super(context);
        HandlerThread ht = new HandlerThread("image-thread");
        ht.start();
        mChildThread = new Handler(ht.getLooper());
        mHandler = new Handler();
        setContentView(R.layout.page_facility_add);
        initView(context);
    }

    private void initView(final Context context) {
        if (getBundle() != null) {
            ((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.update_device));
            initFacilityDetail(context, getBundle());
        } else {
            ((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.add_device));
        }

        findViewById(R.id.add_device_sparepart).setOnClickListener(this);

        ((TextView) findViewById(R.id.save)).setText("保存");
        findViewById(R.id.right_title_layout).setOnClickListener(this);

        ImageView image = (ImageView) findViewById(R.id.add_image);
        image.setImageDrawable(new AddImageDrawable());
        image.setOnClickListener(this);

        findViewById(R.id.add).setOnClickListener(this);
    }

    private void addOrUpdateFacility(final Context context) {
        if (Utils.isNetWork(context)) {
            final String name = ((EditText) findViewById(R.id.name)).getText().toString();
            final String manufacturer = ((EditText) findViewById(R.id.manufacturer)).getText().toString();
            final String model = ((EditText) findViewById(R.id.model)).getText().toString();
            final String controllerBrand = ((EditText) findViewById(R.id.controllerBrand)).getText().toString();
            final String remark = ((EditText) findViewById(R.id.remark)).getText().toString();
            final String SN = ((EditText) findViewById(R.id.SN)).getText().toString();
            if (checkParams(context, name, manufacturer, model, controllerBrand, SN)) {
                addOrUpdate(name, manufacturer, model, controllerBrand, remark, SN);
                CustomDialog.createLoadingDialog(context).show();
            } else {
                return;
            }
        } else {
            ToastUtils.show(context, "网络错误");
        }
    }

    private void initFacilityDetail(Context context, Bundle bundle) {
        final Facility facility = (Facility) bundle.getSerializable(FACILITY_DETAIL);
        ((EditText) findViewById(R.id.name)).setText(facility.getName());
        ((EditText) findViewById(R.id.manufacturer)).setText(facility.getManufacturer());
        ((EditText) findViewById(R.id.model)).setText(facility.getModel());
        ((EditText) findViewById(R.id.remark)).setText(facility.getRemark());
        ((EditText) findViewById(R.id.controllerBrand)).setText(facility.getControllerBrand());
        ((EditText) findViewById(R.id.SN)).setText(facility.getfSN());
        LinearLayout rootView = (LinearLayout) findViewById(R.id.container);
        for (int i = 0; i < facility.getSpareparts().size(); i++) {
            initSparepartList(rootView, facility.getSpareparts().get(i));
        }
        File sdDir = new File(Environment.getExternalStorageDirectory(), "Facility/image/");
        if (!sdDir.exists()) {
            sdDir.mkdirs();
        }
//		CustomDialog.createLoadingDialog(context).show();
        for (int i = 0; i < facility.getImageId().length; i++) {
            if (TextUtils.isEmpty(facility.getImageId()[i])) {
                continue;
            }
            if (imageId == null) {
                imageId = new StringBuilder();
                imageId.append(facility.getImageId()[i]);
            } else {
                imageId.append("," + facility.getImageId()[i]);
            }
//            final File image = new File(sdDir, facility.getImageId()[i] + ".png");
//            if (image.exists()) {
//                LoadImageManager.addImage(getContext(), image.toString(), (ViewGroup) findViewById(R.id.add_image).getParent());
//            } else {
//                httpFlag = HttpConst.Request.DOWNLOAD_IMAGE;
//                final String id = facility.getImageId()[i];
//                new Thread(new Runnable() {
//                    public void run() {
//                        HttpRequest request = new HttpRequest(FacilityAddPage.this);
//                        request.downloadImage(id, image);
//                    }
//                }).start();
//
//            }
            showImageView(facility.getImageId()[i], facility.getImageId()[i]);
        }
        if (!TextUtils.isEmpty(imageId)) {
            findViewById(R.id.add_image).setVisibility(View.GONE);
            findViewById(R.id.add).setVisibility(View.VISIBLE);
        }
    }

    private void showImageView(final String imgId, final String tag) {
        mChildThread.post(new Runnable() {
            @Override
            public void run() {
                String url = HttpConst.Request.IMAGE_PATH + imgId;
                File folder = new File(Environment.getExternalStorageDirectory(), "Facility/image");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                final String imagePath = Environment.getExternalStorageDirectory() + "/Facility/image/" + imgId + ".png";
                if (HttpRequest.downloadImage(url, imagePath)) {
                    LogUtils.d("下载成功：" + imagePath);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            final LinearLayout parent = (LinearLayout) findViewById(R.id.add_image).getParent();
                            FacilityImageView imageView = (FacilityImageView) parent.findViewWithTag(tag);
                            if (imageView == null) {
                                imageView = new FacilityImageView(getContext());
                                parent.addView(imageView, 0);
                            }
                            imageView.setImageId(imgId);
                            imageView.setTag(imagePath);
                            Picasso.with(getContext()).load(new File(imagePath))
                                    .resize(Density.dip2px(100), Density.dip2px(100))
                                    .centerCrop()
                                    .into(imageView.getImageView());
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String path = (String) view.getTag();
                                    LoadImageManager.openImageFile(getContext(), path);
                                }
                            });
                            imageView.setOnDeleteClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    View v = parent.findViewWithTag(imagePath);
                                    if (v != null) {
                                        parent.removeView(v);
                                    }
                                    if (imageId != null) {
                                        String[] ids = imageId.toString().split(",");
                                        if (ids != null) {
                                            imageId = new StringBuilder();
                                            for (String id : ids) {
                                                if (!id.equals(imgId)) {
                                                    if (TextUtils.isEmpty(imageId.toString())) {
                                                        imageId.append(id);
                                                    } else {
                                                        imageId.append(",");
                                                        imageId.append(id);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (imageId == null || TextUtils.isEmpty(imageId.toString())) {
                                        findViewById(R.id.add_image).setVisibility(View.VISIBLE);
                                        findViewById(R.id.add).setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    });
                } else {
                    LogUtils.d("下载失败：" + imagePath);
                }
            }
        });
    }

    private boolean checkParams(Context context, String name, String manufacturer, String model,
                                String controllerBrand, String SN) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show(context, "设备名不能为空");
            return false;
        } else if (TextUtils.isEmpty(manufacturer)) {
            ToastUtils.show(context, "生成厂家不能为空");
            return false;
        } else if (TextUtils.isEmpty(model)) {
            ToastUtils.show(context, "设备型号不能为空");
            return false;
        } else if (TextUtils.isEmpty(controllerBrand)) {
            ToastUtils.show(context, "控制器品牌不能为空");
            return false;
        } else if (TextUtils.isEmpty(SN)) {
            ToastUtils.show(context, "请输入设备序列号");
            return false;
        }
        if (!(checkParamsLength(name) && checkParamsLength(manufacturer) && checkParamsLength(model)
                && checkParamsLength(controllerBrand))) {
            ToastUtils.show(context, "长度不合法(2~50字符)");
            return false;
        }
        return true;
    }

    private boolean checkParamsLength(String param) {
        if (param.length() > 50 || param.length() < 2) {
            return false;
        } else {
            return true;
        }
    }

    private void addOrUpdate(final String name, final String manufacturer, final String model,
                             final String controllerBrand, final String remark, final String SN) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequest request = new HttpRequest(FacilityAddPage.this);
                Facility facility;
                if (imageId != null) {
                    facility = new Facility(name, manufacturer, model, controllerBrand, imageId.toString().split(","),
                            remark);
                } else {
                    facility = new Facility(name, manufacturer, model, controllerBrand, null, remark);
                }
                facility.setfSN(SN);
                try {
                    if (getBundle() != null) {
                        facility.setId(((Facility) getBundle().getSerializable(FACILITY_DETAIL)).getId());
                        JSONObject json = facility.facilityToJson();
                        JSONObject finalJson = new JSONObject().put("facility", json);
                        httpFlag = HttpConst.Request.UPDATE_FACILITY;
                        request.postRequest(HttpConst.Request.UPDATE_FACILITY, finalJson);
                    } else {
                        JSONObject json = facility.facilityToJson();
                        if (httpFlag == HttpConst.Request.ADD_FACILITY || httpFlag == HttpConst.Request.UPDATE_SPAREPARTS
                                || httpFlag == HttpConst.Request.UPDATE_FACILITY) {
                            httpFlag = HttpConst.Request.UPDATE_FACILITY;
                            json.put(HttpConst.Facility.ID, facilityId);
                            JSONObject finalJson = new JSONObject().put("facility", json);
                            LogUtils.d(finalJson);
                            request.postRequest(httpFlag, finalJson);
                        } else {
                            httpFlag = HttpConst.Request.ADD_FACILITY;
                            JSONObject finalJson = new JSONObject().put("facility", json);
                            request.postRequest(HttpConst.Request.ADD_FACILITY, finalJson);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!(resultCode == Activity.RESULT_OK)) {
            return;
        }
        String localUrl = "";
        if (requestCode == LoadImageManager.CHOOSE_IMAGE && data != null) {
            Uri uri = data.getData();
            String url = LoadImageManager.uriToUrl(getContext(), uri);
            localUrl = url;
        } else if (requestCode == LoadImageManager.IMAGE_CAPTURE) {
            File picture = new File(PickImageUploadDialog.getImagePath());
            if (picture.length() > 0) {
                localUrl = picture.getAbsolutePath();
            } else {
                localUrl = "";
                picture.delete();
            }
        } else if ((requestCode == ADD_SPAREPARTS || requestCode == UPDATE_SPAREPART) && data != null) {
            Bundle bundle = data.getExtras();
            LinearLayout rootView = (LinearLayout) findViewById(R.id.container);
            Spare sparepart = (Spare) bundle.getSerializable(SparepartAddPage.SPAREPART_DETAIL);
            initSparepartList(rootView, sparepart);
//			localUrl = "";
        }
        if (!localUrl.equals("")) {
            CustomDialog.createLoadingDialog(getContext()).show();
            uploadImage(localUrl);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initSparepartList(ViewGroup rootView, final Spare sparepart) {
        LinearLayout container;
        if (httpFlag != null && httpFlag.equals(HttpConst.Request.UPDATE_SPAREPARTS)) {
            container = (LinearLayout) rootView.findViewById(Integer.parseInt(sparepart.getId()));
            ((Spare) container.getTag()).setName(sparepart.getName());
            ((Spare) container.getTag()).setType(sparepart.getType());
//			((TextView) container.findViewById(R.id.name)).setText(sparepart.getName());
//			((TextView) container.findViewById(R.id.type)).setText(sparepart.getType());
            ((TextView) container.findViewById(R.id.name)).setText(sparepart.getModel());
            ((TextView) container.findViewById(R.id.type)).setText(sparepart.getNum());
        } else {
            container = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_sparepart, null);
//			((TextView) container.findViewById(R.id.name)).setText(sparepart.getName());
//			((TextView) container.findViewById(R.id.type)).setText(sparepart.getType());
            ((TextView) container.findViewById(R.id.name)).setText(sparepart.getModel());
            ((TextView) container.findViewById(R.id.type)).setText(sparepart.getNum());
            container.setId(Integer.parseInt(sparepart.getId()));
            container.setTag(sparepart);

            rootView.addView(container);
        }
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpFlag = HttpConst.Request.UPDATE_SPAREPARTS;
                Bundle b = new Bundle();
                b.putSerializable(SparepartAddPage.SPAREPART_DETAIL, sparepart);
                PageActivity.openPageForResult(getContext(), SparepartAddPage.class, b, UPDATE_SPAREPART);
            }
        });
        container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CustomDialog.createChooseDialog(getContext(), "是否删除该备件？", "", new ReturnResults() {
                    @Override
                    public void result(Object o) {
                        deleteSparepart(sparepart.getId());
                    }
                }).show();
                return true;
            }
        });

    }

    private void deleteSparepart(final String id) {
        new Thread(new Runnable() {
            public void run() {
                httpFlag = HttpConst.Request.DELETE_SPAREPARTS;
                HttpRequest request = new HttpRequest(FacilityAddPage.this);
                JSONObject json;
                try {
                    json = new JSONObject().put(HttpConst.Sparepart.ID, id);
                    request.postRequest(HttpConst.Request.DELETE_SPAREPARTS, json);
                    deletedId = id;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void uploadImage(final String url) {
        final File sdDir = new File(Environment.getExternalStorageDirectory(), "Facility/image/temp");
        if (!sdDir.exists()) {
            sdDir.mkdirs();
        }
        File image = new File(sdDir, "temp.png");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(image);

            Bitmap bitmap = BitmapManager.compressImage(url);
            boolean success = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUrl = sdDir + "/temp.png";
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpFlag = HttpConst.Request.UPLOAD_IMAGE;
                HttpRequest httpRequest = new HttpRequest(FacilityAddPage.this);
                httpRequest.uploadImage(HttpConst.Request.UPLOAD_IMAGE, sdDir + "/temp.png");
            }
        }).start();
    }

    @Override
    public void callback(final String data) {
        LogUtils.d(data);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                CustomDialog.dismissDialog();
                if (data == null) {
                    ToastUtils.show(getContext(), "请求服务器失败");
                    return;
                }
                try {
                    JSONObject json = new JSONObject(data);
                    final String msg = (String) json.get("errMsg");
                    if (json.get("errCode").equals(0)) {
                        if (httpFlag.equals(HttpConst.Request.UPLOAD_IMAGE)) {
                            final String name = json.getString("image_id");

                            if (imageId == null) {
                                imageId = new StringBuilder();
                                imageId.append(name);
                            } else {
                                imageId.append("," + name);
                            }

                            final String localPath = json.getString("local_path");
                            mChildThread.post(new Runnable() {
                                @Override
                                public void run() {
                                    showImageView(name, localPath);
                                }
                            });
                        } else if (httpFlag.equals(HttpConst.Request.ADD_FACILITY)
                                || httpFlag.equals(HttpConst.Request.UPDATE_FACILITY)) {
                            if (isAddSparepart) {
                                Bundle bundle = new Bundle();
                                bundle.putString(SparepartAddPage.FACILITY_ID,
                                        json.getString(SparepartAddPage.FACILITY_ID));
                                facilityId = json.getString(SparepartAddPage.FACILITY_ID);
                                PageActivity.openPageForResult(getContext(), SparepartAddPage.class, bundle,
                                        ADD_SPAREPARTS);
                            } else {
                                ((Activity) getContext()).setResult(Activity.RESULT_OK);
                                finish();
                            }
                        } else if (httpFlag.equals(HttpConst.Request.DELETE_SPAREPARTS)) {
                            LinearLayout rootView = (LinearLayout) findViewById(R.id.container);
                            rootView.removeView(rootView.findViewById(Integer.parseInt(deletedId)));
                        } else if (httpFlag.equals(HttpConst.Request.DOWNLOAD_IMAGE)) {
                            File image = new File(Environment.getExternalStorageDirectory(), "Facility/image/" + json.getString("imageId") + ".png");
                            LoadImageManager.addImage(getContext(), image.toString(), (ViewGroup) findViewById(R.id.add_image).getParent());
                        }
                    } else {
                        ToastUtils.show(getContext(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_device_sparepart:
                isAddSparepart = true;
                addOrUpdateFacility(getContext());
                break;
            case R.id.right_title_layout:
                isAddSparepart = false;
                addOrUpdateFacility(getContext());
                break;
            case R.id.add_image:
            case R.id.add:
                FunctionConfig config = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(9)
                        .setEnablePreview(true)//是否开启预览功能
                        .setEnableCamera(true)
                        .build();
                GalleryFinal.openGalleryMuti(110, config, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                        if (resultList != null && resultList.size() > 0) {
                            for (PhotoInfo info : resultList) {
                                mImageList.add(info.getPhotoPath());
                            }
                            startUpload();
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
                break;
            default:
                break;
        }
    }

    private void startUpload() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.add).setVisibility(View.VISIBLE);
                findViewById(R.id.add_image).setVisibility(View.GONE);
                synchronized (mImageList) {
                    if (mImageList.size() == 0) {
                        return;
                    }
                    final String path = mImageList.get(0);
                    mImageList.remove(0);

                    FacilityImageView imageView = new FacilityImageView(getContext());
                    imageView.setTag(path);
                    LinearLayout parent = (LinearLayout) findViewById(R.id.add_image).getParent();
                    parent.addView(imageView, 0);
                    imageView.getImageView().setImageResource(R.mipmap.loading);
                    imageView.setOnDeleteClickListener(null);

                    mChildThread.post(new Runnable() {
                        @Override
                        public void run() {
                            httpFlag = HttpConst.Request.UPLOAD_IMAGE;
                            HttpRequest httpRequest = new HttpRequest(FacilityAddPage.this);
                            httpRequest.uploadImage(HttpConst.Request.UPLOAD_IMAGE, path);
                            startUpload();
                        }
                    });
                }
            }
        });


    }


}
