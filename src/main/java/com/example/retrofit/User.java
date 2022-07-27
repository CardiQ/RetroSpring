package com.example.retrofit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
//存储前端数据的实体类
@Data
@TableName("user")
public class User {

   @TableId("id")
   private Integer id;//原为long

   @TableField("username")
   private String username;

   @TableField("password")
   private String password;

}
