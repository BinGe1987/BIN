package com.kwaijian.facility.OldSource.http;

public class HttpConst {

	public static final String SEPARATOR_1 = "~";
	public static final String SEPARATOR_2 = ",";
	public static final String SEPARATOR_3 = "|||";

	public static final int NOTIFITION_ID_NEW_ORDER = 10001;
	public static final int NOTIFITION_ID_CANCEL_ORDER = 10002;
	public static final int NOTIFITION_ID_HASTEN_ORDER = 10003;

	public static class Request {

		//http://app.kwaijian.com/
		public static final String REAL_URL = "http://facility.kwaijian.com/Api/Client/mobileInterface";
//		public static final String REAL_URL = "http://app.kwaijian.com/facility/Api/Client/mobileInterface";
//		public static final String REAL_URL = "http://192.168.11.221/facility/Api/Client/mobileInterface";
		public static final String REQUEST_INTERFACE = "interface";
		public static String URL = REAL_URL;
//		public static String URL = TEST_URL;
		public static final String REQUEST_PARAM = "param";

		public static final String IMAGE_PATH = "http://facility.kwaijian.com/Rest/Pic/get/id/";
//		public static final String IMAGE_PATH = "http://app.kwaijian.com/facility/Rest/Pic/get/id/";
//        public static final String IMAGE_PATH = "http://192.168.11.221/facility/Rest/Pic/get/id/";
//		static {
//			if (BuildConfig.DEBUG) {
//				URL = TEST_URL;
//			}
//			Log.d("server", BuildConfig.DEBUG ? "测试服务器" : "正式服务器");
//		}

//		public static final String API_URL = URL + "Api/Client/mobileInterface";
//		public static final String IMAGE_URL = URL + "Rest/Pic/get/id";
//		public static final String CRASH_URL = URL + "Rest/File/upload";
		public static final String LOGIN = "login";
		public static final String LOGOUT = "logout";
		public static final String CHECK_LOGIN = "checkLogin";
		public static final String GET_SERVICE_LIST = "getServiceList";
		public static final String GET_SERVICE_HISTORY_LIST = "getServiceHistoryList";
		public static final String GET_SERVICE_DETAIL = "getServiceDetail";
		public static final String UPDATE_SERVICE = "updateService";
		public static final String FINISH_SERVICE = "finishService";
		public static final String CANCEL_SERVICE = "cancelService";

        public static final String GET_REPAIR_LIST = "getRepairList";
        public static final String GET_REPAIR_HISTORY_LIST = "getRepairHistoryList";
        public static final String GET_REPAIR_DETAIL = "getRepairDetail";
        public static final String UPDATE_REPAIR = "updateRepair";
        public static final String FINISH_REPAIR = "finishRepair";
        public static final String CANCEL_REPAIR = "cancelRepair";

		public static final String GET_COMPANY_LIST = "getCompanyList";
		public static final String ADD_COMPANY = "addCompany";
		public static final String GET_FACILITY_LIST = "getFacilityList";
		public static final String UPLOAD_IMAGE = "uploadImage";
		public static final String ADD_FACILITY = "addFacility";
		public static final String UPDATE_FACILITY = "updateFacility";
		public static final String DELETE_FACILITY = "delFacility";
		public static final String GET_SPAREPARTS_LIST = "getSparepartsList";
		public static final String ADD_SPAREPARTS = "addSpareparts";
		public static final String UPDATE_SPAREPARTS = "updateSpareparts";
		public static final String DELETE_SPAREPARTS = "delSpareparts";
		public static final String ADD_LINKMAN = "addLinkman";
		public static final String GET_LINKMAN_LIST = "getLinkmanList";
		public static final String DOWNLOAD_IMAGE = "downloadImage";
	}

	public static class Login {
		public static final String ACCOUNT = "account";
		public static final String PASSWORD = "password";
	}
	
	public static class Facility{
		public static final String ID = "facility_id";
		public static final String NAME = "f_name";
		public static final String MANUFACTURER = "f_manufacturer";
		public static final String MODEL = "f_model";
		public static final String CONTROLLER_BRAND = "f_controllerBrand";
		public static final String CONTROLLER_TYPE = "f_controllerType";
		public static final String REMARK = "f_remark";
		public static final String IMAGE = "f_image";
		public static final String STATUS = "status";
		public static final String SPAREPARTS = "spareparts";
	}
	
	public static class Sparepart{
		public static final String ID = "spareparts_id";
		public static final String NAME = "sp_name";
		public static final String TYPE = "sp_type";
		public static final String MANUFACTURER = "sp_manufacturer";
		public static final String MODEL = "sp_model";
		public static final String IMAGE = "sp_image";
		public static final String REMARK = "sp_remark";
		public static final String COUNT = "sp_count";
		public static final String BRAND = "sp_brand";
	}
}
