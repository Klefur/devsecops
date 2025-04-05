package com.main.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private Long id;

    @Setter @Getter private Integer total;
    @Setter @Getter private Integer nro_cuota;
    @Setter @Getter private Date fecha_plazo;
    @Setter @Getter private Date fecha_pago;
    @Setter @Getter private Boolean pagado = Boolean.FALSE;
    @Setter @Getter private Boolean atrasado = Boolean.FALSE;
    @Setter @Getter private Integer dcto_aplicable;
    @Setter @Getter private Integer interes_acumulado;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @Setter @Getter private Usuario usuario;
}
