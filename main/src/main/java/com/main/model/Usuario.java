package com.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "usuario")
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter private Long id;

    @Setter @Getter private String nombres;
    @Setter @Getter private String apellidos;
    @Setter @Getter private String rut;
    @Setter @Getter private Date fecha_nacimiento;
    @Setter @Getter private Integer grad_year;
    @Setter @Getter private String nombre_colegio;

    @ManyToOne
    @JoinColumn(name = "id_tipo_colegio")
    @Setter @Getter private TipoColegio tipo_colegio;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    @Setter @Getter private List<Nota> notas;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    @Setter @Getter private List<Pago> pagos;

}
