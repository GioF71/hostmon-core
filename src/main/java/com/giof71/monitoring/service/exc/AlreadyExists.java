package com.giof71.monitoring.service.exc;

public class AlreadyExists extends Exception {

	private static final long serialVersionUID = 539241284548664069L;

	private final Class<?> entityType;
	private final String keyRepresentation;
	
	public AlreadyExists(Class<?> entityType, String keyRepresentation) {
		super();
		this.entityType = entityType;
		this.keyRepresentation = keyRepresentation;
	}

	@Override
	public String getMessage() {
		return String.format("A %s identified by %s already exists", 
			getEntityType().getSimpleName(), 
			getKeyRepresentation());
	}

	public Class<?> getEntityType() {
		return entityType;
	}

	public String getKeyRepresentation() {
		return keyRepresentation;
	}
}
