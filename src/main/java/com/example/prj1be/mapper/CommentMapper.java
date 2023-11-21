package com.example.prj1be.mapper;

import com.example.prj1be.domain.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

   @Insert("""
      INSERT INTO comment (boardId, comment, memberId)
      VALUES (#{boardId}, #{comment}, #{memberId})
      """)
   int insert(Comment comment);

   @Select("""
      SELECT *
      FROM comment
      WHERE boardId = #{boardId}
      """)
      // TODO: 지금 작성한 테이블의 컬럼명과 comment의 프로퍼티명이 같으니깐 그냥 셀렉트 별을 쓴다.
   List<Comment> selectByBoardId(Integer boardId);

   @Delete("""
      DELETE FROM comment
      WHERE id = #{id}
      """)
   Integer deleteById(Integer id);

   @Select("""
      SELECT *
      FROM comment
      WHERE id = #{id}
      """)
   Comment selectById(Integer id);
}
