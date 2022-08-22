#Popolare le tabelle

#clienti
INSERT INTO CLIENTI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Email, Numero_di_telefono, Via_residenza, Numero_civ_residenza, Citta_residenza, Coordinate_bancarie)
VALUES ('RSSMRA90A01H501W','Mario','Rossi','1990-01-01', 'rossimario@alice.com', 277839121, 'Via Roma', 4, 'Roma', '1234');
INSERT INTO CLIENTI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Email, Numero_di_telefono, Via_residenza, Numero_civ_residenza, Citta_residenza, Coordinate_bancarie)
VALUES ('VRDLGU67D10F205E','Luigi','Verdi','1967-04-10', 'lverdi@gmail.com', 816830320, 'Piazza Italia', 56, 'Milano', '4321');
INSERT INTO CLIENTI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Email, Numero_di_telefono, Via_residenza, Numero_civ_residenza, Citta_residenza, Coordinate_bancarie)
VALUES ('VRDLNE69P62F839Z','Elena','Verdi','1969-09-14', 'elena.verdi@gmail.com', 846257382, 'Via Dante', 22, 'Napoli', '0000');
INSERT INTO CLIENTI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Email, Numero_di_telefono, Via_residenza, Numero_civ_residenza, Citta_residenza, Coordinate_bancarie)
VALUES ('NREGDU00P22C573P','Guido','Neri','2000-09-22', 'superguido@gmail.com', 141835857, 'Via Universita', 20, 'Cesena', '56789');
INSERT INTO CLIENTI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Email, Numero_di_telefono, Via_residenza, Numero_civ_residenza, Citta_residenza, Coordinate_bancarie)
VALUES ('RSSNNA98T41H294S','Anna','Russo','1998-12-01', 'anna.russo3@alice.com', 261945077,'Piazza Kennedy', 3, 'Rimini', '47843');

#spese
INSERT INTO SPESE (Costo, Cod_cliente)
VALUES (46.97,1);

INSERT INTO SPESE (Costo, Cod_cliente)
VALUES (21.67,2);

INSERT INTO SPESE (Costo, Cod_cliente)
VALUES (57.48,3);

INSERT INTO SPESE (Costo, Cod_cliente)
VALUES (30.99,4);

INSERT INTO SPESE (Costo, Cod_cliente)
VALUES (30.99,4);

#produttori
INSERT INTO PRODUTTORI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Partita_IVA)
VALUES ('RCCNTN70M30C352J','Antonio','Ricci','1970-08-30', '29172320250');
INSERT INTO PRODUTTORI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Partita_IVA)
VALUES ('GLLCRL65L23G942Q','Carlo','Gallo','1965-07-23', '79112460963');
INSERT INTO PRODUTTORI (Codice_fiscale, Nome, Cognome, Data_di_nascita, Partita_IVA)
VALUES ('RMNGPP76H15G702Q','Giuseppe','Romano','1976-06-15', '66390230358');

#fabbriche
INSERT INTO FABBRICHE (Via, Numero_civ, Citta)
VALUES ('Via Lazio', '34', 'Bologna');
INSERT INTO FABBRICHE (Via, Numero_civ, Citta)
VALUES ('Via Lombardia', '46', 'Torino');
INSERT INTO FABBRICHE (Via, Numero_civ, Citta)
VALUES ('Via Umbria', '3', 'Pavia');
INSERT INTO FABBRICHE (Via, Numero_civ, Citta)
VALUES ('Via Aquila', '65', 'Reggio Emilia');
INSERT INTO FABBRICHE (Via, Numero_civ, Citta)
VALUES ('Via Riccione', '8', 'Roma');

#gestioni
INSERT INTO GESTIONE_FABBRICHE (Cod_fabbrica, Data_inizio, Data_fine, Cod_produttore)
VALUES (1,'1990-01-01', '2000-01-01', 1);
INSERT INTO GESTIONE_FABBRICHE (Cod_fabbrica, Data_inizio, Cod_produttore)
VALUES (1,'2000-01-02', 2);
INSERT INTO GESTIONE_FABBRICHE (Cod_fabbrica, Data_inizio, Cod_produttore)
VALUES (2,'1990-01-01', 1);
INSERT INTO GESTIONE_FABBRICHE (Cod_fabbrica, Data_inizio, Cod_produttore)
VALUES (3,'1990-01-01', 2);
INSERT INTO GESTIONE_FABBRICHE (Cod_fabbrica, Data_inizio, Cod_produttore)
VALUES (4,'2000-02-01', 3);
INSERT INTO GESTIONE_FABBRICHE (Cod_fabbrica, Data_inizio, Cod_produttore)
VALUES (5,'1980-01-01', 3);

