package ar.edu.unlam.pb1.dominio;

import ar.edu.unlam.pb1.dominio.enums.EstadoFactura;
import ar.edu.unlam.pb1.dominio.enums.MetodoPago;
import ar.edu.unlam.pb1.dominio.enums.Servicio;

public class Factura {
	private static int proximoId = 0;

	private int numeroFactura;
	private String nombreCliente;
	private Servicio servicio;
	private double monto;
	private MetodoPago metodoPago;
	private EstadoFactura estadoFactura;

	public Factura(String nombreCliente, Servicio servicio, double monto, MetodoPago metodoPago,
			EstadoFactura estadoFactura) {
		this.numeroFactura = ++proximoId;
		this.nombreCliente = nombreCliente;
		this.servicio = servicio;
		this.monto = monto;
		this.metodoPago = metodoPago;
		this.estadoFactura = estadoFactura;
	}

	public int getNumeroFactura() {
		return this.numeroFactura;
	}

	public MetodoPago getMetodoPago() {
		return this.metodoPago;
	}

	public EstadoFactura getEstadoFactura() {
		return this.estadoFactura;
	}

	public double getMonto() {
		return this.monto;
	}

	public Servicio getServicio() {
		return this.servicio;
	}

	@Override
	public String toString() {
		return String.format(
				"\n-Número de factura: %d\n-Nombre de cliente: %s\n-Servicio: %s\n-Monto: $%.2f\n-Metodo de pago: %s\n-Estado de factura: %s\n",
				this.numeroFactura, this.nombreCliente, this.servicio, this.monto, this.metodoPago, this.estadoFactura);
	}
}
