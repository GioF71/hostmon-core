package com.giof71.monitoring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.giof71.monitoring.model.MonitoredHost;

public interface HostRepository extends CrudRepository<MonitoredHost, Long> {
	boolean existsByFriendlyName(String friendlyName);
	MonitoredHost getByFriendlyName(String host);
	List<MonitoredHost> findAllByOrderByFriendlyNameAsc();
}
