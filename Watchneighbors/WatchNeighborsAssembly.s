#*********************************************************
#*******************WATCHNEIGHBORS************************
#*********************************************************
#Author: Marco Di Capua, Filippo Pelosi, Riccardo Zorzi***
#Ver. 1.0.0***********************************************
#*********************************************************

.data

	# allocazione memoria statica per messaggi del menu principale
	messaggioIniziale: .asciiz "Watchneighbors: programma per il controllo del vicinato\n\nL'applicazione permette di registrare fino a 10 utenti, creare una rete quartiere, aggiungere gli utenti registrati alla rete quartiere, visualizzare la rete quartiere, capire se due utenti sono vicini di casa, aggiungere delle segnalazioni e stampare la lista completa delle segnalazioni.\n\nVer. 1.0.0\nAutori: Marco Di Capua, Filippo Pelosi, Riccardo Zorzi\n\n\n"
	msgMenuPrincipale: .asciiz "\n\n***MENU PRINCIPALE***\nSelezionare una tra le opzioni seguenti:\n0-Crea Profilo\n1-Crea Rete Quartiere\n2-Lista Segnalazioni\n3-Aggiungi Segnalazione\n4-Esci\n\n>>>"
	msgMenuPrincipaleScelta0: .asciiz "\n***Pagina per la creazione di un nuovo profilo***\n\n"
	msgMenuPrincipaleScelta1: .asciiz "\n***Pagina per la creazione di una nuova rete quartiere***\n\n"
	msgMenuPrincipaleScelta2: .asciiz "\n***Pagina per visionare la lista delle segnalazioni***\n\n"
	msgMenuPrincipaleScelta3: .asciiz "\n***Pagina per l'aggiunta di una nuova segnalazione***\n\n"
	msgMenuPrincipaleScelta4: .asciiz "\n***Applicazione terminata***\n\n"
	msgSceltaNonValida: .asciiz "Scelta non valida. Ripetere la scelta tra quelle disponibili\n"
	jumpTableMenuPrinc: .word scelta_menu_princ_0,scelta_menu_princ_1,scelta_menu_princ_2,scelta_menu_princ_3,scelta_menu_princ_4,menu_principale_default
	
	# allocazione messaggi per la richiesta di inserimento di un nuovo utente
	msgChiediNome: .asciiz "Inserire nome e cognome (max 40 caratteri): "
	msgChiediLatitudine: .asciiz "Inserire la latitudine: "
	msgChiediLongitudine: .asciiz "Inserire la longitudine: "
	msgRegistrazioneEffettuata: .asciiz "\nUtente registrato correttamente\n"
	msgFineRegistrazione: .asciiz "\nInserire '0' per tornare al menu principale: "
	
	# allocazione memoria statica per messaggi del menu per la creazione e visualizzazione della rete quartiere
	msgChiediNomeReteQuartiere: .asciiz "Inserire un nome per la rete quartiere (max 40 caratteri): "
	msgChiediNumeroUtenti: .asciiz "Inserire il numero utenti della rete quartiere(max 10 utenti): "
	msgChiediUtenteReteQuartiere: .asciiz "Inserire l' utente da aggiungere alla rete quartiere: "
	msgCreazioneReteEffettuata: .asciiz "\nRete quartiere creata correttamente\n"
	msgMenuCreazioneRete: .asciiz "\n\n***MENU CREAZIONE RETE QUARTIERE***\nSelezionare una tra le opzioni seguenti:\n0-Torna al menu principale\n1-Visualizza Rete Quartiere\n2-Vicini Di Casa\n\n>>>"
	msgMenuReteScelta1: .asciiz "\n***Pagina per la visualizzazione della rete quartiere***\n\n"
	msgMenuReteScelta2: .asciiz "\n***Pagina di visualizzazione dei vicini di casa***\n\n"
	jumpTableMenuRete: .word scelta_menu_rete_0,scelta_menu_rete_1,scelta_menu_rete_2,menu_quartiere_default
	visualizzazioneRete: .asciiz "\nRete quartiere: "
	msgDatiPrimoUtente: .asciiz "\nInserire nome e cognome del primo utente: "
	msgDatiSecondoUtente: .asciiz "Inserire nome e cognome del secondo utente: "
	msgUtentiVicini: .asciiz "\nI due utenti inseriti risultano essere vicini di casa"
	msgUtentiNonVicini: .asciiz "\nI due utenti inseriti non risultano essere vicini di casa"
	nuovaRiga: .asciiz "\n"
	costanteTab: .asciiz "\t"
	separatoreCoordinate: .asciiz "/"
	separatoreUtenti: .asciiz "-"
	
	# allocazione memoria statica per messaggi inserimento dati segnalazioni
	msgChiediNomeSegnalazione: .asciiz "Inserire nome e cognome utente che esegue la segnalazione: "
	msgChiediCoordinateSegnalazione: .asciiz "Inserire latitudine/longitudine della segnalazione: "
	msgChiediDataOraSegnalazione: .asciiz "Inserire il timestamp della segnalazione (data e ora): "
	msgChiediMotivazioneSegnalazione: .asciiz "Inserire il motivo della segnalazione (max 40 caratteri): "
	msgChiediStatoSegnalazione: .asciiz "Inserire lo stato della segnalazione (max 40 caratteri): "
	msgSegnalazioneRegistrata: .asciiz "\n\nSegnalazione registrata con successo\n\n"
	msgNessunaSegnalazionePresente: .asciiz "\n\nNessuna segnalazione presente\n\n"
	msgNumeroSegnalazione: .asciiz "\n\nSegnalazione numero: "
	msgNomeEsecutoreSegnalazione: .asciiz "\nUtente che ha eseguito la segnalazione: "
	msgCoordinateSegnalazione: .asciiz "Coordinate della segnalazione: "
	msgDataOraSegnalazione: .asciiz "Data e ora della segnalazione: "
	msgMotivoSegnalazione:	.asciiz "Motivo della segnalazione: "
	msgStatoSegnalazione: .asciiz "Stato della segnalazione: "
	
	#messaggi di errore
	msgLimiteUtentiRaggiunto: .asciiz "Numero utenti registrati massimo raggiunto, non è possibile registrare un nuovo utente"
	msgLimiteUtentiReteiRaggiunto: .asciiz "\n\nNumero inserito non valido\n\n"
	msgNumeroUtentiReteEccessivi: .asciiz "\nIl numero di utenti associabili alla rete non può essere maggiore a quello degli utenti registrati\n\n"
	msgUtenteNonRegistrato: .asciiz "\nL'utente inserito non esiste. "
	msgCreazioneReteNonPermessa: .asciiz "\nNon è possibile creare la rete quartiere perchè non sono presenti utenti registrati\n\n"
	msgUtenteNonAppartieneRete: .asciiz "\nL'utente inserito non appartiene alla rete quartiere\n\n"
	msgReteNonAncoraCreata: .asciiz "Non e' possibile aggiungere segnalazioni perchè non è ancora stata creata la rete quartiere\n\n"
	msgNomeReteNonEsistente: .asciiz "\nIl nome della rete quartiere inserito non esiste. Riprovare.\n\n"

	# allocazione memoria statica per memorizzare i dati degli utenti
	# 52 byte per ciascun utente così suddivisi:
	# 44 nome (formato string) in realtà ne legge poi 40
	# 4 latitudine (formato float 64 bit = 4 byte)
	# 4 longitudine (formato float 64 bit = 4 byte)

	.align 2
	elencoUtenti: .space 520 
	numeroUtentiRegistrati: .byte 0
	
	# allocazione memoria statica per memorizzare gli indirizzi degli utenti della rete di quartiere
	# vengono allocati 4 byte per ciascun utente, ogni word conterrà l'indirizzo di memoria di un utente, per un massimo di 10 utenti, dunque 40 byte totali
	.align 2
	nomeReteQuartiere: .space 40
	reteQuartiere: .space 40
	nuovoIdUtenteRete: .space 44
	numeroUtentiRete: .byte 0
	flagUtentiRete: .space 10 				# array di 10 byte utilizzato per marcare gli utenti da confrontare nella funzione "vicini di casa"
	nomeReteQuartiereInserita: .space 40 	# variabile per memorizzare il nome della rete quartiere da confrontare
	
	# definizione struttura di una segnalazione
	listaSegnalazioni: .word 0			    # variabile per memorizzare l'indirizzo del primo oggetto della lista delle segnalazioni
	# 172 byte totali per memorizzare una segnalazione nello heap
	# 40 byte per contentere nome e cognome dell'utente
	# 24 byte per contenere latitudine e longitudine della segnalazione
	# 24 byte per contenere il timestamp
	# 40 byte per contenere il motivo della segnalazione
	# 40 byte per contenere lo stato della segnalazione
	# 4 byte per contenere l'indirizzo in memoria (heap) della segnalazione successiva 
	
	
	#dichiarazione costanti
	NUMEROMAXUTENTIREGISTRABILI = 10
	OFFSETLATITUDINE = 44
	OFFSETLONGITUDINE = 48
	OFFSETPROSSIMOUTENTE = 52
	
	BYTESFORALARM = 172
	OFFSETALLATLONG = 40
	OFFSETALTIMESTAMP = 64
	OFFSETALMOTIVATION = 88
	OFFSETALSTATUS = 128
	OFFSETALNEXT = 168
	

.text						

.globl main

