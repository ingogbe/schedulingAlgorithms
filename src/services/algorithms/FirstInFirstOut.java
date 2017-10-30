package services.algorithms;

import java.util.ArrayList;

import bo.ProcessControlBlock;
import bo.ProcessStatus;
import exception.process.ProcessStatusException;

public class FirstInFirstOut {
	
	private ArrayList<ProcessControlBlock> allArrayPCB;
	private ArrayList<ProcessControlBlock> executingPCBs;
	private int currentTime;
	
	public FirstInFirstOut(ArrayList<ProcessControlBlock> allArrayPCB) {
		this.allArrayPCB = allArrayPCB;
		this.currentTime = 0;
	}

	public ArrayList<ProcessControlBlock> getArrayPCB() {
		return allArrayPCB;
	}
	
	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	
	public int checkForNewArrivals() throws ProcessStatusException {
		int count = 0;
		
		for(ProcessControlBlock pcb: allArrayPCB) {
			if(pcb.isArrivalTime(getCurrentTime())) {
				pcb.changeStatus(ProcessStatus.READY);
				executingPCBs.add(pcb);
				count++;
			}
		}
		
		return count;
	}

	public void execute() throws ProcessStatusException {
		ProcessControlBlock currentPCB = null;
		int terminatedPCBs = 0;
		
		while(terminatedPCBs != allArrayPCB.size()) {
			
			//Pra evitar checagem nova toda hora
			if(executingPCBs.size() != allArrayPCB.size()) {
				checkForNewArrivals();
			}	
			
			//Se não houver PCB atual ou ela estiver terminada, pega próxima PCB da fila
			if(currentPCB == null || currentPCB.getCurrentStatus() == ProcessStatus.TERMINATED) {
				for(ProcessControlBlock pcb: executingPCBs) {
					if(pcb.getCurrentStatus() == ProcessStatus.READY) {
						pcb.changeStatus(ProcessStatus.RUNNING);
						currentPCB = pcb;
						break;
					}
				}
			}
			
			if(currentPCB.getBurstTimeLeft(currentTime) == 0) {
				currentPCB.changeStatus(ProcessStatus.TERMINATED);
			}
			
			currentTime++;
			
		}
		
	}
}
