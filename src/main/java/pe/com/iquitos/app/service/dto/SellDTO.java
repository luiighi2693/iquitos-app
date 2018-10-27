package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.SellStatus;

/**
 * A DTO for the Sell entity.
 */
public class SellDTO implements Serializable {

    private Long id;

    private String code;

    private Double subTotalAmount;

    private Double taxAmount;

    private Double totalAmount;

    private LocalDate date;

    private SellStatus status;

    private String gloss;

    private String metaData;

    private Long clientId;

    private Long employeeId;

    private Long boxId;

    private Long documentTypeSellId;

    private Long paymentTypeId;

    private Long productsDeliveredStatusId;

    private Set<ProductDTO> products = new HashSet<>();

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

    public Double getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(Double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SellStatus getStatus() {
        return status;
    }

    public void setStatus(SellStatus status) {
        this.status = status;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
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

    public Long getProductsDeliveredStatusId() {
        return productsDeliveredStatusId;
    }

    public void setProductsDeliveredStatusId(Long productsDeliveredStatusId) {
        this.productsDeliveredStatusId = productsDeliveredStatusId;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SellDTO sellDTO = (SellDTO) o;
        if (sellDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sellDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SellDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", subTotalAmount=" + getSubTotalAmount() +
            ", taxAmount=" + getTaxAmount() +
            ", totalAmount=" + getTotalAmount() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", gloss='" + getGloss() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", client=" + getClientId() +
            ", employee=" + getEmployeeId() +
            ", box=" + getBoxId() +
            ", documentTypeSell=" + getDocumentTypeSellId() +
            ", paymentType=" + getPaymentTypeId() +
            ", productsDeliveredStatus=" + getProductsDeliveredStatusId() +
            "}";
    }
}
