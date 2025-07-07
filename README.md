# Simple Money Transfer Platform

Bu layihə mikroservis arxitekturası ilə yazılmış, elektron cüzdan və pul köçürmə funksionallığı təqdim edən backend sistemidir.

---

## Servislər və Texnologiyalar

- **user-ms** – İstifadəçi qeydiyyatı və identifikasiyası
- **card-ms** – İstifadəçinin balans və top-up əməliyyatları
- **transfer-ms** – Pul köçürmə və transaction log sistemi
- **RabbitMQ** – Asinxron mesajlaşma
- **PostgreSQL** – Hər servis üçün ayrıca database və schema
- **Redis** – Keş və token yaddaşı
- **Elasticsearch** – Transaction log axtarış imkanı(impl edilməyib)

---

## Docker Servislərinin Başladılması

Əvvəlcə aşağıdakı komandaları işlədərək Docker servis mühitini qurun:

```bash
docker-compose -f docker/docker-compose.db.yml \
               -f docker/docker-compose.redis.yml \
               -f docker/docker-compose.rabbit.yml up -d
```

-- userms database və schema
CREATE DATABASE userms;
\c userms
CREATE SCHEMA IF NOT EXISTS userms;

-- cardms database və schema
\connect postgres
CREATE DATABASE cardms;
\c cardms
CREATE SCHEMA IF NOT EXISTS cardms;

-- transferms database və schema
\connect postgres
CREATE DATABASE transferms;
\c transferms
CREATE SCHEMA IF NOT EXISTS transferms;



-- userms database-ə keçin
\c userms

-- roles cədvəlini yaradın (əgər yoxdursa)
CREATE TABLE IF NOT EXISTS userms.roles (
id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
name VARCHAR(50) UNIQUE NOT NULL
);

-- default rolları əlavə edin
INSERT INTO userms.roles (name) VALUES ('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO userms.roles (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;