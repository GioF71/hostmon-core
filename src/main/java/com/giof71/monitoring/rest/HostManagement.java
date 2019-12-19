package com.giof71.monitoring.rest;

import java.util.List;

import com.giof71.monitoring.dto.structure.Host;
import com.giof71.monitoring.dto.structure.NewHost;
import com.giof71.monitoring.dto.structure.UpdateAddress;
import com.giof71.monitoring.dto.structure.decorated.concrete.DeleteHostResult;
import com.giof71.monitoring.dto.structure.decorated.concrete.HostResult;
import com.giof71.monitoring.dto.structure.decorated.concrete.NewHostResult;

public interface HostManagement {
	NewHostResult add(NewHost newHost);
	HostResult get(String friendlyName);
	DeleteHostResult delete(String friendlyName);
	HostResult updateAddress(UpdateAddress updateAddress);
	List<Host> list();
	Long count();
}
