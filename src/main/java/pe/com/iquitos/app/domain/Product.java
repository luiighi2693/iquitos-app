package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.ProductType;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "dni")
    private Integer dni;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "is_favorite")
    private Boolean isFavorite;

    @Column(name = "visible_to_sell")
    private Boolean visibleToSell;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "stock_limit_notification")
    private Integer stockLimitNotification;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private UnitMeasurement unitMeasurement;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Category category;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_variants",
               joinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "variants_id", referencedColumnName = "id"))
    private Set<Variant> variants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDni() {
        return dni;
    }

    public Product dni(Integer dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Product expirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean isIsFavorite() {
        return isFavorite;
    }

    public Product isFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
        return this;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Boolean isVisibleToSell() {
        return visibleToSell;
    }

    public Product visibleToSell(Boolean visibleToSell) {
        this.visibleToSell = visibleToSell;
        return this;
    }

    public void setVisibleToSell(Boolean visibleToSell) {
        this.visibleToSell = visibleToSell;
    }

    public byte[] getImage() {
        return image;
    }

    public Product image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Product imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Integer getStock() {
        return stock;
    }

    public Product stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockLimitNotification() {
        return stockLimitNotification;
    }

    public Product stockLimitNotification(Integer stockLimitNotification) {
        this.stockLimitNotification = stockLimitNotification;
        return this;
    }

    public void setStockLimitNotification(Integer stockLimitNotification) {
        this.stockLimitNotification = stockLimitNotification;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public UnitMeasurement getUnitMeasurement() {
        return unitMeasurement;
    }

    public Product unitMeasurement(UnitMeasurement unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
        return this;
    }

    public void setUnitMeasurement(UnitMeasurement unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Variant> getVariants() {
        return variants;
    }

    public Product variants(Set<Variant> variants) {
        this.variants = variants;
        return this;
    }

    public Product addVariants(Variant variant) {
        this.variants.add(variant);
        return this;
    }

    public Product removeVariants(Variant variant) {
        this.variants.remove(variant);
        return this;
    }

    public void setVariants(Set<Variant> variants) {
        this.variants = variants;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", dni=" + getDni() +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", isFavorite='" + isIsFavorite() + "'" +
            ", visibleToSell='" + isVisibleToSell() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", stock=" + getStock() +
            ", stockLimitNotification=" + getStockLimitNotification() +
            ", productType='" + getProductType() + "'" +
            "}";
    }
}
