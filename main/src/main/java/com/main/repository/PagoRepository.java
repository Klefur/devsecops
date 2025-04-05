package com.main.repository;

import com.main.model.Pago;
import com.main.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findAllByUsuario(Usuario usuario);
}
