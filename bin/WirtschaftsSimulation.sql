-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Erstellungszeit: 08. Feb 2019 um 08:06
-- Server-Version: 10.3.12-MariaDB
-- PHP-Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `WirtschaftsSimulation`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `arbeiter_forschung`
--

CREATE TABLE `arbeiter_forschung` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Beschreibung` text NOT NULL,
  `Kosten` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `arbeiter_forschung`
--

INSERT INTO `arbeiter_forschung` (`ID`, `Name`, `Beschreibung`, `Kosten`) VALUES
(1, 'Arbeiter_1', '+10%', 200000),
(2, 'Arbeiter_2', '+10%', 250000),
(3, 'Arbeiter_3', '+10%', 350000);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `forschung`
--

CREATE TABLE `forschung` (
  `ID` int(11) NOT NULL,
  `SpielerID` int(11) NOT NULL,
  `ForschungsID` int(11) NOT NULL,
  `WannAbgeschlosseneRunde` int(11) NOT NULL,
  `laeuftAb` int(11) NOT NULL,
  `laeuftAbIn` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `item_forschung`
--

CREATE TABLE `item_forschung` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Beschreibung` text NOT NULL,
  `Kosten` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `item_forschung`
--

INSERT INTO `item_forschung` (`ID`, `Name`, `Beschreibung`, `Kosten`) VALUES
(1, 'Presslufthammer', '+10%', 200000),
(2, 'Bagger', '+25%', 1000000),
(3, 'Scahufelradbagger', '+100%', 10000000);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lager_forshung`
--

CREATE TABLE `lager_forshung` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Beschreibung` text NOT NULL,
  `Kosten` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `lager_forshung`
--

INSERT INTO `lager_forshung` (`ID`, `Name`, `Beschreibung`, `Kosten`) VALUES
(1, 'Lager_1', '+5000t', 100000),
(2, 'Lager_2', '+7500t', 200000),
(3, 'Lager_3', '+12500t', 300000);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `runden`
--

CREATE TABLE `runden` (
  `ID` int(11) NOT NULL,
  `Runde` int(11) NOT NULL,
  `SpielerID` int(11) DEFAULT NULL,
  `Kapital` int(11) NOT NULL,
  `Geldkapital` int(11) NOT NULL,
  `Geldaenderung` int(11) NOT NULL,
  `Lager` int(11) NOT NULL,
  `Lagermenge` int(11) NOT NULL,
  `Verkaufsmenge` int(11) NOT NULL,
  `Kreditprozentsatz` float NOT NULL,
  `Kreditnahme` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sicherheit_forschung`
--

CREATE TABLE `sicherheit_forschung` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Beschreibung` text NOT NULL,
  `Kosten` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `sicherheit_forschung`
--

INSERT INTO `sicherheit_forschung` (`ID`, `Name`, `Beschreibung`, `Kosten`) VALUES
(1, 'Kanarien_Vogel', '-60% Verletzte bei Explosion', 100000),
(2, 'Holzfeiler', '-30% Eistürze', 200000),
(3, 'Stahlfeiler', '-80% Einstürze', 1000000),
(4, 'Wasserpumpe', '-50% Grubenwasser', 500000),
(5, 'Rauchverbot', '-1% Explosion', 600),
(6, 'Staubschutz', '-10% Ausfälle', 10000);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `spiele`
--

CREATE TABLE `spiele` (
  `ID` int(11) NOT NULL,
  `Spielname` text NOT NULL,
  `Datum` date NOT NULL,
  `max. Spieler` int(11) NOT NULL,
  `Spielrunden` int(11) NOT NULL,
  `max. Spielrunden` int(11) NOT NULL,
  `ins. erwirtschaftetes Geld` int(11) NOT NULL,
  `ins. abgebaute Menge` int(11) NOT NULL,
  `ins. Geld für Forschung` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `spieler`
--

CREATE TABLE `spieler` (
  `ID` int(11) NOT NULL,
  `Spielername` text NOT NULL,
  `Passwort` text DEFAULT NULL,
  `ins_gespielte_Spiele` int(11) DEFAULT NULL,
  `ins_erwirtschafttes_Geld` int(11) DEFAULT NULL,
  `ins_Kredit_genommen` int(11) DEFAULT NULL,
  `ins_Kredit_agbezahlt` int(11) DEFAULT NULL,
  `ins_verkaufte_Menge` int(11) DEFAULT NULL,
  `ins_abgebaute_Menge` int(11) DEFAULT NULL,
  `Geld_für_Forschung` int(11) DEFAULT NULL,
  `ins_Forschung` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `versicherung_forschung`
--

CREATE TABLE `versicherung_forschung` (
  `ID` int(11) NOT NULL,
  `Name` text NOT NULL,
  `Beschreibung` text NOT NULL,
  `Kosten` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `versicherung_forschung`
--

INSERT INTO `versicherung_forschung` (`ID`, `Name`, `Beschreibung`, `Kosten`) VALUES
(1, 'Krankenversicherung', '-50% Krankheitsfälle (pro Runde)', 40000),
(2, 'Unfallvrsicherung', '-50% Verletzte (pro runde)', 40000),
(3, 'Streik-Umsatzausfall-Versicherung', '-50% Föderung (pro Runde)', 40000);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `arbeiter_forschung`
--
ALTER TABLE `arbeiter_forschung`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `forschung`
--
ALTER TABLE `forschung`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `item_forschung`
--
ALTER TABLE `item_forschung`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `lager_forshung`
--
ALTER TABLE `lager_forshung`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `runden`
--
ALTER TABLE `runden`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `sicherheit_forschung`
--
ALTER TABLE `sicherheit_forschung`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `spiele`
--
ALTER TABLE `spiele`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `spieler`
--
ALTER TABLE `spieler`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `versicherung_forschung`
--
ALTER TABLE `versicherung_forschung`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `arbeiter_forschung`
--
ALTER TABLE `arbeiter_forschung`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `forschung`
--
ALTER TABLE `forschung`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `item_forschung`
--
ALTER TABLE `item_forschung`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `lager_forshung`
--
ALTER TABLE `lager_forshung`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `runden`
--
ALTER TABLE `runden`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `sicherheit_forschung`
--
ALTER TABLE `sicherheit_forschung`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT für Tabelle `spiele`
--
ALTER TABLE `spiele`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `spieler`
--
ALTER TABLE `spieler`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `versicherung_forschung`
--
ALTER TABLE `versicherung_forschung`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
