package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.OperationType;

/**
 * A Operation.
 */
@Entity
@Table(name = "operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "operation")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "init_date")
    private LocalDate initDate;

    @Column(name = "gloss")
    private String gloss;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Box box;

    @ManyToOne
    @JsonIgnoreProperties("")
    private PaymentType paymentType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ConceptOperationInput conceptOperationInput;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ConceptOperationOutput conceptOperationOutput;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public Operation initDate(LocalDate initDate) {
        this.initDate = initDate;
        return this;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public String getGloss() {
        return gloss;
    }

    public Operation gloss(String gloss) {
        this.gloss = gloss;
        return this;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public Double getAmount() {
        return amount;
    }

    public Operation amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Operation operationType(OperationType operationType) {
        this.operationType = operationType;
        return this;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Box getBox() {
        return box;
    }

    public Operation box(Box box) {
        this.box = box;
        return this;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Operation paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ConceptOperationInput getConceptOperationInput() {
        return conceptOperationInput;
    }

    public Operation conceptOperationInput(ConceptOperationInput conceptOperationInput) {
        this.conceptOperationInput = conceptOperationInput;
        return this;
    }

    public void setConceptOperationInput(ConceptOperationInput conceptOperationInput) {
        this.conceptOperationInput = conceptOperationInput;
    }

    public ConceptOperationOutput getConceptOperationOutput() {
        return conceptOperationOutput;
    }

    public Operation conceptOperationOutput(ConceptOperationOutput conceptOperationOutput) {
        this.conceptOperationOutput = conceptOperationOutput;
        return this;
    }

    public void setConceptOperationOutput(ConceptOperationOutput conceptOperationOutput) {
        this.conceptOperationOutput = conceptOperationOutput;
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
        Operation operation = (Operation) o;
        if (operation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", initDate='" + getInitDate() + "'" +
            ", gloss='" + getGloss() + "'" +
            ", amount=" + getAmount() +
            ", operationType='" + getOperationType() + "'" +
            "}";
    }
}
