-- Adminer 4.7.8 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DELIMITER ;;

DROP FUNCTION IF EXISTS `zakaznik_aktivni`;;
CREATE FUNCTION `zakaznik_aktivni`(`zakaznik_id` int(11)) RETURNS tinyint(1)
BEGIN

DECLARE active smallint(3);
DECLARE expire_date datetime;

IF zakaznik_id IS NOT NULL THEN

SELECT platnost_do INTO expire_date FROM zakaznik WHERE id = zakaznik_id;

IF expire_date is null OR expire_date < CURDATE() then
  SET active = 0;

ELSE 
 SET active = 1;
END IF;

END IF;

IF active is null then
  SET active = 0;

END IF;

RETURN (active);

END;;

DROP FUNCTION IF EXISTS `zapsane_udalosti`;;
CREATE FUNCTION `zapsane_udalosti`(`zakaznik_id` int(11)) RETURNS smallint
BEGIN

DECLARE ret smallint(3);

IF zakaznik_id IS NOT NULL THEN

SELECT COUNT(*) INTO ret FROM udalost_zakaznik WHERE zakaznik = zakaznik_id;

END IF;

IF ret IS NULL THEN
 SET ret = 0;
END IF;

RETURN (ret) ; 

END;;

DROP PROCEDURE IF EXISTS `aktivace_predplatne`;;
CREATE DEFINER=`habr`@`localhost` PROCEDURE `aktivace_predplatne`(IN `p_typ_predobjednani` INT(11), IN `p_zakaznik` INT(11))
BEGIN

DECLARE active_date datetime;
DECLARE days_add int(11);
DECLARE p_cena float;

IF p_typ_predobjednani is not null and p_zakaznik is not null THEN

  SELECT cena INTO p_cena FROM typ_predobjednani WHERE id = p_typ_predobjednani;

  SELECT doba_platnosti INTO days_add FROM typ_predobjednani WHERE id = p_typ_predobjednani;

  INSERT INTO predobjednano (cena, typ_predobjednani, zakaznik) VALUES (p_cena, p_typ_predobjednani, p_zakaznik);

  SELECT platnost_do into active_date FROM zakaznik WHERE id = p_zakaznik;

  IF active_date is null THEN

     UPDATE zakaznik SET platnost_do = DATE_ADD(CURDATE(), INTERVAL days_add DAY) WHERE id = p_zakaznik;

  ELSE
     UPDATE zakaznik SET platnost_do = DATE_ADD(platnost_do, INTERVAL days_add DAY) WHERE id = p_zakaznik;

  END IF;

END IF;


END;;

DROP PROCEDURE IF EXISTS `registrace_zakaznik`;;
CREATE DEFINER=`habr`@`localhost` PROCEDURE `registrace_zakaznik`(IN `p_datum_narozeni` DATETIME, IN `p_email` VARCHAR(50) CHARACTER SET 'utf8', IN `p_jmeno` VARCHAR(20) CHARACTER SET 'utf8', IN `p_prijmeni` VARCHAR(30) CHARACTER SET 'utf8', IN `p_rodne_cislo` VARCHAR(10) CHARACTER SET 'utf8', IN `p_telefon` CHAR(13) CHARACTER SET 'utf8', IN `p_mesto` VARCHAR(30) CHARACTER SET 'utf8', IN `p_stat` VARCHAR(30) CHARACTER SET 'utf8', IN `p_ulice` VARCHAR(50) CHARACTER SET 'utf8', IN `p_psc` CHAR(5) CHARACTER SET 'utf8', IN `p_username` VARCHAR(30) CHARACTER SET 'utf8', IN `p_password` VARCHAR(100) CHARACTER SET 'utf8')
BEGIN

DECLARE login_id INT;
DECLARE adresa_id INT;

IF p_datum_narozeni IS NOT NULL AND p_email IS NOT NULL AND p_jmeno IS NOT NULL AND p_prijmeni IS NOT NULL AND p_mesto IS NOT NULL
AND p_stat IS NOT NULL AND p_ulice IS NOT NULL AND p_psc IS NOT NULL AND p_username IS NOT NULL AND p_password IS NOT NULL THEN

INSERT INTO login (username, password) VALUES (p_username, p_password);
SET login_id = LAST_INSERT_ID();

INSERT INTO adresa (mesto, stat, ulice, psc) VALUES (p_mesto, p_stat, p_ulice, p_psc);
SET adresa_id = LAST_INSERT_ID();

INSERT INTO zakaznik (datum_narozeni, email, jmeno, prijmeni, rodne_cislo, telefon, adresa, login) VALUES
(p_datum_narozeni, p_email, p_jmeno, p_prijmeni, p_rodne_cislo, p_telefon, adresa_id, login_id);

END IF;

END;;

