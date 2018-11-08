package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the PagoDeProveedor entity.
 */
public class PagoDeProveedorDTO implements Serializable {

    private Long id;

    @NotNull
    private Double monto;

    @NotNull
    private Double montoPagado;

    private LocalDate fecha;

    @NotNull
    @Size(max = 150)
    private String codigoDeDocumento;

    @Size(max = 1000)
    private String glosa;

    @Lob
    private byte[] imagen;
    private String imagenContentType;

    private Long tipoDeDocumentoDeCompraId;

    private String tipoDeDocumentoDeCompraNombre;

    private Long tipoDePagoId;

    private String tipoDePagoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCodigoDeDocumento() {
        return codigoDeDocumento;
    }

    public void setCodigoDeDocumento(String codigoDeDocumento) {
        this.codigoDeDocumento = codigoDeDocumento;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Long getTipoDeDocumentoDeCompraId() {
        return tipoDeDocumentoDeCompraId;
    }

    public void setTipoDeDocumentoDeCompraId(Long tipoDeDocumentoDeCompraId) {
        this.tipoDeDocumentoDeCompraId = tipoDeDocumentoDeCompraId;
    }

    public String getTipoDeDocumentoDeCompraNombre() {
        return tipoDeDocumentoDeCompraNombre;
    }

    public void setTipoDeDocumentoDeCompraNombre(String tipoDeDocumentoDeCompraNombre) {
        this.tipoDeDocumentoDeCompraNombre = tipoDeDocumentoDeCompraNombre;
    }

    public Long getTipoDePagoId() {
        return tipoDePagoId;
    }

    public void setTipoDePagoId(Long tipoDePagoId) {
        this.tipoDePagoId = tipoDePagoId;
    }

    public String getTipoDePagoNombre() {
        return tipoDePagoNombre;
    }

    public void setTipoDePagoNombre(String tipoDePagoNombre) {
        this.tipoDePagoNombre = tipoDePagoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PagoDeProveedorDTO pagoDeProveedorDTO = (PagoDeProveedorDTO) o;
        if (pagoDeProveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pagoDeProveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagoDeProveedorDTO{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", montoPagado=" + getMontoPagado() +
            ", fecha='" + getFecha() + "'" +
            ", codigoDeDocumento='" + getCodigoDeDocumento() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", tipoDeDocumentoDeCompra=" + getTipoDeDocumentoDeCompraId() +
            ", tipoDeDocumentoDeCompra='" + getTipoDeDocumentoDeCompraNombre() + "'" +
            ", tipoDePago=" + getTipoDePagoId() +
            ", tipoDePago='" + getTipoDePagoNombre() + "'" +
            "}";
    }
}
