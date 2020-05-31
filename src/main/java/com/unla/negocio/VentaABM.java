package com.unla.negocio;

import com.unla.funciones.Funciones;
import java.util.List;
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
    
    public void agregar(final Venta objeto) throws Exception {
        final Venta venta = this.traer(objeto.getNroTicket());
        if (venta != null) {
            throw new Exception("Ya existe el ticket con el nroTicket.");
        }
        if (!Funciones.validarNroTicket(objeto.getNroTicket())) {
            throw new Exception("El nroTicket ingresado no es v\u00e1lido.");
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