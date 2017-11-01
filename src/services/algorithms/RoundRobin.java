package services.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import bo.ProcessControlBlock;
import bo.ProcessStatus;
import controller.file.FileController;
import exception.algorithms.RoundRobinException;
import exception.file.FileException;
import exception.process.ProcessStatusException;

public class RoundRobin {
	private int quantum;
	private ArrayList<ProcessControlBlock> allPCB;
	private ArrayList<ProcessControlBlock> executingPCBs;
	private int currentTime;
	private int currentQuantum;
	private FileController fileController;
	
	public RoundRobin(int quantum, ArrayList<ProcessControlBlock> allPCB) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileException, IOException{
		this.quantum = quantum;
		this.allPCB = allPCB;
		this.executingPCBs = new ArrayList<ProcessControlBlock>();
		this.currentTime = 0;
		this.currentQuantum = 0;
		
		this.fileController = new FileController(FileController.CHOOSER_TYPE_SAVE, true);
		this.fileController.chooseFile();
	}
	
	public FileController getFileController() {
		return this.fileController;
	}
	
	public ArrayList<ProcessControlBlock> getAllPCB() {
		return this.allPCB;
	}
	
	public int getTotalAllPCBs() {
		return this.allPCB.size();
	}
	
	public int getTotalExecutingPCBs() {
		return this.executingPCBs.size();
	}
	
	public ArrayList<ProcessControlBlock> getExecutingPCBs() {
		return this.executingPCBs;
	}
	
	public ProcessControlBlock getExecutingPCB(int i) {
		return this.executingPCBs.get(i);
	}
	
	public void removeExecutingPCB(ProcessControlBlock pcb) {
		this.executingPCBs.remove(pcb);
	}
	
	public void addExecutingPCB(ProcessControlBlock pcb) {
		this.executingPCBs.add(pcb);
	}
	
	public int getCurrentTime() {
		return this.currentTime;
	}
	
	public void incrementCurrentTime(){
		this.currentTime++;
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
	
	public int checkForNewArrivals() throws ProcessStatusException {
		int count = 0;
		
		for(ProcessControlBlock pcb: getAllPCB()) {
			if(pcb.isArrivalTime(getCurrentTime())) {
				pcb.changeStatus(ProcessStatus.NEW_TO_READY);
				
				addExecutingPCB(pcb);
				count++;
			}
		}
		
		return count;
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
		
		getFileController().closeBuffer();
	}
	
	public void write(int currentTime, ProcessControlBlock currentPCB) throws FileException, IOException{
		
		String line = currentTime + "\t";
		
		if(currentPCB != null){
			line = line + "P(" + currentPCB.getId() + ")";
			
			line = line + "\tT_LEFT[" + currentPCB.getBurstTimeLeft(currentTime) + "]";
		}
		else{
			line = line + "P( )\tT_LEFT[ ]";
		}
		
		line = line + "\tREADY[";
		
		String executingArray = "";
		for(int i = 0; i < getTotalExecutingPCBs(); i++){
			
			if(getExecutingPCB(i).getCurrentStatus() != ProcessStatus.TERMINATED) {
				executingArray = executingArray + getExecutingPCB(i).getId();
			}
			
			if(i != (getTotalExecutingPCBs() - 1) && !executingArray.isEmpty()){
				executingArray = executingArray + ",";
			}
		}
		
		line = line + executingArray + "]";
		
		getFileController().writeFile(line);
		System.out.println(line);
	}
}