main:
	la $a0, messaggioIniziale		# stampa intestazione ad avvio programma
	li $v0, 4
	syscall

# stampa del menu principale e attesa inserimento selezione da parte dell'utente	
selezione_iniziale:	
	la $a0, msgMenuPrincipale		# stampa menu principale
	li $v0, 4
	syscall
	
	li $v0, 5			        	# attende e legge la scelta dell'utente
	syscall
	move $s0, $v0			        # indice della selezione in $s0
	
									        # verifica che la scelta sia valida (compresa tra 0 e 4)
	move $a0, $v0					        # $a0 = valore inserito dall'utente				
	li $a1, 4						        # $a1 = 4 : valore limite per questo menu di scelta
	jal scelta_del_menu_corretta            # richiama la funzione per verificare che la scelta sia corretta
	bnez $v0, selezione_iniziale_corretta	# se la scelta è corretta (valore di ritorno della funzione diverso da 0) va allo step successivo
	la $a0, msgSceltaNonValida			    # altrimenti stampa un messaggio di errore e torna alla richiesta di selezione iniziale 
	li $v0, 4
	syscall
	j selezione_iniziale
	
selezione_iniziale_corretta:
	sll $s0, $s0, 2			      # $s0 = $s0 * 4
	la $t0, jumpTableMenuPrinc	  # indirizzo della tabella di salto del menu principale in $t0
	add $s0, $s0, $t0		      # si porta all'indirizzo corretto dell'indice della tabella 
	lw $s0, 0($s0)			      # carica il valore dell'etichetta corrispondente all'indirizzo di destinazione
	jr $s0					      # salta all'etichetta corrispondente

#**************************************************************************
#**Gestione creazione di un nuovo profilo (scelta 0 del menu principale)***
#**************************************************************************
scelta_menu_princ_0:
	la $a0, msgMenuPrincipaleScelta0 		# stampa messaggio di ingresso nella pagina di creazione di un nuovo profilo
	li $v0, 4
	syscall
	
	la $s0, numeroUtentiRegistrati			# verifica se il numero utenti registrati ha già raggiunto il limite massimo  
	lbu $s1, 0($s0)							# carica in $s1 il numero di utenti già registrati
	li $s2, NUMEROMAXUTENTIREGISTRABILI
	beq $s1, $s2, limite_utenti_raggiunto	# se il limite è stato raggiunto va a stampare il relativo messaggio 
											# altrimenti procede all'inserimento del nuovo utente.
	la $s3, elencoUtenti 					# per prima cosa carica l'indirizzo di partenza dell'elenco utenti in $s3
	li $s4, OFFSETPROSSIMOUTENTE			# salva in s4 il numero di byte che occupa ogni registrazione
	mul $s4, $s4, $s1						# calcola l'offset totale in modo da sapere qual'è la prima locazione libera
	add $s4, $s4, $s3						# calcola il primo indirizzo disponibile per la registrazione, l'indirizzo viene salvato in $s4
	move $s5, $s4							# utilizza $s5 come registro di ripristino per riportare successivamente $s4 al valore originale
	
	la $a0, msgChiediNome 					    # richiesta di inserimento del nome. Stampa il messaggio per l'inserimento
	li $v0, 4
	syscall
	move $a0, $s4							# legge il dato inserito e lo salva all'indirizzo corretto salvato in $s4 precedentemente
	li $a1, 44								# 44 sono i caratteri dedicati al nome
	li $v0, 8
	syscall
	
	move $a0, $s4							    # carica l'indirizzo della stringa salvata in a0
	jal rimuovi_nuova_riga						# richiama la funzione per l'eliminazione dell'eventuale carattere di nuova linea dalla stringa
		
	addi $s4, OFFSETLATITUDINE				        # si sposta all'indirizzo che deve contenere la latitudine	
	la $a0, msgChiediLatitudine					    # richiesta di inserimento della latitudine. Stampa il messaggio per l'inserimento
	li $v0, 4
	syscall
	li $v0, 6
	syscall
	swc1 $f0, 0($s4)						# salva la latitudine in memoria, all'indirizzo corretto (a cui punta $s4)
	move $s4, $s5							# ripristina $s4 con il valore di partenza, ovvero quello dell'inizio della registrazione dell'utente
	addi $s4, OFFSETLONGITUDINE				# si sposta all'indirizzo che deve contenere la longitudine
	
	la $a0, msgChiediLongitudine		    # richiesta di inserimento della longitudine. Stampa il messaggio per l'inserimento
	li $v0, 4
	syscall
	li $v0, 6
	syscall
	swc1 $f0, 0($s4)						# salva la longitudine in memoria, all'indirizzo corretto (a cui punta $s4)
	
	addi $s1, $s1, 1						# incrementa la variabile contatore che contiene il numero utenti registrati
	sw $s1, 0($s0)
		
	la $a0, msgRegistrazioneEffettuata		# stampa un messaggio per indicare che la registrazione è andata a buon fine
	li $v0, 4								
	syscall
	
	jal menu_con_sola_scelta_zero			# richiama la funzione che mostra la sola scelta zero e restituisce l'indirizzo del menu principale
	jr $v0
	
limite_utenti_raggiunto:							# se il numero massimo di utenti registrati è stato raggiunto, stampa il relativo messaggio e torna
	la $a0, msgLimiteUtentiRaggiunto				# al menu principale
	li, $v0, 4
	syscall	
	j selezione_iniziale

#**********************************************************************************
#**Gestione creazione di una nuova rete quartiere (scelta 1 del menu principale)***	
#**********************************************************************************
scelta_menu_princ_1:
	# per prima cosa verifica se sono stati registrati degli utenti, altrimenti non è possibile creare una rete quartiere
	la $t0, numeroUtentiRegistrati		        # carica in $t0 l'indirizzo di memoria dove è presente il numero degli utenti registrati
	lb $t1, 0($t0)						        # carica in $t1 il valore del numero degli utenti registrati
	bnez $t1, registrazione_utente_corretta		# se il valore degli utenti registrati è diverso da 0 continua il processo di creazione del quartiere
	la $a0, msgCreazioneReteNonPermessa	        # altrimenti visualizza un messagio di errore e torna al menu principale
	li $v0, 4
	syscall
	j selezione_iniziale

registrazione_utente_corretta:						
	la $a0, msgMenuPrincipaleScelta1 		# stampa messaggio di ingresso nella pagina di creazione di una nuova rete quartiere
	li $v0, 4
	syscall
	
	la $a0, msgChiediNomeReteQuartiere		# stampa messaggio per l'inserimento del nome per la rete quartiere
	li $v0, 4
	syscall
	
	la $a0, nomeReteQuartiere		        # memorizza il nome della rete quartiere inserito dall'utente nell'apposita variabile
	li $a1, 40
	li $v0, 8
    syscall
	
inserisci_numeri_utenti_rete:	
	la $a0, msgChiediNumeroUtenti 		    # stampa messaggio per l'inserimento della quantità degli utenti della rete quartiere
	li $v0, 4
	syscall
	
	li $v0, 5						        # chiamata di sistema per la lettura di un intero inserito dall'utente
	syscall
	
	#verifica che la quantità degli utenti da associare alla rete sia conforme (compresa tra 1 e 10) e non superiore al totale degli utenti registrati
	move $s0, $v0							    # sposta la quantità inserita dall'utente in $s0. Deve verificare che sia compresa tra 1 e 10
	blez $s0, errore_numero_utenti_rete	        # se il numero inserito ($s0) è minore o uguale a 0 l'inserimento non è corretto e salta a segnalare l'errore
	addi, $s1, $s0, -10						    # sottrae 10 a $s0 e lo salva in $s1
	bgtz $s1, errore_numero_utenti_rete	        # se $s1 è maggiore di 0 il numero inserito è ok  non è corretto e visualizza il retativo messaggio di errore
	la $t0, numeroUtentiRegistrati			    # carica il numero di utenti registrati in $s1
	lb $s1, 0($t0)
	sub $s1, $s1, $s0 						    # sottrae al numero di utenti registrati il numero di utenti da associare alla rete
	bgez $s1, numero_utenti_rete_corretto		# se il numero trovato è maggiore o uguale a 0 allora la guantità è valida e procede con la creazione della rete
    la $a0, msgNumeroUtentiReteEccessivi		# altrimenti visualizza il relativo messaggio di errore e torna all'inserimento della quantità
	li $v0, 4
	syscall
	j inserisci_numeri_utenti_rete			    # torna al messaggio di inserimento del numero utenti per la rete di quartiere
	
errore_numero_utenti_rete:
	la $a0, msgLimiteUtentiReteiRaggiunto 	    # altrimenti stampa messaggio per informare che il dato inserito non è valido
	li $v0, 4
	syscall
	j inserisci_numeri_utenti_rete			    # torna al messaggio di inserimento del numero utenti per la rete di quartiere
	
numero_utenti_rete_corretto:
	# procede con l'associazione degli utenti alla rete quartiere
	la $s1, numeroUtentiRete			    # carica in $s1 l'indirizzo dove salvare il numero utenti per la rete quartiere
	sb $s0, 0($s1)						    # memorizza nella variabile indicizzata da 'numeroUtentiRete' il numero inserito dall'utente
	
	move $a0, $s0						    # carica in $a0 il numero degli utenti che faranno parte della rete quartiere
	la $a1, reteQuartiere				    # carica in $a1 l'indirizzo dove salvare gli utenti appartenenti alla rete quartiere
	jal costruisci_rete_utenti				# richiama la procedura per l'inserimento degli utenti nella rete quartiere
	
	la $a0, msgCreazioneReteEffettuata		# stampa un messaggio per informare che la rete quartiere è stata creata correttamente
	li $v0, 4
	syscall

