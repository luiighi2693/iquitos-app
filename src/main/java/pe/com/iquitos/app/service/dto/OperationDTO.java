package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.OperationType;

/**
 * A DTO for the Operation entity.
 */
public class OperationDTO implements Serializable {

    private Long id;

    private LocalDate initDate;

    private String gloss;

    private Double amount;

    private OperationType operationType;

    private Long boxId;

    private Long paymentTypeId;

    private Long conceptOperationInputId;

    private Long conceptOperationOutputId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Long getConceptOperationInputId() {
        return conceptOperationInputId;
    }

    public void setConceptOperationInputId(Long conceptOperationInputId) {
        this.conceptOperationInputId = conceptOperationInputId;
    }

    public Long getConceptOperationOutputId() {
        return conceptOperationOutputId;
    }

    public void setConceptOperationOutputId(Long conceptOperationOutputId) {
        this.conceptOperationOutputId = conceptOperationOutputId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperationDTO operationDTO = (OperationDTO) o;
        if (operationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
            "id=" + getId() +
            ", initDate='" + getInitDate() + "'" +
            ", gloss='" + getGloss() + "'" +
            ", amount=" + getAmount() +
            ", operationType='" + getOperationType() + "'" +
            ", box=" + getBoxId() +
            ", paymentType=" + getPaymentTypeId() +
            ", conceptOperationInput=" + getConceptOperationInputId() +
            ", conceptOperationOutput=" + getConceptOperationOutputId() +
            "}";
    }
}
