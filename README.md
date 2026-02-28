# Training Project 🏋️

Un'applicazione Android per la gestione degli allenamenti, sviluppata nel tempo libero come progetto personale.

## Descrizione

Training Project è un'app dedicata a chi si allena e vuole tenere traccia dei propri workout in modo semplice ed efficace. L'applicazione offre strumenti di cronometraggio e gestione delle schede di allenamento, tutto salvato localmente sul dispositivo.

## Funzionalità

### Cronometro 
- Cronometro con funzioni di start/stop
- Registrazione dei tempi parziali
- Interfaccia intuitiva per monitorare i tempi di esecuzione

### Timer 
- Timer personalizzabile con selezione di minuti e secondi
- Funzioni di pausa e reset
- Segnale sonoro al termine del countdown
- Ideale per esercizi a tempo o intervalli

### Gestione Schede 
- Creazione di schede di allenamento personalizzate
- Aggiunta e modifica di esercizi per ogni scheda
- Per ogni esercizio è possibile specificare:
  - Nome dell'esercizio
  - Numero di set
  - Tempi di recupero
  - Tempi di esecuzione
- Eliminazione di schede ed esercizi
- Modifica dei nomi delle schede
- Tutti i dati salvati localmente con Room Database

## Tecnologie Utilizzate

- **Linguaggio**: Kotlin
- **Database**: Room Database (SQLite)
- **UI**: XML Layouts + ViewBinding
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34

## Struttura del Progetto

```
app/src/main/java/com/example/trainingproject/
├── Home.kt                    # Activity principale con bottom navigation
├── Cronometro.kt             # Fragment del cronometro
├── Timer.kt                  # Fragment del timer
├── Schede.kt                 # Fragment gestione schede
├── EserciziScheda.kt         # Activity visualizzazione esercizi
├── AggiungiEsercizio.kt      # Activity aggiunta esercizio
├── ModificaEsercizio.kt      # Activity modifica esercizio
├── AdapterListView/          # Adapter personalizzati per le liste
├── RoomDatabase/
│   ├── entities/             # Entità del database (Scheda, Esercizio)
│   ├── DAO/                  # Data Access Objects
│   └── database/             # Database configuration
└── ViewModels/               # ViewModel per la gestione dello stato
```

## Possibili Sviluppi Futuri

- **Statistiche di Allenamento**: Generazione di grafici e metriche sull'andamento degli allenamenti dell'utente nel tempo
- **Cronometri Automatici**: Sistema di timer automatici per tracciare i tempi di recupero tra gli esercizi senza intervento manuale
- **Storico Allenamenti**: Registro completo degli allenamenti effettuati con date e performance
- **Export/Import Dati**: Possibilità di esportare e importare le schede di allenamento
- **Notifiche**: Promemoria per gli allenamenti programmati
- **Temi Personalizzati**: Dark mode e personalizzazione dei colori

## Requisiti

- Android 7.0 (API 24) o superiore
- Circa 10 MB di spazio libero

## 📝 Note

Questo è un progetto personale sviluppato nel tempo libero per scopi educativi e di utilità personale. L'app non raccoglie alcun dato personale e funziona completamente offline.

Tutti i dati vengono salvati localmente sul dispositivo dell'utente utilizzando Room Database.
