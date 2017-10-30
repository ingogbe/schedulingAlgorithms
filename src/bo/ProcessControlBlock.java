package bo;

import exception.process.ProcessStatusException;

public class ProcessControlBlock {
	
	private int id;
	private String name;
	
	private ProcessStatus status;
	private int priority;
	private int arrivalTime;
	private int burstTime;
	
	public ProcessControlBlock(int id, int arrivalTime, int burstTime) {
		this.id = id;
		this.name = "";
		this.arrivalTime = arrivalTime;
		this.burstTime = 0;
		this.status = new ProcessStatus();
	}
	
	public ProcessControlBlock(int id, String name, int arrivalTime, int burstTime) {
		this.id = id;
		this.name = name;
		this.arrivalTime = arrivalTime;
		this.burstTime = 0;
		this.status = new ProcessStatus();
	}
	
	public int getBurstTimeLeft(int currentTime) {
		return getBurstTime() - (currentTime - getArrivalTime());
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
	
	
	
}
