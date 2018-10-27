package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import pe.com.iquitos.app.domain.enumeration.ProductType;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String code;

    private String description;

    private Integer dni;

    private LocalDate expirationDate;

    private Boolean isFavorite;

    private Boolean visibleToSell;

    @Lob
    private byte[] image;
    private String imageContentType;

    private Integer stock;

    private Integer stockLimitNotification;

    private ProductType productType;

    private Long unitMeasurementId;

    private Long categoryId;

    private Set<VariantDTO> variants = new HashSet<>();

    private Long sellHasProductId;

    private Long purchaseHasProductId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Boolean isVisibleToSell() {
        return visibleToSell;
    }

    public void setVisibleToSell(Boolean visibleToSell) {
        this.visibleToSell = visibleToSell;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockLimitNotification() {
        return stockLimitNotification;
    }

    public void setStockLimitNotification(Integer stockLimitNotification) {
        this.stockLimitNotification = stockLimitNotification;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Long getUnitMeasurementId() {
        return unitMeasurementId;
    }

    public void setUnitMeasurementId(Long unitMeasurementId) {
        this.unitMeasurementId = unitMeasurementId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Set<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(Set<VariantDTO> variants) {
        this.variants = variants;
    }

    public Long getSellHasProductId() {
        return sellHasProductId;
    }

    public void setSellHasProductId(Long sellHasProductId) {
        this.sellHasProductId = sellHasProductId;
    }

    public Long getPurchaseHasProductId() {
        return purchaseHasProductId;
    }

    public void setPurchaseHasProductId(Long purchaseHasProductId) {
        this.purchaseHasProductId = purchaseHasProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", dni=" + getDni() +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", isFavorite='" + isIsFavorite() + "'" +
            ", visibleToSell='" + isVisibleToSell() + "'" +
            ", image='" + getImage() + "'" +
            ", stock=" + getStock() +
            ", stockLimitNotification=" + getStockLimitNotification() +
            ", productType='" + getProductType() + "'" +
            ", unitMeasurement=" + getUnitMeasurementId() +
            ", category=" + getCategoryId() +
            ", sellHasProduct=" + getSellHasProductId() +
            ", purchaseHasProduct=" + getPurchaseHasProductId() +
            "}";
    }
}
