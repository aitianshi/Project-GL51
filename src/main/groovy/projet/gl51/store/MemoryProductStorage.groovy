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
        int indexOfProduct = products.findIndexOf {it.id == id}
        products.remove(indexOfProduct)
        products.add(indexOfProduct, p)
    }

    @Override
    Product getByID(String id) throws NotExistingProductException {
        Product productFound = products.find { it.id == id }

        if (productFound == null) {
            throw new NotExistingProductException("The product has not been found !")
        } // else

        return productFound
    }

    @Override
    void delete(String id) {
        products.removeIf { it.id == id }
    }

    @Override
    List<Product> all() {
        products
    }
}
