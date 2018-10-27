package pe.com.iquitos.app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.OrderStatus;

/**
 * A DTO for the OrderProduct entity.
 */
public class OrderProductDTO implements Serializable {

    private Long id;

    private String note;

    private String guide;

    private OrderStatus orderStatus;

    private String metaData;

    private Long providerId;

    private Set<ProductDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
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

        OrderProductDTO orderProductDTO = (OrderProductDTO) o;
        if (orderProductDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderProductDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderProductDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", guide='" + getGuide() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", provider=" + getProviderId() +
            "}";
    }
}
