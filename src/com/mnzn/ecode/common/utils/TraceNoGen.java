package com.mnzn.ecode.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TraceNoGen {
	
	 public static String getTraceNo(){  
         SimpleDateFormat format= new SimpleDateFormat("yyyyMMddHHmmss");  
         String date=format.format(new Date());  
         String firstNo="MN";  
         Random rd = new Random();
         String n="";
         int getNum;
         do {
          getNum = Math.abs(rd.nextInt())%10 + 48;//产生数字0-9的随机数
          //getNum = Math.abs(rd.nextInt())%26 + 97;//产生字母a-z的随机数
          char num1 = (char)getNum;
          String dn = Character.toString(num1);
          n += dn;
         } while (n.length()<6);
         
         String lastNo=firstNo+date+n;  
     return lastNo;  
       
 }  

}
