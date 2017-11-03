package services.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import bo.Algorithm;
import bo.ProcessControlBlock;
import bo.ProcessStatus;
import exception.algorithms.RoundRobinException;
import exception.file.FileException;
import exception.process.ProcessStatusException;

public class RoundRobin extends Algorithm{
	
	private int currentQuantum;
	private int quantum;
	
	public RoundRobin(int quantum, ArrayList<ProcessControlBlock> allPCB) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileException, IOException{
		super(allPCB);
		
		this.quantum = quantum;
		this.currentQuantum = 0;
	}
	
	public void incrementCurrentQuantum(){
		this.currentQuantum++;
	}

	public int getCurrentQuantum() {
		return this.currentQuantum;
	}
	
	public void resetCurrentQuantum() {
		this.currentQuantum = 0;
	}

	public int getQuantum() {
		return this.quantum;
	}
	
	public void sendCurrentPCBToTheEndOfList(ProcessControlBlock currentPCB) {
		removeExecutingPCB(currentPCB);
		addExecutingPCB(currentPCB);
	}
	
	public void executeProcessAtCurrentTime(ProcessControlBlock currentPCB) {
		currentPCB.incrementElapsedTime();
		incrementCurrentQuantum();
	}
	
	public ProcessControlBlock getAndExecuteNextPCB() throws ProcessStatusException {
		ProcessControlBlock currentPCB = null;
		
		for(ProcessControlBlock pcb: getExecutingPCBs()) {
			if(pcb.getCurrentStatus() == ProcessStatus.READY) {
				resetCurrentQuantum();
				
				pcb.changeStatus(ProcessStatus.READY_TO_RUNNING);
				currentPCB = pcb;
				executeProcessAtCurrentTime(currentPCB);
				
				break;
			}
		}
		
		return currentPCB;
	}
	
	public void execute() throws ProcessStatusException, FileException, IOException, RoundRobinException{
		long tempoInicial = System.currentTimeMillis();
		
		getFileController().writeFile("Initializating Round Robin");
		System.out.println("Initializating Round Robin");
		
		ProcessControlBlock currentPCB = null;
		int terminatedPCBs = 0;
		
		while(terminatedPCBs != getTotalAllPCBs()) {
			
			if(currentPCB == null || currentPCB.getCurrentStatus() == ProcessStatus.TERMINATED) {
				currentPCB = getAndExecuteNextPCB();
			}
			else if(getCurrentQuantum() == getQuantum()){
				if(currentPCB.getBurstTimeLeft(getCurrentTime()) == 0){
					currentPCB.changeStatus(ProcessStatus.RUNNING_TO_TERMINATED);
					terminatedPCBs++;
				}
				else {
					currentPCB.changeStatus(ProcessStatus.RUNNING_TO_READY);
					sendCurrentPCBToTheEndOfList(currentPCB);
				}
				
				currentPCB = getAndExecuteNextPCB();
			}
			else if(currentPCB != null && currentPCB.getCurrentStatus() == ProcessStatus.RUNNING) {
				if(currentPCB.getBurstTimeLeft(getCurrentTime()) == 0){
					currentPCB.changeStatus(ProcessStatus.RUNNING_TO_TERMINATED);
					terminatedPCBs++;
					
					currentPCB = getAndExecuteNextPCB();
				}
				else{
					executeProcessAtCurrentTime(currentPCB);
				}
			}
			else {
				throw new RoundRobinException("Something went wrong into Round Robin execution, please verify your input!");
			}
			
			//Verifica se algum processo novo chegou
			checkForNewArrivals();
			
			write(getCurrentTime(), currentPCB);
			
			incrementCurrentTime();
			
		}
		
		long tempoFinal = System.currentTimeMillis();
		
		System.out.println("Tempo de execução:");
		getFileController().writeFile("Tempo de execução:");
		System.out.println("- " + getCurrentTime() + " loops.");
		getFileController().writeFile("- " + getCurrentTime() + " loops.");
		System.out.println("- " + (tempoFinal - tempoInicial) + " milisegundos.");
		getFileController().writeFile("- " + (tempoFinal - tempoInicial) + " milisegundos.");
		
		getFileController().closeBuffer();
	}
	
}
