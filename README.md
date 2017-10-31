# schedulingAlgorithms

## Exemplo formato do arquivo de entrada:

### FIFO
```
7 // Numero de processos
First In First Out //Algoritmo
1, calculadora.exe, 1, 12, 0 //Processo
2, notepad.exe, 1, 4, 0 //ID, Name, Arrival Time, Burst Time, Priority
3, eclipse.exe, 4, 21, 0
4, chrome.exe, 3, 9, 0
5, firefox.exe, 12, 10, 0
6, sublime_text.exe, 7, 8, 0
7, taskmgr.exe, 9, 13, 0
```

### Round Robin
```
7 // Numero de processos
Round Robin, 4 //Algoritmo, Quantum
1, calculadora.exe, 1, 12, 0 //Processo
2, notepad.exe, 1, 4, 0 //ID, Name, Arrival Time, Burst Time, Priority
3, eclipse.exe, 4, 21, 0
4, chrome.exe, 3, 9, 0
5, firefox.exe, 12, 10, 0
6, sublime_text.exe, 7, 8, 0
7, taskmgr.exe, 9, 13, 0
```