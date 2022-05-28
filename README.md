
# Planeteer

2022 AP CS A Final Project. Space Invadersesque game writen in Java

![Demo](https://github.com/ParadoxFlame/Planeteer/blob/master/assets/game/Demo.png)
## Authors

- [@ParadoxFlame](https://github.com/ParadoxFlame)
- [@asdfqwertyzxcvtyui](https://github.com/asdfqwertyzxcvtyui)
- [Suvan Amruth](https://www.instagram.com/suvan_a/)


## Project Structure

```
.
├── assets                      # assets
├── core                        # core interpreter module
│   └── src/.../
│       ├── Bullets             # bulets used by both the player and the enemy
│       ├── Enemy               # enemy
│       ├── Game                # core class, renders everything
│       ├── Rumble              # camera shake effect
├── desktop                     
│   └── src/.../
│       ├── DesktopLauncher     # launcher window
├── docs                        # javadocs
```
## Run Locally

Requires [Gradle](https://gradle.org/install/)

Clone the project

```bash
  git clone https://github.com/ParadoxFlame/Planeteer
```

Go to the project directory

```bash
  cd Planeteer
```

Run the game

```bash
  ./gradlew desktop:run
```

Use `WASD` or `arrow keys` to move. Use `space` to shoot

## Documentation

[Link to Javadocs](https://paradoxflame.github.io/Planeteer)


## License

The project is licensed under the [MIT](https://github.com/ParadoxFlame/Planeteer/blob/master/docs/javadoc/legal/LICENSE) License, meaning you can use it free of charge, without strings attached in commercial and non-commercial projects.



