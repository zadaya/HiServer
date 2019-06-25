package com.community.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.community.mapper.PublicMapper;
import com.community.po.Public;
import com.community.po.PublicExample;
import com.community.utils.ResultData;

/**
 * 微信公众号列表
 * @author luqinmao
 *
 */

@Controller
@RequestMapping(value="public",produces="text/plain;charset=UTF-8")
public class PublicCtrl {

	@Resource
	private PublicMapper publicMapper;

	/***
	 * 获取微信公众号列表
	 */
	@RequestMapping(value = "getPublics", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public ResultData<List<Public>> getPublics() throws Exception {
		List<Public> publics = publicMapper.selectByExample(new PublicExample());
		ResultData<List<Public>> resultData = new ResultData<>();
		resultData.setData(publics);
		resultData.setCode(200);
		resultData.setMsg("请求成功");
		return resultData;
	}
	
}