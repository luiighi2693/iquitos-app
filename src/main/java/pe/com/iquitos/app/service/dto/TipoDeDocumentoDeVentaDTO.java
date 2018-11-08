package pe.com.iquitos.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TipoDeDocumentoDeVenta entity.
 */
public class TipoDeDocumentoDeVentaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nombre;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO = (TipoDeDocumentoDeVentaDTO) o;
        if (tipoDeDocumentoDeVentaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoDeDocumentoDeVentaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoDeDocumentoDeVentaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
