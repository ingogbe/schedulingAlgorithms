package controller.file;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UnsupportedLookAndFeelException;

import bo.ProcessControlBlock;
import exception.algorithms.FirstInFirstOutException;
import exception.algorithms.RoundRobinException;
import exception.file.FileException;
import exception.process.ProcessStatusException;
import services.algorithms.FirstInFirstOut;
import services.algorithms.RoundRobin;

public class ProcessController {
	
	//Algorithms
	public static final int FIFO = 1;
	public static final int ROUND_ROBIN = 2;
	
	private int algorithm;
	private ArrayList<ProcessControlBlock> arrayPCB;
	
	private FirstInFirstOut fifo;
	private RoundRobin roundRobin;
	
	public ProcessController() {
		this.algorithm = 0;
		this.arrayPCB = new ArrayList<ProcessControlBlock>();
		this.fifo = null;
		this.roundRobin = null;
	}
	
	public int getAlgorithm() {
		return this.algorithm;
	}

	public ArrayList<ProcessControlBlock> getArrayPCB() {
		return arrayPCB;
	}
	
	public void loadFile() throws NumberFormatException, IOException, FileException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		//TODO: Load PCBs from file
		
		FileController fc = new FileController(FileController.CHOOSER_TYPE_LOAD, false);
		fc.chooseFile();
		int numberOfProcesses = Integer.parseInt(fc.readLine());
		
		String algorithmInfo[] = fc.readLine().split(",");
		
		if(algorithmInfo[0].trim().equals("First In First Out")) {
			this.algorithm = FIFO;
		
			for(int i = 0; i < numberOfProcesses; i++){
				String content = fc.readLine();
				
				String processInfo[] = content.split(",");
				ProcessControlBlock pcb = new ProcessControlBlock(
					Integer.parseInt(processInfo[0].trim()), 
					Integer.parseInt(processInfo[4].trim()), 
					processInfo[1].trim(), 
					Integer.parseInt(processInfo[2].trim()), 
					Integer.parseInt(processInfo[3].trim())
				);
				
				arrayPCB.add(pcb);
			}
			
			this.fifo = new FirstInFirstOut(arrayPCB);
		}
		else if(algorithmInfo[0].trim().equals("Round Robin")) {
			this.algorithm = ROUND_ROBIN;
			int quantum = Integer.parseInt(algorithmInfo[1].trim());
			
			for(int i = 0; i < numberOfProcesses; i++){
				String content = fc.readLine();
				
				String processInfo[] = content.split(",");
				ProcessControlBlock pcb = new ProcessControlBlock(
					Integer.parseInt(processInfo[0].trim()), 
					Integer.parseInt(processInfo[4].trim()), 
					processInfo[1].trim(), 
					Integer.parseInt(processInfo[2].trim()), 
					Integer.parseInt(processInfo[3].trim())
				);
				
				arrayPCB.add(pcb);
			}
			
			this.roundRobin = new RoundRobin(quantum, arrayPCB);
			
		}
		else {
			//TODO: Algoritmo não reconhecido
			System.out.println("Algoritmo não reconhecido");
		}
		
		fc.closeBuffer();
	}
	
	public void execute() throws ProcessStatusException, FileException, IOException, RoundRobinException, FirstInFirstOutException {
		if(getAlgorithm() == FIFO){
			this.fifo.execute();
		}
		else if(getAlgorithm() == ROUND_ROBIN){
			this.roundRobin.execute();
		}
		else{
			//TODO: Algoritmo não reconhecido
			System.out.println("Algoritmo não reconhecido");
		}
	}
	
	
	
}
