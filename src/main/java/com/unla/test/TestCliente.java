package com.unla.test;

import com.unla.datos.Cliente;
import com.unla.negocio.ClienteABM;

public class TestCliente {
    public static void main(String[] args) {
        ClienteABM abm = ClienteABM.getInstance();

        // Agregar cliente
        // ***************************
        Cliente cliente = new Cliente("Juan", "Rodriguez", 11222333, null);
        try {
            abm.agregar(cliente);
            System.out.println("Agregado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Traer cliente
        // ***************************
        cliente = abm.traer(10000000);
        if (cliente == null) {
            System.out.println("Datos del cliente:\n" + cliente);
        } else {
            System.out.println("No existe cliente con ese DNI");
        }

        // Modificar cliente dado su DNI
        // **********************************************
        try {
            abm.modificar(new Cliente("Pepe", "Pasdsa", 10000000, null));
            System.out.println("Cliente modificado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Eliminar cliente dado su DNI
        // **********************************************
        try {
            abm.eliminar(new Cliente("Pepe", "Pasdsa", 11222333, null));
            System.out.println("Cliente eliminado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
