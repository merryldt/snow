package com.summer.icore.model;

import java.util.Date;

import java.io.Serializable;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UserAdminRoleRelation")
@TableName("user_admin_role_relation")
@Data
public class UserAdminRoleRelation  extends Model<UserAdminRoleRelation> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }

	@ApiModelProperty(value = "ID", required = true)
	 @TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("admin_id")
	private Integer adminId;
	@TableField("role_id")
	private Integer roleId;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
	private Integer status;


}
