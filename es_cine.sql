-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 14, 2026 at 06:44 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `es_cine`
--
CREATE DATABASE IF NOT EXISTS `es_cine` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `es_cine`;

-- --------------------------------------------------------

--
-- Table structure for table `asientos`
--

CREATE TABLE `asientos` (
  `id_asiento` int(11) NOT NULL,
  `fk_id_sala` int(11) NOT NULL,
  `fila` varchar(2) NOT NULL,
  `numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `correo` varchar(100) DEFAULT 'Sin correo',
  `rut` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `funciones`
--

CREATE TABLE `funciones` (
  `id_funcion` int(11) NOT NULL,
  `fk_id_pelicula` int(11) NOT NULL,
  `fk_id_sala` int(11) NOT NULL,
  `horario` time NOT NULL,
  `precio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `peliculas`
--

CREATE TABLE `peliculas` (
  `id_pelicula` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `duracion` time NOT NULL,
  `clasificacion` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reservas`
--

CREATE TABLE `reservas` (
  `id_reserva` int(11) NOT NULL,
  `fk_id_cliente` int(11) NOT NULL,
  `fk_id_funcion` int(11) NOT NULL,
  `fecha_reserva` date NOT NULL,
  `tipo_entrada` varchar(20) NOT NULL DEFAULT 'General',
  `precio_final` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reservas_asientos`
--

CREATE TABLE `reservas_asientos` (
  `id_reserva_asiento` int(11) NOT NULL,
  `fk_id_reserva` int(11) NOT NULL,
  `fk_id_asiento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salas`
--

CREATE TABLE `salas` (
  `id_sala` int(11) NOT NULL,
  `capacidad` int(11) NOT NULL,
  `nombre_sala` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `asientos`
--
ALTER TABLE `asientos`
  ADD PRIMARY KEY (`id_asiento`),
  ADD KEY `FK_SALA` (`fk_id_sala`);

--
-- Indexes for table `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indexes for table `funciones`
--
ALTER TABLE `funciones`
  ADD PRIMARY KEY (`id_funcion`),
  ADD KEY `FK_PELICULA_FUNCION` (`fk_id_pelicula`),
  ADD KEY `FK_SALA_FUNCION` (`fk_id_sala`);

--
-- Indexes for table `peliculas`
--
ALTER TABLE `peliculas`
  ADD PRIMARY KEY (`id_pelicula`);

--
-- Indexes for table `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `FK_CLIENTE_RESERVA` (`fk_id_cliente`),
  ADD KEY `FK_FUNCION_RESERVA` (`fk_id_funcion`);

--
-- Indexes for table `reservas_asientos`
--
ALTER TABLE `reservas_asientos`
  ADD PRIMARY KEY (`id_reserva_asiento`),
  ADD KEY `FK_RESERVA_RA` (`fk_id_reserva`),
  ADD KEY `FK_ASIENTO_RA` (`fk_id_asiento`);

--
-- Indexes for table `salas`
--
ALTER TABLE `salas`
  ADD PRIMARY KEY (`id_sala`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `asientos`
--
ALTER TABLE `asientos`
  MODIFY `id_asiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `funciones`
--
ALTER TABLE `funciones`
  MODIFY `id_funcion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `peliculas`
--
ALTER TABLE `peliculas`
  MODIFY `id_pelicula` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservas_asientos`
--
ALTER TABLE `reservas_asientos`
  MODIFY `id_reserva_asiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salas`
--
ALTER TABLE `salas`
  MODIFY `id_sala` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `asientos`
--
ALTER TABLE `asientos`
  ADD CONSTRAINT `FK_SALA` FOREIGN KEY (`fk_id_sala`) REFERENCES `salas` (`id_sala`);

--
-- Constraints for table `funciones`
--
ALTER TABLE `funciones`
  ADD CONSTRAINT `FK_PELICULA_FUNCION` FOREIGN KEY (`fk_id_pelicula`) REFERENCES `peliculas` (`id_pelicula`),
  ADD CONSTRAINT `FK_SALA_FUNCION` FOREIGN KEY (`fk_id_sala`) REFERENCES `salas` (`id_sala`);

--
-- Constraints for table `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `FK_CLIENTE_RESERVA` FOREIGN KEY (`fk_id_cliente`) REFERENCES `clientes` (`id_cliente`),
  ADD CONSTRAINT `FK_FUNCION_RESERVA` FOREIGN KEY (`fk_id_funcion`) REFERENCES `funciones` (`id_funcion`);

--
-- Constraints for table `reservas_asientos`
--
ALTER TABLE `reservas_asientos`
  ADD CONSTRAINT `FK_ASIENTO_RA` FOREIGN KEY (`fk_id_asiento`) REFERENCES `asientos` (`id_asiento`),
  ADD CONSTRAINT `FK_RESERVA_RA` FOREIGN KEY (`fk_id_reserva`) REFERENCES `reservas` (`id_reserva`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
