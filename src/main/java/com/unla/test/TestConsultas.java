package com.unla.test;

import java.time.LocalDate;

import com.unla.funciones.Consultas;

public class TestConsultas {

	public static void main(String[] args) {
		LocalDate fechaDesde = LocalDate.of(2020, 1, 1);
		LocalDate fechaHasta = LocalDate.of(2020, 6, 6);
		Consultas.consulta1(fechaDesde, fechaHasta);
		Consultas.consulta2(fechaDesde, fechaHasta);
		Consultas.consulta3(fechaDesde, fechaHasta);
		Consultas.consulta4(fechaDesde, fechaHasta);
		Consultas.consulta5(fechaDesde, fechaHasta);
		Consultas.consulta6(fechaDesde, fechaHasta);
		Consultas.consulta7(fechaDesde, fechaHasta);
		Consultas.consulta8(fechaDesde, fechaHasta);
	}
}
