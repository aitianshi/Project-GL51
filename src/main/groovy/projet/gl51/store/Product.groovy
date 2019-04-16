package projet.gl51.store

class Product {
    String id
    String name
    String description
    double price
    double idealTemperature

    Product(name, description, price, idealTemperature) {
        this.name = name
        this.description = description
        this.price = price
        this.idealTemperature = idealTemperature
        this.id = UUID.randomUUID().toString()
    }

    Product(name, price) {
        this.name = name
        this.price = price
        this.id = UUID.randomUUID().toString()
    }

    String getId() {
        return id
    }
}
