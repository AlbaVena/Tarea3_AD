-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-04-2026 a las 16:04:33
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
(6, 'Chispas', NULL),
(7, 'La Maga', NULL),
(8, NULL, NULL),
(9, 'Anitta', NULL),
(10, NULL, NULL);

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
(6, 1),
(8, 1),
(7, 2),
(9, 3),
(10, 3),
(10, 4),
(7, 4),
(7, 5),
(6, 6),
(8, 6),
(9, 7),
(9, 8),
(6, 9),
(10, 9),
(8, 10),
(7, 10);

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
(6, 1),
(6, 3),
(7, 2),
(7, 4),
(7, 5),
(8, 1),
(8, 2),
(9, 3),
(9, 4),
(10, 5);

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
(1, 1, '2023-01-15', 1),
(2, 1, '2022-06-20', 2),
(3, 0, NULL, 3),
(4, 0, NULL, 4),
(5, 0, NULL, 5);

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
(1, 1, 'alba', 'pass', 'COORDINACION'),
(2, 2, 'carlos', 'pass', 'COORDINACION'),
(3, 3, 'lucia', 'pass', 'COORDINACION'),
(4, 4, 'marcos', 'pass', 'COORDINACION'),
(5, 5, 'elena', 'pass', 'COORDINACION'),
(6, 6, 'pedro', 'pass', 'ARTISTA'),
(7, 7, 'marta', 'pass', 'ARTISTA'),
(8, 8, 'diego', 'pass', 'ARTISTA'),
(9, 9, 'ana', 'pass', 'ARTISTA'),
(10, 10, 'luis', 'pass', 'ARTISTA');

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
(1, 'Gran Circo de Invierno', '2024-01-10', '2024-06-10', 1),
(2, 'Magia y Acrobacia 2026', '2026-01-01', '2026-09-30', 2),
(3, 'El Gran Show del Futuro', '2026-10-01', '2027-03-01', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `numeros`
--

CREATE TABLE `numeros` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `duracion` double NOT NULL,
  `orden` int(11) NOT NULL,
  `id_espectaculo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `numeros`
--

INSERT INTO `numeros` (`id`, `nombre`, `duracion`, `orden`, `id_espectaculo`) VALUES
(1, 'Vuelo Acrobatico', 10, 1, 1),
(2, 'Magia Sorprendente', 7, 2, 1),
(3, 'Equilibrio Extremo', 12, 3, 1),
(4, 'Humor y Malabares', 5, 1, 2),
(5, 'La Gran Ilusion', 15, 2, 2),
(6, 'Acrobacia Aerea', 10, 3, 2),
(7, 'Equilibrismo Total', 7, 4, 2),
(8, 'El Mago Supremo', 12, 1, 3),
(9, 'Vuelo de Cometas', 5, 2, 3),
(10, 'Finales de Fuego', 15, 3, 3);

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
(1, 'alba@circo.com', 'Alba Garcia', 'España', 'COORDINACION'),
(2, 'carlos@circo.com', 'Carlos Lopez', 'Mexico', 'COORDINACION'),
(3, 'lucia@circo.com', 'Lucia Perez', 'Argentina', 'COORDINACION'),
(4, 'marcos@circo.com', 'Marcos Ruiz', 'Chile', 'COORDINACION'),
(5, 'elena@circo.com', 'Elena Torres', 'Colombia', 'COORDINACION'),
(6, 'pedro@circo.com', 'Pedro Sanchez', 'España', 'ARTISTA'),
(7, 'marta@circo.com', 'Marta Gomez', 'Francia', 'ARTISTA'),
(8, 'diego@circo.com', 'Diego Martinez', 'Italia', 'ARTISTA'),
(9, 'ana@circo.com', 'Ana Fernandez', 'Portugal', 'ARTISTA'),
(10, 'luis@circo.com', 'Luis Rodriguez', 'Brasil', 'ARTISTA');

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
  MODIFY `id_coordinador` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id_credenciales` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  MODIFY `id_especialidad` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `espectaculos`
--
ALTER TABLE `espectaculos`
  MODIFY `id_espectaculo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `numeros`
--
ALTER TABLE `numeros`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id_persona` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
