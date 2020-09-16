package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Product;
import utility.ConnectionManager;

public class ProductDAO implements ProductDAOInterface {
	ConnectionManager cm = new ConnectionManager();
    Connection con;
	@Override
	public int genratepid() throws ClassNotFoundException, SQLException {
		int id=0;
		con=ConnectionManager.getConnection();
		//ConnectionManager cm=new ConnectionManager();
		Statement st=con.createStatement();
		String sql="select count(id)+1 from product";
		ResultSet rs=st.executeQuery(sql);
		if(rs.next()) {
			id=rs.getInt(1);
			}
		
	int pid=id;
	
		return pid;
	}
//	public String generateProductId() throws Exception {
//		 int id = 0;
//		 String sql = "select count(id)+1 from product";
//		     con = cm.getConnection();
//		     Statement st = con.createStatement();
//		     ResultSet rs = st.executeQuery(sql);
//		     if(rs.next()) {
//			 id = Integer.parseInt(rs.getString(1));
//		     }
//		     String prodid = "prod"+id;
//		     con.close();
//		     return prodid ;
//	    }
//	
    public void addProduct(Product product, int quantity) throws Exception {
	 addStock(product,quantity);
	Connection con;
	con=ConnectionManager.getConnection();
	String  sql3 = "insert into product(id,name,price, description) values(?,?,?,?)";
	 PreparedStatement ps = con.prepareStatement(sql3);
	 ps.setInt(1, product.getProductid());
	 ps.setString(2, product.getProductname());
	 ps.setInt(3, product.getProductprice());
	 ps.setString(4, product.getProductdisc());
	 int x = ps.executeUpdate();
	 if(x==1) {
	     System.out.println("Product added.");
	 }else {
	     System.out.println("Error in adding products.");
	 }
	
	
    }

	@Override
	public void addStock(Product product, int quantity) throws Exception {
		String insertStock = "insert into stock(stockid,quantity) values(?,?)";
		con = ConnectionManager.getConnection();
		PreparedStatement ps = con.prepareStatement(insertStock);
		String stockid = product.getProductname();
		try {
		    	ps.setString(1, stockid);
		    	ps.setInt(2, quantity);
			ps.executeUpdate();
		}catch(Exception e) {
		    String updateStock = "update stock set quantity = "+quantity+"where stockid = "+stockid;
		    PreparedStatement ps1 = con.prepareStatement(updateStock);
		    ps1.setInt(1, quantity);
		}
		
	}


}