menu_rete:	
	la $a0, msgMenuCreazioneRete			# stampa menu della rete quartiere
	li $v0, 4
	syscall
	
	li $v0, 5					            # attende e legge la scelta dell'utente
	syscall
	move $s0, $v0				            # indice della selezione in $s0
	
										    # verifica che la scelta sia valida (compresa tra 0 e 2)
	move $a0, $v0						    # $a0 = valore inserito dall'utente				
	li $a1, 2							    # $a1 = 4 : valore limite per questo menu di scelta
	jal scelta_del_menu_corretta			# richiama la funzione per verificare che la scelta sia corretta
	bnez $v0, scelta_menu_rete_corretta	    # se la scelta è corretta (valore di ritorno della funzione diverso da 0) va allo step successivo
	la $a0, msgSceltaNonValida				# altrimenti stampa un messaggio di errore e torna alla richiesta di selezione del menu rete quartiere
	li $v0, 4
	syscall
	j menu_rete

scelta_menu_rete_corretta:	
	sll $s0, $s0, 2				# $s0 = $s0 * 4
	la $t0, jumpTableMenuRete	# indirizzo della tabella di salto del menu principale in $t0
	add $s0, $s0, $t0			# si porta all'indirizzo corretto dell'indice della tabella 
	lw $s0, 0($s0)				# carica il valore dell'etichetta corrispondente all'indirizzo di destinazione
	jr $s0						# salta all'etichetta corrispondente

#**********************************************************************************
#**Gestione ritorno al menu principale (scelta 0 del menu della rete quartiere))***	
#**********************************************************************************	
scelta_menu_rete_0:
	j selezione_iniziale

#***********************************************************************************
#**Gestione stampa della rete quartiere (scelta 1 del menu della rete quartiere))***	
#***********************************************************************************	
scelta_menu_rete_1:
	la $a0, msgMenuReteScelta1
	li $v0, 4
	syscall

	la $a0, reteQuartiere				    # carica in $a0 l'indirizzo dove è presente la rete quartiere (l'array degli utenti, non il nome)
	jal stampa_rete_quartiere				# richiama la procedura per la stampa della rete quartiere
	
	j menu_rete

#*********************************************************************************
#**Gestione stampa dei vicini di casa (scelta 2 del menu della rete quartiere))***	
#*********************************************************************************
scelta_menu_rete_2:
	la $a0, msgMenuReteScelta2			    # stampa del messaggio iniziale per avvertire dell'ingresso nella funzione
	li $v0, 4
	syscall
											# inizializzazione array per marcare gli utenti di rete da confrontare. Tutti i valori vengono messi a 1
	la $t0, flagUtentiRete					# $t0 = indirizzo array con i valori da inizializzare
	li $t1, 10								# $t1 = 10 : indice contatore (l'array è composto da 10 elementi, numero massimo di utenti)
loop_init_user_flag:
	li $t2, 1
	sb $t2, 0($t0)
	addi $t0, $t0, 1						# incrementa il puntatore al valore dell'array
	addi $t1, $t1, -1 						# decrementa il contatore
	bnez $t1, loop_init_user_flag			# se il contatore non è ancora arrivato a 0, torna all'inizio del loop per inizializzare il successivo 
											# elemento
	
											# inserimento e verifica del primo utente da confrontare
	la $a0, msgDatiPrimoUtente				# carica l'indirizzo della stringa che contiene il messaggio per l'inserimento del primo utente in $a0
	la $a1, reteQuartiere					# carica l'indirizzo della rete quartiere in $a1
	jal inserisci_e_verifica_vicino			# richiama la funzione che gestisce l'inserimento e verifica dell'utente
	move $s0, $v0							# salva l'indirizzo del primo utente in $s0
	
											# inserimento e verifica del secondo utente da confrontare
	la $a0, msgDatiSecondoUtente				# carica l'indirizzo della stringa che contiene il messaggio per l'inserimento del primo utente in $a0
	la $a1, reteQuartiere					# carica l'indirizzo della rete quartiere in $a1
	jal inserisci_e_verifica_vicino			# richiama la funzione che gestisce l'inserimento e verifica dell'utente
	move $s1, $v0							# salva l'indirizzo del secondo utente in $s1
	
											# verifica se i due utenti inseriti sono vicini di casa
	move $a0, $s0							# $a0 = $s0: indirizzo primo utente
	move $a1, $s1							# $a1 = $s1: indirizzo secondo utente
	la $a2, reteQuartiere					# $a2 = indirizzo rete quartiere
	jal sono_utenti_vicini					# richiama la funzione per verificare se i due utenti sono vicini
	beqz $v0, non_sono_utenti_vicini 					# se il valore di ritorno è 0 i due utenti non sono vicini. Procede stampando il messaggio relativo	
	
	la $a0, msgUtentiVicini				    # altrimenti stampa il messaggio che gli utenti sono vicini
	li $v0, 4
	syscall
	j fine_sezione_vicini
	
non_sono_utenti_vicini:
	la $a0, msgUtentiNonVicini			    # stampa messaggio che gli utenti non sono vicini
	li $v0, 4
	syscall
fine_sezione_vicini:	
	j menu_rete
		
menu_quartiere_default:
	# se la scelta dell'utente non è corretta stampa un messaggio di avvertimento e torna al menu di selezione della rete quartiere	
	la $a0, msgSceltaNonValida
	li $v0, 4
	syscall
	j menu_rete
	
#*****************************************************************************
#**Gestione stampa lista delle segnalazioni (scelta 2 del menu principale))***	
#*****************************************************************************		
scelta_menu_princ_2:
	la $a0, msgMenuPrincipaleScelta2 		# stampa messaggio di ingresso nella pagina per la stampa della lista delle segnalazioni
	li $v0, 4
	syscall
	
	la $a0, listaSegnalazioni		        # $a0 = indirizzo della variabile che contiene l'indirizzo della lista delle segnalazioni
	jal stampa_lista_segnalazioni			# richiama la funzione per la stampa della lista delle segnalazioni
	
	jal menu_con_sola_scelta_zero		    # richiama la funzione per la stampa del menu con la sola selezione dello zero. Restituisce l'indirizzo del main menu
	jr $v0

#*******************************************************************************
#**Gestione aggiunta di una nuova segnalazione (scelta 3 del menu principale)***
#*******************************************************************************		
scelta_menu_princ_3:
	la $a0, msgMenuPrincipaleScelta3 		# stampa messaggio di ingresso nella pagina per l'inserimento di una nuova segnalazione
	li $v0, 4
	syscall
	
									    # per prima cosa verifica che il numero utenti rete non sia uguale a 0, indice che 
									    # la rete quartiere non è ancora stata creata
	la $t0, numeroUtentiRete
	lb $t1, 0($t0)					    # $t1 = numero utenti rete
	beqz $t1, rete_non_ancora_creata	# se il numero utenti rete è uguale a 0 va a 'rete_non_ancora_creata'

inserisci_nome_rete:	
	la $a0, visualizzazioneRete		    # se invece la rete esiste richiede all'utente l'inserimento del nome della rete
	li $v0, 4
	syscall
	
	la $a0, nomeReteQuartiereInserita	# salva il nome della rete quartiere inserita in 'nomeReteQuartiereInserita'
	li $a1, 40
	li $v0, 8
	syscall
										# verifica che il nome della rete quartiere inserito sia corretto
	la $a0, nomeReteQuartiere			# carica gli indirizzi delle stringhe in $a0 e $a1 e richiama la funzione per il confronto
	la $a1, nomeReteQuartiereInserita
	jal confronta_stringhe
	beqz $v0, nome_rete_non_corretto	# se il nome della rete inserito non è corretto va a 'nome_rete_non_corretto'
										# altrimenti procede con la registrazione di una nuova segnalazione
	la $a0, listaSegnalazioni			# carica l'indirizzo in memoria della lista delle segnalazioni
	jal aggiungi_nuova_segnalazione					# richiama la procedura per l'inserimento di una nuova segnalazione
	
	jal menu_con_sola_scelta_zero		# richiama la funzione per la stampa del menu con la sola selezione dello zero. Restituisce l'indirizzo del main menu
	jr $v0
 
nome_rete_non_corretto:					# se il nome della rete inserito non è corretto stampa un messaggio di errore
	la $a0, msgNomeReteNonEsistente
	li $v0, 4
	syscall
	j inserisci_nome_rete				# torna alla richiesta di inserimento del nome della rete quartiere
 
rete_non_ancora_creata:					# se non esiste ancora la rete quartiere visualizza un messaggio di errore
	la $a0, msgReteNonAncoraCreata
	li $v0, 4
	syscall
	j selezione_iniziale

#*********************************************************************
#**Gestione uscita dall'applicazione (scelta 4 del menu principale)***
#*********************************************************************
scelta_menu_princ_4:
	la $a0, msgMenuPrincipaleScelta4 		# stampa messaggio per informare che l'applicazione verrà terminata
	li $v0, 4
	syscall
			
	li $v0, 10						        # termina l'applicazione con la chiamata di sistema
	syscall

