-- ============================================================
--  Schema  : scm_db
--  Author  : Abu Dujanah Siregar
--  Purpose : Supply Chain Management - PO Processing System
--  Target  : PT EDI Indonesia (Member of PELINDO)
-- ============================================================

CREATE DATABASE IF NOT EXISTS scm_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE scm_db;

-- ------------------------------------------------------------
--  Table : tm_header
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tm_header (
    po_number   VARCHAR(20)   NOT NULL,
    po_date     DATE          NOT NULL,
    buyer_name  VARCHAR(100)  NOT NULL,
    buyer_addr  VARCHAR(255)  NOT NULL,
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_tm_header PRIMARY KEY (po_number)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = 'Master tabel header Purchase Order';

-- ------------------------------------------------------------
--  Table : tm_detail
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tm_detail (
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    po_number   VARCHAR(20)   NOT NULL,
    part_no     VARCHAR(50)   NOT NULL,
    part_name   VARCHAR(100)  NOT NULL,
    qty         INT UNSIGNED  NOT NULL DEFAULT 0,
    unit        VARCHAR(20)   NOT NULL,
    price       BIGINT        NOT NULL DEFAULT 0,
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_tm_detail        PRIMARY KEY (id),
    CONSTRAINT fk_detail_to_header FOREIGN KEY (po_number)
        REFERENCES tm_header (po_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = 'Tabel detail item per Purchase Order';

CREATE INDEX idx_detail_po_number ON tm_detail (po_number);
