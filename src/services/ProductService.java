package services;

import modal.ASCByPrice;
import modal.DESByPrice;
import modal.Product;
import utils.CSVUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService{
    public final static String PATH = "data/products.csv";
    public static List<Product> productsList;

    public ProductService() {
    }

    @Override
    public List<Product> getAll() {
        productsList = new ArrayList<>();
        List<String> records = CSVUtils.read(PATH);
        for (String record : records) {
            productsList.add(Product.parseUser(record));
        }
        return productsList;
    }

    @Override
    public void addProduct(Product product) {
        productsList = getAll();
        productsList.add(product);
        CSVUtils.write(PATH, productsList);
    }

    @Override
    public boolean existId(int id) {
        productsList = getAll();
        for (Product product : productsList)
            if (product.getId() == id)
                return true;
        return false;
    }

    @Override
    public int getIndex(int id) {
        productsList = getAll();
        for (int i = 0; i < productsList.size(); i++)
            if (productsList.get(i).getId() == id)
                return i;
        return -1;
    }

    @Override
    public List<Product> findAllByPriceASC() {
        productsList = getAll();
        List<Product> products = new ArrayList<>(productsList);
        products.sort(new ASCByPrice());
        return products;
    }

    @Override
    public List<Product> findAllByPriceDES() {
        productsList = getAll();
        List<Product> products = new ArrayList<>(productsList);
        products.sort(new DESByPrice());
        return products;
    }

    @Override
    public List<Product> findMaxPrice() {
        productsList = getAll();
        int max = 0;
        for (Product product: productsList)
            if (product.getPrice() > max)
                max = product.getPrice();
        List<Product> maxProducts = new ArrayList<>();

        for (Product product: productsList)
            if (product.getPrice() == max)
                maxProducts.add(product);
        return maxProducts;
    }

    public List<String> read(String path) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null && !line.trim().isEmpty())
                lines.add(line);
        } catch (IOException e) {
            throw new IllegalArgumentException(path + " invalid");
        }
        return lines;
    }

    public <T> void write(String path, List<T> items) {
        try {
            PrintWriter printWriter = new PrintWriter(path);
            for (Object item : items) {
                printWriter.println(item.toString());
            }
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(path + " invalid");
        }
    }
}
