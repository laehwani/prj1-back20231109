package com.example.prj1be.mapper;

import com.example.prj1be.domain.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommentMapper {

   @Insert("""
      INSERT INTO comment (boardId, comment, memberId)
      VALUES (#{boardId}, #{comment}, #{memberId})
      """)
   int insert(Comment comment);

   @Select("""
         SELECT c.id, c.boardId, c.memberId, c.comment, m.nickName memberNickName ,c.inserted
         FROM comment c
         JOIN member m on m.id = c.memberId
         WHERE boardId = #{boardId}
         ORDER BY c.id DESC;
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

   @Update("""
      UPDATE comment
      SET comment = #{comment}
      WHERE id = #{id}
      """)
   int update(Comment comment);

   @Delete("""
      DELETE FROM comment
      WHERE boardId = #{boardId}
      """)
   int deleteByBoardId(Integer boardId);

   @Delete("""
      DELETE FROM comment
      WHERE memberId = #{memberId}
      """)
   int deleteByMemberId(String memberId);
}
