package codegym.model.Product;

public class ProductHasAmount {
    private Long id;
    private String image;
    private String name;
    private double price;
    private String describetion;
    private int amount;

    public ProductHasAmount(Long id, String image, String name, double price, String describetion, int amount) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.describetion = describetion;
        this.amount = amount;
    }

    public ProductHasAmount() {
    }

    public ProductHasAmount(Long id, String image, String name, double price, String describetion) {
        this.id = id;
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

    public void setDescribetion(String describetion) {
        this.describetion = describetion;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
