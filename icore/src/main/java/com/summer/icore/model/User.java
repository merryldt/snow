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

	@ApiModelProperty()
	@TableField("member_level_id")
	private Integer memberLevelId;

	@ApiModelProperty()
	@TableField("user_name")
	private String userName;

	private String password;

	@ApiModelProperty()
	@TableField("nick_name")
	private String nickName;

	private String phone;

	private Integer status;

	@ApiModelProperty()
	@TableField("create_time")
	private Date createTime;

	@ApiModelProperty()
	@TableField("update_time")
	private Date updateTime;

	private String icon;

	private String gender;

	private Date birthday;

	private String city;

	private String job;

	@ApiModelProperty()
	@TableField("personalized_signature")
	private String personalizedSignature;

	@ApiModelProperty()
	@TableField("source_type")
	private Integer sourceType;

	private Integer integration;

	private Integer growth;

	@ApiModelProperty()
	@TableField("luckey_count")
	private Integer luckeyCount;

	@ApiModelProperty()
	@TableField("history_integration")
	private Integer historyIntegration;

}
