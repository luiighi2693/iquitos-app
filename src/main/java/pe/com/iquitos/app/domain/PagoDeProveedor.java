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
 * A PagoDeProveedor.
 */
@Entity
@Table(name = "pago_de_proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pagodeproveedor")
public class PagoDeProveedor implements Serializable {

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
    @Column(name = "codigo_de_documento", length = 150, nullable = false)
    private String codigoDeDocumento;

    @Size(max = 1000)
    @Column(name = "glosa", length = 1000)
    private String glosa;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDePago tipoDePago;

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

    public PagoDeProveedor monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public PagoDeProveedor montoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
        return this;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public PagoDeProveedor fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCodigoDeDocumento() {
        return codigoDeDocumento;
    }

    public PagoDeProveedor codigoDeDocumento(String codigoDeDocumento) {
        this.codigoDeDocumento = codigoDeDocumento;
        return this;
    }

    public void setCodigoDeDocumento(String codigoDeDocumento) {
        this.codigoDeDocumento = codigoDeDocumento;
    }

    public String getGlosa() {
        return glosa;
    }

    public PagoDeProveedor glosa(String glosa) {
        this.glosa = glosa;
        return this;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public PagoDeProveedor imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public PagoDeProveedor imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public TipoDeDocumentoDeCompra getTipoDeDocumentoDeCompra() {
        return tipoDeDocumentoDeCompra;
    }

    public PagoDeProveedor tipoDeDocumentoDeCompra(TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra) {
        this.tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompra;
        return this;
    }

    public void setTipoDeDocumentoDeCompra(TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra) {
        this.tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompra;
    }

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public PagoDeProveedor tipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
        return this;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
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
        PagoDeProveedor pagoDeProveedor = (PagoDeProveedor) o;
        if (pagoDeProveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagoDeProveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagoDeProveedor{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", montoPagado=" + getMontoPagado() +
            ", fecha='" + getFecha() + "'" +
            ", codigoDeDocumento='" + getCodigoDeDocumento() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
