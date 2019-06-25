package com.community.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.mapper.CommentMapper;
import com.community.mapper.CommentMapperCustom;
import com.community.po.Comment;
import com.community.po.CommentExample;
import com.community.pojo.CommentCustom;
import com.community.pojo.Page;
import com.community.service.CommentService;
import com.community.utils.PageUtils;
import com.community.utils.ResultData;

@Transactional
@Service
public class CommentServiceImpl implements CommentService{

	@Resource
	private CommentMapper commentMapper;
	@Resource
	private CommentMapperCustom commentMapperCustom;
	
	
	public List<Comment> selectCommentsPageByPostId(CommentCustom custom) throws Exception {
		return commentMapperCustom.selectCommentsPageByPostId(custom);
	}


	@Override
	public ResultData<List<Comment>> selectComments(Integer currentPage, Integer pageCount, Integer postid) throws Exception {
		CommentCustom custom=new CommentCustom();
		custom.setPostid(postid);
		Page page;
		ResultData<List<Comment>> resultData = new ResultData<>();
		if(currentPage>=2){
			/* @param everyPage 每一页记录数
			 * @param totalCount 总记录数
			 * @param currentPage 当前页数
			 */ 
			page = PageUtils.createPage(pageCount, currentPage);
		}else{
			int count = countCommentBypostid(postid);
			if(count == 0){
//				json.put("pageNum", count);
				resultData.setData(null);
				return resultData;
			}else{
				page = PageUtils.createPage(pageCount, count, currentPage);
//				json.put("pageNum", page.getTotalPage());
			}
		}	
		custom.setPage(page);
		List<Comment> list = commentMapperCustom.selectCommentsPageByPostId(custom);
		resultData.setData(list);
		return resultData;
	}
	
	public int countCommentBypostid(int postid) {
		CommentExample commentExample = new CommentExample();
		com.community.po.CommentExample.Criteria criteria = commentExample.createCriteria();
		criteria.andPostidEqualTo(postid);
		return commentMapper.countByExample(commentExample);
	}


	@Override
	public void insert(Comment record) {
		commentMapperCustom.insertCommentGetCommentId(record);
	}


	@Override
	public Comment selectByPrimaryKey(Integer id) {
		return commentMapper.selectByPrimaryKey(id);
	}

}
