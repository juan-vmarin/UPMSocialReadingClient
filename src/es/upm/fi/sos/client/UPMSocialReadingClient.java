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
			System.out.println("\n1. Iniciar sesion\n" + "2. Cerrar sesion\n"
					+ "3. Añadir usuario\n" + "4. Eliminar usuario\n"
					+ "5. Cambiar contraseña\n" + "6. Añadir amigo\n"
					+ "7. Eliminar amigo\n" + "8. Lista de amigos\n"
					+ "9. Añadir lectura\n" + "10. Lista de lecturas\n"
					+ "11. Lecturas de amigo\n"
					+ "12. Realizar todas las pruebas en una\n" + "13. Salir\n");

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
				System.out.println("Prueba login:");
				login();
				System.out.println("Prueba logout:");
				logout();
				System.out.println("Prueba addUser:");
				addUser();
				System.out.println("Prueba removeUser:");
				removeUser();
				System.out.println("Prueba changePassword:");
				changePwd();
				System.out.println("Prueba addFriend:");
				addFriend();
				System.out.println("Prueba removeFriend:");
				deleteFriend();
				System.out.println("Prueba getMyFriendList:");
				getFriendList();
				System.out.println("Prueba addReading:");
				addReading();
				System.out.println("Prueba getMyReadings:");
				getReadingList();
				System.out.println("Prueba getMyFriendReadings:");
				getFriendReadings();
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

		// logout de un usuario
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
		user.setName("gxjusuario2");
		user.setPwd(password2);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (loginResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: login user 1 y login user 2 consecutivamente");
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
			System.out.println("Fallo test: login 3 veces logout 2 veces");
		}

		user = new User();
		user.setName("gxjusuario2");
		user.setPwd(password2);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (loginResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: login 3 veces usuario 1, 2 logout usuario 1, y login usuario 1");
		}

		logout = new Logout();
		stub.logout(logout);

		getMyReadings = new GetMyReadings();
		getMyReadingsResponse = stub.getMyReadings(getMyReadings);

		if (getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: logout total de un usuario");
		}

		user = new User();
		user.setName("gxjusuario2");
		user.setPwd(password2);
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (!loginResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: login 3 veces logout 3 veces, login con usuario 2");
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

		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		// anadir usuario inexistente
		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		if (!addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: anadir usuario");
		}

		// anadir usuario existente
		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);
		String password2 = addUserResponse.get_return().getPwd();

		if (addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: anadir usuario existente");
		}

		// login con un usuario creado
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
			System.out.println("Fallo test: login con un usuario creado");
		}

		// crear un usuario con cuenta de un usuario normal
		username = new Username();
		username.setUsername("gxjusuario2");
		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);

		if (addUserResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: crear un usuario con cuenta de un usuario normal");
		}

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

		// Preparar 2 usuarios
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

		username = new Username();
		username.setUsername("gxjusuario3");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		addUser = new AddUser();
		addUser.setArgs0(username);
		addUserResponse = stub.addUser(addUser);
		String password3 = addUserResponse.get_return().getPwd();

		// eliminar usuario inextistente
		username = new Username();
		username.setUsername("usurio inexistente");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		RemoveUserResponse removeUserResponse = stub.removeUser(removeUser);

		if (removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: eliminar un usuario inexistente");
		}

		// eliminar admin
		username = new Username();
		username.setUsername("admin");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);

		if (removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: eliminar admin");
		}

		UPMSocialReadingStub stub2 = new UPMSocialReadingStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);

		// eliminar usuario existente siendo admin
		username = new Username();
		username.setUsername("gxjusuario3");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub.removeUser(removeUser);

		if (!removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: eliminar usuario existente siendo admin");
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

		// eliminar a otro usuario siendo usuario normal
		username = new Username();
		username.setUsername("gxjusuario2");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub2.removeUser(removeUser);

		if (removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: eliminar a otro usuario siendo usuario normal");
		}

		// eliminarse a si mismo
		username = new Username();
		username.setUsername("gxjusuario1");
		removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		removeUserResponse = stub2.removeUser(removeUser);

		if (!removeUserResponse.get_return().getResponse()) {
			exito = false;
			System.out.println("Fallo test: eliminarse a si mismo");
		}

		// usuario logeado anteriormente pero borrado intenta realizar accion
		GetMyReadings getMyReadings = new GetMyReadings();
		GetMyReadingsResponse getMyReadingsResponse = stub
				.getMyReadings(getMyReadings);

		if (getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out
					.println("Fallo test: usuario logeado anteriormente pero borrado intenta realizar accion");
		}

		if (exito) {
			System.out.println("Exito en las pruebas de RemoveUser");
		} else {
			System.out.println("Fallo en las pruebas de RemoveUser");
		}

	}

	private static void changePwd() throws RemoteException { // 5
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
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

		// preparar 1 usuarios
		Username username = new Username();
		username.setUsername("gxjusuario1");
		RemoveUser removeUser = new RemoveUser();
		removeUser.setArgs0(username);
		stub.removeUser(removeUser);

		AddUser addUser = new AddUser();
		addUser.setArgs0(username);
		AddUserResponseE addUserResponse = stub.addUser(addUser);
		String password1 = addUserResponse.get_return().getPwd();

		// cambiar contrasena del admin
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

		// restaurar la contrasena del admin
		passwordPair = new PasswordPair();
		passwordPair.setOldpwd("newadmin");
		passwordPair.setNewpwd("admin");
		changePassword = new ChangePassword();
		changePassword.setArgs0(passwordPair);
		changePasswordResponse = stub.changePassword(changePassword);

		// cambiar contrasena entre varias sesiones
		stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);

		UPMSocialReadingStub stub2 = new UPMSocialReadingStub();
		stub2._getServiceClient().engageModule("addressing");
		stub2._getServiceClient().getOptions().setManageSession(true);

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
					.println("Fallo test: cambiar contrasena entre varias sesiones (paso 1)");
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
					.println("Fallo test: cambiar contrasena entre varias sesiones (paso 2)");
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
					.println("Fallo test: cambiar contrasena entre varias sesiones (paso 3)");
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

		// anadir un libro sin logearse
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

		// anadir un libro
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
			System.out.println("Fallo test: anadir un libro (paso 1)");
		}

		GetMyReadings getMyReadings = new GetMyReadings();
		GetMyReadingsResponse getMyReadingsResponse = stub
				.getMyReadings(getMyReadings);

		if (!getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: anadir un libro (paso 2)");
		}

		if (!getMyReadingsResponse.get_return().getTitles()[0].equals(book
				.getTitle())) {

			exito = false;
			System.out.println("Fallo test: anadir un libro (paso 3)");
		}

		// modificar un libro
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

		// preparar 1 usuario
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

		// obtener lecturas sin logearse
		GetMyReadings getMyReadings = new GetMyReadings();
		GetMyReadingsResponse getMyReadingsResponse = stub
				.getMyReadings(getMyReadings);

		if (getMyReadingsResponse.get_return().getResult()) {
			exito = false;
			System.out.println("Fallo test: obtener lecturas sin logearse");
		}

		// obtener lecturas
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

		// obtener lecturas del amigo sin logearse
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

		// obtener lecturas del amigo

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