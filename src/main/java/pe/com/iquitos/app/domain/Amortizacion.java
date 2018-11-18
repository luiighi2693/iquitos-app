package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Amortizacion.
 */
@Entity
@Table(name = "amortizacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "amortizacion")
public class Amortizacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "monto", nullable = false)
    private Double monto;

    @NotNull
    @Column(name = "monto_pagado", nullable = false)
    private Double montoPagado;

    @Column(name = "fecha")
    private LocalDate fecha;

    @NotNull
    @Size(max = 150)
    @Column(name = "codigo_documento", length = 150, nullable = false)
    private String codigoDocumento;

    @Size(max = 1000)
    @Column(name = "glosa", length = 1000)
    private String glosa;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDePago tipoDePago;

    @ManyToOne
    @JsonIgnoreProperties("amortizacions")
    private Venta venta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public Amortizacion monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public Amortizacion montoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
        return this;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Amortizacion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public Amortizacion codigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
        return this;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getGlosa() {
        return glosa;
    }

    public Amortizacion glosa(String glosa) {
        this.glosa = glosa;
        return this;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public TipoDeDocumentoDeVenta getTipoDeDocumentoDeVenta() {
        return tipoDeDocumentoDeVenta;
    }

    public Amortizacion tipoDeDocumentoDeVenta(TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta) {
        this.tipoDeDocumentoDeVenta = tipoDeDocumentoDeVenta;
        return this;
    }

    public void setTipoDeDocumentoDeVenta(TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta) {
        this.tipoDeDocumentoDeVenta = tipoDeDocumentoDeVenta;
    }

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public Amortizacion tipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
        return this;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public Venta getVenta() {
        return venta;
    }

    public Amortizacion venta(Venta venta) {
        this.venta = venta;
        return this;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Amortizacion amortizacion = (Amortizacion) o;
        if (amortizacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Amortizacion{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", montoPagado=" + getMontoPagado() +
            ", fecha='" + getFecha() + "'" +
            ", codigoDocumento='" + getCodigoDocumento() + "'" +
            ", glosa='" + getGlosa() + "'" +
            "}";
    }
}
