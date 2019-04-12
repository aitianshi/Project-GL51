package projet.gl51.store

class MemoryProductStorage implements ProductStorage {

    ArrayList<Product> products

    MemoryProductStorage() {
        products = new ArrayList<Product>()
    }

    @Override
    String save(Product p) {
        products.add(p)
        p.id
    }

    @Override
    void update(String id, Product p) {

    }

    @Override
    Product getByID(String id) {
        return null
    }

    @Override
    void delete(String id) {

    }

    @Override
    List<Product> all() {
        products
    }
}
