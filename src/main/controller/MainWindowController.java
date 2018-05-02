package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import model.album.AlbumSortingCriterias;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
final public class MainWindowController implements Initializable
{
    @FXML
    private ComboBox<String> sortBy;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        List<String> stringAlbumSortingCriterias = Arrays.stream(AlbumSortingCriterias.values()).map(Enum::toString)
                .collect(Collectors.toList());
        ObservableList<String> ioOperations = FXCollections.observableList(stringAlbumSortingCriterias);
        sortBy.setItems(ioOperations);
    }
}