#prodotti
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Seta','Vestito estivo', 3, 4);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Cotone','Camicia a Maniche corte', 3, 4);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Lino','Maglietta per donna', 3, 4);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Lycra','Costume estivo pantaloncino', 2, 3);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Lycra','Bikini rosso', 2, 3);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Lycra','Costume intero', 2, 3);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Organico','Giardiniera in barattolo', 1, 2);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Organico','Cetriolini sott aceto', 1, 2);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Organico','Funghi secchi', 2, 1);
INSERT INTO PRODOTTI(Materiale, Descrizione, Cod_produttore, Cod_fabbrica)
VALUES ('Organico','Pasta secca, fusilli', 3, 5);

#prodotti in vendita
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (10.99, '2022-05-01', 'Vestiario', 'XS', 1);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (10.99, '2022-05-01', 'Vestiario', 'S', 1);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (10.99, '2022-05-01', 'Vestiario', 'M', 1);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (12.99, '2022-05-01', 'Vestiario', 'L', 1);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Data_fine, Tipo, Taglia, Cod_prodotto)
VALUES (12.99, '2022-05-01','2022-06-01', 'Vestiario', 'XL', 1);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (15.99, '2022-06-10', 'Vestiario', 'M', 2);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (15.99, '2022-06-10', 'Vestiario', 'L', 2);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (10.99, '2022-05-01', 'Vestiario', 'S', 3);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Data_fine, Tipo, Taglia, Cod_prodotto)
VALUES (19.99, '2022-04-15','2022-04-30', 'Vestiario', 'M', 4);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Data_fine, Tipo, Taglia, Cod_prodotto)
VALUES (19.99, '2022-04-15','2022-04-30', 'Vestiario', 'S', 4);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (29.99, '2022-05-01', 'Vestiario', 'M', 4);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (29.99, '2022-05-01', 'Vestiario', 'S', 4);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (7.99, '2022-06-01', 'Vestiario', 'Unica', 5);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Taglia, Cod_prodotto)
VALUES (24.99, '2022-06-01', 'Vestiario', 'S', 6);
#alimentare
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Scadenza, Cod_prodotto)
VALUES(5.59, '2022-06-01','Alimentare', '2023-01-01',7);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Scadenza, Cod_prodotto)
VALUES(7.99, '2022-06-01','Alimentare', '2023-01-01',8);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Scadenza, Cod_prodotto)
VALUES(5.59, '2022-06-01','Alimentare', '2023-06-01',9);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Data_fine, Tipo, Scadenza, Cod_prodotto)
VALUES(5.59, '2022-01-01','2022-05-30','Alimentare', '2022-06-01',9);
INSERT INTO PRODOTTI_IN_VENDITA(Prezzo, Data_inizio, Tipo, Scadenza, Cod_prodotto)
VALUES(9.99, '2022-04-01','Alimentare','2023-07-01',10);

#contenuto
INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(3, 1);
INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(6, 1);
INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(9, 1);

INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(15, 2);
INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(16, 2);
INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(17, 2);

INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(14, 3);
INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(12, 3);

INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(12, 4);
INSERT INTO CONTENUTO(Cod_prodotto_vendita, Cod_spesa)
VALUES(11, 5);

#corrieri
INSERT INTO CORRIERI(Codice_fiscale, Nome, Cognome, Data_di_nascita, Cod_patente, Nazionalita_patente)
VALUES('CSTNDR70A01D704R', 'Andrea', 'Costa', '1970-01-01', '123ABC', 'Italia');
INSERT INTO CORRIERI(Codice_fiscale, Nome, Cognome, Data_di_nascita, Cod_patente, Nazionalita_patente)
VALUES('FRRFRC80D24D548Z', 'Federico', 'Ferrara', '1980-04-24', '345LMN', 'San Marino');
INSERT INTO CORRIERI(Codice_fiscale, Nome, Cognome, Data_di_nascita, Cod_patente, Nazionalita_patente)
VALUES('RMORCR68D05D969I', 'Riccardo', 'Roma', '1968-04-05', '678RMR', 'Italia');
INSERT INTO CORRIERI(Codice_fiscale, Nome, Cognome, Data_di_nascita, Cod_patente, Nazionalita_patente)
VALUES('JNSJMS78T12Z106U', 'James', 'Jones', '1978-12-12', '456JJJ', 'UK');

