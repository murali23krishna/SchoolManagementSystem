package com.murali.school.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PERMISSION_ROLE")
public class PermissionRole {

	@Id
	@GeneratedValue
	private int permission_Id;
	private int role_id;
	public int getPermission_Id() {
		return permission_Id;
	}
	public void setPermission_Id(int permission_Id) {
		this.permission_Id = permission_Id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	
	
}
