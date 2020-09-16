package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;
import utility.ConnectionManager;

public class VarifyUserDAO{
	
	public boolean varifyuserdetails(String username,String password) throws ClassNotFoundException, SQLException {
		ConnectionManager con = new ConnectionManager();
		Statement st = ConnectionManager.getConnection().createStatement();
		ResultSet rs = st.executeQuery("SELECT username,password from USERDETAILSEM");
		String uname;		
		String pass;
		while(rs.next()) {
			uname=rs.getString("username");
			pass=rs.getString("password");
			if(username.equals(uname) && password.equals(pass)) {
				return true;
			}	
	
        }
		ConnectionManager.getConnection().close();
		return false;
	}
	public boolean varifyadmin(String auname,String apassword) throws ClassNotFoundException, SQLException {
		Statement st=ConnectionManager.getConnection().createStatement();
		String sql="select username,password from admindetailsem";
		ResultSet rs=st.executeQuery(sql);
		String auname1;
		String apass1;
		while(rs.next()) {
		auname1=rs.getString("username");
		apass1=rs.getString("password");
		if(auname1.equals(auname)&&apass1.equals(apassword)) {
			
		return true;
		}
			
			
		}
		
		
		
		return false;
	}
	public User loadUserdetails(String username , String password) throws SQLException, Exception {
		int customerid =0;
		int userid = 0;
		User user = new User();
		ConnectionManager con = new ConnectionManager();
		Statement st = ConnectionManager.getConnection().createStatement();
		ResultSet rs = st.executeQuery("select userid,customerid from userdetailsem where username = '"+username+"' and password = '"+password+"'");
		while(rs.next())
		{ 	
		    userid = rs.getInt(1);
		    customerid = rs.getInt(2);
		}
		ConnectionManager.getConnection().close();
		user.setUserid(userid);                                                             
		user.setCustomerid(customerid);
		user.setFirstname(username);
		user.setPassword(password);
		return user;
	    }
	public User loadCustomerDetails(User user) throws Exception {
		int customerid = user.getCustomerid();
		ConnectionManager cm = new ConnectionManager();
		Statement st = ConnectionManager.getConnection().createStatement();
		ResultSet rs = st.executeQuery("select customerid,firstname,lastname,phonenumber,age,address from customersem where customerid="+customerid);
		while(rs.next()) {
		    user.setCustomerid(rs.getInt(1));
		    user.setFirstname(rs.getString(2));
		    user.setLastname(rs.getString(3));
		    user.setPhonenumber(rs.getString(4));
		    user.setAge(rs.getInt(5));
		    user.setAddress(rs.getString(6));
		   
	
		}
		ConnectionManager.getConnection().close();
		return user;
	    }
	
	
	
}

