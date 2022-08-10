package db.ecommerce.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class LoadFXMLImpl implements LoadFXML {

    private final FXMLLoader loader;

    public LoadFXMLImpl() {
        this.loader = new FXMLLoader();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object load(final String s) throws IOException {
        this.loader.setLocation(getClass().getResource(s));
        return loader.load();
    }

}
