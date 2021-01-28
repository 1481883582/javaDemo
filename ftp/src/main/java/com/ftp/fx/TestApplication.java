package com.ftp.fx;
import com.ftp.utils.SystemTrayUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void stop() throws Exception {
        System.exit(0);
        log.debug("正在clone（）。。。。");
        super.stop();
    }
}
