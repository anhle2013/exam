package services;

import modal.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
    void addProduct(Product product);
    boolean existId(int id);
    int getIndex(int id);
    List<Product> findAllByPriceASC();
    List<Product> findAllByPriceDES();
    List<Product> findMaxPrice();
    List<String> read(String path);
    <T> void write(String path, List<T> items);
}
