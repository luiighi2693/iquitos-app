package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.ProviderStatus;

/**
 * A DTO for the ProviderAccount entity.
 */
public class ProviderAccountDTO implements Serializable {

    private Long id;

    private String code;

    private ProviderStatus status;

    private String bank;

    private String accountName;

    private Integer accountNumber;

    private LocalDate initDate;

    private Long providerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProviderStatus getStatus() {
        return status;
    }

    public void setStatus(ProviderStatus status) {
        this.status = status;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProviderAccountDTO providerAccountDTO = (ProviderAccountDTO) o;
        if (providerAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderAccountDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", bank='" + getBank() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", accountNumber=" + getAccountNumber() +
            ", initDate='" + getInitDate() + "'" +
            ", provider=" + getProviderId() +
            "}";
    }
}
