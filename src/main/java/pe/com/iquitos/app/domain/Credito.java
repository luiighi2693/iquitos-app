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

/**
 * A Credito.
 */
@Entity
@Table(name = "credito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "credito")
public class Credito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha")
    private LocalDate fecha;

    @NotNull
    @Column(name = "modo_de_pago", nullable = false)
    private Double modoDePago;

    @Column(name = "numero")
    private Integer numero;

    @NotNull
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;

    @NotNull
    @Size(max = 150)
    @Column(name = "nota_de_credito", length = 150, nullable = false)
    private String notaDeCredito;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Venta venta;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Compra compra;

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

    public Credito monto(Double monto) {
        this.monto = monto;
        return this;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Credito fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getModoDePago() {
        return modoDePago;
    }

    public Credito modoDePago(Double modoDePago) {
        this.modoDePago = modoDePago;
        return this;
    }

    public void setModoDePago(Double modoDePago) {
        this.modoDePago = modoDePago;
    }

    public Integer getNumero() {
        return numero;
    }

    public Credito numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public Credito montoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public Credito fechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
        return this;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getNotaDeCredito() {
        return notaDeCredito;
    }

    public Credito notaDeCredito(String notaDeCredito) {
        this.notaDeCredito = notaDeCredito;
        return this;
    }

    public void setNotaDeCredito(String notaDeCredito) {
        this.notaDeCredito = notaDeCredito;
    }

    public Venta getVenta() {
        return venta;
    }

    public Credito venta(Venta venta) {
        this.venta = venta;
        return this;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Compra getCompra() {
        return compra;
    }

    public Credito compra(Compra compra) {
        this.compra = compra;
        return this;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
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
        Credito credito = (Credito) o;
        if (credito.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), credito.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Credito{" +
            "id=" + getId() +
            ", monto=" + getMonto() +
            ", fecha='" + getFecha() + "'" +
            ", modoDePago=" + getModoDePago() +
            ", numero=" + getNumero() +
            ", montoTotal=" + getMontoTotal() +
            ", fechaLimite='" + getFechaLimite() + "'" +
            ", notaDeCredito='" + getNotaDeCredito() + "'" +
            "}";
    }
}
