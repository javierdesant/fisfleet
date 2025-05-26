# 🚢 Battleship UPM - Hundir la Flota 2025

Bienvenido al desarrollo del proyecto **Battleship UPM**, una versión web moderna y accesible del clásico juego de mesa **Hundir la Flota** con nuevas mecánicas especiales y adaptada a los valores y necesidades de la **Universidad Politécnica de Madrid**.

Este proyecto forma parte de la asignatura **Fundamentos de Ingeniería del Software (FIS)** y está diseñado para que los estudiantes apliquen conceptos fundamentales de la ingeniería del software mientras trabajan en equipo para construir un software robusto, accesible y preparado para el mantenimiento futuro.

---

## 🎯 Objetivos del Proyecto

El desarrollo de Battleship permite que los estudiantes practiquen y apliquen los siguientes conceptos clave de la ingeniería del software:

- 📜 **Especificación de Requisitos**: Recopilación y análisis detallados para definir las funcionalidades necesarias.
- 🧩 **Modelado de Análisis y Diseño**: Uso de herramientas UML y técnicas de diseño para trazar el sistema.
- 🚀 **Desarrollo y Gestión de Cambios**: Implementación dinámica administrando mejoras y revisiones constantes.
- ✔️ **Verificación y Validación**: Garantizando la calidad y adaptabilidad en todas las etapas del desarrollo.
- 🤝 **Trabajo en Equipo y Liderazgo**: Coordinación grupal con roles bien definidos y enfoque en la colaboración.

---

## 🛠️ Características del Juego

**Battleship UPM** trae una versión renovada y desafiante:

1. **Tablero estándar 10x10**, denotado con filas y columnas numeradas de 0 a 9.
2. **Flotas únicas con habilidades especiales**:
    - **Portaviones (4 casillas)**: Contrataca automáticamente con cada impacto recibido.
    - **Acorazado (3 casillas)**: Puede ejecutar ataques de artillería que afecten su posición y adyacentes.
    - **Submarino (3 casillas)**: Repara y resucita una vez durante la partida.
    - **Patrullero (2 casillas)**: Revela toda una fila como habilidad especial.
3. **Puntuación de Partida**:
    - 5 puntos por cada posición de barco enemigo hundida.
    - 2 puntos por cada posición impactada y no hundida.
    - Penalización de -1 por impacto en agua.
    - Bonus/malus de 20 puntos al ganador o perdedor.
4. **Soporte para Usuarios**:
    - Registro y almacenamiento de jugadores humanos (integración con el **LDAP de la UPM** para validación).
    - Contraseñas y datos almacenados bajo cifrado y con cumplimiento de la **LOPD-GDD**.
    - Soporte para jugar contra la máquina con niveles de dificultad (*Fácil, Normal y Difícil*).
    - Rastreabilidad de estadísticas: Ranking de las 10 mejores puntuaciones de partidas.

---

## 🌐 Accesibilidad y Diseño

El software está diseñado para ser **inclusivo, accesible y multiplataforma**, cumpliendo con estándares modernos como:

- **HTML5**, compatible con navegadores modernos.
- Diseño adaptable y ligero para su uso en **ordenadores, tablets, móviles y televisores**.
- **Modos de visualización personalizados** para personas con dificultades visuales (soporte para daltonismo, ceguera parcial o total).
- **Colores corporativos de la UPM**, integrando un diseño unificado y elegante.

---

## 📂 Estructura del Proyecto

```plaintext
fisfleet/
├── docs/                        # Documentación del proyecto (UML, requisitos, etc.)
│   └── uml/
├── frontend/                    # Código fuente del frontend (React)
│   ├── design/                  # Recursos de diseño (colores, estilos, etc.)
│   ├── app/
│   │   ├── assets/images/       # Imágenes usadas en la UI
│   │   ├── routes/              # Rutas de React Router
│   │   ├── _landing/            # Página principal
│   │   ├── _landing._index/
│   │   ├── _auth.login/
│   │   │   └── icons/
│   │   ├── _auth.register/
│   │   ├── _auth.profile/
│   │   ├── admin/
│   │   ├── match_.$id/
│   │   └── match_.$id._index/
│   │       └── icons/
│   ├── public/                 # Archivos estáticos (favicon, imágenes públicas)
│   ├── package.json            # Dependencias del frontend
│   ├── package-lock.json       # Lockfile de npm
│   ├── tsconfig.json           # Configuración TypeScript
│   └── vite.config.ts          # Configuración Vite
├── libs/                       # Librerías externas en formato JAR
│   └── etsisi/
│       ├── Battleship/1.11/
│       └── externals/5.1/
├── src/                        # Código fuente del backend (Spring Boot)
│   ├── main/
│   │   ├── java/es/upm/etsisi/fis/fisfleet/
│   │   │   ├── api/            # DTOs, mappers, validaciones
│   │   │   ├── domain/         # Entidades y repositorios
│   │   │   ├── infrastructure/ # Adaptadores, configuración, servicios
│   │   │   └── utils/          # Utilidades generales
│   │   └── resources/db/       # Scripts de base de datos
│   └── test/                   # Tests automatizados del backend
├── target/                     # Directorio de build de Maven (ignorado en git)
├── TODO/                       # Gestión de tareas y notas internas
├── .env.example                # Ejemplo de variables de entorno
├── .gitignore                  # Exclusiones de archivos en Git
├── Dockerfile                  # Imagen Docker del proyecto
├── docker-compose.yml          # Orquestación de servicios
├── mvnw, mvnw.cmd              # Wrappers de Maven
├── pom.xml                     # Configuración Maven del backend
├── LICENSE                     # Licencia del proyecto
└── README.md                   # Este archivo
```