#indirizzi
INSERT INTO INDIRIZZI(Via, Numero_civico, Citta, CAP, Provincia, Paese)
VALUES('Via Roma', 4, 'Roma', 00042, 'RM', 'Italia');

INSERT INTO INDIRIZZI(Via, Numero_civico, Citta, CAP, Provincia, Paese)
VALUES('Via Italia', 35, 'San Marino', 47890, '-', 'San Marino');

INSERT INTO INDIRIZZI(Via, Numero_civico, Citta, CAP, Provincia, Paese)
VALUES('Via Verdi', 20, 'Torino', 10094, 'TO', 'Italia');

INSERT INTO INDIRIZZI(Via, Numero_civico, Citta, CAP, Provincia, Paese)
VALUES('Via Cavour', 5, 'Milano', 20019, 'ML', 'Italia');

#mezzi
INSERT INTO MEZZI(Targa, Paese_immatricolazione, Marca, Tipo_veicolo)
VALUES('AB123CD', 'Italia', 'Toyota', 'Furgone');

INSERT INTO MEZZI(Targa, Paese_immatricolazione, Marca, Tipo_veicolo)
VALUES('EE666EE', 'San Marino', 'Ford', 'Furgone');

INSERT INTO MEZZI(Targa, Paese_immatricolazione, Marca, Tipo_veicolo)
VALUES('CC321JK', 'Italia', 'Volvo', 'Camion');

INSERT INTO MEZZI(Targa, Paese_immatricolazione, Marca, Tipo_veicolo)
VALUES('UK356LL', 'UK', 'Nissan', 'Furgone');

#guide
INSERT INTO GUIDE(Data_inizio, Data_fine, Ora_inizio, Ora_fine, Cod_corriere, Targa)
VALUES('2022-01-06', '2022-01-08', 8.30, 19.40, 1, 'CC321JK');

INSERT INTO GUIDE(Data_inizio, Data_fine, Ora_inizio, Ora_fine, Cod_corriere, Targa)
VALUES('2022-05-12', '2022-05-12', 8.30, 12.30, 2, 'EE666EE');

INSERT INTO GUIDE(Data_inizio, Ora_inizio, Cod_corriere, Targa)
VALUES('2022-06-01', 8.30, 1, 'CC321JK');

INSERT INTO GUIDE(Data_inizio, Data_fine, Ora_inizio, Ora_fine, Cod_corriere, Targa)
VALUES('2022-06-01', '2022-06-15', 8.30, 19.30, 3, 'EE666EE');

INSERT INTO GUIDE(Data_inizio, Ora_inizio, Cod_corriere, Targa)
VALUES('2022-06-20', 8.30, 4, 'AB123CD');

#consegne
INSERT INTO CONSEGNE(Cod_spesa, Costo_consegna, Tipo, Cod_indirizzo)
VALUES(1, 1.00, 'Standard', 2);

INSERT INTO CONSEGNE(Cod_spesa, Costo_consegna, Data, Tipo, Cod_indirizzo, Cod_corriere, Targa)
VALUES(2, 2.50, '2022-06-02', 'Premium', 3, 1, 'CC321JK');

INSERT INTO CONSEGNE(Cod_spesa, Costo_consegna, Data, Tipo, Cod_indirizzo, Cod_corriere, Targa)
VALUES(3, 2.50, '2022-06-03','Premium', 4, 3,'CC321JK');

INSERT INTO CONSEGNE(Cod_spesa, Costo_consegna, Data, Tipo, Cod_indirizzo, Cod_corriere, Targa)
VALUES(4, 1.00, '2022-06-21', 'Standard', 4, 4, 'AB123CD');

INSERT INTO CONSEGNE(Cod_spesa, Costo_consegna, Data, Tipo, Cod_indirizzo, Cod_corriere, Targa)
VALUES(5, 1.00, '2022-01-07', 'Standard', 1, 1, 'CC321JK');

