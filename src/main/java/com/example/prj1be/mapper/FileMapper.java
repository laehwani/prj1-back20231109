package com.example.prj1be.mapper;

import com.example.prj1be.domain.BoardFile;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

   @Insert("""
        INSERT INTO boardFile (boardId, name)
        VALUES (#{boardId}, #{name})
        """)
   int insert(Integer boardId, String name);

   List<BoardFile> selectNamesByBoardId(Integer id);
}
