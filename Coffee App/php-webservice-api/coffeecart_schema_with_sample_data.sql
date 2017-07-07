-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 14, 2014 at 01:22 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `coffeecart`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
  `vip_card_number` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL DEFAULT '0000-00-00',
  `phone_number` bigint(10) unsigned NOT NULL,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `zip` varchar(25) NOT NULL,
  `created_date` datetime NOT NULL,
  UNIQUE KEY `vip_card_number` (`vip_card_number`),
  UNIQUE KEY `phone_number_idx` (`phone_number`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10006 ;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`vip_card_number`, `first_name`, `last_name`, `date_of_birth`, `phone_number`, `address1`, `address2`, `city`, `state`, `zip`, `created_date`) VALUES
(10001, 'Lionel', 'Messi', '1987-06-24', 4045550001, '801 Atlantic Drive', '', 'Atlanta', 'GA', '30332', '2014-07-13 13:52:34'),
(10002, 'Cristiano', 'Ronaldo', '1985-02-05', 4045550002, '801 Atlantic Drive', '', 'Atlanta', 'GA', '30332', '2014-07-13 13:54:42'),
(10003, 'Xavi', 'Hernandez', '1980-01-25', 4045550003, '801 Atlantic Drive', '', 'Atlanta', 'GA', '30332', '2014-07-13 13:56:16'),
(10004, 'Luis', 'Suarez', '1987-01-24', 4045550004, '801 Atlantic Drive', '', 'Atlanta', 'GA', '30332', '2014-07-13 13:59:48'),
(10005, 'Mario', 'Goetze', '1992-06-03', 4045550005, '801 Atlantic Drive', '', 'Atlanta', 'GA', '30332', '2014-07-13 15:22:29');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE IF NOT EXISTS `items` (
  `item_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  `item_type` varchar(255) NOT NULL,
  `item_cost` decimal(15,2) unsigned NOT NULL,
  `bestseller` tinyint(4) NOT NULL DEFAULT '0',
  UNIQUE KEY `item_id` (`item_id`),
  UNIQUE KEY `item_name_idx` (`item_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`item_id`, `item_name`, `item_type`, `item_cost`, `bestseller`) VALUES
(1, 'Coffee', 'beverage', '2.50', 0),
(2, 'Coffee Refill', 'beverage', '1.25', 0),
(3, 'Brownie', 'dessert', '2.50', 1),
(4, 'Chocolate Chip Cookie', 'dessert', '1.50', 1),
(5, 'Vegan Brownie', 'dessert', '3.50', 0),
(6, 'Oatmeal Raisin Cookie', 'dessert', '1.50', 1),
(7, 'Carrot Cake', 'dessert', '2.00', 0),
(8, 'Sugar Donut', 'dessert', '1.00', 1),
(9, 'Chocolate Donut', 'dessert', '1.00', 1),
(10, 'Chocolate Cupcake', 'dessert', '1.75', 0),
(11, 'Red Velvet Cupcake', 'dessert', '1.75', 0),
(12, 'Strawberry Cupcake', 'dessert', '1.75', 0),
(13, 'Donut Holes (6)', 'dessert', '1.50', 1),
(14, 'Cheesecake', 'dessert', '2.00', 0),
(15, 'Mini Cream Puffs (4)', 'dessert', '1.50', 0),
(16, 'Macaroon', 'dessert', '2.50', 0),
(17, 'Cannoli', 'dessert', '3.00', 1),
(18, 'Tiramisu', 'dessert', '2.50', 0),
(19, 'Biscotti', 'dessert', '1.75', 0),
(20, 'Cinnamon Roll', 'dessert', '1.50', 0),
(21, 'Rugelach', 'dessert', '2.25', 0),
(22, 'Pound Cake', 'dessert', '1.50', 0),
(23, 'Coffee Cake', 'dessert', '1.50', 0);

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE IF NOT EXISTS `locations` (
  `location_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `location_neighborhood` varchar(255) NOT NULL,
  `location_name` varchar(255) NOT NULL,
  UNIQUE KEY `location_id` (`location_id`),
  UNIQUE KEY `location_neighborhood_name_idx` (`location_neighborhood`,`location_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`location_id`, `location_neighborhood`, `location_name`) VALUES
(3, 'Berkeley Park', 'West Midtown'),
(1, 'Downtown', 'Centennial Hill'),
(10, 'Downtown', 'Five Points'),
(7, 'English Avenue', 'Northwestern Atlanta'),
(6, 'Mechanicsville', 'Southwestern Atlanta'),
(2, 'Midtown', 'Georgia Tech'),
(9, 'Midtown', 'Midtown'),
(8, 'Sherwood Forest', 'Buckhead'),
(5, 'South Atlanta', 'Southeastern Atlanta'),
(4, 'Sweet Auburn', 'East Side');

