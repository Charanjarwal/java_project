package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.User;
import utility.ConnectionManager;

public class UserDAO implements UserDAOInterface{
	ConnectionManager cm = new ConnectionManager();
	Connection con;
			

	
	public void insertUserDetails(User user) throws Exception {
		con=ConnectionManager.getConnection();
		String sql1="insert into userdetailsem(userid,customerid,username,password) values(?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(sql1);
				ps.setInt(1, user.getUserid());
	     ps.setInt(2,user.getCustomerid());
	     ps.setString(3,user.getUsername());
	     ps.setString(4,user.getPassword());
	     String sql2="insert into customersem(customerid,firstname,lastname,phonenumber,age,address) values(?,?,?,?,?,?)";
	     
	     PreparedStatement ps1=con.prepareStatement(sql2);
	     
	     ps1.setInt(1,user.getCustomerid());
	     ps1.setString(2,user.getFirstname());
	     ps1.setString(3,user.getLastname());
	     ps1.setString(4,user.getPhonenumber());
	     ps1.setInt(5,user.getAge());
	     ps1.setString(6,user.getAddress());
	     
	    
	  
	     ps.executeUpdate(); 
	     ps1.executeUpdate();
	     
	     
		
	}

	 public ResultSet getDb(String sql) throws Exception {
		 con=ConnectionManager.getConnection();
		
		// Statement st=con.createStatement(); //
		 PreparedStatement st=con.prepareStatement(sql);
		 ResultSet rs=st.executeQuery(sql);
	    
	     return rs; 
	 }
	  
	 // generate unique user id for each user
	 public int generateUserId() throws Exception {
	     int userid = 0;
	     
	     String sql = "select count(userid)+1 from userdetailsem";
	     ResultSet rs = getDb(sql);
	     if(rs.next()) {
		 userid = Integer.parseInt(rs.getString(1));
	     }
	     return userid;
	 }
	 
	 // generate unique customer id for each customer
	 public int generateCustomerId() throws Exception {
	     int customerid = 0;
	     String sql = "select count(customerid)+1 from customersem";
	     ResultSet rs = getDb(sql);
	     if(rs.next()) {
		customerid = rs.getInt(1);
	     }
	     return customerid;
	 }
	 public void fetchOrderHistory(int custid) throws Exception {
	     String ordid = null;
	     
	     String prodid = null;
	     String pname = null;
	     int prdprice =0;
	     int quant = 0;
	     int totalamount = 0;
	     String invono = null;
	     String ptype = null;
	     Date paydate = null ;
	    Date shipdate = null;
	     String contact = null;
	     String add  = null;

	     String getOrderhistory = "select orders.orderno,orderdate from orders where customerid = "+custid;
	     ResultSet rs = getDb(getOrderhistory);
	     while(rs.next()) {
		 ordid = rs.getString(1);
		 
		 String getProductDetails = "select orderdetails.productid,product.name,product.price,orderdetails.quantity\r\n" + 
		 	"from orderdetails\r\n" + 
		 	"inner join product on orderdetails.productid = product.id \r\n" + 
		 	"where orderdetails.orderid = '"+ordid+"'";
		 ResultSet rs1 = getDb(getProductDetails);
		 while(rs1.next()) {
		     prodid = rs1.getString(1);
		     pname = rs1.getString(2);
		     prdprice = rs1.getInt(3);
		     quant = rs1.getInt(4);
		 System.out.println();
		 System.out.println("Order Id : "+ordid+"\t\t Product Id : "+prodid+"\t\t Product name : "+pname+"\t\t Price : "+prdprice+"\t Quantity : "+quant+" kg.");
		 
		 
		 String getPayDetails = "select payment.amount,payment.payno,payment.type,payment.paydate,\r\n" + 
		 	"shipment.contact,shipment.address,shipment.shipdate\r\n" + 
		 	"from shipment inner join payment on payment.ordid = shipment.ordrid\r\n" + 
		 	"where ordrid = '"+ordid+"'";
		 
		 ResultSet rs2 = getDb(getPayDetails);
		 while(rs2.next()) {
		     totalamount = rs2.getInt(1);
		     invono = rs2.getString(2);
		     ptype  = rs2.getString(3);
		    paydate = rs2.getDate(4);
		     contact = rs2.getString(5);
		     add = rs2.getString(6);
		     shipdate = rs2.getDate(7);
		 }
	     }
		 System.out.println();	
	     System.out.println("---------------------------------  Total amount : "+totalamount+"\t\t Invoice Number : "+invono+"  ---------------------------------");
	     System.out.println("");
	     
	    System.out.println("\t\t Payment Date : "+paydate+"\t\t Type of Payment : "+ptype+"\t\t Shiping Date : "+shipdate);
	     System.out.println();	
	     System.out.println("\t\t Delivery Address : "+add+"\t\t Mobile Number : "+contact);	
	     System.out.println();	
	     System.out.println("|=============================================================================|");
	 }
	 }
	 public void fetchUserDetails(int i) throws Exception {
	     String username = null;
	     String pass = null;
	     String firstname = null;
	     String lastname = null;
	   
	     int age =0;
	     
	     String add = null;
	     String contact = null;
	     String userDetailsFetch = "select userdetailsem.username, userdetailsem.password,\r\n" + 
		     	"customersem.firstname,customersem.lastname,customersem.age,customersem.address,customersem.phonenumber\r\n" + 
		     	"from userdetailsem\r\n" + 
		     	"inner join customersem on userdetailsem.customerid = customersem.customerid\r\n" + 
		     	"where customerid = "+i;
	     ResultSet rs = getDb(userDetailsFetch);
	     while(rs.next()) {
		 username = rs.getString(1);
		 pass = rs.getString(2);
		 firstname = rs.getString(3);
		 lastname = rs.getString(4);
		age = rs.getInt(5);
		 add = rs.getString(6);
		 contact = rs.getString(7);
	     }
	     // fetching data from database

	     System.out.println("*___________________________________User details_____________________________________________*");
	     System.out.println();	
	     System.out.println("User Name : "+username+"\t First Name : "+firstname+"\t Last Name : "+lastname);
	     
	     System.out.println("Age : "+age+"" );
	     System.out.println("Contact Number : "+contact);
	     System.out.println("Address : "+add); 
	     System.out.println("Password : "+pass);
	     System.out.println();	
	     System.out.println("|=============================================================================|");
	     System.out.println();	
	     }
	 
	
	

}
