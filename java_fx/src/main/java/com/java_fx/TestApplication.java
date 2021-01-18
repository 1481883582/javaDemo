package com.java_fx;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
	    SystemTrayUtil.getInstance().listen(primaryStage);
    }

    public void stop() throws Exception {
        System.exit(0);
        log.debug("正在clone（）。。。。");
        super.stop();
    }
}
