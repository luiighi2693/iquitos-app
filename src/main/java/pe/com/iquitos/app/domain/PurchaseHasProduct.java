package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PurchaseHasProduct.
 */
@Entity
@Table(name = "purchase_has_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "purchasehasproduct")
public class PurchaseHasProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "date_purchase")
    private LocalDate datePurchase;

    @OneToOne    @JoinColumn(unique = true)
    private Purchase purchase;

    @OneToMany(mappedBy = "purchaseHasProduct")
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

    public PurchaseHasProduct quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTax() {
        return tax;
    }

    public PurchaseHasProduct tax(Double tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public LocalDate getDatePurchase() {
        return datePurchase;
    }

    public PurchaseHasProduct datePurchase(LocalDate datePurchase) {
        this.datePurchase = datePurchase;
        return this;
    }

    public void setDatePurchase(LocalDate datePurchase) {
        this.datePurchase = datePurchase;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public PurchaseHasProduct purchase(Purchase purchase) {
        this.purchase = purchase;
        return this;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public PurchaseHasProduct products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public PurchaseHasProduct addProduct(Product product) {
        this.products.add(product);
        product.setPurchaseHasProduct(this);
        return this;
    }

    public PurchaseHasProduct removeProduct(Product product) {
        this.products.remove(product);
        product.setPurchaseHasProduct(null);
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
        PurchaseHasProduct purchaseHasProduct = (PurchaseHasProduct) o;
        if (purchaseHasProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseHasProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseHasProduct{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", tax=" + getTax() +
            ", datePurchase='" + getDatePurchase() + "'" +
            "}";
    }
}
