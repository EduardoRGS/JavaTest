package com.api.SigaBem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "encomenda")
@Getter
@Setter
@ToString
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    @Size(max = 8)
    private String cepOrigem;

    @Column(nullable = false)
    @Size(max = 8)
    private String cepDestino;

    @Size(max = 2)
    @Column(nullable = false)
    private String ddd;

    @Size(max = 2)
    @Column(nullable = false)
    private String uf;

    @Column(nullable = false)
    private String nomeDestinatario;

    @Column(nullable = false)
    private double vlTotalFrete;

    @Column(nullable = false)
    private Date dataPrevistaEntrega;

    @UpdateTimestamp
    private Date dataConsulta;
}
