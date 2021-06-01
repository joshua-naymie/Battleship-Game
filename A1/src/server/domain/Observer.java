package server.domain;

public abstract class Observer
{
	protected
	Subject<?> subject;
	
	public abstract void update();
	
	/**
	 * Sets the Subject of this Observer
	 * @param subject
	 */
	protected void setSubject(Subject<?> subject)
	{
		this.subject = subject;
	}
}
