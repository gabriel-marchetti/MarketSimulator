module com.supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.base;
    requires java.xml;

    opens com.supermarket to javafx.fxml;

    exports com.supermarket;

    exports com.supermarket.controllers to javafx.fxml;

    opens com.supermarket.controllers to javafx.fxml;

    exports com.supermarket.models to javafx.fxml;

    opens com.supermarket.models to javafx.fxml;

}
