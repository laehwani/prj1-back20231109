package com.example.prj1be.controller;

import com.example.prj1be.domain.Member;
import com.example.prj1be.service.MemberService;
import lombok.RequiredArgsConstructor;
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
   public void signup(@RequestBody Member member) {
//      System.out.println("member = " + member);
      service.add(member);
   }
}