package com.example.prj1be.domain;

import com.example.prj1be.util.AppUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Board {

   private Integer id;
   private String title;
   private String content;
   private String writer;
   private String nickName;
   private LocalDateTime inserted;
   private Integer commentCount;
   private Integer countLike;
   private List<BoardFile> files;

   public String getAgo() {
      return AppUtil.getAgo(inserted, LocalDateTime.now());
   }


}
