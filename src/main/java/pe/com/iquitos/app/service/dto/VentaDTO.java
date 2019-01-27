package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.SellStatus;

/**
 * A DTO for the Venta entity.
 */
public class VentaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String codigo;

    @NotNull
    private Double subTotal;

    @NotNull
    private Double impuesto;

    @NotNull
    private Double montoTotal;

    private LocalDate fecha;

    private SellStatus estatus;

    @Size(max = 1000)
    private String glosa;

    @NotNull
    @Size(max = 5000)
    private String metaData;

    private Long clienteId;

    private String clienteNombre;

    private Long empleadoId;

    private String empleadoNombre;

    private Long cajaId;

    private Long tipoDeDocumentoDeVentaId;

    private String tipoDeDocumentoDeVentaNombre;

    private Long tipoDePagoId;

    private String tipoDePagoNombre;

    private Set<ProductoDTO> productos = new HashSet<>();

    private Set<ProductoDetalleDTO> productoDetalles = new HashSet<>();

    private Set<AmortizacionDTO> amortizacions = new HashSet<>();

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

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public SellStatus getEstatus() {
        return estatus;
    }

    public void setEstatus(SellStatus estatus) {
        this.estatus = estatus;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    public Long getCajaId() {
        return cajaId;
    }

    public void setCajaId(Long cajaId) {
        this.cajaId = cajaId;
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

    public Set<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoDTO> productos) {
        this.productos = productos;
    }

    public Set<ProductoDetalleDTO> getProductoDetalles() {
        return productoDetalles;
    }

    public void setProductoDetalles(Set<ProductoDetalleDTO> productoDetalles) {
        this.productoDetalles = productoDetalles;
    }

    public Set<AmortizacionDTO> getAmortizacions() {
        return amortizacions;
    }

    public void setAmortizacions(Set<AmortizacionDTO> amortizacions) {
        this.amortizacions = amortizacions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (ventaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ventaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", subTotal=" + getSubTotal() +
            ", impuesto=" + getImpuesto() +
            ", montoTotal=" + getMontoTotal() +
            ", fecha='" + getFecha() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombre() + "'" +
            ", empleado=" + getEmpleadoId() +
            ", empleado='" + getEmpleadoNombre() + "'" +
            ", caja=" + getCajaId() +
            ", tipoDeDocumentoDeVenta=" + getTipoDeDocumentoDeVentaId() +
            ", tipoDeDocumentoDeVenta='" + getTipoDeDocumentoDeVentaNombre() + "'" +
            ", tipoDePago=" + getTipoDePagoId() +
            ", tipoDePago='" + getTipoDePagoNombre() + "'" +
            "}";
    }
}
