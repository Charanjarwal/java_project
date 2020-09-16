package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Product;
import model.User;
import utility.ConnectionManager;

public class CrudDAO implements CrudDAOInterface {
BufferedReader br=new BufferedReader(new InputStreamReader (System.in));
ConnectionManager cm=new ConnectionManager();
Connection con;
	
	public void updateProduct(int proid) throws Exception {
		System.out.println("Select What you want to update.");
		System.out.println("1. Product Name.");
		System.out.println("2. Product Price.");
		System.out.println("3. Product Description.");
		System.out.println("4. Delete Product.");
		int choice = Integer.parseInt(br.readLine().trim());
		switch(choice) {
		case 1:
		    // update name
		    System.out.println("Enter New Name  :  ");
		    String name = br.readLine().trim();
		    String updateName = "update product set name ='"+name+"' where id="+proid;
		    updateDB(updateName);
		    break;
		case 2:
		    // update price
		    System.out.println("Enter New Price  :  ");
		    int price = Integer.parseInt(br.readLine().trim());
		    String updatePrice = "update product set price = "+price+" where id="+proid;
		    updateDB(updatePrice);
		    break;
		case 3:
		    // update description
		    System.out.println("Enter New Description  :  ");
		    String desc = br.readLine().trim();
		    String updateDes = "update product set description = '"+desc+"' where id="+proid;
		    updateDB(updateDes);
		    break;
		case 4:
		    // delete item
		    try {
			String delete = "delete from product where id = "+proid;
			    updateDB(delete);
		    }catch(Exception e) {
			System.out.println("Item cannot be deleted because of maintaining order history.");
		    }
		   break;
		}

}
		public void displayProductlist() throws Exception {
			String sql = "select * from product";
			ResultSet rs = getDB(sql);
			int srNo = 1;
			while(rs.next()) {
			   int id = rs.getInt(1);
			   String name = rs.getString(2);
			   int price = rs.getInt(3);
			   String desc = rs.getString(4);
			   System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
			   System.out.println(srNo+".\t "+"Product ID :  "+id+"\t\t Product Name :  "+name+"\t\t Price :  "+price+"\t\t Description :  "+desc);
			   srNo++;
			}
			   System.out.println();
			   System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
		    }

		public ArrayList<Product> addTocart(String nm, ArrayList<Product> list,int num) throws Exception {
		    String sql = "select product.id,product.name,product.price,product.description,stock.quantity from product inner join stock on product.name = stock.stockid where product.name='"+nm+"'";
			ResultSet rs = getDB( sql);
			while(rs.next()) {
			   int id = rs.getInt(1);
			   String name = rs.getString(2);
			   int price = rs.getInt(3);
			   String desc = rs.getString(4);
			   int qaunt = rs.getInt(5);
			   if(qaunt>=num) {
			       list.add(new Product(id,name,price*num, desc)); 
			   }else {
			       System.out.println("Stock not available");
			   }
			}
			return list;
	    }
		 public  int  displayCart(ArrayList<Product> cartlist) throws Exception{
			    String sql = null;
			    int cartTotal = 0; 
			    try {
				int lenOfList = cartlist.size();
				int n = 1;
				int itemprice=0;
				System.out.println();
				System.out.println("ITEMS IN CART : ");
				System.out.println();
				for (int i = 0; i < lenOfList; i++) {
					Product product = cartlist.get(i);
					int pid = product.getProductid();
					cartTotal = cartTotal+product.getProductprice();
					sql = "select price from product where id = "+pid;
					 ResultSet rs = getDB(sql);
					 while(rs.next()) {
					     itemprice = rs.getInt(1);
					 }
					System.out.println("___________________________________________________________________________________________________________________________________________________________________________");
					System.out.println("itemNo. : "+(i+n)+"\tItem : " +product.getProductprice()/itemprice+" Kg. "+product.getProductname()+" \tPrice : "+product.getProductprice()/itemprice+"x"+itemprice+" = "+product.getProductprice()+" Rs. \tDescription : "+product.getProductdisc());
					
				}
				/// total amount to pay
				System.out.println();
				System.out.println("TOTAL CART VALUE : "+cartTotal);
				System.out.println();
			    	}catch(Exception e) {
				System.out.println("CART IS EMPTY.");
				System.out.println();
				}
			    return  cartTotal;
			}
		 public ArrayList<Product> removeItem(int n,ArrayList<Product> cartlist) {
			    int index = n-1;
			    cartlist.remove(index);
			    System.out.println("item removed successfully");
			    return cartlist;
			}
		 public String placeOrder(ArrayList<Product> cartlist,int custid) throws Exception {
			    LocalDate date = LocalDate.now();
			    String orderNo = generateOrderNo();
			    String sql = "insert into orders(orderno,custid,orderdate) values(?,?,?)";
				 con = ConnectionManager.getConnection();
				 PreparedStatement ps = con.prepareStatement(sql);
				 ps.setString(1, orderNo);
				 ps.setInt(2, custid);
				 ps.setDate(3,Date.valueOf(date));
				int y =  ps.executeUpdate();				if(y==1) {
				    System.out.println("Order placed Successfuly.");
				}
				return orderNo;
			}
		 public String generateOrderNo() throws Exception {
			 int id = 0;
			 String sql = "select count(orderno)+1 from orders";
			     ResultSet rs = getDB(sql);
			     if(rs.next()) {
				 id = Integer.parseInt(rs.getString(1));
			     }
			     String ordNo = "order"+id;
			     return ordNo ;
		    }
		 public void updateStock(ArrayList<Product> cartlist,String orderNo) throws Exception {
		    	for(int i=0;i<cartlist.size();i++) {
		    	    Product p = cartlist.get(i);
		    	    String nm = p.getProductname();
		    	    int prodid = p.getProductid();
		    	    String sql = "select product.price,stock.quantity from product inner join stock on product.name=stock.stockid where product.name='"+nm+"'";
		    	    //// getting quantity from stock
		    	    ResultSet rs = getDB(sql);
		    	    int quantity = 0;
		    	    int price = 0;
		    	    while(rs.next()) {
		    		price  = rs.getInt(1);
		    		quantity= rs.getInt(2);
		    	    }
		    	    int Amount = p.getProductprice();
		    	    int no = Amount/price;
		    	    quantity = quantity-no;
		    	    String updatestock = "update  stock set quantity = ?  where stockid ='"+nm+"'";
		    	    con = ConnectionManager.getConnection();
		    	    PreparedStatement ps1 = con.prepareStatement(updatestock);
		    	    ps1.setInt(1, quantity);
		    	   // int y =ps1.executeUpdate();
//		    	    if(y==1) {
//				    System.out.println("Updated quantity in database.");
//				}
		    	    /// update table orderdetails
		    	    InsertOrderDetails(prodid,orderNo,no);
		    	}
		}
			

