package com.summer.icore.model;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UserAdmin")
@TableName("user_admin")
@Data
public class UserAdmin  extends Model<UserAdmin> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }

	@ApiModelProperty(value = "ID", required = true)
	 @TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String icon;
	private String email;
	@TableField("nick_name")
	private String nickName;
	private String note;
	@TableField("create_time")
	private Date createTime;
	@TableField("login_time")
	private Date loginTime;
	private Integer status;
	@TableField("update_time")
	private Date updateTime;
	private String salt;
	@TableField(exist = false)
	private List<String> roles;


}
