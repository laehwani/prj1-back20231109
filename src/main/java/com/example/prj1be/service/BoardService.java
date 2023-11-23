package com.example.prj1be.service;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Member;
import com.example.prj1be.mapper.BoardMapper;
import com.example.prj1be.mapper.CommentMapper;
import com.example.prj1be.mapper.FileMapper;
import com.example.prj1be.mapper.LikeMapper;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {

   private final BoardMapper mapper;
   private final CommentMapper commentMapper;
   private final LikeMapper likeMapper;
   private final FileMapper fileMapper;

   public boolean save(Board board, Member login, MultipartFile[] files) {

      board.setWriter(login.getId());

      int cnt = mapper.insert(board);

      // boardFile 테이블에 files 정보 저장
      if (files != null) {
         for (int i = 0; i < files.length; i++) {
            // boardId, name
            fileMapper.insert(board.getId(), files[i].getOriginalFilename());

            // 실제 파일은 S3 bucket에 upload 하게..
         }
      }
      return cnt == 1;
   }


   public boolean validate(Board board) {

      if (board == null) {
         return false;
      }
      if (board.getContent() == null || board.getContent().isBlank()) {
         return false;
      }
      if (board.getTitle() == null || board.getTitle().isBlank()) {
         return false;
      }
      return true;
   }

   public Map<String, Object> list(Integer page, String keyword) {
      Map<String, Object> map = new HashMap<>();
      Map<String, Object> pageInfo = new HashMap<>();

      int countAll = mapper.countAll("%" + keyword + "%");
      int lastPageNumber = (countAll - 1) / 10 + 1;
      int startPageNumber = (page - 1) / 10 * 10 + 1;
      int endPageNumber = startPageNumber + 9;
      endPageNumber = Math.min(endPageNumber, lastPageNumber);
      int prevPageNumber = startPageNumber - 10;
      int nextPageNumber = endPageNumber + 1;

      pageInfo.put("currentPageNumber", page);
      pageInfo.put("startPageNumber", startPageNumber);
      pageInfo.put("endPageNumber", endPageNumber);
      if (prevPageNumber > 0) {
         pageInfo.put("prevPageNumber", prevPageNumber);
      }
      if (nextPageNumber <= lastPageNumber) {
         pageInfo.put("nextPageNumber", nextPageNumber);
      }

      int from = (page - 1) * 10;
      map.put("boardList", mapper.selectAll(from, "%" + keyword + "%"));
      map.put("pageInfo", pageInfo);
      return map;
   }

   public Board get(Integer id) {
      return mapper.selectById(id);
   }

   public boolean remove(Integer id) {
      // 게시물에 달린 댓글들 지우기
      commentMapper.deleteByBoardId(id);

      // 좋아요 기록 지우기...
      likeMapper.deleteByBoardId(id);

      return mapper.deleteById(id) == 1;
   }

   public boolean update(Board board) {
      return mapper.update(board) == 1;
   }

   public boolean hasAccess(Integer id, Member login) {
      if (login == null) {
         return false;
      }
      if (login.isAdmin()) {
         return true;
      }
      Board board = mapper.selectById(id);

      return board.getWriter().equals(login.getId());
   }

}
