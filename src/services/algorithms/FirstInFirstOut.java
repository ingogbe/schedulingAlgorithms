package services.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import bo.Algorithm;
import bo.ProcessControlBlock;
import bo.ProcessStatus;
import exception.algorithms.FirstInFirstOutException;
import exception.file.FileException;
import exception.process.ProcessStatusException;

public class FirstInFirstOut extends Algorithm{
	
	public FirstInFirstOut(ArrayList<ProcessControlBlock> allPCB) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileException, IOException {
		super(allPCB);
	}
	
	public ProcessControlBlock getAndExecuteNextPCB() throws ProcessStatusException {
		ProcessControlBlock currentPCB = null;
		
		for(ProcessControlBlock pcb: getExecutingPCBs()) {
			if(pcb.getCurrentStatus() == ProcessStatus.READY) {
				
				pcb.changeStatus(ProcessStatus.READY_TO_RUNNING);
				currentPCB = pcb;
				executeProcessAtCurrentTime(currentPCB);
				
				break;
			}
		}
		
		return currentPCB;
	}
	
	public void executeProcessAtCurrentTime(ProcessControlBlock currentPCB) {
		currentPCB.incrementElapsedTime();
	}

	public void execute() throws ProcessStatusException, FileException, IOException, FirstInFirstOutException {
		long tempoInicial = System.currentTimeMillis();
		
		getFileController().writeFile("Initializating First In First Out");
		System.out.println("Initializating First In First Out");
		
		ProcessControlBlock currentPCB = null;
		int terminatedPCBs = 0;
		
		while(terminatedPCBs != getTotalAllPCBs()) {
			
			//Se não houver PCB atual ou ela estiver terminada, pega próxima PCB da fila
			if(currentPCB == null || currentPCB.getCurrentStatus() == ProcessStatus.TERMINATED) {
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
				throw new FirstInFirstOutException("Something went wrong into First In First Out execution, please verify your input!");
			}
		
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
