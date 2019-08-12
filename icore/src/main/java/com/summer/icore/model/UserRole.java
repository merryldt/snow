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

@ApiModel(value = "UserRole")
@TableName("user_role")
public class UserRole  extends Model<UserRole> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }

	@ApiModelProperty(value = "ID", required = true)
	 @TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String name;
	private String description;
	@TableField("admin_count")
	private Integer adminCount;
	@TableField("create_time")
	private Date createTime;
	private Integer status;
	private Integer sort;
	private String category;

  	public Integer getId() {
   	 return id;
 	 }
  	public void setId(Integer id) {
   	 this.id = id;
 	 }


  	public String getName() {
   	 return name;
 	 }
  	public void setName(String name) {
   	 this.name = name;
 	 }


  	public String getDescription() {
   	 return description;
 	 }
  	public void setDescription(String description) {
   	 this.description = description;
 	 }


  	public Integer getAdminCount() {
   	 return adminCount;
 	 }
  	public void setAdminCount(Integer adminCount) {
   	 this.adminCount = adminCount;
 	 }


  	public Date getCreateTime() {
   	 return createTime;
 	 }
  	public void setCreateTime(Date createTime) {
   	 this.createTime = createTime;
 	 }


  	public Integer getStatus() {
   	 return status;
 	 }
  	public void setStatus(Integer status) {
   	 this.status = status;
 	 }


  	public Integer getSort() {
   	 return sort;
 	 }
  	public void setSort(Integer sort) {
   	 this.sort = sort;
 	 }


  	public String getCategory() {
   	 return category;
 	 }
  	public void setCategory(String category) {
   	 this.category = category;
 	 }

}
