package com.example.prj1be.controller;

import com.example.prj1be.domain.Board;
import com.example.prj1be.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

   private final BoardService service;

   @PostMapping("add")
   public ResponseEntity<Object> add(@RequestBody Board board

   ) {
      if (!service.validate(board)) {
         return ResponseEntity.badRequest().build();
      }
      if (service.save(board)) {
         return ResponseEntity.ok().build();
      } else {
         return ResponseEntity.internalServerError().build();
      }
   }

   @GetMapping("list")
   public List<Board> list() {
      return service.list();
   }

   @GetMapping("id/{id}")
   public Board get(@PathVariable Integer id) {
      return service.get(id);
   }

   @DeleteMapping("remove/{id}")
   public ResponseEntity remove(@PathVariable Integer id) {
      if (service.remove(id)) {
         return ResponseEntity.ok().build();
      } else {
         return ResponseEntity.internalServerError().build();
      }
   }

   @PutMapping("edit")
   // 프론트에서 넘어온 edit 정보를 받아야하니깐
   // @리퀘스트바디 애노테이션을 쓰자!

   public ResponseEntity edit(@RequestBody Board board) {
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

