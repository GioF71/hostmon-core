package com.giof71.monitoring.service.exc;

public class AlreadyExists extends EntityRelatedException {

	private static final long serialVersionUID = 539241284548664069L;

	public AlreadyExists(Class<?> entityType, String keyRepresentation) {
		super(entityType, keyRepresentation);
	}

	@Override
	protected String createMessage() {
		return String.format("A %s identified by [%s] already exists", 
			getEntityType().getSimpleName(), 
			getKeyRepresentation());	
	}
}
