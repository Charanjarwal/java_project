package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dao.CrudDAO;
import dao.ProductDAO;
import dao.UserDAO;
import dao.VarifyUserDAO;
import model.Product;
import model.User;
import service.ValidateContent;

public class Main {

	public static void main(String[] args) throws Exception {
		String username;
		String password;
		String firstname;
		String lastname;
		String address;
		String phonenumber;
		 String orderid;
		int age;
		ValidateContent valcont=new ValidateContent();
		User user=new User();
		UserDAO userdao=new UserDAO();
		VarifyUserDAO varifyud=new VarifyUserDAO();
		Product product=new Product();
		ArrayList<Product> list = new ArrayList<Product>();
		ProductDAO productdao=new ProductDAO();
		CrudDAO cruddao=new CrudDAO();
		
		BufferedReader br=new BufferedReader(new InputStreamReader (System.in));
		String yes=null;
				do {
		System.out.println(":::::::::::::::--- Wellcome To The FreshMarket(E-Mandi) ---::::::::::::::");
		System.out.println(" 1:Registration \t\t"
				            + "2:Admin Control \t\t"
				            +"3:Login page");
		System.out.println("Enter your choice");
		int x=Integer.parseInt(br.readLine().trim());
		switch(x)
		{
		case 1:
			System.out.println("Enter the new Username");
			System.out.println("[Username must contain Uppercase and Lowercase Characters with length(6-10: characters)]");
			username=br.readLine();
			System.out.println("Enter the new password");
			System.out.println("[Password must contain Lowercase,Uppercase and Number with length(6-10: characters)]");
			password=br.readLine();
			boolean valid=valcont.validateunamepass(username, password);
			
			if(valid) {
				
				user.setUsername(username);
				user.setPassword(password);
				System.out.println("Enter Your Firstname : ");
				firstname=br.readLine();
				System.out.println("Enter Your lasttname");
				lastname=br.readLine();
				
				System.out.println("Enter Your Address");
				address=br.readLine();
				System.out.println("Enter Your Age");
				age=br.read();
				System.out.println("Enter Your Phone Number");
				phonenumber=br.readLine();
				
				int customerid=userdao.generateCustomerId();
				int userid=userdao.generateUserId();
				user.setCustomerid(customerid);
				user.setUserid(userid);
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setAddress(address);
				user.setAge(age);
				user.setPhonenumber(phonenumber);
				
				
				//fetch the values into table
    		    userdao.insertUserDetails(user);
    		   
				
				
				
				
				
			}
			break;
		case 2:
			System.out.println(":::::::::::::::::::::---Hii Admin---::::::::::::::::::::::");
			System.out.println("::::::::::::---WellCome To The Admin Page---:::::::::::::");
			System.out.println("Enter your Username :-");
			System.out.println("[Username must contain Uppercase and Lowercase Characters with length(6-10: characters)]");
			String auname=br.readLine();
			
			System.out.println("Enter your Password:-");
			System.out.println("[Password must contain Lowercase,Uppercase and Number with length(6-10: characters)]");
			String apassword=br.readLine();
			boolean avalid=varifyud.varifyadmin(auname, apassword);
			if(avalid) {
				do {
				
				System.out.println("::::::-Admin Login Succesfull-:::::::::");
				System.out.println("");
				System.out.println("\t Choose options : ");
			    System.out.println("\t 1. Add Product to the freshMarket ");
			    System.out.println("\t 2. Delete Product or Update on FreshMarket");
			    int x1=Integer.parseInt(br.readLine().trim());
			    int quantity=0;
			   switch(x1) {
			   case 1:
				   
				   System.out.print("\t Enter nunber of products you want to add :  ");
					 int  n = Integer.parseInt(br.readLine().trim());
					 for(int i=0;i<n;i++) {
					     System.out.print("\t\t Enter Product Name :    ");
					     String pname = br.readLine().trim();
					     System.out.print("\t\t Add Product Price :    ");
					     int  price = Integer.parseInt(br.readLine().trim());
					     System.out.print("\t\t Add Product Description :    ");
					     String pdes = br.readLine().trim();
					     System.out.print("\t\t Add Product Quatity in Kg  :    ");
					     quantity = Integer.parseInt(br.readLine().trim());
					     
					     /// getting product id
					     int pid = productdao.genratepid();
					     product.setProductid(pid);
					     product.setProductname(pname);
					     product.setProductprice(price);
					     product.setProductquantity(quantity);
					     product.setProductdisc(pdes);
					    
					     /// insert in product table
					     productdao.addProduct(product,quantity);}
					break;
			   case 2:
				   try {
					//displayproduct list AND update product
					cruddao.displayProductlist();
					System.out.println("\t ENTER PRODUCT_ID TO UPDATE OR DELETE PRODUCT .");
					int proid = Integer.parseInt(br.readLine().trim());
					System.out.println();
					cruddao.updateProduct(proid);
					}catch(Exception e) {
					    System.out.println("\t Invalid Input");
					}
					break;
				    }  
				    System.out.print("\t Do you want to continue  Admin Pannel ? yes/no:    ");
				    yes = br.readLine();
				}
				while(yes.equalsIgnoreCase("yes"));
			}
			else {
				System.out.println("inalid");
			}
				
			 break;    
			   
			 
			
			
		case 3:
			System.out.println("::::::::::::::::---Hii User---::::::::::::::");
			System.out.println("::::::::::---Wellcome to Login Page--- ::::::::::::");
			System.out.println("Enter your Username :- ");
			String uname=br.readLine();
			System.out.println("Enter Your Password :-");
			String pass=br.readLine();
			boolean valid1=varifyud.varifyuserdetails(uname, pass);
			if(valid1) {
				
				System.out.println("");
				 user =varifyud.loadUserdetails(uname,pass);
				    user = varifyud.loadCustomerDetails(user);
				    System.out.println("\t\t Login successful");
				    int x1 = 0;
				    do
					{
					System.out.println();
				    System.out.println("\t\t Choose options : ");
				    System.out.println("\t\t 1. Home Page : ");
				    System.out.println("\t\t 2. Go to cart : ");
				    System.out.println("\t\t 3. User details and order History : ");
				     x1 = Integer.parseInt(br.readLine().trim());
				     System.out.println();
				     String nm;
				     int num;
				  
				    switch(x1){
					case 1:
					    do {
						  /// show products
						    cruddao.displayProductlist();
						    try {
						    System.out.print("Add to Cart Item by name :     ");
						    nm  = br.readLine().trim();
						    System.out.print("\t\t Enter Quantity :      ");
						    num = Integer.parseInt(br.readLine().trim());
						    list = cruddao.addTocart(nm,list,num);
						    }catch(Exception e) {
							System.out.println("\t\t Wrong Input ....not Match with items..");
						    }
						    System.out.print("\t\t Want to add more items ? yes/no : ");
						    yes = br.readLine();
					    }while(yes.equals("yes"));
					    break;
					case 2:
					   int CartValue = cruddao.displayCart(list);
					   
					 System.out.println("do u wants to remove item");
					 String ans1=br.readLine();
					 if(ans1.equalsIgnoreCase("yes")) {
					 try {
						    System.out.print("\t\t Want to remove any item ? Enter Item.No.  : ");
						    int index =Integer.parseInt(br.readLine().trim());
						   list =  cruddao.removeItem(index,list);
						}catch(Exception e) {
						    System.out.println();
						}
					    System.out.println("");
					  
						}
						System.out.print("\t \t Want to place order ? yes/no : ");
						yes = br.readLine();
						if(yes.equals("yes")) {
						    System.out.print("\t Select Payment type : card / cash on delivery(cod) / wallet ? :     ");
						    String type = br.readLine().trim();
						    System.out.println("You Selected "+type);
						    orderid = cruddao.placeOrder(list,user.getCustomerid());
						    cruddao.updateStock(list,orderid);
						    String shipId = cruddao.generateShipId();
						    cruddao.shippingDetails(user,orderid,shipId);
						    String inv = cruddao.generateInvoice();
						    cruddao.payment(type,inv,orderid,CartValue);
						    //cruddao.pdfBillGeneration(list, user);
						    list.clear();
						    System.out.println("\t \t Order Placed Successfuly .... ! Congratulations ..");
						}
					    break;
					case 3:
					    /// display user details
					    userdao.fetchUserDetails(user.getCustomerid());
					    userdao.fetchOrderHistory(user.getCustomerid());
					    break;
				    }  
				    System.out.println();
				    System.out.print("\t \t Do you want to continue UserPanel ?   yes/no   :     ");
				    yes = br.readLine();
				} while(yes.equals("yes"));
				}else {
					System.out.println("\t\t Incorrect username and password.");
				}
				
				break;
				
				  
			
			
			
		}
		System.out.print(" \t Do you want to continue Login Page ? yes/no:    ");
		yes = br.readLine();
		}while(yes.equalsIgnoreCase("yes"));
	}
}

				

	

