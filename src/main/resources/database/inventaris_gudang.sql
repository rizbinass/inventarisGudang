CREATE DATABASE IF NOT EXISTS inventaris_gudang;

USE inventaris_gudang;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(100) NOT NULL
);

SET @has_nama := (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = 'inventaris_gudang'
      AND TABLE_NAME = 'users'
      AND COLUMN_NAME = 'nama'
);
SET @sql := IF(
    @has_nama = 0,
    'ALTER TABLE users ADD COLUMN nama VARCHAR(100) NOT NULL DEFAULT ''Administrator'' AFTER password',
    'DO 0'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

CREATE TABLE IF NOT EXISTS kategori (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS barang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode_barang VARCHAR(50) NOT NULL UNIQUE,
    nama_barang VARCHAR(150) NOT NULL,
    kategori_id INT NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    satuan VARCHAR(50) NOT NULL,
    CONSTRAINT fk_barang_kategori
        FOREIGN KEY (kategori_id)
        REFERENCES kategori(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS transaksi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    barang_id INT NOT NULL,
    user_id INT NOT NULL,
    jenis_transaksi ENUM('Masuk', 'Keluar') NOT NULL,
    jumlah INT NOT NULL,
    tanggal_transaksi DATETIME NOT NULL,
    CONSTRAINT fk_transaksi_barang
        FOREIGN KEY (barang_id)
        REFERENCES barang(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_transaksi_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

INSERT INTO users (username, password, nama)
VALUES ('admin', '$2a$10$T4xu9TkAZ5RJXendAEgWS.nLhU4FH63zSKdVPvwWZKH6pTBvTEQRu', 'Administrator')
ON DUPLICATE KEY UPDATE password = VALUES(password), nama = VALUES(nama);

INSERT INTO kategori (nama_kategori)
VALUES
    ('Elektronik'),
    ('Aksesoris'),
    ('Perangkat Kantor'),
    ('ATK'),
    ('Perlengkapan Gudang')
ON DUPLICATE KEY UPDATE nama_kategori = VALUES(nama_kategori);
