package com.summer.isnow.dto;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonView;
import com.summer.icommon.utils.View;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
public class UserAdminView  {

	public interface UserSimpView{};

	private Integer id;
	@JsonView(value = View.Base.class )
	private String username;
	@JsonView(value = View.Base.class )
	private String password;

	private String icon;
	private String email;
	@ApiModelProperty()
	private String nickName;
	@JsonView(value = View.Base.class )
	private String note;
	@JsonView(value = View.Base.class )
	private Map<String,String> map;
	@JsonView(value = View.Base.class )
	private String []  ss;
	@JsonView(value = View.Base.class )
	private int [] intDemo;
	@JsonView(value = View.Base.class )
	private Integer b =null;
	@JsonView(value = View.Base.class )
	private boolean bbbb ;
	@JsonView(value = View.Base.class )
	private List<String> dd;
	@ApiModelProperty()
	private Date createTime;
	@ApiModelProperty()
	private Date loginTime;
	private Integer status;
	@ApiModelProperty()
	private Date updateTime;
	private String salt;
	private List<String> roles;

}
