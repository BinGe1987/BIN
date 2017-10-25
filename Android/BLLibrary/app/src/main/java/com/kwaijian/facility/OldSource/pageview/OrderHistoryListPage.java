package com.kwaijian.facility.OldSource.pageview;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kwaijian.facility.UI.Home.Old.OrderAdapter;
import com.kwaijian.facility.R;
import com.kwaijian.facility.OldSource.http.HttpConst;
import com.kwaijian.facility.OldSource.http.HttpRequest;
import com.kwaijian.facility.OldSource.http.RequestCallback;
import com.kwaijian.facility.DataCenter.Home.DataObj.Data;
import com.kwaijian.facility.OldSource.tools.LogUtils;
import com.kwaijian.facility.OldSource.tools.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderHistoryListPage extends Page implements RequestCallback{
	private Handler mHandler;

	public OrderHistoryListPage(Context context) {
		super(context);
		setContentView(R.layout.page_order_history_list);
		mHandler = new Handler();
		initView();
	}

	private void initView() {
		((TextView)findViewById(R.id.title)).setText("历史工单");
		findViewById(R.id.save).setVisibility(View.GONE);
		ListView listview = (ListView) findViewById(R.id.list);
		listview.setAdapter(new OrderAdapter(Data.getInstence().getHistoryOrderlist()));
		
		getServiceHistoryList();
	}
	
	private void getServiceHistoryList() {
//		new Thread(new Runnable() {
//			public void run() {
//				HttpRequest request = new HttpRequest(OrderHistoryListPage.this);
//				request.postRequest(HttpConst.Request.GET_SERVICE_HISTORY_LIST);
//			}
//		}).start();
	}

	@Override
	public void callback(final String data) {
		LogUtils.d(data);
		mHandler.post(new Runnable() {
			public void run() {
				if (data == null) {
					ToastUtils.show(getContext(), "请求服务器失败");
					return;
				}
				try {
					JSONObject json = new JSONObject(data);
					if(json.get("errCode").equals(0)){
						Data.getInstence().getOrders(json.getJSONArray("list_service"));
						ListView listview = (ListView) findViewById(R.id.list);
						listview.setAdapter(new OrderAdapter(Data.getInstence().getHistoryOrderlist()));
					}else{
						ToastUtils.show(getContext(), (String)json.get("errMsg"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
