package com.ftp.utils;

import com.ftp.consts.ErrorConst;
import com.ftp.consts.SysConst;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import java.awt.*;

@Slf4j
public class AppUtil {

    private static Pane paneRoot;

    private static double rootPrefHeight = SysConst.SYS_HEIGHT;

    private static double rootPrefWidth = SysConst.SYS_WIDTH;

    public static Pane getPaneRoot() {
        return paneRoot;
    }

    public static void setPaneRoot(Pane paneRoot) {
        AppUtil.paneRoot = paneRoot;
    }

    public static double getRootPrefHeight() {
        return rootPrefHeight;
    }

    public static void setRootPrefHeight(double rootPrefHeight) {
        AppUtil.rootPrefHeight = rootPrefHeight;
    }

    public static double getRootPrefWidth() {
        return rootPrefWidth;
    }

    public static void setRootPrefWidth(double rootPrefWidth) {
        AppUtil.rootPrefWidth = rootPrefWidth;
    }

    /**
     * 跳转其他页面
     *
     * @param event       点击事件
     * @param application 要跳转的页面
     * @param paneRoot  当前页面根节点
     */
    public static void join(MouseEvent event, Application application, Pane paneRoot) {
        MouseButton button = event.getButton();
        //单击操作
        if (button == MouseButton.PRIMARY) {
            instance(application);
            close(paneRoot);
        }
    }

    /**
     * 打开一个新页面
     *
     * @param application
     */
    public static void instance(Application application) {
        if (Strings.isEmpty(application)) {
            AppUtil.dialogError(ErrorConst.E_APPLICATION_NULL);
            return;
        }
        Stage stage = new Stage();
        try {
            application.start(stage);
        } catch (Exception e) {
            AppUtil.dialogError(e.getMessage(), e);
            return;
        }
    }

    /**
     * 关闭页面
     *
     * @param paneRoot
     */
    public static void close(Pane paneRoot) {
        Window window = paneRoot.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).close();
        }
    }


    /**
     * 自定义弹框
     *
     * @param title       标题
     * @param body        内容
     * @param defineClick 确认后发生什么
     * @param cancelClick 取消后发生什么
     * @param dialogError 错误处理
     */
    public static void dialog(String title, String body, DialogClick defineClick, DialogClick cancelClick, DialogError dialogError) {
        try {
            Platform.runLater(() -> {
                //设置一个遮罩
                StackPane stackPane = new StackPane();
                //设置系统配置性大小
                stackPane.setPrefHeight(rootPrefHeight);
                stackPane.setPrefWidth(rootPrefWidth);
                //加到传入的页面
                paneRoot.getChildren().add(stackPane);
                //创建弹框
                JFXDialogLayout messge = new JFXDialogLayout();
                //设置title
                messge.setHeading(new Text(title));
                //设置内容
                messge.setBody(new Text(Strings.getStringByEnter(60, body)));
                //居中弹框
                JFXDialog mag = new JFXDialog(stackPane, messge, JFXDialog.DialogTransition.CENTER);
                //关闭弹框时  设置关闭遮罩
                mag.onDialogClosedProperty().set(event -> paneRoot.getChildren().remove(stackPane));
                JFXButton define = new JFXButton(SysConst.SYS_DEFINE);
	            JFXButton cancel = new JFXButton(SysConst.SYS_CANCEL);
                define.setOnAction(msg -> {
                    mag.close();
                    defineClick.onClick();
                });
                cancel.setOnAction(msg -> {
                    mag.close();
                    cancelClick.onClick();
                });
                messge.setActions(define, cancel);
                mag.show();
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            dialogError.Error();
        }
    }

    /**
     * 自定义弹框
     *
     * @param title       标题
     * @param body        内容
     * @param dialogError 错误处理
     */
    public static void dialogInstance(String title, String body, DialogError dialogError, int prefWidth, int prefHeight) {
        try {
            if (Strings.isEmpty(body)) {
                body = ErrorConst.E_NULL;
            }

            String finalBody = body;
            Platform.runLater(() -> {
                //设置一个遮罩
                StackPane stackPane = new StackPane();
                //设置系统配置性大小
                stackPane.setPrefHeight(rootPrefHeight);
                stackPane.setPrefWidth(rootPrefWidth);

                if (prefWidth > 0) {
                    stackPane.setPrefWidth(prefWidth);
                }
                if (prefHeight > 0) {
                    stackPane.setPrefHeight(prefHeight);
                }

                //加到传入的页面
                paneRoot.getChildren().add(stackPane);
                //创建弹框
                JFXDialogLayout messge = new JFXDialogLayout();
                //设置title
                messge.setHeading(new Text(title));
                //设置内容
                messge.setBody(new Text(Strings.getStringByEnter(60, finalBody)));
                //居中弹框
                JFXDialog mag = new JFXDialog(stackPane, messge, JFXDialog.DialogTransition.CENTER);
                //关闭弹框时  设置关闭遮罩
                mag.onDialogClosedProperty().set(event -> paneRoot.getChildren().remove(stackPane));
                JFXButton define = new JFXButton(SysConst.SYS_DEFINE);
                define.setOnAction(msg -> {
                    mag.close();
                });
                messge.setActions(define);
                mag.show();
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            dialogError.Error();
        }
    }

    public static boolean dialogError(Exception e) {
        log.error(e.getMessage(), e);
        dialogInstance(SysConst.SYS_ERROR, e.getMessage(), () -> {
        }, 0, 0);
        return false;
    }

    public static boolean dialogError(Exception e, int prefWidth, int prefHeight) {
        log.error(e.getMessage(), e);
        dialogInstance(SysConst.SYS_ERROR, e.getMessage(), () -> {
        }, prefWidth, prefHeight);
        return false;
    }

    public static boolean dialogError(String body, Exception e) {
        log.error(body, e);
        dialogInstance(SysConst.SYS_ERROR, body, () -> {
        }, 0, 0);
        return false;
    }

    public static boolean dialogError(String body) {
        log.error(body);
        dialogInstance(SysConst.SYS_ERROR, body, () -> {
        }, 0, 0);
        return false;
    }

    public static boolean dialogSuccess(String body) {
        dialogInstance(SysConst.SYS_SUCCESS, body, () -> {
        }, 0, 0);
        return true;
    }

    public static void dialogInfo(String body) {
        dialogInstance(SysConst.SYS_NFO, body, () -> {
        }, 0, 0);
    }

    /**
     * @param title     数据列表名称
     * @param boName    对象内部属性名称
     * @param prefWidth 预计跨度
     * @param t         传入每一行bo类的对象class
     * @param e         传入属性class
     * @return
     */
    public static <T, E> TableColumn getTableColumn(String title, String boName, Integer prefWidth, T t, E e) {
        TableColumn tableColumn = new TableColumn(title);
        tableColumn.setPrefWidth(prefWidth);
        tableColumn.setCellValueFactory(
                new PropertyValueFactory<T, E>(boName));
        return tableColumn;
    }


}