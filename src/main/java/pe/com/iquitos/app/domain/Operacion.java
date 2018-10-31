package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.OperationType;

/**
 * A Operacion.
 */
@Entity
@Table(name = "operacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "operacion")
public class Operacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Size(max = 1000)
    @Column(name = "glosa", length = 1000)
    private String glosa;

    @NotNull
    @Column(name = "monto", nullable = false)
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_operacion")
    private OperationType tipoDeOperacion;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Caja caja;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDePago tipoDePago;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDeOperacionDeGasto tipoDeOperacionDeGasto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Operacion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getGlosa() {
        return glosa;
    }

    public Operacion glosa(String glosa) {
        this.glosa = glosa;
        return this;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public Double getMonto() {
        return monto;
    }

    public Operacion monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public OperationType getTipoDeOperacion() {
        return tipoDeOperacion;
    }

    public Operacion tipoDeOperacion(OperationType tipoDeOperacion) {
        this.tipoDeOperacion = tipoDeOperacion;
        return this;
    }

    public void setTipoDeOperacion(OperationType tipoDeOperacion) {
        this.tipoDeOperacion = tipoDeOperacion;
    }

    public Caja getCaja() {
        return caja;
    }

    public Operacion caja(Caja caja) {
        this.caja = caja;
        return this;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public Operacion tipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
        return this;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public TipoDeOperacionDeIngreso getTipoDeOperacionDeIngreso() {
        return tipoDeOperacionDeIngreso;
    }

    public Operacion tipoDeOperacionDeIngreso(TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso) {
        this.tipoDeOperacionDeIngreso = tipoDeOperacionDeIngreso;
        return this;
    }

    public void setTipoDeOperacionDeIngreso(TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso) {
        this.tipoDeOperacionDeIngreso = tipoDeOperacionDeIngreso;
    }

    public TipoDeOperacionDeGasto getTipoDeOperacionDeGasto() {
        return tipoDeOperacionDeGasto;
    }

    public Operacion tipoDeOperacionDeGasto(TipoDeOperacionDeGasto tipoDeOperacionDeGasto) {
        this.tipoDeOperacionDeGasto = tipoDeOperacionDeGasto;
        return this;
    }

    public void setTipoDeOperacionDeGasto(TipoDeOperacionDeGasto tipoDeOperacionDeGasto) {
        this.tipoDeOperacionDeGasto = tipoDeOperacionDeGasto;
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
        Operacion operacion = (Operacion) o;
        if (operacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operacion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", monto=" + getMonto() +
            ", tipoDeOperacion='" + getTipoDeOperacion() + "'" +
            "}";
    }
}
