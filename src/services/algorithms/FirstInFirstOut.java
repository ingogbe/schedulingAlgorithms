package services.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import bo.ProcessControlBlock;
import bo.ProcessStatus;
import controller.file.FileController;
import exception.file.FileException;
import exception.process.ProcessStatusException;

public class FirstInFirstOut {
	
	private ArrayList<ProcessControlBlock> allArrayPCB;
	private ArrayList<ProcessControlBlock> executingPCBs;
	private int currentTime;
	private FileController ucFile;
	
	public FirstInFirstOut(ArrayList<ProcessControlBlock> allArrayPCB) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileException, IOException {
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

	public void execute() throws ProcessStatusException, FileException, IOException {
		ProcessControlBlock currentPCB = null;
		int terminatedPCBs = 0;
		
		while(terminatedPCBs != allArrayPCB.size()) {
			
			//Pra evitar checagem nova toda hora
			if(this.executingPCBs.size() != allArrayPCB.size()) {
				checkForNewArrivals();
			}	
			
			write(currentTime, currentPCB);
			
			if(currentPCB != null) {
				if(currentPCB.getBurstTimeLeft(getCurrentTime()) == 0){
					currentPCB.changeStatus(ProcessStatus.RUNNING_TO_TERMINATED);
					terminatedPCBs++;
					this.executingPCBs.remove(currentPCB);
				}
				else{
					currentPCB.incrementElapsedTime();
				}
			}
			
			//Se não houver PCB atual ou ela estiver terminada, pega próxima PCB da fila
			if(currentPCB == null || currentPCB.getCurrentStatus() == ProcessStatus.TERMINATED) {
				for(ProcessControlBlock pcb: this.executingPCBs) {
					if(pcb.getCurrentStatus() == ProcessStatus.READY) {
						pcb.changeStatus(ProcessStatus.READY_TO_RUNNING);
						currentPCB = pcb;
						break;
					}
				}
			}
		
			incrementCurrentTime();
		}
		
		ucFile.closeBuffer();
		
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
