package ar.edu.unlam.pb1.interfaz.enums;

import ar.edu.unlam.pb1.interfaz.PruebaGestionPagos;

public enum MenuPrincipal {
	REGISTRAR_FACTURA("Agregar una factura"),
	BUSCAR_FACTURA_POR_NUMERO("Buscar factura por número"),
	OBTENER_FACTURAS_POR_MEDIO_PAGO("Listar facturas pagadas por método de pago"),
	ORDENAR_FACTURAS_POR_MONTO("Ordenar facturas pagadas con monto menor a un valor"),
	VER_TOTAL_POR_SERVICIO("Ver cantidad de facturas pagadas por servicio"),
	VER_PROMEDIO_DE_PAGOS("Calcular promedio de pagos realizados"),
	VER_MONTO_MAXIMO_PAGADO("Consultar monto máximo pagado"),
	SALIR("Salir");
	
	private String descripcion;

	private MenuPrincipal(String descripcion) {
		this.descripcion = descripcion;
	}
	
	private String getDescripcion() {
		return this.descripcion;
	}
	
	public static String opcionesDelMenuPrincipal() {
		String menu = PruebaGestionPagos.mensajeCyanBold("========================================================\n");
		menu += PruebaGestionPagos.mensajeCyanBold("           === ✍️ GESTIÓN DE PAGOS ✍️ ===\n");
		menu += PruebaGestionPagos.mensajeCyanBold("========================================================\n\n");
		
		for (int i = 0; i < MenuPrincipal.values().length; i++) {
			menu += (MenuPrincipal.values()[i].ordinal()) + 1 + ". " + MenuPrincipal.values()[i].getDescripcion() + "\n";
		}
		
		menu += PruebaGestionPagos.mensajeCyanBold("\n========================================================\n");
		
		return menu;
	}
}
