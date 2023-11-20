package com.example.prj1be.mapper;

import com.example.prj1be.domain.Board;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BoardMapper {


   @Insert("""
      INSERT INTO board(title, content, writer)
      VALUES (#{title}, #{content}, #{writer})
      """)
   Integer insert(Board board);


   @Select("""
      SELECT b.id, b.title, b.writer, m.nickName, b.inserted
      FROM board b JOIN member m ON b.writer = m.id
      ORDER BY b.id DESC 
      """)
   List<Board> selectAll();


   @Select("""
      SELECT b.id, b.title, b.content, b.writer, m.nickName, b.inserted
      FROM board b JOIN member m ON b.writer = m.id
      WHERE b.id = #{id}

      """)
   Board selectById(Integer id);

   @Delete("""
      DELETE FROM board
      WHERE id = #{id}
      """)
   int deleteById(Integer id);


   @Update("""
      UPDATE board
      SET title = #{title},
      content= #{content},
      writer= #{writer}
      WHERE id = #{id}
      """)
   int update(Board board);


   @Delete("""
      DELETE FROM board
      WHERE writer = #{writer}
      """
   )
   int deleteByWriter(String writer);
}
