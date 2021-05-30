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
			System.out.println("Fallo en login admin, addUser");
		}
		
		// Preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);
		
		username = new Username();
		username.setUsername("gxjusuario2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);
		
	
		// Aniadir usuario
		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		
		// Fallo en contrasenia
		if (addUserResponse.get_return().getPwd() == null) {
			exito = false;
			System.out.println("Fallo en contrasenia, addUser");
		}
		
		// Fallo en aniadir usuario
		if(!addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en aniadir usuario, addUser");
		}
		
		// Fallo en aniadir usuario ya existente
		addUserResponse = stub.addUser(addUser);
		if (addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en aniadir usuario ya existente, addUser");
		}
		
		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user2
		User user2 = new User();
		user2.setName("gxjusuario2");
		Login login2 = new Login();
		login2.setArgs0(user2);
		loginResponse = stub.login(login2);
		
		// Aniadir usuario no siendo admin
		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);
		
		if (addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en aniadir usuario no siendo admin, addUser");
		}

		// Logout user1
		logout = new Logout();
		stub.logout(logout);
				
		if (exito) {
			System.out.println("Exito en las pruebas de addUser");
		} else {
			System.out.println("Fallo en las pruebas de addUser");
		}
		

	}

	private static void removeUser() throws RemoteException { // 4
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
			System.out.println("Fallo en login admin, removeUser");
		}
		
		// Preparar 2 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);
		
		username = new Username();
		username.setUsername("gxjusuario2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);
		
		
		// Aniadir usuario
		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		stub.addUser(addUser);
		
		// Eliminar usuario
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		RemoveUserResponse removeUserResponse = stub.removeUser(removeUser);
			
		if (!removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar usuario, removeUser");
		}
		
		// Eliminar admin
		Username usernameAdmin = new Username();
		usernameAdmin.setUsername(user.getName());
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);
		
		if(removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar admin, removeUser");
		}
		
		
		// Eliminar usuario con sesion activa
		User user3 = new User();
		user3.setName("gxjusuario3");
		user3.setPwd("pwd");
		UPMSocialReadingStub stub2 = new UPMSocialReadingStub();
		Username username3 = new Username();
		username3.setUsername(user3.getName());
		removeUser = new RemoveUser();
		removeUser.setArgs0(username3);
		stub2.removeUser(removeUser);
		addUser = new AddUser();
		addUser.setArgs0(username3);
		stub2.addUser(addUser);
		login = new Login();
		login.setArgs0(user3);
		loginResponse = stub2.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login otro usuario, removeUser");
		}
		
		
		// Eliminar usuario
		removeUser = new RemoveUser();
		removeUser.setArgs0(username3);
		removeUserResponse = stub.removeUser(removeUser);
		
		if (!removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar otro usuario, removeUser");
		} else {
			AddFriend addFriend = new AddFriend();
			

			Username username4 = new Username();
			username4.setUsername("gxjusuario4");
			removeUser = new RemoveUser();
			addUser = new AddUser();
			addUser.setArgs0(username4);
			stub.addUser(addUser);
			addFriend.setArgs0(username4);
			AddFriendResponse addFriendResponse = stub2.addFriend(addFriend);
			
			if (addFriendResponse.get_return().getResponse()) {
				exito = false;
				System.out.println("Fallo en accion usuario eliminado, removeUser");
			}
		}
		
		
		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);
		
		// Aniadir usuario
		addUser = new AddUser();
		addUser.setArgs0(username);
		stub.addUser(addUser);
		
		// Eliminar usuario sin login
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);
		
		if(removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar usuarios sin login, removeUser");
		}
		
		// Login user1
		User user1 = new User();
		user1.setName("gxjusuario1");
		Login login1 = new Login();
		login1.setArgs0(user1);
		loginResponse = stub.login(login1);
		
		// Eliminar diferente usuario
		username = new Username();
		username.setUsername("gxjusuario2");
		addUser = new AddUser();
		addUser.setArgs0(username);
		stub.addUser(addUser);
		
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);
		
		if(removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar diferente usuario, removeUser");
		}
		
		// Eliminarse a si mismo
		username = new Username();
		username.setUsername(user1.getName()); // gxjusuario1
		
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);
		
		if(!removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminarse a si mismo, removeUser");
		}

				
		if (exito) {
			System.out.println("Exito en las pruebas de removeUser");
		} else {
			System.out.println("Fallo en las pruebas de removeUser");
		}
		
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
		
		// Eliminar posibles usuarios existentes
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(usname1);
		stub.removeUser(removeUser);
		removeUser.setArgs0(usname2);
		stub.removeUser(removeUser);
		

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
		
		// Eliminar posibles usuarios existentes
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(usname1);
		stub.removeUser(removeUser);
		removeUser.setArgs0(usname2);
		stub.removeUser(removeUser);
		removeUser.setArgs0(usname3);
		stub.removeUser(removeUser);
		

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
		
		if (!found) {
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
		
		
		// Eliminar posibles usuarios existentes
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(usname1);
		stub.removeUser(removeUser);
		removeUser.setArgs0(usname2);
		stub.removeUser(removeUser);
		removeUser.setArgs0(usname3);
		stub.removeUser(removeUser);
		

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

	}

	private static void getReadingList() throws RemoteException { // 11

	}

	private static void getFriendReadings() throws RemoteException { // 12

	}

}