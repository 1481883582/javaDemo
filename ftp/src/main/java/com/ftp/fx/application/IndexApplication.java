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
public class IndexApplication extends SysApplication {

	@Override
	public void init() throws Exception {
		log.debug("正在init（）。。。。");
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		log.debug("正在start（）。。。。");
		config(primaryStage, FXControllerConst.INDEX_FXML).show();
	}

	@Override
	public void stop() throws Exception {
		log.debug("正在stop（）。。。。");
		super.stop();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		log.debug("正在clone（）。。。。");
		return super.clone();
	}
}
