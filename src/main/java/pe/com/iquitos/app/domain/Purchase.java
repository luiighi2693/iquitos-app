package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.PurchaseLocation;

import pe.com.iquitos.app.domain.enumeration.PaymentPurchaseType;

/**
 * A Purchase.
 */
@Entity
@Table(name = "purchase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "purchase")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "remision_guide")
    private String remisionGuide;

    @Column(name = "document_number")
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private PurchaseLocation location;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "correlative")
    private String correlative;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_purchase_type")
    private PaymentPurchaseType paymentPurchaseType;

    @OneToOne    @JoinColumn(unique = true)
    private Provider provider;

    @OneToOne    @JoinColumn(unique = true)
    private DocumentTypePurchase documentTypePurchase;

    @OneToOne    @JoinColumn(unique = true)
    private PurchaseStatus purchaseStatus;

    @OneToOne    @JoinColumn(unique = true)
    private Box box;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Purchase date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRemisionGuide() {
        return remisionGuide;
    }

    public Purchase remisionGuide(String remisionGuide) {
        this.remisionGuide = remisionGuide;
        return this;
    }

    public void setRemisionGuide(String remisionGuide) {
        this.remisionGuide = remisionGuide;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public Purchase documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public PurchaseLocation getLocation() {
        return location;
    }

    public Purchase location(PurchaseLocation location) {
        this.location = location;
        return this;
    }

    public void setLocation(PurchaseLocation location) {
        this.location = location;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Purchase totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCorrelative() {
        return correlative;
    }

    public Purchase correlative(String correlative) {
        this.correlative = correlative;
        return this;
    }

    public void setCorrelative(String correlative) {
        this.correlative = correlative;
    }

    public PaymentPurchaseType getPaymentPurchaseType() {
        return paymentPurchaseType;
    }

    public Purchase paymentPurchaseType(PaymentPurchaseType paymentPurchaseType) {
        this.paymentPurchaseType = paymentPurchaseType;
        return this;
    }

    public void setPaymentPurchaseType(PaymentPurchaseType paymentPurchaseType) {
        this.paymentPurchaseType = paymentPurchaseType;
    }

    public Provider getProvider() {
        return provider;
    }

    public Purchase provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public DocumentTypePurchase getDocumentTypePurchase() {
        return documentTypePurchase;
    }

    public Purchase documentTypePurchase(DocumentTypePurchase documentTypePurchase) {
        this.documentTypePurchase = documentTypePurchase;
        return this;
    }

    public void setDocumentTypePurchase(DocumentTypePurchase documentTypePurchase) {
        this.documentTypePurchase = documentTypePurchase;
    }

    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }

    public Purchase purchaseStatus(PurchaseStatus purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
        return this;
    }

    public void setPurchaseStatus(PurchaseStatus purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Box getBox() {
        return box;
    }

    public Purchase box(Box box) {
        this.box = box;
        return this;
    }

    public void setBox(Box box) {
        this.box = box;
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
        Purchase purchase = (Purchase) o;
        if (purchase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Purchase{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", remisionGuide='" + getRemisionGuide() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", correlative='" + getCorrelative() + "'" +
            ", paymentPurchaseType='" + getPaymentPurchaseType() + "'" +
            "}";
    }
}