	private void InsertOrderDetails(int prodid, String orderNo, int no) throws SQLException {
		 String insertOrderDetails = "insert into orderdetails(productid,orderid,quantity) values(?,?,?)";
 	    PreparedStatement ps2 = con.prepareStatement(insertOrderDetails);
 	    ps2.setInt(1, prodid);
 	    ps2.setString(2, orderNo);
 	    ps2.setInt(3, no);
 	    //int y = ps2.executeUpdate();
			
		}
	 public String generateShipId() throws Exception {
		 int id = 0;
		 String sql = "select count(orderno)+1 from orders";
		     ResultSet rs = getDB(sql);
		     if(rs.next()) {
			 id = Integer.parseInt(rs.getString(1));
		     }
		     String ShipNo = "ShipNo"+id;
		     return ShipNo ;
	    }
	 public void shippingDetails(User user, String orderid, String shipid) throws SQLException {
		    LocalDate shipdate = LocalDate.now();
		    String shippingAddress = user.getAddress();
		    String contactNumber = user.getPhonenumber();
		    String sql = "insert into shipment(id,address,shipdate,contact,ordrid) values(?,?,?,?,?)";
		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setString(1, shipid);
		    ps.setString(2, shippingAddress);
		    ps.setDate(3, Date.valueOf(shipdate));
		    ps.setString(4,contactNumber);
		    ps.setString(5, orderid);
		  
		}
	 public void payment(String type,String inv,String orderid,int cartTotal) throws Exception {
		    LocalDate pdate = LocalDate.now();
		    String sql = "insert into payment values(?,?,?,?,?)";
		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setString(1, inv);
		    ps.setString(2, type);
		    ps.setDate(3, Date.valueOf(pdate));
		    ps.setInt(4, cartTotal);
		    ps.setString(5,orderid);
		    ps.executeUpdate();
		}
	 public String generateInvoice() throws Exception {
		    String sql = "select count(payno)+1 from payment";
		    int shipid = 0;
		     ResultSet rs = getDB(sql);
		     if(rs.next()) {
			 shipid = Integer.parseInt(rs.getString(1));
		     }
		    String inv = "INVO"+shipid;
		    return inv;
		}
	private ResultSet getDB(String sql) throws ClassNotFoundException, SQLException {
		con = ConnectionManager.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		return rs;
		}
	private void updateDB(String updateName) throws ClassNotFoundException, SQLException {
		con = ConnectionManager.getConnection();
		PreparedStatement ps = con.prepareStatement(updateName);
		    int x = ps.executeUpdate();
			if(x==1) {
			    System.out.println("Updated Successfully.");
			}
		
	}
	}


	