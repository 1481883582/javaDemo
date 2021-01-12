package com.swagger.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "测试接口", tags = "测试接口")
@RestController
@RequestMapping("/test")
public class TestController {

	@ApiOperation(value = "测试get", tags = "测试get", httpMethod = "GET")
	@GetMapping("/get")
	public Object get() {
		return "你好，我是Get";
	}

	@ApiOperation(value = "测试post", tags = "测试post", httpMethod = "POST")
	@PostMapping("/post")
	public String post(@RequestParam("msg") String msg) {
		return msg + ",我是Post";
	}

	@ApiOperation(value = "测试put", tags = "测试put", httpMethod = "PUT")
	@GetMapping("/put/{msg}")
	public String put(@PathVariable String msg) {
		return msg + ",我是Put";
	}

	@ApiOperation(value = "测试delete", tags = "测试delete", httpMethod = "DELETE")
	@GetMapping("/delete")
	public String delete(@RequestParam(value = "id", required = false) Long id) {
		return "我是delete";
	}

}