###Gestione selezione non corretta nel menu di scelta	
menu_principale_default:
	la $a0, msgSceltaNonValida			    # stampa messaggio di ingresso nella pagina per la stampa della lista delle segnalazioni
	li $v0, 4
	syscall
	j selezione_iniziale

#********************************************************FUNZIONI***************************************************************************
#*******************************************************************************************************************************************

#***************************************************
#**Funzione per la creazione della rete quartiere***
#***************************************************
#In ingresso accetta il numero di utenti da aggiungere alla rete e l'indirizzo della struttura dati per il salvataggio della rete

costruisci_rete_utenti:
	addi $sp, $sp, -8	# memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	
	move $s0, $a0		# salva in $s0 il numero degli utenti da registrare
	move $s1, $a1		# salva in $s1 l'indirizzo di partenza per la memorizzazione degli indirizzi appartenenti alla rete quartiere
	move $s2, $zero		# utilizza $s2 come contatore per il numero di utenti già inseriti nella rete
	
loop_inserisci_utente_nella_rete:
	la $a0, msgChiediUtenteReteQuartiere		# stampa il messaggio per l'inserimento dell'utente
	li $v0, 4 
	syscall
	
	la $a0, nuovoIdUtenteRete		# memorizza l'utente inserito in 'nuovoIdUtenteRete'
	li $a1, 44						
	li $v0, 8
	syscall
	
	la $a0, nuovoIdUtenteRete
	jal rimuovi_nuova_riga						# richiama la funzione per l'eliminazione dell'eventuale carattere di nuova linea dalla stringa

	#verifica se l'id che deve essere inserito nella rete è presente nell'elenco con gli utenti registrati
	la $s3, elencoUtenti			# carica in $s3 indirizzo di partenza dell'elenco degli utenti registrati
	move $s4, $zero					# utilizzo $s4 come contatore per capire se ha già passato tutti gli utenti nell'elenco di quelli registrati
	la $t0, numeroUtentiRegistrati	# carica in $s5 il numero degli utenti registrati
	lb $s5, 0($t0)
	
	#loop per la verifica della presenza dell'utente inserito all'interno dell'elenco
loop_verifica_presenza_utente:	
	move $a0, $s3								 # carica in $a0 l'indirizzo dove è presente il nome dell'utente in esame
	la $a1, nuovoIdUtenteRete					 # carica in $a1 l'indirizzo dove è presente il nome dell'utente da inserire nella rete
	jal confronta_stringhe						 # richiama la funzione per il confronto di due stringhe, per verificare se corrispondono
	bnez $v0, utente_corretto_per_rete			 # se l'utente inserito esiste procede con l'associazione dell'id alla rete, altrimenti
	addi $s4, $s4, 1							 # incrementa il contatore che tiene traccia del numero di utenti già verificati nell'elenco di quelli registrati
	addi $s3, $s3, OFFSETPROSSIMOUTENTE			 # incrementa il puntatore all'indirizzo che contiene l' utente di cui verificare il nome
	bne $s4, $s5, loop_verifica_presenza_utente	 # se il contatore non ha ancora raggiunto il numero degli utenti registrati torna a verificare il prossimo utente
	la $a0, msgUtenteNonRegistrato				 # altrimenti stampa un messaggio che informa che l'utente inserito non esiste
	li $v0, 4
	syscall
	j loop_inserisci_utente_nella_rete			 # e torna all'inserimento dell'id dell'utente da associare alla rete
	
utente_corretto_per_rete:
	# se l'utente inserito è corretto associa l'indirizzo dove è linkato l'utente in memoria nell'array che contiene la rete di quartiere
	sw $s3, 0($s1)								     # indirizzo utente nella rete quartiere
	addi $s1, $s1, 4							     # incrementa il puntatore della rete quartiere in modo da puntare al prossimo elemento da salvare
	addi $s2, $s2, 1							     # incrementa il contatore che contiene il numero degli utenti già registrati nella rete quartiere
	bne $s2, $s0, loop_inserisci_utente_nella_rete 	 # se il numero degli utenti nella rete non ha ancora raggiunto la quantità torna ad aggiungere un altro utente
	
	lw $fp, 4($sp)								     # ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8
	jr $ra										     # altrimenti la procedura di creazione della rete quartiere è terminata e torna al chiamante
	
	
#***********************************************	
#**Funzione per il confronto tra due stringhe***
#***********************************************
#Come argomenti riceve gli indirizzi delle due stringhe da confrontare, in uscita restrituisce 1 se sono uguali, 0 altrimenti
	
confronta_stringhe:
	addi $sp, $sp, -8				     # memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	
	move $t0, $a0					    # indirizzo di partenza della stringa 1 in $t0
	move $t1, $a1					    # indirizzo di partenza della stringa 2 in $t1
loop_confronta_stringhe:
	lbu $t2, 0($t0)					    # carica un carattere della stringa 1 in $t2
	lbu $t3, 0($t1)					    # carica un carattere della stringa 2 in $t3
	bne $t2, $t3, stringa_non_uguale	# verifica se i due valori non sono uguali, nel caso va "stringa_non_uguale"
	beqz $t2, stringa_uguale			# se valore in $t2 è uguale a 0 la stringa è terminata e va a "stringa_uguale"
	addi $t0, $t0, 1				    # incrementa i registri con gli indirizzi delle stringhe
	addi $t1, $t1, 1
	j loop_confronta_stringhe
stringa_non_uguale:
	li $v0, 0						    # se le stringhe non sono uguali carica il valore di ritorno 0
	j fine_confronto_stringhe			# salta al termine della procedura
stringa_uguale:	
	li $v0, 1						    # se le stringhe sono uguali carica il valore di ritorno 1
fine_confronto_stringhe:
	lw $fp, 4($sp)					    # ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8	
	jr $ra							    #ritorna al chiamante	

#*********************************************************************************************		
#**Funzione per la stampa della rete quartiere************************************************
#*********************************************************************************************
#Come argomento riceve l'indirizzo della rete quartiere da stampare

stampa_rete_quartiere:
	addi $sp, $sp, -8					# memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	
	move $t0, $a0					 	# carica l'indirizzo della rete di quartiere, passato come argomento, in t0
	la $a0, visualizzazioneRete		    # stampa la stringa che annuncia il nome della rete quartiere 
	li $v0, 4
	syscall								# dopo questa istruzione stampa il nome della rete quartiere
	addi $a0, $t0, -40					# sottrae 40 all'indirizzo della rete quartiere per avere l'indirizzo dove è contenuto il nome della rete quartiere
	li $v0, 4
	syscall
	
	la $t9, numeroUtentiRete			# carica in t9 l'indirizzo dove è presente il numero degli utenti registrati nella rete
	lb $t1, 0($t9)						# carica il numero utenti della rete in t1
	addi $t2, $t1, -1					# decrementa di 1 t1 (numero utenti rete) e salva in t2 per verificare se il numero utenti è uguale a 1
	bnez $t2, rete_utente_maggiore_di_uno  # se la rete ha più di un utente va all'etichetta indicata
	
										# se è presente un solo utente stampa il nome e le coordinate separate da separatore
	la $t9, reteQuartiere				# recupera l'indirizzo dove è salvato l'indirizzo dell'utente e lo salva in t9	
	lw $a0, 0($t9)						# carica il valore puntato da t9 che rappresenta l'indirizzo dell'utente da stampare	
	li $v0, 4
	syscall								# stampa del nome dell'utente
	
	move $t2, $a0
	addi $t2, $t2, OFFSETLATITUDINE		# stampa della latitudine: usa t2 da supporto per salvare l'indirizzo in cui è contenuta la latitudine
	lwc1 $f12, 0($t2)
	li $v0, 2
	syscall
	
	move $t2, $a0						# ripristina in t2 il valore di a0, che al momento è l'indirizzo dell'unico utente della rete
	la $a0, separatoreCoordinate				# stampa del separatore tra le due coordinate
	li $v0, 4
	syscall
	
	addi $t2, $t2, OFFSETLONGITUDINE	# stampa della longitudine: usa t2 da supporto per salvare l'indirizzo in cui è contenuta la longitudine
	lwc1 $f12, 0($t2)
	li $v0, 2
	syscall
	
	j fine_stampa_rete_quartiere		# salta alla fine della procedura
	
