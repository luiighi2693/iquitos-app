package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Credit.
 */
@Entity
@Table(name = "credit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "credit")
public class Credit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "emission_date")
    private LocalDate emissionDate;

    @Column(name = "payment_mode")
    private Double paymentMode;

    @Column(name = "credit_number")
    private Integer creditNumber;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "limit_date")
    private LocalDate limitDate;

    @Column(name = "credit_note")
    private String creditNote;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Sell sell;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Purchase purchase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Credit amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public Credit emissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
        return this;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public Double getPaymentMode() {
        return paymentMode;
    }

    public Credit paymentMode(Double paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(Double paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getCreditNumber() {
        return creditNumber;
    }

    public Credit creditNumber(Integer creditNumber) {
        this.creditNumber = creditNumber;
        return this;
    }

    public void setCreditNumber(Integer creditNumber) {
        this.creditNumber = creditNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Credit totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public Credit limitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
        return this;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public String getCreditNote() {
        return creditNote;
    }

    public Credit creditNote(String creditNote) {
        this.creditNote = creditNote;
        return this;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public Sell getSell() {
        return sell;
    }

    public Credit sell(Sell sell) {
        this.sell = sell;
        return this;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public Credit purchase(Purchase purchase) {
        this.purchase = purchase;
        return this;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
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
        Credit credit = (Credit) o;
        if (credit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), credit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Credit{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", paymentMode=" + getPaymentMode() +
            ", creditNumber=" + getCreditNumber() +
            ", totalAmount=" + getTotalAmount() +
            ", limitDate='" + getLimitDate() + "'" +
            ", creditNote='" + getCreditNote() + "'" +
            "}";
    }
}