DROP PROCEDURE IF EXISTS `registrace_zamestnanec`;;
CREATE DEFINER=`habr`@`localhost` PROCEDURE `registrace_zamestnanec`(IN `p_datum_narozeni` DATETIME, IN `p_email` VARCHAR(50) CHARACTER SET 'utf8', IN `p_jmeno` VARCHAR(20) CHARACTER SET 'utf8', IN `p_prijmeni` VARCHAR(30) CHARACTER SET 'utf8', IN `p_rodne_cislo` VARCHAR(10) CHARACTER SET 'utf8', IN `p_telefon` CHAR(13) CHARACTER SET 'utf8', IN `p_mesto` VARCHAR(30) CHARACTER SET 'utf8', IN `p_stat` VARCHAR(30) CHARACTER SET 'utf8', IN `p_ulice` VARCHAR(50) CHARACTER SET 'utf8', IN `p_psc` CHAR(5) CHARACTER SET 'utf8', IN `p_username` VARCHAR(30) CHARACTER SET 'utf8', IN `p_password` VARCHAR(100) CHARACTER SET 'utf8', IN `p_role` INT(11))
BEGIN

DECLARE login_id INT;
DECLARE adresa_id INT;

IF p_datum_narozeni IS NOT NULL AND p_email IS NOT NULL AND p_jmeno IS NOT NULL AND p_prijmeni IS NOT NULL AND p_rodne_cislo IS NOT NULL AND p_mesto IS NOT NULL
AND p_telefon IS NOT NULL AND p_stat IS NOT NULL AND p_ulice IS NOT NULL AND p_psc IS NOT NULL AND p_username IS NOT NULL AND p_password IS NOT NULL AND p_role IS NOT NULL THEN

INSERT INTO login (username, password) VALUES (p_username, p_password);
SET login_id = LAST_INSERT_ID();

INSERT INTO adresa (mesto, stat, ulice, psc) VALUES (p_mesto, p_stat, p_ulice, p_psc);
SET adresa_id = LAST_INSERT_ID();

INSERT INTO zamestnanci (datum_narozeni, email, jmeno, prijmeni, rodne_cislo, telefon, adresa, login, role) VALUES
(p_datum_narozeni, p_email, p_jmeno, p_prijmeni, p_rodne_cislo, p_telefon, adresa_id, login_id, p_role);

END IF;

END;;

DELIMITER ;

DROP TABLE IF EXISTS `adresa`;
CREATE TABLE `adresa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mesto` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `stat` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `ulice` varchar(50) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `psc` char(5) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `adresa` (`id`, `mesto`, `stat`, `ulice`, `psc`) VALUES
(3,	'Starý Bydžov',	'Česká republika',	'Pražská 20',	'52127'),
(5,	'Náchod',	'Česko',	'Raichlové 855',	'54701'),
(6,	'Náchod',	'Česko',	'Raichlové 852',	'54701'),
(7,	'Jelev',	'Česko',	'Dlouhá 15',	'65321'),
(8,	'Náchod',	'Česko',	'Raichlové 855',	'54701'),
(9,	'Náchod',	'Česko',	'Test 32',	'54701'),
(10,	'Náchod',	'Česko',	'Raichlové 855',	'54701'),
(11,	'Starý Bydžov',	'Česko',	'Pod Strání 32',	'65874'),
(12,	'Zábor',	'Česko',	'Bílá 65',	'88530'),
(13,	'Tábor',	'Česko',	'Bílá 65',	'98862'),
(14,	'Hradec Králové',	'Česko',	'Nábřeží 54',	'69920'),
(15,	'Rychnov nad Kněžnou',	'Česko',	'Zelená 32',	'74521'),
(16,	'Hradec Králové',	'Česká Republika',	'Dlouhá 15',	'54878'),
(17,	'Náchod',	'Česko',	'Raichlové 855',	'54701'),
(18,	'Náchod',	'Česko',	'Raichlové 855',	'54701'),
(19,	'Domažlice',	'Česko',	'Požární 65',	'84521');

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `login` (`id`, `password`, `username`) VALUES
(3,	'testheslo',	'holfa5'),
(5,	'heslo123',	'habron'),
(6,	'$2a$10$3mll3vZxjTTmqj0LP9pLzOzrupp..d4EKizCDQ1i1Ax4Gg4IVg7hW',	'habron2'),
(13,	'$2a$10$Ai9wlzjz8rcdBZrmELRMmeuVW4/K9i929OMFqTbj4aUnmeB8ea762',	'test1'),
(14,	'$2a$10$jDOUozHcR2oAG4eCzRFzu.C1npTa6mZaFR8VPDBYeSD/TA/z8h91S',	'admin'),
(17,	'$2a$10$jDOUozHcR2oAG4eCzRFzu.C1npTa6mZaFR8VPDBYeSD/TA/z8h91S',	'lektor'),
(18,	'$2a$10$2lJm.pP2TbzYz3nfcbYFiOb055/ACorXLJePG6.CPWOy6OiEYTYs2',	'uzivatel'),
(19,	'$2a$10$jDOUozHcR2oAG4eCzRFzu.C1npTa6mZaFR8VPDBYeSD/TA/z8h91S',	'manazer'),
(20,	'$2a$10$Q/HpPVLihD7nDj8DYaBGvOMpNJ1M3VjVWCwJPw.S1m8ixxGdvgYIa',	'uzivatel1'),
(21,	'$2a$10$oB0IfmO4E/vWqoJK4.fHFue6CE/m0Sz98S68sr/Xmu6nXtHKtYVCu',	'instruktor4'),
(22,	'$2a$10$iIKWDSVPFSzR.F2lsBAyouw6m4/5rDviX4sO.NoN9p06TSMoGIoua',	'lektor54'),
(23,	'$2a$10$jDOUozHcR2oAG4eCzRFzu.C1npTa6mZaFR8VPDBYeSD/TA/z8h91S',	'pavel');

