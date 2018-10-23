package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Provider.
 */
@Entity
@Table(name = "provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "social_reason")
    private String socialReason;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "cellphone")
    private Integer cellphone;

    @OneToMany(mappedBy = "provider")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProviderAccount> providerAccounts = new HashSet<>();
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

    public Provider code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public Provider socialReason(String socialReason) {
        this.socialReason = socialReason;
        return this;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public String getAddress() {
        return address;
    }

    public Provider address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public Provider email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCellphone() {
        return cellphone;
    }

    public Provider cellphone(Integer cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public void setCellphone(Integer cellphone) {
        this.cellphone = cellphone;
    }

    public Set<ProviderAccount> getProviderAccounts() {
        return providerAccounts;
    }

    public Provider providerAccounts(Set<ProviderAccount> providerAccounts) {
        this.providerAccounts = providerAccounts;
        return this;
    }

    public Provider addProviderAccount(ProviderAccount providerAccount) {
        this.providerAccounts.add(providerAccount);
        providerAccount.setProvider(this);
        return this;
    }

    public Provider removeProviderAccount(ProviderAccount providerAccount) {
        this.providerAccounts.remove(providerAccount);
        providerAccount.setProvider(null);
        return this;
    }

    public void setProviderAccounts(Set<ProviderAccount> providerAccounts) {
        this.providerAccounts = providerAccounts;
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
        Provider provider = (Provider) o;
        if (provider.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provider.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", socialReason='" + getSocialReason() + "'" +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", cellphone=" + getCellphone() +
            "}";
    }
}
