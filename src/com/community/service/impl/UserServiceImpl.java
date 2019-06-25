package com.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.mapper.FriendMapper;
import com.community.mapper.UserMapper;
import com.community.po.Friend;
import com.community.po.FriendExample;
import com.community.po.LoginReturnData;
import com.community.po.User;
import com.community.po.UserExample;
import com.community.po.UserExample.Criteria;
import com.community.service.UserService;
import com.community.utils.ResultData;
import com.community.utils.StringUtils;
import com.community.utils.TimeUtil;
import com.community.yunxin.MainTest;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private FriendMapper friendMapper;

	@Override
	public ResultData<User> checkUsername(String username) throws Exception {
		ResultData<User> resultData = new ResultData<>();
		User user = getUser(username);
		if (user == null) {
			resultData.setCode(0);
			resultData.setMsg("用户名不存在");
		} else {
			resultData.setCode(1);
			resultData.setMsg("用户名已存在");
		}
		return resultData;
	}

//	@Override
//	public ResultData<List<User>> login(String username, String password) throws Exception {
//		ResultData<List<User>> resultData = new ResultData<>();
//		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
//			resultData.setCode(-100);
//			resultData.setMsg("请求参数不能为空");
//			resultData.setSuccess(false);
//			return resultData;
//		}
//
//		User user = getUser(username);
//		
//		if (user == null) {
//			// 用户名不存在
//			resultData.setCode(-2);
//			resultData.setMsg("用户名不存在");
//			resultData.setSuccess(false);
//			return resultData;
//		}
//
//		if (!password.equals(user.getPassword())) {
//			// 密码错误
//			resultData.setCode(-1);
//			resultData.setMsg("密码不正确");
//			resultData.setSuccess(false);
//			return resultData;
//		}
//
//		List<Friend> friends = getFriends(user.getId());
//		List<User> users = new ArrayList<>();
//		for (int i = 0; i < friends.size(); i++) {
//			User userinfo = userMapper.selectByPrimaryKey(friends.get(i).getId());
//			users.add(userinfo);
//		}
//		
//		resultData.setData(users);
//		resultData.setCode(1);
//		resultData.setMsg("登录成功");
//		resultData.setSuccess(true);
//		return resultData;
//	}
	@Override
	public ResultData<LoginReturnData> login(String accid, String password) throws Exception {
		ResultData<LoginReturnData> resultData = new ResultData<>();
		if (StringUtils.isEmpty(accid) || StringUtils.isEmpty(password)) {
			resultData.setCode(-100);
			resultData.setMsg("请求参数不能为空");
			resultData.setSuccess(false);
			return resultData;
		}

		User user = getUser(accid);

		if (user == null) {
			// 用户名不存在
			resultData.setCode(-2);
			resultData.setMsg("用户名不存在");
			resultData.setSuccess(false);
			return resultData;
		}

		if (!password.equals(user.getPassword())) {
			// 密码错误
			resultData.setCode(-1);
			resultData.setMsg("密码不正确");
			resultData.setSuccess(false);
			return resultData;
		}

		List<Friend> friends = getFriends(user.getId());
		LoginReturnData logindata = new LoginReturnData();
		logindata.setMyselfInfo(getUser(accid));
		List<User> users = new ArrayList<>();
		for (int i = 0; i < friends.size(); i++) {
			User userinfo = userMapper.selectByPrimaryKey(friends.get(i).getId());
			users.add(userinfo);
		}
		logindata.setFriends(users);
		resultData.setData(logindata);
		resultData.setCode(1);
		resultData.setMsg("登录成功");
		resultData.setSuccess(true);
		return resultData;
	}

	@Override
	public ResultData<User> register(int homeid, String phone, String password, String accid, String username,
			String userphoto, String sex, String birthday, String address, String describe) throws Exception {
		ResultData<User> resultData = new ResultData<>();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			resultData.setCode(-100);
			resultData.setMsg("请求参数不能为空");
			resultData.setSuccess(false);
			return resultData;
		}
		User haveuser = getUser(username);
		if (haveuser != null) {
			// 用户名已经存在
			resultData.setCode(0);
			resultData.setMsg("用户名已经存在");
			resultData.setSuccess(false);
			return resultData;
		}

		// 同时注册云信
		MainTest.createUser(accid, username, password, userphoto);

//		//注册环信账号
//		registInHuanxin(username,password,username);

		// 插入到admin表
		User user = new User();
		user.setHomeid(homeid);
		user.setPhone(phone);
		user.setPassword(password);
		user.setUsername(username);
		user.setAccid(accid);
		user.setToken(password);
		user.setUserPhoto(userphoto);
		user.setSex(sex);
		user.setBirthday(birthday);
		user.setAddress(address);
		user.setSignDesc(describe);
		user.setCreateTime(TimeUtil.getCurrentTimeString());
		userMapper.insert(user);// 插入到数据库

		resultData.setData(user);
		resultData.setCode(1);
		resultData.setMsg("注册成功");
		resultData.setSuccess(true);
		return resultData;
	}

//按照账号查询数据库
	private User getUser(String accid) {
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andAccidEqualTo(accid);
		// 查询数据库语句
		List<User> list = userMapper.selectByExample(userExample);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	private List<Friend> getFriends(Integer id) {
		FriendExample friendExample = new FriendExample();
		com.community.po.FriendExample.Criteria criteria = friendExample.createCriteria();
		criteria.andOwnidEqualTo(id);
		List<Friend> friends = friendMapper.selectByExample(friendExample);
		return friends;
	}

	@Override
	public Boolean isAlreadyRegistered(String accid) {
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andAccidEqualTo(accid);
		List<User> users = userMapper.selectByExample(userExample);
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int update(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

//	private List<User> getFriendsInfo(Integer id) {
//		UserExample userExample = new UserExample();
//		com.community.po.UserExample.Criteria criteria = userExample.createCriteria();
//		criteria.andIdEqualTo(id);
//		List<User> users = userMapper.selectByExample(userExample);
//		return users;
//		
//	}

}
