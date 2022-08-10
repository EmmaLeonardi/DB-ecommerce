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

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getController() throws IllegalStateException {
        var a = this.loader.getController();
        if (a == null) {
            throw new IllegalStateException("Cannot call getController without first calling load");
        } else {
            return a;
        }
    }

}
