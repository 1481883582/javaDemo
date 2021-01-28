package com.ftp.fx.fxview;

import de.felixroske.jfxsupport.FXMLView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

@Getter
@Setter
@FXMLView(value = "/fxml/sys.fxml")
public class SysFXView {
    @FXML
    protected AnchorPane anchorPaneRoot;
 }
