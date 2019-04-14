package projet.gl51.store

import spock.lang.Specification

class MemoryProductStorageTest extends Specification {

    ProductStorage store = new MemoryProductStorage()

    def "empty storage return empty list"(){
        expect:
        store.all() == []
    }

    def "adding a product returns the product in the list"(){
        setup:
        store.save(new Product('parapluie', 12))

        when:
        def all = store.all()

        then:
        all.size() == 1
        all.first().getName() == 'parapluie'
    }

    def "adding a product will generate a new id"(){
        setup:
        store.save(new Product('parapluie', 12))
        store.save(new Product('écharpe', 12))

        when:
        def all = store.all()

        then:
        all.get(0).getId() != all.get(1).getId()
    }

    def "deleting a product will remove it from the list"(){
        setup:
        store.save(new Product('parapluie', 12))
        def all = store.all()
        def productID = all.first().getId()

        when:
        all.delete(productID)

        then:
        all.size() == 0
    }

    def "modifying a product will change it in the list"(){
        setup:
        store.save(new Product('parapluie', 12))
        def updateProduct = new Product('parapluie 3D', 20)
        def all = store.all()
        def productID = all.first().getId()

        when:
        all.first().update(productID, updateProduct)

        then:
        all.first().getName() == 'parapluie 3D'
        all.first().getPrice() == 20
    }

    def "getting a product by its id will throw a NotExistingProductException if it does not exits"(){
        when:
        store.getByID('notExist')

        then:
        throw NotExistingProductException
    }

    def "getting a product by its id will return it if it does exist"(){
        setup:
        def product = new Product('parapluie',12)
        def productID = product.getId()
        store.save(product)
        def productExist

        when:
        productExist = store.getByID(productID)

        then:
        product == productExist
    }
}
