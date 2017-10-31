package services.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import bo.ProcessControlBlock;
import bo.ProcessStatus;
import controller.file.FileController;
import exception.file.FileException;
import exception.process.ProcessStatusException;

public class RoundRobin {
	private int quantum;
	private ArrayList<ProcessControlBlock> allArrayPCB;
	private ArrayList<ProcessControlBlock> executingPCBs;
	private int currentTime;
	private int currentQuantum;
	private FileController ucFile;
	
	public RoundRobin(int quantum, ArrayList<ProcessControlBlock> allArrayPCB) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileException, IOException{
		this.quantum = quantum;
		this.allArrayPCB = allArrayPCB;
		this.executingPCBs = new ArrayList<ProcessControlBlock>();
		this.currentTime = 0;
		
		this.ucFile = new FileController(FileController.CHOOSER_TYPE_SAVE, true);
		this.ucFile.chooseFile();
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
	
	public void incrementCurrentTime(){
		this.currentTime++;
	}
	
	public void incrementCurrentQuantum(){
		this.currentQuantum++;
	}

	public int getCurrentQuantum() {
		return currentQuantum;
	}

	public void setCurrentQuantum(int currentQuantum) {
		this.currentQuantum = currentQuantum;
	}

	public int getQuantum() {
		return quantum;
	}
	
	public int checkForNewArrivals() throws ProcessStatusException {
		int count = 0;
		
		for(ProcessControlBlock pcb: allArrayPCB) {
			if(pcb.isArrivalTime(getCurrentTime())) {
				pcb.changeStatus(ProcessStatus.NEW_TO_READY);
				
				this.executingPCBs.add(pcb);
				count++;
			}
		}
		
		return count;
	}
	
	public void execute() throws ProcessStatusException, FileException, IOException{
		ProcessControlBlock currentPCB = null;
		int terminatedPCBs = 0;
		
		while(terminatedPCBs != allArrayPCB.size()) {
			
			//Pra evitar checagem nova toda hora
			if(this.executingPCBs.size() != allArrayPCB.size()) {
				checkForNewArrivals();
			}	
			
			if(currentPCB == null && this.executingPCBs.size() > 0){
				this.executingPCBs.get(0).changeStatus(ProcessStatus.READY_TO_RUNNING);
				currentPCB = this.executingPCBs.get(0);
				this.currentQuantum = 0;
			}
			else if(currentPCB != null){
				if(currentPCB.getBurstTimeLeft(this.currentTime) == 0){
					currentPCB.changeStatus(ProcessStatus.RUNNING_TO_TERMINATED);
					terminatedPCBs++;
					
					this.executingPCBs.remove(currentPCB);
					currentPCB = getNextPCB(currentPCB);
					
					if(currentPCB != null){
						currentPCB.changeStatus(ProcessStatus.READY_TO_RUNNING);
						this.currentQuantum = 0;
					}
				}
				else if(currentQuantum == quantum){
					currentPCB.changeStatus(ProcessStatus.RUNNING_TO_READY);
					
					currentPCB = getNextPCB(currentPCB);
					
					if(currentPCB != null){
						currentPCB.changeStatus(ProcessStatus.READY_TO_RUNNING);
						this.currentQuantum = 0;
					}
				}
				else{
					currentPCB.incrementElapsedTime();
					incrementCurrentQuantum();
				}
			}
			
			write(this.currentTime, currentPCB);
			incrementCurrentTime();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		ucFile.closeBuffer();
	}
	
	public ProcessControlBlock getNextPCB(ProcessControlBlock currentPCB){
		
		if(this.executingPCBs.size() == 1){
			if(currentPCB.getCurrentStatus() == ProcessStatus.READY){
				return currentPCB;
			}
			
			return null;
		}
		else{
			int number = -1;
			
			for(int i = 0; i < this.executingPCBs.size(); i++){
				if(currentPCB.equals(this.executingPCBs.get(i))){
					number = i;
				}
				
				if(number != -1 && (this.executingPCBs.get(i).getCurrentStatus() == ProcessStatus.READY)){
					return this.executingPCBs.get(i);
				}
			}
			
			if(number != -1){
				for(int i = 0; i < number; i++){
					if(this.executingPCBs.get(i).getCurrentStatus() == ProcessStatus.READY){
						return this.executingPCBs.get(i);
					}
				}
				
				return null;
			}
			else if(currentPCB.getCurrentStatus() == ProcessStatus.READY){
				return currentPCB;
			}
			else{
				return null;
			}
		}
		
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
		
		for(int i = 0; i < this.executingPCBs.size(); i++){
			if(i == this.executingPCBs.size() - 1){
				line = line + this.executingPCBs.get(i).getId();
			}
			else{
				line = line + this.executingPCBs.get(i).getId() + ",";
			}
		}
		
		line = line + "]";
		
		this.ucFile.writeFile(line);
		System.out.println(line);
	}
}
