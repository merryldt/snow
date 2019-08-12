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

@ApiModel(value = "UserPermission")
@TableName("user_permission")
public class UserPermission  extends Model<UserPermission> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }

	 @TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private Integer pid;
	private String name;
	private String value;
	private String icon;
	private Integer type;
	private String uri;
	private Integer status;
	@TableField("create_time")
	private Date createTime;
	private Integer sort;
	@TableField(exist = false)
	private String category;



  	public Integer getId() {
   	 return id;
 	 }
  	public void setId(Integer id) {
   	 this.id = id;
 	 }


  	public Integer getPid() {
   	 return pid;
 	 }
  	public void setPid(Integer pid) {
   	 this.pid = pid;
 	 }


  	public String getName() {
   	 return name;
 	 }
  	public void setName(String name) {
   	 this.name = name;
 	 }


  	public String getValue() {
   	 return value;
 	 }
  	public void setValue(String value) {
   	 this.value = value;
 	 }


  	public String getIcon() {
   	 return icon;
 	 }
  	public void setIcon(String icon) {
   	 this.icon = icon;
 	 }


  	public Integer getType() {
   	 return type;
 	 }
  	public void setType(Integer type) {
   	 this.type = type;
 	 }


  	public String getUri() {
   	 return uri;
 	 }
  	public void setUri(String uri) {
   	 this.uri = uri;
 	 }


  	public Integer getStatus() {
   	 return status;
 	 }
  	public void setStatus(Integer status) {
   	 this.status = status;
 	 }


  	public Date getCreateTime() {
   	 return createTime;
 	 }
  	public void setCreateTime(Date createTime) {
   	 this.createTime = createTime;
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
