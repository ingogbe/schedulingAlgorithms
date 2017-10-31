package services.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import bo.ProcessControlBlock;
import controller.file.FileController;
import exception.file.FileException;

public class RoundRobin {
	private int quantum;
	private ArrayList<ProcessControlBlock> allArrayPCB;
	private ArrayList<ProcessControlBlock> executingPCBs;
	private int currentTime;
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

	public int getQuantum() {
		return quantum;
	}
	
	public void execute(){
		
	}
}