DROP TABLE IF EXISTS `mistnost`;
CREATE TABLE `mistnost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `kapacita` int NOT NULL,
  `typ_mistnosti` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `typ_mistnosti` (`typ_mistnosti`),
  CONSTRAINT `mistnost_ibfk_1` FOREIGN KEY (`typ_mistnosti`) REFERENCES `typ_mistnosti` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `mistnost` (`id`, `kapacita`, `typ_mistnosti`) VALUES
(6,	20,	2),
(7,	50,	3),
(9,	40,	1),
(11,	50,	3);

DROP VIEW IF EXISTS `mistnost_view`;
CREATE TABLE `mistnost_view` (`m_id` int, `kapacita` int, `id` int, `nazev` varchar(30), `popis` text, `provozni_rad` text);


DROP TABLE IF EXISTS `predobjednano`;
CREATE TABLE `predobjednano` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cena` float NOT NULL,
  `objednano` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `typ_predobjednani` int DEFAULT NULL,
  `zakaznik` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `typ_predobjednani` (`typ_predobjednani`),
  KEY `zakaznik` (`zakaznik`),
  CONSTRAINT `predobjednano_ibfk_1` FOREIGN KEY (`typ_predobjednani`) REFERENCES `typ_predobjednani` (`id`) ON DELETE SET NULL,
  CONSTRAINT `predobjednano_ibfk_2` FOREIGN KEY (`zakaznik`) REFERENCES `zakaznik` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `predobjednano` (`id`, `cena`, `objednano`, `typ_predobjednani`, `zakaznik`) VALUES
(1,	350,	'2019-05-08 17:47:16',	1,	9),
(2,	350,	'2019-05-08 17:47:51',	1,	5),
(3,	350,	'2019-05-08 18:52:31',	1,	9),
(4,	350,	'2019-05-08 18:56:24',	1,	9),
(5,	590,	'2019-05-08 19:48:49',	2,	9),
(6,	350,	'2019-05-09 12:43:21',	1,	10),
(7,	350,	'2019-05-09 14:29:03',	1,	11),
(8,	590,	'2019-05-09 14:30:30',	2,	11),
(9,	350,	'2019-05-09 14:30:38',	1,	11),
(10,	350,	'2021-01-17 21:06:10',	1,	12),
(11,	350,	'2021-01-17 21:06:31',	1,	12);

DROP VIEW IF EXISTS `prehled_view`;
CREATE TABLE `prehled_view` (`od` datetime, `do` datetime, `kapacita` int, `nazev` varchar(80), `popis` text, `zamestnanec` varchar(61), `mistnost` varchar(30), `prihlaseni` bigint);


