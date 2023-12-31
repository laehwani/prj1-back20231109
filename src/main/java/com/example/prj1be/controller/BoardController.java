package com.example.prj1be.controller;

import com.example.prj1be.domain.Board;
import com.example.prj1be.domain.Member;
import com.example.prj1be.service.BoardService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

   private final BoardService service;

   @PostMapping("add")
   public ResponseEntity add(Board board,
      @RequestParam(value = "files[]", required = false) MultipartFile[] files,
      @SessionAttribute(value = "login", required = false) Member login
   ) throws IOException {
      if (files != null) {
         for (int i = 0; i < files.length; i++) {
            System.out.println("file = " + files[i].getOriginalFilename());
            System.out.println("file.getSize() = " + files[i].getSize());
         }
      }
      if (login == null) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      if (!service.validate(board)) {
         return ResponseEntity.badRequest().build();
      }
      if (service.save(board, login, files)) {
         return ResponseEntity.ok().build();
      } else {
         return ResponseEntity.internalServerError().build();
      }
   }

   // /api/board/list?p=6
   // /api/board/list?k=java
   // /api/board/list?p=7&k=java
   @GetMapping("list")
   public Map<String, Object> list(
      @RequestParam(value = "p", defaultValue = "1") Integer page,
      @RequestParam(value = "k", defaultValue = "") String keyword) {


      return service.list(page, keyword);
   }

   @GetMapping("id/{id}")
   public Board get(@PathVariable Integer id) {
      return service.get(id);
   }

   @DeleteMapping("remove/{id}")
   public ResponseEntity remove(@PathVariable Integer id,
      @SessionAttribute(value = "login", required = false) Member login) {
      if (login == null) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 에러
      }
      if (!service.hasAccess(id, login)) {
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 에러
      }

      if (service.remove(id)) {
         return ResponseEntity.ok().build();
      } else {
         return ResponseEntity.internalServerError().build();
      }
   }

   @PutMapping("edit")
   // 프론트에서 넘어온 edit 정보를 받아야하니깐
   // @리퀘스트바디 애노테이션을 쓰자!

   public ResponseEntity edit(@RequestBody Board board,
      @SessionAttribute(value = "login", required = false) Member login) {

      if (login == null) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      if (!service.hasAccess(board.getId(), login)) {
//         게시물의 아이디를 가리키는 것이라 board.getId() 를 썼다.
         return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

//      System.out.println("board = " + board);
      if (service.validate(board)) {
         if (service.update(board)) {
            return ResponseEntity.ok().build();
         } else {
            return ResponseEntity.internalServerError().build();
         }
      } else {
         return ResponseEntity.badRequest().build();
      }
   }
}


