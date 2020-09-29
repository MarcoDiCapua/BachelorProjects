package utils;

/**
 * This class contains String representing the application guides
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Guide {
	/**
	 * Main librarian interface guide
	 */
	public final static String mainInterfaceLibrarianGuide = "<html>L’interfaccia “Menu principale” presenta le funzioni principali di cui l’utente può usufruire. \r\n"
			+ "<br>Facendo click su “Seleziona utente” si richiede il codice fiscale di un utente di tipo lettore. Inserendo il codice si aprirà la finestra principale contenente le funzioni possibili per gli utenti di tipo lettore. \r\n"
			+ "<br>Facendo click su “Analisi del sistema” si aprirà la finestra principale contenente le possibili funzioni per le analisi del sistema. \r\n"
			+ "<br>Facendo click su “Logout” l’utente sarà disconnesso, la finestra di menu principale sarà chiusa e verrà aperta la finestra di Login.</html>";
	/**
	 * Add book interface guide
	 */
	public final static String addBookGuide = "<html>L’interfaccia “Aggiungi libro” permette al bibliotecario di aggiungere un libro alla libreria della biblioteca. È necessario compilare correttamente ogni campo per poter proseguire. Per inserire uno o più autori è necessario cliccare sul bottone “+”, posto di fianco ad “Autore:”. In questo modo, verrà aperta una nuova finestra dedita all’inserimento dei dati degli autori. \r\n"
			+ "<br>Dopo aver compilato i campi richiesti, sarà possibile cliccare su “Aggiungi!” per visualizzare i dati dell’autore di fianco alla dicitura “Autori inseriti:”. È possibile ripetere questo procedimento più volte in base al numero degli autori che si intende aggiungere alla lista. Cliccare poi sul tasto “Conferma!” per tornare alla finestra “Aggiungi libro”.  \r\n"
			+ "<br>Cliccare su “Annulla!” se si desidera annullare il procedimento di inserimento degli autori e tornare alla finestra “Aggiungi libro”.\r\n"
			+ "<br>Dopo aver compilato i campi in “Aggiungi libro”, cliccare su “Aggiungi!” per inserire definitivamente il libro nella biblioteca ed essere riportati al “Menu principale”.</html>";
	/**
	 * Delete book interface guide
	 */
	public final static String deleteBookGuide = "<html>L’interfaccia “Rimuovi libro” permette al bibliotecario di rimuovere un libro dalla lista dei libri in libreria. Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri presenti in libreria. \r\n"
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella. È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. Nella prima colonna della tabella è presente un riquadro di selezione che, se spuntato, permette di selezionare il libro. In questo modo il libro selezionato sarà visualizzato anche nella tabella posta nella parte inferiore della schermata dedita alla rimozione dei libri. \r\n"
			+ "<br>A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati. Per filtrare una categoria è necessario spuntare il riquadro posto immediatamente dopo “Categoria:”. Non spuntando tale riquadro, non sarà possibile selezionare le categorie e visualizzare i libri filtrati secondo esse.  \r\n"
			+ "<br>Nella parte inferiore della schermata è disposta una tabella, inizialmente vuota, che contiene i libri selezionati tramite l’apposito riquadro di selezione presente nella tabella centrale, di cui sopra. Questi libri sono destinati alla rimozione.\r\n"
			+ "<br>È possibile riordinare i libri della tabella cliccando su una delle intestazioni della colonna. Una volta terminate le operazioni di selezione, l’utente, facendo click su “Elimina!”, completerà la rimozione dei libri dalla libreria.\r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
	/**
	 * All books librarian interface guide
	 */
	public final static String allBooksLibrarianGuide = "<html>L’interfaccia “Elenco libri” consente la visualizzazione di tutti i libri presenti in libreria. Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri in libreria."
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella."
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati."
			+ "<br>Per filtrare una categoria è necessario spuntare il riquadro posto dopo “Categoria:”. Non spuntando tale riquadro, non sarà possibile selezionare le categorie e visualizzare i libri filtrati secondo esse."
			+ "<br>Facendo click sul tasto “Menù”, l’utente verrà riportato nel Menu di selezione delle funzioni.</html>";
	/**
	 * New user interface guide
	 */
	public final static String newUserGuide = "<html>L’interfaccia “Aggiungi nuovo utente” permette al bibliotecario di creare un nuovo profilo per conto di un utente lettore."
			+ "<br>È necessario compilare tutti i campi correttamente per poter continuare. La password verrà generata automaticamente dal sistema ed inviata all’utente tramite indirizzo e-mail."
			+ "<br>Dopo aver compilato correttamente i campi, fare click su “Registrati!” per completare la registrazione e tornare al Menu principale.</html>";
	/**
	 * Settings librarian interface guide
	 */
	public final static String librarianSettingsGuide = "<html>L’interfaccia “Modifica profilo” permette all’ utente bibliotecario di modificare i propri dati personali. \r\n"
			+ "<br>E’ necessario correggere i dati inseriti in automatico e cliccare su “Conferma modifiche” per salvare e tornare al “Menù principale”. \r\n"
			+ "<br>È necessario fare click su “Modifica password” per accedere alle impostazioni di modifica. Cliccare su “Modifica!” una volta riempiti i campi “vecchia password”, “nuova password” e “conferma password”.\r\n"
			+ "<br>È necessario fare click su “Cancella utente” per accedere alle impostazioni di cancellazione. Cliccare su “Elimina!” per confermare la cancellazione del profilo;  l’utente verrà riportato al “Menu di login”.</html>";
	/**
	 * Analysis interface guide
	 */
	public final static String mainInterfaceAnalysisGuide = "<html>L’interfaccia “Menu principale” mette a disposizione dell’ utente le principali funzioni di analisi del sistema. Cliccare su “Torna al menu principale” per tornare al “Menu principale bibliotecario”.</html>";
	/**
	 * All loaned books list interface guide
	 */
	public final static String allLoanedBooksListGuide = "<html>L’interfaccia “Elenco prestiti” consente la visualizzazione di tutti i libri in prestito presenti in libreria.\r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri in prestito. \r\n"
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati. \r\n"
			+ "<br>Per filtrare una categoria è necessario spuntare il riquadro posto dopo “Categoria:”. \r\n"
			+ "<br>Non spuntando tale riquadro, non sarà possibile selezionare le categorie e visualizzare i libri filtrati secondo esse.\r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel Menu di selezione delle funzioni di analisi.</html>";
	/**
	 * All booked books list interface guide
	 */
	public final static String allBookedBooksListGuide = "<html>L’interfaccia “Elenco prenotazioni” consente la visualizzazione di tutti i libri prenotati presenti in libreria. \r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri prenotati presenti nella libreria. \r\n"
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati. \r\n"
			+ "<br>Per filtrare una categoria è necessario spuntare il riquadro posto dopo “Categoria:”. \r\n"
			+ "<br>Non spuntando tale riquadro, non sarà possibile selezionare le categorie e visualizzare i libri filtrati secondo esse. \r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
	/**
	 * Books rank interface guide
	 */
	public final static String booksRankGuide = "<html>L’interfaccia “Classifica libri” consente la visualizzazione della classifica dei libri presenti in libreria.\r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri presenti nella classifica. \r\n"
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati. \r\n"
			+ "<br>Per filtrare una categoria è necessario spuntare il riquadro posto dopo “Categoria:”. \r\n"
			+ "<br>Non spuntando tale riquadro, non si potrà selezionare le categorie e visualizzare i libri filtrati secondo esse. \r\n"
			+ "<br>Per filtrare un inquadramento è necessario spuntare il riquadro posto dopo “Inquadramento:”. \r\n"
			+ "<br>Non spuntando tale riquadro, non sarà possibile selezionare gli inquadramenti e visualizzare i libri filtrati secondo essi. \r\n"
			+ "<br>Il riquadro di selezione posto di fianco a “Assoluta:” consente la visualizzazione della classifica assoluta dei libri.\r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni di analisi.</html>";
	/**
	 * Overwhelming loans interface guide
	 */
	public final static String overwhelmingLoansGuide = "<html>L’interfaccia “Prestiti sconfinanti” consente la visualizzazione della lista dei libri sconfinanti.\r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri in prestiti sconfinanti. \r\n"
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati. \r\n"
			+ "<br>Per filtrare una categoria è necessario spuntare il riquadro posto dopo “Categoria:”. \r\n"
			+ "<br>Non spuntando tale riquadro, non sarà possibile selezionare le categorie e visualizzare i libri filtrati secondo esse. \r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni di analisi.</html>";
	/**
	 * Main reader interface guide
	 */
	public final static String mainInterfaceReaderGuide = "<html>L’interfaccia “Menu principale” mette a disposizione dell’utente le principali funzioni consentite al lettore.\r\n"
			+ "<br>Facendo click su “Cancella utente” si aprirà una finestra di conferma. \r\n"
			+ "<br>Facendo click su “Ok” l’utente verrà eliminato e riportato alla finestra principale bibliotecario. \r\n"
			+ "<br>Facendo click su “Logout” l’utente verrà disconnesso e riportato alla finestra principale bibliotecario.</html>";
	/**
	 * Loan book interface guide
	 */
	public final static String loanBookGuide = "<html>L’interfaccia “Ricerca libro” permette al lettore di cercare, prenotare o prendere in prestito un libro dalla lista dei libri presenti in libreria. \r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri prenotati presenti in libreria. \r\n"
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna.\r\n"
			+ "<br>Nella prima colonna della tabella è presente un riquadro di selezione che permette di selezionare il libro. \r\n"
			+ "<br>Il libro sarà visualizzato anche nella tabella dedita alla prenotazione del libro posta in fondo alla schermata. \r\n"
			+ "<br>A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati. \r\n"
			+ "<br>Per filtrare una categoria è necessario spuntare il riquadro posto immediatamente dopo “Categoria:”. \r\n"
			+ "<br>Non spuntando tale riquadro, non sarà possibile selezionare le categorie e visualizzare i libri filtrati secondo esse.  \r\n"
			+ "<br>Nella parte inferiore della schermata è disposta una tabella, inizialmente vuota, che contiene i libri selezionati tramite l’apposito riquadro di selezione presente nella tabella centrale, di cui sopra. \r\n"
			+ "<br>Questi libri sono destinati alla prenotazione.\r\n"
			+ "<br>È possibile riordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Terminate le operazioni di selezione, l’utente, facendo click su “Prenota!”, potrà selezionare la durata prestito. Cliccando su “OK” completerà la prenotazione dei libri in libreria.\r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
	/**
	 * Back book interface guide
	 */
	public final static String backBookGuide = "<html>L’interfaccia “Restituisci libro” permette al lettore di restituire un libro.  \r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri in prestito presenti nella libreria. \r\n"
			+ "<br>La tabella permette di visualizzare i libri in prestito. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Nella prima colonna della tabella è presente un riquadro di selezione che permette di selezionare il libro. \r\n"
			+ "<br>Il libro sarà visualizzato anche nella tabella dedita alla restituzione del libro posta in fondo alla schermata. \r\n"
			+ "<br>Nella parte inferiore della schermata è disposta una tabella, inizialmente vuota, che contiene i libri selezionati tramite l’apposito riquadro di selezione presente nella tabella centrale, di cui sopra. \r\n"
			+ "<br>Questi libri sono destinati alla restituzione. \r\n"
			+ "<br>È possibile riordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Terminate le operazioni di selezione, l’utente, facendo click su “Restituisci!”, completerà la restituzione dei libri in libreria.\r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
	/**
	 * Reader booked books list interface guide
	 */
	public final static String readerBookedBooksListGuide = "<html>L’interfaccia “Elenco prenotazioni” permette al lettore di visualizzare e ritirare i libri prenotati.\r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri prenotati. \r\n"
			+ "<br>La tabella permette di visualizzare i libri prenotati. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Nella prima colonna della tabella è presente un riquadro di selezione che permette di selezionare il libro. \r\n"
			+ "<br>Il libro sarà visualizzato anche nella tabella dedita al ritiro del libro posta in fondo alla schermata. \r\n"
			+ "<br>Nella parte inferiore della schermata è presente una tabella, inizialmente vuota, che contiene i libri selezionati tramite l’apposito riquadro di selezione presente nella tabella centrale, di cui sopra. \r\n"
			+ "<br>Questi libri sono destinati al ritiro. \r\n"
			+ "<br>È possibile riordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Terminate le operazioni di selezione, l’utente, facendo click su “Consegna!”, completerà il ritiro dei libri dalla libreria. \r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
	/**
	 * Delete booking interface guide
	 */
	public final static String deleteBookingGuide = "<html>L’interfaccia “Cancella prenotazione” permette al lettore di visualizzare e annullare la prenotazione dei libri.  \r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dei libri prenotati. \r\n"
			+ "<br>La tabella permette di visualizzare i libri prenotati. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Nella prima colonna della tabella è presente un riquadro di selezione che permette di selezionare il libro. \r\n"
			+ "<br>Il libro sarà visualizzato anche nella tabella dedita all’annullamento della prenotazione del libro posta in fondo alla schermata. \r\n"
			+ "<br>Nella parte inferiore della schermata è disposta una tabella, inizialmente vuota, che contiene i libri selezionati tramite l’apposito riquadro di selezione presente nella tabella centrale, di cui sopra. \r\n"
			+ "<br>Questi libri sono destinati all’annullamento della prenotazione. \r\n"
			+ "<br>È possibile riordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Terminate le operazioni di selezione, l’utente, facendo click su “Cancella!”, completerà l’annullamento della prenotazione dei libri in libreria.\r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
	/**
	 * Reader loaned books list interface guide
	 */
	public final static String readerLoanedBooksListGuide = "<html>L’interfaccia “Elenco prestiti” permette al lettore di visualizzare e annullare il prestito dei libri. \r\n"
			+ "<br>La tabella permette di visualizzare i libri in prestito. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Nella prima colonna della tabella è presente un riquadro di selezione che permette di selezionare il libro. \r\n"
			+ "<br>Il libro sarà visualizzato anche nella tabella dedita all’annullamento del prestito del libro posta in fondo alla schermata. \r\n"
			+ "<br>Nella parte inferiore della schermata è disposta una tabella, inizialmente vuota, che contiene i libri selezionati tramite l’apposito riquadro di selezione presente nella tabella centrale, di cui sopra. \r\n"
			+ "<br>Questi libri sono destinati all’annullamento del prestito. \r\n"
			+ "<br>È possibile riordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>Terminate le operazioni di selezione, l’utente, facendo click su “Cancella!”, completerà l’annullamento del prestito dei libri in libreria.\r\n"
			+ "<br>Facendo click sul tasto “Menu” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
	/**
	 * History loans inteface guide
	 */
	public final static String histroyLoansGuide = "<html>L’interfaccia “Cronologia prestiti” consente la visualizzazione lo storico dei libri presi in prestito dall’ utente. \r\n"
			+ "<br>Nella sezione centrale della finestra è disposta una tabella contenente le informazioni dello storico dei libri.  \r\n"
			+ "<br>La tabella permette di visualizzare 25 libri per pagina: per poter proseguire nella visualizzazione dei libri è necessario sfogliare le pagine cliccando sui numeri posti sotto la tabella. \r\n"
			+ "<br>È possibile ordinare i libri della tabella cliccando su una delle intestazioni della colonna. \r\n"
			+ "<br>A sinistra della finestra sono presenti i filtri, grazie ai quali è possibile filtrare la lista dei libri presenti nella tabella centrale secondo i criteri di ricerca selezionati. \r\n"
			+ "<br>Per poter filtrare una categoria è necessario spuntare il riquadro posto immediatamente dopo “Categoria:”. \r\n"
			+ "<br>Non spuntando tale riquadro, non si potrà selezionare le categorie e visualizzare i libri filtrati secondo esse.  \r\n"
			+ "<br>Facendo click sul tasto “Menù” l’utente verrà riportato nel menu di selezione delle funzioni.</html>";
}
