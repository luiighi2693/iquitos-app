package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Amortization entity.
 */
public class AmortizationDTO implements Serializable {

    private Long id;

    private Double amountToPay;

    private Double amountPayed;

    private LocalDate emissionDate;

    private String documentCode;

    private String gloss;

    private Long documentTypeSellId;

    private Long paymentTypeId;

    private Long sellId;

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

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public Long getDocumentTypeSellId() {
        return documentTypeSellId;
    }

    public void setDocumentTypeSellId(Long documentTypeSellId) {
        this.documentTypeSellId = documentTypeSellId;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Long getSellId() {
        return sellId;
    }

    public void setSellId(Long sellId) {
        this.sellId = sellId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmortizationDTO amortizationDTO = (AmortizationDTO) o;
        if (amortizationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmortizationDTO{" +
            "id=" + getId() +
            ", amountToPay=" + getAmountToPay() +
            ", amountPayed=" + getAmountPayed() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", documentCode='" + getDocumentCode() + "'" +
            ", gloss='" + getGloss() + "'" +
            ", documentTypeSell=" + getDocumentTypeSellId() +
            ", paymentType=" + getPaymentTypeId() +
            ", sell=" + getSellId() +
            "}";
    }
}
