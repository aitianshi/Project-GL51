package projet.gl51.store

import spock.lang.Specification

class MemoryProductStorageTest extends Specification {

    ProductStorage store = new MemoryProductStorage()
    ProductStorage emptyStore = new MemoryProductStorage()

    def setup(){
        store.save(new Product('parapluie', 12))
        store.save(new Product('écharpe', 12))
    }

    def "empty storage return empty list"(){
        expect:
        emptyStore.all() == []
    }

    def "adding a product returns the product in the list"(){
        setup:
        emptyStore.save(new Product('manteau', 12))

        when:
        def all = emptyStore.all()

        then:
        all.size() == 1
        all.first().getName() == 'manteau'
    }

    def "adding a product will generate a new id"(){
        when:
        def all = store.all()

        then:
        all.get(0).getId() != all.get(1).getId()
    }

    def "deleting a product will remove it from the list"(){
        setup:
        def all = store.all()
        def productID = all.first().getId()

        when:
        store.delete(productID)

        then:
        all.size() == 1
    }

    def "modifying a product will change it in the list"(){
        setup:
        def updateProduct = new Product('parapluie 3D', 20)
        def all = store.all()
        def productID = all.first().getId()

        when:
        store.update(productID, updateProduct)

        then:
        all.first().getName() == 'parapluie 3D'
        all.first().getPrice() == 20
    }

    def "getting a product by its id will throw a NotExistingProductException if it does not exits"(){
        when:
        store.getByID('notExist')

        then:
        thrown NotExistingProductException
    }

    def "getting a product by its id will return it if it does exist"(){
        setup:
        def productID = store.all().first().getId()

        when:
        def productExist = store.getByID(productID)

        then:
        productExist.getName() == 'parapluie'
        productExist.getPrice() == 12
    }
}
