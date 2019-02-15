package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoDeDocumentoDeVenta.
 */
@Entity
@Table(name = "tipo_de_documento_de_venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tipodedocumentodeventa")
public class TipoDeDocumentoDeVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Size(max = 150)
    @Column(name = "serie", length = 150)
    private String serie;

    @Size(max = 150)
    @Column(name = "formato", length = 150)
    private String formato;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoDeDocumentoDeVenta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSerie() {
        return serie;
    }

    public TipoDeDocumentoDeVenta serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFormato() {
        return formato;
    }

    public TipoDeDocumentoDeVenta formato(String formato) {
        this.formato = formato;
        return this;
    }

    public void setFormato(String formato) {
        this.formato = formato;
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
        TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta = (TipoDeDocumentoDeVenta) o;
        if (tipoDeDocumentoDeVenta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDeDocumentoDeVenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDeDocumentoDeVenta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", serie='" + getSerie() + "'" +
            ", formato='" + getFormato() + "'" +
            "}";
    }
}
