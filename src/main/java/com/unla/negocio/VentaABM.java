package com.unla.negocio;

import com.unla.funciones.Funciones;

import java.time.LocalDate;
import java.util.List;

import org.bson.Document;

import com.unla.datos.Venta;
import com.unla.dao.VentaDao;

public class VentaABM
{
    private static VentaABM instancia;
    private static VentaDao dao;
    
    static {
        VentaABM.instancia = null;
        VentaABM.dao = null;
    }
    
    protected VentaABM() {
        VentaABM.dao = VentaDao.getInstance();
    }
    
    public static VentaABM getInstance() {
        if (VentaABM.instancia == null) {
            VentaABM.instancia = new VentaABM();
        }
        return VentaABM.instancia;
    }
    
    public Venta traer(final String nroTicket) {
        return VentaABM.dao.traer(nroTicket);
    }
    
    public List<Venta> traer() {
        return (List<Venta>)VentaABM.dao.traer();
    }
    
    public List<Venta> traerEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Venta>)VentaABM.dao.traerEntreFechas(fechaDesde, fechaHasta);
    }

    public List<Venta> ventasCadaSucursalPorFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Venta>)VentaABM.dao.ventasCadaSucursalPorFecha(fechaDesde, fechaHasta);
    }

    public List<Document> totalCadaVentaEntreFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.totalCadaVentaEntreFecha(fechaDesde, fechaHasta);
    }

    public Document detalleYTotalVentasSucursalesEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (Document)VentaABM.dao.detalleYTotalVentasSucursalesEntreFechas(fechaDesde, fechaHasta);
    }
    
    public List<Document> detallesVentaEntreFechaPorTipo(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.detallesVentaEntreFechaPorTipo(fechaDesde, fechaHasta);
    }
    
    public List<Document> traerVentasPorObraSocialEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.traerVentasPorObraSocialEntreFechas(fechaDesde, fechaHasta);
    }
    
    public List<Document> traerRankingProductosPorMontoEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.traerRankingProductosPorMontoEntreFechas(fechaDesde, fechaHasta);
    }
    
    public List<Document> traerRankingProductosPorCantidadEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.traerRankingProductosPorCantidadEntreFechas(fechaDesde, fechaHasta);
    }
    
    public List<Document> traerCobranzasPorMedioPagoEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.traerCobranzasPorMedioPagoEntreFechas(fechaDesde, fechaHasta);
    }
    
    public List<Document> traerRankingClientesPorMontoEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.traerRankingClientesPorMontoEntreFechas(fechaDesde, fechaHasta);
    }
    
    public List<Document> traerRankingClientesPorCantidadEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        return (List<Document>)VentaABM.dao.traerRankingClientesPorCantidadEntreFechas(fechaDesde, fechaHasta);
    }
    
    public void agregar(final Venta objeto) throws Exception {
        final Venta venta = this.traer(objeto.getNroTicket());
        if (venta != null) {
            throw new Exception("Ya existe la venta con el nroTicket.");
        }
        if (!Funciones.validarNroTicket(objeto.getNroTicket())) {
            throw new Exception("El nroTicket ingresado no es válido.");
        }
        VentaABM.dao.agregar(objeto);
    }
    
    public void modificar(final Venta objetoModificado) throws Exception {
        final Venta venta = this.traer(objetoModificado.getNroTicket());
        if (venta == null) {
            throw new Exception("La venta especificada no existe.");
        }
        VentaABM.dao.modificar(objetoModificado);
    }
    
    public void eliminar(final Venta objeto) throws Exception {
        final long cant = VentaABM.dao.eliminar(objeto);
        if (cant == 0L) {
            throw new Exception("No existe el venta con ese Numero de Ticket.");
        }
    }
}