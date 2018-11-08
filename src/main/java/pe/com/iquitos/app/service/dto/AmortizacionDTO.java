package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Amortizacion entity.
 */
public class AmortizacionDTO implements Serializable {

    private Long id;

    @NotNull
    private Double monto;

    @NotNull
    private Double montoPagado;

    private LocalDate fecha;

    @NotNull
    @Size(max = 150)
    private String codigoDocumento;

    @Size(max = 1000)
    private String glosa;

    private Long tipoDeDocumentoDeVentaId;

    private String tipoDeDocumentoDeVentaNombre;

    private Long tipoDePagoId;

    private String tipoDePagoNombre;

    private Long ventaId;

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

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public Long getTipoDeDocumentoDeVentaId() {
        return tipoDeDocumentoDeVentaId;
    }

    public void setTipoDeDocumentoDeVentaId(Long tipoDeDocumentoDeVentaId) {
        this.tipoDeDocumentoDeVentaId = tipoDeDocumentoDeVentaId;
    }

    public String getTipoDeDocumentoDeVentaNombre() {
        return tipoDeDocumentoDeVentaNombre;
    }

    public void setTipoDeDocumentoDeVentaNombre(String tipoDeDocumentoDeVentaNombre) {
        this.tipoDeDocumentoDeVentaNombre = tipoDeDocumentoDeVentaNombre;
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

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmortizacionDTO amortizacionDTO = (AmortizacionDTO) o;
        if (amortizacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmortizacionDTO{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", montoPagado=" + getMontoPagado() +
            ", fecha='" + getFecha() + "'" +
            ", codigoDocumento='" + getCodigoDocumento() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", tipoDeDocumentoDeVenta=" + getTipoDeDocumentoDeVentaId() +
            ", tipoDeDocumentoDeVenta='" + getTipoDeDocumentoDeVentaNombre() + "'" +
            ", tipoDePago=" + getTipoDePagoId() +
            ", tipoDePago='" + getTipoDePagoNombre() + "'" +
            ", venta=" + getVentaId() +
            "}";
    }
}
