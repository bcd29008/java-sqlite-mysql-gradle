-- DROP SCHEMA IF EXISTS lab02;
-- CREATE SCHEMA lab02;
-- USE lab02;

-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: lab02
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Departamento`
--

DROP TABLE IF EXISTS `Departamento`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Departamento`
(
    `idDepartamento` int(11)        NOT NULL,
    `dNome`          varchar(255)   NOT NULL,
    `Orcamento`      decimal(10, 0) NOT NULL,
    PRIMARY KEY (`idDepartamento`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Departamento`
--

LOCK TABLES `Departamento` WRITE;
/*!40000 ALTER TABLE `Departamento`
    DISABLE KEYS */;
INSERT INTO `Departamento`
VALUES (1, 'Financeiro', 15000),
       (2, 'TI', 60000),
       (3, 'Gestão de Pessoas', 150000),
       (4, 'Pesquisa e Desenvolvimento', 7500),
       (5, 'Jurídico', 1000);
/*!40000 ALTER TABLE `Departamento`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Funcionario`
--

DROP TABLE IF EXISTS `Funcionario`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Funcionario`
(
    `idFuncionario`  INT         NOT NULL,
    `Nome`           VARCHAR(45) NOT NULL,
    `Sobrenome`      VARCHAR(45) NOT NULL,
    `idDepartamento` INT         NOT NULL,
    PRIMARY KEY (`idFuncionario`),
    CONSTRAINT `fk_Funcionario_Departamento` FOREIGN KEY (`idDepartamento`)
        REFERENCES `Departamento` (`idDepartamento`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Funcionario`
--

LOCK TABLES `Funcionario` WRITE;
/*!40000 ALTER TABLE `Funcionario`
    DISABLE KEYS */;
INSERT INTO `Funcionario`
VALUES (123, 'Julio', 'Silva', 1),
       (152, 'Arnaldo', 'Coelho', 1),
       (222, 'Carol', 'Ferreira', 2),
       (326, 'João', 'Silveira', 2),
       (331, 'George', 'de la Rocha', 3),
       (332, 'José', 'Oliveira', 1),
       (546, 'José', 'Pereira', 4),
       (631, 'David', 'Luz', 3),
       (654, 'Zacarias', 'Ferreira', 4),
       (745, 'Eric', 'Estrada', 4),
       (845, 'Elizabeth', 'Coelho', 1),
       (846, 'Joaquim', 'Goveia', 1);
/*!40000 ALTER TABLE `Funcionario`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2017-03-15 16:26:09
