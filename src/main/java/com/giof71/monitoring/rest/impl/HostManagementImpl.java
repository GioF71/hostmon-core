package com.giof71.monitoring.rest.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.giof71.monitoring.dto.OperationResult;
import com.giof71.monitoring.dto.structure.Host;
import com.giof71.monitoring.dto.structure.NewHost;
import com.giof71.monitoring.dto.structure.UpdateAddress;
import com.giof71.monitoring.dto.structure.decorated.concrete.DeleteHostResult;
import com.giof71.monitoring.dto.structure.decorated.concrete.HostList;
import com.giof71.monitoring.dto.structure.decorated.concrete.HostListData;
import com.giof71.monitoring.dto.structure.decorated.concrete.HostResult;
import com.giof71.monitoring.dto.structure.decorated.concrete.NewHostResult;
import com.giof71.monitoring.error.ConfigurationError;
import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.rest.HostManagement;
import com.giof71.monitoring.service.HostService;
import com.giof71.monitoring.service.exc.AlreadyExists;
import com.giof71.monitoring.service.exc.NotFound;

@RestController
public class HostManagementImpl implements HostManagement {
	
	@Autowired
	private HostService hostService;
	
	@Override
	@GetMapping(value = "/host-management/hosts")
	public HostList list() {
		HostList hostList = new HostList();
		try {
			List<MonitoredHost> monitoredHostList = hostService.findAll();
			List<Host> list = new ArrayList<>();
			for (MonitoredHost current : Optional.ofNullable(monitoredHostList).orElse(Collections.emptyList())) {
				list.add(convertToDto(current));
			}
			HostListData hostListData = new HostListData();
			hostListData.setCount(list.size());
			hostListData.setHostList(list);
			hostList.setData(hostListData);
		} catch (Exception exc) {
			setUnspecifiedError(hostList.getOperationResult(), exc);
		}
		return hostList;
	}

	@Override
	@GetMapping(value = "/host-management/count")
	public Long count() {
		return hostService.count();
	}

	@Override
	@PutMapping(value = "/host-management/add")
	public NewHostResult add(@RequestBody NewHost newHost) {
		NewHostResult result = new NewHostResult();
		try {
			MonitoredHost newMonitoredHost = hostService.add(newHost.getFriendlyName(), newHost.getAddress());
			result.setData(convertToDto(newMonitoredHost));
		} catch (AlreadyExists alreadyExists) {
			result.getOperationResult().fail(ConfigurationError.HOST_ALREADY_EXISTS.name(), alreadyExists.getMessage());
		} catch (Exception e) {
			setUnspecifiedError(result.getOperationResult(), e);
		}
		return result;
	}

	@Override
	@PutMapping(value = "/host-management/update-address")
	public HostResult updateAddress(@RequestBody UpdateAddress updateAddress) {
		HostResult result = new HostResult();
		try {
			MonitoredHost host = hostService.updateAddress(updateAddress.getFriendlyName(), updateAddress.getNewAddress());
			result.setData(convertToDto(host));
		} catch (NotFound exc) {
			result.getOperationResult().fail(ConfigurationError.HOST_NOT_FOUND.name(), exc.getMessage());
		} catch (Exception e) {
			setUnspecifiedError(result.getOperationResult(), e);
		}
		return result;
	}

	@Override
	@GetMapping(value = "/host-management/hosts/{friendlyName}")
	public HostResult get(@PathVariable String friendlyName) {
		HostResult result = new HostResult();
		try {
			MonitoredHost host = hostService.getByFriendlyName(friendlyName);
			result.setData(convertToDto(host));
		} catch (NotFound exc) {
			result.getOperationResult().fail(ConfigurationError.HOST_NOT_FOUND.name(), exc.getMessage());
		} catch (Exception e) {
			setUnspecifiedError(result.getOperationResult(), e);
		}
		return result;
	}

	@Override
	@PostMapping(value = "/host-management/delete/{friendlyName}")
	public DeleteHostResult delete(@PathVariable String friendlyName) {
		DeleteHostResult result = new DeleteHostResult();
		try {
			MonitoredHost host = hostService.getByFriendlyName(friendlyName);
			hostService.removeById(host.getId());
			result.setData(convertToDto(host));
		} catch (NotFound exc) {
			result.getOperationResult().fail(ConfigurationError.HOST_NOT_FOUND.name(), exc.getMessage());
		} catch (Exception e) {
			setUnspecifiedError(result.getOperationResult(), e);
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

	private void setUnspecifiedError(OperationResult operationResult, Exception exc) {
		operationResult.fail(
			ConfigurationError.UNSPECIFIED_ERROR.name(),
			String.format("An %s of type [%s] occurred", 
				Exception.class.getSimpleName(),
				exc.getClass().getSimpleName()));
		exc.printStackTrace(System.err);
	}
}
