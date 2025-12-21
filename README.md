# 🌾 Farming Simulator

A comprehensive 2D tile-based farming simulation game developed in **Java** as a 3rd semester academic project. This interactive game features dynamic crop management, day-night cycles, player progression systems, and a servlet-based backend for persistent game state management.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-007396?style=for-the-badge&logo=java&logoColor=white)
![Gson](https://img.shields.io/badge/Gson-4285F4?style=for-the-badge&logo=google&logoColor=white)

---

## 📋 Table of Contents

- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [Project Architecture](#-project-architecture)
- [Installation & Setup](#-installation--setup)
- [How to Play](#-how-to-play)
- [Project Structure](#-project-structure)
- [Game Components](#-game-components)
- [Backend Integration](#-backend-integration)
- [Controls](#-controls)
- [Screenshots](#-screenshots)
- [Learning Outcomes](#-learning-outcomes)
- [Future Enhancements](#-future-enhancements)
- [Contributors](#-contributors)
- [License](#-license)

---

## ✨ Features

### Core Gameplay
- **Tile-Based World Rendering**: Smooth 2D world with camera system and auto-tile detection
- **Farming Mechanics**: Complete crop lifecycle - plant, water, grow, and harvest
- **Dynamic Day-Night Cycle**: Time-based progression with 20-second day cycles
- **Player Movement**: WASD keyboard controls with directional sprite animations
- **Interactive Tilling**: Transform grass tiles into farmland for planting crops
- **Crop Management**: Multiple crop types with growth stages and harvest mechanics
- **Currency System**: Earn coins by harvesting crops to buy more seeds

### Technical Features
- **Double Buffering**: Implements BufferedImage strategy for flicker-free rendering
- **Auto-Tile Detection**: Automatically detects tile size from sprite sheets
- **Sprite Animation System**: Idle and walking animations for player character
- **HUD (Heads-Up Display)**: Real-time display of player stats, money, and day counter
- **Shop System**: In-game store for purchasing seeds and farming tools
- **Save/Load System**: Persistent game progress using file serialization
- **Backend Integration**: Jakarta Servlets with Gson for JSON-based state management
- **Camera Follow System**: Smooth camera tracking of player movement

---

## 🛠 Technologies Used

| Technology | Purpose |
|------------|----------|
| **Java SE** | Core game logic and OOP implementation |
| **Jakarta Servlets** | Backend HTTP request handling |
| **Gson Library** | JSON serialization/deserialization |
| **Java AWT/Swing** | Graphics rendering and UI components |
| **BufferedImage** | Image handling and double buffering |
| **ImageIO** | Sprite sheet loading and processing |
| **Java Collections** | Data structures (HashMap, Map) |

---

## 🏗 Project Architecture

The project follows **Object-Oriented Programming** principles with clear separation of concerns:

```
┌─────────────────────────────────────────┐
│         Main.java (Entry Point)         │
└────────────────┬────────────────────────┘
                 │
        ┌────────▼─────────┐
        │  GameWindow.java │
        │   (JFrame Setup) │
        └────────┬─────────┘
                 │
        ┌────────▼─────────┐
        │    Game.java     │
        │  (Game Loop &    │
        │   Rendering)     │
        └─┬────────────┬───┘
          │            │
    ┌─────▼─────┐  ┌──▼────────┐
    │ Renderer  │  │  Camera   │
    └───────────┘  └───────────┘
          │            │
    ┌─────▼─────┐  ┌──▼────────┐
    │  Player   │  │  TileMap  │
    │  Entity   │  │  World    │
    └───────────┘  └──┬────────┘
          │            │
    ┌─────▼─────┐  ┌──▼────────┐
    │   Crop    │  │   Tile    │
    │  System   │  │  System   │
    └───────────┘  └───────────┘
          │
    ┌─────▼──────────────┐
    │   GameServlet      │
    │  (Backend API)     │
    │   WorldState       │
    │   PlayerData       │
    └────────────────────┘
```

---

## 🚀 Installation & Setup

### Prerequisites
- **Java JDK 11** or higher
- **Apache Tomcat 10+** (for servlet deployment)
- **Gson Library** (com.google.gson)
- **Jakarta Servlet API**

### Step 1: Clone the Repository
```bash
git clone https://github.com/shadowassassin0903/farming-sim.git
cd farming-sim
```

### Step 2: Compile the Project
```bash
# Ensure all .java files are in the same directory
javac -cp .:gson.jar:jakarta.servlet-api.jar *.java
```

### Step 3: Run the Game (Standalone)
```bash
java Main
```

### Step 4: Deploy Servlet (Optional)
1. Package the servlet classes:
   ```bash
   jar cvf farming-sim.war GameServlet.class WorldState.class PlayerData.class
   ```
2. Deploy to Tomcat `webapps` folder
3. Access servlet at: `http://localhost:8080/farming-sim/game`

---

## 🎮 How to Play

1. **Launch the game** using `java Main`
2. **Move your character** using WASD keys
3. **Till the soil** by left-clicking on grass tiles
4. **Buy seeds** by pressing `1` (costs 5 coins)
5. **Select seeds** by pressing `2`
6. **Plant crops** by left-clicking on tilled soil
7. **Water crops** by right-clicking on planted crops
8. **Harvest** by left-clicking on fully grown crops
9. **Earn coins** from harvesting to buy more seeds
10. **Watch the day counter** - crops grow over time!

---

## 📁 Project Structure

```
farming-sim/
│
├── Main.java                    # Application entry point
├── Game.java                    # Core game loop and update logic
├── GameWindow.java              # JFrame window setup
├── GameClient.java              # Client-side networking
├── GameServlet.java             # Backend servlet for state management
│
├── Player.java                  # Player entity with movement and inventory
├── PlayerData.java              # Serializable player data model
│
├── Tile.java                    # Individual tile representation
├── TileMap.java                 # 2D tile grid world
├── Tileset.java                 # Tile sprite sheet loader
├── AutoTileDetector.java        # Auto-detect tile dimensions
│
├── Crop.java                    # Crop entity with growth stages
├── CropSheet.java               # Crop sprite sheet manager
│
├── Camera.java                  # Viewport and camera system
├── Renderer.java                # Graphics rendering engine
├── HUD.java                     # Heads-up display overlay
│
├── InputHandler.java            # Keyboard and mouse input
├── SpriteSheet.java             # Generic sprite sheet loader
├── Shop.java                    # In-game shop system
├── SaveLoad.java                # Game save/load functionality
│
├── WorldState.java              # Backend world state model
├── GameState.java               # Game state management
│
└── assets/
    ├── Tileset Spring.png       # Tile sprites
    ├── Spring Crops.png         # Crop sprites
    ├── Idle.png                 # Player idle animation
    └── Walk.png                 # Player walking animation
```

---

## 🎯 Game Components

### 1. Player System (`Player.java`)
- **Inventory Management**: Track seeds and coins
- **Tool Selection**: Switchable tools (Hoe, Hand, etc.)
- **Animation**: Idle and walking sprite animations
- **Movement**: Tile-based positional system

### 2. World System (`TileMap.java`, `Tile.java`)
- **Grid-Based World**: 80×60 tile map
- **Tile States**: Grass, Tilled, Planted
- **Day Advancement**: Triggers crop growth

### 3. Crop System (`Crop.java`, `CropSheet.java`)
- **Growth Stages**: Multi-stage crop lifecycle
- **Watering Mechanic**: Affects growth rate
- **Harvest Values**: Different coins per crop type

### 4. Rendering System (`Renderer.java`, `Camera.java`)
- **Viewport**: 20×15 tile visible area
- **Scaling**: 3x pixel scaling for retro aesthetic
- **Layer Rendering**: Background → Tiles → Crops → Player → HUD

### 5. Input System (`InputHandler.java`)
- **Keyboard Events**: WASD movement, hotkeys
- **Mouse Events**: Click-to-interact mechanics
- **Key State Tracking**: Continuous and just-pressed detection

---

## 🌐 Backend Integration

### GameServlet.java
The servlet provides a **RESTful API** for game state management:

#### Endpoints:

**GET** `/game`
- Returns current world state as JSON
- Response: `{"world": {...}, "players": [...]}`

**POST** `/game`
- Updates player data on the server
- Request Body: `{"playerId": "...", "coins": 100, "inventory": {...}}`
- Response: `{"status": "ok"}`

### Data Flow:
```
GameClient → HTTP POST → GameServlet → WorldState (in-memory)
                              ↓
                        Gson.toJson()
                              ↓
                      JSON Response ← GameClient
```

---

## 🎮 Controls

| Key | Action |
|-----|--------|
| **W** | Move Up |
| **A** | Move Left |
| **S** | Move Down |
| **D** | Move Right |
| **1** | Buy Carrot Seed (5 coins) |
| **2** | Select Carrot Seed for Planting |
| **H** | Select Hoe Tool |
| **Left Click** | Till Soil / Plant Seed / Harvest Crop |
| **Right Click** | Water Crop |

---

## 📸 Screenshots

_Add your game screenshots here once you have them!_

```
[Gameplay Screenshot 1]
[Gameplay Screenshot 2]
[Shop System Screenshot]
```

---

## 🎓 Learning Outcomes

This project demonstrates understanding of:

### Object-Oriented Programming
- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: SpriteSheet classes extend base functionality
- **Polymorphism**: Tool enum with different behaviors
- **Abstraction**: Separate rendering and game logic

### Game Development Concepts
- **Game Loop Architecture**: Fixed update rate with variable rendering
- **Double Buffering**: Prevents screen tearing and flicker
- **Sprite Sheet Management**: Efficient texture atlas usage
- **Camera Systems**: Viewport calculations and world-to-screen conversion

### Backend Development
- **Servlet Lifecycle**: doGet(), doPost() implementation
- **JSON Processing**: Gson library for serialization
- **HTTP Protocol**: RESTful API design
- **State Management**: In-memory game state on server

### Software Engineering
- **Version Control**: Git and GitHub workflow
- **Project Structure**: Organized file hierarchy
- **Code Documentation**: Clear variable and method naming
- **Separation of Concerns**: Modular class design

---

## 🚀 Future Enhancements

- [ ] Add multiple crop varieties (wheat, tomato, corn)
- [ ] Implement weather system affecting crop growth
- [ ] Add NPC characters and trading system
- [ ] Create seasonal changes in tileset
- [ ] Add sound effects and background music
- [ ] Implement multiplayer support via WebSockets
- [ ] Add database persistence (MySQL/PostgreSQL)
- [ ] Create tool upgrade system
- [ ] Add farm animals (chickens, cows)
- [ ] Implement quest/achievement system

---

## 👥 Contributors

- **Shaswat** - *Lead Developer & Designer*
- **Anuj Kumar** - *UI & UX and Testing & Designer*
- **Vikash Kumar** - *Testing and Support*

---

## 📄 License

This project is created for **educational purposes** as part of a 3rd semester Java coursework.

---

## 🙏 Acknowledgments

- Sprite assets from open-source farming game tilesets
- Jakarta EE community for servlet documentation
- Java AWT/Swing tutorials and resources

---

## 📞 Contact

For questions or feedback about this project:
- GitHub: [@shadowassassin0903](https://github.com/shadowassassin0903)
- Repository: [farming-sim](https://github.com/shadowassassin0903/farming-sim)

---

**⭐ If you found this project helpful, please consider giving it a star!**

---

*Developed with ☕ and 💚 for 3rd Semester Java Project*
