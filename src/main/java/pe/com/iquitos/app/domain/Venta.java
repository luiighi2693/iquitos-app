package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.SellStatus;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "venta")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "codigo", length = 150, nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "sub_total", nullable = false)
    private Double subTotal;

    @NotNull
    @Column(name = "impuesto", nullable = false)
    private Double impuesto;

    @NotNull
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "dias_credito")
    private Double diasCredito;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private SellStatus estatus;

    @Size(max = 1000)
    @Column(name = "glosa", length = 1000)
    private String glosa;

    @NotNull
    @Size(max = 5000)
    @Column(name = "meta_data", length = 5000, nullable = false)
    private String metaData;

    @OneToOne    @JoinColumn(unique = true)
    private Caja caja;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDePago tipoDePago;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Empleado empleado;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "venta_productos",
               joinColumns = @JoinColumn(name = "ventas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "productos_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "venta_producto_detalles",
               joinColumns = @JoinColumn(name = "ventas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "producto_detalles_id", referencedColumnName = "id"))
    private Set<ProductoDetalle> productoDetalles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "venta_amortizacion",
               joinColumns = @JoinColumn(name = "ventas_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "amortizacions_id", referencedColumnName = "id"))
    private Set<Amortizacion> amortizacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Venta codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public Venta subTotal(Double subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public Venta impuesto(Double impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public Venta montoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getDiasCredito() {
        return diasCredito;
    }

    public Venta diasCredito(Double diasCredito) {
        this.diasCredito = diasCredito;
        return this;
    }

    public void setDiasCredito(Double diasCredito) {
        this.diasCredito = diasCredito;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Venta fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public SellStatus getEstatus() {
        return estatus;
    }

    public Venta estatus(SellStatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(SellStatus estatus) {
        this.estatus = estatus;
    }

    public String getGlosa() {
        return glosa;
    }

    public Venta glosa(String glosa) {
        this.glosa = glosa;
        return this;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getMetaData() {
        return metaData;
    }

    public Venta metaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Caja getCaja() {
        return caja;
    }

    public Venta caja(Caja caja) {
        this.caja = caja;
        return this;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public TipoDeDocumentoDeVenta getTipoDeDocumentoDeVenta() {
        return tipoDeDocumentoDeVenta;
    }

    public Venta tipoDeDocumentoDeVenta(TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta) {
        this.tipoDeDocumentoDeVenta = tipoDeDocumentoDeVenta;
        return this;
    }

    public void setTipoDeDocumentoDeVenta(TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta) {
        this.tipoDeDocumentoDeVenta = tipoDeDocumentoDeVenta;
    }

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public Venta tipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
        return this;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Venta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Venta empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Venta productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Venta addProductos(Producto producto) {
        this.productos.add(producto);
        return this;
    }

    public Venta removeProductos(Producto producto) {
        this.productos.remove(producto);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public Set<ProductoDetalle> getProductoDetalles() {
        return productoDetalles;
    }

    public Venta productoDetalles(Set<ProductoDetalle> productoDetalles) {
        this.productoDetalles = productoDetalles;
        return this;
    }

    public Venta addProductoDetalles(ProductoDetalle productoDetalle) {
        this.productoDetalles.add(productoDetalle);
        return this;
    }

    public Venta removeProductoDetalles(ProductoDetalle productoDetalle) {
        this.productoDetalles.remove(productoDetalle);
        return this;
    }

    public void setProductoDetalles(Set<ProductoDetalle> productoDetalles) {
        this.productoDetalles = productoDetalles;
    }

    public Set<Amortizacion> getAmortizacions() {
        return amortizacions;
    }

    public Venta amortizacions(Set<Amortizacion> amortizacions) {
        this.amortizacions = amortizacions;
        return this;
    }

    public Venta addAmortizacion(Amortizacion amortizacion) {
        this.amortizacions.add(amortizacion);
        return this;
    }

    public Venta removeAmortizacion(Amortizacion amortizacion) {
        this.amortizacions.remove(amortizacion);
        return this;
    }

    public void setAmortizacions(Set<Amortizacion> amortizacions) {
        this.amortizacions = amortizacions;
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
        Venta venta = (Venta) o;
        if (venta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), venta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Venta{" +
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
            "}";
    }
}
