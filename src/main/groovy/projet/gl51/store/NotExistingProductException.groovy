package projet.gl51.store

class NotExistingProductException extends Exception {
    NotExistingProductException(){
        super()
    }

    NotExistingProductException(String message){
        super(message)
    }
}
