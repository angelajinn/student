package com.cicad.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ThymeleafController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	private String getApp(Model model) {
		return "app";
	}

}
