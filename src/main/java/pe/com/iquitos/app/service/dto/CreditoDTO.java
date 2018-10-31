package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Credito entity.
 */
public class CreditoDTO implements Serializable {

    private Long id;

    @NotNull
    private Double monto;

    private LocalDate fecha;

    @NotNull
    private Double modoDePago;

    private Integer numero;

    @NotNull
    private Double montoTotal;

    private LocalDate fechaLimite;

    @NotNull
    @Size(max = 150)
    private String notaDeCredito;

    private Long ventaId;

    private Long compraId;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getModoDePago() {
        return modoDePago;
    }

    public void setModoDePago(Double modoDePago) {
        this.modoDePago = modoDePago;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getNotaDeCredito() {
        return notaDeCredito;
    }

    public void setNotaDeCredito(String notaDeCredito) {
        this.notaDeCredito = notaDeCredito;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Long getCompraId() {
        return compraId;
    }

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditoDTO creditoDTO = (CreditoDTO) o;
        if (creditoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditoDTO{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", fecha='" + getFecha() + "'" +
            ", modoDePago=" + getModoDePago() +
            ", numero=" + getNumero() +
            ", montoTotal=" + getMontoTotal() +
            ", fechaLimite='" + getFechaLimite() + "'" +
            ", notaDeCredito='" + getNotaDeCredito() + "'" +
            ", venta=" + getVentaId() +
            ", compra=" + getCompraId() +
            "}";
    }
}
