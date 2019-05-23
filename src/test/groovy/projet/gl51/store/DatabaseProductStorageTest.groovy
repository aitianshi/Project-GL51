
package projet.gl51.store

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import static io.micronaut.http.HttpStatus.*

class DatabaseProductStorageTest extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())

    Product sampleProduct = new Product("parapluie ", 12)


    void "index should return empty product list"() {
        given:
        List<Product> products = client.toBlocking().retrieve(HttpRequest.GET('/store/product'), Argument.listOf(Product).type)

        expect:
        products == []
    }

    void "create & get should return the created product"(){
        setup:
        Product productSample = new Product( "parapluie", 12)

        when:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', productSample))
        Product productReturned = client.toBlocking().retrieve(HttpRequest.GET('/store/product/' + id), Argument.of(Product).type)

        then:
        id != ""
        productReturned == productSample
    }

    void "update a product should change its attributes"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', sampleProduct))

        when:
        Product otherProduct = new Product( "parapluie", 15)
        HttpStatus status = client.toBlocking().retrieve(HttpRequest.PATCH('/store/product/' + id, otherProduct), Argument.of(HttpStatus).type)
        Product updatedProduct = client.toBlocking().retrieve(HttpRequest.GET('/store/product/' + id), Argument.of(Product).type)

        then:
        status == OK
        otherProduct == updatedProduct
    }

    void "delete a product should remove it from the list"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', sampleProduct))

        when:
        HttpStatus status = client.toBlocking().retrieve(HttpRequest.DELETE('/store/product/' + id), Argument.of(HttpStatus).type)
        def productList = client.toBlocking().retrieve(HttpRequest.GET('/store/product/'), Argument.of(Product).type)

        then:
        status == OK
        productList == []
    }

    void "delete a product which doesn't exist throw an exception"(){
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', sampleProduct))

        when:
        HttpStatus status = client.toBlocking().retrieve(HttpRequest.DELETE('/store/product/' + id), Argument.of(HttpStatus).type)
        Product productReturned = client.toBlocking().retrieve(HttpRequest.GET('/store/product/' + id), Argument.of(Product).type)

        then:
        status == OK
        thrown HttpClientResponseException
        productReturned == null
    }
}
