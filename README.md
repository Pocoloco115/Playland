# PlayLand

PlayLand es una aplicación móvil desarrollada en Kotlin con Jetpack Compose en Android Studio. Reúne varios videojuegos clásicos en una sola aplicación con el objetivo de ofrecer entretenimiento sencillo y adictivo.

![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?logo=android-studio&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)

---

## Videojuegos Incluidos

### Catch Food
Juego de reflejos donde el jugador debe atrapar la comida que cae mientras evita perder vidas.

**Características:**
- Sistema de puntuación
- Aumento progresivo de dificultad
- Controles táctiles simples

### Flappy Bird
Versión del clásico juego arcade en el que se controla un pájaro que debe atravesar obstáculos sin chocar.

**Características:**
- Generación dinámica de obstáculos
- Sistema de puntuación y récord
- Física básica

### Tic Tac Toe
El clásico juego de tres en raya para dos jugadores en el mismo dispositivo.

**Características:**
- Modo multijugador local (hotseat)
- Detección automática de ganador y empate
- Reinicio rápido de partida

### Snake
Versión clásica del juego Snake. Controla una serpiente que crece al comer comida y evita chocar contra las paredes o contra sí misma.

**Características:**
- Incremento de velocidad según la puntuación
- Controles direccionales
- Sistema de puntuación

---

## Estructura del Proyecto

```
Playland2/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/playland2/
│   │   │   │   ├── core/              
│   │   │   │   └── feature/
│   │   │   │       ├── snake/         
│   │   │   │       ├── flappybird/    
│   │   │   │       ├── catchfood/    
│   │   │   │       ├── tictactoe/     
│   │   │   │       └── menu/          
│   │   │   ├── res/                  
│   │   │   └── assets/
│   │   │       └── games/             
│   │   │
│   ├── build.gradle.kts
│   └── AndroidManifest.xml
│
└──media/                            

```

---

## Descargas

Las versiones estables de la aplicación se publicarán en la sección **[Releases](https://github.com/Pocoloco115/Playland/releases)** de este repositorio en formato APK.

**Recomendado:** Descarga el APK desde Releases en lugar de compilar el proyecto manualmente.

---

## Tecnologías Utilizadas

- Kotlin
- Jetpack Compose
- Android Studio
- Gradle
- Coil (para carga de imágenes desde assets)
- Material Design 3

---

## Equipo de Desarrollo

- **Kristel Geraldine Villalta Porras**
- **Cristopher Amaru Rodriguez Arauz**
- **William Alberto Torres Moreira**
- **Walter Javier López Villega**

---

## Cómo Ejecutar el Proyecto (Desarrollo)

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Pocoloco115/Playland.git