DROP TABLE IF EXISTS `profilove_obrazky`;
CREATE TABLE `profilove_obrazky` (
  `id` int NOT NULL AUTO_INCREMENT,
  `obrazek` longblob NOT NULL,
  `pripona` varchar(20) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `zakaznik` int NOT NULL,
  `nazev` varchar(50) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `zakaznik` (`zakaznik`),
  CONSTRAINT `profilove_obrazky_ibfk_1` FOREIGN KEY (`zakaznik`) REFERENCES `zakaznik` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nazev` varchar(20) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `role` varchar(20) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `role` (`id`, `nazev`, `role`) VALUES
(1,	'Manažer',	'ROLE_MANAGER'),
(2,	'Instruktor',	'ROLE_INSTRUCTOR'),
(3,	'Zákazník',	'ROLE_CUSTOMER'),
(4,	'Admin',	'ROLE_ADMIN');

DROP TABLE IF EXISTS `satna`;
CREATE TABLE `satna` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cislo_skrine` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `satna` (`id`, `cislo_skrine`) VALUES
(1,	100),
(2,	101),
(4,	103),
(5,	104),
(6,	105);

DROP TABLE IF EXISTS `satna_historie`;
CREATE TABLE `satna_historie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datum` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `satna` int NOT NULL,
  `zakaznik` int NOT NULL,
  `do` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `satna` (`satna`),
  KEY `zakaznik` (`zakaznik`),
  CONSTRAINT `satna_historie_ibfk_1` FOREIGN KEY (`satna`) REFERENCES `satna` (`id`) ON DELETE CASCADE,
  CONSTRAINT `satna_historie_ibfk_2` FOREIGN KEY (`zakaznik`) REFERENCES `zakaznik` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `satna_historie` (`id`, `datum`, `satna`, `zakaznik`, `do`) VALUES
(1,	'2019-05-08 15:29:21',	1,	3,	3),
(2,	'2019-05-08 16:09:48',	2,	5,	1),
(3,	'2019-05-09 12:38:45',	1,	10,	1),
(4,	'2019-05-09 14:40:47',	1,	11,	2);

DROP TABLE IF EXISTS `typ_mistnosti`;
CREATE TABLE `typ_mistnosti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nazev` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `popis` text CHARACTER SET utf8 COLLATE utf8_czech_ci,
  `provozni_rad` text CHARACTER SET utf8 COLLATE utf8_czech_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `typ_mistnosti` (`id`, `nazev`, `popis`, `provozni_rad`) VALUES
(1,	'Zrcadlový sál',	'Tělocvična se zrcadly',	'Test\r\ntest'),
(2,	'Posilovna',	'',	'Cvičení v naší posilovně je určeno všem návštěvníkům, kteří jsou ochotni akceptovat tato základní pravidla:\r\n\r\nNávštěvník Fittsportu je povinen řídit se provozním řádem posilovny a pokyny obsluhy.\r\nKaždý návštěvník je plně zodpovědný za svůj zdravotní stav. Každý návštěvník cvičí na svoji zodpovědnost!\r\nZakoupením (jednorázového vstupu, členské karty) návštěvník stvrzuje, že byl seznámen a porozuměl znění tohoto řádu a zavazuje se ho dodržovat.\r\nCvičenec musí dosáhnout věku minimálně 15ti let. Je-li mladší (7 – 14 let), je mu vstup do posilovny povolen pouze v doprovodu osoby starší 18 let (rodiče, sourozenci) nebo trenéra. Tato osoba potom přebírá plně zodpovědnost za bezpečí nezletilého cvičence, je povinna se mu neustále věnovat a nenechat jej bez dozoru.\r\nPro malé děti (do 7 let) je prostředí posilovny nebezpečné a tudíž absolutně nevhodné!  Přivede-li však návštěvník posilovny takto malé dítě, je povinen zamezit dítěti, aby se volně pohybovalo v prostoru posilovny a vylézalo na posilovací stroje.\r\nPřed vstupem do posilovny a šaten je každý návštěvník povinen se vyzout a ihned přezout čistou obuv určenou do vnitřních prostor.\r\nZa boty a jiné odložené osobní věci vedení posilovny nezodpovídá. Proto si obuv a osobní věci uložte do skříňky pro Vás určené.\r\nU obsluhy si návštěvník vyzvedne klíč od skříňky. Ihned se dohodne s obsluhou na způsobu platby (hotově, členská karta) a u obsluhy mu bude založen konzumní účet. Po ukončení cvičení mu bude (členská karta) obsluhou opět vydána.\r\nCenné věci (šperky, hodinky, peníze atd.)je návštěvník povinen si zajistit sám. V případě ztráty těchto věcí ze skříňky v šatně, nenese vedení posilovny žádnou zodpovědnost! Návštěvník odpovídá v plné výši za škody či ztráty způsobené provozovateli a to i v případě neúmyslného způsobení.\r\nKe cvičení používá každý návštěvník čisté, pohodlné sportovní oblečení a vždy pevnou sportovní obuv! V případě, že tak neučiní, může být obsluhou z posilovny vykázán. Každý cvičenec musí mít ručník. V případě, že ho nemá, může si ručník zapůjčit u obsluhy. Nařizujeme, pokud by jakákoliv část holé pokožky měla mít kontakt s jakoukoliv částí posilovacích strojů, ručník pod sebe!!!\r\nPosilovací stroje a náčiní používá cvičenec vždy šetrně, pouze k účelu, pro které jsou vyrobeny. Vždy používá bezpečnostní uzávěry! V případě, že cvičenec používá zařízení i po upozornění obsluhy nevhodně, může být z posilovny vykázán! V případě, že cvičenec úmyslně poškodí jakékoliv zařízení posilovny, je povinen způsobenou škodu uhradit!\r\nPoužité nářadí a náčiní je každý cvičenec povinen uklidit na místo k tomu určené. (Kotouče vždy patří na stojany!)\r\nNávštěvník může využít občerstvení baru, přičemž sklenice patří pouze na bar nebo na místo k tomu určené! Návštěvník je povinen prázdné sklenice odevzdat na baru.\r\nPo ukončení cvičení předá cvičenec klíč od skříňky obsluze, zkontroluje, zda v prostoru celé posilovny nic nezapomněl (za věci zapomenuté v posilovně nenese vedení posilovny žádnou odpovědnost!).\r\nNávštěvník se může v posilovně pohybovat po dobu nejvíce 2 hodin.\r\nNávštěvníci jsou povinni respektovat příkazy vedení Fittsportu a trenéra, pokud neuposlechnou mohou být vykázáni k okamžitému odchodu.\r\nDo všech prostor Fittsportu je zakázán vstup osobám v podnapilém stavu,pod vlivem návykových látek,ve špinavém nebo nevhodném oblečení. V těchto případech je personál oprávněn kdykoliv vyzvat tyto osoby k opuštění Fittsportu. Pokud tak neučiní, mohou být ze zařízení bez náhrady vyvedeni.\r\nNerušit ostatní při tréninku, být slušný a ohleduplný vůči všem návštěvníkům Fittsportu.\r\nNávštěvník je povinen dodržovat provozní dobu tj. pondělí až pátek 7:30 – 21:00, sobota – zavřeno, neděle 9:00-12:00 , 15:00 – 21:00 a ukončit cvičení s dostatečným předstihem 15 minut před uzavřením provozovny. Návštěvník je povinen opustit veškeré prostory včetně šaten a uzavřít účet u obsluhy nejpozději do konce provozní doby.\r\nV prostorách Fittsportu je umístěn kamerový systém a to v souladu se zákonem o ochraně osobních údajů a zachování lidských práv. Slouží především pro bezpečnost klientů a k ochraně majetku.\r\nPlatnost permanentek je 3 měsíce ode dne zakoupení.\r\nVložený kredit na členské kartě je platný maximálně 18 měsíců od jeho nabití. Po uplynutí této lhůty kredit propadá.\r\nAktualizace členské karty vždy k 1. lednu (50,-Kč).\r\nDoufáme, že dodržování těchto nenáročných pravidel nám i Vám pomůže vytvořit příjemnou atmosféru v bezpečném prostředí, kde každý najde to, co hledá.'),
(3,	'Tělocvična',	'',	'');

DROP TABLE IF EXISTS `typ_predobjednani`;
CREATE TABLE `typ_predobjednani` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cena` float NOT NULL,
  `doba_platnosti` int NOT NULL,
  `nazev` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `typ_predobjednani` (`id`, `cena`, `doba_platnosti`, `nazev`) VALUES
(1,	350,	30,	'30 dní'),
(2,	590,	185,	'6 měsíců'),
(3,	1000,	365,	'1 rok');

DROP TABLE IF EXISTS `udalost`;
CREATE TABLE `udalost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `od` datetime NOT NULL,
  `do` datetime NOT NULL,
  `kapacita` int NOT NULL,
  `mistnost` int NOT NULL,
  `nazev` varchar(80) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `popis` text CHARACTER SET utf8 COLLATE utf8_czech_ci,
  `zamestnanci` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mistnost` (`mistnost`),
  KEY `zamestnanci` (`zamestnanci`),
  CONSTRAINT `udalost_ibfk_1` FOREIGN KEY (`mistnost`) REFERENCES `mistnost` (`id`) ON DELETE CASCADE,
  CONSTRAINT `udalost_ibfk_2` FOREIGN KEY (`zamestnanci`) REFERENCES `zamestnanci` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `udalost` (`id`, `od`, `do`, `kapacita`, `mistnost`, `nazev`, `popis`, `zamestnanci`) VALUES
(3,	'2019-04-24 00:00:00',	'2019-04-24 00:00:00',	15,	9,	'Událost',	'TRX středeční cvičení od 15:00-16:30',	5),
(5,	'2019-05-09 00:00:00',	'2019-05-09 00:00:00',	1,	9,	'Test',	'Cvičení na míči, pátky 16:00-17:00',	2),
(6,	'2019-05-06 00:00:00',	'2019-05-19 00:00:00',	10,	7,	'Událost',	'Denně od pšti hodin',	5);

DELIMITER ;;

CREATE TRIGGER `udalost_ai` AFTER INSERT ON `udalost` FOR EACH ROW
INSERT INTO udalost_historie (text, udalost) VALUES ("Událost vytvořena", NEW.id);;

CREATE TRIGGER `udalost_au` AFTER UPDATE ON `udalost` FOR EACH ROW
INSERT INTO udalost_historie (text, udalost) VALUES ("Událost upravena", OLD.id);;

CREATE TRIGGER `udalost_bd` BEFORE DELETE ON `udalost` FOR EACH ROW
INSERT INTO udalost_historie (text, udalost) VALUES ("Událost zrušena", OLD.id);;

DELIMITER ;

DROP TABLE IF EXISTS `udalost_historie`;
CREATE TABLE `udalost_historie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datum` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `text` varchar(100) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `udalost` int NOT NULL,
  `zamestnanci` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `zamestnanci` (`zamestnanci`),
  KEY `udalost` (`udalost`),
  CONSTRAINT `udalost_historie_ibfk_2` FOREIGN KEY (`zamestnanci`) REFERENCES `zamestnanci` (`id`) ON DELETE SET NULL,
  CONSTRAINT `udalost_historie_ibfk_3` FOREIGN KEY (`udalost`) REFERENCES `udalost` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `udalost_historie` (`id`, `datum`, `text`, `udalost`, `zamestnanci`) VALUES
(1,	'2019-04-22 14:12:18',	'Uživatel přihlášen',	3,	NULL),
(2,	'2019-04-22 14:15:29',	'Uživatel odhlášen',	3,	NULL),
(10,	'2019-05-07 13:06:46',	'Uživatel přihlášen',	3,	NULL),
(11,	'2019-05-07 13:07:10',	'Uživatel přihlášen',	3,	NULL),
(12,	'2019-05-07 13:07:12',	'Uživatel přihlášen',	3,	NULL),
(13,	'2019-05-07 13:07:17',	'Uživatel přihlášen',	3,	NULL),
(14,	'2019-05-07 13:08:52',	'Uživatel přihlášen',	3,	NULL),
(15,	'2019-05-07 13:08:55',	'Uživatel přihlášen',	3,	NULL),
(16,	'2019-05-07 13:11:19',	'Uživatel odhlášen',	3,	NULL),
(17,	'2019-05-07 13:11:19',	'Uživatel odhlášen',	3,	NULL),
(18,	'2019-05-07 13:11:19',	'Uživatel odhlášen',	3,	NULL),
(19,	'2019-05-07 13:11:19',	'Uživatel odhlášen',	3,	NULL),
(20,	'2019-05-07 13:11:19',	'Uživatel odhlášen',	3,	NULL),
(21,	'2019-05-07 13:11:19',	'Uživatel odhlášen',	3,	NULL),
(22,	'2019-05-07 13:11:24',	'Uživatel přihlášen',	3,	NULL),
(23,	'2019-05-07 13:11:28',	'Uživatel odhlášen',	3,	NULL),
(24,	'2019-05-07 13:11:29',	'Uživatel přihlášen',	3,	NULL),
(25,	'2019-05-07 13:11:30',	'Uživatel odhlášen',	3,	NULL),
(26,	'2019-05-07 13:16:21',	'Uživatel přihlášen',	3,	NULL),
(27,	'2019-05-07 13:37:59',	'Událost upravena',	3,	NULL),
(28,	'2019-05-07 16:44:29',	'Událost vytvořena',	5,	NULL),
(29,	'2019-05-07 17:15:21',	'Událost upravena',	3,	NULL),
(30,	'2019-05-07 18:21:55',	'Uživatel odhlášen',	3,	NULL),
(31,	'2019-05-07 18:21:57',	'Uživatel přihlášen',	3,	NULL),
(32,	'2019-05-07 18:21:59',	'Uživatel přihlášen',	5,	NULL),
(33,	'2019-05-07 18:22:00',	'Uživatel odhlášen',	5,	NULL),
(34,	'2019-05-07 18:22:01',	'Uživatel přihlášen',	5,	NULL),
(35,	'2019-05-07 18:22:02',	'Uživatel odhlášen',	5,	NULL),
(36,	'2019-05-07 18:31:37',	'Uživatel odhlášen',	3,	NULL),
(37,	'2019-05-07 18:31:38',	'Uživatel přihlášen',	3,	NULL),
(38,	'2019-05-07 18:34:03',	'Událost upravena',	5,	NULL),
(39,	'2019-05-07 18:34:19',	'Uživatel přihlášen',	5,	NULL),
(40,	'2019-05-07 18:35:21',	'Uživatel odhlášen',	5,	NULL),
(41,	'2019-05-07 18:35:22',	'Uživatel přihlášen',	5,	NULL),
(42,	'2019-05-07 18:35:24',	'Uživatel odhlášen',	5,	NULL),
(43,	'2019-05-09 13:12:23',	'Událost upravena',	3,	NULL),
(44,	'2019-05-09 13:13:24',	'Událost upravena',	5,	NULL),
(45,	'2019-05-09 14:27:02',	'Uživatel přihlášen',	5,	NULL),
(46,	'2019-05-09 14:33:23',	'Událost vytvořena',	6,	NULL),
(47,	'2019-05-09 14:47:09',	'Uživatel přihlášen',	6,	NULL);

DROP TABLE IF EXISTS `udalost_poznamky`;
CREATE TABLE `udalost_poznamky` (
  `id` int NOT NULL AUTO_INCREMENT,
  `udalost` int NOT NULL,
  `text` text CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `zamestnanci` int NOT NULL,
  `datum` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `udalost` (`udalost`),
  KEY `zamestnanci` (`zamestnanci`),
  CONSTRAINT `udalost_poznamky_ibfk_1` FOREIGN KEY (`udalost`) REFERENCES `udalost` (`id`) ON DELETE CASCADE,
  CONSTRAINT `udalost_poznamky_ibfk_2` FOREIGN KEY (`zamestnanci`) REFERENCES `zamestnanci` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `udalost_poznamky` (`id`, `udalost`, `text`, `zamestnanci`, `datum`) VALUES
(1,	3,	'Test',	5,	'2019-05-07 17:33:29'),
(2,	3,	'Hustý co',	5,	'2019-05-07 17:33:38'),
(3,	3,	'Poznámka',	5,	'2019-05-07 17:33:51'),
(5,	3,	'Hotovo',	5,	'2019-05-07 17:36:57'),
(6,	5,	'Test',	2,	'2019-05-07 20:04:23'),
(7,	5,	'Výměna',	2,	'2019-05-07 20:09:44'),
(8,	3,	'Poznámka',	5,	'2019-05-09 14:35:25');

DROP VIEW IF EXISTS `udalost_view`;
CREATE TABLE `udalost_view` (`id` int, `od` datetime, `do` datetime, `kapacita` int, `mistnost` int, `nazev` varchar(80), `popis` text, `zamestnanci` int, `tm_nazev` varchar(30), `tm_popis` text, `tm_id` int, `provozni_rad` text, `m_id` int, `m_kapacita` int);


DROP TABLE IF EXISTS `udalost_zakaznik`;
CREATE TABLE `udalost_zakaznik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `udalost` int NOT NULL,
  `zakaznik` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `udalost` (`udalost`),
  KEY `zakaznik` (`zakaznik`),
  CONSTRAINT `udalost_zakaznik_ibfk_1` FOREIGN KEY (`udalost`) REFERENCES `udalost` (`id`) ON DELETE CASCADE,
  CONSTRAINT `udalost_zakaznik_ibfk_2` FOREIGN KEY (`zakaznik`) REFERENCES `zakaznik` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `udalost_zakaznik` (`id`, `date`, `udalost`, `zakaznik`) VALUES
(15,	'2019-05-07 18:31:38',	3,	9),
(18,	'2019-05-09 14:27:02',	5,	11),
(19,	'2019-05-09 14:47:09',	6,	10);

DELIMITER ;;

CREATE TRIGGER `udalost_zakaznik_ai` AFTER INSERT ON `udalost_zakaznik` FOR EACH ROW
INSERT INTO udalost_historie (text, udalost) VALUES ("Uživatel přihlášen", NEW.udalost);;

CREATE TRIGGER `udalost_zakaznik_ad` AFTER DELETE ON `udalost_zakaznik` FOR EACH ROW
INSERT INTO udalost_historie (text, udalost) VALUES ("Uživatel odhlášen", OLD.udalost);;

DELIMITER ;

DROP TABLE IF EXISTS `zakaznik`;
CREATE TABLE `zakaznik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datum_narozeni` datetime NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `jmeno` varchar(20) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `prijmeni` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `rodne_cislo` varchar(10) CHARACTER SET utf8 COLLATE utf8_czech_ci DEFAULT NULL,
  `telefon` char(13) CHARACTER SET utf8 COLLATE utf8_czech_ci DEFAULT NULL,
  `adresa` int NOT NULL,
  `login` int NOT NULL,
  `role` int NOT NULL DEFAULT '3',
  `platnost_do` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `adresa` (`adresa`),
  KEY `login` (`login`),
  KEY `role` (`role`),
  CONSTRAINT `zakaznik_ibfk_1` FOREIGN KEY (`adresa`) REFERENCES `adresa` (`id`),
  CONSTRAINT `zakaznik_ibfk_2` FOREIGN KEY (`login`) REFERENCES `login` (`id`),
  CONSTRAINT `zakaznik_ibfk_3` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `zakaznik` (`id`, `datum_narozeni`, `email`, `jmeno`, `prijmeni`, `rodne_cislo`, `telefon`, `adresa`, `login`, `role`, `platnost_do`) VALUES
(3,	'1995-06-21 00:00:00',	'test@test.com',	'František',	'Holan',	'9506213652',	'455632211',	3,	3,	3,	'2019-06-07 00:00:00'),
(5,	'1992-02-02 00:00:00',	'ondrej.habr97@gmail.com',	'Ondřej',	'Habr',	'',	'988241121',	5,	5,	3,	'2019-06-07 00:00:00'),
(9,	'2019-05-16 00:00:00',	'ondrej.habr97@gmail.com',	'Ondřej',	'Habr',	'',	'',	10,	13,	3,	'2020-01-08 00:00:00'),
(10,	'1994-05-15 00:00:00',	'uzivatel@gmail.com',	'Uživatel',	'UživateP',	'',	'',	14,	18,	3,	'2019-06-08 00:00:00'),
(11,	'2019-05-14 00:00:00',	'uzivatel@gmail.com',	'Uzivatel',	'Test',	'',	'',	16,	20,	3,	'2020-01-09 00:00:00'),
(12,	'2000-01-07 00:00:00',	'fhfgh@fghfghh.cz',	'Pavel',	'Trnka',	'',	'445577414',	19,	23,	3,	'2021-03-18 00:00:00');

DROP TABLE IF EXISTS `zamestnanci`;
CREATE TABLE `zamestnanci` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datum_narozeni` datetime NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `jmeno` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `prijmeni` varchar(30) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `rodne_cislo` char(10) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `telefon` char(13) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `adresa` int NOT NULL,
  `login` int NOT NULL,
  `role` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rodne_cislo` (`rodne_cislo`),
  KEY `adresa` (`adresa`),
  KEY `login` (`login`),
  KEY `role` (`role`),
  CONSTRAINT `zamestnanci_ibfk_1` FOREIGN KEY (`adresa`) REFERENCES `adresa` (`id`),
  CONSTRAINT `zamestnanci_ibfk_2` FOREIGN KEY (`login`) REFERENCES `login` (`id`),
  CONSTRAINT `zamestnanci_ibfk_3` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `zamestnanci` (`id`, `datum_narozeni`, `email`, `jmeno`, `prijmeni`, `rodne_cislo`, `telefon`, `adresa`, `login`, `role`) VALUES
(2,	'2019-04-19 00:00:00',	'ondrej.habr97@gmail.com',	'Ondřej',	'Habr',	'9004183124',	'986551198',	6,	6,	1),
(3,	'1996-05-17 00:00:00',	'admin@taksup.cz',	'Admin',	'Admin',	'9605176512',	'654212245',	11,	14,	4),
(5,	'1990-04-05 00:00:00',	'testtest@gmail.com',	'Lektro',	'Instrukto',	'9004183942',	'988241178',	13,	17,	2),
(6,	'1985-05-22 00:00:00',	'vedouci@taksup.cz',	'Vedoucí',	'Prodejny',	'8505226574',	'775988652',	15,	19,	1),
(8,	'2019-05-11 00:00:00',	'ondrej.habr97@gmail.com',	'Ondřej',	'Habr',	'9004183777',	'988241121',	18,	22,	2);

DROP TABLE IF EXISTS `mistnost_view`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `mistnost_view` AS select `m`.`id` AS `m_id`,`m`.`kapacita` AS `kapacita`,`tm`.`id` AS `id`,`tm`.`nazev` AS `nazev`,`tm`.`popis` AS `popis`,`tm`.`provozni_rad` AS `provozni_rad` from (`mistnost` `m` join `typ_mistnosti` `tm` on((`m`.`typ_mistnosti` = `tm`.`id`)));

DROP TABLE IF EXISTS `prehled_view`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `prehled_view` AS select `u`.`od` AS `od`,`u`.`do` AS `do`,`u`.`kapacita` AS `kapacita`,`u`.`nazev` AS `nazev`,`u`.`popis` AS `popis`,concat(`z`.`jmeno`,' ',`z`.`prijmeni`) AS `zamestnanec`,`tm`.`nazev` AS `mistnost`,count(`uz`.`id`) AS `prihlaseni` from ((((`udalost` `u` join `zamestnanci` `z` on((`u`.`zamestnanci` = `z`.`id`))) join `mistnost` `m` on((`u`.`mistnost` = `m`.`id`))) join `typ_mistnosti` `tm` on((`m`.`typ_mistnosti` = `tm`.`id`))) left join `udalost_zakaznik` `uz` on((`uz`.`udalost` = `u`.`id`))) group by `u`.`id` order by `u`.`od` desc;

DROP TABLE IF EXISTS `udalost_view`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `udalost_view` AS select `u`.`id` AS `id`,`u`.`od` AS `od`,`u`.`do` AS `do`,`u`.`kapacita` AS `kapacita`,`u`.`mistnost` AS `mistnost`,`u`.`nazev` AS `nazev`,`u`.`popis` AS `popis`,`u`.`zamestnanci` AS `zamestnanci`,`tm`.`nazev` AS `tm_nazev`,`tm`.`popis` AS `tm_popis`,`tm`.`id` AS `tm_id`,`tm`.`provozni_rad` AS `provozni_rad`,`m`.`id` AS `m_id`,`m`.`kapacita` AS `m_kapacita` from ((`udalost` `u` join `mistnost` `m` on((`u`.`mistnost` = `m`.`id`))) join `typ_mistnosti` `tm` on((`m`.`typ_mistnosti` = `tm`.`id`)));

-- 2021-01-17 21:58:13