rete_utente_maggiore_di_uno:			# sezione per il confronto delle coordinate di tutti gli utenti con relativa stampa a video
	la $t9, numeroUtentiRete
	lb $t1, 0($t9)						# t1 = numero utenti rete
	la $t6, reteQuartiere				# t6 = indirizzo rete quartiere
	li $t4, -1							# t4 = -1; utilizza t4 come indice per il primo utente in esame
	
	loop_rumdu_esterno:					# loop esterno che di volta in volta incrementa il primo utente della rete che verrà confrontato con tutti i successivi
		addi $t4, $t4, 1				# incrementa l'indice del primo utente. Al primo giro avrà valore 0, essendo inizializzato a -1
		sll $t9, $t4, 2					# t9 = t4 * 4: moltiplica per 4 l'indice del primo utente per calcolare l'offset nell'array con gli utenti di rete
		add $t9, $t9, $t6				# t9 = t6 + t9: calcola l'indirizzo dell'utente a partire dall'indirizzo di inizio dell'array
		lw $t2, 0($t9)					# recupera l'indirizzo del primo utente
		move $t5, $t4					# t5 = t4; t5 viene utilizzato come indice per il secondo utente da confrontare
		loop_rumdu_interno:				# loop interno per far scorrere l'indice del secondo utente che deve essere confrontato con il primo
			addi $t5, $t5, 1			# incrementa di 1 l'indice per il secondo utente (t5)
			sll $t9, $t5, 2				# t9 = t5 * 4: moltiplica per 4 l'indice del secondo utente per calcolare l'offset nell'array con gli utenti di rete
			add $t9, $t9, $t6			# t9 = t6 + t9: calcola l'indirizzo dell'utente a partire dall'indirizzo di inizio dell'array
			lw $t3, 0($t9)				# recupera l'indirizzo del secondo utente
										 
			move $a0, $t2				# stampa del nome del primo utente
			li $v0, 4
			syscall
			la $a0, separatoreUtenti	# stampa del separatore tra i nomi degli utenti ('-')
			li $v0, 4
			syscall
			move $a0, $t3				# stampa del nome del secondo utente
			li $v0, 4
			syscall
			la $a0, costanteTab			# stampa del carattere di tabulazione per separare i nomi dalle coordinate
			li $v0, 4
			syscall
												                # calcolo e stampa del valore di latitudine tra il primo e il secondo utente
			addi $t7, $t2, OFFSETLATITUDINE		                # ulilizza t7 per salvare l'indirizzo della latitudine del primo utente
			addi $t8, $t3, OFFSETLATITUDINE		                # ulilizza t8 per salvare l'indirizzo della latitudine del secondo utente
			lwc1 $f12, 0($t7)					                # carica in f12 la latitudine del primo utente
			lwc1 $f14, 0($t8)					                # carica in f14 la latitudine del secondo utente
			jal sottrazzione_valore_assoluto_float				# richiama la funzione per calcolare il valore assoluto della differenza delle due coordinate
			mov.s $f12, $f0						                # carica il valore di ritorno in f12 e stampa il risultato a video
			li $v0, 2
			syscall
			
			la $a0, separatoreCoordinate				# stampa del separatore tra le due coordinate ('/')
			li $v0, 4
			syscall			
												                # calcolo e stampa del valore di longitudine tra il primo e il secondo utente
			addi $t7, $t2, OFFSETLONGITUDINE	                # ulilizza t7 per salvare l'indirizzo della longitudine del primo utente
			addi $t8, $t3, OFFSETLONGITUDINE	                # ulilizza t8 per salvare l'indirizzo della longitudine del secondo utente
			lwc1 $f12, 0($t7)					                # carica in f12 la longitudine del primo utente
			lwc1 $f14, 0($t8)					                # carica in f14 la longitudine del secondo utente
			jal sottrazzione_valore_assoluto_float				# richiama la funzione per calcolare il valore assoluto della differenza delle due coordinate
			mov.s $f12, $f0						                # carica il valore di ritorno in f12 e stampa il risultato a video
			li $v0, 2
			syscall
			
			la $a0, nuovaRiga						# va a capo
			li $v0, 4
			syscall
												# verifica se l'indice del secondo utente ha già raggiunto il numero di utenti della rete 
			addi $t9, $t1, -1					# decrementa il numero utenti e salva il risultato in t9 (serve perchè l'indice degli utenti è a base 0)
			bne $t9, $t5, loop_rumdu_interno			# confronta l'indice del secondo utente con il numero degli utenti -1 e se non sono uguali prosegue con
												# tornando all'inizio del loop interno, dove verrà assegnato al secondo utente, l'utente successivo
												
		addi $t9, $t1, -2						# se invece l'indice del secondo utente ha già raggiunto il limite, verifica che il primo utente non lo abbia
		bne $t9, $t4, loop_rumdu_esterno				# raggiunto. In questo caso il numero degli utenti viene decrementato di 2: una unità serve sempre perchè
												# l'indice degli utenti è a base zero. La seconda unità invece è perchè, per eseguire un confronto, deve 
												# esserci almeno un utente successivo al primo.
	
fine_stampa_rete_quartiere:	
	lw $fp, 4($sp)								# ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8	
	jr $ra		
	
	
#*********************************************************************************************		
#**Funzione per calcolare il valore assoluto della sottrazione tra due valori di tipo float***
#*********************************************************************************************	
#Come argomenti riceve i valori float, restituisce il valore assoluto della differenza tra i due numeri

sottrazzione_valore_assoluto_float:
	addi $sp, $sp, -8				# memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	
	c.lt.s $f12, $f14				                # verifica se $f12 è minore di $f14 (i due registri contengono i valori da sottrarre passati come argomento alla funzione)
	bc1t f14_maggiore_di_f12		                # se $f12 è minore a $f14 va a sottrarre $f14 a $f12
	sub.s $f0, $f12, $f14			                # altrimenti sottrae $f12 a $f14 e salva il risulta in $f0
	j fine_sottrazzione_valore_assoluto_float
	
f14_maggiore_di_f12:
	sub.s $f0, $f14, $f12			# sottrae $f14 a $f12 e salva il risulta in $f0
	
fine_sottrazzione_valore_assoluto_float:
	lw $fp, 4($sp)					# ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8	
	jr $ra	
	

#****************************************************************************		
#**Funzione per l'eliminazione del carattere di nuova linea da una stringa***
#****************************************************************************	
#Come argomento riceve l'indirizzo della stringa da elaborare

rimuovi_nuova_riga:
	addi $sp, $sp, -8				# memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	
	addi $t0, $a0, -1			        # si porta 1 byte prima dell'inizio della stringa da analizzare					
loop_rimuovi_nuova_riga:  
	addi $t0,$t0,1      		        # incrementa l'indirizzo che determina il carattere della stringa indicizzato 
    lb $t1,0($t0)    			        # carica il primo carattere della stringa in t1    
	addi $t2, $t1, -10			        # sottrae 10 al carattere caricato. Se il carattere è quello di nuova linea, t2 va a zero
	beqz $t2, sostituisci_nuova_riga	# se il carattere è quello di nuova linea, va a rimpiazzare il valore con il carattere null
	bnez $t1, loop_rimuovi_nuova_riga	# se non è ancora arrivato al carattere di terminazione incrementa l'indirizzo e prosegue con la verifica
	j fine_rimuovi_nuova_riga			# se la stringa è terminata prosegue saltando alla parte di codice dopo la sostituzione del carattere di nuova linea
sostituisci_nuova_riga:
	sb $zero, 0($t0)
fine_rimuovi_nuova_riga:
	lw $fp, 4($sp)					# ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8	
	jr $ra

#*********************************************************************************************
#**Funzione per confrontare le distanze di due utenti rispetto ad un valore di riferimento****
#*********************************************************************************************
#Come argomenti riceve:
#a0 = indirizzo in memoria del primo utente
#a1 = indirizzo in memoria del secondo utente
#f1 = valore float di riferimento
#Restituisce 1 se la distanza tra il primo e il secondo utente è inferiore o uguale al valore float di riferimento,
#restituisce invece 0 se la distanza è superiore

e_utente_piu_vicino:
	addi $sp, $sp, -16				    # memorizza $ra, $fp e i registri $s0 e $s1 nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	sw $s1, 8($sp)
	sw $s0, 12($sp)
	
	move $s0, $a0						                # indirizzo primo utente in s0
	move $s1, $a1						                # indirizzo secondo utente in s1
	addi $t0, $s0, OFFSETLATITUDINE		                # recupera l'indirizzo della latitudine del primo utente e la salva in t0
	addi $t1, $s1, OFFSETLATITUDINE		                # recupera l'indirizzo della latitudine del secondo utente e la salva in t1
	lwc1 $f12, 0($t0)					                # carica in f12 la latitudine del primo utente
	lwc1 $f14, 0($t1)					                # carica in f14 la latitudine del secondo utente
	jal sottrazzione_valore_assoluto_float				# richiama la funzione per calcolare il valore assoluto della differenza delle due coordinate
	mov.s $f3, $f0						                # carica il valore di ritorno in f3
	addi $t0, $s0, OFFSETLONGITUDINE	                # recupera l'indirizzo della longitudine del primo utente e la salva in t0
	addi $t1, $s1, OFFSETLONGITUDINE	                # recupera l'indirizzo della longitudine del secondo utente e la salva in t1
	lwc1 $f12, 0($t0)					                # carica in f12 la longitudine del primo utente
	lwc1 $f14, 0($t1)					                # carica in f14 la longitudine del secondo utente
	jal sottrazzione_valore_assoluto_float				# richiama la funzione per calcolare il valore assoluto della differenza delle due coordinate
	mov.s $f5, $f0						                # carica il valore di ritorno in f5
	
	add.s $f3, $f3, $f5					                # somma latitudine e longitudine e salva il risultato in f3
	c.le.s $f1, $f3						                # verifica se f1 (somma delle coordinate in ingresso) è minore o uguale a f3 (coordinate calcolate) 
	bc1t f1_minore_o_uguale_f3 			                # se $f1 è minore o uguale a $f3 scrive 1 come risultato di ritorno, altirmenti scrive 0		
	move $v0, $zero
	j fine_e_utente_piu_vicino
f1_minore_o_uguale_f3:
	li $v0, 1	
