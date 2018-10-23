package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PurchaseHasProduct entity.
 */
public class PurchaseHasProductDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Double tax;

    private LocalDate datePurchase;

    private Long purchaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public LocalDate getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(LocalDate datePurchase) {
        this.datePurchase = datePurchase;
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

        PurchaseHasProductDTO purchaseHasProductDTO = (PurchaseHasProductDTO) o;
        if (purchaseHasProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseHasProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseHasProductDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", tax=" + getTax() +
            ", datePurchase='" + getDatePurchase() + "'" +
            ", purchase=" + getPurchaseId() +
            "}";
    }
}
