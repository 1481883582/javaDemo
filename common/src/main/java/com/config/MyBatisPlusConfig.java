package com.config;


import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MyBatisPlusConfig {

	/**
	 * 分表操作
	 * @return
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

		// 创建SQL解析器集合
		List<ISqlParser> sqlParserList = new ArrayList<>();

		// 动态表名SQL解析器
		DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
		Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>();
		// Map的key就是需要替换的原始表名
//		tableNameHandlerMap.put("em_equinf_task",new ITableNameHandler(){
//			@Override
//			public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
//				// 自定义表名规则，或者从配置文件、request上下文中读取
//
//				// 假设这里的用户表根据年份来进行分表操作
//				String format = LocalDateTimeUtil.format(new Date(), LocalDateTimeUtil.DATE_YYYYMM_PATTERN);
//
//				// 返回最后需要操作的表名，em_equinf_task202001
//				return "em_equinf_task" + format;
//			}
//		});
		dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
		sqlParserList.add(dynamicTableNameParser);
		paginationInterceptor.setSqlParserList(sqlParserList);

		return paginationInterceptor;
	}

}
