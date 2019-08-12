package com.summer.icore.model;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "User")
@TableName("user")
@Data
public class User  extends Model<User> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }

	@ApiModelProperty(value = "ID", required = true)
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("salt")
	private String salt;
	@TableField("member_level_id")
	private Integer memberLevelId;
	@TableField("user_name")
	private String userName;
	private String password;
	@TableField("nick_name")
	private String nickName;
	private String phone;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
	@TableField("login_time")
	private Date loginTime;
	private String icon;
	private String gender;
	private Date birthday;
	private String city;
	private String job;
	@TableField("personalized_signature")
	private String personalizedSignature;
	@TableField("source_type")
	private Integer sourceType;
	private Integer integration;
	private Integer growth;
	@TableField("luckey_count")
	private Integer luckeyCount;
	@TableField("history_integration")
	private Integer historyIntegration;

}
