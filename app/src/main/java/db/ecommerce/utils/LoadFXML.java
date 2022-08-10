package db.ecommerce.utils;

import java.io.IOException;

/** This interface models a FXML Loader */
public interface LoadFXML {

    /**
     * @param s the name of the fxml file to load
     * @return the object loaded, to cast to appropriate type 
     * */
    Object load(String s) throws IOException;

}
