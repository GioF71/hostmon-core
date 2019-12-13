package com.giof71.monitoring.service;

import java.util.List;
import java.util.Optional;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.service.exc.AlreadyExists;

public interface HostService {
	MonitoredHost addHost(String friendlyName, String address) throws AlreadyExists;
	List<MonitoredHost> findAll();
	long getHostCount();
	void remove(Long id);
	Optional<MonitoredHost> getHost(Long hostId);
	MonitoredHost save(MonitoredHost monitoredHost);
}

