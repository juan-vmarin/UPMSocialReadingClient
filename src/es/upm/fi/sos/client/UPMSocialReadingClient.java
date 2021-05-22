package es.upm.fi.sos.client;

import java.rmi.RemoteException;
import java.util.Scanner;
import es.upm.fi.sos.client.UPMSocialReadingStub.*;

public class UPMSocialReadingClient {

	private static Scanner sc;

	public static void main(String[] args) throws Exception {
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		System.out.println("Escoja la prueba a realizar");
		sc = new Scanner(System.in);

		int option;
		boolean finish = false;
		while (!finish) {
			System.out.println("1. Iniciar sesion\n" + "2. Cerrar sesion\n"
					+ "3. Añadir usuario\n" + "4. Eliminar usuario\n"
					+ "5. Cambiar contraseña\n" + "6. Buscar usuario\n"
					+ "7. Añadir amigo\n" + "8. Eliminar amigo\n"
					+ "9. Lista de amigos\n" + "10. Añadir lectura\n"
					+ "11. Lista de lecturas\n" + "12. Lecturas de amigo\n"
					+ "13. Salir\n");

			option = sc.nextInt();
			sc.nextLine();
			switch (option) {
			case 1:
				login();
				break;
			case 2:
				logout();
				break;
			case 3:
				addUser();
				break;
			case 4:
				removeUser();
				break;
			case 5:
				changePwd();
				break;
			case 6:
				searchUser();
				break;
			case 7:
				addFriend();
				break;
			case 8:
				deleteFriend();
				break;
			case 9:
				friendList();
				break;
			case 10:
				addReading();
				break;
			case 11:
				readingList();
				break;
			case 12:
				friendReadings();
				break;
			case 13:
				finish = true;
				break;
			default:
				break;
			} // end switch

		} // end while
		sc.close();
	}

	private static void login() throws RemoteException { // 1
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		User user;
		Login login;
		LoginResponse loginResponse;
		
		boolean exito = true;
		
		// prueba para logear admin
		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);
		
		if(!loginResponse.get_return().getResponse()){
			exito = false;
		}
		
		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		user = new User();
		user.setName("grupo13333");
		user.setPwd("sucontrasenia");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);
		
		if(loginResponse.get_return().getResponse()){
			exito = false;
		}
		
		if(exito){
			System.out.println("Exito en las pruebas de Login");
		}else{
			System.out.println("Fallo en las pruebas de Login");
		}
		
	}

	private static void logout() throws RemoteException { // 2
	}

	private static void addUser() throws RemoteException { // 3
//		UPMSocialReadingStub stub = new UPMSocialReadingStub();
//		stub._getServiceClient().engageModule("addressing");
//		stub._getServiceClient().getOptions().setManageSession(true);
//		
//		
//		Username username = new Username();
//		
//		AddUser addUser = new AddUser();
//		addUser.setArgs0(user);
	}

	private static void removeUser() throws RemoteException { // 4

	}

	private static void changePwd() throws RemoteException { // 5

	}

	private static void searchUser() throws RemoteException { // 6

	}

	private static void addFriend() throws RemoteException { // 7

	}

	private static void deleteFriend() throws RemoteException { // 8

	}

	private static void friendList() throws RemoteException { // 9

	}

	private static void addReading() throws RemoteException { // 10

	}

	private static void readingList() throws RemoteException { // 11

	}

	private static void friendReadings() throws RemoteException { // 12

	}

}