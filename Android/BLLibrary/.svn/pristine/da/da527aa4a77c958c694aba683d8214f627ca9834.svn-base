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

import com.kwaijian.facility.UI.Home.Old.PageActivity;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.DataCenter.Home.DataObj.Facility;
import com.kwaijian.facility.DataCenter.Home.DataObj.Spare;
import com.kwaijian.facility.OldSource.tools.BitmapManager;
import com.kwaijian.facility.OldSource.tools.Density;
import com.kwaijian.facility.OldSource.tools.LoadImageManager;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;
import com.kwaijian.facility.OldSource.widget.AddImageDrawable;
import com.kwaijian.facility.OldSource.widget.CustomDialog;
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

public class CompanyFacilityAddPage extends Page implements RequestCallback {
    public static final String FACILITY_DETAIL = "facilityDetail";
    private static final int UPDATE_COMPANY_SPAREPARTS = 11;
    private static final int ADD_COMPANY_SPAREPARTS = 21;
    private Handler mHandler;
    private StringBuilder imageId;
    private String httpFlag;
    private String imageUrl;
    private boolean isAddSparepart;
    private String facilityId;

    private List<String> mImageList = new ArrayList<>();
    private Handler mChildThread;

    public CompanyFacilityAddPage(Context context) {
        super(context);

        HandlerThread ht = new HandlerThread("image-thread");
        ht.start();
        mChildThread = new Handler(ht.getLooper());
        mHandler = new Handler();

        setContentView(R.layout.page_company_facility_add);

        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.add_device));
        ((TextView) findViewById(R.id.save)).setText("保存");

        findViewById(R.id.right_title_layout).setOnClickListener(this);
        findViewById(R.id.add_device_sparepart).setOnClickListener(this);

        ImageView image = (ImageView) findViewById(R.id.add_image);
        image.setImageDrawable(new AddImageDrawable());
        image.setOnClickListener(this);

        findViewById(R.id.add).setOnClickListener(this);

        if (getBundle().getSerializable(FACILITY_DETAIL) != null) {
            ((TextView) findViewById(R.id.title)).setText(
                    /* getContext().getString(R.string.update_device) */"设备详情");
            initFacilityDetail(getContext(), getBundle());
        } else {
            ((TextView) findViewById(R.id.title)).setText(getContext().getString(R.string.add_device));
        }
    }

    private void initFacilityDetail(Context context, Bundle bundle) {
        Facility facility = (Facility) bundle.getSerializable(FACILITY_DETAIL);
        ((EditText) findViewById(R.id.name)).setText(facility.getName());
        ((EditText) findViewById(R.id.manufacturer)).setText(facility.getManufacturer());
        ((EditText) findViewById(R.id.model)).setText(facility.getModel());
        ((EditText) findViewById(R.id.remark)).setText(facility.getRemark());
        ((EditText) findViewById(R.id.controllerBrand)).setText(facility.getControllerBrand());
//		((EditText) findViewById(R.id.ON)).setText(facility.getON());
        ((EditText) findViewById(R.id.SN)).setText(facility.getfSN());
        getSparepartList();
        File sdDir = new File(Environment.getExternalStorageDirectory(), "Facility/image/");
        if (!sdDir.exists()) {
            sdDir.mkdirs();
        }
        for (int i = 0; i < facility.getImageId().length; i++) {
            if (imageId == null) {
                imageId = new StringBuilder();
                imageId.append(facility.getImageId()[i]);
            } else {
                imageId.append("," + facility.getImageId()[i]);
            }
//			File image = new File(sdDir, facility.getImageId()[i] + ".png");
//			if (image.exists()) {
//				LoadImageManager.addImage(getContext(), image.toString(),
//						(ViewGroup) findViewById(R.id.add_image).getParent());
//			}
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

    private void initSparepartsList(ViewGroup rootView, final Spare sparepart) {
        LinearLayout container;
        if (httpFlag != null && httpFlag.equals(HttpConst.Request.UPDATE_SPAREPARTS)) {
            container = (LinearLayout) rootView.findViewById(Integer.parseInt(sparepart.getId()));
            ((Spare) container.getTag()).setModel(sparepart.getModel());
            ((Spare) container.getTag()).setNum(sparepart.getNum());
            ((TextView) container.findViewById(R.id.name)).setText(sparepart.getModel());
            ((TextView) container.findViewById(R.id.type)).setText(sparepart.getNum());
        } else {
            container = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_sparepart, null);
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
                b.putSerializable(CompanySparepartAddPage.SPAREPART_DETAIL, sparepart);
                b.putString(CompanySparepartAddPage.FACILITY_ID, facilityId);
                b.putString(CompanySparepartAddPage.COMPANY_ID, getBundle().getString(FacilityListPage.COMPANY_ID));
                PageActivity.openPageForResult(getContext(), CompanySparepartAddPage.class, b,
                        UPDATE_COMPANY_SPAREPARTS);
            }
        });
    }

    /**
     * 获得备件列表
     **/
    private void getSparepartList() {
        httpFlag = HttpConst.Request.GET_SPAREPARTS_LIST;
        new Thread(new Runnable() {
            public void run() {
                HttpRequest request = new HttpRequest(CompanyFacilityAddPage.this);
                JSONObject json;
                try {
                    Bundle bundle = getBundle();
                    String companyId = bundle.getString(FacilityListPage.COMPANY_ID);
                    Facility facility = (Facility) bundle.getSerializable(FACILITY_DETAIL);
                    json = new JSONObject().put("company_id", companyId).put("facility_id", facility.getId());
                    request.postRequest(HttpConst.Request.GET_SPAREPARTS_LIST, json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean checkParams(Context context, String name, String manufacturer, String model, String controllerBrand,
                                String ON, String SN) {
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

    private void addFacility(final Facility facility) {
        new Thread(new Runnable() {
            public void run() {
                HttpRequest request = new HttpRequest(CompanyFacilityAddPage.this);
                try {
                    Facility f = (Facility) getBundle().getSerializable(FACILITY_DETAIL);
                    if (f != null) {
                        facility.setId(f.getId());
                        JSONObject json = facility.facilityToJson();
                        json.put("company_id", getBundle().getString(FacilityListPage.COMPANY_ID));
                        JSONObject finalJson = new JSONObject().put("facility", json);
                        httpFlag = HttpConst.Request.UPDATE_FACILITY;
                        LogUtils.d(finalJson);
                        request.postRequest(HttpConst.Request.UPDATE_FACILITY, finalJson);
                    } else {
                        if (httpFlag == HttpConst.Request.ADD_FACILITY || httpFlag == HttpConst.Request.UPDATE_SPAREPARTS
                                || httpFlag == HttpConst.Request.UPDATE_FACILITY) {
                            httpFlag = HttpConst.Request.UPDATE_FACILITY;
                            JSONObject json = facility.facilityToJson();
                            json.put(HttpConst.Facility.ID, facilityId);
                            json.put("company_id", getBundle().getString(FacilityListPage.COMPANY_ID));
                            JSONObject finalJson = new JSONObject().put("facility", json);
                            LogUtils.d(finalJson);
                            request.postRequest(httpFlag, finalJson);
                        } else {
                            httpFlag = HttpConst.Request.ADD_FACILITY;
                            JSONObject json = facility.facilityToJson();
                            json.put("company_id", getBundle().getString(FacilityListPage.COMPANY_ID));
                            JSONObject fianlJson = new JSONObject().put("facility", json);
                            LogUtils.d("post json : " + fianlJson);
                            request.postRequest(HttpConst.Request.ADD_FACILITY, fianlJson);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void uploadImage(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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

                    image = new File(sdDir, "temp.png");
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long length = image.length();
                LogUtils.d("image size:" + length);
                // imageUrl = url;
                imageUrl = sdDir + "/temp.png";

                httpFlag = HttpConst.Request.UPLOAD_IMAGE;
                HttpRequest httpRequest = new HttpRequest(CompanyFacilityAddPage.this);
                httpRequest.uploadImage(HttpConst.Request.UPLOAD_IMAGE, imageUrl);
            }
        }).start();
    }

    @Override
    public void callback(final String data) {
        LogUtils.d(data);
        mHandler.post(new Runnable() {
            public void run() {
                CustomDialog.dismissDialog();
                if (data == null) {
                    ToastUtils.show(getContext(), "请求服务器失败");
                    return;
                } else {
                    try {
                        JSONObject json = new JSONObject(data);
                        if (json.get("errCode").equals(0)) {
                            if (httpFlag.equals(HttpConst.Request.UPLOAD_IMAGE)) {
//								LoadImageManager.addImage(getContext(), imageUrl,
//										(ViewGroup) findViewById(R.id.add_image).getParent());
//								if (imageId == null) {
//									imageId = new StringBuilder();
//									imageId.append(json.get("image_id"));
//								} else {
//									imageId.append("," + json.get("image_id"));
//								}
//								LinearLayout parent = (LinearLayout) findViewById(R.id.add_image).getParent();
//								int childCount = parent.getChildCount();
//								String fromPath = (String) parent.getChildAt(childCount - 1).getTag();
//								String toPath = String.valueOf(json.get("image_id"));
//								Utils.saveImage(fromPath, toPath);
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
                                    Bundle bundle = getBundle();
                                    String companyId = bundle.getString(FacilityListPage.COMPANY_ID);
                                    Bundle b = new Bundle();
                                    // 为了不搞混淆,另new一个bundle，使用CompanySparepartAddPage中的常量作为key
                                    b.putString(CompanySparepartAddPage.COMPANY_ID, companyId);
                                    facilityId = json.getString("facility_id");
                                    b.putString(CompanySparepartAddPage.FACILITY_ID, facilityId);
                                    PageActivity.openPageForResult(getContext(), CompanySparepartAddPage.class, b,
                                            ADD_COMPANY_SPAREPARTS);
                                    isAddSparepart = false;
                                } else {
                                    ((Activity) getContext()).setResult(Activity.RESULT_OK);
                                    finish();
                                }
                            } else if (httpFlag.equals(HttpConst.Request.GET_SPAREPARTS_LIST)) {
                                LinearLayout rootView = (LinearLayout) findViewById(R.id.container);
                                Data.getInstence().getSpareparts(json.getJSONArray("list_spareparts"));
                                List<Spare> spareparts = Data.getInstence().getmSparepartslist();
                                for (int i = 0; i < spareparts.size(); i++) {
                                    initSparepartsList(rootView, spareparts.get(i));
                                }
                            }
                        } else {
                            ToastUtils.show(getContext(), (String) json.get("errMsg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        String manufacturer = ((EditText) findViewById(R.id.manufacturer)).getText().toString();
        String model = ((EditText) findViewById(R.id.model)).getText().toString();
        String brand = ((EditText) findViewById(R.id.controllerBrand)).getText().toString();
        String ON = ((EditText) findViewById(R.id.ON)).getText().toString();
        String SN = ((EditText) findViewById(R.id.SN)).getText().toString();
        String remark = ((EditText) findViewById(R.id.remark)).getText().toString();
        Facility facility;
        switch (v.getId()) {
            case R.id.right_title_layout:
                if (checkParams(getContext(), name, manufacturer, model, brand, ON, SN)) {
                    if (imageId == null) {
                        facility = new Facility(name, manufacturer, model, brand, null, remark);
                    } else {
                        facility = new Facility(name, manufacturer, model, brand, imageId.toString().split(","), remark);
                    }
//				facility.setON(ON);
                    facility.setfSN(SN);
                    facility.setStatus(1);
                    addFacility(facility);
                    CustomDialog.createLoadingDialog(getContext());
                }
                break;

            case R.id.add_device_sparepart:
                // Bundle bundle = getBundle();
                // String companyId = bundle.getString(FacilityListPage.COMPANY_ID);
                // Facility f = (Facility) bundle.getSerializable(FACILITY_DETAIL);
                // Bundle b = new Bundle();
                // // 为了不搞混淆,另new一个bundle，使用CompanySparepartAddPage中的常量作为key
                // b.putString(CompanySparepartAddPage.COMPANY_ID, companyId);
                // b.putString(CompanySparepartAddPage.FACILITY_ID, f.getId());
                // PageActivity.openPageForResult(getContext(),
                // CompanySparepartAddPage.class, b, ADD_COMPANY_SPAREPARTS);
                if (checkParams(getContext(), name, manufacturer, model, brand, ON, SN)) {
                    if (imageId == null) {
                        facility = new Facility(name, manufacturer, model, brand, null, remark);
                    } else {
                        facility = new Facility(name, manufacturer, model, brand, imageId.toString().split(","), remark);
                    }
                    facility.setON(ON);
                    facility.setSN(SN);
                    facility.setStatus(1);
                    addFacility(facility);
                    isAddSparepart = true;
                    CustomDialog.createLoadingDialog(getContext());
                }
                break;
            case R.id.add_image:
            case R.id.add:
//			new PickImageUploadDialog(getContext()).show();
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
                            HttpRequest httpRequest = new HttpRequest(CompanyFacilityAddPage.this);
                            httpRequest.uploadImage(HttpConst.Request.UPLOAD_IMAGE, path);
                            startUpload();
                        }
                    });
                }
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
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
            } else if ((requestCode == ADD_COMPANY_SPAREPARTS || requestCode == UPDATE_COMPANY_SPAREPARTS)
                    && data != null) {
                Bundle bundle = data.getExtras();
                LinearLayout rootView = (LinearLayout) findViewById(R.id.container);
                Spare sparepart = (Spare) bundle.getSerializable(CompanySparepartAddPage.SPAREPART_DETAIL);
                initSparepartsList(rootView, sparepart);
            }
            if (!localUrl.equals("")) {
                CustomDialog.createLoadingDialog(getContext()).show();
                uploadImage(localUrl);
            }
        }
    }

}
