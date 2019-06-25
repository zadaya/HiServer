package com.community.service;

import java.util.List;

import com.community.po.Village;
import com.community.utils.ResultData;

public interface VillageService {



	Village selectVillageInfo(Integer homeid);

	List<Village> getVillageListByDistrict(String district);

	Village searchByName(String villageName);

	boolean isAlreadyCreated(String villageName);

	ResultData<Village> insert(String villageIcon, String villageName, String villageDesc, int attentionNum, int postNum,
			String province, String city, String district);
	
}
