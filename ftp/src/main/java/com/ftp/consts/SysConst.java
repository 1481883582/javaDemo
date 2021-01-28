package com.ftp.consts;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SysConst {
    public static final int SYS_WIDTH = 1160;
    public static final int SYS_HEIGHT = 710;
    public static final String SYS_ERROR = "错误";
    public static final String SYS_NFO = "提示";
    public static final String SYS_DEFINE = "确定";
    public static final String SYS_CANCEL = "取消";
    public static final String SYS_SUCCESS = "成功";
    public static final int SYS_WIDTH_20 = SYS_WIDTH / 100 * 20;
    public static final int SYS_WIDTH_80 = SYS_WIDTH / 100 * 80;
    public static final String SYS_APP_TITLE = "工具";
    public static final String SYS_APP_FAVICON = "/img/favicon.png";
    public static final String SYS_CLOSE_TITLE = "提示";
    public static final String SYS_CLOSE_BODY = "确定要放弃本次操作么？";
    public static final int SELECT_INIT_0 = 0;
    public static final int PREF_WIDTH_260 = 260;
    public static final int PREF_WIDTH_300 = 300;
    public static final String REGISTERED_SQLITE = "jdbc:sqlite:config/mydb.db";
    public static final String ALL_CONFIG = "所有工具";
    public static final String INSERT = "新增";
    public static final String UPDATE = "修改";
    public static final String DELETE = "删除";
    public static final String TEST_DATA = "操作记录";
}
