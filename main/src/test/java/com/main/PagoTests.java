package com.main;

import com.main.model.Pago;
import com.main.model.Usuario;
import com.main.service.PagoService;
import com.main.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PagoTests {
    @Autowired
    UsuarioService uServ;
    @Autowired
    PagoService pagoServ;

    @Test
    void testPago() {
        Usuario user = new Usuario();

        Date fecha = new Date();

        user.setNombres("Valentina");
        user.setApellidos("Campos");
        user.setGrad_year(2020);
        user.setRut("123456774");
        user.setFecha_nacimiento(fecha);
        user.setNombre_colegio("College");
        user = uServ.crear(user, 1L);


        Pago cuotaForm = new Pago();
        cuotaForm.setNro_cuota(5);
        pagoServ.generarCuotas(cuotaForm, user.getId());

        assertNotNull(pagoServ.getUsuarioById(user.getId()));
        assertNotNull(pagoServ.getALlByUser(user));
        assertNotNull(pagoServ.pagarCuota(1L));
        assertNotNull(pagoServ.calcularInteres(pagoServ.getALlByUser(user)));
        assertNotNull(pagoServ.getInteres(pagoServ.getALlByUser(user)));
        assertNotNull(pagoServ.getDescuentos(pagoServ.getALlByUser(user)));
        pagoServ.delete(1L);
        pagoServ.delete(2L);
        pagoServ.delete(3L);
        pagoServ.delete(4L);
        pagoServ.delete(5L);
        uServ.delete(user.getId());
    }

}