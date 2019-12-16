package com.giof71.monitoring.service.exc;

abstract class EntityRelatedException extends Exception {

	private static final long serialVersionUID = -2683730905096175320L;

	private final Class<?> entityType;
	private final String keyRepresentation;
	
	EntityRelatedException(Class<?> entityType, String keyRepresentation) {
		super();
		this.entityType = entityType;
		this.keyRepresentation = keyRepresentation;
	}
	
	protected abstract String createMessage();

	@Override
	public String getMessage() {
		return createMessage();
	}

	public Class<?> getEntityType() {
		return entityType;
	}

	public String getKeyRepresentation() {
		return keyRepresentation;
	}
}
