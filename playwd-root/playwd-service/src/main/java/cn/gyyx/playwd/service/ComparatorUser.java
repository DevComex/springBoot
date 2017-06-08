package cn.gyyx.playwd.service;

import java.util.Comparator;

import cn.gyyx.playwd.beans.playwd.RoleBean;

public class ComparatorUser implements Comparator{

    public int compare(Object obj0, Object obj1) {
        RoleBean user0=(RoleBean)obj0;
        RoleBean user1=(RoleBean)obj1;

      //首先比较等级，如果等级相同，则比较名字

     int flag=user0.getlevel().compareTo(user1.getlevel());
     if(flag==0){
      return user0.getNickName().compareTo(user1.getNickName());
     }else{
      return flag;
     }  
    }
    
   }