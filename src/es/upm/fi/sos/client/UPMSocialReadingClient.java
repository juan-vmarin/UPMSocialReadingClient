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

		if (!loginResponse.get_return().getResponse()) {
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

		if (loginResponse.get_return().getResponse()) {
			exito = false;
		}

		if (exito) {
			System.out.println("Exito en las pruebas de Login");
		} else {
			System.out.println("Fallo en las pruebas de Login");
		}

	}

	private static void logout() throws RemoteException { // 2
	}

	private static void addUser() throws RemoteException { // 3
		// UPMSocialReadingStub stub = new UPMSocialReadingStub();
		// stub._getServiceClient().engageModule("addressing");
		// stub._getServiceClient().getOptions().setManageSession(true);
		//
		//
		// Username username = new Username();
		//
		// AddUser addUser = new AddUser();
		// addUser.setArgs0(user);
	}

	private static void removeUser() throws RemoteException { // 4

	}

	private static void changePwd() throws RemoteException { // 5

	}

	private static void searchUser() throws RemoteException { // 6

	}

	private static void addFriend() throws RemoteException { // 7
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		User user;
		Login login;
		LoginResponse loginResponse;

		boolean exito = true;

		// Login admin
		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login admin, addFriend"); // borrar una
																	// vez
																	// funcione
		}

		// Crear 2 usuarios
		User user1 = new User();
		user1.setName("userAF1");
		user1.setPwd("pwd");
		Username usname1 = new Username();
		usname1.setUsername(user1.getName());

		User user2 = new User();
		user2.setName("userAF2");
		user2.setPwd("pwd");
		Username usname2 = new Username();
		usname2.setUsername(user2.getName());

		// Aniadir los 2 usuarios
		AddUser addUser = new AddUser();
		AddUserResponseE addUserResponseE;

		addUser.setArgs0(usname1);
		addUserResponseE = stub.addUser(addUser);
		addUser.setArgs0(usname2);
		addUserResponseE = stub.addUser(addUser);

		if (!addUserResponseE.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add user, addFriend"); // borrar una
																// vez funcione
		}

		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user1
		Login login1 = new Login();
		login1.setArgs0(user1);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login user1, addFriend"); // borrar una
																	// vez
																	// funcione
		}

		// Add friend
		AddFriend addFriend = new AddFriend();

		Username usnameF = new Username();
		usnameF.setUsername(user2.getName());
		addFriend.setArgs0(usnameF);
		AddFriendResponse addFriendResponse = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en addFriend"); // borrar una vez funcione
		}

		// Usuario no existente
		Username usname3 = new Username();
		usname3.setUsername("userNoExist");
		addFriend.setArgs0(usname3);
		addFriendResponse = stub.addFriend(addFriend);
		if (addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en aniadir amigo no existente, addFriend"); // borrar
																				// una
																				// vez
																				// funcione
		}

		// Texto final
		if (exito) {
			System.out.println("Exito en las pruebas de addFriend");
		} else {
			System.out.println("Fallo en las pruebas de addFriend");
		}

		// Logout user1
		logout = new Logout();
		stub.logout(logout);

	}

	private static void deleteFriend() throws RemoteException { // 8
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		User user;
		Login login;
		LoginResponse loginResponse;

		boolean exito = true;

		// Login admin
		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login admin, deleteFriend"); // borrar
																		// una
																		// vez
																		// funcione
		}

		// Crear 2 + 1 usuarios
		User user1 = new User();
		user1.setName("userDF1");
		user1.setPwd("pwd");
		Username usname1 = new Username();
		usname1.setUsername(user1.getName());

		User user2 = new User();
		user2.setName("userDF2");
		user2.setPwd("pwd");
		Username usname2 = new Username();
		usname2.setUsername(user2.getName());

		User user3 = new User();
		user3.setName("userDF3");
		user3.setPwd("pwd");
		Username usname3 = new Username();
		usname2.setUsername(user3.getName());

		// Aniadir los 3 usuarios
		AddUser addUser = new AddUser();
		AddUserResponseE addUserResponseE;

		addUser.setArgs0(usname1);
		addUserResponseE = stub.addUser(addUser);
		addUser.setArgs0(usname2);
		addUserResponseE = stub.addUser(addUser);
		addUser.setArgs0(usname3);
		addUserResponseE = stub.addUser(addUser);

		if (!addUserResponseE.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add user, deleteFriend"); // borrar una
																	// vez
																	// funcione
		}

		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user1
		Login login1 = new Login();
		login1.setArgs0(user1);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login user1, deleteFriend"); // borrar
																		// una
																		// vez
																		// funcione
		}

		// Add friend
		AddFriend addFriend = new AddFriend();

		addFriend.setArgs0(usname2);
		AddFriendResponse addFriendResponse = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add friend ,deleteFriend"); // borrar
																		// una
																		// vez
																		// funcione
		}

		// Remove friend
		RemoveFriend removeFriend = new RemoveFriend();
		removeFriend.setArgs0(usname2);
		RemoveFriendResponse removeFriendResponse = stub
				.removeFriend(removeFriend);

		if (!removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en deleteFriend"); // borrar una vez
															// funcione
		}

		// No es amigo
		// No creo q esto sea necesario
		FriendList friendList = new FriendList();
		String[] friends = friendList.getFriends();
		boolean found = false;
		for (String name : friends) {
			if (name.equals(user3.getName())) {
				found = true;
			}
		}

		removeFriend.setArgs0(usname3);
		removeFriendResponse = stub.removeFriend(removeFriend);
		if (removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en eliminar usuario no amigo, deleteFriend"); // borrar
																					// una
																					// vez
			// funcione
		}

		// No existe
		String nameFail = "userNoExist";
		Username usname4 = new Username();
		usname4.setUsername(nameFail);

		removeFriend.setArgs0(usname4);
		removeFriendResponse = stub.removeFriend(removeFriend);
		if (removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en eliminar usuario no existente, deleteFriend"); // borrar
																						// una
																						// vez
			// funcione
		}

		// Texto final
		if (exito) {
			System.out.println("Exito en las pruebas de deleteFriend");
		} else {
			System.out.println("Fallo en las pruebas de deleteFriend");
		}

		// Logout user1
		logout = new Logout();
		stub.logout(logout);
	}

	private static void friendList() throws RemoteException { // 9
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		User user;
		Login login;
		LoginResponse loginResponse;

		boolean exito = true;

		// Login admin
		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login admin, friendList"); // borrar
																	// una vez
																	// funcione
		}

		// Crear 3 usuarios
		User user1 = new User();
		user1.setName("userFL1");
		user1.setPwd("pwd");
		Username usname1 = new Username();
		usname1.setUsername(user1.getName());

		User user2 = new User();
		user2.setName("userFL2");
		user2.setPwd("pwd");
		Username usname2 = new Username();
		usname2.setUsername(user2.getName());

		User user3 = new User();
		user3.setName("userFL3");
		user3.setPwd("pwd");
		Username usname3 = new Username();
		usname3.setUsername(user3.getName());

		// Aniadir los 3 usuarios
		AddUser addUser = new AddUser();
		AddUserResponseE addUserResponseE;

		addUser.setArgs0(usname1);
		addUserResponseE = stub.addUser(addUser);
		addUser.setArgs0(usname2);
		addUserResponseE = stub.addUser(addUser);
		addUser.setArgs0(usname3);
		addUserResponseE = stub.addUser(addUser);

		if (!addUserResponseE.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add user, friendList"); // borrar una
																	// vez
																	// funcione
		}

		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user1
		Login login1 = new Login();
		login1.setArgs0(user1);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login user1, friendList"); // borrar
																	// una vez
																	// funcione
		}

		// Add friends
		AddFriend addFriend = new AddFriend();

		addFriend.setArgs0(usname2);
		AddFriendResponse addFriendResponse = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add friend, friendListd"); // borrar
																	// una vez
																	// funcione
		}

		addFriend.setArgs0(usname3);
		AddFriendResponse addFriendResponse2 = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add friend, friendList"); // borrar una
																	// vez
																	// funcione
		}

		// FriendList
		FriendList friendList = new FriendList();
		String[] friends = friendList.getFriends();
		if ((friends[0] != "userFL2") || (friends[1] != "userFL1")) {
			exito = false;
			System.out.println("Fallo en friends, friendList"); // borrar una
																// vez funcione
		}

		// Texto final
		if (exito) {
			System.out.println("Exito en las pruebas de friendList");
		} else {
			System.out.println("Fallo en las pruebas de friendList");
		}

		// Logout user1
		logout = new Logout();
		stub.logout(logout);
	}

	private static void addReading() throws RemoteException { // 10

	}

	private static void readingList() throws RemoteException { // 11

	}

	private static void friendReadings() throws RemoteException { // 12

	}

}