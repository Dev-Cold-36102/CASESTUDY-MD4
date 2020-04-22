package codegym.model.Product;


import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String image;
    private String name;
    private double price;
    private String describetion;


    public Product() {
    }

    public Product(Long id, String image, String name, double price, String describetion) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.describetion = describetion;
    }

    public Product(String image, String name, double price, String describetion) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.describetion = describetion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescribetion() {
        return describetion;
    }

    public void setDescribetion(String describe) {
        this.describetion = describe;
    }
}