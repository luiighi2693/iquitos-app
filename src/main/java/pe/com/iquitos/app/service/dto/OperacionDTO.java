package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.OperationType;

/**
 * A DTO for the Operacion entity.
 */
public class OperacionDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    @Size(max = 1000)
    private String glosa;

    @NotNull
    private Double monto;

    private OperationType tipoDeOperacion;

    private Long cajaId;

    private Long tipoDePagoId;

    private String tipoDePagoNombre;

    private Long tipoDeOperacionDeIngresoId;

    private String tipoDeOperacionDeIngresoNombre;

    private Long tipoDeOperacionDeGastoId;

    private String tipoDeOperacionDeGastoNombre;

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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public OperationType getTipoDeOperacion() {
        return tipoDeOperacion;
    }

    public void setTipoDeOperacion(OperationType tipoDeOperacion) {
        this.tipoDeOperacion = tipoDeOperacion;
    }

    public Long getCajaId() {
        return cajaId;
    }

    public void setCajaId(Long cajaId) {
        this.cajaId = cajaId;
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

    public Long getTipoDeOperacionDeIngresoId() {
        return tipoDeOperacionDeIngresoId;
    }

    public void setTipoDeOperacionDeIngresoId(Long tipoDeOperacionDeIngresoId) {
        this.tipoDeOperacionDeIngresoId = tipoDeOperacionDeIngresoId;
    }

    public String getTipoDeOperacionDeIngresoNombre() {
        return tipoDeOperacionDeIngresoNombre;
    }

    public void setTipoDeOperacionDeIngresoNombre(String tipoDeOperacionDeIngresoNombre) {
        this.tipoDeOperacionDeIngresoNombre = tipoDeOperacionDeIngresoNombre;
    }

    public Long getTipoDeOperacionDeGastoId() {
        return tipoDeOperacionDeGastoId;
    }

    public void setTipoDeOperacionDeGastoId(Long tipoDeOperacionDeGastoId) {
        this.tipoDeOperacionDeGastoId = tipoDeOperacionDeGastoId;
    }

    public String getTipoDeOperacionDeGastoNombre() {
        return tipoDeOperacionDeGastoNombre;
    }

    public void setTipoDeOperacionDeGastoNombre(String tipoDeOperacionDeGastoNombre) {
        this.tipoDeOperacionDeGastoNombre = tipoDeOperacionDeGastoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperacionDTO operacionDTO = (OperacionDTO) o;
        if (operacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperacionDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", monto=" + getMonto() +
            ", tipoDeOperacion='" + getTipoDeOperacion() + "'" +
            ", caja=" + getCajaId() +
            ", tipoDePago=" + getTipoDePagoId() +
            ", tipoDePago='" + getTipoDePagoNombre() + "'" +
            ", tipoDeOperacionDeIngreso=" + getTipoDeOperacionDeIngresoId() +
            ", tipoDeOperacionDeIngreso='" + getTipoDeOperacionDeIngresoNombre() + "'" +
            ", tipoDeOperacionDeGasto=" + getTipoDeOperacionDeGastoId() +
            ", tipoDeOperacionDeGasto='" + getTipoDeOperacionDeGastoNombre() + "'" +
            "}";
    }
}
