package com.community.service;

import com.community.po.LoginReturnData;
import com.community.po.User;
import com.community.utils.ResultData;

public interface UserService {

	

	/**
	 * 检查用户名是否存在
	 * @param username 用户名
	 * @return
	 */
	ResultData<User> checkUsername(String username) throws Exception;

	/**
	 * 登录
	 * @param session
	 * @param username 用户名
	 * @param password 密码
	 * @param checkCode 验证码
	 * @return
	 * @throws Exception
	 */
	ResultData<LoginReturnData> login(String accid, String password) throws Exception;

	/**
	 * 注册
	 * @param session
	 * @param username 用户名
	 * @param password 密码
	 * @param nickName 昵称
	 * @return
	 */
	ResultData<User> register(int homeid,String phone, String password, String accid,String username, String userphoto, String sex, String birthday,
			String address, String describe) throws Exception;

	/**
	 * 判断乡吧号是否已经被注册了
	 * @param accid
	 * @return
	 */
	Boolean isAlreadyRegistered(String accid);
	
	int update(User user);
	
}
