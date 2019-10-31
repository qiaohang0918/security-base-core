package com.cloud.cloudcommon.pojo;

import javax.persistence.*;

/**
 * tb_user 实体类
 * Fri Sep 27 15:16:28 CST 2019
 * @lry
 */ 
@Entity
public class Tb_user{

	private String user_id;
	private String name;
	private String password;
	private String phone;
	private String create_time;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getUser_id(){
		return user_id;
	}

	public void setUser_id(String user_id){
		this.user_id=user_id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getCreate_time(){
		return create_time;
	}

	public void setCreate_time(String create_time){
		this.create_time=create_time;
	}

}

