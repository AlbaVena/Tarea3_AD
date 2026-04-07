-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-04-2026 a las 22:49:13
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
  `id_persona` bigint(20) NOT NULL,
  `apodo` varchar(25) DEFAULT NULL,
  `id_art` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `artistas`
--

INSERT INTO `artistas` (`id_persona`, `apodo`, `id_art`) VALUES
(2, 'Chispas', NULL),
(4, '', NULL),
(5, ' ', NULL),
(6, 'Diego', NULL),
(12, 'pruebadefx', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artistas_numeros`
--

CREATE TABLE `artistas_numeros` (
  `id_artista` bigint(20) NOT NULL,
  `id_numero` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `artistas_numeros`
--

INSERT INTO `artistas_numeros` (`id_artista`, `id_numero`) VALUES
(2, 31),
(5, 31),
(2, 32),
(6, 32),
(12, 33),
(12, 34),
(6, 34),
(12, 36),
(6, 36),
(2, 36),
(2, 37),
(5, 37),
(6, 38),
(4, 39),
(5, 39),
(6, 41);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `artista_especialidad`
--

CREATE TABLE `artista_especialidad` (
  `id_artista` bigint(20) NOT NULL,
  `id_especialidad` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `artista_especialidad`
--

INSERT INTO `artista_especialidad` (`id_artista`, `id_especialidad`) VALUES
(2, 1),
(2, 4),
(4, 1),
(4, 2),
(5, 1),
(5, 4),
(5, 5),
(6, 3);

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
-- Volcado de datos para la tabla `coordinadores`
--

INSERT INTO `coordinadores` (`id_coordinador`, `senior`, `fechasenior`, `id_persona`) VALUES
(1, 1, '2025-02-10', 1),
(2, 1, '2025-01-23', 3),
(3, 0, NULL, 7),
(4, 0, NULL, 8),
(7, 0, NULL, 11);

-- --------------------------------------------------------

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
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`id_credenciales`, `id_persona`, `nombre`, `password`, `perfil`) VALUES
(1, 1, 'albita', 'pass', 'ADMIN'),
(2, 2, 'pedros', 'pass', 'ARTISTA'),
(3, 3, 'carlos', 'pass', 'ADMIN'),
(4, 4, 'martius', 'pass', 'ADMIN'),
(5, 5, 'peppe', 'pass', 'ADMIN'),
(6, 6, 'diego', 'diego', 'ARTISTA'),
(7, 7, 'prueba', 'prueba', 'ADMIN'),
(8, 8, 'noadmin', 'pass', 'COORDINACION'),
(9, 11, 'pa_co', 'pass', 'COORDINACION'),
(11, 12, 'pruebaFX', 'pass', 'ARTISTA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidades`
--

CREATE TABLE `especialidades` (
  `id_especialidad` bigint(20) NOT NULL,
  `nombre` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `especialidades`
--

INSERT INTO `especialidades` (`id_especialidad`, `nombre`) VALUES
(1, 'ACROBACIA'),
(2, 'HUMOR'),
(3, 'MAGIA'),
(4, 'EQUILIBRISMO'),
(5, 'MALABARISMO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `espectaculos`
--

CREATE TABLE `espectaculos` (
  `id_espectaculo` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `id_coordinador_fk` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `espectaculos`
--

INSERT INTO `espectaculos` (`id_espectaculo`, `nombre`, `fecha_inicio`, `fecha_fin`, `id_coordinador_fk`) VALUES
(1, 'Primer espectaculo', '2025-02-12', '2025-03-12', 3),
(2, 'Segundo espectaculo', '2025-12-12', '2026-04-12', 1),
(3, 'Prueba pantallas modificada', '2026-02-23', '2026-03-21', 11),
(5, 'modificado', '2026-03-11', '2026-04-09', 11),
(6, 'modificado dos', '2026-02-23', '2026-03-15', 11),
(7, 'especta', '2010-10-10', '2011-10-11', 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `numeros`
--

CREATE TABLE `numeros` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `duracion` int(11) NOT NULL,
  `orden` int(11) NOT NULL,
  `id_espectaculo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `numeros`
--

INSERT INTO `numeros` (`id`, `nombre`, `duracion`, `orden`, `id_espectaculo`) VALUES
(1, 'Numero Uno modificado', 123, 0, 2),
(2, 'prueba interface', 123, 0, 2),
(3, 'numero tres', 15, 0, 3),
(4, 'numero dos', 49, 0, 3),
(5, 'numero uno', 60, 0, 3),
(27, 'modificado tres', 32, 0, 3),
(28, 'modificado uno', 150, 0, 3),
(29, 'modificado dos', 58, 0, 3),
(31, 'num dos', 15, 0, 5),
(32, 'num tres', 150, 0, 5),
(33, 'num modificado', 55, 0, 5),
(34, 'numero uno', 15, 0, 6),
(36, 'numero tres', 45, 0, 6),
(37, 'num dos modif', 68, 0, 6),
(38, 'cuatro', 15, 0, 7),
(39, 'okeeeeeiletsgo', 15, 0, 7),
(41, 'siuuuu', 15, 0, 7);

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
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id_persona`, `email`, `nombre`, `nacionalidad`, `perfil`) VALUES
(1, 'alba@email.com', 'Alba', 'Korea', 'COORDINACION'),
(2, 'chispas@email.com', 'Pedro', 'Suiza', 'ARTISTA'),
(3, 'carlos@email.com', 'Carlos', 'India', 'COORDINACION'),
(4, 'marta@email.com', 'marta', 'Reino Unido', 'ARTISTA'),
(5, 'pepemail@correo.es', 'pepe', 'Holanda', 'ARTISTA'),
(6, 'diegp@doegp.com', 'diego', 'España', 'ARTISTA'),
(7, 'prueba1@email.bro', 'pruebauno', 'España', 'COORDINACION'),
(8, 'noEsAdmin@gmail.com', 'no es admin', 'Holanda', 'COORDINACION'),
(11, 'paco@email.com', 'paco', 'Suecia', 'COORDINACION'),
(12, 'pruebafx@email.com', 'pruebaFX', 'Suecia', 'ARTISTA');

--
-- Índices para tablas volcadas
--

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
  ADD KEY `FK_ae_especialidad` (`id_especialidad`);

--
-- Indices de la tabla `coordinadores`
--
ALTER TABLE `coordinadores`
  ADD PRIMARY KEY (`id_coordinador`),
  ADD KEY `id_persona` (`id_persona`);

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id_credenciales`),
  ADD UNIQUE KEY `id_persona` (`id_persona`);

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
  ADD KEY `FK_numeros_espectaculo` (`id_espectaculo`);

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
  MODIFY `id_coordinador` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id_credenciales` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  MODIFY `id_especialidad` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `espectaculos`
--
ALTER TABLE `espectaculos`
  MODIFY `id_espectaculo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `numeros`
--
ALTER TABLE `numeros`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id_persona` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `artistas`
--
ALTER TABLE `artistas`
  ADD CONSTRAINT `FK_artistas_personas` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`);

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
  ADD CONSTRAINT `FK_ae_artista` FOREIGN KEY (`id_artista`) REFERENCES `artistas` (`id_persona`),
  ADD CONSTRAINT `FK_ae_especialidad` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidades` (`id_especialidad`);

--
-- Filtros para la tabla `coordinadores`
--
ALTER TABLE `coordinadores`
  ADD CONSTRAINT `FK_coordinadores_personas` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`);

--
-- Filtros para la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD CONSTRAINT `FK_credenciales_personas` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`);

--
-- Filtros para la tabla `espectaculos`
--
ALTER TABLE `espectaculos`
  ADD CONSTRAINT `FK_espectaculos_coordinador` FOREIGN KEY (`id_coordinador_fk`) REFERENCES `personas` (`id_persona`),
  ADD CONSTRAINT `FKkce8hdfe6qamx7ll44oug0lc` FOREIGN KEY (`id_coordinador_fk`) REFERENCES `coordinadores` (`id_persona`);

--
-- Filtros para la tabla `numeros`
--
ALTER TABLE `numeros`
  ADD CONSTRAINT `FK_numeros_espectaculo` FOREIGN KEY (`id_espectaculo`) REFERENCES `espectaculos` (`id_espectaculo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
