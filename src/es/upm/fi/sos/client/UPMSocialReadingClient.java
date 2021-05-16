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
		System.out.println("Escoja la accion a realizar");
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
				System.out.println("Introduzca su nombre de usuario\n");
				String user = sc.nextLine();
				System.out.println("Introduzca su contrasena\n");
				String pwd = sc.nextLine();
				login(user, pwd, stub);
				break;
			case 2:
				System.out.println("Introduzca el nombre de usuario\n");
				user = sc.nextLine();
				logout(user, stub);
				break;
			case 3:
				System.out.println("Introduzca el nombre del nuevo usuario\n");
				String userNew = sc.nextLine();
				addUser(userNew, stub);
				break;
			case 4:
				removeUser(stub);
				break;
			case 5:
				System.out.println("Introduzca la contrasena actual\n");
				String actualPwd = sc.nextLine();
				System.out.println("Introduzca la nueva contrasena\n");
				String newPwd = sc.nextLine();
				changePwd(actualPwd, newPwd, stub);
				break;
			case 6:
				System.out.println("Introduzca el usuario que desea buscar\n");
				String userSearch = sc.nextLine();
				searchUser(userSearch, stub);
				break;
			case 7:
				System.out.println("Introduzca el usuario que desea añadir\n");
				String userAdd = sc.nextLine();
				addFriend(userAdd, stub);
				break;
			case 8:
				System.out
						.println("Introduzca el usuario que desea eliminar\n");
				String userDelete = sc.nextLine();
				deleteFriend(userDelete, stub);
				break;
			case 9:
				friendList(stub);
				break;
			case 10:
				System.out.println("Introduzca la lectura que desea añadir\n");
				String reading = sc.nextLine();
				addReading(reading, stub);
				finish = true;
				break;
			case 11:
				readingList(stub);
				break;
			case 12:
				System.out
						.println("Introduzca el usuario del cual desea ver las lecturas\n");
				String userReadings = sc.nextLine();
				friendReadings(userReadings, stub);
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

	private static void login(String user, String pwd, UPMSocialReadingStub stub)
			throws RemoteException { // 1

	}

	private static void logout(String user, UPMSocialReadingStub stub)
			throws RemoteException { // 2
		Logout logout = new Logout();
		stub.logout(logout);
		return;
	}

	private static void addUser(String user, UPMSocialReadingStub stub)
			throws RemoteException { // 3

	}

	private static void removeUser(UPMSocialReadingStub stub)
			throws RemoteException { // 4

	}

	private static void changePwd(String actualPwd, String newPwd,
			UPMSocialReadingStub stub) throws RemoteException { // 5

	}

	private static void searchUser(String user, UPMSocialReadingStub stub)
			throws RemoteException { // 6

	}

	private static void addFriend(String user, UPMSocialReadingStub stub)
			throws RemoteException { // 7

	}

	private static void deleteFriend(String user, UPMSocialReadingStub stub)
			throws RemoteException { // 8

	}

	private static void friendList(UPMSocialReadingStub stub)
			throws RemoteException { // 9

	}

	private static void addReading(String reading, UPMSocialReadingStub stub)
			throws RemoteException { // 10

	}

	private static void readingList(UPMSocialReadingStub stub)
			throws RemoteException { // 11

	}

	private static void friendReadings(String user, UPMSocialReadingStub stub)
			throws RemoteException { // 12

	}

}