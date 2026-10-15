package com.bodhlens.bodhlens_ai.analytics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bodhlens.bodhlens_ai.analytics.dto.AnalyticsResponse;
import com.bodhlens.bodhlens_ai.analytics.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
	
	@Autowired
	private final AnalyticsService analyticsService;

	public AnalyticsController(AnalyticsService analyticsService) {
		this.analyticsService = analyticsService;
	}
	
	@GetMapping
	public ResponseEntity<AnalyticsResponse> getAnalytics(){
		return ResponseEntity.ok(analyticsService.getAnalytics());
	}
}
