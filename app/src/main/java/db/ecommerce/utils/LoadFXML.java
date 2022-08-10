package db.ecommerce.utils;

import java.io.IOException;

/** This interface models a FXML Loader */
public interface LoadFXML {

    /**
     * @param s the name of the fxml file to load
     * @return the object loaded, to cast to appropriate type
     */
    Object load(String s) throws IOException;

    /**
     * @return the controller of the object loaded
     * @throws IllegalStateException if this method is called before load
     */
    Object getController() throws IllegalStateException;

}
