package com.giof71.monitoring.service;

import java.util.List;
import java.util.Optional;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.service.exc.AlreadyExists;
import com.giof71.monitoring.service.exc.NotFound;

public interface HostService {
	MonitoredHost add(String friendlyName, String address) throws AlreadyExists;
	MonitoredHost updateAddress(String friendlyName, String address) throws NotFound; 
	List<MonitoredHost> findAll();
	long count();
	void removeById(Long id);
	Optional<MonitoredHost> getById(Long hostId);
	MonitoredHost getByFriendlyName(String friendlyName) throws NotFound;
	MonitoredHost save(MonitoredHost monitoredHost);
}

