package bo;

import exception.process.ProcessStatusException;

public class ProcessStatus {
	
	//Status
	public static final int RUNNING = 1;
	public static final int BLOCKED = 2;
	public static final int READY = 3;
	public static final int NEW = 4;
	public static final int TERMINATED = 5;
	
	//Status change types
	public static final int NEW_TO_READY = 1;
	public static final int READY_TO_RUNNING = 2;
	public static final int RUNNING_TO_READY = 3;
	public static final int RUNNING_TO_BLOCKED = 4;
	public static final int BLOCKED_TO_READY = 5;
	public static final int RUNNING_TO_TERMINATED = 6;
	
	private int status;
	
	public ProcessStatus() {
		this.status = NEW;
	}

	public int getCurrentStatus() {
		return this.status;
	}
	
	public void changeStatus(int changeType) throws ProcessStatusException {
		if(changeType == NEW_TO_READY) {
			if(getCurrentStatus() == NEW) {
				this.status = READY;
			}
			else {
				throw new ProcessStatusException("The 'NEW_TO_READY' change type needs the current status to be 'NEW'.");
			}
		}
		
		else if(changeType == READY_TO_RUNNING) {
			if(getCurrentStatus() == READY) {
				this.status = RUNNING;
			}
			else {
				throw new ProcessStatusException("The 'READY_TO_RUNNING' change type needs the current status to be 'READY'.");
			}
		}
		
		else if(changeType == RUNNING_TO_READY) {
			if(getCurrentStatus() == RUNNING) {
				this.status = READY;
			}
			else {
				throw new ProcessStatusException("The 'RUNNING_TO_READY' change type needs the current status to be 'RUNNING'.");
			}
		}
		
		else if(changeType == RUNNING_TO_BLOCKED) {
			if(getCurrentStatus() == RUNNING) {
				this.status = BLOCKED;
			}
			else {
				throw new ProcessStatusException("The 'RUNNING_TO_BLOCKED' change type needs the current status to be 'RUNNING'.");
			}
		}
		
		else if(changeType == BLOCKED_TO_READY) {
			if(getCurrentStatus() == BLOCKED) {
				this.status = READY;
			}
			else {
				throw new ProcessStatusException("The 'BLOCKED_TO_READY' change type needs the current status to be 'BLOCKED'.");
			}
		}
		
		else if(changeType == RUNNING_TO_TERMINATED) {
			if(getCurrentStatus() == RUNNING) {
				this.status = TERMINATED;
			}
			else {
				throw new ProcessStatusException("The 'RUNNING_TO_TERMINATED' change type needs the current status to be 'RUNNING'.");
			}
		}
		
		else {
			throw new ProcessStatusException("The passed parameter is not a valid status change type.");
		}
		
	}
	
}
