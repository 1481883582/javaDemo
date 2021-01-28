package com.ftp.fx.fxview;

import de.felixroske.jfxsupport.FXMLView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FXMLView(value = "/fxml/index.fxml")
public abstract class IndexFXView implements Initializable {

    @FXML
    protected AnchorPane paneRoot;
    @FXML
    protected AnchorPane tabPane;
    @FXML
    protected TreeView<String> treeRoot;
    @FXML
    protected TableView<Object> tableView;
    @FXML
    protected TextArea textArea;
    @FXML
	protected TextField hostName;
    @FXML
	protected TextField userName;
	@FXML
	protected TextField password;
	@FXML
	protected TextField port;
	@FXML
	protected Button connect;
	@FXML
	protected Button disconnect;
}