-- --------------------------------------------------------

--
-- Table structure for table `preorders`
--

CREATE TABLE IF NOT EXISTS `preorders` (
  `preorder_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `vip_card_number` bigint(20) unsigned NOT NULL,
  `requested_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `created_date` datetime NOT NULL,
  `location_id` bigint(20) unsigned NOT NULL,
  `purchase_id` bigint(20) unsigned NOT NULL,
  UNIQUE KEY `preorder_id_and_vip_number_idx` (`preorder_id`,`vip_card_number`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `preorders`
--

INSERT INTO `preorders` (`preorder_id`, `vip_card_number`, `requested_date`, `created_date`, `location_id`, `purchase_id`) VALUES
(1, 10001, '2014-08-10 00:00:00', '2014-07-13 15:48:52', 2, 0),
(2, 10001, '2014-08-03 00:00:00', '2014-07-13 15:49:14', 2, 0),
(3, 10001, '2014-07-27 00:00:00', '2014-07-13 15:49:33', 2, 0),
(4, 10002, '2014-07-16 00:00:00', '2014-07-13 15:51:16', 2, 0),
(5, 10002, '2014-07-23 00:00:00', '2014-07-13 15:51:24', 2, 0),
(6, 10002, '2014-07-30 00:00:00', '2014-07-13 15:51:30', 2, 0),
(7, 10002, '2014-08-06 00:00:00', '2014-07-13 15:51:54', 2, 0),
(8, 10004, '2014-07-16 00:00:00', '2014-07-13 15:53:00', 2, 0),
(9, 10004, '2014-07-30 00:00:00', '2014-07-13 15:53:17', 2, 0),
(10, 10004, '2014-07-20 00:00:00', '2014-07-13 15:54:18', 2, 0),
(11, 10004, '2014-07-27 00:00:00', '2014-07-13 15:54:25', 2, 0),
(12, 10004, '2014-08-03 00:00:00', '2014-07-13 15:54:39', 2, 0),
(13, 10005, '2014-07-14 00:00:00', '2014-07-13 15:55:49', 2, 0),
(14, 10005, '2014-07-21 00:00:00', '2014-07-13 15:55:55', 2, 0),
(15, 10005, '2014-07-28 00:00:00', '2014-07-13 15:56:01', 2, 0),
(16, 10005, '2014-08-04 00:00:00', '2014-07-13 15:56:16', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `preorder_items`
--

CREATE TABLE IF NOT EXISTS `preorder_items` (
  `preorder_id` bigint(20) unsigned NOT NULL,
  `item_id` bigint(20) unsigned NOT NULL,
  `item_quantity` smallint(5) unsigned NOT NULL,
  UNIQUE KEY `preorder_and_item_id_idx` (`preorder_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `preorder_items`
--

INSERT INTO `preorder_items` (`preorder_id`, `item_id`, `item_quantity`) VALUES
(1, 3, 1),
(2, 3, 1),
(3, 3, 1),
(4, 4, 1),
(5, 4, 1),
(6, 4, 1),
(7, 4, 1),
(8, 16, 1),
(9, 16, 1),
(10, 3, 1),
(11, 3, 1),
(12, 3, 1),
(13, 21, 1),
(14, 21, 1),
(15, 21, 1),
(16, 21, 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE IF NOT EXISTS `purchases` (
  `purchase_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `vip_card_number` bigint(20) unsigned NOT NULL,
  `purchase_amount` decimal(15,2) unsigned NOT NULL,
  `purchase_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `location_id` bigint(20) unsigned NOT NULL,
  UNIQUE KEY `purchase_id_and_vip_number_idx` (`purchase_id`,`vip_card_number`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=27 ;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`purchase_id`, `vip_card_number`, `purchase_amount`, `purchase_date`, `location_id`) VALUES
(1, 10001, '6.25', '2014-04-21 16:00:20', 2),
(2, 10001, '6.25', '2014-04-28 16:00:24', 2),
(3, 10001, '6.25', '2014-05-05 16:00:57', 2),
(4, 10001, '6.25', '2014-05-12 16:00:58', 2),
(5, 10001, '6.25', '2014-05-19 16:00:59', 2),
(6, 10001, '6.25', '2014-05-26 16:01:00', 2),
(7, 10001, '6.25', '2014-06-02 16:01:01', 2),
(8, 10001, '6.25', '2014-06-09 16:01:03', 2),
(9, 10001, '6.25', '2014-06-16 16:01:04', 2),
(10, 10001, '6.25', '2014-06-26 16:01:05', 2),
(11, 10002, '5.75', '2014-06-22 16:07:04', 2),
(12, 10003, '6.25', '2014-07-08 16:09:05', 2),
(13, 10004, '7.25', '2014-06-30 16:10:04', 2),
(14, 10005, '6.00', '2014-06-16 16:12:15', 2),
(15, 10002, '5.75', '2014-06-29 16:12:26', 2),
(16, 10003, '6.25', '2014-07-09 16:12:30', 2),
(17, 10004, '7.25', '2014-07-01 16:12:38', 2),
(18, 10005, '6.00', '2014-06-23 16:12:42', 2),
(19, 10002, '5.75', '2014-07-06 16:12:48', 2),
(20, 10003, '6.25', '2014-07-09 16:12:52', 2),
(21, 10004, '7.25', '2014-07-07 16:12:57', 2),
(22, 10005, '6.00', '2014-06-30 16:13:02', 2),
(23, 10002, '5.75', '2014-07-13 16:13:14', 2),
(24, 10003, '6.25', '2014-07-10 16:13:18', 2),
(25, 10004, '7.25', '2014-07-08 16:13:22', 2),
(26, 10005, '6.00', '2014-07-07 16:13:26', 2);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_items`
--

CREATE TABLE IF NOT EXISTS `purchase_items` (
  `purchase_id` bigint(20) unsigned NOT NULL,
  `item_id` bigint(20) unsigned NOT NULL,
  `item_cost` decimal(15,2) unsigned NOT NULL COMMENT 'We cache the current cost here (so we can always use the cost at the time of the purchase to calculate points accurately)',
  `item_quantity` smallint(5) unsigned NOT NULL,
  UNIQUE KEY `purchase_and_item_id_idx` (`purchase_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchase_items`
--

INSERT INTO `purchase_items` (`purchase_id`, `item_id`, `item_cost`, `item_quantity`) VALUES
(1, 1, '2.50', 1),
(1, 2, '1.25', 1),
(1, 3, '2.50', 1),
(2, 1, '2.50', 1),
(2, 2, '1.25', 1),
(2, 3, '2.50', 1),
(3, 1, '2.50', 1),
(3, 2, '1.25', 1),
(3, 3, '2.50', 1),
(4, 1, '2.50', 1),
(4, 2, '1.25', 1),
(4, 3, '2.50', 1),
(5, 1, '2.50', 1),
(5, 2, '1.25', 1),
(5, 3, '2.50', 1),
(6, 1, '2.50', 1),
(6, 2, '1.25', 1),
(6, 3, '2.50', 1),
(7, 1, '2.50', 1),
(7, 2, '1.25', 1),
(7, 3, '2.50', 1),
(8, 1, '2.50', 1),
(8, 2, '1.25', 1),
(8, 3, '2.50', 1),
(9, 1, '2.50', 1),
(9, 2, '1.25', 1),
(9, 3, '2.50', 1),
(10, 1, '2.50', 1),
(10, 2, '1.25', 1),
(10, 3, '2.50', 1),
(11, 1, '2.50', 1),
(11, 2, '1.25', 1),
(11, 7, '2.00', 1),
(12, 1, '2.50', 1),
(12, 2, '1.25', 1),
(12, 16, '2.50', 1),
(13, 1, '2.50', 1),
(13, 2, '1.25', 1),
(13, 5, '3.50', 1),
(14, 1, '2.50', 1),
(14, 2, '1.25', 1),
(14, 21, '2.25', 1),
(15, 1, '2.50', 1),
(15, 2, '1.25', 1),
(15, 7, '2.00', 1),
(16, 1, '2.50', 1),
(16, 2, '1.25', 1),
(16, 16, '2.50', 1),
(17, 1, '2.50', 1),
(17, 2, '1.25', 1),
(17, 5, '3.50', 1),
(18, 1, '2.50', 1),
(18, 2, '1.25', 1),
(18, 21, '2.25', 1),
(19, 1, '2.50', 1),
(19, 2, '1.25', 1),
(19, 7, '2.00', 1),
(20, 1, '2.50', 1),
(20, 2, '1.25', 1),
(20, 16, '2.50', 1),
(21, 1, '2.50', 1),
(21, 2, '1.25', 1),
(21, 5, '3.50', 1),
(22, 1, '2.50', 1),
(22, 2, '1.25', 1),
(22, 21, '2.25', 1),
(23, 1, '2.50', 1),
(23, 2, '1.25', 1),
(23, 7, '2.00', 1),
(24, 1, '2.50', 1),
(24, 2, '1.25', 1),
(24, 16, '2.50', 1),
(25, 1, '2.50', 1),
(25, 2, '1.25', 1),
(25, 5, '3.50', 1),
(26, 1, '2.50', 1),
(26, 2, '1.25', 1),
(26, 21, '2.25', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
