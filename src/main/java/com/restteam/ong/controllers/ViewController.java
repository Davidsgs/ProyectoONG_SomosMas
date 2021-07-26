package com.restteam.ong.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	@RequestMapping(value = "/hello")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "helloworld";
	}
	
}
