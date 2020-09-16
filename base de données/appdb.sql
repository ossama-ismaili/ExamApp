-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Jun 30, 2020 at 09:20 AM
-- Server version: 8.0.18
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `appdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(150) NOT NULL,
  `choices` varchar(200) NOT NULL,
  `answer` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `question`, `choices`, `answer`) VALUES
(1, 'The default value of a static integer variable of a class in Java is', '0-1-Garbage value-Null', '1'),
(2, 'The fields in an interface are implicitly specified as', 'static only-private-both static and final-none of the above', 'both static and final'),
(3, 'Which of the following variable declaration would NOT compile in a java program?', 'int var;-int VAR;-int var1;-int var_1-int 1_var;', 'int 1_var;'),
(4, 'The java run time system automatically calls this method while garbage collection.', 'finalizer()-finalize()-finally()-finalized()-none of the above', 'finalize()'),
(5, 'objects are passed as', 'Method called call by value-Memory address-Constructor-Default constructor', 'Memory address'),
(6, 'A process that involves recognizing and focusing on the important characteristics of a situation or object is known as:', 'Encapsulation-Polymorphism-Abstraction-Inheritance-Object persistence', 'Abstraction'),
(7, 'In object-oriented programming, new classes can be defined by extending existing classes. This is an example of:', 'Encapsulation-Interface-Composition-Inheritance-Aggregation', 'Inheritance'),
(8, 'Given a class named student, which of the following is a valid constructor declaration for the class?', 'Student(student s){}-Student student(){}-Private final student(){}-Void student(){}-Static void student(){}', 'Student(student s){}'),
(9, 'What is the fundamental unit of information of writer streams?', 'Characters-Bytes-Files-Records-Information', 'Characters'),
(10, 'What will be the result of the expression 13&25?', '38-25-9-12-21', '9'),
(11, 'Which of those doesn\'t have an index based structure?', 'List-Set-Map', 'Set'),
(12, 'java.util.Collections is a:', 'Class-Interface-Object-None of the above', 'Class');

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

DROP TABLE IF EXISTS `results`;
CREATE TABLE IF NOT EXISTS `results` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `note` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`id`, `username`, `note`) VALUES
(1, 'etudiantuser1', 20),
(2, 'etudiantuser2', 20),
(3, 'etudiantuser3', 16),
(4, 'etudiantuser4', 12),
(7, 'etudiantuser5', 20);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(150) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fullname`, `username`, `password`, `admin`) VALUES
(1, 'Prof User', 'profuser1', '1e8cce1f441b14fe6faf27045c36771', 1),
(2, 'Etudiant User', 'etudiantuser1', '1e8cce1f441b14fe6faf27045c36771', 0),
(3, 'Etudiant User', 'etudiantuser2', '1e8cce1f441b14fe6faf27045c36771', 0),
(4, 'Etudiant User', 'etudiantuser3', '1e8cce1f441b14fe6faf27045c36771', 0),
(5, 'Etudiant User', 'etudiantuser4', '1e8cce1f441b14fe6faf27045c36771', 0),
(9, 'Etudianr User', 'etudiantuser5', '1e8cce1f441b14fe6faf27045c36771', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
