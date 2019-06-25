package com.community.mapper;

import java.util.List;

import com.community.po.Post;
import com.community.pojo.PostCustom;


public interface PostMapperCustom {
	
	List<Post> selectPostpage(PostCustom custom) throws Exception;

}
