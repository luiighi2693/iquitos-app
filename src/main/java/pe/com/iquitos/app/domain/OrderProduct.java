package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.OrderStatus;

/**
 * A OrderProduct.
 */
@Entity
@Table(name = "order_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderproduct")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "guide")
    private String guide;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "meta_data")
    private String metaData;

    @OneToOne    @JoinColumn(unique = true)
    private Provider provider;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "order_product_products",
               joinColumns = @JoinColumn(name = "order_products_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"))
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public OrderProduct note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGuide() {
        return guide;
    }

    public OrderProduct guide(String guide) {
        this.guide = guide;
        return this;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderProduct orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMetaData() {
        return metaData;
    }

    public OrderProduct metaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Provider getProvider() {
        return provider;
    }

    public OrderProduct provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public OrderProduct products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public OrderProduct addProducts(Product product) {
        this.products.add(product);
        return this;
    }

    public OrderProduct removeProducts(Product product) {
        this.products.remove(product);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
        OrderProduct orderProduct = (OrderProduct) o;
        if (orderProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", guide='" + getGuide() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", metaData='" + getMetaData() + "'" +
            "}";
    }
}
