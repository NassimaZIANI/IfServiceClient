package client;

import java.rmi.RemoteException;
import java.util.HashMap;
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
		
		System.out.println("Welcome to our service");
		System.out.println("\nDo you have an account?");
		boolean keepGoing = true;
		int response = 0;
		while (keepGoing) {
		    System.out.println("1. yes");
		    System.out.println("2. no");
			Scanner scanner = new Scanner(System.in);
			response = scanner.nextInt();
		    switch (response) {
		    	case 1:
		    		keepGoing = false;
		    		login();
		    		break;
		        case 2:
		        	keepGoing = false;
		        	signup();
		            break;
		        default:
		            System.out.println("Unknown option");
		            break;
		    }
	    }
		
		Scanner scanner = new Scanner(System.in);
		if (user != null) {
			System.out.println(user);
			System.out.println("You have " + ifService.balanceValue(idUser));
			
			// currency
			System.out.println("\nPlease enter your country currency code");
			scanner = new Scanner(System.in);
			String currency = scanner.nextLine();
			
			// get all products
			String[] tab = ifService.getAllProduct();
			System.out.println("\nProduct List");
			if (tab == null) {
				System.out.println("\nThere is no products");
			} else {
				for(String i : tab) {
					System.out.println("id: " + i + " type: " + ifService.getType(i) + " name: " + ifService.getName(i) + " price: " + ifService.getPrice(i, currency) + " state: " + ifService.getState(i) + " note: " + ifService.getNote(i));
				}
			
				// select product
				placeOrder(tab, currency);
				optionMenu(tab, currency);
				
				if (cart.size() != 0) {
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
					
					// pay
					boolean b = ifService.buyProduct(tabId, priceTotal, idUser);
					
					// finish buying (result)
					if (b) {
						System.out.println("Thank you for your purchase");
						cart.clear();
						System.out.println("Your new balance is: " + ifService.balanceValue(idUser));
					} else {
						System.out.println("You don't have enough money!");
					}
				} else {
					System.out.println("Your cart is empty!");
				}
			}
		}
		
	}
	
	private static void signup() throws RemoteException {
		System.out.println("\n///////////////////////");
		System.out.println("SIGNUP");
		System.out.println("Please enter your ID : ");
		Scanner scanner = new Scanner(System.in) ;
		idUser = scanner.nextInt();
		System.out.println("Please enter your password : ");
		scanner = new Scanner(System.in);
		String password = scanner.nextLine();
		System.out.println("Please enter your last name : ");
		scanner = new Scanner(System.in);
		String lastName = scanner.nextLine();
		System.out.println("Please enter your first name : ");
		scanner = new Scanner(System.in);
		String firstName = scanner.nextLine();
		boolean sigup = ifService.signUp(idUser, password, firstName, lastName);
		if (sigup) {
			login();
		} else {
			System.out.println("You already have an account");
		}
	}

	public static String login() throws RemoteException {
		System.out.println("\n///////////////////////");
		System.out.println("LOGIN");
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
		
		if(user == null)
			System.out.println("Sorry! You have reached the maximum of tries");
		return user;
	}
	
	public static void optionMenu(String tab[], String currency) throws RemoteException{
		boolean keepGoing = true;
		int response = 0;
		while (keepGoing) {
	    	System.out.println("\nPlease select an option from the menu:");
		    System.out.println("1. Continue");
		    System.out.println("2. Finish & pay");
			Scanner scanner = new Scanner(System.in);
			response = scanner.nextInt();
		    switch (response) {
		    	case 1:
		    		placeOrder(tab, currency);
		    		break;
		        case 2:
		        	keepGoing = false;
		            break;
		        default:
		            System.out.println("Unknown option");
		            break;
		    }
	    }
	}

	private static void placeOrder(String tab[], String currency) throws RemoteException {
		System.out.println("\nPlease select the product's id that you want to buy");
		Scanner scanner = new Scanner(System.in);
		String idProduct = scanner.nextLine();
		boolean select = ifService.selectProduct(idProduct);
		
		if (select) {
			// add to cart
			for(String i : tab) {
				if (i.equals(idProduct)) {
					cart.put(idProduct, ifService.getPrice(i, currency));
					System.out.println("this product is added to your cart");
				}
			}
			
		} else {
			System.out.println("this product isn't available for now or doesn't exist");
		}
	}
}
