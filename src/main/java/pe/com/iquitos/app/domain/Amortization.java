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
 * A Amortization.
 */
@Entity
@Table(name = "amortization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "amortization")
public class Amortization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_to_pay")
    private Double amountToPay;

    @Column(name = "amount_payed")
    private Double amountPayed;

    @Column(name = "emission_date")
    private LocalDate emissionDate;

    @Column(name = "document_code")
    private String documentCode;

    @Column(name = "gloss")
    private String gloss;

    @ManyToOne
    @JsonIgnoreProperties("")
    private DocumentTypeSell documentTypeSell;

    @ManyToOne
    @JsonIgnoreProperties("")
    private PaymentType paymentType;

    @ManyToOne
    @JsonIgnoreProperties("amortizations")
    private Sell sell;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public Amortization amountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
        return this;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Double getAmountPayed() {
        return amountPayed;
    }

    public Amortization amountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
        return this;
    }

    public void setAmountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
    }

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public Amortization emissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
        return this;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public Amortization documentCode(String documentCode) {
        this.documentCode = documentCode;
        return this;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getGloss() {
        return gloss;
    }

    public Amortization gloss(String gloss) {
        this.gloss = gloss;
        return this;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public DocumentTypeSell getDocumentTypeSell() {
        return documentTypeSell;
    }

    public Amortization documentTypeSell(DocumentTypeSell documentTypeSell) {
        this.documentTypeSell = documentTypeSell;
        return this;
    }

    public void setDocumentTypeSell(DocumentTypeSell documentTypeSell) {
        this.documentTypeSell = documentTypeSell;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Amortization paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Sell getSell() {
        return sell;
    }

    public Amortization sell(Sell sell) {
        this.sell = sell;
        return this;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
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
        Amortization amortization = (Amortization) o;
        if (amortization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Amortization{" +
            "id=" + getId() +
            ", amountToPay=" + getAmountToPay() +
            ", amountPayed=" + getAmountPayed() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", documentCode='" + getDocumentCode() + "'" +
            ", gloss='" + getGloss() + "'" +
            "}";
    }
}
