package pe.com.iquitos.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TipoDeDocumento entity.
 */
public class TipoDeDocumentoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nombre;

    @NotNull
    @Size(max = 150)
    private String cantidadDigitos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidadDigitos() {
        return cantidadDigitos;
    }

    public void setCantidadDigitos(String cantidadDigitos) {
        this.cantidadDigitos = cantidadDigitos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoDeDocumentoDTO tipoDeDocumentoDTO = (TipoDeDocumentoDTO) o;
        if (tipoDeDocumentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDeDocumentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDeDocumentoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", cantidadDigitos='" + getCantidadDigitos() + "'" +
            "}";
    }
}
