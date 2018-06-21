package com.rest.api.dao;

import com.rest.api.models.UserDetails;

public interface UserDao {
	public void saveUser(UserDetails users);
	
	public UserDetails getByToken(String token);

}
