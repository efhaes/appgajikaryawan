-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Waktu pembuatan: 11 Okt 2026 pada 18.04
-- Versi server: 13.0.2-MariaDB
-- Versi PHP: 8.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Basis data: `dbaplikasigajikaryawan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbgaji`
--

CREATE TABLE `tbgaji` (
  `id` int(11) NOT NULL,
  `ktp` varchar(20) DEFAULT NULL,
  `kodepekerjaan` varchar(20) DEFAULT NULL,
  `gajibersih` decimal(15,2) DEFAULT NULL,
  `gajikotor` decimal(15,2) DEFAULT NULL,
  `tunjangan` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbkaryawan`
--

CREATE TABLE `tbkaryawan` (
  `ktp` varchar(20) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `ruang` int(11) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `tbkaryawan`
--

INSERT INTO `tbkaryawan` (`ktp`, `nama`, `ruang`, `password`) VALUES
('12345', 'Budi Santosoq', 1, '5f4dcc3b5aa765d61d8327deb882cf99');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbpekerjaan`
--

CREATE TABLE `tbpekerjaan` (
  `kodepekerjaan` varchar(20) NOT NULL,
  `namapekerjaan` varchar(100) DEFAULT NULL,
  `jumlahtugas` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `tbpekerjaan`
--

INSERT INTO `tbpekerjaan` (`kodepekerjaan`, `namapekerjaan`, `jumlahtugas`) VALUES
('P001', 'Programmerw', 3);

--
-- Indeks untuk tabel yang dibuang
--

--
-- Indeks untuk tabel `tbgaji`
--
ALTER TABLE `tbgaji`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ktp` (`ktp`),
  ADD KEY `kodepekerjaan` (`kodepekerjaan`);

--
-- Indeks untuk tabel `tbkaryawan`
--
ALTER TABLE `tbkaryawan`
  ADD PRIMARY KEY (`ktp`);

--
-- Indeks untuk tabel `tbpekerjaan`
--
ALTER TABLE `tbpekerjaan`
  ADD PRIMARY KEY (`kodepekerjaan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tbgaji`
--
ALTER TABLE `tbgaji`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbgaji`
--
ALTER TABLE `tbgaji`
  ADD CONSTRAINT `1` FOREIGN KEY (`ktp`) REFERENCES `tbkaryawan` (`ktp`),
  ADD CONSTRAINT `2` FOREIGN KEY (`kodepekerjaan`) REFERENCES `tbpekerjaan` (`kodepekerjaan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
