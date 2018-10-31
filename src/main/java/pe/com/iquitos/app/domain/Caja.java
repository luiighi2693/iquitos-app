package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Caja.
 */
@Entity
@Table(name = "caja")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "caja")
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "monto", nullable = false)
    private Double monto;

    @NotNull
    @Column(name = "monto_actual", nullable = false)
    private Double montoActual;

    @Column(name = "fecha_inicial")
    private LocalDate fechaInicial;

    @Column(name = "fecha_final")
    private LocalDate fechaFinal;

    @OneToOne    @JoinColumn(unique = true)
    private Caja caja;

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

    public Caja monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoActual() {
        return montoActual;
    }

    public Caja montoActual(Double montoActual) {
        this.montoActual = montoActual;
        return this;
    }

    public void setMontoActual(Double montoActual) {
        this.montoActual = montoActual;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public Caja fechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
        return this;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public Caja fechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
        return this;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Caja getCaja() {
        return caja;
    }

    public Caja caja(Caja caja) {
        this.caja = caja;
        return this;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
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
        Caja caja = (Caja) o;
        if (caja.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caja.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Caja{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", montoActual=" + getMontoActual() +
            ", fechaInicial='" + getFechaInicial() + "'" +
            ", fechaFinal='" + getFechaFinal() + "'" +
            "}";
    }
}
