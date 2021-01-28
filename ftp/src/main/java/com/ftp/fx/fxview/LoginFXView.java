package com.ftp.fx.fxview;

import de.felixroske.jfxsupport.FXMLView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FXMLView(value = "/fxml/login.fxml")
public abstract class LoginFXView implements Initializable {

    @FXML
    protected AnchorPane anchorPaneRoot;
    @FXML
    protected TextField host;
    @FXML
    protected TextField domain;
    @FXML
    protected TextField user;
    @FXML
    protected TextField pwd;
    @FXML
    protected TextField clsId;
    @FXML
    protected TextField progId;
    @FXML
    protected Button connection;

}
