package org.github.albertyang2007.selfcareportal.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	private String userId;
	private String password;
	private String nickName;
	
	@Id
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Override
	public String toString(){
		return "userId: "+userId+" nickName: "+nickName;
	}
	
	@Override
	public int hashCode(){
		return userId.hashCode();
	}
	
	@Override 
	public boolean equals(Object o){
		if(!(o instanceof User)){
			return false;
		}
		User compared = ((User) o);
		return compared.getUserId().equals(userId);
	}

}
