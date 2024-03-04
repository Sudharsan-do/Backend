package com.hackathon.Events.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hackathon.Events.forms.LoginForm;
import com.hackathon.Events.models.UserDetails;
import com.hackathon.Events.repositories.UserRepository;
import com.hackathon.Events.utilities.Validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

public class MainController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model, @RequestParam(value = "clubId", required = false) String clubId,
			@RequestParam(value = "name", required = false) String name) {
		return "home";
	}

	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("redirect:/loginPage");
		try {
			if ("1".equals(loginForm.getReg())) {
				if (Validator.validate(loginForm)) {
					if(userRepository.findByEmail(loginForm.getEmail())!=null) {
						model.addObject("msg", "Already exists");
						return model;
					}
					UserDetails u = new UserDetails();
					u.setEmail(loginForm.getEmail());
					u.setHashedPass(loginForm.getPassword());
					userRepository.save(u);
					model.addObject("msg", "Registration Successfull");
				} else {
					model.addObject("msg", Validator.msg);

				}
				return model;
			} else {
				if (Validator.validate(loginForm)) {
					UserDetails u = userRepository.findByEmail(loginForm.getEmail());
					if (u!=null && u.getHashedPass().equals(loginForm.getPassword())) {
						HttpSession s = req.getSession(true);
						s.setMaxInactiveInterval(60);
						s.setAttribute("loginForm", loginForm);
						model.setViewName("redirect:/");
						return model;
					} else {
						model.addObject("msg", "Invalid email or pass");
					}
				} else {
					model.addObject("msg", Validator.msg);
				}
				return model;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setViewName("timeout");
		return model;
	}

	@GetMapping("/loginPage")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "loginPage";
	}

	@PostMapping("/logout")
	public String logout(HttpSession s) {
		if (s != null) {
			s.invalidate();
		}
		return "redirect:/loginPage";
	}

}
