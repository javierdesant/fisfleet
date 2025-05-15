# Endpoints Resumen

| **Categoría**                      | **Método** | **Endpoint**                      | **Descripción**                                                                                       |
|------------------------------------|------------|-----------------------------------|-------------------------------------------------------------------------------------------------------|
| **Autenticación y Usuarios**       |
| Registro de usuarios               | POST       | `/api/users/register`             | Registra un usuario humano validando contra el LDAP de la UPM.                                        |
| Inicio de sesión                   | POST       | `/api/users/login`                | Autentica usuarios y devuelve un token de sesión.                                                     |
| Actualizar perfil                  | PUT        | `/api/users/profile`              | Permite actualizar información del perfil del usuario (excepto para administradores).                 |
| Detalles del usuario               | GET        | `/api/users/profile`              | Devuelve la información del perfil del usuario logueado.                                              |
| **Gestión de Partidas**            |
| Crear partida                      | POST       | `/api/matches`                    | Crea una nueva partida contra una máquina con dificultad seleccionada (FÁCIL, NORMAL, DIFÍCIL).       |
| Detalles de partida                | GET        | `/api/matches/:matchId`           | Devuelve el estado actual de una partida específica.                                                  |
| Registrar ataque                   | POST       | `/api/matches/:matchId/attack`    | Registra un ataque en una posición del tablero enemigo y devuelve el resultado (impacto/agua).        |
| Activar habilidad                  | POST       | `/api/matches/:matchId/abilities` | Activa la habilidad especial del barco impactado, según las reglas del juego.                         |
| Configuración del tablero inicial  | GET        | `/api/matches/:matchId/board`     | Muestra la distribución inicial de los barcos del jugador y la máquina.                               |
| Finalizar partida                  | POST       | `/api/matches/:matchId/finish`    | Finaliza la partida y calcula las puntuaciones finales de ambos jugadores.                            |
| Listar partidas del usuario        | GET        | `/api/matches`                    | Devuelve una lista de las partidas del usuario logueado.                                              |
| Reproducción de partida            | GET        | `/api/matches/:matchId/replay`    | Devuelve todos los movimientos y eventos de una partida en forma de lista ordenada.                   |
| **Puntuaciones**                   |
| Mejores puntuaciones               | GET        | `/api/scores/top10`               | Lista las 10 mejores puntuaciones de todos los jugadores, ordenadas de mayor a menor.                 |
| Puntuaciones personales            | GET        | `/api/scores/user/:userId`        | Devuelve las puntuaciones del jugador logueado.                                                       |
| Ver todas las puntuaciones (admin) | GET        | `/api/scores`                     | Lista todas las puntuaciones, incluidas las de las máquinas (solo disponible para administradores).   |
| **Administración**                 |
| Crear máquina                      | POST       | `/api/admin/machines`             | Permite crear una máquina con un nivel de dificultad (FÁCIL, NORMAL, DIFÍCIL).                        |
| Listar máquinas                    | GET        | `/api/admin/machines`             | Devuelve una lista de todas las máquinas creadas.                                                     |
| Estadísticas de partidas (admin)   | GET        | `/api/admin/matches`              | Lista todas las partidas, incluidas las de los usuarios y máquinas.                                   |
| Estrategias de máquinas            | GET        | `/api/admin/machines/strategies`  | Muestra información y métricas sobre las estrategias de ataque y defensa de las máquinas (debugging). |
