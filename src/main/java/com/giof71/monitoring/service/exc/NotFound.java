package com.giof71.monitoring.service.exc;

public class NotFound extends EntityRelatedException {

	private static final long serialVersionUID = 9038076111683156466L;

	public NotFound(Class<?> entityType, String keyRepresentation) {
		super(entityType, keyRepresentation);
	}

	@Override
	protected String createMessage() {
		return String.format("A %s identified by [%s] could not be found", 
			getEntityType().getSimpleName(), 
			getKeyRepresentation());
	}
}
