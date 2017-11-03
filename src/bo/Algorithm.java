package bo;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import controller.file.FileController;
import exception.file.FileException;
import exception.process.ProcessStatusException;

public class Algorithm {

	private ArrayList<ProcessControlBlock> allPCB;
	private ArrayList<ProcessControlBlock> executingPCBs;
	private int currentTime;
	private FileController fileController;
	
	public Algorithm(ArrayList<ProcessControlBlock> allPCB) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileException, IOException {
		this.allPCB = allPCB;
		this.executingPCBs = new ArrayList<ProcessControlBlock>();
		this.currentTime = 0;
		
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
	
	public void addExecutingPCB(ProcessControlBlock pcb) {
		this.executingPCBs.add(pcb);
	}
	
	public void removeExecutingPCB(ProcessControlBlock pcb) {
		this.executingPCBs.remove(pcb);
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
		
		for(ProcessControlBlock pcb: getAllPCB()) {
			if(pcb.isArrivalTime(getCurrentTime())) {
				pcb.changeStatus(ProcessStatus.NEW_TO_READY);
				
				addExecutingPCB(pcb);
				count++;
			}
		}
		
		return count;
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
