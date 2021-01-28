package com.ftp.fx.application;

import com.ftp.FtpApplication;
import com.ftp.consts.FXControllerConst;
import com.ftp.consts.FileConst;
import com.ftp.consts.SysConst;
import com.ftp.utils.FileUtil;
import com.ftp.utils.Strings;
import com.ftp.utils.SystemTrayUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysApplication extends Application {

    /**
     * 项目favicon图标
     */
    public static volatile String configFaviconIcon = FileUtil.getAppUpPath(FileConst.SYS_CONFIG, FileConst.SYS_FAVICON);


    public static Image loadIcon(String configIcon, String defaultIcon) {
        defaultIcon = FileUtil.isFileExist(configIcon) ? configIcon : defaultIcon;
        return new Image(FileUtil.getUrIToString(defaultIcon));
    }


    public Stage config(Stage primaryStage, String fxml, String icon, String title) throws Exception {
        String defaultFxml = FXControllerConst.INDEX_FXML;
        String defaultIcon = SysConst.SYS_APP_FAVICON;
        String defaultTitle = SysConst.SYS_APP_TITLE;

        if (Strings.isNotEmpty(fxml)) {
            defaultFxml = fxml;
        }

        if (Strings.isNotEmpty(icon)) {
            defaultIcon = icon;
        }

        if (Strings.isNotEmpty(title)) {
            defaultTitle = title;
        }

        // 设置stage的scen，然后显示我们的stage
        primaryStage.setScene(new Scene(FtpApplication.LoadFxml(defaultFxml).load()));

        //设置窗口的图标.
//        primaryStage.getIcons().add(new Image(FileUtil.getUrIToString(defaultIcon)));
        primaryStage.getIcons().add(loadIcon(configFaviconIcon, defaultIcon));
        //名字
        primaryStage.setTitle(defaultTitle);

        //自由改变大小
        primaryStage.setResizable(false);
        return primaryStage;
    }

    public Stage config(final Stage primaryStage) throws Exception {
        return config(primaryStage, null, null, null);
    }

    public Stage config(final Stage primaryStage, final String fxml) throws Exception {
        return config(primaryStage, fxml, null, null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
	    SystemTrayUtil.getInstance().listen(primaryStage);
	    config(primaryStage).show();
    }


    public void stop() throws Exception {
        System.exit(0);
        log.debug("正在clone（）。。。。");
        super.stop();
    }
}
