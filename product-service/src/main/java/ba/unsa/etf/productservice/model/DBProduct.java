package ba.unsa.etf.productservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(schema = "PRODUCTS", name = "PRODUCTS")
public class DBProduct {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private BigDecimal price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBProduct dbProduct = (DBProduct) o;
        return Objects.equals(id, dbProduct.id) &&
                Objects.equals(name, dbProduct.name) &&
                Objects.equals(category, dbProduct.category) &&
                Objects.equals(price, dbProduct.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, category, price);
    }
}
