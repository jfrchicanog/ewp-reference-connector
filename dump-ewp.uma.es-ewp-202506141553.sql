-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ewp
-- ------------------------------------------------------
-- Server version	5.7.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ACADEMICYEARLASTATS`
--

DROP TABLE IF EXISTS `ACADEMICYEARLASTATS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ACADEMICYEARLASTATS` (
  `ID` varchar(255) NOT NULL,
  `LAOUTGOINGLATESTVERSIONAPPROVED` bigint(20) DEFAULT NULL,
  `LAOUTGOINGLATESTVERSIONAWAITING` bigint(20) DEFAULT NULL,
  `LAOUTGOINGLATESTVERSIONREJECTED` bigint(20) DEFAULT NULL,
  `LAOUTGOINGMODIFIEDAFTERAPPROVAL` bigint(20) DEFAULT NULL,
  `LAOUTGOINGNOTMODIFIEDAFTERAPPROVAL` bigint(20) DEFAULT NULL,
  `LAOUTGOINGTOTAL` bigint(20) DEFAULT NULL,
  `RECEIVINGACADEMICYEARID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ACCESSIBILITY`
--

DROP TABLE IF EXISTS `ACCESSIBILITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ACCESSIBILITY` (
  `ID` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `INFO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ACCESSIBILITY_INFO` (`INFO`),
  CONSTRAINT `FK_ACCESSIBILITY_INFO` FOREIGN KEY (`INFO`) REFERENCES `CONTACTINFO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ACCESSIBLITY`
--

DROP TABLE IF EXISTS `ACCESSIBLITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ACCESSIBLITY` (
  `MobilityFactsheet_ID` varchar(255) NOT NULL,
  `accessibility_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`MobilityFactsheet_ID`,`accessibility_ID`),
  KEY `FK_ACCESSIBLITY_accessibility_ID` (`accessibility_ID`),
  CONSTRAINT `FK_ACCESSIBLITY_MobilityFactsheet_ID` FOREIGN KEY (`MobilityFactsheet_ID`) REFERENCES `MOBILITYFACTSHEET` (`ID`),
  CONSTRAINT `FK_ACCESSIBLITY_accessibility_ID` FOREIGN KEY (`accessibility_ID`) REFERENCES `ACCESSIBILITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ADDITIONALREQUIREMENT`
--

DROP TABLE IF EXISTS `ADDITIONALREQUIREMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ADDITIONALREQUIREMENT` (
  `ID` varchar(255) NOT NULL,
  `DETAILS` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ADDITIONALREQUIREMENTS`
--

DROP TABLE IF EXISTS `ADDITIONALREQUIREMENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ADDITIONALREQUIREMENTS` (
  `ID` varchar(255) NOT NULL,
  `FILE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ADDITIONAL_INFO`
--

DROP TABLE IF EXISTS `ADDITIONAL_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ADDITIONAL_INFO` (
  `MobilityFactsheet_ID` varchar(255) NOT NULL,
  `additionalInfo_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`MobilityFactsheet_ID`,`additionalInfo_ID`),
  KEY `FK_ADDITIONAL_INFO_additionalInfo_ID` (`additionalInfo_ID`),
  CONSTRAINT `FK_ADDITIONAL_INFO_MobilityFactsheet_ID` FOREIGN KEY (`MobilityFactsheet_ID`) REFERENCES `MOBILITYFACTSHEET` (`ID`),
  CONSTRAINT `FK_ADDITIONAL_INFO_additionalInfo_ID` FOREIGN KEY (`additionalInfo_ID`) REFERENCES `FACTSHEETADDITIONALINFO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ADDITIONAL_REQUIREMENT`
--

