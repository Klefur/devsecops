package com.main;

import com.main.model.Nota;
import com.main.model.Pago;
import com.main.model.Usuario;
import com.main.repository.NotaRepository;
import com.main.service.PagoService;
import com.main.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UsuarioTests {
    @Autowired
    UsuarioService uServ;
    @Autowired
    PagoService pagServ;
    @Autowired
    NotaRepository notaRep;

    @Test
    void testUsuario() {
        Usuario user = new Usuario();

        Date fecha = new Date();

        user.setNombres("Valentina");
        user.setApellidos("Campos");
        user.setGrad_year(2020);
        user.setRut("1234567");
        user.setFecha_nacimiento(fecha);
        user.setNombre_colegio("College");
        user.setId(12L);
        user = uServ.crear(user, 1L);
        assertNotNull(uServ.getALl());
        assertNotNull(uServ.show(user.getId()));
        assertNotNull(uServ.findByRut(user.getRut()));

        Pago form = new Pago();
        form.setNro_cuota(1);
        pagServ.generarCuotas(form, user.getId());
        assertNotNull(uServ.obtenerUltimaFechaDePago(user.getId()));
        assertNotNull(uServ.getMontoArancel(user.getId()));
        assertNotNull(uServ.totalPagadoyPagar(user.getId()));
        assertNotNull(uServ.cantidadCuotas(user.getId()));
        pagServ.delete(1L);

        Nota nota = new Nota();
        nota.setUsuario(user);
        nota.setNota(1000);
        nota.setFecha(new Date());
        notaRep.save(nota);
        assert(uServ.getPromedioNotas(user.getId()) > 0);
        assertNotNull(uServ.getTotalNotas(user.getId()));
        notaRep.delete(nota);


        uServ.delete(user.getId());
    }

}