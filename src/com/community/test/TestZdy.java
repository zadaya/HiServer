package com.community.test;

import com.community.controller.UserCtrl;
import com.community.po.LoginReturnData;
import com.community.utils.ResultData;

public class TestZdy {
	public static void main(String[] args) throws Exception {
		UserCtrl userServiceImpl = new UserCtrl();
		ResultData<LoginReturnData> resultData = new ResultData<>();
		resultData = userServiceImpl.login("ppp", "123456");
		
		System.err.println("===========================================");
		System.err.println(resultData);
		System.err.println("===========================================");

	}
}