DROP TABLE IF EXISTS `ADDITIONAL_REQUIREMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ADDITIONAL_REQUIREMENT` (
  `MobilityFactsheet_ID` varchar(255) NOT NULL,
  `additionalRequirements_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`MobilityFactsheet_ID`,`additionalRequirements_ID`),
  KEY `ADDITIONAL_REQUIREMENT_additionalRequirements_ID` (`additionalRequirements_ID`),
  CONSTRAINT `ADDITIONAL_REQUIREMENT_additionalRequirements_ID` FOREIGN KEY (`additionalRequirements_ID`) REFERENCES `ADDITIONALREQUIREMENT` (`ID`),
  CONSTRAINT `FK_ADDITIONAL_REQUIREMENT_MobilityFactsheet_ID` FOREIGN KEY (`MobilityFactsheet_ID`) REFERENCES `MOBILITYFACTSHEET` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ADDITIONAL_REQUIREMENTS_MOBILITY`
--

DROP TABLE IF EXISTS `ADDITIONAL_REQUIREMENTS_MOBILITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ADDITIONAL_REQUIREMENTS_MOBILITY` (
  `Mobility_ID` varchar(255) NOT NULL,
  `additionalRequirements_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Mobility_ID`,`additionalRequirements_ID`),
  KEY `DDTIONALREQUIREMENTSMOBILITYddtionalRequirementsID` (`additionalRequirements_ID`),
  CONSTRAINT `DDTIONALREQUIREMENTSMOBILITYddtionalRequirementsID` FOREIGN KEY (`additionalRequirements_ID`) REFERENCES `ADDITIONALREQUIREMENTS` (`ID`),
  CONSTRAINT `FK_ADDITIONAL_REQUIREMENTS_MOBILITY_Mobility_ID` FOREIGN KEY (`Mobility_ID`) REFERENCES `MOBILITY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `APPROVAL`
--

DROP TABLE IF EXISTS `APPROVAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `APPROVAL` (
  `ID` varchar(255) NOT NULL,
  `BYPARTY` varchar(255) DEFAULT NULL,
  `TIMESTAMP` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `APPROVEDPROPOSAL`
--

DROP TABLE IF EXISTS `APPROVEDPROPOSAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `APPROVEDPROPOSAL` (
  `ID` varchar(255) NOT NULL,
  `CHANGESPROPOSALID` varchar(255) DEFAULT NULL,
  `OMOBILITYID` varchar(255) DEFAULT NULL,
  `APPROVED_PROPOSAL_SIGNATURE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_APPROVEDPROPOSAL_APPROVED_PROPOSAL_SIGNATURE` (`APPROVED_PROPOSAL_SIGNATURE`),
  CONSTRAINT `FK_APPROVEDPROPOSAL_APPROVED_PROPOSAL_SIGNATURE` FOREIGN KEY (`APPROVED_PROPOSAL_SIGNATURE`) REFERENCES `SIGNATURE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CALENDARENTRY`
--

DROP TABLE IF EXISTS `CALENDARENTRY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CALENDARENTRY` (
  `ID` varchar(255) NOT NULL,
  `AUTUMNTERM` date DEFAULT NULL,
  `FACTSHEETID` varchar(255) DEFAULT NULL,
  `SPRINGTERM` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMMENTPROPOSAL`
--

DROP TABLE IF EXISTS `COMMENTPROPOSAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMMENTPROPOSAL` (
  `ID` varchar(255) NOT NULL,
  `CHANGESPROPOSALID` varchar(255) DEFAULT NULL,
  `COMMENT` varchar(255) DEFAULT NULL,
  `OMOBILITYID` varchar(255) DEFAULT NULL,
  `COMMENT_PROPOSAL_SIGNATURE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_COMMENTPROPOSAL_COMMENT_PROPOSAL_SIGNATURE` (`COMMENT_PROPOSAL_SIGNATURE`),
  CONSTRAINT `FK_COMMENTPROPOSAL_COMMENT_PROPOSAL_SIGNATURE` FOREIGN KEY (`COMMENT_PROPOSAL_SIGNATURE`) REFERENCES `SIGNATURE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENT`
--

DROP TABLE IF EXISTS `COMPONENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENT` (
  `ID` varchar(255) NOT NULL,
  `LOIID` varchar(255) DEFAULT NULL,
  `LOSCODE` varchar(255) DEFAULT NULL,
  `LOSID` varchar(255) DEFAULT NULL,
  `REASONCODE` varchar(255) DEFAULT NULL,
  `REASONTEXT` varchar(255) DEFAULT NULL,
  `RECOGNITIONCONDITIONS` varchar(255) DEFAULT NULL,
  `SHORTDESCRIPTION` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `COMPONENT_TERM_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_COMPONENT_COMPONENT_TERM_ID` (`COMPONENT_TERM_ID`),
  CONSTRAINT `FK_COMPONENT_COMPONENT_TERM_ID` FOREIGN KEY (`COMPONENT_TERM_ID`) REFERENCES `TERMID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENTSSTUDIED`
--

DROP TABLE IF EXISTS `COMPONENTSSTUDIED`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENTSSTUDIED` (
  `ID` varchar(255) NOT NULL,
  `ACADEMICTERMDISPLAYNAME` varchar(255) DEFAULT NULL,
  `LOIID` varchar(255) DEFAULT NULL,
  `LOSCODE` varchar(255) DEFAULT NULL,
  `LOSID` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENTS_BLENDED_MOBILITY`
--

DROP TABLE IF EXISTS `COMPONENTS_BLENDED_MOBILITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENTS_BLENDED_MOBILITY` (
  `ListOfComponents_ID` varchar(255) NOT NULL,
  `blendedMobilityComponents_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ListOfComponents_ID`,`blendedMobilityComponents_ID`),
  KEY `CMPONENTSBLENDEDMOBILITYblndedMobilityComponentsID` (`blendedMobilityComponents_ID`),
  CONSTRAINT `CMPONENTSBLENDEDMOBILITYblndedMobilityComponentsID` FOREIGN KEY (`blendedMobilityComponents_ID`) REFERENCES `COMPONENT` (`ID`),
  CONSTRAINT `FK_COMPONENTS_BLENDED_MOBILITY_ListOfComponents_ID` FOREIGN KEY (`ListOfComponents_ID`) REFERENCES `LISTOFCOMPONENTS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENTS_RECOGNIZED`
--

DROP TABLE IF EXISTS `COMPONENTS_RECOGNIZED`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENTS_RECOGNIZED` (
  `ListOfComponents_ID` varchar(255) NOT NULL,
  `componentsRecognized_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ListOfComponents_ID`,`componentsRecognized_ID`),
  KEY `FK_COMPONENTS_RECOGNIZED_componentsRecognized_ID` (`componentsRecognized_ID`),
  CONSTRAINT `FK_COMPONENTS_RECOGNIZED_ListOfComponents_ID` FOREIGN KEY (`ListOfComponents_ID`) REFERENCES `LISTOFCOMPONENTS` (`ID`),
  CONSTRAINT `FK_COMPONENTS_RECOGNIZED_componentsRecognized_ID` FOREIGN KEY (`componentsRecognized_ID`) REFERENCES `COMPONENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENTS_SHORT_TERM_DOCTORAL`
--

DROP TABLE IF EXISTS `COMPONENTS_SHORT_TERM_DOCTORAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENTS_SHORT_TERM_DOCTORAL` (
  `ListOfComponents_ID` varchar(255) NOT NULL,
  `shortTermDoctoralComponents_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ListOfComponents_ID`,`shortTermDoctoralComponents_ID`),
  KEY `CMPNNTSSHORTTERMDOCTORALshrtTrmDctoralComponentsID` (`shortTermDoctoralComponents_ID`),
  CONSTRAINT `CMPNNTSSHORTTERMDOCTORALshrtTrmDctoralComponentsID` FOREIGN KEY (`shortTermDoctoralComponents_ID`) REFERENCES `COMPONENT` (`ID`),
  CONSTRAINT `COMPONENTS_SHORT_TERM_DOCTORAL_ListOfComponents_ID` FOREIGN KEY (`ListOfComponents_ID`) REFERENCES `LISTOFCOMPONENTS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENTS_STUDIED`
--

DROP TABLE IF EXISTS `COMPONENTS_STUDIED`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENTS_STUDIED` (
  `ListOfComponents_ID` varchar(255) NOT NULL,
  `componentsStudied_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ListOfComponents_ID`,`componentsStudied_ID`),
  KEY `FK_COMPONENTS_STUDIED_componentsStudied_ID` (`componentsStudied_ID`),
  CONSTRAINT `FK_COMPONENTS_STUDIED_ListOfComponents_ID` FOREIGN KEY (`ListOfComponents_ID`) REFERENCES `LISTOFCOMPONENTS` (`ID`),
  CONSTRAINT `FK_COMPONENTS_STUDIED_componentsStudied_ID` FOREIGN KEY (`componentsStudied_ID`) REFERENCES `COMPONENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENTS_VIRTUAL`
--

DROP TABLE IF EXISTS `COMPONENTS_VIRTUAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENTS_VIRTUAL` (
  `ListOfComponents_ID` varchar(255) NOT NULL,
  `virtualComponents_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ListOfComponents_ID`,`virtualComponents_ID`),
  KEY `FK_COMPONENTS_VIRTUAL_virtualComponents_ID` (`virtualComponents_ID`),
  CONSTRAINT `FK_COMPONENTS_VIRTUAL_ListOfComponents_ID` FOREIGN KEY (`ListOfComponents_ID`) REFERENCES `LISTOFCOMPONENTS` (`ID`),
  CONSTRAINT `FK_COMPONENTS_VIRTUAL_virtualComponents_ID` FOREIGN KEY (`virtualComponents_ID`) REFERENCES `COMPONENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENT_CREDIT`
--

DROP TABLE IF EXISTS `COMPONENT_CREDIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENT_CREDIT` (
  `Component_ID` varchar(255) NOT NULL,
  `credit_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Component_ID`,`credit_ID`),
  KEY `FK_COMPONENT_CREDIT_credit_ID` (`credit_ID`),
  CONSTRAINT `FK_COMPONENT_CREDIT_Component_ID` FOREIGN KEY (`Component_ID`) REFERENCES `COMPONENT` (`ID`),
  CONSTRAINT `FK_COMPONENT_CREDIT_credit_ID` FOREIGN KEY (`credit_ID`) REFERENCES `CREDIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMPONENT_STUDIED_CREDIT`
--

DROP TABLE IF EXISTS `COMPONENT_STUDIED_CREDIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMPONENT_STUDIED_CREDIT` (
  `ComponentsStudied_ID` varchar(255) NOT NULL,
  `credit_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ComponentsStudied_ID`,`credit_ID`),
  KEY `FK_COMPONENT_STUDIED_CREDIT_credit_ID` (`credit_ID`),
  CONSTRAINT `FK_COMPONENT_STUDIED_CREDIT_ComponentsStudied_ID` FOREIGN KEY (`ComponentsStudied_ID`) REFERENCES `COMPONENTSSTUDIED` (`ID`),
  CONSTRAINT `FK_COMPONENT_STUDIED_CREDIT_credit_ID` FOREIGN KEY (`credit_ID`) REFERENCES `CREDIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONFIG`
--

DROP TABLE IF EXISTS `CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONFIG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLAVE` varchar(255) NOT NULL,
  `VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_key` (`CLAVE`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACT`
--

DROP TABLE IF EXISTS `CONTACT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACT` (
  `ID` varchar(255) NOT NULL,
  `INSTITUTIONID` varchar(255) DEFAULT NULL,
  `ORGANIZATIONUNITID` varchar(255) DEFAULT NULL,
  `CONTACT_ROLE` varchar(255) DEFAULT NULL,
  `PERSON_ID` varchar(255) DEFAULT NULL,
  `CONTACTDETAILS_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CONTACT_PERSON_ID` (`PERSON_ID`),
  KEY `FK_CONTACT_CONTACTDETAILS_ID` (`CONTACTDETAILS_ID`),
  CONSTRAINT `FK_CONTACT_CONTACTDETAILS_ID` FOREIGN KEY (`CONTACTDETAILS_ID`) REFERENCES `CONTACTDETAILS` (`ID`),
  CONSTRAINT `FK_CONTACT_PERSON_ID` FOREIGN KEY (`PERSON_ID`) REFERENCES `PERSON` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACTDETAILS`
--

DROP TABLE IF EXISTS `CONTACTDETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACTDETAILS` (
  `ID` varchar(255) NOT NULL,
  `FAX_NUMBER` varchar(255) DEFAULT NULL,
  `MAILING_ADDRESS` varchar(255) DEFAULT NULL,
  `PHONE_NUMBER` varchar(255) DEFAULT NULL,
  `STREET_ADDRESS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CONTACTDETAILS_PHONE_NUMBER` (`PHONE_NUMBER`),
  KEY `FK_CONTACTDETAILS_MAILING_ADDRESS` (`MAILING_ADDRESS`),
  KEY `FK_CONTACTDETAILS_FAX_NUMBER` (`FAX_NUMBER`),
  KEY `FK_CONTACTDETAILS_STREET_ADDRESS` (`STREET_ADDRESS`),
  CONSTRAINT `FK_CONTACTDETAILS_FAX_NUMBER` FOREIGN KEY (`FAX_NUMBER`) REFERENCES `PHONENUMBER` (`ID`),
  CONSTRAINT `FK_CONTACTDETAILS_MAILING_ADDRESS` FOREIGN KEY (`MAILING_ADDRESS`) REFERENCES `FLEXIBLEADDRESS` (`ID`),
  CONSTRAINT `FK_CONTACTDETAILS_PHONE_NUMBER` FOREIGN KEY (`PHONE_NUMBER`) REFERENCES `PHONENUMBER` (`ID`),
  CONSTRAINT `FK_CONTACTDETAILS_STREET_ADDRESS` FOREIGN KEY (`STREET_ADDRESS`) REFERENCES `FLEXIBLEADDRESS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACTINFO`
--

DROP TABLE IF EXISTS `CONTACTINFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACTINFO` (
  `ID` varchar(255) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PHONE_NUMBER_FACTSHEET_CONTACT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CONTACTINFO_PHONE_NUMBER_FACTSHEET_CONTACT` (`PHONE_NUMBER_FACTSHEET_CONTACT`),
  CONSTRAINT `FK_CONTACTINFO_PHONE_NUMBER_FACTSHEET_CONTACT` FOREIGN KEY (`PHONE_NUMBER_FACTSHEET_CONTACT`) REFERENCES `PHONENUMBER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACTPERSON`
--

DROP TABLE IF EXISTS `CONTACTPERSON`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACTPERSON` (
  `ID` varchar(255) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FAMILYNAME` varchar(255) DEFAULT NULL,
  `GIVENNAMES` varchar(255) DEFAULT NULL,
  `PHONE_NUMBER` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CONTACTPERSON_PHONE_NUMBER` (`PHONE_NUMBER`),
  CONSTRAINT `FK_CONTACTPERSON_PHONE_NUMBER` FOREIGN KEY (`PHONE_NUMBER`) REFERENCES `OMOBILITYPHONENUMBER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACT_DESCRIPTION`
--

DROP TABLE IF EXISTS `CONTACT_DESCRIPTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACT_DESCRIPTION` (
  `Contact_ID` varchar(255) NOT NULL,
  `description_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Contact_ID`,`description_ID`),
  KEY `FK_CONTACT_DESCRIPTION_description_ID` (`description_ID`),
  CONSTRAINT `FK_CONTACT_DESCRIPTION_Contact_ID` FOREIGN KEY (`Contact_ID`) REFERENCES `CONTACT` (`ID`),
  CONSTRAINT `FK_CONTACT_DESCRIPTION_description_ID` FOREIGN KEY (`description_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACT_NAME`
--

DROP TABLE IF EXISTS `CONTACT_NAME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACT_NAME` (
  `Contact_ID` varchar(255) NOT NULL,
  `name_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Contact_ID`,`name_ID`),
  KEY `FK_CONTACT_NAME_name_ID` (`name_ID`),
  CONSTRAINT `FK_CONTACT_NAME_Contact_ID` FOREIGN KEY (`Contact_ID`) REFERENCES `CONTACT` (`ID`),
  CONSTRAINT `FK_CONTACT_NAME_name_ID` FOREIGN KEY (`name_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACT_URL`
--

DROP TABLE IF EXISTS `CONTACT_URL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONTACT_URL` (
  `ContactDetails_ID` varchar(255) NOT NULL,
  `url_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ContactDetails_ID`,`url_ID`),
  KEY `FK_CONTACT_URL_url_ID` (`url_ID`),
  CONSTRAINT `FK_CONTACT_URL_ContactDetails_ID` FOREIGN KEY (`ContactDetails_ID`) REFERENCES `CONTACTDETAILS` (`ID`),
  CONSTRAINT `FK_CONTACT_URL_url_ID` FOREIGN KEY (`url_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COOPERATIONCONDITION`
--

DROP TABLE IF EXISTS `COOPERATIONCONDITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COOPERATIONCONDITION` (
  `ID` varchar(255) NOT NULL,
  `BLENDED` tinyint(1) DEFAULT '0',
  `ENDDATE` date DEFAULT NULL,
  `EQFLEVEL` longblob,
  `OTHERINFOTERMS` varchar(255) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `MOBILITY_TYPE_ID` varchar(255) DEFAULT NULL,
  `DURATION_ID` varchar(255) DEFAULT NULL,
  `MOBILITYNUMBER_ID` varchar(255) DEFAULT NULL,
  `RECEIVING_PARTNER_ID` varchar(255) DEFAULT NULL,
  `SENDING_PARTNER_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_COOPERATIONCONDITION_RECEIVING_PARTNER_ID` (`RECEIVING_PARTNER_ID`),
  KEY `FK_COOPERATIONCONDITION_SENDING_PARTNER_ID` (`SENDING_PARTNER_ID`),
  KEY `FK_COOPERATIONCONDITION_DURATION_ID` (`DURATION_ID`),
  KEY `FK_COOPERATIONCONDITION_MOBILITYNUMBER_ID` (`MOBILITYNUMBER_ID`),
  KEY `FK_COOPERATIONCONDITION_MOBILITY_TYPE_ID` (`MOBILITY_TYPE_ID`),
  CONSTRAINT `FK_COOPERATIONCONDITION_DURATION_ID` FOREIGN KEY (`DURATION_ID`) REFERENCES `DURATION` (`ID`),
  CONSTRAINT `FK_COOPERATIONCONDITION_MOBILITYNUMBER_ID` FOREIGN KEY (`MOBILITYNUMBER_ID`) REFERENCES `MOBILITYNUMBER` (`ID`),
  CONSTRAINT `FK_COOPERATIONCONDITION_MOBILITY_TYPE_ID` FOREIGN KEY (`MOBILITY_TYPE_ID`) REFERENCES `MOBILITYTYPE` (`ID`),
  CONSTRAINT `FK_COOPERATIONCONDITION_RECEIVING_PARTNER_ID` FOREIGN KEY (`RECEIVING_PARTNER_ID`) REFERENCES `IIAPARTNER` (`ID`),
  CONSTRAINT `FK_COOPERATIONCONDITION_SENDING_PARTNER_ID` FOREIGN KEY (`SENDING_PARTNER_ID`) REFERENCES `IIAPARTNER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COOPERATIONCONDLANGUAGESKILL`
--

DROP TABLE IF EXISTS `COOPERATIONCONDLANGUAGESKILL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COOPERATIONCONDLANGUAGESKILL` (
  `ID` varchar(255) NOT NULL,
  `CEFRLEVEL` varchar(255) DEFAULT NULL,
  `LANGUAGE` varchar(255) DEFAULT NULL,
  `SUBJECT_AREA` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_COOPERATIONCONDLANGUAGESKILL_SUBJECT_AREA` (`SUBJECT_AREA`),
  CONSTRAINT `FK_COOPERATIONCONDLANGUAGESKILL_SUBJECT_AREA` FOREIGN KEY (`SUBJECT_AREA`) REFERENCES `SUBJECTAREA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COURSE_CATALOG`
--

DROP TABLE IF EXISTS `COURSE_CATALOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COURSE_CATALOG` (
  `Institution_ID` varchar(255) NOT NULL,
  `courseCatalog_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Institution_ID`,`courseCatalog_ID`),
  KEY `FK_COURSE_CATALOG_courseCatalog_ID` (`courseCatalog_ID`),
  CONSTRAINT `FK_COURSE_CATALOG_Institution_ID` FOREIGN KEY (`Institution_ID`) REFERENCES `INSTITUTION` (`ID`),
  CONSTRAINT `FK_COURSE_CATALOG_courseCatalog_ID` FOREIGN KEY (`courseCatalog_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CREDIT`
--

DROP TABLE IF EXISTS `CREDIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CREDIT` (
  `ID` varchar(255) NOT NULL,
  `SCHEME` varchar(255) DEFAULT NULL,
  `VALUE` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ContactDetails_EMAIL`
--

DROP TABLE IF EXISTS `ContactDetails_EMAIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ContactDetails_EMAIL` (
  `ContactDetails_ID` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  KEY `FK_ContactDetails_EMAIL_ContactDetails_ID` (`ContactDetails_ID`),
  CONSTRAINT `FK_ContactDetails_EMAIL_ContactDetails_ID` FOREIGN KEY (`ContactDetails_ID`) REFERENCES `CONTACTDETAILS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DISTR_CATEGORIES`
--

DROP TABLE IF EXISTS `DISTR_CATEGORIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DISTR_CATEGORIES` (
  `ResultDistribution_ID` varchar(255) NOT NULL,
  `resultDistributionCategory_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ResultDistribution_ID`,`resultDistributionCategory_ID`),
  KEY `FK_DISTR_CATEGORIES_resultDistributionCategory_ID` (`resultDistributionCategory_ID`),
  CONSTRAINT `FK_DISTR_CATEGORIES_ResultDistribution_ID` FOREIGN KEY (`ResultDistribution_ID`) REFERENCES `RESULTDISTRIBUTION` (`ID`),
  CONSTRAINT `FK_DISTR_CATEGORIES_resultDistributionCategory_ID` FOREIGN KEY (`resultDistributionCategory_ID`) REFERENCES `RESULTDISTRIBUTIONCATEGORY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DISTR_DESCRIPTION`
--

DROP TABLE IF EXISTS `DISTR_DESCRIPTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DISTR_DESCRIPTION` (
  `ResultDistribution_ID` varchar(255) NOT NULL,
  `description_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ResultDistribution_ID`,`description_ID`),
  KEY `FK_DISTR_DESCRIPTION_description_ID` (`description_ID`),
  CONSTRAINT `FK_DISTR_DESCRIPTION_ResultDistribution_ID` FOREIGN KEY (`ResultDistribution_ID`) REFERENCES `RESULTDISTRIBUTION` (`ID`),
  CONSTRAINT `FK_DISTR_DESCRIPTION_description_ID` FOREIGN KEY (`description_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DURATION`
--

DROP TABLE IF EXISTS `DURATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DURATION` (
  `ID` varchar(255) NOT NULL,
  `NUMBER` decimal(5,1) DEFAULT NULL,
  `UNIT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FACTSHEET`
--

DROP TABLE IF EXISTS `FACTSHEET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FACTSHEET` (
  `ID` varchar(255) NOT NULL,
  `CONTACTDETAILS_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FACTSHEET_CONTACTDETAILS_ID` (`CONTACTDETAILS_ID`),
  CONSTRAINT `FK_FACTSHEET_CONTACTDETAILS_ID` FOREIGN KEY (`CONTACTDETAILS_ID`) REFERENCES `CONTACTDETAILS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FACTSHEETADDITIONALINFO`
--

DROP TABLE IF EXISTS `FACTSHEETADDITIONALINFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FACTSHEETADDITIONALINFO` (
  `ID` varchar(255) NOT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  `INFO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FACTSHEETADDITIONALINFO_INFO` (`INFO`),
  CONSTRAINT `FK_FACTSHEETADDITIONALINFO_INFO` FOREIGN KEY (`INFO`) REFERENCES `CONTACTINFO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FACTSHEETLANGUAGEITEM`
--

DROP TABLE IF EXISTS `FACTSHEETLANGUAGEITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FACTSHEETLANGUAGEITEM` (
  `ID` varchar(255) NOT NULL,
  `LANG` varchar(255) DEFAULT NULL,
  `TEXT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FACTSHEET_CONTACT_URL`
--

DROP TABLE IF EXISTS `FACTSHEET_CONTACT_URL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FACTSHEET_CONTACT_URL` (
  `ContactInfo_ID` varchar(255) NOT NULL,
  `url_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ContactInfo_ID`,`url_ID`),
  KEY `FK_FACTSHEET_CONTACT_URL_url_ID` (`url_ID`),
  CONSTRAINT `FK_FACTSHEET_CONTACT_URL_ContactInfo_ID` FOREIGN KEY (`ContactInfo_ID`) REFERENCES `CONTACTINFO` (`ID`),
  CONSTRAINT `FK_FACTSHEET_CONTACT_URL_url_ID` FOREIGN KEY (`url_ID`) REFERENCES `FACTSHEETLANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FACT_SHEET_URL`
--

DROP TABLE IF EXISTS `FACT_SHEET_URL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FACT_SHEET_URL` (
  `FactSheet_ID` varchar(255) NOT NULL,
  `url_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`FactSheet_ID`,`url_ID`),
  KEY `FK_FACT_SHEET_URL_url_ID` (`url_ID`),
  CONSTRAINT `FK_FACT_SHEET_URL_FactSheet_ID` FOREIGN KEY (`FactSheet_ID`) REFERENCES `FACTSHEET` (`ID`),
  CONSTRAINT `FK_FACT_SHEET_URL_url_ID` FOREIGN KEY (`url_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FLEXIBLEADDRESS`
--

DROP TABLE IF EXISTS `FLEXIBLEADDRESS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FLEXIBLEADDRESS` (
  `ID` varchar(255) NOT NULL,
  `BUILDINGNAME` varchar(255) DEFAULT NULL,
  `BUILDINGNUMBER` varchar(255) DEFAULT NULL,
  `COUNTRY` varchar(255) DEFAULT NULL,
  `DELIVERYPOINTCODE` longblob,
  `FLOOR` varchar(255) DEFAULT NULL,
  `LOCALITY` varchar(255) DEFAULT NULL,
  `POSTOFFICEBOX` varchar(255) DEFAULT NULL,
  `POSTALCODE` varchar(255) DEFAULT NULL,
  `RECIPIENTNAME` longblob,
  `REGION` varchar(255) DEFAULT NULL,
  `STREETNAME` varchar(255) DEFAULT NULL,
  `UNIT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FlexibleAddress_ADDRESSLINE`
--

DROP TABLE IF EXISTS `FlexibleAddress_ADDRESSLINE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FlexibleAddress_ADDRESSLINE` (
  `FlexibleAddress_ID` varchar(255) DEFAULT NULL,
  `ADDRESSLINE` varchar(255) DEFAULT NULL,
  KEY `FK_FlexibleAddress_ADDRESSLINE_FlexibleAddress_ID` (`FlexibleAddress_ID`),
  CONSTRAINT `FK_FlexibleAddress_ADDRESSLINE_FlexibleAddress_ID` FOREIGN KEY (`FlexibleAddress_ID`) REFERENCES `FLEXIBLEADDRESS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FlexibleAddress_DELIVERYPOINTCODE`
--

DROP TABLE IF EXISTS `FlexibleAddress_DELIVERYPOINTCODE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FlexibleAddress_DELIVERYPOINTCODE` (
  `FlexibleAddress_ID` varchar(255) DEFAULT NULL,
  `DELIVERYPOINTCODE` varchar(255) DEFAULT NULL,
  KEY `FlexibleAddressDELIVERYPOINTCODEFlexibleAddress_ID` (`FlexibleAddress_ID`),
  CONSTRAINT `FlexibleAddressDELIVERYPOINTCODEFlexibleAddress_ID` FOREIGN KEY (`FlexibleAddress_ID`) REFERENCES `FLEXIBLEADDRESS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FlexibleAddress_RECIPIENTNAME`
--

DROP TABLE IF EXISTS `FlexibleAddress_RECIPIENTNAME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FlexibleAddress_RECIPIENTNAME` (
  `FlexibleAddress_ID` varchar(255) DEFAULT NULL,
  `RECIPIENTNAME` varchar(255) DEFAULT NULL,
  KEY `FlexibleAddress_RECIPIENTNAME_FlexibleAddress_ID` (`FlexibleAddress_ID`),
  CONSTRAINT `FlexibleAddress_RECIPIENTNAME_FlexibleAddress_ID` FOREIGN KEY (`FlexibleAddress_ID`) REFERENCES `FLEXIBLEADDRESS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IDENTIFICATIONITEM`
--

DROP TABLE IF EXISTS `IDENTIFICATIONITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IDENTIFICATIONITEM` (
  `ID` varchar(255) NOT NULL,
  `IDTYPE` varchar(255) DEFAULT NULL,
  `IDVALUE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IIA`
--

DROP TABLE IF EXISTS `IIA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IIA` (
  `ID` varchar(255) NOT NULL,
  `CONDITIONS_HASH` varchar(255) DEFAULT NULL,
  `ENDDATE` date DEFAULT NULL,
  `IIACODE` varchar(255) DEFAULT NULL,
  `INEFECT` tinyint(1) DEFAULT '0',
  `MODIFYDATE` date DEFAULT NULL,
  `PDF` longblob,
  `SIGNINGDATE` date DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `ID_PARTNER` varchar(255) DEFAULT NULL,
  `HASH_PARTNER` varchar(255) DEFAULT NULL,
  `CONDITIONS_TERMINATED_AS_A_WHOLE` tinyint(1) DEFAULT NULL,
  `ORIGINAL_IIA` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IIA_FK_ORIGINAL` (`ORIGINAL_IIA`),
  CONSTRAINT `IIA_FK_ORIGINAL` FOREIGN KEY (`ORIGINAL_IIA`) REFERENCES `IIA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IIAAPPROVAL`
--

DROP TABLE IF EXISTS `IIAAPPROVAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IIAAPPROVAL` (
  `ID` varchar(255) NOT NULL,
  `CONDITIONS_HASH` varchar(255) DEFAULT NULL,
  `ENDDATE` date DEFAULT NULL,
  `IIACODE` varchar(255) DEFAULT NULL,
  `MODIFYDATE` date DEFAULT NULL,
  `PDF` longblob,
  `STARTDATE` date DEFAULT NULL,
  `HEI_ID` varchar(255) DEFAULT NULL,
  `IIA_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IIAAPPROVAL_IIA_FK` (`IIA_ID`),
  CONSTRAINT `IIAAPPROVAL_IIA_FK` FOREIGN KEY (`IIA_ID`) REFERENCES `IIA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IIAPARTNER`
--

DROP TABLE IF EXISTS `IIAPARTNER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IIAPARTNER` (
  `ID` varchar(255) NOT NULL,
  `IIACODE` varchar(255) DEFAULT NULL,
  `IIAID` varchar(255) DEFAULT NULL,
  `INSTITUTIONID` varchar(255) DEFAULT NULL,
  `ORGANIZATIONUNITID` varchar(255) DEFAULT NULL,
  `SINGING_CONTACT_PARTNER` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_IIAPARTNER_SINGING_CONTACT_PARTNER` (`SINGING_CONTACT_PARTNER`),
  CONSTRAINT `FK_IIAPARTNER_SINGING_CONTACT_PARTNER` FOREIGN KEY (`SINGING_CONTACT_PARTNER`) REFERENCES `CONTACT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IIA_COOPERATION_CONDITION`
--

DROP TABLE IF EXISTS `IIA_COOPERATION_CONDITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IIA_COOPERATION_CONDITION` (
  `Iia_ID` varchar(255) NOT NULL,
  `cooperationConditions_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Iia_ID`,`cooperationConditions_ID`),
  KEY `IIA_COOPERATION_CONDITION_cooperationConditions_ID` (`cooperationConditions_ID`),
  CONSTRAINT `FK_IIA_COOPERATION_CONDITION_Iia_ID` FOREIGN KEY (`Iia_ID`) REFERENCES `IIA` (`ID`),
  CONSTRAINT `IIA_COOPERATION_CONDITION_cooperationConditions_ID` FOREIGN KEY (`cooperationConditions_ID`) REFERENCES `COOPERATIONCONDITION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IIA_PARTNER_CONTACTS`
--

DROP TABLE IF EXISTS `IIA_PARTNER_CONTACTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IIA_PARTNER_CONTACTS` (
  `IiaPartner_ID` varchar(255) NOT NULL,
  `contacts_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`IiaPartner_ID`,`contacts_ID`),
  KEY `FK_IIA_PARTNER_CONTACTS_contacts_ID` (`contacts_ID`),
  CONSTRAINT `FK_IIA_PARTNER_CONTACTS_IiaPartner_ID` FOREIGN KEY (`IiaPartner_ID`) REFERENCES `IIAPARTNER` (`ID`),
  CONSTRAINT `FK_IIA_PARTNER_CONTACTS_contacts_ID` FOREIGN KEY (`contacts_ID`) REFERENCES `CONTACT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `IMOBILITY`
--

DROP TABLE IF EXISTS `IMOBILITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `IMOBILITY` (
  `ID` varchar(255) NOT NULL,
  `ACTUALARRIVALDATE` date DEFAULT NULL,
  `ACTUALDEPARTUREDATE` date DEFAULT NULL,
  `COMMENT` varchar(255) DEFAULT NULL,
  `ISTATUS` int(11) DEFAULT NULL,
  `OMOBILITYID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INFO_WEB_SITE`
--

DROP TABLE IF EXISTS `INFO_WEB_SITE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INFO_WEB_SITE` (
  `AdditionalRequirement_ID` varchar(255) NOT NULL,
  `informationWebsite_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`AdditionalRequirement_ID`,`informationWebsite_ID`),
  KEY `FK_INFO_WEB_SITE_informationWebsite_ID` (`informationWebsite_ID`),
  CONSTRAINT `FK_INFO_WEB_SITE_AdditionalRequirement_ID` FOREIGN KEY (`AdditionalRequirement_ID`) REFERENCES `ADDITIONALREQUIREMENT` (`ID`),
  CONSTRAINT `FK_INFO_WEB_SITE_informationWebsite_ID` FOREIGN KEY (`informationWebsite_ID`) REFERENCES `FACTSHEETLANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INSTITUTION`
--

DROP TABLE IF EXISTS `INSTITUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INSTITUTION` (
  `ID` varchar(255) NOT NULL,
  `ABBREVIATION` varchar(255) DEFAULT NULL,
  `INSTITUTIONID` varchar(255) DEFAULT NULL,
  `LOGOURL` varchar(255) DEFAULT NULL,
  `FACT_SHEET` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_INSTITUTION_FACT_SHEET` (`FACT_SHEET`),
  CONSTRAINT `FK_INSTITUTION_FACT_SHEET` FOREIGN KEY (`FACT_SHEET`) REFERENCES `FACTSHEET` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INSTITUTION_NAME`
--

DROP TABLE IF EXISTS `INSTITUTION_NAME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INSTITUTION_NAME` (
  `Institution_ID` varchar(255) NOT NULL,
  `name_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Institution_ID`,`name_ID`),
  KEY `FK_INSTITUTION_NAME_name_ID` (`name_ID`),
  CONSTRAINT `FK_INSTITUTION_NAME_Institution_ID` FOREIGN KEY (`Institution_ID`) REFERENCES `INSTITUTION` (`ID`),
  CONSTRAINT `FK_INSTITUTION_NAME_name_ID` FOREIGN KEY (`name_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INST_ORG_UNIT`
--

DROP TABLE IF EXISTS `INST_ORG_UNIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INST_ORG_UNIT` (
  `Institution_ID` varchar(255) NOT NULL,
  `organizationUnits_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Institution_ID`,`organizationUnits_ID`),
  KEY `FK_INST_ORG_UNIT_organizationUnits_ID` (`organizationUnits_ID`),
  CONSTRAINT `FK_INST_ORG_UNIT_Institution_ID` FOREIGN KEY (`Institution_ID`) REFERENCES `INSTITUTION` (`ID`),
  CONSTRAINT `FK_INST_ORG_UNIT_organizationUnits_ID` FOREIGN KEY (`organizationUnits_ID`) REFERENCES `ORGANIZATIONUNIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INST_OTHER_ID`
--

DROP TABLE IF EXISTS `INST_OTHER_ID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INST_OTHER_ID` (
  `Institution_ID` varchar(255) NOT NULL,
  `otherId_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Institution_ID`,`otherId_ID`),
  KEY `FK_INST_OTHER_ID_otherId_ID` (`otherId_ID`),
  CONSTRAINT `FK_INST_OTHER_ID_Institution_ID` FOREIGN KEY (`Institution_ID`) REFERENCES `INSTITUTION` (`ID`),
  CONSTRAINT `FK_INST_OTHER_ID_otherId_ID` FOREIGN KEY (`otherId_ID`) REFERENCES `IDENTIFICATIONITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LANGSKILL_COOPERATION_CONDITION`
--

DROP TABLE IF EXISTS `LANGSKILL_COOPERATION_CONDITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LANGSKILL_COOPERATION_CONDITION` (
  `CooperationCondition_ID` varchar(255) NOT NULL,
  `recommendedLanguageSkill_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`CooperationCondition_ID`,`recommendedLanguageSkill_ID`),
  KEY `LNGSKLLCOOPERATIONCONDITIONrcmmndedLanguageSkillID` (`recommendedLanguageSkill_ID`),
  CONSTRAINT `LNGSKILLCOOPERATIONCONDITIONCooperationConditionID` FOREIGN KEY (`CooperationCondition_ID`) REFERENCES `COOPERATIONCONDITION` (`ID`),
  CONSTRAINT `LNGSKLLCOOPERATIONCONDITIONrcmmndedLanguageSkillID` FOREIGN KEY (`recommendedLanguageSkill_ID`) REFERENCES `COOPERATIONCONDLANGUAGESKILL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LANGSKILL_MOBILITY`
--

DROP TABLE IF EXISTS `LANGSKILL_MOBILITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LANGSKILL_MOBILITY` (
  `Mobility_ID` varchar(255) NOT NULL,
  `nomineeLanguageSkill_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`Mobility_ID`,`nomineeLanguageSkill_ID`),
  KEY `FK_LANGSKILL_MOBILITY_nomineeLanguageSkill_ID` (`nomineeLanguageSkill_ID`),
  CONSTRAINT `FK_LANGSKILL_MOBILITY_Mobility_ID` FOREIGN KEY (`Mobility_ID`) REFERENCES `MOBILITY` (`ID`),
  CONSTRAINT `FK_LANGSKILL_MOBILITY_nomineeLanguageSkill_ID` FOREIGN KEY (`nomineeLanguageSkill_ID`) REFERENCES `LANGUAGESKILL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LANGUAGEITEM`
--

DROP TABLE IF EXISTS `LANGUAGEITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LANGUAGEITEM` (
  `ID` varchar(255) NOT NULL,
  `LANG` varchar(255) DEFAULT NULL,
  `TEXT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LANGUAGESKILL`
--

DROP TABLE IF EXISTS `LANGUAGESKILL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LANGUAGESKILL` (
  `ID` varchar(255) NOT NULL,
  `CEFRLEVEL` varchar(255) DEFAULT NULL,
  `LANGUAGE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LISTOFCOMPONENTS`
--

DROP TABLE IF EXISTS `LISTOFCOMPONENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LISTOFCOMPONENTS` (
  `ID` varchar(255) NOT NULL,
  `DTYPE` varchar(31) DEFAULT NULL,
  `COMMENT_RECEIVING_HEI_SIGNATURE` varchar(255) DEFAULT NULL,
  `COMMENT_SENDING_HEI_SIGNATURE` varchar(255) DEFAULT NULL,
  `COMMENT_STUDENT_SIGNATURE` varchar(255) DEFAULT NULL,
  `CHANGE_PROPOSAL_STUDENT` varchar(255) DEFAULT NULL,
  `CHANGE_PROPOSAL_APPROVED_PROPOSAL` varchar(255) DEFAULT NULL,
  `CHANGE_PROPOSAL_COMMENT_PROPOSAL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_LISTOFCOMPONENTS_COMMENT_SENDING_HEI_SIGNATURE` (`COMMENT_SENDING_HEI_SIGNATURE`),
  KEY `LISTOFCOMPONENTS_COMMENT_RECEIVING_HEI_SIGNATURE` (`COMMENT_RECEIVING_HEI_SIGNATURE`),
  KEY `FK_LISTOFCOMPONENTS_CHANGE_PROPOSAL_STUDENT` (`CHANGE_PROPOSAL_STUDENT`),
  KEY `FK_LISTOFCOMPONENTS_COMMENT_STUDENT_SIGNATURE` (`COMMENT_STUDENT_SIGNATURE`),
  KEY `fk_CHANGE_PROPOSAL_APPROVED_PROPOSAL` (`CHANGE_PROPOSAL_APPROVED_PROPOSAL`),
  KEY `fk_CHANGE_PROPOSAL_COMMENT_PROPOSAL` (`CHANGE_PROPOSAL_COMMENT_PROPOSAL`),
  CONSTRAINT `FK_LISTOFCOMPONENTS_CHANGE_PROPOSAL_STUDENT` FOREIGN KEY (`CHANGE_PROPOSAL_STUDENT`) REFERENCES `STUDENT` (`ID`),
  CONSTRAINT `FK_LISTOFCOMPONENTS_COMMENT_SENDING_HEI_SIGNATURE` FOREIGN KEY (`COMMENT_SENDING_HEI_SIGNATURE`) REFERENCES `SIGNATURE` (`ID`),
  CONSTRAINT `FK_LISTOFCOMPONENTS_COMMENT_STUDENT_SIGNATURE` FOREIGN KEY (`COMMENT_STUDENT_SIGNATURE`) REFERENCES `SIGNATURE` (`ID`),
  CONSTRAINT `LISTOFCOMPONENTS_COMMENT_RECEIVING_HEI_SIGNATURE` FOREIGN KEY (`COMMENT_RECEIVING_HEI_SIGNATURE`) REFERENCES `SIGNATURE` (`ID`),
  CONSTRAINT `fk_CHANGE_PROPOSAL_APPROVED_PROPOSAL` FOREIGN KEY (`CHANGE_PROPOSAL_APPROVED_PROPOSAL`) REFERENCES `APPROVEDPROPOSAL` (`ID`),
  CONSTRAINT `fk_CHANGE_PROPOSAL_COMMENT_PROPOSAL` FOREIGN KEY (`CHANGE_PROPOSAL_COMMENT_PROPOSAL`) REFERENCES `COMMENTPROPOSAL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MOBILITY`
--

DROP TABLE IF EXISTS `MOBILITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOBILITY` (
  `ID` varchar(255) NOT NULL,
  `ACTIVITYATTRIBUTE` int(11) DEFAULT NULL,
  `ACTIVITYTYPE` int(11) DEFAULT NULL,
  `ACTUALARRIVALDATE` date DEFAULT NULL,
  `ACTUALDEPARTUREDATE` date DEFAULT NULL,
  `EQFLEVELDEPARTURE` tinyint(4) DEFAULT NULL,
  `EQFLEVELNOMINATION` tinyint(4) DEFAULT NULL,
  `IIAID` varchar(255) DEFAULT NULL,
  `MOBILITYPARTICIPANTID` varchar(255) DEFAULT NULL,
  `NOMINEEISCEDFCODE` varchar(255) DEFAULT NULL,
  `PLANNEDARRIVALDATE` date DEFAULT NULL,
  `PLANNEDDEPARTUREDATE` date DEFAULT NULL,
  `RECEIVINGACADEMICYEARID` varchar(255) DEFAULT NULL,
  `RECEIVINGINSTITUTIONID` varchar(255) DEFAULT NULL,
  `RECEIVINGORGANIZATIONUNITID` varchar(255) DEFAULT NULL,
  `SENDINGACADEMICTERMEWPID` varchar(255) DEFAULT NULL,
  `SENDINGINSTITUTIONID` varchar(255) DEFAULT NULL,
  `SENDINGORGANIZATIONUNITID` varchar(255) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MOBILITYFACTSHEET`
--

DROP TABLE IF EXISTS `MOBILITYFACTSHEET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOBILITYFACTSHEET` (
  `ID` varchar(255) NOT NULL,
  `DECISIONWEEKSLIMIT` bigint(20) DEFAULT NULL,
  `HEIID` varchar(255) DEFAULT NULL,
  `TORWEEKSLIMIT` bigint(20) DEFAULT NULL,
  `APPLICATION_INFO` varchar(255) DEFAULT NULL,
  `HOUSING_INFO` varchar(255) DEFAULT NULL,
  `INSURANCE_INFO` varchar(255) DEFAULT NULL,
  `AUTOM_TERM_ID` varchar(255) DEFAULT NULL,
  `SPRING_TERM_ID` varchar(255) DEFAULT NULL,
  `VISA_INFO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_MOBILITYFACTSHEET_SPRING_TERM_ID` (`SPRING_TERM_ID`),
  KEY `FK_MOBILITYFACTSHEET_AUTOM_TERM_ID` (`AUTOM_TERM_ID`),
  KEY `FK_MOBILITYFACTSHEET_VISA_INFO` (`VISA_INFO`),
  KEY `FK_MOBILITYFACTSHEET_HOUSING_INFO` (`HOUSING_INFO`),
  KEY `FK_MOBILITYFACTSHEET_APPLICATION_INFO` (`APPLICATION_INFO`),
  KEY `FK_MOBILITYFACTSHEET_INSURANCE_INFO` (`INSURANCE_INFO`),
  CONSTRAINT `FK_MOBILITYFACTSHEET_APPLICATION_INFO` FOREIGN KEY (`APPLICATION_INFO`) REFERENCES `CONTACTINFO` (`ID`),
  CONSTRAINT `FK_MOBILITYFACTSHEET_AUTOM_TERM_ID` FOREIGN KEY (`AUTOM_TERM_ID`) REFERENCES `CALENDARENTRY` (`ID`),
  CONSTRAINT `FK_MOBILITYFACTSHEET_HOUSING_INFO` FOREIGN KEY (`HOUSING_INFO`) REFERENCES `CONTACTINFO` (`ID`),
  CONSTRAINT `FK_MOBILITYFACTSHEET_INSURANCE_INFO` FOREIGN KEY (`INSURANCE_INFO`) REFERENCES `CONTACTINFO` (`ID`),
  CONSTRAINT `FK_MOBILITYFACTSHEET_SPRING_TERM_ID` FOREIGN KEY (`SPRING_TERM_ID`) REFERENCES `CALENDARENTRY` (`ID`),
  CONSTRAINT `FK_MOBILITYFACTSHEET_VISA_INFO` FOREIGN KEY (`VISA_INFO`) REFERENCES `CONTACTINFO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MOBILITYINSTITUTION`
--

DROP TABLE IF EXISTS `MOBILITYINSTITUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOBILITYINSTITUTION` (
  `ID` varchar(255) NOT NULL,
  `HEIID` varchar(255) DEFAULT NULL,
  `OUNITID` varchar(255) DEFAULT NULL,
  `OUNITNAME` varchar(255) DEFAULT NULL,
  `OMOBILITY_LAS_CONTACT_PERSON` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `MOBILITYINSTITUTION_OMOBILITY_LAS_CONTACT_PERSON` (`OMOBILITY_LAS_CONTACT_PERSON`),
  CONSTRAINT `MOBILITYINSTITUTION_OMOBILITY_LAS_CONTACT_PERSON` FOREIGN KEY (`OMOBILITY_LAS_CONTACT_PERSON`) REFERENCES `CONTACTPERSON` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MOBILITYNUMBER`
--

DROP TABLE IF EXISTS `MOBILITYNUMBER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOBILITYNUMBER` (
  `ID` varchar(255) NOT NULL,
  `NUMBER` int(11) DEFAULT NULL,
  `VARIANT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MOBILITYPARTICIPANT`
--

DROP TABLE IF EXISTS `MOBILITYPARTICIPANT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOBILITYPARTICIPANT` (
  `ID` varchar(255) NOT NULL,
  `CONTACTDETAILS_ID` varchar(255) DEFAULT NULL,
  `PERSON_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_MOBILITYPARTICIPANT_PERSON_ID` (`PERSON_ID`),
  KEY `FK_MOBILITYPARTICIPANT_CONTACTDETAILS_ID` (`CONTACTDETAILS_ID`),
  CONSTRAINT `FK_MOBILITYPARTICIPANT_CONTACTDETAILS_ID` FOREIGN KEY (`CONTACTDETAILS_ID`) REFERENCES `CONTACTDETAILS` (`ID`),
  CONSTRAINT `FK_MOBILITYPARTICIPANT_PERSON_ID` FOREIGN KEY (`PERSON_ID`) REFERENCES `PERSON` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MOBILITYTYPE`
--

DROP TABLE IF EXISTS `MOBILITYTYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOBILITYTYPE` (
  `ID` varchar(255) NOT NULL,
  `MOBILITYCATEGORY` varchar(255) DEFAULT NULL,
  `MOBILITYGROUP` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NOTIFICATION`
--

DROP TABLE IF EXISTS `NOTIFICATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `NOTIFICATION` (
  `ID` varchar(255) NOT NULL,
  `CHANGEDELEMENTIDS` varchar(255) DEFAULT NULL,
  `HEIID` varchar(255) DEFAULT NULL,
  `NOTIFICATIONDATE` date DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OLASLANGUAGESKILL`
--

DROP TABLE IF EXISTS `OLASLANGUAGESKILL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OLASLANGUAGESKILL` (
  `ID` varchar(255) NOT NULL,
  `CEFRLEVEL` varchar(255) DEFAULT NULL,
  `LANGUAGE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OLEARNINGAGREEMENT`
--

DROP TABLE IF EXISTS `OLEARNINGAGREEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OLEARNINGAGREEMENT` (
  `ID` varchar(255) NOT NULL,
  `ENDDATE` date DEFAULT NULL,
  `ENDYEARMONTH` date DEFAULT NULL,
  `EQFLEVELSTUDIEDATDEPARTURE` tinyint(4) DEFAULT NULL,
  `ISCEDCLARIFICATION` varchar(255) DEFAULT NULL,
  `ISCEDFCODE` varchar(255) DEFAULT NULL,
  `LEARNINGOUTCOMESURL` varchar(255) DEFAULT NULL,
  `OMOBILITYID` varchar(255) DEFAULT NULL,
  `PROVISIONSURL` varchar(255) DEFAULT NULL,
  `RECEIVINGACADEMICTERMEWPID` varchar(255) DEFAULT NULL,
  `STARTDATE` date DEFAULT NULL,
  `STARTYEARMONTH` date DEFAULT NULL,
  `APPROVED_VERSION` varchar(255) DEFAULT NULL,
  `FIRST_VERSION` varchar(255) DEFAULT NULL,
  `LANGSKILL_OMOBILITY_LAS` varchar(255) DEFAULT NULL,
  `CHANGES_PROPOSAL` varchar(255) DEFAULT NULL,
  `OMOBILITY_LAS_RECEIVING_HEI` varchar(255) DEFAULT NULL,
  `OMOBILITY_LAS_SENDING_HEI` varchar(255) DEFAULT NULL,
  `OMOBILITY_LAS_STUDENT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_OLEARNINGAGREEMENT_CHANGES_PROPOSAL` (`CHANGES_PROPOSAL`),
  KEY `FK_OLEARNINGAGREEMENT_OMOBILITY_LAS_SENDING_HEI` (`OMOBILITY_LAS_SENDING_HEI`),
  KEY `FK_OLEARNINGAGREEMENT_APPROVED_VERSION` (`APPROVED_VERSION`),
  KEY `FK_OLEARNINGAGREEMENT_OMOBILITY_LAS_STUDENT` (`OMOBILITY_LAS_STUDENT`),
  KEY `FK_OLEARNINGAGREEMENT_LANGSKILL_OMOBILITY_LAS` (`LANGSKILL_OMOBILITY_LAS`),
  KEY `FK_OLEARNINGAGREEMENT_FIRST_VERSION` (`FIRST_VERSION`),
  KEY `FK_OLEARNINGAGREEMENT_OMOBILITY_LAS_RECEIVING_HEI` (`OMOBILITY_LAS_RECEIVING_HEI`),
  CONSTRAINT `FK_OLEARNINGAGREEMENT_APPROVED_VERSION` FOREIGN KEY (`APPROVED_VERSION`) REFERENCES `LISTOFCOMPONENTS` (`ID`),
  CONSTRAINT `FK_OLEARNINGAGREEMENT_CHANGES_PROPOSAL` FOREIGN KEY (`CHANGES_PROPOSAL`) REFERENCES `LISTOFCOMPONENTS` (`ID`),
  CONSTRAINT `FK_OLEARNINGAGREEMENT_FIRST_VERSION` FOREIGN KEY (`FIRST_VERSION`) REFERENCES `LISTOFCOMPONENTS` (`ID`),
  CONSTRAINT `FK_OLEARNINGAGREEMENT_LANGSKILL_OMOBILITY_LAS` FOREIGN KEY (`LANGSKILL_OMOBILITY_LAS`) REFERENCES `OLASLANGUAGESKILL` (`ID`),
  CONSTRAINT `FK_OLEARNINGAGREEMENT_OMOBILITY_LAS_RECEIVING_HEI` FOREIGN KEY (`OMOBILITY_LAS_RECEIVING_HEI`) REFERENCES `MOBILITYINSTITUTION` (`ID`),
  CONSTRAINT `FK_OLEARNINGAGREEMENT_OMOBILITY_LAS_SENDING_HEI` FOREIGN KEY (`OMOBILITY_LAS_SENDING_HEI`) REFERENCES `MOBILITYINSTITUTION` (`ID`),
  CONSTRAINT `FK_OLEARNINGAGREEMENT_OMOBILITY_LAS_STUDENT` FOREIGN KEY (`OMOBILITY_LAS_STUDENT`) REFERENCES `STUDENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OMIBILITYCOMPONENTRECOGNIZED`
--

DROP TABLE IF EXISTS `OMIBILITYCOMPONENTRECOGNIZED`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OMIBILITYCOMPONENTRECOGNIZED` (
  `ID` varchar(255) NOT NULL,
  `LOIID` varchar(255) DEFAULT NULL,
  `LOSID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OMOBILITYLASUPDATEREQUEST`
--

DROP TABLE IF EXISTS `OMOBILITYLASUPDATEREQUEST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OMOBILITYLASUPDATEREQUEST` (
  `ID` varchar(255) NOT NULL,
  `SENDINGHEIID` varchar(255) DEFAULT NULL,
  `APPROVE_COMPONENT_STUDIE_DRAFT` varchar(255) DEFAULT NULL,
  `UPDATE_COMPONENT_STUDIED` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `OMOBILITYLASUPDATEREQUEST_UPDATE_COMPONENT_STUDIED` (`UPDATE_COMPONENT_STUDIED`),
  KEY `MOBILITYLASUPDATEREQUESTPPROVECOMPONENTSTUDIEDRAFT` (`APPROVE_COMPONENT_STUDIE_DRAFT`),
  CONSTRAINT `MOBILITYLASUPDATEREQUESTPPROVECOMPONENTSTUDIEDRAFT` FOREIGN KEY (`APPROVE_COMPONENT_STUDIE_DRAFT`) REFERENCES `APPROVEDPROPOSAL` (`ID`),
  CONSTRAINT `OMOBILITYLASUPDATEREQUEST_UPDATE_COMPONENT_STUDIED` FOREIGN KEY (`UPDATE_COMPONENT_STUDIED`) REFERENCES `COMMENTPROPOSAL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OMOBILITYPHONENUMBER`
--

DROP TABLE IF EXISTS `OMOBILITYPHONENUMBER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OMOBILITYPHONENUMBER` (
  `ID` varchar(255) NOT NULL,
  `E164` varchar(255) DEFAULT NULL,
  `EXTENSIONNUMBER` varchar(255) DEFAULT NULL,
  `OTHERFORMAT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ORGANIZATIONUNIT`
--

DROP TABLE IF EXISTS `ORGANIZATIONUNIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ORGANIZATIONUNIT` (
  `ID` varchar(255) NOT NULL,
  `ABBREVIATION` varchar(255) DEFAULT NULL,
  `LOGOURL` varchar(255) DEFAULT NULL,
  `ORGANIZATIONUNITCODE` varchar(255) DEFAULT NULL,
  `FACT_SHEET` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ORGANIZATIONUNIT_FACT_SHEET` (`FACT_SHEET`),
  CONSTRAINT `FK_ORGANIZATIONUNIT_FACT_SHEET` FOREIGN KEY (`FACT_SHEET`) REFERENCES `FACTSHEET` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ORGANIZATION_UNIT_NAME`
--

DROP TABLE IF EXISTS `ORGANIZATION_UNIT_NAME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ORGANIZATION_UNIT_NAME` (
  `OrganizationUnit_ID` varchar(255) NOT NULL,
  `name_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`OrganizationUnit_ID`,`name_ID`),
  KEY `FK_ORGANIZATION_UNIT_NAME_name_ID` (`name_ID`),
  CONSTRAINT `FK_ORGANIZATION_UNIT_NAME_OrganizationUnit_ID` FOREIGN KEY (`OrganizationUnit_ID`) REFERENCES `ORGANIZATIONUNIT` (`ID`),
  CONSTRAINT `FK_ORGANIZATION_UNIT_NAME_name_ID` FOREIGN KEY (`name_ID`) REFERENCES `LANGUAGEITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ORG_UNIT_ORG_UNIT`
--

DROP TABLE IF EXISTS `ORG_UNIT_ORG_UNIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ORG_UNIT_ORG_UNIT` (
  `OrganizationUnit_ID` varchar(255) NOT NULL,
  `organizationUnits_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`OrganizationUnit_ID`,`organizationUnits_ID`),
  KEY `FK_ORG_UNIT_ORG_UNIT_organizationUnits_ID` (`organizationUnits_ID`),
  CONSTRAINT `FK_ORG_UNIT_ORG_UNIT_OrganizationUnit_ID` FOREIGN KEY (`OrganizationUnit_ID`) REFERENCES `ORGANIZATIONUNIT` (`ID`),
  CONSTRAINT `FK_ORG_UNIT_ORG_UNIT_organizationUnits_ID` FOREIGN KEY (`organizationUnits_ID`) REFERENCES `ORGANIZATIONUNIT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `OU_OTHER_ID`
--

DROP TABLE IF EXISTS `OU_OTHER_ID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OU_OTHER_ID` (
  `OrganizationUnit_ID` varchar(255) NOT NULL,
  `otherId_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`OrganizationUnit_ID`,`otherId_ID`),
  KEY `FK_OU_OTHER_ID_otherId_ID` (`otherId_ID`),
  CONSTRAINT `FK_OU_OTHER_ID_OrganizationUnit_ID` FOREIGN KEY (`OrganizationUnit_ID`) REFERENCES `ORGANIZATIONUNIT` (`ID`),
  CONSTRAINT `FK_OU_OTHER_ID_otherId_ID` FOREIGN KEY (`otherId_ID`) REFERENCES `IDENTIFICATIONITEM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PERSON`
--

DROP TABLE IF EXISTS `PERSON`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PERSON` (
  `ID` varchar(255) NOT NULL,
  `BIRTHDATE` date DEFAULT NULL,
  `COUNTRYCODE` varchar(255) DEFAULT NULL,
  `FIRSTNAMES` varchar(255) DEFAULT NULL,
  `GENDER` int(11) DEFAULT NULL,
  `GLOBALID` varchar(255) DEFAULT NULL,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `PERSONID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PHONENUMBER`
--

DROP TABLE IF EXISTS `PHONENUMBER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PHONENUMBER` (
  `ID` varchar(255) NOT NULL,
  `E164` varchar(255) DEFAULT NULL,
  `EXTENSIONNUMBER` varchar(255) DEFAULT NULL,
  `OTHERFORMAT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RECEIVING_ACADEMIC_YEAR_ID`
--

DROP TABLE IF EXISTS `RECEIVING_ACADEMIC_YEAR_ID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RECEIVING_ACADEMIC_YEAR_ID` (
  `ID` varchar(255) DEFAULT NULL,
  `RECEIVINGACADEMICYEARID` varchar(255) DEFAULT NULL,
  KEY `FK_RECEIVING_ACADEMIC_YEAR_ID_ID` (`ID`),
  CONSTRAINT `FK_RECEIVING_ACADEMIC_YEAR_ID_ID` FOREIGN KEY (`ID`) REFERENCES `COOPERATIONCONDITION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RESULTDISTRIBUTION`
--

DROP TABLE IF EXISTS `RESULTDISTRIBUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RESULTDISTRIBUTION` (
  `ID` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RESULTDISTRIBUTIONCATEGORY`
--

DROP TABLE IF EXISTS `RESULTDISTRIBUTIONCATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RESULTDISTRIBUTIONCATEGORY` (
  `ID` varchar(255) NOT NULL,
  `DISTRUBTION_COUNT` bigint(20) DEFAULT NULL,
  `LABEL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SIGNATURE`
--

DROP TABLE IF EXISTS `SIGNATURE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SIGNATURE` (
  `ID` varchar(255) NOT NULL,
  `SIGNERAPP` varchar(255) DEFAULT NULL,
  `SIGNEREMAIL` varchar(255) DEFAULT NULL,
  `SIGNERNAME` varchar(255) DEFAULT NULL,
  `SIGNERPOSITION` varchar(255) DEFAULT NULL,
  `TIMESTAMP` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SINGLECHANGE`
--

DROP TABLE IF EXISTS `SINGLECHANGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SINGLECHANGE` (
  `ID` varchar(255) NOT NULL,
  `delete_field` tinyint(1) DEFAULT '0',
  `DISPLAYTEXT` varchar(255) DEFAULT NULL,
  `EWPREASONCODE` varchar(255) DEFAULT NULL,
  `index_field` bigint(20) DEFAULT NULL,
  `OMOBILITY_INSERT_COMPONENT_RECOGNIZED` varchar(255) DEFAULT NULL,
  `OMOBILITY_INSERT_COMPONENT_STUDIED` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `SINGLECHANGE_OMOBILITY_INSERT_COMPONENT_RECOGNIZED` (`OMOBILITY_INSERT_COMPONENT_RECOGNIZED`),
  KEY `FK_SINGLECHANGE_OMOBILITY_INSERT_COMPONENT_STUDIED` (`OMOBILITY_INSERT_COMPONENT_STUDIED`),
  CONSTRAINT `FK_SINGLECHANGE_OMOBILITY_INSERT_COMPONENT_STUDIED` FOREIGN KEY (`OMOBILITY_INSERT_COMPONENT_STUDIED`) REFERENCES `COMPONENTSSTUDIED` (`ID`),
  CONSTRAINT `SINGLECHANGE_OMOBILITY_INSERT_COMPONENT_RECOGNIZED` FOREIGN KEY (`OMOBILITY_INSERT_COMPONENT_RECOGNIZED`) REFERENCES `OMIBILITYCOMPONENTRECOGNIZED` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `STUDENT`
--

DROP TABLE IF EXISTS `STUDENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `STUDENT` (
  `ID` varchar(255) NOT NULL,
  `BIRTHDATE` date DEFAULT NULL,
  `CITIZENSHIP` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FAMILYNAME` varchar(255) DEFAULT NULL,
  `GENDER` bigint(20) DEFAULT NULL,
  `GIVENNAMES` varchar(255) DEFAULT NULL,
  `GLOBALID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SUBJECTAREA`
--

DROP TABLE IF EXISTS `SUBJECTAREA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SUBJECTAREA` (
  `ID` varchar(255) NOT NULL,
  `ISCEDCLARIFICATION` varchar(255) DEFAULT NULL,
  `ISCEDFCODE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SUBJECT_AREA_COOPERATION_CONDITION`
--

DROP TABLE IF EXISTS `SUBJECT_AREA_COOPERATION_CONDITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SUBJECT_AREA_COOPERATION_CONDITION` (
  `CooperationCondition_ID` varchar(255) NOT NULL,
  `subjectAreas_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`CooperationCondition_ID`,`subjectAreas_ID`),
  KEY `SUBJECT_AREA_COOPERATION_CONDITION_subjectAreas_ID` (`subjectAreas_ID`),
  CONSTRAINT `SBJECTAREACOOPERATIONCONDITIONCperationConditionID` FOREIGN KEY (`CooperationCondition_ID`) REFERENCES `COOPERATIONCONDITION` (`ID`),
  CONSTRAINT `SUBJECT_AREA_COOPERATION_CONDITION_subjectAreas_ID` FOREIGN KEY (`subjectAreas_ID`) REFERENCES `SUBJECTAREA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TERMID`
--

DROP TABLE IF EXISTS `TERMID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TERMID` (
  `ID` varchar(255) NOT NULL,
  `TERMNUMBER` bigint(20) DEFAULT NULL,
  `TOTALTERMS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'ewp'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-14 15:54:29
