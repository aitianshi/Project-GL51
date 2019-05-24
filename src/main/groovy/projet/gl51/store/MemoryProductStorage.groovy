package projet.gl51.store

import javax.inject.Singleton

@Singleton
class MemoryProductStorage implements ProductStorage {

    List<Product> products = []

    @Override
    String save(Product p) {
        products.add(p)
        p.id
    }

    @Override
    void update(String id, Product p) {
        Integer productIndex = products.findIndexOf { it.id == id }
        if (productIndex == -1) throw new NotExistingProductException()
        p.id = id;
        products.set(productIndex, p)
    }

    @Override
    Product getByID(String id) throws NotExistingProductException {
        Product product = products.find { it.id == id }
        if (product == null) throw new NotExistingProductException()
        product
    }

    @Override
    void delete(String id) {
        Product toRemove = this.getByID(id)
        products.remove(toRemove)
    }

    @Override
    List<Product> all() {
        products
    }
}
