package pe.com.iquitos.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SellHasProduct entity.
 */
public class SellHasProductDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Double discount;

    private Long sellId;

    private Long variantId;

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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getSellId() {
        return sellId;
    }

    public void setSellId(Long sellId) {
        this.sellId = sellId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SellHasProductDTO sellHasProductDTO = (SellHasProductDTO) o;
        if (sellHasProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sellHasProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SellHasProductDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", discount=" + getDiscount() +
            ", sell=" + getSellId() +
            ", variant=" + getVariantId() +
            "}";
    }
}
