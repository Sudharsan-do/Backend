package com.hackathon.Events.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hackathon.Events.forms.BlogForm;
import com.hackathon.Events.forms.LoginForm;
import com.hackathon.Events.models.Blog;
import com.hackathon.Events.models.UserDetails;
import com.hackathon.Events.repositories.BlogRepository;
import com.hackathon.Events.repositories.UserRepository;
import com.hackathon.Events.utilities.Constants;
import com.hackathon.Events.utilities.Security;
import com.hackathon.Events.utilities.Validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller

public class MainController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BlogRepository blogRepository;

	@GetMapping("/")
	public ModelAndView home(HttpServletRequest req) {
		ModelAndView model = new ModelAndView("home");
		HttpSession s = req.getSession(false);
		UserDetails l = (UserDetails) s.getAttribute("userForm");
		model.addObject("list", blogRepository.findByUserDetailsAndStatus(l, Constants.YES));
		return model;
	}

	@GetMapping("/addBlog")
	public ModelAndView add(Model m) {
		ModelAndView model = new ModelAndView("addOrEditBlog");
		model.addObject("blogForm", new BlogForm());
		return model;
	}

	@GetMapping("/editBlog/{id}")
	public ModelAndView edit(Model m, @PathVariable("id") String id) {
		ModelAndView model = new ModelAndView("addOrEditBlog");
		Blog b = blogRepository.findById(id).get();
		b.setStatus("2");//edit
		model.addObject("blogForm", b);
		return model;
	}

	@PostMapping("/editBlogPost")
	public ModelAndView editPost(Model m, @ModelAttribute("blogForm") BlogForm blogForm, HttpServletRequest req) {
		try {
			ModelAndView model = new ModelAndView("redirect:/");
			HttpSession s = req.getSession(false);
			Optional<Blog> b = blogRepository.findById(blogForm.getId());
			UserDetails l = (UserDetails) s.getAttribute("userForm");
			if (!b.get().getUserDetails().getUserId().equals(l.getUserId())) {
				model.addObject("msg", "Access Restricted");
			}
			if (Validator.validate(blogForm)) {
				Blog b1 = new Blog();
				b1.setName(blogForm.getName());
				b1.setContent(blogForm.getContent());
				b1.setUserDetails((UserDetails) s.getAttribute("userForm"));
				b1.setUrl(blogForm.getUrl());
				if (blogForm.getScheduleFlag()!=null && blogForm.getScheduleFlag()) {
					b1.setStatus(Constants.NO);
					b1.setScheduledAt(new Timestamp(format(blogForm.getScheduleAt()).getTime()));
				} else {
					b1.setStatus(Constants.YES);
				}
				b1.setModifiedAt(new Timestamp(System.currentTimeMillis()));
				blogRepository.deleteById(blogForm.getId().toString());
				blogRepository.save(b1);
				model.addObject("msg", "Edited Successfully!!");
			} else {
				model.addObject("msg", Validator.msg);
			}
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/timeout");
		}
	}

	@PostMapping("/addBlogPost")
	public ModelAndView addPost(Model m, @ModelAttribute("blogForm") BlogForm blogForm, HttpServletRequest req) {
		try {
			if("2".equals(blogForm.getStatus())){
				return editPost(m,blogForm,req);
			}
			ModelAndView model = new ModelAndView("redirect:/");
			HttpSession s = req.getSession(false);
			if (Validator.validate(blogForm)) {
				Blog b = new Blog();
				b.setName(blogForm.getName());
				b.setContent(blogForm.getContent());
				b.setUserDetails((UserDetails) s.getAttribute("userForm"));
				if (blogForm.getScheduleFlag()!=null && blogForm.getScheduleFlag()) {
					b.setStatus(Constants.NO);
					b.setScheduledAt(new Timestamp(format(blogForm.getScheduleAt()).getTime()));
				} else {
					b.setStatus(Constants.YES);
				}
				b.setUrl(blogForm.getUrl());
				b.setModifiedAt(new Timestamp(System.currentTimeMillis()));
				blogRepository.save(b);
				model.addObject("msg", "Saved Successfully!!");
			} else {
				model.addObject("msg", Validator.msg);
			}
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/timeout");
		}
	}

	@GetMapping("/delete/{id}")
	public ModelAndView del(HttpServletRequest req, @PathVariable(name = "id") String id) {
		ModelAndView model = new ModelAndView("redirect:/");
		Optional<Blog> b = blogRepository.findById(id);
		HttpSession s = req.getSession(false);
		UserDetails l = (UserDetails) s.getAttribute("userForm");
		if (l.getUserId().equals(b.get().getUserDetails().getUserId())) {
			blogRepository.deleteById(id);
			model.addObject("msg", "Delete Successfull");
		} else {
			model.addObject("msg", "Access restricted");
		}
		return model;
	}

	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("redirect:/loginPage");
		try {
			if ("1".equals(loginForm.getReg())) {
				if (Validator.validate(loginForm)) {
					if (userRepository.findByEmail(loginForm.getEmail()) != null) {
						model.addObject("msg", "Already exists");
						return model;
					}
					UserDetails u = new UserDetails();
					u.setEmail(loginForm.getEmail());
					u.setHashedPass(Security.encrypt(loginForm.getPassword()));
					userRepository.save(u);
					model.addObject("msg", "Registration Successfull");
				} else {
					model.addObject("msg", Validator.msg);

				}
				return model;
			} else {
				if (Validator.validate(loginForm)) {
					UserDetails u = userRepository.findByEmail(loginForm.getEmail());
					if (u != null && Security.decrypt(u.getHashedPass()).equals(loginForm.getPassword())) {
						HttpSession s = req.getSession(true);
						s.setMaxInactiveInterval(360);
						s.setAttribute("userForm", u);
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

	@GetMapping("/timeout")
	public String timeout() {
		return "/timeout";
	}

	public Date format(String s) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return f.parse(s);
	}

}
