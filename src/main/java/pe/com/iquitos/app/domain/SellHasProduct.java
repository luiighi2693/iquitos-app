package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SellHasProduct.
 */
@Entity
@Table(name = "sell_has_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sellhasproduct")
public class SellHasProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "discount")
    private Double discount;

    @OneToOne    @JoinColumn(unique = true)
    private Sell sell;

    @OneToOne    @JoinColumn(unique = true)
    private Variant variant;

    @OneToMany(mappedBy = "sellHasProduct")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public SellHasProduct quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public SellHasProduct discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Sell getSell() {
        return sell;
    }

    public SellHasProduct sell(Sell sell) {
        this.sell = sell;
        return this;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public Variant getVariant() {
        return variant;
    }

    public SellHasProduct variant(Variant variant) {
        this.variant = variant;
        return this;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public SellHasProduct products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public SellHasProduct addProduct(Product product) {
        this.products.add(product);
        product.setSellHasProduct(this);
        return this;
    }

    public SellHasProduct removeProduct(Product product) {
        this.products.remove(product);
        product.setSellHasProduct(null);
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
        SellHasProduct sellHasProduct = (SellHasProduct) o;
        if (sellHasProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sellHasProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SellHasProduct{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", discount=" + getDiscount() +
            "}";
    }
}
