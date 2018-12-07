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

import pe.com.iquitos.app.domain.enumeration.ProductType;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "codigo", length = 150, nullable = false)
    private String codigo;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Size(max = 150)
    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

    @Column(name = "es_favorito")
    private Boolean esFavorito;

    @Column(name = "visible_para_la_venta")
    private Boolean visibleParaLaVenta;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "notificacion_de_limite_de_stock")
    private Integer notificacionDeLimiteDeStock;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_producto")
    private ProductType tipoDeProducto;

    @ManyToOne
    @JsonIgnoreProperties("")
    private UnidadDeMedida unidadDeMedida;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Categoria categoria;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "producto_variantes",
               joinColumns = @JoinColumn(name = "productos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "variantes_id", referencedColumnName = "id"))
    private Set<Variante> variantes = new HashSet<>();

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

    public Producto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public Producto fechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
        return this;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Boolean isEsFavorito() {
        return esFavorito;
    }

    public Producto esFavorito(Boolean esFavorito) {
        this.esFavorito = esFavorito;
        return this;
    }

    public void setEsFavorito(Boolean esFavorito) {
        this.esFavorito = esFavorito;
    }

    public Boolean isVisibleParaLaVenta() {
        return visibleParaLaVenta;
    }

    public Producto visibleParaLaVenta(Boolean visibleParaLaVenta) {
        this.visibleParaLaVenta = visibleParaLaVenta;
        return this;
    }

    public void setVisibleParaLaVenta(Boolean visibleParaLaVenta) {
        this.visibleParaLaVenta = visibleParaLaVenta;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Producto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Producto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Integer getStock() {
        return stock;
    }

    public Producto stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getNotificacionDeLimiteDeStock() {
        return notificacionDeLimiteDeStock;
    }

    public Producto notificacionDeLimiteDeStock(Integer notificacionDeLimiteDeStock) {
        this.notificacionDeLimiteDeStock = notificacionDeLimiteDeStock;
        return this;
    }

    public void setNotificacionDeLimiteDeStock(Integer notificacionDeLimiteDeStock) {
        this.notificacionDeLimiteDeStock = notificacionDeLimiteDeStock;
    }

    public ProductType getTipoDeProducto() {
        return tipoDeProducto;
    }

    public Producto tipoDeProducto(ProductType tipoDeProducto) {
        this.tipoDeProducto = tipoDeProducto;
        return this;
    }

    public void setTipoDeProducto(ProductType tipoDeProducto) {
        this.tipoDeProducto = tipoDeProducto;
    }

    public UnidadDeMedida getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public Producto unidadDeMedida(UnidadDeMedida unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
        return this;
    }

    public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Producto categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Variante> getVariantes() {
        return variantes;
    }

    public Producto variantes(Set<Variante> variantes) {
        this.variantes = variantes;
        return this;
    }

    public Producto addVariantes(Variante variante) {
        this.variantes.add(variante);
        return this;
    }

    public Producto removeVariantes(Variante variante) {
        this.variantes.remove(variante);
        return this;
    }

    public void setVariantes(Set<Variante> variantes) {
        this.variantes = variantes;
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
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaExpiracion='" + getFechaExpiracion() + "'" +
            ", esFavorito='" + isEsFavorito() + "'" +
            ", visibleParaLaVenta='" + isVisibleParaLaVenta() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", stock=" + getStock() +
            ", notificacionDeLimiteDeStock=" + getNotificacionDeLimiteDeStock() +
            ", tipoDeProducto='" + getTipoDeProducto() + "'" +
            "}";
    }
}
