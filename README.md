# Sistem Integrasi Dokumen Purchase Order (SCM)

Layanan backend berbasis Java Spring Boot untuk otomatisasi pemrosesan dokumen transaksi rantai pasok (*Supply Chain Management*). Sistem ini mengintegrasikan berkas format lembar kerja Excel (.xlsx) dari direktori `INBOX`, memvalidasi dan memetakan data ke dalam basis data relasional MySQL, serta mentransformasikan data tersebut ke dalam format standar pertukaran data elektronik (*Electronic Data Interchange* / XML) pada direktori `OUTBOX`.

---

## 1. Komponen dan Spesifikasi Teknis

| Komponen | Spesifikasi / Library | Keterangan |
| :--- | :--- | :--- |
| **Runtime Environment** | Java SE Development Kit 21 (LTS) | Baseline runtime Java 21 |
| **Framework Utama** | Spring Boot 4.1.x | Core framework (WebMVC, Data JPA) |
| **Basis Data** | MySQL Server / MariaDB | Storage engine InnoDB, `utf8mb4_unicode_ci` |
| **Excel Parser** | Apache POI 5.3.0 (`poi-ooxml`) | Ekstraksi format spreadsheet OpenXML (.xlsx) |
| **XML Serializer** | Jackson Dataformat XML | Transformasi objek data ke format dokumen XML |
| **Dokumentasi API** | SpringDoc OpenAPI 2.6.0 | Antarmuka pengujian interaktif Swagger UI |
| **Build Tool** | Apache Maven 3.9.x | Manajemen dependensi dan kompilasi |

---

## 2. Struktur Direktori Sistem

Struktur direktori kerja yang digunakan untuk proses input, output, dan kode sumber:

```
E:\SCM\
├── INBOX\
│   └── PO_SCM.xlsx            [Input: File Excel 2 sheet (HEADER & DETAIL)]
├── OUTBOX\
│   └── PO_OUTPUT.xml          [Output: Dokumen XML hasil transformasi]
└── po-processor\
    └── po-processor\          [Aplikasi: Direktori root Maven project]
        ├── pom.xml
        └── src\
            ├── main\java\
            └── main\resources\application.properties
```

---

## 3. Skema Basis Data

Sistem menggunakan skema basis data `scm_db` dengan dua tabel yang saling berelasi:

### Tabel `tm_header` (Induk Transaksi PO)
* `po_number` : VARCHAR(20) - **Primary Key**
* `po_date` : DATE - Tanggal penerbitan PO
* `buyer_name` : VARCHAR(100) - Nama pihak pembeli
* `buyer_addr` : VARCHAR(255) - Alamat pihak pembeli
* `created_at` : TIMESTAMP - Jejak waktu pencatatan data

### Tabel `tm_detail` (Rincian Item Barang)
* `id` : BIGINT - **Primary Key (Auto Increment)**
* `po_number` : VARCHAR(20) - **Foreign Key** mereferensi ke `tm_header(po_number)`
* `part_no` : VARCHAR(50) - Kode part barang
* `part_name` : VARCHAR(100) - Deskripsi nama barang
* `qty` : INT - Kuantitas barang
* `unit` : VARCHAR(20) - Satuan kuantitas (UNIT, PCE, dsb)
* `price` : BIGINT - Harga satuan barang
* `created_at` : TIMESTAMP - Jejak waktu pencatatan data

*Integritas Relasional:* `ON UPDATE CASCADE`, `ON DELETE RESTRICT`.

---

## 4. Alur Pemrosesan Data

