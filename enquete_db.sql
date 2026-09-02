-- --------------------------------------------------------
-- ホスト:                          127.0.0.1
-- サーバーのバージョン:                   8.0.35 - MySQL Community Server - GPL
-- サーバー OS:                      Win64
-- HeidiSQL バージョン:               12.14.0.7165
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- enquete_db のデータベース構造をダンプしています
CREATE DATABASE IF NOT EXISTS `enquete_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `enquete_db`;

--  テーブル enquete_db.answer_table の構造をダンプしています
CREATE TABLE IF NOT EXISTS `answer_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_id` int NOT NULL,
  `answer_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `question_id` FOREIGN KEY (`question_id`) REFERENCES `question_table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- テーブル enquete_db.answer_table: ~0 rows (約) のデータをダンプしています

--  テーブル enquete_db.enquete_table の構造をダンプしています
CREATE TABLE IF NOT EXISTS `enquete_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `enquete_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- テーブル enquete_db.enquete_table: ~0 rows (約) のデータをダンプしています

--  テーブル enquete_db.question_table の構造をダンプしています
CREATE TABLE IF NOT EXISTS `question_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `enquete_id` int NOT NULL,
  `question_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `enquete_id` (`enquete_id`),
  CONSTRAINT `enquete_id` FOREIGN KEY (`enquete_id`) REFERENCES `enquete_table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- テーブル enquete_db.question_table: ~0 rows (約) のデータをダンプしています

--  テーブル enquete_db.result_table の構造をダンプしています
CREATE TABLE IF NOT EXISTS `result_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `enquete_id` int NOT NULL,
  `question_id` int NOT NULL,
  `answer_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- テーブル enquete_db.result_table: ~0 rows (約) のデータをダンプしています

--  テーブル enquete_db.users_table の構造をダンプしています
CREATE TABLE IF NOT EXISTS `users_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `authority` enum('ADMIN','USER') NOT NULL DEFAULT 'ADMIN',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- テーブル enquete_db.users_table: ~1 rows (約) のデータをダンプしています
INSERT INTO `users_table` (`id`, `user_name`, `password`, `authority`) VALUES
	(3, 'admin', '$2a$10$yzGZRCmI5l4DjPqXHjcBPeYWdjunsSP7arLWoztl.3hijMlMaWxcK', 'ADMIN');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