fine_e_utente_piu_vicino:
	lw $s0, 12($sp)						                # ripristino di $ra, $fp, dei registri $s0 e $s1, e deallocazione dello spazio nello stack
	lw $s1, 8($sp)
	lw $fp, 4($sp)					
	lw $ra, 0($sp)
	addiu $sp, $sp, 16	
	jr $ra

#********************************************************************************		
#**Funzione per recuperare l'indirizzo di un utente partendo da nome e cognome***
#********************************************************************************	
#Come argomento riceve la stringa che contiene nome e cognome
#Restituisce 1 in $v0 se esiste e l'indirizzo dell'utente in memoria in $v1 altrimenti solo 0 in $v0

verifica_presenza_utenti:
	addi $sp, $sp, -24				# memorizza $ra, $fp e i registri $s0,$s1,$s2,$s3 nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	sw $s3, 8($sp)
	sw $s2, 12($sp)
	sw $s1, 16($sp)
	sw $s0, 20($sp)
	
	move $s3, $a0					# salva in $s3 l'indirizzo dove è presente il nome e cognome dell'utente da verificare

	la $s0, elencoUtenti			# carica in $s0 indirizzo di partenza dell'elenco degli utenti registrati
	move $s1, $zero					# utilizzo $s1 come contatore per capire se ha già passato tutti gli utenti nell'elenco di quelli registrati
	la $t0, numeroUtentiRegistrati	# carica in $s2 il numero degli utenti registrati
	lb $s2, 0($t0)
	
	#loop per la verifica della presenza dell'utente inserito all'interno dell'elenco
loop_verifica_presenza_utente_offset:	
	move $a0, $s0							                  # carica in $a0 l'indirizzo dove è presente il nome dell'utente in esame
	move $a1, $s3							                  # carica in $a1 l'indirizzo dove è presente il nome dell'utente da verificare
	jal confronta_stringhe					                  # richiama la funzione per il confronto di due stringhe, per verificare se corrispondono
	bnez $v0, utente_presente				                  # se l'utente inserito esiste salta a 'utente_presente'
	addi $s1, $s1, 1						                  # incrementa il contatore che tiene traccia del numero di utenti già verificati nell'elenco di quelli registrati
	addi $s0, $s0, OFFSETPROSSIMOUTENTE		                  # incrementa il puntatore all'indirizzo che contiene l' utente di cui verificare il nome
	bne $s1, $s2, loop_verifica_presenza_utente_offset	      # se il contatore non ha ancora raggiunto il numero degli utenti registrati torna a verificare il prossimo utente
	li $v0, 0								                  # altrimenti mette il valore di ritorno a 0
	j fine_verifica_presenza_utente				              # salta alla fine della funzione per il ripristino dei registri
utente_presente:
	li $v0, 1								                  # $v0 = 1
	move $v1, $s0							                  # $v1 = indirizzo in memoria utente cercato
fine_verifica_presenza_utente:
	lw $s0, 20($sp)						           	          # ripristino dei registri
	lw $s1, 16($sp)
	lw $s2, 12($sp)
	lw $s3, 8($sp)
	lw $fp, 4($sp)					
	lw $ra, 0($sp)
	addiu $sp, $sp, 24	
	jr $ra	


#***********************************************************************************		
#**Funzione per verificare che un utente esista ed appartenga alla rete quartiere***
#***********************************************************************************	
#Come argomento riceve l'indirizzo della stringa che contiene il messaggio da stampare e l'indirizzo della rete quartiere
#Restituisce in $v0 l'indirizzo dell'utente
#Durante l'esecuzione della funzione viene anche settata la flag corrispondente dell'array con le flag per marcare a che indice un utente è presente nella rete

inserisci_e_verifica_vicino:
	addi $sp, $sp, -24				        # memorizza $ra, $fp e i registri $s0,$s1,$s2,$s3 nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	sw $s3, 8($sp)
	sw $s2, 12($sp)
	sw $s1, 16($sp)
	sw $s0, 20($sp)
	
	move $s3, $a0							# $s3 = indirizzo della stringa per il messaggio da stampare
	move $s1, $a1							# $s1 = indirizzo della rete quartiere
	
inserisci_vicino:							# stampa la richiesta di inserimento dell'utente
	move $a0, $s3 							# carica l'indirizzo della stringa da stampare in $a0
	li $v0, 4
	syscall
	
	la $a0, nuovoIdUtenteRete				# utilizza 'nuovoIdUtenteRete' come variabile d'appoggio e memorizza a quell'indirizzo la stringa inserita
	li $a1, 44						
	li $v0, 8
	syscall		
	la $a0, nuovoIdUtenteRete
	jal rimuovi_nuova_riga						    # richiama la funzione per l'eliminazione dell'eventuale carattere di nuova linea dalla stringa	
	la $a0, nuovoIdUtenteRete				        # richiama la funzione per verificare che l'utente inserito sia registrato e per recuperare il suo indirizzo
	jal verifica_presenza_utenti					# in memoria	
	bnez $v0, verifica_se_utente_e_nella_rete	    # se l'utente inserito esiste salta a controllare che sia presente nella rete quartiere	
	la $a0, msgUtenteNonRegistrato				    # se invece l'utente inserito non esiste informa l'utente con un messaggio di errore 
	li $v0, 4
	syscall
	j inserisci_vicino						                        # torna all'inizio per l'inserimento dell' utente
	
verifica_se_utente_e_nella_rete:					                # verifica che l'utente inserito appartenga alla rete quartiere
	move $s0, $v1								                    # salva l'indirizzo dell' utente, di ritorno dalla funzione, in $s0
	move $s2, $s1								                    # $s1 = indirizzo di inizio array rete quartiere
	li $t0, 0									                    # $t0 = contatore per indicizzare successivamente l'array con le flag 
	la $t7, numeroUtentiRete					                    # $t3 = indirizzo che contiene il numero utenti rete
	lb $t1, 0($t7)								                    # $t1 = numero utenti registrati nella rete, verrà utilizzato come contatore da decrementare
loop_verifica_se_utente_e_nella_rete:	
	lw $t2, 0($s2)								                    # $t2 = indirizzo del primo utente salvato nella rete quartiere
	beq $s0, $t2, fine_loop_verifica_se_utente_e_nella_rete        	# se l'indirizzo corrisponde salta allo step successivo
	addi $t0, $t0, 1							                    # incrementa il contatore per indicizzare successivamente l'array con le flag
	addi $s2, $s2, 4							                    # incrementa il registro che contiene il puntatore all'indirizzo della rete quartiere per puntare al successivo
	addi $t1, $t1, -1							                    # decrementa il contatore del numero utenti della rete
	bnez $t1, loop_verifica_se_utente_e_nella_rete	                # se il contatore non è ancora a zero passa a valutare l'utente successivo
	la $a0, msgUtenteNonAppartieneRete					            # stampa un messaggio per informare che l'utente inserito non appartiene alla rete in esame
	li $v0, 4
	j inserisci_vicino							                    # torna all'inserimento del primo utente da confrontare
fine_loop_verifica_se_utente_e_nella_rete:							# se invece l'utente è nella rete, marca l'elemento corrispondente dell'array con le flag 
	la $t7, flagUtentiRete						                    # carica l'indirizzo in memoria dell'inizio dell'array con le flag in $t7
	add $t0, $t0, $t7							                    # aggiunge l'offset corrispondente che corrisponde al valore del contatore $t0 e salva il risultato in $t0
	sb $zero, 0($t0)							                    # scrive il valore 0 all'indirizzo corrispondente dell'array delle flag
	move $v0, $s0								                    # $v0 = $s0 (indirizzo in memoria dell'utente salvato in $v0)
	
	lw $s0, 20($sp)								                    # ripristino dei registri
	lw $s1, 16($sp)
	lw $s2, 12($sp)
	lw $s3, 8($sp)
	lw $fp, 4($sp)					
	lw $ra, 0($sp)
	addiu $sp, $sp, 24	
	jr $ra			
	
#******************************************************************		
#**Funzione per verificare se due utenti di una rete sono vicini***
#******************************************************************
#Come argomenti riceve:
#$a0 = indirizzo del primo utente da confrontare
#$a1 = indirizzo del secondo utente da confrontare
#$a2 = indirizzo della rete quartiere
#Restituisce in $v0 il valore 1 se i due utenti sono vicini, 0 altrimenti

