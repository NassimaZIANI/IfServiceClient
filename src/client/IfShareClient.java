package client;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.xml.rpc.ServiceException;

import ifService.IfService;
import ifService.IfServiceServiceLocator;

public class IfShareClient {
	
	static private int loginAttempt = 3;
	static private String user = null;
	static IfService ifService;
	static int idUser;
	static Map<String, Double> cart = new HashMap<String, Double>();
	
	public static void main(String[] args) throws ServiceException, RemoteException {
		ifService = new IfServiceServiceLocator().getIfService();
		Scanner scanner = new Scanner(System.in);
		login();
		if (user == null) {
			System.out.println("Sorry! You have reached the maximum of tries");
		} else {
			System.out.println(user);
			
			// currency
			System.out.println("Please enter your country currency code");
			scanner = new Scanner(System.in);
			String currency = scanner.nextLine();
			
			// get all products
			String[] tab = ifService.getAllProduct();
			System.out.println("Product List");
			if (tab == null) {
				System.out.println("There is no products");
			} else {
				for(String i : tab) {
					System.out.println("id: " + i + " type: " + ifService.getType(i) + " name: " + ifService.getName(i) + " price: " + ifService.getPrice(i, currency));
				}
			
				// select product
				System.out.println("Please select the product's id that you want to buy");
				scanner = new Scanner(System.in);
				String idProduct = scanner.nextLine();
				boolean select = ifService.selectProduct(idProduct);
				
				if (select) {
					
					// add to cart
					for(String i : tab) {
						if (i.equals(idProduct)) {
							cart.put(idProduct, ifService.getPrice(i, currency));
						}
					}
					
					// TODO ask if finish
					
					// buy products
					String[] tabId = new String[cart.size()];
					double priceTotal = 0.0;
					int i = 0;
					for(Entry<String, Double> e : cart.entrySet()) {
						String idCart = e.getKey();
						double priceCart = e.getValue();
						tabId[i] = idCart;
						priceTotal += priceCart;
						i++;
					}
					boolean b = ifService.buyProduct(tabId, priceTotal, idUser);
					
					// finish buying (result)
					if (b) {
						cart.clear();
						System.out.println("You have " + ifService.balanceValue(idUser));
					} else {
						System.out.println("You don't have enough money!");
					}
					
				} else {
					System.out.println("this product isn't available for now");
				}
				
			}
		}
		
		
		// TODO
		// signup or login ?
		// cart
		
	}
	
	public static String login() throws RemoteException {
		while(loginAttempt !=0 && user == null) {
			System.out.println("Please enter your ID : ");
			Scanner scanner = new Scanner(System.in) ;
			idUser = scanner.nextInt();
			System.out.println("Please enter your password : ");
			scanner = new Scanner(System.in);
			String password = scanner.nextLine();
			user = ifService.login(idUser, password);
			loginAttempt--;
			if (user == null) {
				System.out.println("Wrong id or password, you have " + loginAttempt + " more tries");
			}
		}
		return user;
	}
}
