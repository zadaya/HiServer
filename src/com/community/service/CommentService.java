package com.community.service;

import java.util.List;

import com.community.po.Comment;
import com.community.utils.ResultData;

public interface CommentService {


	ResultData<List<Comment>> selectComments(Integer page, Integer num, Integer postid) throws Exception;
	
	void insert(Comment record);  
	 
	Comment selectByPrimaryKey(Integer id);
	 
}
