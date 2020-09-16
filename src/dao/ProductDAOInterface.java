package dao;

import java.sql.SQLException;

import model.Product;

public interface ProductDAOInterface {
	public int genratepid() throws ClassNotFoundException, SQLException;
public void addProduct(Product product, int quantity) throws Exception ;
    
    // adding item stocks to database table
    public void addStock(Product product, int quantity) throws Exception;

}
