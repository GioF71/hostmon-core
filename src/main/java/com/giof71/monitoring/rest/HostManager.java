package com.giof71.monitoring.rest;

import java.util.List;

import com.giof71.monitoring.dto.structure.Host;
import com.giof71.monitoring.dto.structure.NewHost;
import com.giof71.monitoring.dto.structure.decorated.concrete.HostResult;
import com.giof71.monitoring.dto.structure.decorated.concrete.NewHostResult;

public interface HostManager {
	NewHostResult add(NewHost newHost);
	HostResult get(String friendlyName);
	List<Host> list();
}
