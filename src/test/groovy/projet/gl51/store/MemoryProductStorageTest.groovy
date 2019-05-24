package projet.gl51.store

import spock.lang.Specification

class MemoryProductStorageTest extends Specification {

    ProductStorage store = new MemoryProductStorage()

    def "empty storage should return empty list"(){
        expect:
        store.all() == []
    }

    def "adding a product should return the product in the list"(){
        setup:
        store.save(new Product(name, description, price, idealTemperature))

        when:
        List<Product> all = store.all()

        then:
        all.size() == 1
        all.first().getName() == "Parapluie"

        where:
        name | description | price | idealTemperature
        "Parapluie" | "Très utile" | 12 | 5
    }

    def "adding a product should generate a new id"(){
        setup:
        store.save(new Product())
        store.save(new Product())

        when:
        List<Product> all = store.all()

        then:
        all.get(0).getId() != all.get(1).getId()
    }

    def "deleting a product should remove it from the list"(){
        setup:
        store.save(new Product())
        List<Product> all = store.all()
        String productID = all.first().getId()

        when:
        store.delete(productID)

        then:
        all.size() == 0
    }

    def "modifying a product will change it in the list"(){
        setup:
        store.save(new Product(name, description, price, idealTemperature))
        Product updateProduct = new Product(name1, description1, price1, idealTemperature1)
        List<Product> all = store.all()
        String productID = all.first().getId()

        when:
        store.update(productID, updateProduct)

        then:
        all.first().getName() == "Parapluie transparent"
        all.first().getPrice() == 20

        where:
        name | description | price | idealTemperature | name1 | description1 | price1 | idealTemperature1
        "Parapluie" | "Très utile" | 12 | 5 | "Parapluie transparent" | "Pour voir qui est devant" | 20 | 5
    }

    def "getting a product by its id should throw a NotExistingProductException if it does not exits"(){
        when:
        store.getByID('notExist')

        then:
        thrown NotExistingProductException
    }

    def "getting a product by its id should return it if it does exist"(){
        setup:
        Product product = new Product(name, description, price, idealTemperature)
        String productID = product.getId()
        store.save(product)

        when:
        Product productExist = store.getByID(productID)

        then:
        product == productExist

        where:
        name | description | price | idealTemperature
        "Parapluie" | "Très utile" | 12 | 5
    }
}
