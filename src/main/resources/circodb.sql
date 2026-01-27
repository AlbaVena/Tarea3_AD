-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-01-2026 a las 16:34:58
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `circodb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artistas`
--

CREATE TABLE `artistas` (
  `id_art` bigint(20) DEFAULT NULL,
  `id_persona` bigint(20) NOT NULL,
  `apodo` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artistas_numeros`
--

CREATE TABLE `artistas_numeros` (
  `id_artista` bigint(20) NOT NULL,
  `id_numero` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artista_especialidad`
--

CREATE TABLE `artista_especialidad` (
  `id_artista` bigint(20) NOT NULL,
  `id_especialidad` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `coordinadores`
--

CREATE TABLE `coordinadores` (
  `id_coordinador` bigint(20) NOT NULL,
  `senior` tinyint(1) NOT NULL DEFAULT 0,
  `fechasenior` date DEFAULT NULL,
  `id_persona` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE `credenciales` (
  `id_credenciales` bigint(20) NOT NULL,
  `id_persona` bigint(20) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `perfil` enum('ADMIN','ARTISTA','COORDINACION','INVITADO') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



--
-- Estructura de tabla para la tabla `especialidades`
--

CREATE TABLE `especialidades` (
  `id_especialidad` bigint(20) NOT NULL,
  `nombre` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `espectaculos`
--

CREATE TABLE `espectaculos` (
  `fecha_fin` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `id_coordinador_fk` bigint(20) DEFAULT NULL,
  `id_espectaculo` bigint(20) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



--
-- Estructura de tabla para la tabla `numeros`
--

CREATE TABLE `numeros` (
  `duracion` int(11) NOT NULL,
  `orden` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `id_espectaculo` bigint(20) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `id_persona` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `nacionalidad` varchar(30) NOT NULL,
  `perfil` enum('ADMIN','ARTISTA','COORDINACION','INVITADO') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




--
-- Indices de la tabla `artistas`
--
ALTER TABLE `artistas`
  ADD PRIMARY KEY (`id_persona`);

--
-- Indices de la tabla `artistas_numeros`
--
ALTER TABLE `artistas_numeros`
  ADD KEY `FKe04wskd8ej4vjrxsbgof32rml` (`id_numero`),
  ADD KEY `FK41usmdae7iupebnua4i5px3fr` (`id_artista`);

--
-- Indices de la tabla `artista_especialidad`
--
ALTER TABLE `artista_especialidad`
  ADD PRIMARY KEY (`id_artista`,`id_especialidad`),
  ADD KEY `FKtc0mw7wfc4h1wer82t8vnbf14` (`id_especialidad`);

--
-- Indices de la tabla `coordinadores`
--
ALTER TABLE `coordinadores`
  ADD PRIMARY KEY (`id_coordinador`),
  ADD KEY `coordinadores_ibfk_1` (`id_persona`);

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id_credenciales`),
  ADD UNIQUE KEY `UKkj0bakygq84a8uwy2avcihxqi` (`id_persona`);

--
-- Indices de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  ADD PRIMARY KEY (`id_especialidad`);

--
-- Indices de la tabla `espectaculos`
--
ALTER TABLE `espectaculos`
  ADD PRIMARY KEY (`id_espectaculo`),
  ADD KEY `FKkce8hdfe6qamx7ll44oug0lc` (`id_coordinador_fk`);

--
-- Indices de la tabla `numeros`
--
ALTER TABLE `numeros`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5xhmxws7vicp413dv0o23ini3` (`id_espectaculo`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id_persona`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `coordinadores`
--
ALTER TABLE `coordinadores`
  MODIFY `id_coordinador` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id_credenciales` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  MODIFY `id_especialidad` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `espectaculos`
--
ALTER TABLE `espectaculos`
  MODIFY `id_espectaculo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `numeros`
--
ALTER TABLE `numeros`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id_persona` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `artistas`
--
ALTER TABLE `artistas`
  ADD CONSTRAINT `FKrgw94oajnvibwwoag2w51fth3` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`);

--
-- Filtros para la tabla `artistas_numeros`
--
ALTER TABLE `artistas_numeros`
  ADD CONSTRAINT `FK41usmdae7iupebnua4i5px3fr` FOREIGN KEY (`id_artista`) REFERENCES `artistas` (`id_persona`),
  ADD CONSTRAINT `FKe04wskd8ej4vjrxsbgof32rml` FOREIGN KEY (`id_numero`) REFERENCES `numeros` (`id`);

--
-- Filtros para la tabla `artista_especialidad`
--
ALTER TABLE `artista_especialidad`
  ADD CONSTRAINT `FKd7digg4r757jr8nasa0le0a2p` FOREIGN KEY (`id_artista`) REFERENCES `artistas` (`id_persona`),
  ADD CONSTRAINT `FKtc0mw7wfc4h1wer82t8vnbf14` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidades` (`id_especialidad`);

--
-- Filtros para la tabla `coordinadores`
--
ALTER TABLE `coordinadores`
  ADD CONSTRAINT `FKn1n23y4dvj1dvngblt9am2au7` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`),
  ADD CONSTRAINT `coordinadores_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD CONSTRAINT `FKgntr1s6h8ryu511tqjln50yp2` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`);

--
-- Filtros para la tabla `espectaculos`
--
ALTER TABLE `espectaculos`
  ADD CONSTRAINT `FKkce8hdfe6qamx7ll44oug0lc` FOREIGN KEY (`id_coordinador_fk`) REFERENCES `coordinadores` (`id_persona`);

--
-- Filtros para la tabla `numeros`
--
ALTER TABLE `numeros`
  ADD CONSTRAINT `FK5xhmxws7vicp413dv0o23ini3` FOREIGN KEY (`id_espectaculo`) REFERENCES `espectaculos` (`id_espectaculo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
