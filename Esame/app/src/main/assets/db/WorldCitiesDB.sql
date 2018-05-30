BEGIN TRANSACTION;
DROP TABLE IF EXISTS `tblStato`;
CREATE TABLE IF NOT EXISTS `tblStato` (
	`IdStato`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`NomeStato`	TEXT NOT NULL,
	`CodiceDue`	TEXT NOT NULL UNIQUE,
	`CodiceTre`	TEXT NOT NULL UNIQUE,
	`Bandiera`	TEXT NOT NULL UNIQUE
);
INSERT INTO `tblStato` (IdStato,NomeStato,CodiceDue,CodiceTre,Bandiera) VALUES (1,'Italia','IT','ITA','0');
INSERT INTO `tblStato` (IdStato,NomeStato,CodiceDue,CodiceTre,Bandiera) VALUES (2,'Francia','FR','FRA','1');
INSERT INTO `tblStato` (IdStato,NomeStato,CodiceDue,CodiceTre,Bandiera) VALUES (3,'Stati Uniti d''America','US','USA','2');
INSERT INTO `tblStato` (IdStato,NomeStato,CodiceDue,CodiceTre,Bandiera) VALUES (4,'Giappone','JP','JAP','3');
INSERT INTO `tblStato` (IdStato,NomeStato,CodiceDue,CodiceTre,Bandiera) VALUES (5,'Regno Unito','GB','GBR','4');
INSERT INTO `tblStato` (IdStato,NomeStato,CodiceDue,CodiceTre,Bandiera) VALUES (6,'Germania','DE','DEU','5');
DROP TABLE IF EXISTS `tblCitta`;
CREATE TABLE IF NOT EXISTS `tblCitta` (
	`IdCitta`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`NomeCitta`	TEXT NOT NULL,
	`Popolazione`	INTEGER NOT NULL,
	`IdStato`	INTEGER NOT NULL,
	`IsCapitale`	INTEGER NOT NULL,
	`Latitudine`	REAL NOT NULL,
	`Longitudine`	REAL NOT NULL,
	FOREIGN KEY(`IdStato`) REFERENCES `tblStato`(`IdStato`)
);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (1,'Roma',2873874,1,1,41.88334,12.5);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (2,'Milano',1366037,1,0,45.46667,9.183333);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (3,'Parigi',2206488,2,1,48.86666,2.333333);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (4,'Venezia',261401,1,0,45.43713,12.33265);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (5,'Torino',883281,1,0,45.07049,7.68682);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (6,'Napoli',966425,1,0,40.85631,14.24641);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (7,'Palermo',668630,1,0,38.13205,13.33561);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (8,'Berlino',3531201,6,1,52.52437,13.41053);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (9,'Monaco di Baviera',1528849,6,0,48.13743,11.57549);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (10,'Colonia',1057327,6,0,50.93333,6.95);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (11,'Francoforte sul Meno
',736414,6,0,50.11552,8.68417);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (12,'Lione',513275,2,0,45.74846,4.84671);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (13,'Marsiglia',858120,2,0,43.29695,5.38107);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (14,'Cannes',74626,2,0,43.55135,7.01275);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (15,'Avignone',91451,2,0,43.94834,4.80892);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (16,'New York',8550405,3,0,40.71427,-74.00597);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (17,'Washington',672228,3,1,38.89511,-77.03637);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (18,'Dallas',1197816,3,0,32.78306,-96.80667);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (19,'Boston',617594,3,0,42.35843,-71.05977);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (20,'Los Angeles',4057875,3,0,34.05223,-118.24368);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (21,'Philadelfia',1560297,3,0,44.1545,-75.70882);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (22,'Tokyo',13742906,4,1,35.6895,139.69171);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (23,'Kyoto','1.472.027 ',4,0,35.02107,135.75385);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (24,'Nara',358.915,4,0,34.68505,135.80485);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (25,'Hiroshima','1.185.849',4,0,34.4,132.45);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (26,'Londra','8 787 892',5,1,51.50853,-0.12574);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (27,'Manchester','521 987',5,0,53.48095,-2.23743);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (28,'Liverpool','578 324',5,0,53.41058,-2.97794);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (29,'Oxford','153 700',5,0,51.75222,-1.25596);
INSERT INTO `tblCitta` (IdCitta,NomeCitta,Popolazione,IdStato,IsCapitale,Latitudine,Longitudine) VALUES (30,'Birmingham','1 111 300',5,0,52.48142,-1.89983);
COMMIT;
