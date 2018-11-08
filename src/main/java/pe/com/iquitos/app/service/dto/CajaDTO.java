package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Caja entity.
 */
public class CajaDTO implements Serializable {

    private Long id;

    @NotNull
    private Double monto;

    @NotNull
    private Double montoActual;

    private LocalDate fechaInicial;

    private LocalDate fechaFinal;

    private Long cajaId;

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

    public Double getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(Double montoActual) {
        this.montoActual = montoActual;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Long getCajaId() {
        return cajaId;
    }

    public void setCajaId(Long cajaId) {
        this.cajaId = cajaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CajaDTO cajaDTO = (CajaDTO) o;
        if (cajaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cajaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CajaDTO{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", montoActual=" + getMontoActual() +
            ", fechaInicial='" + getFechaInicial() + "'" +
            ", fechaFinal='" + getFechaFinal() + "'" +
            ", caja=" + getCajaId() +
            "}";
    }
}
