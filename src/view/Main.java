package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import controller.file.ProcessController;
import exception.algorithms.FirstInFirstOutException;
import exception.algorithms.RoundRobinException;
import exception.file.FileException;
import exception.process.ProcessStatusException;

public class Main {
	public static void main(String[] x) {
		
		try {			
			ProcessController pc = new ProcessController();
			pc.loadFile();
			pc.execute();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (FileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProcessStatusException e) {
			e.printStackTrace();
		} catch (RoundRobinException e) {
			e.printStackTrace();
		} catch (FirstInFirstOutException e) {
			e.printStackTrace();
		}
		
	}
}
