# schedulingAlgorithms

## Ambiente usado para implementação e testes

* Sistema Operacional Windows 10
* Eclipse IDE Oxygen Release (4.7.0)
* Eclipse IDE Neon.1a Release (4.6.1)

## Como executar o programa

Abra o Eclipse e adicione o projeto.

1. New
2. Java Project
3. Desmaque o checkbox `Use default location`
4. Browse
5. Selecione a pasta do projeto
6. Finish

Abra o arquivo `Main.java` e execute-o.

Em seguida será solicitado para que seja selecionado o arquivo de entrada, e logo após o nome e local de onde deseja salvar o arquivo de saída.

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

> Não insira comentários nos arquivos. No caso do FIFO, o atributo do algorimo, quantum, é ignorado.

```javascript
4 				// Numero de processos
First In First Out, 0 		// Algoritmo, Quantum
1, calculadora.exe, 1, 2, 0	// Processo
2, notepad.exe, 6, 7, 0 	// ID, Name, Arrival Time, Burst Time, Priority
3, eclipse.exe, 0, 5, 0
4, chrome.exe, 6, 3, 0
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

## Exemplo formato do arquivo de saída:

Todos os arquivos de saída seguem o mesmo modelo:

> A `fila_atual` contém a fila atual do algoritmo de escalonamento utilizado, sempre com o processo sendo executado a frente.

```javascript
tempo_atual	processo_atual	tempo_restante_processo_atual	fila_atual
.
.
.
tempo_atual	processo_atual	tempo_restante_processo_atual	fila_atual
```

### Exemplo

Exemplo de arquivo de saída, modelo igual a de todos os algoritmos executados.

```javascript
0	P( )	T_LEFT[ ]	READY[3]
1	P(3)	T_LEFT[4]	READY[3,1]
2	P(3)	T_LEFT[3]	READY[3,1]
3	P(3)	T_LEFT[2]	READY[3,1]
4	P(3)	T_LEFT[1]	READY[3,1]
5	P(3)	T_LEFT[0]	READY[3,1]
6	P(1)	T_LEFT[1]	READY[1,2,4]
7	P(1)	T_LEFT[0]	READY[1,2,4]
8	P(2)	T_LEFT[6]	READY[2,4]
9	P(2)	T_LEFT[5]	READY[2,4]
10	P(2)	T_LEFT[4]	READY[2,4]
11	P(2)	T_LEFT[3]	READY[2,4]
12	P(2)	T_LEFT[2]	READY[2,4]
13	P(2)	T_LEFT[1]	READY[2,4]
14	P(2)	T_LEFT[0]	READY[2,4]
15	P(4)	T_LEFT[2]	READY[4]
16	P(4)	T_LEFT[1]	READY[4]
17	P(4)	T_LEFT[0]	READY[4]
18	P( )	T_LEFT[ ]	READY[]
```