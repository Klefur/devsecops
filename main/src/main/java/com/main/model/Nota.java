package com.main.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "nota")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private Long id;

    @Setter @Getter private Integer nota;
    @Setter @Getter private Date fecha;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @Setter @Getter private Usuario usuario;

    public Nota(Integer nota, Usuario usuario) {
        this.nota = nota;
        this.fecha = new Date();
        this.usuario = usuario;

    }
}
