package com.ftp.fx.fxcontroller;

import com.ftp.consts.SysConst;
import com.ftp.fx.fxview.IndexFXView;
import com.ftp.utils.AppUtil;
import com.ftp.utils.FTPUtils;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@FXMLController
public class IndexFXController extends IndexFXView implements SysFXController {

    private static Button viewBut;

    //列表根节点
    private static volatile TreeItem<String> treeItemRoot = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppUtil.setPaneRoot(paneRoot);
        AppUtil.setRootPrefHeight(SysConst.SYS_HEIGHT);
        AppUtil.setRootPrefWidth(SysConst.SYS_WIDTH);
        initView();
        initEvent();
        initService();
    }

    @Override
    public void initView() {


        //主线程加入列
        Platform.runLater(() -> {
//            tableViewLoadColumn(tableView);
        });


        //声明左侧栏根目录
        treeItemRoot = new TreeItem<>(SysConst.ALL_CONFIG);
//      默认显示标识

        treeRoot.addEventFilter(MouseDragEvent.MOUSE_CLICKED, event -> {
            MouseButton button = event.getButton();
//            3D 位置坐标

            double x = event.getSceneX();
            double y = event.getSceneY();
            //绝对水平位置坐标
//            double x = event.getScreenX();
//            double y = event.getScreenY();
            if (button == MouseButton.SECONDARY) {
//                    获取事件目录节点
                Node node = event.getPickResult().getIntersectedNode();
                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
//                    String name = (String) ((TreeItem) treeRoot.getSelectionModel().getSelectedItem()).getValue();
//
                }
            }

        });


//            注入界面
            treeRoot.setRoot(treeItemRoot);

    }

    @Override
    public void initEvent() {

        // 点击任意地方删除临时组件
        paneRoot.addEventFilter(MouseDragEvent.MOUSE_CLICKED, event -> {
            getPaneRoot().getChildren().remove(viewBut);
        });

        connect.addEventFilter(MouseDragEvent.MOUSE_CLICKED, event -> {
	        FTPUtils.initFtpClient(
	        		hostName.getText(),
			        userName.getText(),
			        password.getText(),
			        Integer.parseInt(port.getText()),
			        ()->{
	        			connect.setDisable(true);
				        hostName.setDisable(true);
				        userName.setDisable(true);
				        password.setDisable(true);
				        port.setDisable(true);
	        			disconnect.setDisable(false);
	        			//成功
			        },()->{
				        connect.setDisable(false);
				        hostName.setDisable(false);
				        userName.setDisable(false);
				        password.setDisable(false);
				        port.setDisable(false);
				        disconnect.setDisable(true);
	        			//失败
			        }
	        );
        });


    }

    public void initService() {


    }

}
