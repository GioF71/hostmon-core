package com.giof71.monitoring.rest.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.giof71.monitoring.dto.structure.Host;
import com.giof71.monitoring.dto.structure.NewHost;
import com.giof71.monitoring.dto.structure.decorated.concrete.NewHostResult;
import com.giof71.monitoring.error.ConfigurationError;
import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.rest.HostManager;
import com.giof71.monitoring.service.HostService;
import com.giof71.monitoring.service.exc.AlreadyExists;

@RestController
public class HostManagerImpl implements HostManager {
	
	@Autowired
	private HostService hostService;
	
	@Override
	@GetMapping(value = "/hosts/list")
	public List<Host> list() {
		List<MonitoredHost> list = hostService.findAll();
		List<Host> result = new ArrayList<>();
		for (MonitoredHost current : Optional.ofNullable(list).orElse(Collections.emptyList())) {
			result.add(convertToDto(current));
		}
		return result;
	}

	@Override
	@PutMapping(value = "/hosts/add")
	public NewHostResult add(@RequestBody NewHost newHost) {
		NewHostResult result = new NewHostResult();
		try {
			MonitoredHost newMonitoredHost = hostService.addHost(newHost.getFriendlyName(), newHost.getAddress());
			Host host = convertToDto(newMonitoredHost);
			result.setData(host);
			return result;
		} catch (AlreadyExists exc) {
			result.getOperationResult().fail(ConfigurationError.HOST_ALREADY_EXISTS.name(), exc.getMessage());
		}
		return result;
	}

	private Host convertToDto(MonitoredHost monitoredHost) {
		Host host = new Host();
		host.setId(monitoredHost.getId());
		host.setFriendlyName(monitoredHost.getFriendlyName());
		host.setAddress(monitoredHost.getAddress());
		host.setCreationTimestamp(monitoredHost.getCreationTimestamp());
		host.setUpdateTimestamp(monitoredHost.getUpdateTimestamp());
		return host;
	}
}
