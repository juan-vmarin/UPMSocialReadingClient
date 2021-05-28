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
		user.setName("grupo13333");
		user.setPwd("sucontrasenia");
		login = new Login();
		login.setArgs0(user);
		loginResponse = stub.login(login);

		if (loginResponse.get_return().getResponse()) {
			exito = false;
			System.out
					.println("Fallo test: logear con una usuario inexistente");
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

	}

	private static void deleteFriend() throws RemoteException { // 8

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