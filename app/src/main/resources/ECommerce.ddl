


-- Database Section
-- ________________ 

--create database Ecommerce;
use Ecommerce;


-- Tables Section
-- _____________ 

create table CLIENTI (
     Cod_cliente int not null auto_increment,
     Codice_fiscale char(16) not null,
     Nome varchar(20) not null,
     Cognome varchar(20) not null,
     Data_di_nascita date not null,
     Email varchar(50) not null,
     Numero_di_telefono int not null,
     Via_residenza varchar(50) not null,
     Numero_civ_residenza bigint not null,
     Citta_residenza varchar(50) not null,
     Coordinate_bancarie varchar(25) not null,
     constraint IDCLIENTE primary key (Cod_cliente));

create table CONSEGNE (
     Cod_consegna int not null auto_increment,
     Cod_spesa bigint,
     Costo_consegna decimal(6,2) not null,
     Data date,
     Tipo varchar(20) not null,
     Cod_indirizzo int not null,
     Cod_corriere int,
     Targa varchar(15),
     constraint IDCONSEGNA primary key (Cod_consegna),
     constraint FKRECAPITATO_ID unique (Cod_spesa));

create table CONTENUTO (
     Cod_prodotto_vendita int not null,
     Cod_spesa bigint not null,
     constraint IDCONTENUTO primary key (Cod_prodotto_vendita, Cod_spesa));

create table CORRIERI (
     Cod_corriere int not null auto_increment,
     Codice_fiscale char(16) not null,
     Nome varchar(20) not null,
     Cognome varchar(20) not null,
     Data_di_nascita date not null,
     Cod_patente varchar(20) not null,
     Nazionalita_patente varchar(25) not null,
     constraint IDCORRIERE_1 primary key (Cod_corriere));

create table FABBRICHE (
     Cod_fabbrica int not null auto_increment,
     Via varchar(50) not null,
     Numero_civ varchar(10) not null,
     Citta varchar(25) not null,
     constraint IDFABBRICA_ID primary key (Cod_fabbrica));

create table GESTIONE_FABBRICHE (
     Cod_gestione bigint not null auto_increment,
     Cod_fabbrica int not null,
     Data_inizio date not null,
     Data_fine date,
     Cod_produttore int not null,
     constraint IDGESTISTIONE_FABBRICA primary key (Cod_gestione));
     #constraint FKAMMINISTRAZIONE_ID unique (Cod_fabbrica));

create table GUIDE (
     Cod_guida bigint not null auto_increment,
     Data_inizio date not null,
     Data_fine date,
     Ora_inizio decimal(4,2) not null,
     Ora_fine decimal(4,2),
     Cod_corriere int not null,
     Targa varchar(15) not null,
     constraint IDGUIDA primary key (Cod_guida));

create table INDIRIZZI (
     Cod_indirizzo int not null auto_increment,
     Via varchar(25) not null,
     Numero_civico int not null,
     Citta varchar(25) not null,
     CAP int not null,
     Provincia varchar(25) not null,
     Paese varchar(25) not null,
     constraint IDINDIRIZZO primary key (Cod_indirizzo));

create table MEZZI (
     Targa varchar(15) not null,
     Paese_immatricolazione varchar(25) not null,
     Marca varchar(25) not null,
     Tipo_veicolo varchar(25) not null,
     constraint IDMEZZO primary key (Targa));

create table PRODOTTI (
     Cod_prodotto int not null auto_increment,
     Materiale varchar(25) not null,
     Descrizione varchar(100) not null,
     Cod_produttore int not null,
     Cod_fabbrica int not null,
     constraint IDPRODOTTO primary key (Cod_prodotto));

create table PRODOTTI_IN_VENDITA (
     Cod_prodotto_vendita int not null auto_increment,
     Prezzo decimal(6,2) not null,
     Data_inizio date not null,
     Data_fine date,
     Tipo varchar(15) not null,
     Scadenza date,
     Taglia varchar(10),
     Cod_prodotto int not null,
     constraint IDPRODOTTO_IN_VENDITA primary key (Cod_prodotto_vendita));

create table PRODUTTORI (
     Cod_produttore int not null auto_increment,
     Codice_fiscale char(16) not null,
     Nome varchar(20) not null,
     Cognome varchar(20) not null,
     Data_di_nascita date not null,
     Partita_IVA varchar(25) not null,
     constraint IDPRODUTTORE primary key (Cod_produttore));

create table SPESE (
     Cod_spesa bigint not null auto_increment,
     Costo decimal(6,2) not null,
     Cod_cliente int not null,
     constraint IDSPESA_ID primary key (Cod_spesa));


-- Constraints Section
-- ___________________ 

alter table CONSEGNE add constraint FKRECAPITATO_FK
     foreign key (Cod_spesa)
     references SPESE (Cod_spesa);

alter table CONSEGNE add constraint FKRIFERIMENTO
     foreign key (Cod_indirizzo)
     references INDIRIZZI (Cod_indirizzo);

alter table CONSEGNE add constraint FKCOMPIMENTO
     foreign key (Cod_corriere)
     references CORRIERI (Cod_corriere);

alter table CONSEGNE add constraint FKEFFETTUAZIONE
     foreign key (Targa)
     references MEZZI(Targa);

alter table CONTENUTO add constraint FKCON_SPE
     foreign key (Cod_spesa)
     references SPESE (Cod_spesa);

alter table CONTENUTO add constraint FKCON_PRO
     foreign key (Cod_prodotto_vendita)
     references PRODOTTI_IN_VENDITA (Cod_prodotto_vendita);

-- Not implemented
-- alter table FABBRICHE add constraint IDFABBRICA_CHK
--     check(exists(select * from GESTISTIONE FABBRICHE
--                  where GESTISTIONE FABBRICHE.Cod_fabbrica = Cod_fabbrica)); 

alter table GESTIONE_FABBRICHE add constraint FKGESTIONE
     foreign key (Cod_produttore)
     references PRODUTTORI (Cod_produttore);

alter table GESTIONE_FABBRICHE add constraint FKAMMINISTRAZIONE_FK
     foreign key (Cod_fabbrica)
     references FABBRICHE (Cod_fabbrica);

alter table GUIDE add constraint FKCONDUCENTE
     foreign key (Cod_corriere)
     references CORRIERI (Cod_corriere);

alter table GUIDE add constraint FKUTILIZZO
     foreign key (Targa)
     references MEZZI (Targa);

alter table PRODOTTI add constraint FKFABBRICAZIONE
     foreign key (Cod_produttore)
     references PRODUTTORI (Cod_produttore);

alter table PRODOTTI add constraint FKPRODUZIONE
     foreign key (Cod_fabbrica)
     references FABBRICHE (Cod_fabbrica);

alter table PRODOTTI_IN_VENDITA add constraint FKCOMMERCIALIZZATO
     foreign key (Cod_prodotto)
     references PRODOTTI(Cod_prodotto);

-- Not implemented
-- alter table SPESE add constraint IDSPESA_CHK
--     check(exists(select * from CONSEGNE
--                  where CONSEGNE.Cod_spesa = Cod_spesa)); 

alter table SPESE add constraint FKPAGAMENTO
     foreign key (Cod_cliente)
     references CLIENTI (Cod_cliente);


-- Index Section
-- _____________ 

