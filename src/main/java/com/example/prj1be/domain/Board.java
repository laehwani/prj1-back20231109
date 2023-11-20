package com.example.prj1be.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Board {

   private Integer id;
   private String title;
   private String content;
   private String writer;
   private String nickName;
   private LocalDateTime inserted;


}
