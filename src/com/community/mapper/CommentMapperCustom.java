package com.community.mapper;

import java.util.List;

import com.community.po.Comment;
import com.community.pojo.CommentCustom;


public interface CommentMapperCustom {
	
	List<Comment> selectCommentsPageByPostId(CommentCustom custom) throws Exception;
	
	void insertCommentGetCommentId(Comment record);  //返回评论id


}
