package bo;

import exception.process.ProcessStatusException;

public class ProcessControlBlock {
	
	private int id;
	private String name;
	
	private ProcessStatus status;
	private int priority;
	private int arrivalTime;
	private int burstTime;
	private int elapsedTime;
	
	public ProcessControlBlock(int id, int priority, String name, int arrivalTime, int burstTime) {
		this.id = id;
		this.name = name;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.status = new ProcessStatus();
		this.priority = priority;
		this.elapsedTime = 0;
	}
	
	public int getBurstTimeLeft(int currentTime) {
		return (getBurstTime() - getElapsedTime());
	}
	
	public boolean isArrivalTime(int currentTime) {
		return (currentTime == getArrivalTime());
	}
	
	public ProcessStatus getStatus() {
		return this.status;
	}
	
	public void changeStatus(int changeType) throws ProcessStatusException {
		getStatus().changeStatus(changeType);
	}
	
	public int getCurrentStatus() {
		return getStatus().getCurrentStatus();
	}

	public int getPriority() {
		return priority;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void incrementElapsedTime() {
		this.elapsedTime++;
	}
	
	
	
}
