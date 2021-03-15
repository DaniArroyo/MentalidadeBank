-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-03-2021 a las 17:55:14
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.3.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bbddproyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transferencias`
--

CREATE TABLE `transferencias` (
  `idTransaccion` varchar(120) NOT NULL,
  `usuarioEmisor` int(11) NOT NULL,
  `usuarioReceptor` int(11) NOT NULL,
  `cantidad` varchar(45) NOT NULL,
  `fecha` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `transferencias`
--

INSERT INTO `transferencias` (`idTransaccion`, `usuarioEmisor`, `usuarioReceptor`, `cantidad`, `fecha`) VALUES
('/yrpGZi6G2MXl/NkNLZW2gz8iIK16Ps3joCuUKxhVfE=', 13, 12, 'SKtd+2fan00pQ1JeBo0+WQ==', 'SeZVTIFjMEQ9B6CraRxnZ+qV8v7ggJY2fgIdNoitC7U='),
('kaNGWjI6FDlpUqbmJqTK3dKcKm735eWwilECpsCGKkE=', 2, 9, 'fjxRLIQfW6lFt54xy45G4A==', 'lkPYVQhVyJ8V5Sz1WVkMb0oAs3TqDd0Kh603XoMmRUo='),
('lf+Cnvct5APmzflk4tWTrYRMq5jO70/Af/h5enoqmhk=', 1, 9, 'lxI92j4wfv+jwCfpbOdD7A==', 'ZGZETuyZyY4OxDNVA57/6nTE4VxjeOHPCxnLPuj8SGU='),
('qM2isvyqgFk2opr55MzxpMixJy16P45U9fkw1Ydj+kw=', 11, 1, 'wiMdPCVLAp5rZGZFrhD5kw==', 's1/mca04IHsGB3m1r5fP6sCdUeo1WQabvbUpSRRmIH8='),
('rM7aTQZwIEUxs/Wb8WBwTb+os1DxC5gBckVK/hyXBmQ=', 13, 11, 'pYs/7WsnrcHhiC5GQ1OJKQ==', 'keE62lQMEASPJ+mgcA/Jb4+sfU8O76MNLjh6Y827ENk=');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `user` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `idUser` int(11) NOT NULL,
  `balance` int(11) NOT NULL,
  `IP` varchar(15) NOT NULL DEFAULT '0',
  `online` varchar(20) NOT NULL DEFAULT '0',
  `publicKey` text NOT NULL DEFAULT '0',
  `nombre` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`user`, `pass`, `idUser`, `balance`, `IP`, `online`, `publicKey`, `nombre`, `apellidos`) VALUES
('marta', '§c¦o˜IHÊF;ððæÐ', 1, 1001, '0', '0', '0', '', ''),
('griso', 'ÞÁGq¹±sÓ…i£ó5ˆ4', 2, 1000, '0', '0', '0', '', ''),
('dani', 'U·è¸•ÐGS~g\"PÝxU', 9, 1000, '0', '0', '0', '', ''),
('prueba', 'e^xftÙÓç{À^ÑÞ7´¶¼‰÷ˆ‚ŸŸ<gžv‡´È›', 11, 1000, '0', '0', '0', '', ''),
('prueba2', 'e^xftÙÓç{À^ÑÞ7´¶¼‰÷ˆ‚ŸŸ<gžv‡´È›', 12, 1002, '0', '0', '0', '', ''),
('h', 'ª©@&dñ¤@ë¼RÉ™>¶jë6f•ßª(;qæM±#', 13, 997, '0', '0', '0', '', ''),
('klk', 'µÊÃ\\óß\'îxyÎ(×ËAïˆ±žÜ6·ä?×', 14, 1000, '0', '0', '0', '', ''),
('usuario', '¦e¤Y B/�A~HgïÜO¸ J?ÿ ~™Ž†÷÷¢zã', 15, 1000, '', '0', '', 'usususu', 'pellido'),
('pruebaname', '¦e¤Y B/�A~HgïÜO¸ J?ÿ ~™Ž†÷÷¢zã', 16, 1000, '', '0', '', 'pruebita', 'jeje'),
('urem', '¦e¤Y B/�A~HgïÜO¸ J?ÿ ~™Ž†÷÷¢zã', 17, 1000, '', '0', '', 'ejej', 'jojo'),
('supanita', '¦e¤Y B/�A~HgïÜO¸ J?ÿ ~™Ž†÷÷¢zã', 18, 1000, '', '0', '', 'aLVARO', 'yI');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `transferencias`
--
ALTER TABLE `transferencias`
  ADD PRIMARY KEY (`idTransaccion`),
  ADD KEY `usuarioEmisor` (`usuarioEmisor`),
  ADD KEY `usuarioReceptor` (`usuarioReceptor`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `transferencias`
--
ALTER TABLE `transferencias`
  ADD CONSTRAINT `transferencias_ibfk_1` FOREIGN KEY (`usuarioEmisor`) REFERENCES `usuario` (`idUser`) ON DELETE CASCADE,
  ADD CONSTRAINT `transferencias_ibfk_2` FOREIGN KEY (`usuarioReceptor`) REFERENCES `usuario` (`idUser`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
