package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.ProviderStatus;

/**
 * A ProviderAccount.
 */
@Entity
@Table(name = "provider_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "provideraccount")
public class ProviderAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProviderStatus status;

    @Column(name = "bank")
    private String bank;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_number")
    private Integer accountNumber;

    @Column(name = "init_date")
    private LocalDate initDate;

    @ManyToOne
    @JsonIgnoreProperties("providerAccounts")
    private Provider provider;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ProviderAccount code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProviderStatus getStatus() {
        return status;
    }

    public ProviderAccount status(ProviderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProviderStatus status) {
        this.status = status;
    }

    public String getBank() {
        return bank;
    }

    public ProviderAccount bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountName() {
        return accountName;
    }

    public ProviderAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public ProviderAccount accountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public ProviderAccount initDate(LocalDate initDate) {
        this.initDate = initDate;
        return this;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public Provider getProvider() {
        return provider;
    }

    public ProviderAccount provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
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
        ProviderAccount providerAccount = (ProviderAccount) o;
        if (providerAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderAccount{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", status='" + getStatus() + "'" +
            ", bank='" + getBank() + "'" +
            ", accountName='" + getAccountName() + "'" +
            ", accountNumber=" + getAccountNumber() +
            ", initDate='" + getInitDate() + "'" +
            "}";
    }
}
