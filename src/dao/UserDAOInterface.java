package dao;

import java.sql.ResultSet;

import model.User;

public interface UserDAOInterface {
	
public void insertUserDetails(User user) throws Exception;
    
    // fetch result form DataBase
    public ResultSet getDb(String sql) throws Exception;
    
    // generate unique user id for each user
    public int generateUserId() throws Exception;
    
    // generate unique customer id for each customer
    public int generateCustomerId() throws Exception;
	

}
