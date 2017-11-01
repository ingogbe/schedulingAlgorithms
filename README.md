# schedulingAlgorithms

## Ambiente usado para implementação e testes

* Sistema Operacional Windows 10
* Eclipse IDE Oxygen
* Eclipse IDE Neon

## Como executar o programa

Abra o Eclipse e adicione o projeto.

1. New
2. Java Project
3. Desmaque o checkbox `Use default location`
4. Browse
5. Selecione a pasta do projeto
6. Finish

Abra o arquivo `Main.java` e execute-o.

```java
ProcessController pc = new ProcessController();
pc.loadFile();
pc.execute();
```

## Algoritmos implementados

1. First In First Out (FIFO)
2. Round Robin (RR)

## Exemplo formato do arquivo de entrada:

Todos os arquivos de entrada seguem o mesmo modelo:

```javascript
numero_de_processos_no_arquivo
algoritmo_a_ser_usado, dados_adicionais_para_o_algoritmo
id_do_processo, nome, tempo_de_chegada, tempo_de_execucao, prioridade
.
.
.
id_do_processo, nome, tempo_de_chegada, tempo_de_execucao, prioridade
```

### FIFO

Exemplo de arquivo de entrada para o algoritmo FIFO.

> Não insira comentários nos arquivos.

```javascript
7 				// Numero de processos
First In First Out, 0 		// Algoritmo desejado
1, calculadora.exe, 1, 12, 0 	// Processo
2, notepad.exe, 1, 4, 0 	// ID, Name, Arrival Time, Burst Time, Priority
3, eclipse.exe, 4, 21, 0
4, chrome.exe, 3, 9, 0
5, firefox.exe, 12, 10, 0
6, sublime_text.exe, 7, 8, 0
7, taskmgr.exe, 9, 13, 0
```

### Round Robin

Exemplo de arquivo de entrada para o algoritmo RR.

> Não insira comentários nos arquivos.

```javascript
7 				// Numero de processos
Round Robin, 4 			// Algoritmo, Quantum
1, calculadora.exe, 1, 12, 0 	// Processo desejado
2, notepad.exe, 1, 4, 0 	// ID, Name, Arrival Time, Burst Time, Priority
3, eclipse.exe, 4, 21, 0
4, chrome.exe, 3, 9, 0
5, firefox.exe, 12, 10, 0
6, sublime_text.exe, 7, 8, 0
7, taskmgr.exe, 9, 13, 0
```