sono_utenti_vicini:
	addi $sp, $sp, -40				# memorizza $ra, $fp e i registri $s nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	sw $s7, 8($sp)
	sw $s6, 12($sp)
	sw $s5, 16($sp)
	sw $s4, 20($sp)
	sw $s3, 24($sp)
	sw $s2, 28($sp)
	sw $s1, 32($sp)
	sw $s0, 36($sp)
	
	move $s0, $a0 				# $s0 = indirizzo del primo utente
	move $s1, $a1				# $s1 = indirizzo del secondo utente
	move $s2, $a2 				# $s2 = indirizzo rete quartiere
	
										                 # calcola la distanza tra il primo e il secondo utente e salva il valore in $f1
	addi $t0, $s0, OFFSETLATITUDINE		                 # recupera l'indirizzo della latitudine del primo utente e la salva in t0
	addi $t1, $s1, OFFSETLATITUDINE		                 # recupera l'indirizzo della latitudine del secondo utente e la salva in t1
	lwc1 $f12, 0($t0)					                 # carica in f12 la latitudine del primo utente
	lwc1 $f14, 0($t1)					                 # carica in f14 la latitudine del secondo utente
	jal sottrazzione_valore_assoluto_float				 # richiama la funzione per calcolare il valore assoluto della differenza delle due coordinate
	mov.s $f3, $f0						                 # carica il valore di ritorno in f3
	addi $t0, $s0, OFFSETLONGITUDINE	                 # recupera l'indirizzo della longitudine del primo utente e la salva in t0
	addi $t1, $s1, OFFSETLONGITUDINE                     # recupera l'indirizzo della longitudine del secondo utente e la salva in t1
	lwc1 $f12, 0($t0)					                 # carica in f12 la longitudine del primo utente
	lwc1 $f14, 0($t1)					                 # carica in f14 la longitudine del secondo utente
	jal sottrazzione_valore_assoluto_float				 # richiama la funzione per calcolare il valore assoluto della differenza delle due coordinate
	mov.s $f5, $f0						                 # carica il valore di ritorno in f5	
	add.s $f1, $f3, $f5					                 # somma latitudine e longitudine e salva il risultato in f1
	
										# verifica se il primo utente è più vicino ad un altro utente della rete
	move $a0, $s0						# $a0 = indirizzo del primo utente
	move $a1, $s2						# $a1 = indirizzo della rete quartiere
	la $t0, numeroUtentiRete			# $t0 = indirizzo variabile che contiene il numero degli utenti registrati nella rete 
	lb $a2, 0($t0)						# $a2 = numero utenti della rete
	jal distanza_e_minore_della_rete	# richiama la funzione per verificare se la distanza dei due utenti è inferiore alla distanza tra il primo 
										# utente e gli altri utenti della rete
	beqz $v0, utenti_non_sono_vicini	# se la funzione richiamata ha trovato un utente più vicino, salta alla sezione 'utenti_non_sono_vicini'
	
										# altrimenti, verifica se il secondo utente è più vicino ad un altro utente della rete
	move $a0, $s1						# $a0 = indirizzo del secondo utente
	move $a1, $s2						# $a1 = indirizzo della rete quartiere
	la $t0, numeroUtentiRete			# $t0 = indirizzo variabile che contiene il numero degli utenti registrati nella rete 
	lb $a2, 0($t0)						# $a2 = numero utenti della rete
	jal distanza_e_minore_della_rete	# richiama la funzione per verificare se la distanza dei due utenti è inferiore alla distanza tra il secondo utente e	
										# gli altri utenti della rete
	beqz $v0, utenti_non_sono_vicini	# se la funzione richiamata ha trovato un utente più vicino, salta alla sezione 'utenti_non_sono_vicini'

										# altrimenti i due utenti di partenza sono vicini
	li $v0, 1							# carica il valore di ritorno della funzione, cioè 1
	j fine_sono_utenti_vicini			# e salta al ripristino dei registri
	
utenti_non_sono_vicini:				    # sezione eseguita se i due utenti non sono vicini di casa	
	li $v0, 0							# carica il valore di ritorno (0) in $v0
	
fine_sono_utenti_vicini:	
	lw $s0, 36($sp)						# ripristino dei registri salvati nello stack
	lw $s1, 32($sp)
	lw $s2, 28($sp)
	lw $s3, 24($sp)
	lw $s4, 20($sp)
	lw $s5, 16($sp)
	lw $s6, 12($sp)
	lw $s7, 8($sp)
	lw $fp, 4($sp)					
	lw $ra, 0($sp)
	addiu $sp, $sp, 40	
	jr $ra	

#**********************************************************************************************************************************		
#**Funzione per verificare se la distanza tra un utente e tutti quelli della rete quartiere è minore di un valore di riferimento***
#**********************************************************************************************************************************
#Come argomenti riceve:
#$a0 = indirizzo dell'utente da confrontare
#$a1 = indirizzo della rete quartiere
#$a2 = numero utenti nella rete quartiere
#$f1 = valore di riferimento utilizzato per il confronto con le distanze dell'utente passato come parametro e gli altri utenti della rete
#Restituisce in $v0 il valore 1 se la distanza di riferimento risulta minore o uguale rispetto agli altri confronti, 0 altrimenti

distanza_e_minore_della_rete:
	addi $sp, $sp, -40				# memorizza $ra, $fp e i registri $s nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	sw $s7, 8($sp)
	sw $s6, 12($sp)
	sw $s5, 16($sp)
	sw $s4, 20($sp)
	sw $s3, 24($sp)
	sw $s2, 28($sp)
	sw $s1, 32($sp)
	sw $s0, 36($sp)

	move $s0, $a0			# $s0 = indirizzo utente da confrontare
	move $s1, $a1           # $s1 = indirizzo della rete quartiere
	move $s2, $a2           # $s2 = contatore contenente il numero utenti della rete 
	la $s3, flagUtentiRete	# $s3 = indirizzo inizio array flag marker utenti da confrontare
	
loop_demdr:												# loop per il confronto dell'utente in ingresso con il resto degli utenti della rete	
	lb $t0, 0($s3)							            # $t0 = valore per capire se l'utente in esame deve essere confrontato con l'utente della rete
	beqz $t0, passaggio_successivo_demdr				# se il valore è 0 l'utente non deve essere confrontato e passa all'elemento successivo
	move $a0, $s0										# $a0 = indirizzo del primo utente
	lw $a1, 0($s1)										# $a1 = indirizzo dell'utente della rete da confrontare
	jal e_utente_piu_vicino				 			    # richiama la funzione per confrontare la distanza di due utenti con il valore di riferimento in $f1
	beqz $v0, distanza_target_e_maggiore					# se il valore di ritorno è uguale a 0 significa che la distanza tra i due utenti è minore di quella di riferimento
passaggio_successivo_demdr:	
	addi $s3, $s3, 1						# incrementa il puntatore all'elemento dell'array con i marker per puntare al successivo elemento
	addi $s1, $s1, 4						# incrementa il puntatore all'elemento della rete quartiere per puntare al successivo utente
	addi $s2, $s2, -1						# decrementa il contatore con il numero utenti della rete rimasti da confrontare
	bnez $s2, loop_demdr					# se il contatore è diverso da 0 significa che ci sono ancora utenti da confrontare
	
	li $v0, 1								# se non sono state trovate distanze minori a quella di riferimento, carica il valore di ritorno, cioè 1
	j fine_e_distanza_minore_della_rete		# salta al ripristino dei registri e rientro dalla funzione

	distanza_target_e_maggiore:				# questa parte di codice viene eseguita se è stato trovato almeno un utente con distanza minore a quella target
	move $v0, $zero							# carica il valore di ritorno, cioè 0	

fine_e_distanza_minore_della_rete:			# ripristino dei registri salvati nello stack
	lw $s0, 36($sp)							
	lw $s1, 32($sp)
	lw $s2, 28($sp)
	lw $s3, 24($sp)
	lw $s4, 20($sp)
	lw $s5, 16($sp)
	lw $s6, 12($sp)
	lw $s7, 8($sp)
	lw $fp, 4($sp)					
	lw $ra, 0($sp)
	addiu $sp, $sp, 40	
	jr $ra	
	
#*******************************************************************************		
#**Funzione per verificare che la scelta tra le opzioni del menu sia corretta***
#*******************************************************************************	
#Come argomenti riceve:
#$a0 = valore inserito dall'utente
#$a1 = valore limite accettabile
#Restituisce in $v0 il valore 1 se la scelta è corretta, 0 altrimenti
# La funzione verifica che 0<=$a0<=$a1

scelta_del_menu_corretta:
	addi $sp, $sp, -8				# memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	
	bltz $a0, scelta_menu_non_corretta	     # se il valore inserito è minore di 0 la scelta non è valida
	sub $t0, $a1, $a0				         # $t0 = $a1 - $a0 (sottrae il valore limite al valore inserito dall'utente e salva in $t0)
	bltz $t0, scelta_menu_non_corretta	     # se $t0 è minore di 0 la scelta non è valida
	li $v0, 1						         # altrimenti la scelta è valida, carica 1 come valore di ritorno
	j fine_scelta_del_menu_corretta

	scelta_menu_non_corretta:					# se il valore non è nei limiti la scelta è errata. Carica 0 come valore di ritorno
	move $v0, $zero

	fine_scelta_del_menu_corretta:	
	lw $fp, 4($sp)					# ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8	
	jr $ra	
	
#**********************************************************		
#**Procedura per l'inserimento di una nuova segnalazione***
#**********************************************************	
#Come argomento riceve:
#$a0 = puntatore all'indirizzo in memoria della prima segnalazione della lista

aggiungi_nuova_segnalazione:	
	addi $sp, $sp, -20				# memorizza $ra, $fp e i registri $s0 e $s1 nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	sw $s2, 8($sp)
	sw $s1, 12($sp)
	sw $s0, 16($sp)

	move $s2, $a0					       # $s2 = indirizzo del puntatore alla variabile che contiene l'indirizzo della prima segnalazione
	lw $s0, 0($s2)					       # $s0 = indirizzo in memoria della lista delle segnalazioni
	beqz $s0, ottieni_indirizzo_memoria	   # se la lista delle segnalazioni è vuota salta subito alla richiesta di memoria per l'inserimento di un 
									       # nuovo elemento,		
									       # altrimenti scorre la lista verificando il campo 'next' fino a che non lo trova a null. Quello sarà
									       # il punto in cui la nuova segnalazione verrà linkata	
