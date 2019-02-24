package pe.com.iquitos.app.domain;

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

import pe.com.iquitos.app.domain.enumeration.PurchaseLocation;

import pe.com.iquitos.app.domain.enumeration.PaymentPurchaseType;

import pe.com.iquitos.app.domain.enumeration.TipoDeTransaccion;

import pe.com.iquitos.app.domain.enumeration.PurchaseStatus;

/**
 * A Compra.
 */
@Entity
@Table(name = "compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "compra")
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @NotNull
    @Size(max = 150)
    @Column(name = "guia_remision", length = 150, nullable = false)
    private String guiaRemision;

    @NotNull
    @Size(max = 150)
    @Column(name = "numero_de_documento", length = 150, nullable = false)
    private String numeroDeDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "ubicacion")
    private PurchaseLocation ubicacion;

    @NotNull
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @NotNull
    @Size(max = 150)
    @Column(name = "correlativo", length = 150, nullable = false)
    private String correlativo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_pago_de_compra")
    private PaymentPurchaseType tipoDePagoDeCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_transaccion")
    private TipoDeTransaccion tipoDeTransaccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private PurchaseStatus estatus;

    @NotNull
    @Size(max = 5000)
    @Column(name = "meta_data", length = 5000, nullable = false)
    private String metaData;

    @OneToOne    @JoinColumn(unique = true)
    private Proveedor proveedor;

    @OneToOne    @JoinColumn(unique = true)
    private TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra;

    @OneToOne    @JoinColumn(unique = true)
    private EstatusDeCompra estatusDeCompra;

    @OneToOne    @JoinColumn(unique = true)
    private Caja caja;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "compra_productos",
               joinColumns = @JoinColumn(name = "compras_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "productos_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

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

    public Compra fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getGuiaRemision() {
        return guiaRemision;
    }

    public Compra guiaRemision(String guiaRemision) {
        this.guiaRemision = guiaRemision;
        return this;
    }

    public void setGuiaRemision(String guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public Compra numeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
        return this;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    public PurchaseLocation getUbicacion() {
        return ubicacion;
    }

    public Compra ubicacion(PurchaseLocation ubicacion) {
        this.ubicacion = ubicacion;
        return this;
    }

    public void setUbicacion(PurchaseLocation ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public Compra montoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
        return this;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCorrelativo() {
        return correlativo;
    }

    public Compra correlativo(String correlativo) {
        this.correlativo = correlativo;
        return this;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public PaymentPurchaseType getTipoDePagoDeCompra() {
        return tipoDePagoDeCompra;
    }

    public Compra tipoDePagoDeCompra(PaymentPurchaseType tipoDePagoDeCompra) {
        this.tipoDePagoDeCompra = tipoDePagoDeCompra;
        return this;
    }

    public void setTipoDePagoDeCompra(PaymentPurchaseType tipoDePagoDeCompra) {
        this.tipoDePagoDeCompra = tipoDePagoDeCompra;
    }

    public TipoDeTransaccion getTipoDeTransaccion() {
        return tipoDeTransaccion;
    }

    public Compra tipoDeTransaccion(TipoDeTransaccion tipoDeTransaccion) {
        this.tipoDeTransaccion = tipoDeTransaccion;
        return this;
    }

    public void setTipoDeTransaccion(TipoDeTransaccion tipoDeTransaccion) {
        this.tipoDeTransaccion = tipoDeTransaccion;
    }

    public PurchaseStatus getEstatus() {
        return estatus;
    }

    public Compra estatus(PurchaseStatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(PurchaseStatus estatus) {
        this.estatus = estatus;
    }

    public String getMetaData() {
        return metaData;
    }

    public Compra metaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Compra proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public TipoDeDocumentoDeCompra getTipoDeDocumentoDeCompra() {
        return tipoDeDocumentoDeCompra;
    }

    public Compra tipoDeDocumentoDeCompra(TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra) {
        this.tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompra;
        return this;
    }

    public void setTipoDeDocumentoDeCompra(TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra) {
        this.tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompra;
    }

    public EstatusDeCompra getEstatusDeCompra() {
        return estatusDeCompra;
    }

    public Compra estatusDeCompra(EstatusDeCompra estatusDeCompra) {
        this.estatusDeCompra = estatusDeCompra;
        return this;
    }

    public void setEstatusDeCompra(EstatusDeCompra estatusDeCompra) {
        this.estatusDeCompra = estatusDeCompra;
    }

    public Caja getCaja() {
        return caja;
    }

    public Compra caja(Caja caja) {
        this.caja = caja;
        return this;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Compra productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Compra addProductos(Producto producto) {
        this.productos.add(producto);
        return this;
    }

    public Compra removeProductos(Producto producto) {
        this.productos.remove(producto);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
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
        Compra compra = (Compra) o;
        if (compra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", guiaRemision='" + getGuiaRemision() + "'" +
            ", numeroDeDocumento='" + getNumeroDeDocumento() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", montoTotal=" + getMontoTotal() +
            ", correlativo='" + getCorrelativo() + "'" +
            ", tipoDePagoDeCompra='" + getTipoDePagoDeCompra() + "'" +
            ", tipoDeTransaccion='" + getTipoDeTransaccion() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", metaData='" + getMetaData() + "'" +
            "}";
    }
}
