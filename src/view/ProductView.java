package view;

import modal.Product;
import services.IProductService;
import services.ProductService;
import utils.CSVUtils;

import java.util.List;
import java.util.Scanner;

import static services.ProductService.PATH;

public class ProductView {
    IProductService productService = new ProductService();
    public Scanner scanner = new Scanner(System.in);
    int choice;


    public void menuManager() {
        do {
            try {
                System.out.println("---- CHƯƠNG TRÌNH QUẢN LÝ SẢN PHẨM ----");
                System.out.println("Chọn chức năng theo số (để tiếp tục)");
                System.out.println("1. Xem danh sách");
                System.out.println("2. Thêm mới");
                System.out.println("3. Cập nhật");
                System.out.println("4. Xóa");
                System.out.println("5. Sắp xếp");
                System.out.println("6. Tìm sản phẩm có giá đắt nhất");
                System.out.println("7. Đọc từ file");
                System.out.println("8. Ghi vào file");
                System.out.println("9. Thoát");
                System.out.print("Chọn chức năng: ");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<Product> productList = productService.getAll();
                        CSVUtils.write(PATH, productList);
                        displayPart(productList);
                        break;
                    case 2:
                        Product newProduct = addProduct();
                        productService.addProduct(newProduct);
                        break;
                    case 3:
                        editProduct();
                        break;
                    case 4:
                        removeProduct();
                        break;
                    case 5:
                        sortProduct();
                        break;
                    case 6:
                        System.out.println("Sản phẩm có giá cao nhất:");
                        displayProducts(productService.findMaxPrice());
                        break;
                    case 7:
                        productService.read(PATH);
                        break;
                    case 8:
                        productList = productService.getAll();
                        productService.write(PATH,productList);
                        break;
                    case 9:
                        System.exit(0);
                    default:
                        System.out.println("Chọn sai! Vui lòng chọn lại!");

                }
            } catch (Exception e) {
                System.out.println("Nhập sai");
            }
        } while (true);
    }

    public Product addProduct() {
        int id;
        String name;
        int price;
        int quantity;
        String description;
        boolean exists;
        boolean input = false;

        try {
            System.out.println("Thông tin sản phẩm:");
            do {
                System.out.print("\tNhập mã sản phẩm: ");
                id = Integer.parseInt(scanner.nextLine());
                exists = productService.existId(id);
                if (exists)
                    System.out.println("\nMã sản phẩm đã tồn tại!");
            } while (exists);
            System.out.print("\tNhập tên sản phẩm: ");
            name = scanner.nextLine();
            System.out.print("\tNhập giá: ");
            price = Integer.parseInt(scanner.nextLine());
            System.out.print("\tNhập số lượng: ");
            quantity = Integer.parseInt(scanner.nextLine());
            System.out.print("\tNhập mô tả: ");
            description = scanner.nextLine();

            return new Product(id, name, price, quantity, description);
        } catch (Exception e) {
            System.out.println("Nhập sai");
        }
        return null;
    }

    public void editProduct() {
        do {
            int id;
            boolean exists;
            System.out.print("Nhập mã sản phẩm: ");
            id = Integer.parseInt(scanner.nextLine());
            exists = productService.existId(id);
            if (Integer.valueOf(id) == null) {
                return;
            }
            if (exists)
                editAction(id);
            else {
                System.out.println("\nMã sản phẩm không tồn tại!");
            }
        } while (true);
    }
    private void editAction(int id) {
        int newId;
        String newName;
        int newPrice;
        int newQuantity;
        String newDescription;

        int index = productService.getIndex(id);

        System.out.println("Nhập thông tin mới: ");
        System.out.print("\nNhập mã sản phẩm: ");
        newId = Integer.parseInt(scanner.nextLine());

        System.out.print("\nNhập tên sản phẩm: ");
        newName = scanner.nextLine();

        System.out.print("\nNhập giá: ");
        newPrice = Integer.parseInt(scanner.nextLine());

        System.out.print("\nNhập số lượng: ");
        newQuantity = Integer.parseInt(scanner.nextLine());

        System.out.print("\nNhập mô tả: ");
        newDescription = scanner.nextLine();

        List<Product> productList = productService.getAll();
        productList.set(index, new Product(newId,newName,newPrice,newQuantity,newDescription));
        CSVUtils.write(PATH, productList);
    }

    public void removeProduct() {
        do {
            int id;
            boolean exists;
            System.out.print("Nhập mã sản phẩm: ");
            id = Integer.parseInt(scanner.nextLine());
            exists = productService.existId(id);
            if (Integer.valueOf(id) == null) {
                return;
            }
            if (exists) {
                int index = productService.getIndex(id);
                List<Product> productList = productService.getAll();
                System.out.println("\nBạn chắc chắn muốn xóa sản phẩm này?");
                System.out.println("Nhấn 'y' để xóa, hoặc nhấn phím bất kỳ để quay lại");
                String key = scanner.nextLine();
                if (key.equals("y")) {
                    productList.remove(index);
                    CSVUtils.write(PATH, productList);
                } else
                    return;
            } else {
                System.out.println("\nMã sản phẩm không tồn tại!");
            }
        } while (true);
    }

    public void sortProduct() {
        System.out.println("Chọn loại sắp xếp:");
        System.out.println("1. Sắp xếp theo giá tăng dần");
        System.out.println("2. Sắp xếp giá giảm dần");
        System.out.println();
        System.out.println("0. Quay lại");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                displayProducts(productService.findAllByPriceASC());
                break;
            case "2":
                displayProducts(productService.findAllByPriceDES());
                break;
            case "0":
                return;
            default:
                System.out.println("Chọn sai");
        }
    }

    private void displayProducts(List<Product> products) {
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("*                          LIST OF PRODUCTS                         *");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("_____________________________________________________________________");
        System.out.println("|  ID  |       Name        |  Price  |  Quantity  |   Description   |");
        System.out.println("|------|-------------------|---------|------------|-----------------|");
        for (Product product : products)
            System.out.printf("| %-5d| %-18s| %-8d| %-11d| %-16s|\n",
                    product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getDescription());
        System.out.println("|______|___________________|_________|____________|_________________|");
    }

    private void displayPart(List<Product> products) {
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("*                          LIST OF PRODUCTS                         *");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("_____________________________________________________________________");
        System.out.println("|  ID  |       Name        |  Price  |  Quantity  |   Description   |");
        System.out.println("|------|-------------------|---------|------------|-----------------|");
        int j = 0;
        int count = 1;
        String key = "";

        while (key.equals("") && j < products.size()) {
            for (int i = j; i < products.size(); i++) {
                System.out.printf("| %-5d| %-18s| %-8d| %-11d| %-16s|\n",
                        products.get(i).getId(), products.get(i).getName(),
                        products.get(i).getPrice(), products.get(i).getQuantity(), products.get(i).getDescription());
                j = i + 1;
                if (count++ % 5 == 0) {
                    System.out.println("|------|-------------------|---------|------------|-----------------|");
                    break;
                }
                if (j == products.size())
                    System.out.println("|______|___________________|_________|____________|_________________|");
            }
            key = scanner.nextLine();
        }
    }
}
