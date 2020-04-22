package codegym.model.Product;

import org.springframework.web.multipart.MultipartFile;

public class ProductForm {

    private Long id;
    private MultipartFile image;
    private String name;
    private double price;
    private String describe;

    public ProductForm(Long id, MultipartFile image, String name, double price, String describe) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.describe = describe;
    }

    public ProductForm(MultipartFile image, String name, double price, String describe) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.describe = describe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ProductForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
