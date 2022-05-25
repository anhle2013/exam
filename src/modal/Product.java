package modal;

public class Product {
    private int id;
    private String name;
    private int price;
    private int quantity;
    private String description;

    public Product() {}
    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, int price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static Product parseUser(String record) {
        String[] fields = record.split(",");
        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        int price = Integer.parseInt(fields[2]);
        int quantity = Integer.parseInt(fields[3]);
        String description = fields[4];

        return new Product(id,name,price,quantity,description);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",id,name,price,quantity,description);
    }
}
