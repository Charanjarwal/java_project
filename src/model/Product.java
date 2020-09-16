package model;

public class Product {
	
	private int productid;
	private String productname;
	private int productprice;
	private String productdisc;
	private int productquantity;
	
	public Product(int id, String name, int i, String desc) {
		this.productid=id;
		this.productname=name;
		this.productprice=i;
		this.productdisc=desc;
		
	}
	public Product() {

    }
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getProductprice() {
		return productprice;
	}
	public void setProductprice(int productprice) {
		this.productprice = productprice;
	}
	public String getProductdisc() {
		return productdisc;
	}
	public void setProductdisc(String productdisc) {
		this.productdisc = productdisc;
	}
	public int getProductquantity() {
		return productquantity;
	}
	public void setProductquantity(int productquantity) {
		this.productquantity = productquantity;
	}
	

}
