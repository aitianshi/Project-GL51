package projet.gl51.store

class Product {
    String id
    String name
    String description
    double price
    double idealTemperature

    Product() {
        this.id = UUID.randomUUID().toString()
    }

    String getId() {
        return id
    }
}
