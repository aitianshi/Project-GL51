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
        store.save(new Product(name: "parapluie"))

        when:
        def all = store.all()

        then:
        all.size() == 1
        all.first().name == 'parapluie'
    }
}
