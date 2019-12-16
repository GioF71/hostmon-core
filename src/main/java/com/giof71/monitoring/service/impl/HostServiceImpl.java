package com.giof71.monitoring.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.repository.HostRepository;
import com.giof71.monitoring.service.HostService;
import com.giof71.monitoring.service.exc.AlreadyExists;
import com.giof71.monitoring.service.exc.NotFound;

@Component
public class HostServiceImpl implements HostService {
	
	@Autowired
	private HostRepository hostRepository;

	@Transactional
	@Override
	public long count() {
		return hostRepository.count();
	}

	@Transactional
	@Override
	public MonitoredHost add(String friendlyName, String address) throws AlreadyExists {
		if (hostRepository.getByFriendlyName(friendlyName) == null) {
			MonitoredHost host = new MonitoredHost();
			host.setFriendlyName(friendlyName);
			host.setAddress(address);
			hostRepository.save(host);
			return host;
		} else {
			throw new AlreadyExists(MonitoredHost.class, friendlyName);
		}
	}

	@Transactional
	@Override
	public MonitoredHost updateAddress(String friendlyName, String address) throws NotFound {
		MonitoredHost host = hostRepository.getByFriendlyName(friendlyName);
		host.setAddress(address);
		host.setUpdateTimestamp(Calendar.getInstance());
		hostRepository.save(host);
		return host;
	}

	@Transactional
	@Override
	public List<MonitoredHost> findAll() {
		return hostRepository.findAllByOrderByFriendlyNameAsc();
	}

	@Transactional
	@Override
	public void removeById(Long id) {
		hostRepository.deleteById(id);
	}

	@Transactional
	@Override
	public Optional<MonitoredHost> getById(Long hostId) {
		return hostRepository.findById(hostId);
	}

	@Transactional
	@Override
	public MonitoredHost getByFriendlyName(String friendlyName) throws NotFound {
		return Optional.ofNullable(hostRepository.getByFriendlyName(friendlyName))
			.orElseThrow(() -> new NotFound(MonitoredHost.class, friendlyName));
	}

	@Transactional
	@Override
	public MonitoredHost save(MonitoredHost monitoredHost) {
		return hostRepository.save(monitoredHost);
	}
}
