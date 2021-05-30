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
					+ "5. Cambiar contraseña\n" + "6. Añadir amigo\n"
					+ "7. Eliminar amigo\n" + "8. Lista de amigos\n"
					+ "9. Añadir lectura\n" + "10. Lista de lecturas\n"
					+ "11. Lecturas de amigo\n" + "12. Salir\n");

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
				addFriend();
				break;
			case 7:
				deleteFriend();
				break;
			case 8:
				getFriendList();
				break;
			case 9:
				addReading();
				break;
			case 10:
				getReadingList();
				break;
			case 11:
				getFriendReadings();
				break;
			case 12:
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

		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: logear con el usuario 'admin'");
		}

		// preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		username = new Username();
		username.setUsername("gxjusuario2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();

		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: logear con una usuario existente");
		}

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd("cualquiercontrasenia");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: logear consecutivamente con una usuario existente y contrasena incorrecto");
		}

		user = new User();
		user.setName("gxjusuario2");
		user.setPwd(password2);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: logear con otro usuario");
		}

		if (exito) {
			System.out.println("Exito en las pruebas de Login");
		} else {
			System.out.println("Fallo en las pruebas de Login");
		}

	}

	private static void logout() throws RemoteException { // 2
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		Logout logout;
		User user;
		Login login;
		LoginResponse loginResponse;

		boolean exito = true;

		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		// preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		username = new Username();
		username.setUsername("gxjusuario2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();

		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		logout = new Logout();
		stub.logout(logout);

		username = new Username();
		username.setUsername("gxjusuario2");
		AddFriend addFriend = new AddFriend();
		addFriend.setArgs0(username);
		AddFriendResponse addFriendResponse = stub.addFriend(addFriend);

		if (addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: logout de un usuario");
		}

		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		logout = new Logout();
		stub.logout(logout);

		logout = new Logout();
		stub.logout(logout);

		username = new Username();
		username.setUsername("gxjusuario2");
		addFriend = new AddFriend();
		addFriend.setArgs0(username);
		addFriendResponse = stub.addFriend(addFriend);

		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: loging 3veces logout 2 veces");
		}

		logout = new Logout();
		stub.logout(logout);

		user = new User();
		user.setName("gxjusuario2");
		user.setPwd(password2);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: loging 3 veces logout 3 veces, login con usuario2");
		}

		if (exito) {
			System.out.println("Exito en las pruebas de Logout");
		} else {
			System.out.println("Fallo en las pruebas de Logout");
		}

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
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		UPMSocialReadingStub stub2 = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		Logout logout;
		User user;
		Login login;
		LoginResponse loginResponse;
		PasswordPair passwordPair;
		ChangePassword changePassword;
		ChangePasswordResponse changePasswordResponse;

		boolean exito = true;

		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		// preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		username = new Username();
		username.setUsername("gxjusuario2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();

		passwordPair = new PasswordPair();
		passwordPair.setOldpwd("admin");
		passwordPair.setNewpwd("newadmin");
		changePassword = new ChangePassword();
		changePassword.setArgs0(passwordPair);
		changePasswordResponse = stub.changePassword(changePassword);

		if (!changePasswordResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: cambiar contrasena del admin");
		}

		logout = new Logout();
		stub.logout(logout);

		user = new User();
		user.setName("admin");
		user.setPwd("newadmin");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: cambiar contrasena del admin");
		}

		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub2.login(login);

		passwordPair = new PasswordPair();
		passwordPair.setOldpwd(password1);
		passwordPair.setNewpwd("newpassword1");
		changePassword = new ChangePassword();
		changePassword.setArgs0(passwordPair);
		changePasswordResponse = stub.changePassword(changePassword);

		if (!changePasswordResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: cambiar contrasena entre varias sesiones");
		}

		passwordPair = new PasswordPair();
		passwordPair.setOldpwd(password1);
		passwordPair.setNewpwd("newpassword2");
		changePassword = new ChangePassword();
		changePassword.setArgs0(passwordPair);
		changePasswordResponse = stub2.changePassword(changePassword);

		if (!changePasswordResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: cambiar contrasena entre varias sesiones");
		}

		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd("newpassword2");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: cambiar contrasena entre varias sesiones");
		}

		if (exito) {
			System.out.println("Exito en las pruebas de ChangePassword");
		} else {
			System.out.println("Fallo en las pruebas de ChangePassword");
		}
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
	}
	
	private static void getFriendList() throws RemoteException { // 9

	}

	private static void addReading() throws RemoteException { // 10
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		User user;
		Login login;
		boolean exito = true;

		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		// preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();
		
		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		Book book = new Book();
		book.setAuthor("Guido Van Roussum");
		book.setCalification(10);
		book.setTitle("Python");
		AddReading addReading = new AddReading();
		addReading.setArgs0(book);
		AddReadingResponse addReadingResponse = stub.addReading(addReading);
		
		if (addReadingResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: anadir un libro sin logearse");
		}
		
		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		book = new Book();
		book.setAuthor("Guido Van Roussum");
		book.setCalification(9);
		book.setTitle("Python");
		addReading = new AddReading();
		addReading.setArgs0(book);
		addReadingResponse = stub.addReading(addReading);
		
		GetMyReadings getMyReadings = new GetMyReadings();
		GetMyReadingsResponse getMyReadingsResponse = stub.getMyReadings(getMyReadings);
		
		if (getMyReadingsResponse.get_return().getTitles()[0].equals(book.getTitle())) {
			exito = false;
			System.out.println("Fallo test: anadir un libro");
		}
		
		book = new Book();
		book.setAuthor("Guido Van Roussum");
		book.setCalification(10);
		book.setTitle("Python");
		addReading = new AddReading();
		addReading.setArgs0(book);
		addReadingResponse = stub.addReading(addReading);
		
		getMyReadings = new GetMyReadings();
		getMyReadingsResponse = stub.getMyReadings(getMyReadings);
		
		if (getMyReadingsResponse.get_return().getTitles()[0].equals(book.getTitle())) {
			exito = false;
			System.out.println("Fallo test: modificar un libro");
		}

		if (exito) {
			System.out.println("Exito en las pruebas de AddReading");
		} else {
			System.out.println("Fallo en las pruebas de AddReading");
		}
	}

	private static void getReadingList() throws RemoteException { // 11
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		User user;
		Login login;
		boolean exito = true;

		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		// preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();
		
		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		GetMyReadings getMyReadings = new GetMyReadings();
		GetMyReadingsResponse getMyReadingsResponse = stub.getMyReadings(getMyReadings);
		
		if (getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas sin logearse");
		}
		
		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		Book book1 = new Book();
		book1.setAuthor("Guido Van Roussum");
		book1.setCalification(10);
		book1.setTitle("Python");
		AddReading addReading = new AddReading();
		addReading.setArgs0(book1);
		stub.addReading(addReading);
		
		Book book2 = new Book();
		book2.setAuthor("Netscape");
		book2.setCalification(10);
		book2.setTitle("JavaScript");
		addReading = new AddReading();
		addReading.setArgs0(book2);
		stub.addReading(addReading);
		
		Book book3 = new Book();
		book3.setAuthor("Oracle");
		book3.setCalification(10);
		book3.setTitle("Java");
		addReading = new AddReading();
		addReading.setArgs0(book3);
		stub.addReading(addReading);
	
		getMyReadings = new GetMyReadings();
		getMyReadingsResponse = stub.getMyReadings(getMyReadings);
		
		if (!getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas");
		}
		
		String[] readings = getMyReadingsResponse.get_return().getTitles();
		
		if (readings[0].equals(book3.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el primer libro");
		} 
		
		if (readings[1].equals(book2.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el tercer libro");
		} 
		
		if (readings[2].equals(book1.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el primer libro");
		} 

		if (exito) {
			System.out.println("Exito en las pruebas de GetMyReadings");
		} else {
			System.out.println("Fallo en las pruebas de GetMyReadings");
		}
	}

	private static void getFriendReadings() throws RemoteException { // 12
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		User user;
		Login login;
		boolean exito = true;

		user = new User();
		user.setName("admin");
		user.setPwd("admin");
		login = new Login();
		login.setArgs0(user);
		
		// preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();
		
		username = new Username();
		username.setUsername("gxjusuario2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();
		
		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		username = new Username();
		username.setUsername("gxjusuario1");
		GetMyFriendReadings getMyFriendReadings = new GetMyFriendReadings();
		getMyFriendReadings.setArgs0(username);
		GetMyFriendReadingsResponse getMyFriendReadingsResponse = stub.getMyFriendReadings(getMyFriendReadings);
		
		if (getMyFriendReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas del amigo sin logearse");
		}
		
		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		
		username = new Username();
		username.setUsername("gxjusuario2");
		AddFriend addFriend = new AddFriend();
		addFriend.setArgs0(username);
		stub.addFriend(addFriend);
		
		Book book1 = new Book();
		book1.setAuthor("Guido Van Roussum");
		book1.setCalification(10);
		book1.setTitle("Python");
		AddReading addReading = new AddReading();
		addReading.setArgs0(book1);
		stub.addReading(addReading);
		
		Book book2 = new Book();
		book2.setAuthor("Netscape");
		book2.setCalification(10);
		book2.setTitle("JavaScript");
		addReading = new AddReading();
		addReading.setArgs0(book2);
		stub.addReading(addReading);
		
		Book book3 = new Book();
		book3.setAuthor("Oracle");
		book3.setCalification(10);
		book3.setTitle("Java");
		addReading = new AddReading();
		addReading.setArgs0(book3);
		stub.addReading(addReading);
		
		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		
		user = new User();
		user.setName("gxjusuario2");
		user.setPwd(password2);
		login = new Login();
		login.setArgs0(user);
	
		username = new Username();
		username.setUsername("gxjusuario1");
		getMyFriendReadings = new GetMyFriendReadings();
		getMyFriendReadings.setArgs0(username);
		getMyFriendReadingsResponse = stub.getMyFriendReadings(getMyFriendReadings);
		
		if (getMyFriendReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas del amigo");
		}
		
		String[] readings = getMyFriendReadingsResponse.get_return().getTitles();
		
		if (readings[0].equals(book3.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el primer libro");
		} 
		
		if (readings[1].equals(book2.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el tercer libro");
		} 
		
		if (readings[2].equals(book1.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el primer libro");
		} 

		if (exito) {
			System.out.println("Exito en las pruebas de GetMyFriendReadings");
		} else {
			System.out.println("Fallo en las pruebas de GetMyFriendReadings");
		}
	}

}