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
        Product toUpdate = this.getByID(id)
        int indexOfProduct = products.indexOf(toUpdate)

        products.add(indexOfProduct,p)

    }

    @Override
    Product getByID(String id) throws NotExistingProductException {
        for(Product current in products){
            if (current.id.equals(id)) {
                return current
            }
        }

        throw NotExistingProductException()
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