1. **Tahap Ekstraksi (Ingestion):**
   * Aplikasi mengakses berkas `PO_SCM.xlsx` pada direktori `E:\SCM\INBOX\`.
   * Sheet `HEADER` diparsing menjadi entitas `PoHeader`.
   * Sheet `DETAIL` diparsing menjadi entitas `PoDetail` dan diasosiasikan dengan `PoHeader` terkait berdasarkan `po_number`.
2. **Tahap Persistensi (Database Persistence):**
   * Transaksi database membersihkan data transaksi lama (`deleteAllInBatch`) dan menyimpan entitas baru secara atomik.
3. **Tahap Serialisasi (XML Export):**
   * Data diambil secara relasional melalui kueri JPA (`LEFT JOIN FETCH`).
   * Struktur data dipetakan ke Jackson XML DTO dengan hirarki `<HEADERS> -> <HEADER> -> <DETAILS> -> <DETAIL>`.
   * File disimpan pada `E:\SCM\OUTBOX\PO_OUTPUT.xml` dengan deklarasi UTF-8.

---

## 5. Panduan Menjalankan Aplikasi

### Prasyarat Sistem
* Service MySQL telah aktif pada port default `3306`.
* Skema `scm_db` dan tabel telah dibuat (atau otomatis dibuat oleh driver koneksi).
* File `PO_SCM.xlsx` berada di folder `E:\SCM\INBOX\`.

### Menjalankan Melalui Terminal
Navigasikan ke direktori project dan jalankan Maven command:

```bash
cd E:\SCM\po-processor\po-processor
mvn clean spring-boot:run
```

### Mekanisme Eksekusi Otomatis (CommandLineRunner)
Saat aplikasi selesai menginisialisasi Spring context, komponen `ScmStartupRunner` secara otomatis mengeksekusi siklus pemrosesan data (Excel -> Database -> XML) dan mencatat status eksekusi langsung ke konsol log.

---

## 6. Antarmuka REST API & Swagger UI

Selain eksekusi otomatis pada saat startup, aplikasi menyediakan REST endpoint untuk pengujian berkala secara manual:

| HTTP Method | Endpoint | Deskripsi Fungsi |
| :--- | :--- | :--- |
| `POST` | `/scm/api/scm/import` | Mengeksekusi impor data Excel ke MySQL |
| `POST` | `/scm/api/scm/export` | Mengeksekusi ekspor data MySQL ke file XML |
| `POST` | `/scm/api/scm/process` | Mengeksekusi siklus lengkap (impor lalu ekspor) |

* **Dokumentasi Swagger UI:** `http://localhost:8085/scm/swagger-ui/index.html`
* **OpenAPI JSON Spec:** `http://localhost:8085/scm/api-docs`

---

## 7. Format Dokumen XML Luaran

Dokumen XML yang dihasilkan memenuhi spesifikasi berikut:

```xml
<?xml version="1.0" encoding="utf-8"?>
<HEADERS>
  <HEADER>
    <PO_NUMBER>A001</PO_NUMBER>
    <PO_DATE>2025-04-09</PO_DATE>
    <BUYER_NAME>BERKAH BERKAWAN</BUYER_NAME>
    <BUYER_ADDR>SEMARANG</BUYER_ADDR>
    <DETAILS>
      <DETAIL>
        <PART_NO>CT-98-0001</PART_NO>
        <PART_NAME>PLASTIC BOX</PART_NAME>
        <QTY>12</QTY>
        <UNIT>UNIT</UNIT>
        <PRICE>200000</PRICE>
      </DETAIL>
      <DETAIL>
        <PART_NO>KL-92-0219</PART_NO>
        <PART_NAME>TABLE</PART_NAME>
        <QTY>25</QTY>
        <UNIT>UNIT</UNIT>
        <PRICE>300000</PRICE>
      </DETAIL>
    </DETAILS>
  </HEADER>
  <HEADER>
    <PO_NUMBER>A002</PO_NUMBER>
    <PO_DATE>2025-04-10</PO_DATE>
    <BUYER_NAME>KAWAN LAMA</BUYER_NAME>
    <BUYER_ADDR>BANDUNG</BUYER_ADDR>
    <DETAILS>
      <DETAIL>
        <PART_NO>AS-01-0112</PART_NO>
        <PART_NAME>STANLESS STEEL</PART_NAME>
        <QTY>10</QTY>
        <UNIT>PCE</UNIT>
        <PRICE>120000</PRICE>
      </DETAIL>
    </DETAILS>
  </HEADER>
</HEADERS>
```

---

**Penyusun:** Abu Dujanah Siregar  
**Program:** Seleksi Teknis Posisi Java Developer - PT EDI Indonesia (Member of PELINDO)
