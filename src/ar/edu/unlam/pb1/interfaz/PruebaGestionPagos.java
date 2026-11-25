package ar.edu.unlam.pb1.interfaz;

import java.util.Scanner;

import ar.edu.unlam.pb1.dominio.Factura;
import ar.edu.unlam.pb1.dominio.GestionPagos;
import ar.edu.unlam.pb1.dominio.enums.EstadoFactura;
import ar.edu.unlam.pb1.dominio.enums.MetodoPago;
import ar.edu.unlam.pb1.dominio.enums.Servicio;
import ar.edu.unlam.pb1.interfaz.enums.MenuPrincipal;

public class PruebaGestionPagos {

	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		GestionPagos gestionPagos = new GestionPagos();
		MenuPrincipal opcionesMenu = null;

		do {
			opcionesMenu = obtenerOpcionesDelMenuPrincipal();

			switch (opcionesMenu) {
			case REGISTRAR_FACTURA:
				registrarFactura(gestionPagos);
				break;
			case BUSCAR_FACTURA_POR_NUMERO:
				buscarFacturaPorNumero(gestionPagos);
				break;
			case OBTENER_FACTURAS_POR_MEDIO_PAGO:
				obtenerFacturasPorMedioPago(gestionPagos);
				break;
			case ORDENAR_FACTURAS_POR_MONTO:
				ordenarFacturasPorMonto(gestionPagos);
				break;
			case VER_TOTAL_POR_SERVICIO:
				verTotalPorServicio(gestionPagos);
				break;
			case VER_PROMEDIO_DE_PAGOS:
				verPromedioDePagos(gestionPagos);
				break;
			case VER_MONTO_MAXIMO_PAGADO:
				verMontoMaximoPagado(gestionPagos);
				break;
			case SALIR:
				salir();
				break;
			}
		} while (!MenuPrincipal.SALIR.equals(opcionesMenu));
	}

	// -------- MÉTODOS DEL MENÚ --------

	// REGISTRAR FACTURA
	private static void registrarFactura(GestionPagos gestionPagos) {
		String nombreCliente = ingresarString(mensajeFondoNegro("\nIngrese su nombre:"));
		Servicio servicio = ingresarServicio();

		double monto = 0;
		do {
			monto = ingresarDouble(mensajeFondoNegro("\nIngrese el monto:"));
		} while (monto <= 0);

		MetodoPago metodoPago = ingresarMetodoDePago();
		EstadoFactura estadoFactura = ingresarEstadoFactura();

		boolean seAgregoFactura = gestionPagos.agregarFactura(nombreCliente, servicio, monto, metodoPago,
				estadoFactura);

		if (seAgregoFactura) {
			System.out.println(mensajeExito("\nSE INGRESÓ LA FACTURA CON ÉXITO!\n"));
		} else {
			System.err.println("\nERROR! NO SE PUDO INGRESAR LA FACTURA!\n");
		}
	}

	// BUSCAR FACTURA POR NÚMERO
	private static void buscarFacturaPorNumero(GestionPagos gestionPagos) {
		int numeroFactura = 0;
		do {
			numeroFactura = ingresarEntero(mensajeFondoNegro("\nIngrese el número de la factura:"));
		} while (numeroFactura <= 0);

		Factura facturaObtenida = gestionPagos.busquedaFacturaPorNumero(numeroFactura);

		if (facturaObtenida != null) {
			System.out.println(facturaObtenida);
		} else {
			System.err.println("\nERROR! NO SE ENCONTRÓ NINGUNA FACTURA CON ESE NÚMERO!\n");
		}
	}

	// OBTENER FACTURAS POR MEDIO DE PAGO
	private static void obtenerFacturasPorMedioPago(GestionPagos gestionPagos) {
		MetodoPago metodoPago = ingresarMetodoDePago();

		Factura[] facturaObtenida = gestionPagos.obtenerTodasLasFacturasQueFueronPagadaPorMetodoDePago(metodoPago);
		boolean facturaEncontrada = false;

		for (int i = 0; i < facturaObtenida.length; i++) {
			if (facturaObtenida[i] != null) {
				System.out.println(facturaObtenida[i]);
				facturaEncontrada = true;
			}
		}

		if (!facturaEncontrada) {
			System.err.println("\nERROR! NO SE ENCONTRÓ NINGUNA FACTURA CON ESE MÉTODO DE PAGO!\n");
		}
	}

	// Ordena las facturas menores a un monto ingresado(en estado pagado)
	private static void ordenarFacturasPorMonto(GestionPagos gestionPagos) {
		double monto = 0;

		do {
			monto = ingresarDouble(mensajeFondoNegro("\nIngrese el monto:"));
		} while (monto <= 0);

		Factura[] facturasOrdenadasPorMonto = gestionPagos
				.ordenarDescendenteTodasLasFacturasEnEstadoPagadoConMontoMenorA(monto);

		boolean facturaEncontrada = false;

		for (int i = 0; i < facturasOrdenadasPorMonto.length; i++) {
			if (facturasOrdenadasPorMonto[i] != null) {
				System.out.println(facturasOrdenadasPorMonto[i]);
				facturaEncontrada = true;
			}
		}

		if (!facturaEncontrada) {
			System.err.println(
					"\nERROR! AÚN NO SÉ PAGÓ NINGUNA FACTURA O NO EXISTE UNA FACTURA PAGA MENOR AL VALOR INGRESADO!\n");
		}
	}

	// VER TOTAL POR SERVICIO
	private static void verTotalPorServicio(GestionPagos gestionPagos) {
		Servicio servicio = ingresarServicio();

		int cantidadDeFacturas = gestionPagos.obtenerLaCantidadDeFacturasPagadasPorServicio(servicio);

		if (cantidadDeFacturas <= 0) {
			System.err.println("ERROR! AÚN NO SE HAN ABONADO FACTURAS DEL SERVICIO INGRESADO!");
		} else {
			System.out.println(
					String.format(mensajeExito("\nLA CANTIDAD DE FACTURAS ABONADAS DEL SERVICIO '%s' ES DE: %d\n"),
							servicio, cantidadDeFacturas));
		}
	}

	// VER PROMEDIO DE PAGOS
	private static void verPromedioDePagos(GestionPagos gestionPagos) {
		double promedioDePagosRealizados = gestionPagos.obtenerElPromedioDePagosRealizados();

		if (promedioDePagosRealizados <= 0) {
			System.err.println("\nERROR! AÚN NO SE ENCONTRÓ NINGUNA FACTURA PAGADA!\n");
		} else {
			System.out.println(String.format(mensajeExito("\nEL MONTO PROMEDIO DE PAGOS ES DE $%.2f%n"),
					promedioDePagosRealizados));
		}

	}

	// VER MONTO MÁXIMO PAGADO
	private static void verMontoMaximoPagado(GestionPagos gestionPagos) {
		double montoMaximoPagado = gestionPagos.obtenerElMontoMaximoPagado();

		if (montoMaximoPagado > 0) {
			System.out
					.println(String.format(mensajeExito("\nEL MONTO MÁXIMO PAGADO ES DE $%.2f%n"), montoMaximoPagado));
		} else {
			System.err.println("\nERROR! AÚN NO SE ENCONTRÓ NINGUNA FACTURA PAGADA!\n");
		}
	}

	// SALIR
	private static void salir() {
		System.out.println(mensajeCyanBold("\n⭐ GRACIAS POR UTILIZAR NUESTRO SERVICIO ⭐"));
	}
	// ------------------------------------------------------------------------------------------------------------------------------

	// INGRESO DE ENUMS
	private static EstadoFactura ingresarEstadoFactura() {
		boolean estadoValido = false;
		int estadoIngresado = 0;

		String opciones = mensajeFondoNegro("\nIngrese el estado de la factura:\n");

		for (int i = 0; i < EstadoFactura.values().length; i++) {
			opciones += (i + 1) + ". " + EstadoFactura.values()[i] + "\n";
		}

		do {
			estadoIngresado = ingresarEntero(opciones);
			estadoValido = estadoIngresado >= 1 && estadoIngresado <= EstadoFactura.values().length;

			if (!estadoValido) {
				System.err.println("\nINGRESÓ UNA OPCIÓN INCORRECTA\n");
			}
		} while (!estadoValido);

		return EstadoFactura.values()[estadoIngresado - 1];
	}

	private static MetodoPago ingresarMetodoDePago() {
		boolean metodoValido = false;
		int metodoIngresado = 0;

		String opciones = mensajeFondoNegro("\nIngrese el metodo de pago:\n");

		for (int i = 0; i < MetodoPago.values().length; i++) {
			opciones += (i + 1) + ". " + MetodoPago.values()[i] + "\n";
		}

		do {
			metodoIngresado = ingresarEntero(opciones);
			metodoValido = metodoIngresado >= 1 && metodoIngresado <= MetodoPago.values().length;

			if (!metodoValido) {
				System.err.println("\nERROR! INGRESÓ UNA OPCIÓN INCORRECTA\n");
			}
		} while (!metodoValido);

		return MetodoPago.values()[metodoIngresado - 1];
	}

	private static Servicio ingresarServicio() {
		boolean servicioValido = false;
		int servicioIngresado = 0;

		String opciones = mensajeFondoNegro("\nIngrese el servicio:\n");

		for (int i = 0; i < Servicio.values().length; i++) {
			opciones += (i + 1) + ". " + Servicio.values()[i] + "\n";
		}

		do {
			servicioIngresado = ingresarEntero(opciones);
			servicioValido = servicioIngresado >= 1 && servicioIngresado <= Servicio.values().length;

			if (!servicioValido) {
				System.err.println("\nERROR! INGRESÓ UNA OPCIÓN INCORRECTA\n");
			}

		} while (!servicioValido);

		return Servicio.values()[servicioIngresado - 1];
	}
	// ------------------------------------------------------------------------------------------------------------------------------

	// MENÚ PRINCIPAL
	private static MenuPrincipal obtenerOpcionesDelMenuPrincipal() {
		int opcion = 0;

		do {
			System.out.println(MenuPrincipal.opcionesDelMenuPrincipal());
			opcion = ingresarEntero(mensajeFondoNegro("\nIngrese la opcion deseada:"));

			if (opcion < 1 || opcion > MenuPrincipal.values().length) {
				System.err.println("\nLA OPCIÓN INGRESADA ES INCORRECTA\n");
			}
		} while (opcion < 1 || opcion > MenuPrincipal.values().length);
		return MenuPrincipal.values()[opcion - 1];
	}
	// ------------------------------------------------------------------------------------------------------------------------------

	// INGRESO DE DATOS DEL USUARIO
	private static int ingresarEntero(String mensaje) {
		System.out.println(mensaje);
		int valor = teclado.nextInt();
		teclado.nextLine();
		return valor;
	}

	private static String ingresarString(String mensaje) {
		String texto = "";

		do {
			System.out.println(mensaje);
			texto = teclado.nextLine().trim();

			if (texto.isBlank() || texto.length() < 3) {
				System.err.println(
						"\nERROR! SU NOMBRE DEBEN SER LETRAS, NO PUEDE QUEDAR VACÍO O TENER MENOS DE 3 CARACTERES!");
			}
		} while (texto.isBlank() || texto.length() < 3);

		return texto;
	}

	private static double ingresarDouble(String mensaje) {
		System.out.println(mensaje);
		double valor = teclado.nextDouble();
		teclado.nextLine();
		return valor;
	}

	// Colores para los mensajes
	public static String mensajeCyanBold(String mensaje) {
		final String CYAN_BOLD = "\033[1;36m";
		return CYAN_BOLD + mensaje + reset();
	}

	private static String mensajeFondoNegro(String mensaje) {
		final String FONDO_NEGRO = "\033[0;100m";
		return FONDO_NEGRO + mensaje + reset();
	}

	private static String mensajeExito(String mensaje) {
		final String VERDE = "\u001B[32m";
		return VERDE + mensaje + reset();
	}

	private static String reset() {
		final String RESET = "\u001B[0m";
		return RESET;
	}
}
