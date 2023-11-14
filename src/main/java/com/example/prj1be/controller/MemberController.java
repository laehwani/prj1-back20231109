package com.example.prj1be.controller;

import com.example.prj1be.domain.Member;
import com.example.prj1be.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

   private final MemberService service;

   @PostMapping("signup")
   public ResponseEntity<Object> signup(@RequestBody Member member) {
//      System.out.println("member = " + member);
      if (service.validate(member)) {
         if (service.add(member)) {
            return ResponseEntity.ok().build();
         } else {
            return ResponseEntity.internalServerError().build();
         }
      } else {
         return ResponseEntity.badRequest().build();
      }
      service.add(member);
   }

   @GetMapping(value = "check", params = "id")
   public ResponseEntity checkId(String id) {
      if (service.getId(id) == null) {
         return ResponseEntity.notFound().build();
      } else {
         return ResponseEntity.ok().build();
      }
   }

   @GetMapping(value = "check", params = "email")
   public ResponseEntity checkEmail(String email) {
      if (service.getEmail(email) == null) {
         return ResponseEntity.notFound().build();
      } else {
         return ResponseEntity.ok().build();
      }
   }
}
