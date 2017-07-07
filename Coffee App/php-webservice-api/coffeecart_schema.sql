-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 13, 2014 at 10:39 PM
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10001 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
