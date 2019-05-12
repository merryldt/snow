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

	@TableField("head_photo")
	private String headPhoto;

	@TableField("gender")
	private String gender;

	@TableField("phone")
	private String phone;

	@TableField("password")
	private byte[] password;

	@TableField("nick_name")
	private String nickName;

	@TableField("province")
	private String province;

	@TableField("city")
	private String city;

	@TableField("country")
	private String country;

	@TableField(value = "login_time")
	private Date loginTime;

	@TableField(value = "create_time")
	private Date createTime;

	@TableField(value = "update_time")
	private Date updateTime;

	@TableField("status")
	private Integer status;
	@TableField("session_key")
	private String sessionKey;

	@TableField("salt")
	private String salt;
	@TableField(exist = false)
	private String pass;
	@TableField(exist = false)
	private String encryptPwd;

	@TableField(exist = false)
	private List<String> roles;

	@TableField("name")
	private String username;

}
