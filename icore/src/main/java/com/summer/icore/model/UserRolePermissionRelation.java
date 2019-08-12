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

@ApiModel(value = "UserRolePermissionRelation")
@TableName("user_role_permission_relation")
public class UserRolePermissionRelation  extends Model<UserRolePermissionRelation> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }
	 @TableId(value="id", type= IdType.AUTO)
	private Integer id;

	@TableField("role_id")
	private Integer roleId;

	@TableField("permission_id")
	private Integer permissionId;

  	public Integer getId() {
   	 return id;
 	 }
  	public void setId(Integer id) {
   	 this.id = id;
 	 }

  	public Integer getRoleId() {
   	 return roleId;
 	 }
  	public void setRoleId(Integer roleId) {
   	 this.roleId = roleId;
 	 }

  	public Integer getPermissionId() {
   	 return permissionId;
 	 }
  	public void setPermissionId(Integer permissionId) {
   	 this.permissionId = permissionId;
 	 }

}
