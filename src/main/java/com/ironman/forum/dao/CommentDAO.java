package com.ironman.forum.dao;

import com.ironman.forum.entity.Comment;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDAO {
    void save(Comment comment);

    List<Comment> pageByReplyIdAndType(@Param("replyId") long replyId, @Param("type") int type,
                                       @Param("pageRequest") PageRequest pageRequest);

    Comment getBaseInfoByUniqueId(String uniqueId);

    Comment getBaseInfoById(long id);
}
