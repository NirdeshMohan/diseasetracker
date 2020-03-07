package com.rss.diseasetracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rss.diseasetracker.model.LocationWiseStats;
import com.rss.diseasetracker.service.DiseaseDataTrackerService;

@Controller
public class AppControler {
	@Autowired
	DiseaseDataTrackerService diseaseDataTrackerService;
	
	@GetMapping("/")
	public String app(Model model) {
		List<LocationWiseStats> listOfCases = diseaseDataTrackerService.getCurrentStatList();
		int totalWorldWideCases = listOfCases.stream().mapToInt(total -> total.getCurrentTotalCases()).sum();
		model.addAttribute("diseaseresults",listOfCases);
		model.addAttribute("totalCases",totalWorldWideCases);
		return "diseasedetail";
	}

}
