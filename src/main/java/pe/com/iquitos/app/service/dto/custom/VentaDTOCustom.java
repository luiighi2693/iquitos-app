package pe.com.iquitos.app.service.dto.custom;

import pe.com.iquitos.app.domain.enumeration.SellStatus;
import pe.com.iquitos.app.service.dto.ProductoDTO;
import pe.com.iquitos.app.service.dto.ProductoDetalleDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Venta entity.
 */
public class VentaDTOCustom implements Serializable {

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

    private Double diasCredito;

    private LocalDate fecha;

    private SellStatus estatus;

    @Size(max = 1000)
    private String glosa;

    @NotNull
    @Size(max = 5000)
    private String metaData;

    private Long cajaId;

    private Long tipoDeDocumentoDeVentaId;

    private String tipoDeDocumentoDeVentaNombre;

    private Long tipoDePagoId;

    private String tipoDePagoNombre;

    private Long clienteId;

    private String clienteNombre;

    private Long empleadoId;

    private String empleadoNombre;

    private Set<ProductoDTO> productos = new HashSet<>();

    private Set<ProductoDetalleDTO> productoDetalles = new HashSet<>();

    private Set<AmortizacionDTOCustom> amortizacions = new HashSet<>();

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

    public Double getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Double diasCredito) {
        this.diasCredito = diasCredito;
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

    public Set<AmortizacionDTOCustom> getAmortizacions() {
        return amortizacions;
    }

    public void setAmortizacions(Set<AmortizacionDTOCustom> amortizacions) {
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

        VentaDTOCustom ventaDTO = (VentaDTOCustom) o;
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
            ", diasCredito=" + getDiasCredito() +
            ", fecha='" + getFecha() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", caja=" + getCajaId() +
            ", tipoDeDocumentoDeVenta=" + getTipoDeDocumentoDeVentaId() +
            ", tipoDeDocumentoDeVenta='" + getTipoDeDocumentoDeVentaNombre() + "'" +
            ", tipoDePago=" + getTipoDePagoId() +
            ", tipoDePago='" + getTipoDePagoNombre() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombre() + "'" +
            ", empleado=" + getEmpleadoId() +
            ", empleado='" + getEmpleadoNombre() + "'" +
            "}";
    }
}
