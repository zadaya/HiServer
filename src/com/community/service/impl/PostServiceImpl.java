package com.community.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.mapper.PostMapper;
import com.community.mapper.PostMapperCustom;
import com.community.po.Post;
import com.community.po.PostExample;
import com.community.po.PostExample.Criteria;
import com.community.pojo.Page;
import com.community.pojo.PostCustom;
import com.community.service.PostService;
import com.community.utils.PageUtils;
import com.community.utils.ResultData;

@Transactional
@Service
public class PostServiceImpl implements PostService{

	@Resource
	private PostMapper postMapper;
	@Resource
	private PostMapperCustom postMapperCustom;


	public int deleteByPrimaryKey(Integer id) {
		return postMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Post record) {
		return postMapper.insert(record);
	}
	
	@Override
	public int update(Post record){
		return postMapper.updateByPrimaryKey(record);
	}
		

	@Override
	public Post selectByPrimaryKey(Integer id) {
		return postMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultData<List<Post>> selectPosts(Integer currentPage, Integer pageCount, Integer homeid) throws Exception {
        PostCustom custom=new PostCustom();
        custom.setVillageId(homeid);
		Page page;
		ResultData<List<Post>> resultData = new ResultData<>();
		
		if(currentPage>=2){
			page = PageUtils.createPage(pageCount, currentPage);
		}else{
			int count = countByHomeid(homeid);
			page = PageUtils.createPage(pageCount, count, currentPage);
			if(count == 0){
//				json.put("pageNum", count);
				resultData.setData(null);
			}else{
//				json.put("pageNum", page.getTotalPage());
				page = PageUtils.createPage(pageCount, currentPage);
			}
		}	
		custom.setPage(page);
		List<Post> list = postMapperCustom.selectPostpage(custom);
		resultData.setData(list);
		return resultData;
	}

	
	public int countByHomeid(Integer homeid) {
		PostExample postExample = new PostExample();
		Criteria criteria = postExample.createCriteria();
		criteria.andVillageIdEqualTo(homeid);
		return postMapper.countByExample(postExample);
	}
	
}
