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

@ApiModel(value = "WxUser")
@TableName("wx_user")
@Data
public class WxUser  extends Model<WxUser> {

	private static final long serialVersionUID = 1L;
	@Override
	protected Serializable pkVal() {  return this.id;  }

	@ApiModelProperty(value = "ID", required = true)
	 @TableId(value="id", type= IdType.AUTO)
	private Integer id;

	private String icon;

	private String city;

	private String country;

	private String gender;
	@TableField("is_follow")
	private Integer isFollow;
	@TableField("nick_name")
	private String nickName;

	private String province;
	@TableField("user_phone")
	private String userPhone;

	private Integer status;
	@TableField("wx_union_id")
	private String wxUnionId;
	@TableField("open_id")
	private String openId;
	@TableField("create_time")
	private Date createTime;
	@TableField("update_time")
	private Date updateTime;
	@TableField("user_id")
	private Integer userId;

}
