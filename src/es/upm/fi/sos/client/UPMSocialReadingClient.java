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

		GetMyReadings getMyReadings = new GetMyReadings();
		GetMyReadingsResponse getMyReadingsResponse = stub
				.getMyReadings(getMyReadings);

		if (getMyReadingsResponse.get_return().getResult()) {
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

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo login");
		}

		getMyReadings = new GetMyReadings();
		getMyReadingsResponse = stub.getMyReadings(getMyReadings);

		if (!getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: logout de un usuario");
		}

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

		getMyReadings = new GetMyReadings();
		getMyReadingsResponse = stub.getMyReadings(getMyReadings);

		if (!getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: loging 3 veces logout 2 veces");
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
		username.setUsername("gxjusuario11");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		username = new Username();
		username.setUsername("gxjusuario12");
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
		if (!addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en aniadir usuario, addUser");
		}

		// Fallo en aniadir usuario ya existente
		addUserResponse = stub.addUser(addUser);
		if (addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en aniadir usuario ya existente, addUser");
		}

		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user2
		User user2 = new User();
		user2.setName("gxjusuario12");
		Login login2 = new Login();
		login2.setArgs0(user2);
		loginResponse = stub.login(login2);

		// Aniadir usuario no siendo admin
		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);

		if (addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en aniadir usuario no siendo admin, addUser");
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
		username.setUsername("gxjusuario11");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		username = new Username();
		username.setUsername("gxjusuario12");
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

		if (removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar admin, removeUser");
		}

		// Eliminar usuario con sesion activa
		User user3 = new User();
		user3.setName("gxjusuario13");
		user3.setPwd("pwd");
		UPMSocialReadingStub stub2 = new UPMSocialReadingStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);
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
			username4.setUsername("gxjusuario14");
			removeUser = new RemoveUser();
			addUser = new AddUser();
			addUser.setArgs0(username4);
			stub.addUser(addUser);
			addFriend.setArgs0(username4);
			AddFriendResponse addFriendResponse = stub2.addFriend(addFriend);

			if (addFriendResponse.get_return().getResponse()) {
				exito = false;
				System.out
						.println("Fallo en accion usuario eliminado, removeUser");
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

		if (removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en eliminar usuarios sin login, removeUser");
		}

		// Login user1
		User user1 = new User();
		user1.setName("gxjusuario11");
		Login login1 = new Login();
		login1.setArgs0(user1);
		loginResponse = stub.login(login1);

		// Eliminar diferente usuario
		username = new Username();
		username.setUsername("gxjusuario12");
		addUser = new AddUser();
		addUser.setArgs0(username);
		stub.addUser(addUser);

		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);

		if (removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en eliminar diferente usuario, removeUser");
		}

		// Eliminarse a si mismo
		username = new Username();
		username.setUsername(user1.getName()); // gxjusuario11

		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);

		if (!removeUserResponse.get_return().getResponse()) {
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

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo login 1");
		}

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub2.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo login 2");
		}

		passwordPair = new PasswordPair();
		passwordPair.setOldpwd(password1);
		passwordPair.setNewpwd("newpassword1");
		changePassword = new ChangePassword();
		changePassword.setArgs0(passwordPair);
		changePasswordResponse = stub.changePassword(changePassword);

		if (!changePasswordResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: cambiar contrasena entre varias sesiones 1");
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
					.println("Fallo test: cambiar contrasena entre varias sesiones 2");
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
					.println("Fallo test: cambiar contrasena entre varias sesiones 3");
		}

		if (exito) {
			System.out.println("Exito en las pruebas de ChangePassword");
		} else {
			System.out.println("Fallo en las pruebas de ChangePassword");
		}
	}

	private static void addFriend() throws RemoteException { // 6
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		UPMSocialReadingStub stub2 = new UPMSocialReadingStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);
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
			System.out.println("Fallo en login admin, addFriend");
		}

		// Preparar 2 usuarios
		Username username = new Username();
		username.setUsername("userAF1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);
		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		Username username2 = new Username();
		username2.setUsername("userAF2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username2);
		stub.removeUser(removeUser);
		addUser = new AddUser();
		addUser.setArgs0(username2);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();

		if (!addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add user, addFriend");
		}

		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user1
		User user1 = new User();
		user1.setName("userAF1");
		user1.setPwd(password1);
		login = new Login();
		login.setArgs0(user1);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login user1, addFriend");
		}

		// Login user1 stub2
		user1 = new User();
		user1.setName("userAF1");
		user1.setPwd(password1);
		login = new Login();
		login.setArgs0(user1);
		loginResponse = stub2.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login user1 stub2, addFriend");
		}

		// Add friend
		AddFriend addFriend = new AddFriend();

		User user2 = new User();
		user2.setName("userAF2");
		user.setPwd(password2);

		Username usnameF = new Username();
		usnameF.setUsername(user2.getName());
		addFriend.setArgs0(usnameF);
		AddFriendResponse addFriendResponse = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en addFriend");
		}

		FriendList friendList = new FriendList();
		friendList.addFriends(usnameF.getUsername());

		// Amigo repetido
		addFriend = new AddFriend();
		addFriend.setArgs0(usnameF);
		addFriendResponse = stub.addFriend(addFriend);
		if (addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en amigo repetido, addFriend");
		}

		// Usuario no existente
		Username usname3 = new Username();
		usname3.setUsername("userNoExist");
		addFriend.setArgs0(usname3);
		addFriendResponse = stub.addFriend(addFriend);
		if (addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en aniadir amigo no existente, addFriend");
		}

		// Comprobar amigo
		GetMyFriendList getMyFriendList = new GetMyFriendList();
		String[] friends = stub2.getMyFriendList(getMyFriendList).get_return()
				.getFriends();
		if (!friends[friends.length - 1].equals(user2.getName())) {
			exito = false;
			System.out
					.println("Fallo en comprobar amigo en otra sesion, addFriend");
		}

		// Eliminar amigo aniadido
		RemoveFriend removeFriend = new RemoveFriend();
		removeFriend.setArgs0(usnameF);
		RemoveFriendResponse removeFriendResponse = stub
				.removeFriend(removeFriend);

		if (!removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar amigo1, friendList");
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

	private static void deleteFriend() throws RemoteException { // 7
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		UPMSocialReadingStub stub2 = new UPMSocialReadingStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);
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
			System.out.println("Fallo en login admin, deleteFriend");
		}

		// Preparar 3 usuarios
		Username username = new Username();
		username.setUsername("userDF1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);
		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		Username username2 = new Username();
		username2.setUsername("userDF2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username2);
		stub.removeUser(removeUser);
		addUser = new AddUser();
		addUser.setArgs0(username2);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();

		Username username3 = new Username();
		username3.setUsername("userDF3");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username3);
		stub.removeUser(removeUser);
		addUser = new AddUser();
		addUser.setArgs0(username3);
		addUserResponse = stub.addUser(addUser);
		String password3 = addUserResponse.get_return().getPwd();

		if (!addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add user, deleteFriend");
		}

		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user1
		User user1 = new User();
		user1.setName("userDF1");
		user1.setPwd(password1);
		login = new Login();
		login.setArgs0(user1);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login user1, deleteFriend");
		}

		// Add friend
		AddFriend addFriend = new AddFriend();

		User user2 = new User();
		user2.setName("userDF2");
		user.setPwd(password2);

		addFriend.setArgs0(username2);
		AddFriendResponse addFriendResponse = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en add friend ,deleteFriend");
		}

		FriendList friendList = new FriendList();
		friendList.addFriends(username2.getUsername());

		// Remove friend
		RemoveFriend removeFriend = new RemoveFriend();
		removeFriend.setArgs0(username2);
		RemoveFriendResponse removeFriendResponse = stub
				.removeFriend(removeFriend);

		if (!removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en deleteFriend");
		}

		// Comprobar amigo
		GetMyFriendList getMyFriendList = new GetMyFriendList();
		String[] friends = stub2.getMyFriendList(getMyFriendList).get_return()
				.getFriends();
		if (friends != null && friends.length != 0) {

			if (!friends[friends.length - 1].equals(user2.getName())) {
				exito = false;
				System.out.println("Fallo en comprobar amigo, deleteFriend");
			}
		}

		// No es amigo
		removeFriend.setArgs0(username3);
		removeFriendResponse = stub.removeFriend(removeFriend);
		if (removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo en eliminar usuario no amigo, deleteFriend");
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
					.println("Fallo en eliminar usuario no existente, deleteFriend");
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

	private static void getFriendList() throws RemoteException { // 8
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

		// Preparar 3 usuarios
		Username username = new Username();
		username.setUsername("userGF1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);
		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		Username username2 = new Username();
		username2.setUsername("userGF2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username2);
		stub.removeUser(removeUser);
		addUser = new AddUser();
		addUser.setArgs0(username2);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();

		Username username3 = new Username();
		username3.setUsername("userGF3");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username3);
		stub.removeUser(removeUser);
		addUser = new AddUser();
		addUser.setArgs0(username3);
		addUserResponse = stub.addUser(addUser);
		String password3 = addUserResponse.get_return().getPwd();

		// Logout admin
		Logout logout = new Logout();
		stub.logout(logout);

		// Login user1
		User user1 = new User();
		user1.setName("userGF1");
		user1.setPwd(password1);
		login = new Login();
		login.setArgs0(user1);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en login user1, friendList");
		}

		// Add friends
		// 1
		AddFriend addFriend = new AddFriend();

		User user2 = new User();
		user2.setName("userGF2");
		user.setPwd(password2);

		addFriend.setArgs0(username2);
		AddFriendResponse addFriendResponse = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en aniadir amigo 1, getFriendList");
		}

		FriendList friendList = new FriendList();
		friendList.addFriends(username2.getUsername());

		// 2
		addFriend = new AddFriend();

		User user3 = new User();
		user3.setName("userGF3");
		user3.setPwd(password3);

		addFriend.setArgs0(username3);
		addFriendResponse = stub.addFriend(addFriend);
		if (!addFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en aniadir amigo 2, getFriendList");
		}

		friendList.addFriends(username3.getUsername());

		// FriendList
		GetMyFriendList getMyFriendList = new GetMyFriendList();
		String[] friends = stub.getMyFriendList(getMyFriendList).get_return()
				.getFriends();

		if (friends == null || friends.length == 0) {
			exito = false;
			System.out.println("Fallo en friends null, friendList");
		} else if (!friends[0].equals(username2.getUsername())
				|| !friends[1].equals(username3.getUsername())) {
			exito = false;
			System.out.println("Fallo en friends, friendList");
		}

		// Eliminar amigos
		RemoveFriend removeFriend = new RemoveFriend();
		removeFriend.setArgs0(username2);
		RemoveFriendResponse removeFriendResponse = stub
				.removeFriend(removeFriend);

		if (!removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar amigo1, friendList");
		}

		removeFriend = new RemoveFriend();
		removeFriend.setArgs0(username3);
		removeFriendResponse = stub.removeFriend(removeFriend);

		if (!removeFriendResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo en eliminar amigo1, friendList");
		}

		// Logout user1
		logout = new Logout();
		stub.logout(logout);

		// Texto final
		if (exito) {
			System.out.println("Exito en las pruebas de friendList");
		} else {
			System.out.println("Fallo en las pruebas de friendList");
		}
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
		stub.login(login);

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
		stub.login(login);

		book = new Book();
		book.setAuthor("Guido Van Roussum");
		book.setCalification(9);
		book.setTitle("Python");
		addReading = new AddReading();
		addReading.setArgs0(book);
		addReadingResponse = stub.addReading(addReading);

		if (!addReadingResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: anadir un libro 1");
		}

		GetMyReadings getMyReadings = new GetMyReadings();
		GetMyReadingsResponse getMyReadingsResponse = stub
				.getMyReadings(getMyReadings);

		if (!getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: anadir un libro 2");
		}

		if (!getMyReadingsResponse.get_return().getTitles()[0].equals(book
				.getTitle())) {

			exito = false;
			System.out.println("Fallo test: anadir un libro 3");
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

		if (!getMyReadingsResponse.get_return().getTitles()[0].equals(book
				.getTitle())) {
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
		stub.login(login);

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
		GetMyReadingsResponse getMyReadingsResponse = stub
				.getMyReadings(getMyReadings);

		if (getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas sin logearse");
		}

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		stub.login(login);

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
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);
		stub.login(login);

		getMyReadings = new GetMyReadings();
		getMyReadingsResponse = stub.getMyReadings(getMyReadings);

		if (!getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas");
		}

		String[] readings = getMyReadingsResponse.get_return().getTitles();

		if (!(readings.length >= 3)) {
			exito = false;
			System.out.println("Fallo test: longitud de la lista");
		}

		if (!readings[0].equals(book3.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el primer libro");
		}

		if (!readings[1].equals(book2.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el segundo libro");
		}

		if (!readings[2].equals(book1.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el tercer libro");
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

		stub.login(login);

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
		GetMyFriendReadingsResponse getMyFriendReadingsResponse = stub
				.getMyFriendReadings(getMyFriendReadings);

		if (getMyFriendReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out
					.println("Fallo test: obtener lecturas del amigo sin logearse");
		}

		user = new User();
		user.setName("gxjusuario1");
		user.setPwd(password1);
		login = new Login();
		login.setArgs0(user);

		stub.login(login);

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

		stub.login(login);

		username = new Username();
		username.setUsername("gxjusuario1");
		getMyFriendReadings = new GetMyFriendReadings();
		getMyFriendReadings.setArgs0(username);

		getMyFriendReadingsResponse = stub
				.getMyFriendReadings(getMyFriendReadings);

		if (!getMyFriendReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas del amigo");
		}

		String[] readings = getMyFriendReadingsResponse.get_return()
				.getTitles();

		if (!(readings.length >= 3)) {
			exito = false;
			System.out.println("Fallo test: longitud de la lista");
		}

		if (!readings[0].equals(book3.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el primer libro");
		}

		if (!readings[1].equals(book2.getTitle())) {
			exito = false;
			System.out.println("Fallo test: comparar el tercer libro");
		}

		if (!readings[2].equals(book1.getTitle())) {
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