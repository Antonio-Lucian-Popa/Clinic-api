# 🏥 clinic-api

Microserviciu REST pentru gestionarea clinicilor medicale într-o aplicație SaaS. Permite administrarea cabinetelor medicale, doctorilor, asistenților, pacienților, programărilor, consumabilelor, documentelor medicale și a cererilor de invoiri/concedii. Include un sistem robust de invitații pentru onboarding controlat.

> 🔐 Utilizatorii (OWNER, DOCTOR, ASISTENT) sunt gestionați de un Auth Server separat. `clinic-api` extrage `userId`, `email`, `roles` și `tenant_id` din JWT.

---

## ⚙️ Stack Tehnologic

* Java 17+
* Spring Boot 3.x
* Spring Security (JWT)
* PostgreSQL
* Liquibase (versiune SQL)
* Maven

---

## 📁 Structura principală a proiectului

```
clinic-api/
├── controller/
├── service/
├── model/
│   ├── entity/
│   ├── enums/
│   └── dto/
├── repository/
├── config/
└── ClinicApiApplication.java
```

---

## 🧓 Functionalități principale

### 🏢 Cabinete

* CRUD cabinete (OWNER)
* Fiecare cabinet aparține unui OWNER

### 👨‍⚕️ Doctori / Asistenți

* Adăugare doctori și asistenți prin invitație
* Un doctor aparține unui cabinet
* O asistentă poate fi alocată la mai mulți doctori

### 📅 Programări

* Asociate cu doctor, pacient, opțional asistentă
* CRUD programări + status (SCHEDULED, DONE etc.)

### 🧪 Materiale consumabile

* Gestionare materiale per clinică
* Raportare consum pe doctor/programare

### 🧑‍🤝️‍🧑 Pacienți

* Istoric complet per pacient
* CNP, dată naștere, etc.

### 📌 Documente medicale

* Upload fițiere (PDF, radiografii etc.) per pacient
* Descărcare și listare documente

### 🛄 Invitații

* OWNER sau DOCTOR pot invita utilizatori noi prin email
* Utilizatorul se înregistrează apoi acceptă invitația

### 🧘 Cereri de invoire / concediu

* Doctorii și asistenții pot face cereri
* OWNER vede cererile pending

### 📊 Dashboard

* Date personalizate pentru OWNER, DOCTOR și ASISTENT:

    * programări azi
    * materiale consumate
    * cereri invoire

---

## 🔐 Autentificare & JWT

* JWT emis de Auth Server
* Header: `Authorization: Bearer <token>`
* Claims esențiale extrase: `sub`, `email`, `roles`, `tenant_id`

---

## 🛠 Exemplu configurare `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/clinic_db
    username: postgres
    password: your_password
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  liquibase:
    enabled: true
    change-log: classpath:/db/changelog/db.changelog-master.yaml

clinic:
  documents:
    upload-dir: uploads/documents

jwt:
  secret: your_secure_secret
```

---

## 🚀 Pornire aplicație

1. Creează baza de date PostgreSQL: `clinic_db`
2. Rulează aplicația din IDE sau cu Maven:

   ```bash
   ./mvnw spring-boot:run
   ```
3. Liquibase va aplica automat migrațiile SQL

---

## 📬 Rute API

### 🏢 Cabinete

| Metodă | Ruta            | Descriere                 | Body                       |
| ------ | --------------- | ------------------------- | -------------------------- |
| POST   | `/api/cabinets` | Creează cabinet (OWNER)   | `{ name, address, phone }` |
| GET    | `/api/cabinets` | Listează cabinete proprii | —                          |

### 🤝 Invitații

| Metodă | Ruta                           | Descriere                 | Body / Param                             |
| ------ | ------------------------------ | ------------------------- | ---------------------------------------- |
| POST   | `/api/invitations`             | Trimite invitație         | `{ email, role, cabinetId?, doctorId? }` |
| GET    | `/api/invitations/mine`        | Invitatii primite (email) | —                                        |
| GET    | `/api/invitations/sent`        | Invitatii trimise de user | —                                        |
| POST   | `/api/invitations/{id}/accept` | Acceptă invitația         | —                                        |

### 🧓 Pacienți

| Metodă | Ruta            | Descriere         | Body                                                              |
| ------ | --------------- | ----------------- | ----------------------------------------------------------------- |
| POST   | `/api/patients` | Adaugă pacient    | `{ firstName, lastName, email, phone, dateOfBirth, gender, cnp }` |
| GET    | `/api/patients` | Listează pacienți | —                                                                 |

### 📅 Programări

| Metodă | Ruta                | Descriere                            | Body                                                               |
| ------ | ------------------- | ------------------------------------ | ------------------------------------------------------------------ |
| POST   | `/api/appointments` | Creează programare                   | `{ doctorId, assistantId?, patientId, startTime, endTime, notes }` |
| GET    | `/api/appointments` | Programări (filtrare doctor/pacient) | —                                                                  |

### 🧪 Materiale & consum

| Metodă | Ruta                   | Descriere          | Body                                                        |
| ------ | ---------------------- | ------------------ | ----------------------------------------------------------- |
| POST   | `/api/materials`       | Creează material   | `{ name, unit }`                                            |
| GET    | `/api/materials`       | Listează materiale | —                                                           |
| POST   | `/api/material-usages` | Raportează consum  | `{ doctorId, materialId, quantity, appointmentId?, notes }` |

### 📌 Documente medicale

| Metodă | Ruta                                        | Descriere                  | Body                                  |
| ------ | ------------------------------------------- | -------------------------- | ------------------------------------- |
| POST   | `/documents/patients/{patientId}/documents` | Upload document PDF        | `MultipartFile file`, `type`, `notes` |
| GET    | `/documents/patients/{patientId}/documents` | Listează documente pacient | —                                     |
| GET    | `/documents/{id}/download`                  | Descarcă fișier            | —                                     |
| DELETE | `/documents/{id}`                           | Șterge document            | —                                     |

### 🤞 Invoiri / concedii

| Metodă | Ruta                     | Descriere              | Body                             |
| ------ | ------------------------ | ---------------------- | -------------------------------- |
| POST   | `/api/time-off-requests` | Creează cerere invoire | `{ startDate, endDate, reason }` |
| GET    | `/api/time-off-requests` | Listează cereri user   | —                                |

### 📊 Dashboard

| Metodă | Ruta             | Descriere                      |
| ------ | ---------------- | ------------------------------ |
| GET    | `/api/dashboard` | Date sumar pentru rolul curent |

