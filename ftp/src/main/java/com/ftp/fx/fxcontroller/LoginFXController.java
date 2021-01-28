package com.ftp.fx.fxcontroller;

import com.ftp.fx.fxview.LoginFXView;
import com.ftp.utils.AppUtil;
import de.felixroske.jfxsupport.FXMLController;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class LoginFXController extends LoginFXView implements SysFXController {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppUtil.setPaneRoot(anchorPaneRoot);
        AppUtil.setRootPrefHeight(350);
        AppUtil.setRootPrefWidth(580);
        initView();
        initEvent();
        initService();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initService() {
    }
}
