package com.giof71.monitoring.rest.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giof71.monitoring.HostmonCoreApplication;
import com.giof71.monitoring.rest.About;

@RestController
public class AboutImpl implements About {

	@Override
	@GetMapping(value = "/about/name")
	public String name() {
		return HostmonCoreApplication.class.getSimpleName();
	}

}
