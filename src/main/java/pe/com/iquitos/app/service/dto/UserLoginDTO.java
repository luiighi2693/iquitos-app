package pe.com.iquitos.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserLogin entity.
 */
public class UserLoginDTO implements Serializable {

    private Long id;

    private Integer dni;

    private Integer pin;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserLoginDTO userLoginDTO = (UserLoginDTO) o;
        if (userLoginDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userLoginDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", pin=" + getPin() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
