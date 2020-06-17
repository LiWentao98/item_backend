package com.item_backend.utils;

import org.springframework.stereotype.Component;


import java.util.Random;

@Component
public class EmailUtil {
  public String  getCheckCode(){
      String str = "0123456789";
      Random random = new Random();
      StringBuilder s=new StringBuilder();
      for (int i = 1; i <= 6; i++) {
          int index = random.nextInt(str.length());// 获取随机角标
          char c = str.charAt(index); // 获取字符
          s.append(c);
      }
      System.out.println(s);
      return s.toString();
  }
}
