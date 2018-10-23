package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.PurchaseLocation;
import pe.com.iquitos.app.domain.enumeration.PaymentPurchaseType;

/**
 * A DTO for the Purchase entity.
 */
public class PurchaseDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String remisionGuide;

    private String documentNumber;

    private PurchaseLocation location;

    private Double totalAmount;

    private String correlative;

    private PaymentPurchaseType paymentPurchaseType;

    private Long providerId;

    private Long documentTypePurchaseId;

    private Long purchaseStatusId;

    private Long boxId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRemisionGuide() {
        return remisionGuide;
    }

    public void setRemisionGuide(String remisionGuide) {
        this.remisionGuide = remisionGuide;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public PurchaseLocation getLocation() {
        return location;
    }

    public void setLocation(PurchaseLocation location) {
        this.location = location;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCorrelative() {
        return correlative;
    }

    public void setCorrelative(String correlative) {
        this.correlative = correlative;
    }

    public PaymentPurchaseType getPaymentPurchaseType() {
        return paymentPurchaseType;
    }

    public void setPaymentPurchaseType(PaymentPurchaseType paymentPurchaseType) {
        this.paymentPurchaseType = paymentPurchaseType;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getDocumentTypePurchaseId() {
        return documentTypePurchaseId;
    }

    public void setDocumentTypePurchaseId(Long documentTypePurchaseId) {
        this.documentTypePurchaseId = documentTypePurchaseId;
    }

    public Long getPurchaseStatusId() {
        return purchaseStatusId;
    }

    public void setPurchaseStatusId(Long purchaseStatusId) {
        this.purchaseStatusId = purchaseStatusId;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseDTO purchaseDTO = (PurchaseDTO) o;
        if (purchaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", remisionGuide='" + getRemisionGuide() + "'" +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", correlative='" + getCorrelative() + "'" +
            ", paymentPurchaseType='" + getPaymentPurchaseType() + "'" +
            ", provider=" + getProviderId() +
            ", documentTypePurchase=" + getDocumentTypePurchaseId() +
            ", purchaseStatus=" + getPurchaseStatusId() +
            ", box=" + getBoxId() +
            "}";
    }
}
