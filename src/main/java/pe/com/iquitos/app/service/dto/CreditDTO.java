package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Credit entity.
 */
public class CreditDTO implements Serializable {

    private Long id;

    private Double amount;

    private LocalDate emissionDate;

    private Double paymentMode;

    private Integer creditNumber;

    private Double totalAmount;

    private LocalDate limitDate;

    private String creditNote;

    private Long sellId;

    private Long purchaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public Double getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Double paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(Integer creditNumber) {
        this.creditNumber = creditNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public String getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public Long getSellId() {
        return sellId;
    }

    public void setSellId(Long sellId) {
        this.sellId = sellId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditDTO creditDTO = (CreditDTO) o;
        if (creditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", paymentMode=" + getPaymentMode() +
            ", creditNumber=" + getCreditNumber() +
            ", totalAmount=" + getTotalAmount() +
            ", limitDate='" + getLimitDate() + "'" +
            ", creditNote='" + getCreditNote() + "'" +
            ", sell=" + getSellId() +
            ", purchase=" + getPurchaseId() +
            "}";
    }
}
