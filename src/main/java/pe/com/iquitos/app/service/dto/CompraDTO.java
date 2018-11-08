package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.PurchaseLocation;
import pe.com.iquitos.app.domain.enumeration.PaymentPurchaseType;

/**
 * A DTO for the Compra entity.
 */
public class CompraDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    @NotNull
    @Size(max = 150)
    private String guiaRemision;

    @NotNull
    @Size(max = 150)
    private String numeroDeDocumento;

    private PurchaseLocation ubicacion;

    @NotNull
    private Double montoTotal;

    @NotNull
    @Size(max = 150)
    private String correlativo;

    private PaymentPurchaseType tipoDePagoDeCompra;

    @NotNull
    @Size(max = 5000)
    private String metaData;

    private Long proveedorId;

    private Long tipoDeDocumentoDeCompraId;

    private String tipoDeDocumentoDeCompraNombre;

    private Long estatusDeCompraId;

    private String estatusDeCompraNombre;

    private Long cajaId;

    private Set<ProductoDTO> productos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(String guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    public PurchaseLocation getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(PurchaseLocation ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public PaymentPurchaseType getTipoDePagoDeCompra() {
        return tipoDePagoDeCompra;
    }

    public void setTipoDePagoDeCompra(PaymentPurchaseType tipoDePagoDeCompra) {
        this.tipoDePagoDeCompra = tipoDePagoDeCompra;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
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

    public Long getEstatusDeCompraId() {
        return estatusDeCompraId;
    }

    public void setEstatusDeCompraId(Long estatusDeCompraId) {
        this.estatusDeCompraId = estatusDeCompraId;
    }

    public String getEstatusDeCompraNombre() {
        return estatusDeCompraNombre;
    }

    public void setEstatusDeCompraNombre(String estatusDeCompraNombre) {
        this.estatusDeCompraNombre = estatusDeCompraNombre;
    }

    public Long getCajaId() {
        return cajaId;
    }

    public void setCajaId(Long cajaId) {
        this.cajaId = cajaId;
    }

    public Set<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoDTO> productos) {
        this.productos = productos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompraDTO compraDTO = (CompraDTO) o;
        if (compraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompraDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", guiaRemision='" + getGuiaRemision() + "'" +
            ", numeroDeDocumento='" + getNumeroDeDocumento() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", montoTotal=" + getMontoTotal() +
            ", correlativo='" + getCorrelativo() + "'" +
            ", tipoDePagoDeCompra='" + getTipoDePagoDeCompra() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", proveedor=" + getProveedorId() +
            ", tipoDeDocumentoDeCompra=" + getTipoDeDocumentoDeCompraId() +
            ", tipoDeDocumentoDeCompra='" + getTipoDeDocumentoDeCompraNombre() + "'" +
            ", estatusDeCompra=" + getEstatusDeCompraId() +
            ", estatusDeCompra='" + getEstatusDeCompraNombre() + "'" +
            ", caja=" + getCajaId() +
            "}";
    }
}
