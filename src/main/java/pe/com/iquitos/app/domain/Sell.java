package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import pe.com.iquitos.app.domain.enumeration.SellStatus;

/**
 * A Sell.
 */
@Entity
@Table(name = "sell")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sell")
public class Sell implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "sub_total_amount")
    private Double subTotalAmount;

    @Column(name = "tax_amount")
    private Double taxAmount;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SellStatus status;

    @Column(name = "gloss")
    private String gloss;

    @Column(name = "meta_data")
    private String metaData;

    @OneToOne    @JoinColumn(unique = true)
    private Client client;

    @OneToOne    @JoinColumn(unique = true)
    private Employee employee;

    @OneToOne    @JoinColumn(unique = true)
    private Box box;

    @OneToMany(mappedBy = "sell")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Amortization> amortizations = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private DocumentTypeSell documentTypeSell;

    @ManyToOne
    @JsonIgnoreProperties("")
    private PaymentType paymentType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ProductsDeliveredStatus productsDeliveredStatus;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sell_products",
               joinColumns = @JoinColumn(name = "sells_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "products_id", referencedColumnName = "id"))
    private Set<Product> products = new HashSet<>();

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

    public Sell code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getSubTotalAmount() {
        return subTotalAmount;
    }

    public Sell subTotalAmount(Double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
        return this;
    }

    public void setSubTotalAmount(Double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public Sell taxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Sell totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Sell date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SellStatus getStatus() {
        return status;
    }

    public Sell status(SellStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SellStatus status) {
        this.status = status;
    }

    public String getGloss() {
        return gloss;
    }

    public Sell gloss(String gloss) {
        this.gloss = gloss;
        return this;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public String getMetaData() {
        return metaData;
    }

    public Sell metaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Client getClient() {
        return client;
    }

    public Sell client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Sell employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Box getBox() {
        return box;
    }

    public Sell box(Box box) {
        this.box = box;
        return this;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Set<Amortization> getAmortizations() {
        return amortizations;
    }

    public Sell amortizations(Set<Amortization> amortizations) {
        this.amortizations = amortizations;
        return this;
    }

    public Sell addAmortization(Amortization amortization) {
        this.amortizations.add(amortization);
        amortization.setSell(this);
        return this;
    }

    public Sell removeAmortization(Amortization amortization) {
        this.amortizations.remove(amortization);
        amortization.setSell(null);
        return this;
    }

    public void setAmortizations(Set<Amortization> amortizations) {
        this.amortizations = amortizations;
    }

    public DocumentTypeSell getDocumentTypeSell() {
        return documentTypeSell;
    }

    public Sell documentTypeSell(DocumentTypeSell documentTypeSell) {
        this.documentTypeSell = documentTypeSell;
        return this;
    }

    public void setDocumentTypeSell(DocumentTypeSell documentTypeSell) {
        this.documentTypeSell = documentTypeSell;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Sell paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ProductsDeliveredStatus getProductsDeliveredStatus() {
        return productsDeliveredStatus;
    }

    public Sell productsDeliveredStatus(ProductsDeliveredStatus productsDeliveredStatus) {
        this.productsDeliveredStatus = productsDeliveredStatus;
        return this;
    }

    public void setProductsDeliveredStatus(ProductsDeliveredStatus productsDeliveredStatus) {
        this.productsDeliveredStatus = productsDeliveredStatus;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Sell products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Sell addProducts(Product product) {
        this.products.add(product);
        return this;
    }

    public Sell removeProducts(Product product) {
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
        Sell sell = (Sell) o;
        if (sell.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sell.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sell{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", subTotalAmount=" + getSubTotalAmount() +
            ", taxAmount=" + getTaxAmount() +
            ", totalAmount=" + getTotalAmount() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", gloss='" + getGloss() + "'" +
            ", metaData='" + getMetaData() + "'" +
            "}";
    }
}