> **Nota:** El frontend y backend comparten el mismo repositorio, pero cada uno tiene su propia configuración y
> dependencias.

---
## Diagramas de diseño del sistema
### 😎Diagrama de componentes:
Generado utilizando los componentes arquitectónicos y sus relaciones a través de las interfaces.

![Diagrama de componentes.jpg](docs%2FDiagrama%20de%20componentes.jpg)
### 😎Diagrama de despliegue:
Representa la implantación del sistema.

![Diagrama de despliegue.jpg](docs%2FDiagrama%20de%20despliegue.jpg)

## ⚙️ Configuración y Ejecución

### 1️⃣ Clona el repositorio

```bash
git clone https://gitlab.etsisi.upm.es/bu0211/battleship_citim21_02.git
cd fisfleet
```

### 2️⃣ Configura variables de entorno

Copia el archivo de ejemplo y edítalo según tu entorno:

```bash
cp .env .env
```

*Edita .env con tus credenciales y configuración preferida*

> **Nota:** Únicamente el backend requiere de un fichero `.env` en este proyecto.

---

### 3️⃣ Instalación de dependencias

#### Backend (Java/Spring)

```bash
mvn clean install
```

O bien

```bash
mvn clean
mvn compile
```

#### Frontend (React)

```bash
cd frontend
npm install
cd ..
```

---

### 4️⃣ Desarrollo y ejecución local

#### Backend

```bash
mvn spring-boot:run
```

#### Frontend

```bash
cd frontend
npm run dev
cd ..
```

Esto arrancará el servidor de desarrollo de React Router con recarga en caliente y generación de tipos para rutas.

---

### 5️⃣ Pruebas

#### Backend

```bash
mvn test
```

#### Frontend

```bash
cd frontend
npm run typecheck
```

---

### 6️⃣ Ejecución con Docker Compose

La aplicación levantará un contenedor automáticamente al iniciarse.
En caso de error en la base de datos recuerda que siempre puedes hacer:

```bash
docker-compose down -v
```

---

### 7️⃣ Acceso a la aplicación

- **Frontend:** [http://localhost:5173](http://localhost:5173) (modo desarrollo)
- **Backend:** [http://localhost:8080](http://localhost:8080)
- **Producción (Docker):** Consulta los puertos expuestos en `docker-compose.yml`.

---

## 🕹️ Cómo Jugar

1. Accede a la aplicación en [http://localhost:5173](http://localhost:5173).
2. **Regístrate** con un correo válido de la **UPM** (alumnos) utilizando las credenciales LDAP.
3. Configura tu partida:
    - Juega contra una **máquina** con diferentes niveles de dificultad.
    - Guarda las estadísticas de cada partida, consulta el ranking y mejora tu habilidad.
4. ¡Disfruta de las nuevas mecánicas como las habilidades especiales de las flotas! 🚢

---

## 📦 Tecnologías Principales

- **Backend**:
  - Java 23+ con Spring (Boot, MVC, Data JPA).
  - Manejo seguro con autenticación LDAP y cifrado.
- **Infraestructura**:
  - Contenerización con Docker y orquestación con Docker Compose.
  - Integración con bases de datos relacionales como PostgreSQL/MySQL.
  - Uso de Caffeine con cachés de alto rendimiento y baja latencia.
- **Frontend**:
    - React, React Router, TailwindCSS, HTML5, CSS3 y diseño responsivo compatible con dispositivos modernos.
- **Testeo y Validación**:
  - JUnit, Mockito y otras herramientas de pruebas.

---

## 🚀 Próximas Mejoras

El mantenimiento y la evolución del juego están previstos durante los **próximos 5 años**, enfocados en:

- Mejorar los algoritmos de la máquina.
- Implementar nuevas mecánicas y modos de juego.
- Aumentar las opciones de accesibilidad.

---

## 👨‍🏫 Profesores

- Andrea Cimmino
- Carlos Badenes

---

## 👨‍💻 Desarrolladores

Proyecto realizado por el equipo de la **Universidad Politécnica de Madrid**:

- **Raúl Gonzalo Toapanta**  
  [gonzalo.tpaucar@alumnos.upm.es](mailto:gonzalo.tpaucar@alumnos.upm.es)
- **Javier de Santiago Soto**  
  [javier.desantiago@alumnos.upm.es](mailto:javier.desantiago@alumnos.upm.es)
- **Araceli Argandoña Macollunco**  
  [a.argandona@alumnos.upm.es](mailto:a.argandona@alumnos.upm.es)
- **Daniel Rodríguez de Nobrega**  
  [daniel.rdenobrega@alumnos.upm.es](mailto:daniel.rdenobrega@alumnos.upm.es)
- **Estefanía Piccetti**  
  [estefania.piccetti@alumnos.upm.es](mailto:estefania.piccetti@alumnos.upm.es)

---

## 🌟 Licencia

Este proyecto cumple con la **LOPD-GDD** y se rige bajo la [Licencia MIT](LICENSE). Los datos de los jugadores serán
usados exclusivamente con fines relacionados con el juego.

---

## 📅 Fechas Clave

- **Entrega 1** (Requisitos y Análisis): 16 de marzo de 2024.
- **Entrega 2** (Diseño, Implementación y Pruebas): 25 de mayo de 2024. (26 por méritos)

El proyecto se gestionará y entregará a través de **Redmine**, **Gitlab**, y el ecosistema de **Moodle** de la asignatura.

---

> “El viento y las olas siempre están del lado del navegante más hábil.”  
> *Edmund Gibbon*
