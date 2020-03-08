-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3308
-- Létrehozás ideje: 2020. Már 06. 12:26
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

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `delivery_man_order`
--

DROP TABLE IF EXISTS `delivery_man_order`;
CREATE TABLE IF NOT EXISTS `delivery_man_order` (
  `order_id` int(11) NOT NULL,
  `delivery_man` int(11) NOT NULL,
  `turn` int(11) NOT NULL,
  KEY `order_id` (`order_id`),
  KEY `delivery_man` (`delivery_man`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE IF NOT EXISTS `ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `order`
--

DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `city` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `house_number` varchar(20) NOT NULL,
  `other` varchar(50) NOT NULL,
  `comment` varchar(100) NOT NULL,
  `pay_method` int(11) NOT NULL,
  `deadline` datetime NOT NULL,
  `state` int(11) NOT NULL,
  `delivered` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `pay_method` (`pay_method`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `order_pizza`
--

DROP TABLE IF EXISTS `order_pizza`;
CREATE TABLE IF NOT EXISTS `order_pizza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `pizza_id` int(11) NOT NULL,
  `done` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `pizza_id` (`pizza_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `pay_method`
--

DROP TABLE IF EXISTS `pay_method`;
CREATE TABLE IF NOT EXISTS `pay_method` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `pizza`
--

DROP TABLE IF EXISTS `pizza`;
CREATE TABLE IF NOT EXISTS `pizza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `picture_path` varchar(255) NOT NULL,
  `discount_percent` int(11) NOT NULL,
  `unavailable` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `pizza`
--

INSERT INTO `pizza` (`id`, `name`, `price`, `picture_path`, `discount_percent`, `unavailable`) VALUES
(1, 'nev', 1000, 'path', 25, b'0');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `pizza_ingredients`
--

DROP TABLE IF EXISTS `pizza_ingredients`;
CREATE TABLE IF NOT EXISTS `pizza_ingredients` (
  `pizza_id` int(11) NOT NULL,
  `ingredient_id` int(11) NOT NULL,
  KEY `pizza_id` (`pizza_id`),
  KEY `ingredient_id` (`ingredient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `scheduled_pizzas`
--

DROP TABLE IF EXISTS `scheduled_pizzas`;
CREATE TABLE IF NOT EXISTS `scheduled_pizzas` (
  `order_pizza` int(11) NOT NULL AUTO_INCREMENT,
  `prep_number` int(11) NOT NULL,
  PRIMARY KEY (`order_pizza`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `scheduling`
--

DROP TABLE IF EXISTS `scheduling`;
CREATE TABLE IF NOT EXISTS `scheduling` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) NOT NULL,
  `is_active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Megkötések a kiírt táblákhoz
--

ALTER TABLE `delivery_man_order`
  ADD CONSTRAINT `fk_delivery_man_order_delivery_man` FOREIGN KEY (`delivery_man`) REFERENCES `user_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_delivery_man_order_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `order`
  ADD CONSTRAINT `fk_order_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_order_pay_method` FOREIGN KEY (`pay_method`) REFERENCES `pay_method` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `order_pizza`
  ADD CONSTRAINT `fk_order_pizza_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_order_pizza_pizza_id` FOREIGN KEY (`pizza_id`) REFERENCES `pizza` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `pizza_ingredients`
  ADD CONSTRAINT `fk_pizza_ingredients_ingredient_id` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_pizza_ingredients_pizza_id` FOREIGN KEY (`pizza_id`) REFERENCES `pizza` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `scheduled_pizzas`
  ADD CONSTRAINT `fk_scheduled_pizzas_order_pizza` FOREIGN KEY (`order_pizza`) REFERENCES `order_pizza` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `user_role`
  ADD CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
