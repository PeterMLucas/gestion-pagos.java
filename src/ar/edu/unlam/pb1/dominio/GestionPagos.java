package ar.edu.unlam.pb1.dominio;

import ar.edu.unlam.pb1.dominio.enums.EstadoFactura;
import ar.edu.unlam.pb1.dominio.enums.MetodoPago;
import ar.edu.unlam.pb1.dominio.enums.Servicio;

public class GestionPagos {
	private Factura[] facturas;
	private static final int CANTIDAD_FACTURAS = 100;

	public GestionPagos() {
		this.facturas = new Factura[CANTIDAD_FACTURAS];
	}

	// Permite agregar una nueva factura al arreglo controlando no superar el tamaño del mismo.
	public boolean agregarFactura(String nombreCliente, Servicio servicio, double monto, MetodoPago metodoPago,
			EstadoFactura estadoFactura) {
		int indice = 0;
		boolean seAgregoFactura = false;

		while (!seAgregoFactura && indice < this.facturas.length) {
			if (this.facturas[indice] == null) {
				this.facturas[indice] = new Factura(nombreCliente, servicio, monto, metodoPago, estadoFactura);
				seAgregoFactura = true;
			}
			indice++;
		}
		return seAgregoFactura;
	}

	// Retorna un objeto Factura específico realizando una búsqueda lineal.
	public Factura busquedaFacturaPorNumero(int numeroFactura) {
		Factura facturaPorNumero = null;
		int indice = 0;

		while (facturaPorNumero == null && indice < this.facturas.length) {
			if (this.facturas[indice] != null && this.facturas[indice].getNumeroFactura() == numeroFactura) {
				facturaPorNumero = this.facturas[indice];
			}
			indice++;
		}

		return facturaPorNumero;
	}

	// Retorna un nuevo array estático con los elementos filtrados por un MetodoPago particular (ej. TARJETA_CREDITO).
	public Factura[] obtenerTodasLasFacturasQueFueronPagadaPorMetodoDePago(MetodoPago metodoPago) {
		Factura[] facturaPorMetodoPago = new Factura[facturas.length];
		int indice = 0;

		for (int i = 0; i < facturas.length; i++) {
			if (this.facturas[i] != null && this.facturas[i].getMetodoPago().equals(metodoPago)
					&& this.facturas[i].getEstadoFactura().equals(EstadoFactura.PAGADO)) {
				facturaPorMetodoPago[indice++] = this.facturas[i];
			}
		}
		return facturaPorMetodoPago;
	}

	// Implementar el algoritmo de ordenamiento por Burbuja ordenando las facturas
	// que tiene como estado PAGADO y además sean menor a un monto ingresado.
	public Factura[] ordenarDescendenteTodasLasFacturasEnEstadoPagadoConMontoMenorA(double monto) {
		Factura[] facturasPagadas = new Factura[facturas.length];

		arrayConFacturasPagadas(monto, facturasPagadas);

		return facturasPagadas;
	}

	// Calcular cuántas facturas en estado PAGADAS hay según servicio ingresado:
	// LUZ, de AGUA, etc.
	public int obtenerLaCantidadDeFacturasPagadasPorServicio(Servicio servicio) {
		int cantidadDeFacturas = 0;

		for (int i = 0; i < facturas.length; i++) {
			if (this.facturas[i] != null && this.facturas[i].getEstadoFactura().equals(EstadoFactura.PAGADO)
					&& this.facturas[i].getServicio().equals(servicio)) {
				cantidadDeFacturas++;
			}
		}
		return cantidadDeFacturas;
	}

	// Calcula el monto promedio abonado considerando únicamente las facturas en
	// estado PAGADO.
	public double obtenerElPromedioDePagosRealizados() {
		int totalDeFacturasPagadas = 0;
		double montoTotalAbonado = 0;

		for (int i = 0; i < facturas.length; i++) {
			if (this.facturas[i] != null && this.facturas[i].getEstadoFactura().equals(EstadoFactura.PAGADO)) {
				totalDeFacturasPagadas++;
				montoTotalAbonado += this.facturas[i].getMonto();
			}
		}
		return totalDeFacturasPagadas != 0 ? montoTotalAbonado / totalDeFacturasPagadas : 0;
	}

	// Devuelve el valor del monto correspondiente al pago más alto registrado.
	// En caso de no existir al menos un pago, informar lo propio.
	public double obtenerElMontoMaximoPagado() {
		double montoMaximo = 0;

		for (int i = 0; i < facturas.length; i++) {
			if (this.facturas[i] != null && this.facturas[i].getEstadoFactura().equals(EstadoFactura.PAGADO)
					&& this.facturas[i].getMonto() > montoMaximo) {
				montoMaximo = this.facturas[i].getMonto();
			}
		}
		return montoMaximo;
	}

	// Recorre las facturas y guarda en un nuevo array solo las facturas pagadas y
	// con monto menor a (Ordena en ese nuevo array dichas facturas)
	private void arrayConFacturasPagadas(double monto, Factura[] facturasPagadas) {
		Factura auxiliar;
		int indice = 0;
		
		for (int i = 0; i < facturas.length; i++) {
			if (this.facturas[i] != null && this.facturas[i].getEstadoFactura().equals(EstadoFactura.PAGADO)
					&& this.facturas[i].getMonto() < monto) {
				facturasPagadas[indice++] = this.facturas[i];
			}
		}

		for (int i = 0; i < facturasPagadas.length; i++) {
			for (int j = 0; j < facturasPagadas.length - 1 - i; j++) {
				if (facturasPagadas[j] != null && facturasPagadas[j + 1] != null
						&& facturasPagadas[j].getMonto() < facturasPagadas[j + 1].getMonto()) {
					auxiliar = facturasPagadas[j];
					facturasPagadas[j] = facturasPagadas[j + 1];
					facturasPagadas[j + 1] = auxiliar;
				}
			}
		}
	}

}
