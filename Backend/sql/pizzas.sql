-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3308
-- Létrehozás ideje: 2020. Máj 11. 21:13
-- Kiszolgáló verziója: 8.0.18
-- PHP verzió: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `pizzeria`
--

--
-- A tábla adatainak kiíratása `ingredient`
--

INSERT INTO `ingredient` (`id`, `name`) VALUES
(3, 'gomba'),
(4, 'sajt'),
(5, 'paradicsom'),
(6, 'paradicsomos alap'),
(7, 'kukorica'),
(8, 'paprika'),
(9, 'szalámi'),
(10, 'lilahagyma'),
(11, 'tejfölös alap'),
(12, 'mustár'),
(13, 'kolbász'),
(14, 'tejföl'),
(15, 'csirkemell'),
(16, 'brokkoli'),
(17, 'borsó'),
(18, 'sonka'),
(19, 'hagyma'),
(20, 'fokhagymás alap');

--
-- A tábla adatainak kiíratása `pizza`
--

INSERT INTO `pizza` (`id`, `name`, `price`, `picture_path`, `discount_percent`, `unavailable`) VALUES
(1, 'Gombás', 1100, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\gombas.png', 0, b'0'),
(2, 'Szalámis', 1000, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\szalamis.png', 0, b'0'),
(3, 'Vegán 1', 1100, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\vegan.png', 0, b'0'),
(4, 'Kolbászos', 1100, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\kolbaszos.png', 0, b'0'),
(5, 'Csirkés', 1250, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\csirkes.png', 0, b'0'),
(6, 'Kukoricás', 1250, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\kukoricas.png', 0, b'0'),
(7, 'Vegi', 1150, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\vegi.png', 0, b'0'),
(8, 'Las Vegas', 1350, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\lasvegas.png', 0, b'0'),
(9, 'Hollywood', 1300, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\hollywood.png', 0, b'0'),
(10, 'Argentin Grill', 1300, 'G:\\EKE\\ProjektLabor\\AFP2\\OnlinePizzeria_Labor2\\Backend\\images\\pizza\\argentingrill.png', 15, b'0');

--
-- A tábla adatainak kiíratása `pizza_ingredients`
--

INSERT INTO `pizza_ingredients` (`pizza_id`, `ingredient_id`) VALUES
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 4),
(2, 6),
(2, 9),
(3, 3),
(3, 4),
(3, 5),
(3, 8),
(3, 10),
(3, 11),
(4, 4),
(4, 6),
(4, 10),
(4, 12),
(4, 13),
(5, 4),
(5, 6),
(5, 8),
(5, 14),
(5, 15),
(6, 4),
(6, 6),
(6, 7),
(7, 3),
(7, 4),
(7, 6),
(7, 7),
(7, 16),
(7, 17),
(8, 4),
(8, 6),
(8, 15),
(8, 18),
(8, 19),
(9, 4),
(9, 7),
(9, 11),
(9, 15),
(10, 4),
(10, 10),
(10, 15),
(10, 20);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
