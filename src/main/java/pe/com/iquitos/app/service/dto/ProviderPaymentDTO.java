package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ProviderPayment entity.
 */
public class ProviderPaymentDTO implements Serializable {

    private Long id;

    private Double amountToPay;

    private Double amountPayed;

    private LocalDate emissionDate;

    private String documentCode;

    private String glosa;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Long documentTypePurchaseId;

    private Long paymentTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Double getAmountPayed() {
        return amountPayed;
    }

    public void setAmountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
    }

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getDocumentTypePurchaseId() {
        return documentTypePurchaseId;
    }

    public void setDocumentTypePurchaseId(Long documentTypePurchaseId) {
        this.documentTypePurchaseId = documentTypePurchaseId;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProviderPaymentDTO providerPaymentDTO = (ProviderPaymentDTO) o;
        if (providerPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderPaymentDTO{" +
            "id=" + getId() +
            ", amountToPay=" + getAmountToPay() +
            ", amountPayed=" + getAmountPayed() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", documentCode='" + getDocumentCode() + "'" +
            ", glosa='" + getGlosa() + "'" +
            ", image='" + getImage() + "'" +
            ", documentTypePurchase=" + getDocumentTypePurchaseId() +
            ", paymentType=" + getPaymentTypeId() +
            "}";
    }
}
