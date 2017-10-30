package controller.file;

import java.io.IOException;
import java.util.ArrayList;

import bo.ProcessControlBlock;
import exception.file.FileException;
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
	
	public void loadFile() throws NumberFormatException, IOException, FileException {
		//TODO: Load PCBs from file
		
		FileController fc = new FileController(FileController.CHOOSER_TYPE_LOAD, false);
		int numberOfProcesses = Integer.parseInt(fc.readLine());
		
		String algorithmInfo[] = fc.readLine().split(",");
		
		if(algorithmInfo[0].equals("Fifo")) {
			this.algorithm = FIFO;
			this.fifo = new FirstInFirstOut(allArrayPCB);
		}
		else if(algorithmInfo[0].equals("Round Robin")) {
			this.algorithm = ROUND_ROBIN;
			int quantum = Integer.parseInt(algorithmInfo[1]);
			this.roundRobin = new RoundRobin(quantum);
			
		}
		else {
			
		}
	}
	
	public void execute() {
		//TODO: Chama execução de acordo com algoritmo selecionado
	}
	
	
	
}
