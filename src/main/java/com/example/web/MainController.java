package com.example.web;

import java.time.DateTimeException;
import java.time.LocalDate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Human;
import com.example.domain.User;
import com.example.service.HumanService;
import com.example.service.LoginUserDetails;
import com.example.service.UserService;

@Controller
@RequestMapping("humans")
public class MainController {
	@Autowired
	HumanService humanService;
	
	@Autowired
	UserService userService;
	
	@ModelAttribute
	HumanForm setUpForm(){
		return new HumanForm();
	}
	
	@ModelAttribute
	UserForm setUpUserForm(){
		return new UserForm();
	}
	
	@GetMapping
	String list(Model model){
		List<Human> humans = humanService.findAllOrderByIdDesc();
		List<User> users = userService.findAll();
		model.addAttribute("humans",humans);
		model.addAttribute("users",users);
		return "humans/list";
	}
	
	final static Map<String, String> SEX_ITEMS =
		    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		    {
		      put("男", "男");
		      put("女", "女");
		      
		    }
		  });
	
	final static Map<Integer, Integer> YEAR_ITEMS =
		    Collections.unmodifiableMap(new LinkedHashMap<Integer, Integer>() {
		    {
		    	for(int i=2017;i>1900;i--){
		    		put(i, i);		      
		    	}
		    }
		  });
	final static Map<Integer, Integer> MONTH_ITEMS =
		    Collections.unmodifiableMap(new LinkedHashMap<Integer, Integer>() {
		    {
		    	for(int i=1;i<=12;i++){
		    		put(i, i);		      
		    	}
		    }
		  });
	final static Map<Integer, Integer> DAY_ITEMS =
		    Collections.unmodifiableMap(new LinkedHashMap<Integer, Integer>() {
		    {
		    	for(int i=1;i<=31;i++){
		    		put(i, i);		      
		    	}
		    }
		  });
	
	@PostMapping(path="create")
	String input(Model model){
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("yearItems",YEAR_ITEMS);
		model.addAttribute("monthItems",MONTH_ITEMS);
		model.addAttribute("dayItems",DAY_ITEMS);
		return "humans/input";
	}
	
	@PostMapping(path="create2")
	String create(@Validated HumanForm form, BindingResult result,Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails){
		if(result.hasErrors()){
			return input(model);
		}
		try{
		LocalDate birthDay = LocalDate.of(form.getYear(),form.getMonth(),form.getDay());		
		Human human = new Human();
		BeanUtils.copyProperties(form, human);
		human.setAge(humanService.getAge(birthDay));
		humanService.create(human,userDetails.getUser());
		}catch (DateTimeException e){
			// 日時が不適切です、インプットする(後で)
			return input(model);
		}
		return "redirect:/humans";
	}
	
	@GetMapping(path="edit" ,params="form")
	String editForm(@RequestParam Integer id,HumanForm form,Model model){
		Human human = humanService.findOne(id);
		BeanUtils.copyProperties(human, form);
		model.addAttribute("sexItems", SEX_ITEMS);
		model.addAttribute("yearItems",YEAR_ITEMS);
		model.addAttribute("monthItems",MONTH_ITEMS);
		model.addAttribute("dayItems",DAY_ITEMS);
		return "humans/edit";
	}
	
	@PostMapping(path = "edit")
	String edit(@RequestParam  Integer id,@Validated HumanForm form,BindingResult result,Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails){
		if(result.hasErrors()){
			return edit(id, form, result, model,userDetails);
		}
		try{
		LocalDate birthDay = LocalDate.of(form.getYear(),form.getMonth(),form.getDay());	
		Human human = new Human();
		BeanUtils.copyProperties(form, human);
		human.setAge(humanService.getAge(birthDay));
		human.setId(id);
		humanService.update(human,userDetails.getUser());
		}catch (DateTimeException e){
			// 日時が不適切です、インプットする(後で)
			return input(model);
		}
		return "redirect:/humans";
	}
	
	@PostMapping(path = "delete")
	String delete(@RequestParam Integer id){
		humanService.delete(id);
		return "redirect:/humans";
	}
	
	@PostMapping(path="usercreate")
	String userinput(Model model){		
		return "users/userinput";
	}
	
	@PostMapping(path="usercreate2")
	String usercreate(@Validated UserForm form, BindingResult result,Model model){
		if(result.hasErrors()){
			return userinput(model);
		}
		if(!form.getPassword().equals(form.getPassword2())){
			return userinput(model);
		}
		User user = new User();
		user.setUsername(form.getUsername());	
		user.setEncodedPassword(new Pbkdf2PasswordEncoder().encode(form.getPassword()));//securityを入れたらエンコード
		userService.create(user);		
		return "redirect:/humans";
	}
	
	@PostMapping(path = "userdelete")
	String userdelete(@RequestParam String id){
		userService.delete(id);
		return "redirect:/humans";
	}
}
