package com.main.service;

import com.main.model.Pago;
import com.main.model.TipoColegio;
import com.main.model.Usuario;
import com.main.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository userRep;
    @Autowired
    PagoService pagoServ;
    @Autowired
    NotaService notaServ;

    public Usuario crear(Usuario user, Long idTipoColegio) {
        TipoColegio tc = new TipoColegio();
        tc.setId(idTipoColegio);
        user.setTipo_colegio(tc);
        return userRep.save(user);
    }

    public List<Usuario> getALl() {
        return userRep.findAll();
    }

    public Usuario show(Long id) {
        try {
            return userRep.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario findByRut(String rut) {
        try {
            return userRep.findByRut(rut).get();
        } catch (Exception e) {
            return null;
        }
    }

    public String delete(Long id) {
        try {
            userRep.deleteById(id);
            return "Se borro usuario";
        } catch (Exception e) {
            return "No existe usuario con ese id";
        }
    }

    public double getPromedioNotas(Long id) {
        return notaServ.promedioGeneralNotas(id);
    }

    public Integer getTotalNotas(Long id) {
        return notaServ.getByUser(id).size();
    }

    public Integer getMontoArancel(Long id) {
        List<Pago> cuotas = pagoServ.getALlByUser(show(id));
        List<Integer> dcto = pagoServ.getDescuentos(cuotas);
        List<Integer> interes = pagoServ.getInteres(cuotas);
        int totalArancel = 0;

        for (int i = 0; i < cuotas.size(); i++) {
            totalArancel += cuotas.get(i).getTotal() - dcto.get(i) + interes.get(i);
        }

        return totalArancel;
    }

    public List<Integer> cantidadCuotas(Long id) {
        List<Integer> cantidades = new ArrayList<>();

        List<Pago> cuotas = pagoServ.getALlByUser(show(id));
        cantidades.add(cuotas.size());

        Integer pagadas = 0;
        Integer atrasados = 0;

        for (Pago cuota : cuotas) {
            if (cuota.getPagado()) {
                pagadas++;
            }
            if (cuota.getAtrasado()) {
                atrasados++;
            }
        }

        cantidades.add(pagadas);
        cantidades.add(atrasados);

        return cantidades;
    }

    public List<Integer> totalPagadoyPagar(Long id) {
        List<Integer> cantidades = new ArrayList<>();
        int pagado = 0;
        int pagar  = 0;
        List<Pago> cuotas = pagoServ.getALlByUser(show(id));
        List<Integer> dcto = pagoServ.getDescuentos(cuotas);
        List<Integer> interes = pagoServ.getInteres(cuotas);

        for (int i = 0; i < cuotas.size(); i++) {
            if (cuotas.get(i).getPagado()) {
                pagado += cuotas.get(i).getTotal() - dcto.get(i) + interes.get(i);
            }
            else {
                pagar += cuotas.get(i).getTotal() - dcto.get(i) + interes.get(i);
            }
        }

        cantidades.add(pagado);
        cantidades.add(pagar);

        return cantidades;
    }

    public Date obtenerUltimaFechaDePago(Long id) {
        List<Pago> pagos = pagoServ.getALlByUser(show(id));
        Date ultimaFecha = null;

        for (Pago pago : pagos) {
            Date fechaPago = pago.getFecha_pago();
            if (fechaPago != null) {
                if (ultimaFecha == null || fechaPago.after(ultimaFecha)) {
                    ultimaFecha = fechaPago;
                }
            }
        }

        return ultimaFecha;
    }
}
