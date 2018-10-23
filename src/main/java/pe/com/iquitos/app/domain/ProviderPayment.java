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
 * A ProviderPayment.
 */
@Entity
@Table(name = "provider_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "providerpayment")
public class ProviderPayment implements Serializable {

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

    @Column(name = "glosa")
    private String glosa;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private DocumentTypePurchase documentTypePurchase;

    @ManyToOne
    @JsonIgnoreProperties("")
    private PaymentType paymentType;

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

    public ProviderPayment amountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
        return this;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Double getAmountPayed() {
        return amountPayed;
    }

    public ProviderPayment amountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
        return this;
    }

    public void setAmountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
    }

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public ProviderPayment emissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
        return this;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public ProviderPayment documentCode(String documentCode) {
        this.documentCode = documentCode;
        return this;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getGlosa() {
        return glosa;
    }

    public ProviderPayment glosa(String glosa) {
        this.glosa = glosa;
        return this;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public byte[] getImage() {
        return image;
    }

    public ProviderPayment image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public ProviderPayment imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public DocumentTypePurchase getDocumentTypePurchase() {
        return documentTypePurchase;
    }

    public ProviderPayment documentTypePurchase(DocumentTypePurchase documentTypePurchase) {
        this.documentTypePurchase = documentTypePurchase;
        return this;
    }

    public void setDocumentTypePurchase(DocumentTypePurchase documentTypePurchase) {
        this.documentTypePurchase = documentTypePurchase;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public ProviderPayment paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
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
        ProviderPayment providerPayment = (ProviderPayment) o;
        if (providerPayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerPayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderPayment{" +
            "id=" + getId() +
            ", amountToPay=" + getAmountToPay() +
            ", amountPayed=" + getAmountPayed() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", documentCode='" + getDocumentCode() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