loop_ricerca_nulla:	
	addi $s0, $s0, OFFSETALNEXT		    # $s0 = indirizzo in memoria del campo 'next' della segnalazione in esame
	lw $t0, 0($s0)					    # $t0 = indirizzo in cui è presente la successiva segnalazione
	move $s1, $s0					    # $s1 = $s0 (indirizzo in memoria del campo 'next' della segnalazione in esame)
	move $s0, $t0					    # $s0 = indirizzo in memoria della prossima segnalazione
	bnez $s0, loop_ricerca_nulla		# se $s0 è diverso da 0 deve continuare il loop alla ricerca della prossima segnalazione	
									    # se invece è uguale a 0 procede con l'inserimento della nuova segnalazione
	move $s2, $s1					    # $s2 punta di al campo 'next' dell'utlima segnalazione della lista

ottieni_indirizzo_memoria:					# chiamata di sistema per avere a disposizione i byte per la memorizzazione di una nuova segnalazione nello heap
	li $a0, BYTESFORALARM			        # $a0 = numero di byte da allocare nello heap
	li $v0, 9						        # $v0 = 9 (codice per l'allocazione di memoria)
	syscall
	move $s1, $v0					        # $s1 = indirizzo del blocco allocato
	sw $s1, 0($s2)					        # carica nel campo 'next' dell'ultima segnalazione l'indirizzo della 
									        # successiva segnalazione, oppure nella variabile a cui punta la lista delle segnalazioni
	
									        # procede ora alla richiesta e registrazione dei dati della segnalazione
									        # per prima cosa registra nome e cognome dell'utente
	la $a0, msgChiediNomeSegnalazione	    # stampa la richiesta di inserimento del nome dell'utente
	li $v0, 4
	syscall
	move $a0, $s1					# $a0 = indirizzo in memoria dell'utente della segnalazione (primo campo)
	li $a1, 41						# $a1 = 41 (byte da leggere)
	li $v0, 8
	syscall
	
									                # ora registra le coordinate				
	la $a0, msgChiediCoordinateSegnalazione			# stampa la richiesta di inserimento delle coorinate
	li $v0, 4
	syscall
	addi $s0, $s1, OFFSETALLATLONG	# $s0 = indirizzo in memoria del campo latitudine/longitudine
	move $a0, $s0					# $a0 = $s0
	li $a1, 25						# $a1 = 25 (byte da leggere)
	li $v0, 8
	syscall
	
										            # ora registra il timestamp
	la $a0, msgChiediDataOraSegnalazione			# stampa la richiesta di inserimento del timestamp
	li $v0, 4
	syscall
	addi $s0, $s1, OFFSETALTIMESTAMP	# $s0 = indirizzo in memoria del campo timestamp
	move $a0, $s0						# $a0 = $s0
	li $a1, 25							# $a1 = 25 (byte da leggere)
	li $v0, 8
	syscall
	
										                # ora registra la motivazione
	la $a0, msgChiediMotivazioneSegnalazione			# stampa la richiesta di inserimento della motivazione
	li $v0, 4
	syscall
	addi $s0, $s1, OFFSETALMOTIVATION	# $s0 = indirizzo in memoria del campo motivazione
	move $a0, $s0						# $a0 = $s0
	li $a1, 41							# $a1 = 41 (byte da leggere)
	li $v0, 8
	syscall
	
										            # ora registra lo stato
	la $a0, msgChiediStatoSegnalazione				# stampa la richiesta di inserimento dello stato
	li $v0, 4
	syscall
	addi $s0, $s1, OFFSETALSTATUS		# $s0 = indirizzo in memoria del campo stato
	move $a0, $s0						# $a0 = $s0
	li $a1, 41							# $a1 = 41 (byte da leggere)
	li $v0, 8
	syscall	
	
										# per ultimo mette a null il campo next
	addi $s0, $s1, OFFSETALNEXT			# $s0 = indirizzo del campo next della segnalazione
	sw $zero, 0($s0)					# next = null
	
	la $a0, msgSegnalazioneRegistrata				# stampa il messaggio che la registrazione della segnalazione è andata a buon fine
	li $v0, 4
	syscall
								
fine_aggiungi_nuova_segnalazione:						# ripristino dei registri salvati nello stack	
	lw $s0, 16($sp)						                # ripristino di $ra, $fp, dei registri $s0 e $s1, e deallocazione dello spazio nello stack
	lw $s1,	12($sp)
	lw $s2, 8($sp)
	lw $fp, 4($sp)					
	lw $ra, 0($sp)
	addiu $sp, $sp, 20	
	jr $ra	


#******************************************************************		
#**Funzione per la stampa del menu con la sola selezione dello 0***
#******************************************************************
#Restituisce in $v0 il valore dell'indirizzo del menu principale
	
menu_con_sola_scelta_zero:
	addi $sp, $sp, -8							    # memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
loop_menu_con_sola_scelta_zero:
	la $a0, msgFineRegistrazione		    	    # visualizza il menu con la sola possibilità di selezionare '0' per tornare al menu principale
	li $v0, 4								
	syscall
	li $v0, 5									    # legge il valore inserito dall'utente (come intero) e verifica che sia 0							
	syscall
	beqz $v0, fine_menu_con_sola_scelta_zero	    # se l'inserimento è corretto torna va alla fine della funzione
	la $a0, msgSceltaNonValida						# altrimenti visualizza un messaggio che indica che la selezione non è valida
	li $v0, 4
	syscall
	j loop_menu_con_sola_scelta_zero			    # e torna a visualizzare nuovamente il menu dopo registrazione attendendo la selezione dell'utente		

fine_menu_con_sola_scelta_zero:
	la $v0, selezione_iniziale						# carica l'indirizzo dove è presente il menu principale in $v0
	
	lw $fp, 4($sp)								    # ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8	
	jr $ra										    #ritorna al chiamante
	
#***********************************************************	
#**Procedura per la stampa della lista delle segnalazioni***
#***********************************************************
#Come argomento riceve in $a0 l'indirizzo della lista

stampa_lista_segnalazioni:
	addi $sp, $sp, -8				            # memorizza $ra e $fp nello stack
	sw $ra, 0($sp)
	sw $fp, 4($sp)
	
	lw $t0, 0($a0)					            # $t0 = indirizzo della lista
	beqz $t0, nessuna_segnalazione_presente		# se non sono presenti segnalazioni (lista vuota) salta a 'nessuna_segnalazione_presente'
	li $t1, 1						            # $t1 = 1 : contatore per visualizzare il numero delle segnalazioni

loop_stampa_lista_segnalazioni:				    # loop per la stampa delle segnalazioni
	la $a0, msgNumeroSegnalazione			    # stampa il numero della segnalazione
	li $v0, 4
	syscall
	move $a0, $t1
	li $v0, 1
	syscall
	
	la $a0, msgNomeEsecutoreSegnalazione			# stampa il nome di chi ha eseguito la segnalazione
	li $v0, 4
	syscall
	move $a0, $t0					                # il nome si trova come primo campo nell'oggetto segnalazione
	li $v0, 4
	syscall
	
	la $a0, msgCoordinateSegnalazione			    # stampa le coordinate della segnalazione
	li $v0, 4
	syscall
	addi $a0, $t0, OFFSETALLATLONG	                # $a0 = indirizzo in memoria del campo coordinate della segnalazione in esame
	li $v0, 4
	syscall
	
	la $a0, msgDataOraSegnalazione			        # stampa data e ora della segnalazione
	li $v0, 4
	syscall
	addi $a0, $t0, OFFSETALTIMESTAMP	            # $a0 = indirizzo in memoria del campo data e ora della segnalazione in esame
	li $v0, 4
	syscall

	la $a0, msgMotivoSegnalazione			        # stampa la motivazione della segnalazione
	li $v0, 4
	syscall
	addi $a0, $t0, OFFSETALMOTIVATION	            # $a0 = indirizzo in memoria del campo motivazione della segnalazione in esame
	li $v0, 4
	syscall
	
	la $a0, msgStatoSegnalazione				    # stampa lo stato della segnalazione
	li $v0, 4
	syscall
	addi $a0, $t0, OFFSETALSTATUS		            # $a0 = indirizzo in memoria del campo stato della segnalazione in esame
	li $v0, 4
	syscall
	
	addi $t0, $t0, OFFSETALNEXT			            # $t0 = indirizzo in memoria del campo next della segnalazione in esame
	lw $t2, 0($t0)
	beqz $t2, fine_stampa_lista_segnalazioni		# se $t2 = 0 non sono presenti altre segnalazione, va alla fine della procedura
	move $t0, $t2						            # $t0 = indirizzo della successiva segnalazione
	addi $t1, $t1, 1					            # incrementa di 1 il contatore del numero delle segnalazioni
	j loop_stampa_lista_segnalazioni

nessuna_segnalazione_presente:
	la $a0, msgNessunaSegnalazionePresente		    # stampa un messaggio per segnalare che non sono presenti segnalazioni
	li $v0, 4
	syscall
	
fine_stampa_lista_segnalazioni:
	lw $fp, 4($sp)								    # ripristino di $ra e $fp e deallocazione dello spazio nello stack
	lw $ra, 0($sp)
	addiu $sp, $sp, 8	
	jr $ra										    #ritorna al chiamante


