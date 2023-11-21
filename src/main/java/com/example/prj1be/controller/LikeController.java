package com.example.prj1be.controller;

import com.example.prj1be.domain.Like;
import com.example.prj1be.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/like")
@RestController
@RequiredArgsConstructor
public class LikeController {

   private final LikeService service;

   @PostMapping
   public void like(@RequestBody Like like) {
      service.update(like);
   }
}
