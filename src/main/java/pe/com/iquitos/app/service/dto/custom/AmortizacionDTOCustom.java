package pe.com.iquitos.app.service.dto.custom;

//import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Amortizacion entity.
 */
public class AmortizacionDTOCustom implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String codigo;

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

    @Size(max = 1000)
    private String comprobante;

//    @Lob
//    private byte[] fotoComprobante;
//    private String fotoComprobanteContentType;

    private Long tipoDeDocumentoDeVentaId;

    private String tipoDeDocumentoDeVentaNombre;

    private Long tipoDePagoId;

    private String tipoDePagoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

//    public byte[] getFotoComprobante() {
//        return fotoComprobante;
//    }
//
//    public void setFotoComprobante(byte[] fotoComprobante) {
//        this.fotoComprobante = fotoComprobante;
//    }
//
//    public String getFotoComprobanteContentType() {
//        return fotoComprobanteContentType;
//    }
//
//    public void setFotoComprobanteContentType(String fotoComprobanteContentType) {
//        this.fotoComprobanteContentType = fotoComprobanteContentType;
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmortizacionDTOCustom amortizacionDTO = (AmortizacionDTOCustom) o;
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
            ", codigo='" + getCodigo() + "'" +
            ", monto=" + getMonto() +
            ", montoPagado=" + getMontoPagado() +
            ", fecha='" + getFecha() + "'" +
            ", codigoDocumento='" + getCodigoDocumento() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", comprobante='" + getComprobante() + "'" +
//            ", fotoComprobante='" + getFotoComprobante() + "'" +
            ", tipoDeDocumentoDeVenta=" + getTipoDeDocumentoDeVentaId() +
            ", tipoDeDocumentoDeVenta='" + getTipoDeDocumentoDeVentaNombre() + "'" +
            ", tipoDePago=" + getTipoDePagoId() +
            ", tipoDePago='" + getTipoDePagoNombre() + "'" +
            "}";
    }
}
