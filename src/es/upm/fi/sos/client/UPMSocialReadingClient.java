package es.upm.fi.sos.client;

import java.util.Scanner;

public class UPMSocialReadingClient {

	private static Scanner sc;

	public static void main(String[] args) throws Exception {
		UPMSocialReadingStub stub = new UPMSocialReadingStub();
		stub._getServiceClient().engageModule("addressing");
		stub._getServiceClient().getOptions().setManageSession(true);
		System.out.println("Escoja la accion a realizar");
		sc = new Scanner(System.in);

		int option;
		boolean exit = false;
		while (!exit) {
			System.out.println("1. Iniciar sesion\n" + "2. Añadir usuario\n"
					+ "3. Eliminar usuario\n" + "4. Cambiar contraseña\n"
					+ "5. Buscar usuario\n" + "6. Añadir amigo\n"
					+ "7. Eliminar amigo\n" + "8. Lista de amigos\n"
					+ "9. Añadir lectura\n" + "8. Lista de lecturas\n"
					+ "9. Lecturas de amigo\n" + "10. Salir\n");

			option = sc.nextInt();
			sc.nextLine();
			switch (option) {
			case 1:
				System.out.println("Introduzca su nombre de usuario\n");
				String user = sc.nextLine();
				System.out.println("Introduzca su contrasena\n");
				String pwd = sc.nextLine();
				break;
			case 2:
				System.out.println("Introduzca el nombre de usuario\n");
				System.out.println("Introduzca la contrasena\n");
				pwd = sc.nextLine();
				break;
			case 3:
				break;
			case 4:
				System.out.println("Introduzca la contrasena actual\n");
				String oldPwd = sc.nextLine();
				System.out.println("Introduzca la nueva contrasena\n");
				String newPwd = sc.nextLine();
				break;
			case 5:

				break;
			case 6:

				break;
			case 7:

				break;
			case 8:
				break;
			case 9:

				break;
			case 10:

				break;
			default:
				break;
			}

		}

	}

}